package net;

import dao.*;
import model.*;

import java.util.concurrent.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
    public static final int PORT_NUMBER = 5555;
    public static final String IP_ADDRESS = "127.0.0.1";

    private static final Server singleton = new Server();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<Integer, ClientThread> onlineUsers = Collections.synchronizedMap(new HashMap<>());
    private final UserDAOFactory userDAOFactory = new UserDAOFactory();
    private final SongDAOFactory songDAOFactory = new SongDAOFactory();
    private final AlbumDAOFactory albumDAOFactory = new AlbumDAOFactory();
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

    public void loadData() {

    }

    public Song getSong(int songId) {
        return ((SongDAO)songDAOFactory.getDAO()).find(songId);
    }

    public ArrayList<Song> getSongs(){
        return ((SongDAO)songDAOFactory.getDAO()).listById();
    }

    public ArrayList<Song> getSongsByArtist(int artistId) {
        return new ArrayList<>();
    }

    public ArrayList<Song> getSongsInAlbum(int albumId) {
        return new ArrayList<>();
    }

    public ArrayList<Song> getSongsInPlaylist(int playlistId) {
        return new ArrayList<>();

    }

    public boolean addSong(Song song){
        try {
            ((SongDAO)songDAOFactory.getDAO()).create(song);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        return true;
    }

    public void deleteSong(Song song){
        try {
            //remove song
        } catch (IllegalArgumentException e) {
            System.out.println("Quiz was not deleted.");
        }
    }

    public void editSong(Song song){

    }

    public void incrementPlayCount(int songId){

    }

    public void followSong(String name){

    }

    public ArrayList<Playlist> getPlaylists() {
        return ((PlaylistDAO)playlistDAOFactory.getDAO()).listById();
    }

    public boolean addPlaylist(Playlist playlist){
        try {
            ((PlaylistDAO)playlistDAOFactory.getDAO()).create(playlist);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        return true;
    }

    public void deletePlaylist(String name){

    }

    public void editPlaylist(String name){

    }

    public void playPlaylist(String name){

    }

    public void followPlaylist(String name){

    }

    public Album getAlbum(int albumId) {
        return ((AlbumDAO)albumDAOFactory.getDAO()).find(albumId);
    }

    public ArrayList<Album> getAlbums(){
        return ((AlbumDAO)albumDAOFactory.getDAO()).listById();
    }

    public boolean addAlbum(Album album){
        try {
            ((AlbumDAO)albumDAOFactory.getDAO()).create(album);
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
            return false;
        }
        return true;
    }

    public void deleteAlbum(String name){

    }

    public void editAlbum(String name){

    }

    public void playAlbum(String name){

    }

    public void followAlbum(String name){

    }

    public ArrayList<User> getUsers(){
        return ((UserDAO)userDAOFactory.getDAO()).listById();
    }

    public boolean addUser(User user){
        try {
            UserDAO userDAO = ((UserDAO)userDAOFactory.getDAO());
            if (userDAO.existUserName(user.getUserName())) return false;
            userDAO.create(user);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void editUser(String name){

    }

    public void followUser(String name){

    }

    public ArrayList<Artist> getArtists(){
        return ((ArtistDAO)artistDAOFactory.getDAO()).listById();
    }

    public boolean addArtist(Artist artist){
        try {
            ArtistDAO artistDAO = ((ArtistDAO)artistDAOFactory.getDAO());
            if (artistDAO.existUsername(artist.getUserName())) return false;
            artistDAO.create(artist);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void editArtist(String name){

    }

    public void followArtist(String name){

    }

    public void getImageFile(){

    }

    public void setImageFile(int albumId, File img){

    }

    public void getSongFile(){

    }


    public void setSongFile(int songId, File wav){

    }


    public User login(String username, String password, ClientThread thread){
        User user = ((UserDAO)(userDAOFactory.getDAO())).find(username, password);
        if (user != null) onlineUsers.put(user.getUserId(), thread);
        return user;
    }

    public void logout(int userId){
        onlineUsers.remove(userId);
    }

    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.loadData();
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
