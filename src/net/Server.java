package net;

import dao.*;
import events.PlayEvent;
import events.UploadEvent;
import model.*;

import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;

final class Server {
    static final int PORT_NUMBER = 5555;
    static final String IP_ADDRESS = "127.0.0.1";

    private static final Server singleton = new Server();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Integer, ClientThread> onlineUsers = Collections.synchronizedMap(new HashMap<>());

    private final UserDAOFactory userDAOFactory = new UserDAOFactory();
    private final AccountDAOFactory accountDAOFactory = new AccountDAOFactory();
    private final AccountSongDAOFactory accountSongDAOFactory = new AccountSongDAOFactory();
    private final AccountPlaylistDAOFactory accountPlaylistDAOFactory = new AccountPlaylistDAOFactory();
    private final AccountAlbumDAOFactory accountAlbumDAOFactory = new AccountAlbumDAOFactory();
    private final SubscriptionDAOFactory subscriptionDAOFactory = new SubscriptionDAOFactory();
    private final SongDAOFactory songDAOFactory = new SongDAOFactory();
    private final AlbumDAOFactory albumDAOFactory = new AlbumDAOFactory();
    private final PlaylistDAOFactory playlistDAOFactory = new PlaylistDAOFactory();
    private final PlaylistSongDAOFactory playlistSongDAOFactory = new PlaylistSongDAOFactory();
    private final ArtistDAOFactory artistDAOFactory = new ArtistDAOFactory();
    private boolean shutdown = false;

    private Server() {    }

    static Server getInstance() { return singleton; }

    void shutDown() { shutdown = true; }

    boolean isShutDown() { return shutdown; }

    void startThread(Runnable thread) {
        executor.submit(thread);
    }

    Account getAccount(int accountId) { return ((AccountDAO)accountDAOFactory.getDAO()).find(accountId); }

    Song getSong(int songId) {
        return ((SongDAO)songDAOFactory.getDAO()).find(songId);
    }

    ArrayList<Song> getSongs(){
        return ((SongDAO)songDAOFactory.getDAO()).listById();
    }

    ArrayList<Song> getFollowedSongs(int accountId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByAccount(accountId);
    }

    ArrayList<Song> getSongsByArtist(int artistId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByArtistId(artistId);
    }

    ArrayList<Song> getSongsInAlbum(int albumId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByAlbum(albumId);
    }

    ArrayList<Song> getSongsInPlaylist(int playlistId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByPlaylist(playlistId);
    }

    ArrayList<Song> getFavoriteSongs(int accountId) {
        return ((SongDAO)songDAOFactory.getDAO()).listFavorites(accountId);
    }

    boolean addSong(Song song){
        song.setArtist(getArtist(song.getArtist().getArtistId()));
        try {
            ((SongDAO)songDAOFactory.getDAO()).create(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        System.out.println("Song was added.");
        Artist artist = song.getArtist();
        System.out.println(artist);
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscribeeId(artist.getAccount().getId());
        System.out.print("Notifying the following accounts: ");
        for (int i = 0; i < followers.size(); i++) {
            System.out.print(followers.get(i));
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(artist.getAccount(), song));
        }
        return true;
    }

    void addSongToPlaylist(Song song, Playlist playlist) {
        ((PlaylistSongDAO)playlistSongDAOFactory.getDAO()).join(playlist, song);
    }

    void deleteSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).delete(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    void removeSongFromPlaylist(Song song, Playlist playlist) {
        ((PlaylistSongDAO)playlistSongDAOFactory.getDAO()).separate(playlist, song);
    }

    void updateSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).update(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    void playSong(Account account, Song song){
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscribeeId(account.getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new PlayEvent(account, song));
        }
//        ((AccountSongDAO)accountSongDAOFactory.getDAO()).playSong(account, song);
    }

    void followSong(Account account, Song song) {
        ((AccountSongDAO)accountSongDAOFactory.getDAO()).join(account, song);
    }

    void unfollowSong(Account account, Song song) {
        ((AccountSongDAO)accountSongDAOFactory.getDAO()).separate(account, song);
    }

    Playlist getPlaylist(int playlistId) {
        System.out.println(playlistId);
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).find(playlistId); }

    ArrayList<Playlist> getPlaylists() {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listById();
    }

    ArrayList<Playlist> getPlaylistsByAccount(int accountId) {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listByUserId(accountId);
    }

    ArrayList<Playlist> getFollowedPlaylists(int accountId){
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listFollowedPlaylists(accountId);
    }

    boolean addPlaylist(Playlist playlist){
        playlist.setAccount(getAccount(playlist.getAccount().getId()));
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).create(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        Account account = playlist.getAccount();
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscribeeId(account.getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(account, playlist));
        }
        return true;
    }

    void deletePlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).delete(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    void updatePlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).update(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    void followPlaylist(Account account, Playlist playlist){
        ((AccountPlaylistDAO)accountPlaylistDAOFactory.getDAO()).join(account, playlist);
    }

    void unfollowPlaylist(Account account, Playlist playlist){
        ((AccountPlaylistDAO)accountPlaylistDAOFactory.getDAO()).separate(account, playlist);
    }

    Album getAlbum(int albumId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).find(albumId);
    }

    ArrayList<Album> getAlbums(){
        return ((AlbumDAO)albumDAOFactory.getDAO()).listById();
    }

    ArrayList<Album> getFollowedAlbums(int accountId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).listByAccount(accountId);
    }

    ArrayList<Album> getAlbumsByArtist(int artistId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).listByArtist(artistId);
    }

    boolean addAlbum(Album album){
        album.setArtist(getArtist(album.getArtist().getArtistId()));
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).create(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        Artist artist = album.getArtist();
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscribeeId(artist.getAccount().getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(artist.getAccount(), album));
        }
        return true;
    }

    void deleteAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).delete(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
        }
    }

    void updateAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).update(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
        }
    }

    void followAlbum(Account account, Album album){
        ((AccountAlbumDAO)accountAlbumDAOFactory.getDAO()).join(account, album);
    }

    void unfollowAlbum(Account account, Album album){
        ((AccountAlbumDAO)accountAlbumDAOFactory.getDAO()).separate(account, album);
    }

    User getUser(int userId) { return ((UserDAO)userDAOFactory.getDAO()).find(userId); }

    ArrayList<User> getUsers(){
        return ((UserDAO)userDAOFactory.getDAO()).listById();
    }

    ArrayList<User> getFollowedUsers(int accountId) {
        return ((UserDAO)userDAOFactory.getDAO()).listByFollowedUsers(accountId);
    }

    boolean addUser(User user){
        try {
            UserDAO userDAO = ((UserDAO)userDAOFactory.getDAO());
            AccountDAO accountDAO = ((AccountDAO)accountDAOFactory.getDAO());
            if (accountDAO.existUserName(user.getAccount().getUserName())) return false;
            accountDAO.insert(user.getAccount());
            userDAO.create(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void updateUser(User user){
        try {
            ((UserDAO)userDAOFactory.getDAO()).update(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    void followUser(Account account, User user){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).join(account, user.getAccount());
    }

    void unfollowUser(Account account, User user){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).separate(account, user.getAccount());
    }

    Artist getArtist(int artistId) { return ((ArtistDAO)artistDAOFactory.getDAO()).find(artistId); }

    ArrayList<Artist> getArtists(){
        return ((ArtistDAO)artistDAOFactory.getDAO()).listById();
    }

    ArrayList<Artist> getFollowedArtists (int accountId) {
        return ((ArtistDAO)artistDAOFactory.getDAO()).listByFollowedArtists(accountId);
    }

    boolean addArtist(Artist artist){
        try {
            ArtistDAO artistDAO = ((ArtistDAO)artistDAOFactory.getDAO());
            AccountDAO accountDAO = ((AccountDAO)accountDAOFactory.getDAO());
            if (accountDAO.existUserName(artist.getAccount().getUserName())) return false;
            accountDAO.insert(artist.getAccount());
            System.out.println(artist.getAccount());
            artistDAO.create(artist);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    void updateArtist(Artist artist){
        try {
            ((ArtistDAO)artistDAOFactory.getDAO()).update(artist);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    void followArtist(Account account, Artist artist){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).join(account, artist.getAccount());
    }

    void unfollowArtist(Account account, Artist artist){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).separate(account, artist.getAccount());
    }

    void setImageFile(int albumId, File img){
        AlbumDAO albumDAO = ((AlbumDAO)albumDAOFactory.getDAO());
        Album album = albumDAO.find(albumId);
        album.setCover(img);
        updateAlbum(album);
    }

    void setSongFile(int songId, File wav){
        SongDAO songDAO = ((SongDAO)songDAOFactory.getDAO());
        Song song = songDAO.find(songId);
        song.setWAV(wav);
        updateSong(song);
    }


    User login(String username, String password, ClientThread thread){
        Account account = ((AccountDAO)accountDAOFactory.getDAO()).find(username, password);
        if (account == null) return null;
        User user = ((UserDAO)userDAOFactory.getDAO()).findByAccountID(account.getId());
        if (user == null) user = ((ArtistDAO)artistDAOFactory.getDAO()).findByAccountID(account.getId());
        if (user != null) onlineUsers.put(user.getAccount().getId(), thread);
        System.out.println(user);
        System.out.println(user.getAccount());
        return user;
    }

    void logout(int accountId){
        onlineUsers.remove(accountId);
    }

    ArrayList<Song> searchSongs(String keyword) {
        return ((SongDAO)songDAOFactory.getDAO()).search(keyword);
    }

    ArrayList<Playlist> searchPlaylists(String keyword) {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).search(keyword);
    }

    ArrayList<Album> searchAlbums(String keyword) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).search(keyword);
    }

    ArrayList<User> searchUsers(String keyword) {
        return ((UserDAO)userDAOFactory.getDAO()).search(keyword);
    }

    ArrayList<Artist> searchArtists(String keyword) {
        return ((ArtistDAO)artistDAOFactory.getDAO()).search(keyword);
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            while (!server.isShutDown()) {
                Socket client = serverSocket.accept();
                System.out.println("Client accepted.");
                ClientThread thread = new ClientThread(client);
                server.startThread(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
