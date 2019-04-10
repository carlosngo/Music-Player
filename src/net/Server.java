package net;

import com.sun.org.apache.bcel.internal.generic.ALOAD;
import dao.*;
import events.PlayEvent;
import events.UploadEvent;
import model.*;

import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;

public final class Server {
    public static final int PORT_NUMBER = 5555;
    public static final String IP_ADDRESS = "127.0.0.1";

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
    private final AlbumSongDAOFactory albumSongDAOFactory = new AlbumSongDAOFactory();
    private final PlaylistDAOFactory playlistDAOFactory = new PlaylistDAOFactory();
    private final PlaylistSongDAOFactory playlistSongDAOFactory = new PlaylistSongDAOFactory();
    private final ArtistDAOFactory artistDAOFactory = new ArtistDAOFactory();
    private boolean shutdown = false;

    private Server() {    }

    public static Server getInstance() { return singleton; }

    public void shutDown() { shutdown = true; }

    public boolean isShutDown() { return shutdown; }

    public void startThread(Runnable thread) {
        executor.submit(thread);
    }

    public Song getSong(int songId) {
        return ((SongDAO)songDAOFactory.getDAO()).find(songId);
    }

    public ArrayList<Song> getSongs(){
        return ((SongDAO)songDAOFactory.getDAO()).listById();
    }

    public ArrayList<Song> getSongsByAccount(int accountId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByAccount(accountId);
    }

    public ArrayList<Song> getSongsByArtist(int artistId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByArtistId(artistId);
    }

    public ArrayList<Song> getSongsInAlbum(int albumId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByAlbum(albumId);
    }

    public ArrayList<Song> getSongsInPlaylist(int playlistId) {
        return ((SongDAO)songDAOFactory.getDAO()).listByPlaylist(playlistId);
    }

    public ArrayList<Song> getSongsWithGenre(String genre) {
        return ((SongDAO)songDAOFactory.getDAO()).listByGenre(genre);
    }

    public boolean addSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).create(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        Artist artist = song.getArtist();
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscriberId(artist.getAccount().getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(artist, song));
        }
        return true;
    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        ((PlaylistSongDAO)playlistDAOFactory.getDAO()).join(playlist, song);
    }

    public void deleteSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).delete(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    public void removeSongFromPlaylist(Song song, Playlist playlist) {
        ((PlaylistSongDAO)playlistDAOFactory.getDAO()).separate(playlist, song);
    }

    public void updateSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).update(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    public void playSong(Account account, Song song){
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscriberId(account.getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new PlayEvent(account, song));
        }
        ((AccountSongDAO)accountSongDAOFactory.getDAO()).playSong(account, song);
    }

    public void followSong(Account account, Song song) {
        ((AccountSongDAO)accountSongDAOFactory.getDAO()).join(account, song);
    }

    public void unfollowSong(Account account, Song song) {
        ((AccountSongDAO)accountSongDAOFactory.getDAO()).separate(account, song);
    }

    public Playlist getPlaylist(int playlistId) { return ((PlaylistDAO)playlistDAOFactory.getDAO()).find(playlistId); }

    public ArrayList<Playlist> getPlaylists() {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listById();
    }

    public ArrayList<Playlist> getPlaylistsByAccount(int accountId) {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listByUserId(accountId);
    }

    public boolean addPlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).create(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        Account account = playlist.getAccount();
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscriberId(account.getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(account, playlist));
        }
        return true;
    }

    public void deletePlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).delete(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    public void updatePlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).update(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    public void followPlaylist(Account account, Playlist playlist){
        ((AccountPlaylistDAO)accountPlaylistDAOFactory.getDAO()).join(account, playlist);
    }

    public void unfollowPlaylist(Account account, Playlist playlist){
        ((AccountPlaylistDAO)accountPlaylistDAOFactory.getDAO()).separate(account, playlist);
    }

    public Album getAlbum(int albumId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).find(albumId);
    }

    public ArrayList<Album> getAlbums(){
        return ((AlbumDAO)albumDAOFactory.getDAO()).listById();
    }

    public ArrayList<Album> getAlbumsByAccount(int accountId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).listByAccount(accountId);
    }

    public boolean addAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).create(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        Artist artist = album.getArtist();
        ArrayList<Integer> followers =
                ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).listBySubscriberId(artist.getAccount().getId());
        for (int i = 0; i < followers.size(); i++) {
            onlineUsers.get(followers.get(i)).listen(new UploadEvent(artist, album));
        }
        return true;
    }

    public void deleteAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).delete(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
        }
    }

    public void updateAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).delete(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
        }
    }

    public void followAlbum(Account account, Album album){
        ((AccountAlbumDAO)accountAlbumDAOFactory.getDAO()).join(account, album);
    }

    public void unfollowAlbum(Account account, Album album){
        ((AccountAlbumDAO)accountAlbumDAOFactory.getDAO()).separate(account, album);
    }

    public User getUser(int userId) { return ((UserDAO)userDAOFactory.getDAO()).find(userId); }

    public ArrayList<User> getUsers(){
        return ((UserDAO)userDAOFactory.getDAO()).listById();
    }

    public ArrayList<User> getFollowedUsers(int accountId) {
        return ((UserDAO)userDAOFactory.getDAO()).listFollowedUsers(accountId);
    }

    public boolean addUser(User user){
        try {
            UserDAO userDAO = ((UserDAO)userDAOFactory.getDAO());
            AccountDAO accountDAO = ((AccountDAO)accountDAOFactory.getDAO());
            if (accountDAO.existUserName(user.getUserName())) return false;
            accountDAO.insert(user.getAccount());
            userDAO.create(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void updateUser(User user){
        try {
            ((UserDAO)userDAOFactory.getDAO()).update(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void followUser(Account account, User user){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).join(account, user.getAccount());
    }

    public void unfollowUser(Account account, User user){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).separate(account, user.getAccount());
    }

    public Artist getArtist(int artistId) { return ((ArtistDAO)artistDAOFactory.getDAO()).find(artistId); }

    public ArrayList<Artist> getArtists(){
        return ((ArtistDAO)artistDAOFactory.getDAO()).listById();
    }

    public ArrayList<Artist> getFollowedArtists (int accountId) {
        return ((ArtistDAO)artistDAOFactory.getDAO()).listFollowedArtists(accountId);
    }

    public boolean addArtist(Artist artist){
        try {
            ArtistDAO artistDAO = ((ArtistDAO)artistDAOFactory.getDAO());
            AccountDAO accountDAO = ((AccountDAO)accountDAOFactory.getDAO());
            if (accountDAO.existUserName(artist.getUserName())) return false;
            accountDAO.insert(artist.getAccount());
            artistDAO.create(artist);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void updateArtist(Artist artist){
        try {
            ((ArtistDAO)artistDAOFactory.getDAO()).update(artist);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void followArtist(Account account, Artist artist){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).join(account, artist.getAccount());
    }

    public void unfollowArtist(Account account, Artist artist){
        ((SubscriptionDAO)subscriptionDAOFactory.getDAO()).separate(account, artist.getAccount());
    }

    public void setImageFile(int albumId, File img){
        AlbumDAO albumDAO = ((AlbumDAO)albumDAOFactory.getDAO());
        Album album = albumDAO.find(albumId);
        album.setCover(img);
        updateAlbum(album);
    }

    public void setSongFile(int songId, File wav){
        SongDAO songDAO = ((SongDAO)songDAOFactory.getDAO());
        Song song = songDAO.find(songId);
        song.setWAV(wav);
        updateSong(song);
    }


    public User login(String username, String password, ClientThread thread){
        Account account = ((AccountDAO)accountDAOFactory.getDAO()).find(username, password);
        User user = ((UserDAO)userDAOFactory.getDAO()).findByAccountID(account.getId());
        if (user == null) user = ((ArtistDAO)artistDAOFactory.getDAO()).findByAccountID(account.getId());
        if (user != null) onlineUsers.put(user.getAccount().getId(), thread);
        return user;
    }

    public void logout(int accountId){
        onlineUsers.remove(accountId);
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
