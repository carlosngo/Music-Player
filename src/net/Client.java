package net;

import util.FileUtil;
import util.Protocol;
import model.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public final class Client {
    private static final Client singleton = new Client();
    // Network variables to communicate with the server
    private Socket socket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private Client() { }

    public static Client getInstance() { return singleton; }

    public void startConnection() {
        try {
            socket = new Socket(Server.IP_ADDRESS, Server.PORT_NUMBER);
            outToServer = new PrintWriter(socket.getOutputStream(), true);
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            outToServer.close();
            inFromServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Song getSong(int songId) {
        outToServer.println(Protocol.GETSONG);
        outToServer.println(songId);
        try {
            return Song.parseSong(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Song> getSongs(){
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGS);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Song> getSongsByArtist(int artistId) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSBYARTIST);
        outToServer.println(artistId);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Song> getFollowedSongs(int accountId) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDSONGS);
        outToServer.println(accountId);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Song> getSongsInAlbum(int albumId) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSINALBUM);
        outToServer.println(albumId);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Song> getSongsInPlaylist(int playlistId) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSINPLAYLIST);
        outToServer.println(playlistId);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Song> getFavoriteSongs(int accountId) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETFAVORITESONGS);
        outToServer.println(accountId);
        readSongs(songs);
        return songs;
    }

    private void readSongs(ArrayList<Song> songs) {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                songs.add(Song.parseSong(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addSong(Song song){
        outToServer.println(Protocol.ADDSONG);
        outToServer.println(song);
        System.out.println(song);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
            else song.setSongId(Integer.parseInt(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void addSongToPlaylist(Song song, Playlist playlist) {
        outToServer.println(Protocol.ADDSONGTOPLAYLIST);
        outToServer.println(song);
        outToServer.println(playlist);
    }

    public void deleteSong(Song song){
        outToServer.println(Protocol.DELETESONG);
        outToServer.println(song);
    }

    public void removeSongFromPlaylist(Song song, Playlist playlist) {
        outToServer.println(Protocol.REMOVESONGFROMPLAYLIST);
        outToServer.println(song);
        outToServer.println(playlist);
    }

    public void updateSong(Song song){
        outToServer.println(Protocol.UPDATESONG);
        outToServer.println(song);
    }

    public void playSong(int accountId, int songId){
        outToServer.println(Protocol.PLAYSONG);
        outToServer.println(accountId);
        outToServer.println(songId);
    }

    public void followSong(Account follower, Song song){
        outToServer.println(Protocol.FOLLOWSONG);
        outToServer.println(follower);
        outToServer.println(song);
    }

    public void unfollowSong(Account follower, Song song){
        outToServer.println(Protocol.UNFOLLOWSONG);
        outToServer.println(follower);
        outToServer.println(song);
    }

    public Playlist getPlaylist(int playlistId) {
        outToServer.println(Protocol.GETPLAYLIST);
        outToServer.println(playlistId);
        try {
            return Playlist.parsePlaylist(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Playlist> getPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        outToServer.println(Protocol.GETPLAYLISTS);
        readPlaylists(playlists);
        return playlists;
    }

    public ArrayList<Playlist> getPlaylistsByAccount(int accountId) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        outToServer.println(Protocol.GETPLAYLISTSBYACCOUNT);
        outToServer.println(accountId);
        readPlaylists(playlists);
        return playlists;
    }

    public ArrayList<Playlist> getFollowedPlaylists(int accountId) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDPLAYLISTS);
        outToServer.println(accountId);
        readPlaylists(playlists);
        return playlists;
    }

    private void readPlaylists(ArrayList<Playlist> playlists) {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                playlists.add(Playlist.parsePlaylist(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addPlaylist(Playlist playlist){
        outToServer.println(Protocol.ADDPLAYLIST);
        outToServer.println(playlist);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
            else playlist.setPlaylistId(Integer.parseInt(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void deletePlaylist(Playlist playlist){
        outToServer.println(Protocol.DELETEPLAYLIST);
        outToServer.println(playlist);
    }

    public void updatePlaylist(Playlist playlist){
        outToServer.println(Protocol.UPDATEPLAYLIST);
        outToServer.println(playlist);
    }

    public void followPlaylist(Account follower, Playlist playlist){
        outToServer.println(Protocol.FOLLOWPLAYLIST);
        outToServer.println(follower);
        outToServer.println(playlist);
    }

    public void unfollowPlaylist(Account follower, Playlist playlist){
        outToServer.println(Protocol.UNFOLLOWPLAYLIST);
        outToServer.println(follower);
        outToServer.println(playlist);
    }

    public Album getAlbum(int albumId) {
        if (albumId == -1) return new Album();
        outToServer.println(Protocol.GETALBUM);
        outToServer.println(albumId);
        try {
            return Album.parseAlbum(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Album();
    }
    public ArrayList<Album> getAlbums(){
        ArrayList<Album> albums = new ArrayList<>();
        outToServer.println(Protocol.GETALBUMS);
        readAlbums(albums);
        return albums;
    }

    public ArrayList<Album> getFollowedAlbums(int accountId){
        ArrayList<Album> albums = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDALBUMS);
        outToServer.println(accountId);
        readAlbums(albums);
        return albums;
    }

    public ArrayList<Album> getAlbumsByArtist(int artistId) {
        ArrayList<Album> albums = new ArrayList<>();
        outToServer.println(Protocol.GETALBUMSBYARTIST);
        outToServer.println(artistId);
        readAlbums(albums);
        return albums;
    }

    private void readAlbums(ArrayList<Album> albums) {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                albums.add(Album.parseAlbum(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addAlbum(Album album){
        outToServer.println(Protocol.ADDALBUM);
        outToServer.println(album);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
            else album.setAlbumId(Integer.parseInt(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void deleteAlbum(Album album){
        outToServer.println(Protocol.DELETEALBUM);
        outToServer.println(album);
    }

    public void updateAlbum(Album album){
        outToServer.println(Protocol.UPDATEALBUM);
        outToServer.println(album);
    }

    public void followAlbum(Account follower, Album album){
        outToServer.println(Protocol.FOLLOWALBUM);
        outToServer.println(follower);
        outToServer.println(album);
    }

    public void unfollowAlbum(Account follower, Album album){
        outToServer.println(Protocol.UNFOLLOWALBUM);
        outToServer.println(follower);
        outToServer.println(album);
    }

    public User getUser(int userId) {
        outToServer.println(Protocol.GETUSER);
        outToServer.println(userId);
        try {
            return User.parseUser(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        outToServer.println(Protocol.GETUSERS);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                users.add(User.parseUser(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> getFollowedUsers(int accountId) {
        ArrayList<User> users = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDUSERS);
        outToServer.println(accountId);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                users.add(User.parseUser(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user){
        outToServer.println(Protocol.ADDUSER);
        outToServer.println(user);
        outToServer.println(user.getAccount());
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
            user.setUserId(Integer.parseInt(inFromServer.readLine()));
            user.getAccount().setId(Integer.parseInt(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateUser(User user){
        outToServer.println(Protocol.UPDATEUSER);
        outToServer.println(user);
    }

    public void followUser(Account follower, User user){
        outToServer.println(Protocol.FOLLOWUSER);
        outToServer.println(follower);
        outToServer.println(user);
    }

    public void unfollowUser(Account follower, User user){
        outToServer.println(Protocol.UNFOLLOWUSER);
        outToServer.println(follower);
        outToServer.println(user);
    }

    public Artist getArtist(int artistId) {
        outToServer.println(Protocol.GETARTIST);
        outToServer.println(artistId);
        try {
            return Artist.parseArtist(inFromServer.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Artist> getArtists(){
        ArrayList<Artist> artists = new ArrayList<>();
        outToServer.println(Protocol.GETARTISTS);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                artists.add(Artist.parseArtist(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artists;
    }

    public ArrayList<Artist> getFollowedArtists(int accountId) {
        ArrayList<Artist> artists = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDARTISTS);
        outToServer.println(accountId);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                artists.add(Artist.parseArtist(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artists;
    }

    public boolean addArtist(Artist artist){
        outToServer.println(Protocol.ADDARTIST);
        outToServer.println(artist);
        System.out.println(artist);
        outToServer.println(artist.getAccount());
        System.out.println(artist.getAccount());
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
            artist.setArtistId(Integer.parseInt(inFromServer.readLine()));
            artist.getAccount().setId(Integer.parseInt(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateArtist(Artist artist){
        outToServer.println(Protocol.UPDATEARTIST);
        outToServer.println(artist);
    }

    public void followArtist(Account follower, Artist artist){
        outToServer.println(Protocol.FOLLOWARTIST);
        outToServer.println(follower);
        outToServer.println(artist);
    }

    public void unfollowArtist(Account follower, Artist artist){
        outToServer.println(Protocol.UNFOLLOWARTIST);
        outToServer.println(follower);
        outToServer.println(artist);
    }

    public File getImageFile(int albumId){
        outToServer.println(Protocol.GETIMAGEFILE);
        outToServer.println(albumId);
        File dir = new File("resources/images");
        dir.mkdirs();
        File img = new File(dir, albumId + "");
        FileUtil.downloadFile(socket, inFromServer, outToServer, img);
        return img;
    }

    public void setImageFile(int albumId, File img) {
        if (img != null) {
            outToServer.println(Protocol.SETIMAGEFILE);
            outToServer.println(albumId);
            FileUtil.uploadFile(socket, inFromServer, outToServer, img);
        }
    }

    public File getSongFile(int songId){
        outToServer.println(Protocol.GETSONGFILE);
        outToServer.println(songId);
        File dir = new File("resources/songs");
        dir.mkdirs();
        File wav = new File(dir, songId + "");
        FileUtil.downloadFile(socket, inFromServer, outToServer, wav);
        return wav;
    }

    public void setSongFile(int songId, File wav){
        outToServer.println(Protocol.SETSONGFILE);
        outToServer.println(songId);
        FileUtil.uploadFile(socket, inFromServer, outToServer, wav);
    }

    public User logIn(String username, String password) {
        User user = null;
        outToServer.println(Protocol.LOGIN);
        outToServer.println(username);
        outToServer.println(password);
        try {
            Protocol protocol = Protocol.valueOf(inFromServer.readLine());
            switch (protocol) {
                case OK:
                    String userInfo = inFromServer.readLine();
                    if (userInfo.split("\\|").length < 6) user = Artist.parseArtist(userInfo);
                    else user = User.parseUser(userInfo);
                    user.setAccount(Account.parseAccount(inFromServer.readLine()));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void logOut(int userId) {
        outToServer.println(Protocol.LOGOUT);
        outToServer.println(userId);
    }

    public ArrayList<Song> searchSongs(String keyword) {
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.SEARCHSONGS);
        outToServer.println(keyword);
        readSongs(songs);
        return songs;
    }

    public ArrayList<Playlist> searchPlaylists(String keyword) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        outToServer.println(Protocol.SEARCHPLAYLISTS);
        outToServer.println(keyword);
        readPlaylists(playlists);
        return playlists;
    }

    public ArrayList<Album> searchAlbums(String keyword) {
        ArrayList<Album> albums = new ArrayList<>();
        outToServer.println(Protocol.SEARCHALBUMS);
        outToServer.println(keyword);
        readAlbums(albums);
        return albums;
    }

    public ArrayList<User> searchUsers(String keyword) {
        ArrayList<User> users = new ArrayList<>();
        outToServer.println(Protocol.SEARCHUSERS);
        outToServer.println(keyword);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) users.add(User.parseUser(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Artist> searchArtists(String keyword) {
        ArrayList<Artist> artists = new ArrayList<>();
        outToServer.println(Protocol.SEARCHARTISTS);
        outToServer.println(keyword);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) artists.add(Artist.parseArtist(inFromServer.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artists;
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.startConnection();
        c.getSongFile(22);
    }
}
