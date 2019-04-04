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

    public boolean addSong(Song song){
        try {
            //add song
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
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

    public void playSong(String name){

    }

    public void followSong(String name){

    }

    public ArrayList<Playlist> getPlaylists(){
        ArrayList<Playlist> playlists = new ArrayList<Playlist>();
        return playlists;
    }

    public void addPlaylist(Playlist playlist){

    }

    public void deletePlaylist(String name){

    }

    public void editPlaylist(String name){

    }

    public void playPlaylist(String name){

    }

    public void followPlaylist(String name){

    }

    public void getAlbums(){

    }

    public void addAlbum(Album album){

    }

    public void deleteAlbum(String name){

    }

    public void editAlbum(String name){

    }

    public void playAlbum(String name){

    }

    public void followAlbum(String name){

    }

    public void getUsers(){

    }

    public void addUser(User user){

    }

    public void editUser(String name){

    }

    public void followUser(String name){

    }

    public ArrayList<Artist> getArtists(User user){
        ArrayList<Artist> artists = new ArrayList<Artist>();
        return artists;
    }

    public void addArtist(Artist artist){

    }

    public void editArtist(String name){

    }

    public void followArtist(String name){

    }

    public void getImageFile(){

    }

    public void setImageFile(){

    }

    public void getSongFile(){

    }


    public void setSongFile(){

    }


    public User login(String username, String password, ClientThread thread){
        User user = ((UserDAO)(userDAOFactory.getDAO())).find(username, password);
        if (user != null) onlineUsers.put(user.getUserId(), thread);
        return user;
    }

    public void logout(int userId){
        onlineUsers.remove(userId);
    }

    public ArrayList<Song> getSongs(){
        ArrayList<Song> songs = new ArrayList<Song>();
        return songs;
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
