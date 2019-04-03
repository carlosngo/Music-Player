package server;

import dao.*;

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

    public void deleteSong(){

    }

    public void editSong(){

    }

    public void playSong(){

    }

    public void followSong(){

    }

    public void getPlaylists(){

    }

    public void addPlaylist(){

    }

    public void deletePlaylist(){

    }

    public void editPlaylist(){

    }

    public void playPlaylist(){

    }

    public void followPlaylist(){

    }

    public void getAlbums(){

    }

    public void addAlbum(){

    }

    public void deleteAlbum(){

    }

    public void editAlbum(){

    }

    public void playAlbum(){

    }

    public void followAlbum(){

    }

    public void getUsers(){

    }

    public void addUser(){

    }

    public void editUser(){

    }

    public void followUser(){

    }

    public void getArtists(){

    }

    public void addArtist(){

    }

    public void editArtist(){

    }

    public void followArtist(){

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
