package net;

import util.FileUtil;
import util.Protocol;
import model.*;
import controller.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;


public final class Client {
    public static final String IP_ADDRESS = "";
    private static final Client singleton = new Client();
    // Network variables to communicate with the server
    private Socket socket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    // Controller
    private MainController mc;

    // Models
    private Account account;
    private Song song;
    private Playlist playlist;
    private Album album;
    private User user;
    private Artist artist;
    private File img;
    private File wav;
    private ArrayList<Song> songs;
    private ArrayList<Playlist> playlists;
    private ArrayList<Album> albums;
    private ArrayList<User> users;
    private ArrayList<Artist> artists;
    private boolean success;

    private boolean isBusy;


    public synchronized boolean isBusy() {
        return isBusy;
    }

    private Client() { }

    public void setMainController(MainController mc) {
        this.mc = mc;
    }

    public static Client getInstance() { return singleton; }

    public void startConnection() {
        try {
            socket = new Socket(Server.IP_ADDRESS, Server.PORT_NUMBER);
            outToServer = new PrintWriter(socket.getOutputStream(), true);
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            executor.submit(() -> {
                while (true) {
                    Protocol protocol = Protocol.valueOf(inFromServer.readLine());
                    System.out.println("Protocol: " + protocol);
                    switch (protocol) {
                        case GETACCOUNT:
                            account = Account.parseAccount(inFromServer.readLine());
                            break;
                        case GETSONG:
                            song = Song.parseSong(inFromServer.readLine());
                            break;
                        case GETSONGS:
                            readSongs();
                            break;
                        case GETSONGSBYARTIST:
                            readSongs();
                            break;
                        case GETFOLLOWEDSONGS:
                            readSongs();
                            break;
                        case GETSONGSINALBUM:
                            readSongs();
                            break;
                        case GETSONGSINPLAYLIST:
                            readSongs();
                            break;
                        case GETFAVORITESONGS:
                            readSongs();
                            break;
                        case ADDSONG:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) {
                                System.out.println("Adding song successful.");
                                song.setSongId(Integer.parseInt(inFromServer.readLine()));
                                System.out.println(song + " was added successfully.");
                            }
                            break;
                        case ISFOLLOWINGSONG:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case ISFAVORITESONG:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case GETPLAYLIST:
                            playlist = Playlist.parsePlaylist(inFromServer.readLine());
                            break;
                        case GETPLAYLISTS:
                            readPlaylists();
                            break;
                        case GETPLAYLISTSBYACCOUNT:
                            readPlaylists();
                            break;
                        case GETFOLLOWEDPLAYLISTS:
                            readPlaylists();
                            break;
                        case GETFAVORITEPLAYLISTS:
                            readPlaylists();
                            break;
                        case ADDPLAYLIST:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) playlist.setPlaylistId(Integer.parseInt(inFromServer.readLine()));
                            break;
                        case ISFOLLOWINGPLAYLIST:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case ISFAVORITEPLAYLIST:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case GETALBUM:
                            album = Album.parseAlbum(inFromServer.readLine());
                            break;
                        case GETALBUMS:
                            readAlbums();
                            break;
                        case GETALBUMSBYARTIST:
                            readAlbums();
                            break;
                        case GETFOLLOWEDALBUMS:
                            readAlbums();
                            break;
                        case ADDALBUM:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) album.setAlbumId(Integer.parseInt(inFromServer.readLine()));
                            break;
                        case DELETEALBUM:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case ISFOLLOWINGALBUM:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case GETUSER:
                            user = User.parseUser(inFromServer.readLine());
                            break;
                        case GETUSERS:
                            readUsers();
                            break;
                        case GETFOLLOWEDUSERS:
                            readUsers();
                            break;
                        case ADDUSER:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) {
                                user.setUserId(Integer.parseInt(inFromServer.readLine()));
                                user.getAccount().setId(Integer.parseInt(inFromServer.readLine()));
                            }
                            break;
                        case ISFOLLOWINGUSER:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case GETARTIST:
                            artist = Artist.parseArtist(inFromServer.readLine());
                            break;
                        case GETARTISTS:
                            readArtists();
                            break;
                        case GETFOLLOWEDARTISTS:
                            readArtists();
                            break;
                        case ADDARTIST:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) {
                                artist.setArtistId(Integer.parseInt(inFromServer.readLine()));
                                artist.getAccount().setId(Integer.parseInt(inFromServer.readLine()));
                            }
                            break;
                        case ISFOLLOWINGARTIST:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case GETIMAGEFILE:
                            int fileSize = Integer.parseInt(inFromServer.readLine());
                            if (inFromServer.readLine().equals("OK"))
                                new FTPClient(Server.IP_ADDRESS, img, fileSize).receive();
//                            FileUtil.downloadFile(socket, inFromServer, img);
                            break;
                        case SETIMAGEFILE:
                            break;
                        case GETSONGFILE:
//                            FileUtil.downloadFile(socket, inFromServer, wav);
                            fileSize = Integer.parseInt(inFromServer.readLine());
                            System.out.println("Going to download a " + fileSize + " bytes file.");
                            String response = inFromServer.readLine();
                            System.out.println(response);
                            if (response.equals("OK")) {
                                System.out.println("Going to start the download.");
                                new FTPClient(Server.IP_ADDRESS, wav, fileSize).receive();
                            }
                            break;
                        case SETSONGFILE:
                            break;
                        case LOGIN:
                            success = Protocol.valueOf(inFromServer.readLine()) == Protocol.OK;
                            if (success) {
                                String userInfo = inFromServer.readLine();
                                if (userInfo.split("\\|").length > 6) user = Artist.parseArtist(userInfo);
                                else user = User.parseUser(userInfo);
                                user.setAccount(Account.parseAccount(inFromServer.readLine()));
                                break;
                            }
                            break;
                        case ISARTIST:
                            success = Boolean.parseBoolean(inFromServer.readLine());
                            break;
                        case SEARCHSONGS:
                            readSongs();
                            break;
                        case SEARCHPLAYLISTS:
                            readPlaylists();
                            break;
                        case SEARCHALBUMS:
                            readAlbums();
                            break;
                        case SEARCHUSERS:
                            readUsers();
                            break;
                        case SEARCHARTISTS:
                            readArtists();
                            break;
                        case UPLOADEVENT:
                            mc.pushNotification(inFromServer.readLine());
                            break;
                        case PLAYEVENT:
                            mc.pushNotification(inFromServer.readLine());
                            break;
                    }
                    isBusy = false;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

    public Account getAccount(int accountId) {
        isBusy = true;
        account = null;
        outToServer.println(Protocol.GETACCOUNT);
        outToServer.println(accountId);
        while (isBusy());
        return account;
    }

    public Song getSong(int songId) {
        isBusy = true;
        song = null;
        outToServer.println(Protocol.GETSONG);
        outToServer.println(songId);
        while (isBusy());
        song.setAlbum(getAlbum(song.getAlbum().getAlbumId()));
        System.out.println(song.getAlbum());
        song.setArtist(getArtist(song.getArtist().getArtistId()));
        System.out.println(song.getArtist());
        return song;
    }

    public ArrayList<Song> getSongs(){
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGS);
        while (isBusy());
        populateSongs();
        return songs;
    }

    public ArrayList<Song> getSongsByArtist(int artistId) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSBYARTIST);
        outToServer.println(artistId);
        while (isBusy());
        populateSongs();
        return songs;
    }

    public ArrayList<Song> getFollowedSongs(int accountId) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDSONGS);
        outToServer.println(accountId);
        while (isBusy());
        System.out.println("Loop has ended.");
        populateSongs();
        return songs;
    }

    public ArrayList<Song> getSongsInAlbum(int albumId) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSINALBUM);
        outToServer.println(albumId);
        while (isBusy());
        populateSongs();
        return songs;
    }

    public ArrayList<Song> getSongsInPlaylist(int playlistId) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGSINPLAYLIST);
        outToServer.println(playlistId);
        while (isBusy());
        populateSongs();
        return songs;
    }

    public ArrayList<Song> getFavoriteSongs(int accountId) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.GETFAVORITESONGS);
        outToServer.println(accountId);
        while (isBusy());
        populateSongs();
        return songs;
    }

    private void readSongs() {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                Song song = Song.parseSong(inFromServer.readLine());
                songs.add(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateSongs() {
        for (int i = 0; i < songs.size(); i++) {
            Song song = songs.get(i);
            song.setAlbum(getAlbum(song.getAlbum().getAlbumId()));
            song.setArtist(getArtist(song.getArtist().getArtistId()));
            System.out.println(song.getAlbum());
            System.out.println(song.getArtist());
        }
    }

    public boolean addSong(Song song){
        this.song = song;
        isBusy = true;
        outToServer.println(Protocol.ADDSONG);
        outToServer.println(song);
        System.out.println(song);
        while (isBusy());
        System.out.println("Song was added successfully.");
        return success;
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
        isBusy = true;
        outToServer.println(Protocol.PLAYSONG);
        outToServer.println(accountId);
        outToServer.println(songId);
        while (isBusy());
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

    public boolean isFollowingSong(int accountId, int songId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFOLLOWINGSONG);
        outToServer.println(accountId);
        outToServer.println(songId);
        while (isBusy());
        return success;
    }

    public void toggleFavoriteSong(int accountId, int songId) {
        outToServer.println(Protocol.TOGGLEFAVORITESONG);
        outToServer.println(accountId);
        outToServer.println(songId);
    }

    public boolean isFavoriteSong(int accountId, int songId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFAVORITESONG);
        outToServer.println(accountId);
        outToServer.println(songId);
        while (isBusy());
        return success;
    }

    public Playlist getPlaylist(int playlistId) {
        isBusy = true;
        playlist = null;
        outToServer.println(Protocol.GETPLAYLIST);
        outToServer.println(playlistId);
        while (isBusy());
        playlist.setAccount(getAccount(playlist.getAccount().getId()));
        return playlist;
    }

    public ArrayList<Playlist> getPlaylists() {
        isBusy = true;
        playlists = new ArrayList<>();
        outToServer.println(Protocol.GETPLAYLISTS);
        while (isBusy());
        populatePlaylists();
        return playlists;
    }

    public ArrayList<Playlist> getPlaylistsByAccount(int accountId) {
        isBusy = true;
        playlists = new ArrayList<>();
        outToServer.println(Protocol.GETPLAYLISTSBYACCOUNT);
        outToServer.println(accountId);
        while (isBusy());
        populatePlaylists();
        return playlists;
    }

    public ArrayList<Playlist> getFollowedPlaylists(int accountId) {
        isBusy = true;
        playlists = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDPLAYLISTS);
        outToServer.println(accountId);
        while (isBusy());
        populatePlaylists();
        return playlists;
    }

    public ArrayList<Playlist> getFavoritePlaylists(int accountId) {
        isBusy = true;
        playlists = new ArrayList<>();
        outToServer.println(Protocol.GETFAVORITEPLAYLISTS);
        outToServer.println(accountId);
        while (isBusy());
        populatePlaylists();
        return playlists;
    }

    private void readPlaylists() {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                Playlist playlist = Playlist.parsePlaylist(inFromServer.readLine());
                playlists.add(playlist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populatePlaylists() {
        for (int i = 0; i < playlists.size(); i++) {
            Playlist playlist = playlists.get(i);
            playlist.setAccount(getAccount(playlist.getAccount().getId()));
        }
    }

    public boolean addPlaylist(Playlist playlist){
        this.playlist = playlist;
        isBusy = true;
        outToServer.println(Protocol.ADDPLAYLIST);
        outToServer.println(playlist);
        while (isBusy());
        return success;
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

    public boolean isFollowingPlaylist(int accountId, int playlistId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFOLLOWINGPLAYLIST);
        outToServer.println(accountId);
        outToServer.println(playlistId);
        while (isBusy());
        return success;
    }

    public void toggleFavoritePlaylist(int accountId, int playlistId) {
        outToServer.println(Protocol.TOGGLEFAVORITEPLAYLIST);
        outToServer.println(accountId);
        outToServer.println(playlistId);
    }

    public boolean isFavoritePlaylist(int accountId, int playlistId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFAVORITEPLAYLIST);
        outToServer.println(accountId);
        outToServer.println(playlistId);
        while (isBusy());
        return success;
    }

    public Album getAlbum(int albumId) {
        isBusy = true;
        album = new Album();
        if (albumId == -1) return album;
        outToServer.println(Protocol.GETALBUM);
        outToServer.println(albumId);
        System.out.println("Entered the loop.");
        while (isBusy());
        System.out.println("Album successfully retrieved.");
        album.setArtist(getArtist(album.getArtist().getArtistId()));
        return album;
    }

    public ArrayList<Album> getAlbums(){
        albums = new ArrayList<>();
        outToServer.println(Protocol.GETALBUMS);
        isBusy = true;
        while (isBusy());
        populateAlbums();
        return albums;
    }

    public ArrayList<Album> getFollowedAlbums(int accountId){
        isBusy = true;
        albums = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDALBUMS);
        outToServer.println(accountId);
        while (isBusy());
        populateAlbums();
        return albums;
    }

    public ArrayList<Album> getAlbumsByArtist(int artistId) {
        isBusy = true;
        albums = new ArrayList<>();
        outToServer.println(Protocol.GETALBUMSBYARTIST);
        outToServer.println(artistId);
        while (isBusy());
        populateAlbums();
        return albums;
    }

    private void readAlbums() {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                album = Album.parseAlbum(inFromServer.readLine());
                albums.add(album);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void populateAlbums() {
        for (int i = 0; i < albums.size(); i++) {
            album = albums.get(i);
            album.setArtist(getArtist(album.getArtist().getArtistId()));
        }
    }
    public boolean addAlbum(Album album){
        this.album = album;
        isBusy = true;
        outToServer.println(Protocol.ADDALBUM);
        outToServer.println(album);
        while (isBusy());
        return success;
    }

    public boolean deleteAlbum(Album album){
        isBusy = true;
        success = false;
        outToServer.println(Protocol.DELETEALBUM);
        outToServer.println(album);
        while (isBusy());
        return success;
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

    public boolean isFollowingAlbum(int accountId, int albumId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFOLLOWINGALBUM);
        outToServer.println(accountId);
        outToServer.println(albumId);
        while (isBusy());
        return success;
    }

    public User getUser(int userId) {
        isBusy = true;
        user = null;
        outToServer.println(Protocol.GETUSER);
        outToServer.println(userId);
        while (isBusy());
        user.setAccount(getAccount(user.getAccount().getId()));
        return user;
    }

    public ArrayList<User> getUsers(){
        isBusy = true;
        users = new ArrayList<>();
        outToServer.println(Protocol.GETUSERS);
        while (isBusy());
        populateUsers();
        return users;
    }

    public ArrayList<User> getFollowedUsers(int accountId) {
        isBusy = true;
        users = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDUSERS);
        outToServer.println(accountId);
        while (isBusy());
        populateUsers();
        return users;
    }

    private void readUsers() {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                users.add(User.parseUser(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateUsers() {
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            user.setAccount(getAccount(user.getAccount().getId()));
        }
    }

    public boolean addUser(User user){
        this.user = user;
        isBusy = true;
        outToServer.println(Protocol.ADDUSER);
        outToServer.println(user);
        outToServer.println(user.getAccount());
        while (isBusy());
        return success;
    }

    public void updateUser(User user){
        outToServer.println(Protocol.UPDATEUSER);
        outToServer.println(user);
        outToServer.println(user.getAccount());
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

    public boolean isFollowingUser(int accountId, int anotherAccountId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFOLLOWINGUSER);
        outToServer.println(accountId);
        outToServer.println(anotherAccountId);
        while (isBusy());
        return success;
    }

    public Artist getArtist(int artistId) {
        isBusy = true;
        artist = null;
        outToServer.println(Protocol.GETARTIST);
        outToServer.println(artistId);
        while (isBusy());
        artist.setAccount(getAccount(artist.getAccount().getId()));
        return artist;
    }

    public ArrayList<Artist> getArtists(){
        isBusy = true;
        artists = new ArrayList<>();
        outToServer.println(Protocol.GETARTISTS);
        while (isBusy());
        populateArtists();
        return artists;
    }

    public ArrayList<Artist> getFollowedArtists(int accountId) {
        isBusy = true;
        artists = new ArrayList<>();
        outToServer.println(Protocol.GETFOLLOWEDARTISTS);
        outToServer.println(accountId);
        while (isBusy());
        populateArtists();
        return artists;
    }

    private void readArtists() {
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                artists.add(Artist.parseArtist(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateArtists() {
        for (int i = 0; i < artists.size(); i++) {
            artist = artists.get(i);
            artist.setAccount(getAccount(artist.getAccount().getId()));
        }
    }

    public boolean addArtist(Artist artist){
        this.artist = artist;
        isBusy = true;
        outToServer.println(Protocol.ADDARTIST);
        outToServer.println(artist);
        System.out.println(artist);
        outToServer.println(artist.getAccount());
        System.out.println(artist.getAccount());
        while (isBusy());
        return success;
    }

    public void updateArtist(Artist artist){
        outToServer.println(Protocol.UPDATEARTIST);
        outToServer.println(artist);
        outToServer.println(artist.getAccount());
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

    public boolean isFollowingArtist(int accountId, int anotherAccountId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISFOLLOWINGARTIST);
        outToServer.println(accountId);
        outToServer.println(anotherAccountId);
        while (isBusy());
        return success;
    }

    public File getImageFile(int albumId){
        System.out.println(albumId);
        if (albumId == -1) return null;
        isBusy = true;
        File dir = new File("resources/images");
        dir.mkdirs();
        img = new File(dir, albumId + ".png");
        try {
            img.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outToServer.println(Protocol.GETIMAGEFILE);
        outToServer.println(albumId);
        while (isBusy());
        return img;
    }

    public void setImageFile(int albumId, File img) {
        if (img != null) {
            executor.submit(new FTPServer(img));
            outToServer.println(Protocol.SETIMAGEFILE);
            outToServer.println(albumId);
            outToServer.println(img.length());
        }
    }

    public File getSongFile(int songId){
        isBusy = true;
        File dir = new File("resources/songs");
        dir.mkdirs();
        wav = new File(dir, songId + ".wav");
        try {
            wav.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outToServer.println(Protocol.GETSONGFILE);
        outToServer.println(songId);
        System.out.println("Entering the loop...");
        while (isBusy());
        return wav;
    }

    public void setSongFile(int songId, File wav){
        executor.submit(new FTPServer(wav));
        outToServer.println(Protocol.SETSONGFILE);
        outToServer.println(songId);
        outToServer.println(wav.length());
//        FileUtil.uploadFile(socket, outToServer, wav);
    }

    public User logIn(String username, String password) {
        isBusy = true;
        user = null;
        outToServer.println(Protocol.LOGIN);
        outToServer.println(username);
        outToServer.println(password);
        while (isBusy());
        return user;
    }

    public void logOut(int userId) {
        outToServer.println(Protocol.LOGOUT);
        outToServer.println(userId);
    }

    public ArrayList<Song> searchSongs(String keyword) {
        isBusy = true;
        songs = new ArrayList<>();
        outToServer.println(Protocol.SEARCHSONGS);
        outToServer.println(keyword);
        while (isBusy());
        populateSongs();
        return songs;
    }

    public ArrayList<Playlist> searchPlaylists(String keyword) {
        isBusy = true;
        playlists = new ArrayList<>();
        outToServer.println(Protocol.SEARCHPLAYLISTS);
        outToServer.println(keyword);
        while (isBusy());
        populatePlaylists();
        return playlists;
    }

    public ArrayList<Album> searchAlbums(String keyword) {
        isBusy = true;
        albums = new ArrayList<>();
        outToServer.println(Protocol.SEARCHALBUMS);
        outToServer.println(keyword);
        while (isBusy());
        populateAlbums();
        return albums;
    }

    public ArrayList<User> searchUsers(String keyword) {
        isBusy = true;
        users = new ArrayList<>();
        outToServer.println(Protocol.SEARCHUSERS);
        outToServer.println(keyword);
        while (isBusy());
        populateUsers();
        return users;
    }

    public ArrayList<Artist> searchArtists(String keyword) {
        isBusy = true;
        artists = new ArrayList<>();
        outToServer.println(Protocol.SEARCHARTISTS);
        outToServer.println(keyword);
        while (isBusy());
        populateArtists();
        return artists;
    }

    public boolean isArtist(int accountId) {
        isBusy = true;
        success = false;
        outToServer.println(Protocol.ISARTIST);
        outToServer.println(accountId);
        while (isBusy());
        return success;
    }

    public static void main(String[] args) {
        Client c = new Client();
        c.startConnection();
        c.getSongFile(22);
    }
}
