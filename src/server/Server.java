package server;

import dao.*;
import model.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.locks.*;
import java.net.*;
import java.io.*;

public class Server {
    public static final int PORT_NUMBER = 5555;
    public static final String IP_ADDRESS = "127.0.0.1";

    private static final Server singleton = new Server();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Observable observable = new Observable();
    private final Lock lock = new ReentrantLock(true);
    private boolean shutdown = false;

    private Server() { }

    public static Server getInstance() { return singleton; }

    public void shutDown() { shutdown = true; }

    public boolean isShutDown() { return shutdown; }

    public void startThread(Runnable thread) {
        executor.submit(thread);
    }

    public void registerClientThread(ClientThread thread) {
        lock.lock();
        try {
            observable.addObserver(thread);
        } finally {
            lock.unlock();
        }
    }

    public void unregisterClientThread(ClientThread thread) {
        lock.lock();
        try {
            observable.deleteObserver(thread);
        } finally {
            lock.unlock();
        }
    }

    public void broadcast(Object message) {
        lock.lock();
        try {
            executor.execute(new MessageBroadcaster(message, observable));
        } finally {
            lock.unlock();
        }
    }
    public void loadData() {

    }

    public void addSong(Song song){
        try {
            //add song
        } catch (IllegalArgumentException e) {
            System.out.println("Song was not added.");
        }
    }

    public void deleteSong(String name){

    }

    public void editSong(String name){

    }

    public void playSong(String name){

    }

    public void followSong(String name){

    }

    public void getPlaylists(){

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

    public void getArtists(String name){

    }

    public void addArtist(String name){

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


    public void login(){

    }

    public void logout(){

    }

    public ArrayList<Song> getSongs(){
        ArrayList<Song> dummyList = new ArrayList<Song>();
        return dummyList;
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
                server.registerClientThread(thread);
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
