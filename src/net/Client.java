package net;

import util.Protocol;
import model.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.*;


public class Client {
    private static final Client singleton = new Client();
    // Network variables to communicate with the server
    private Socket socket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private Client() { }

    public static Client getInstance() { return singleton; }

    public ArrayList<Song> getSongs(){
        ArrayList<Song> songs = new ArrayList<>();
        outToServer.println(Protocol.GETSONGS);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                songs.add(Song.parseSong(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
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

    }

    public void editSong(Song song){

    }

    public void playSong(String name){

    }

    public void followSong(String name){

    }

    public ArrayList<Playlist> getPlaylists() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        outToServer.println(Protocol.GETPLAYLISTS);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                playlists.add(Playlist.parsePlaylist(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Album> getAlbums(){
        ArrayList<Album> albums = new ArrayList<>();
        outToServer.println(Protocol.GETALBUMS);
        try {
            int n = Integer.parseInt(inFromServer.readLine());
            for (int i = 0; i < n; i++) {
                albums.add(Album.parseAlbum(inFromServer.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return albums;

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

    public boolean addUser(User user){
        outToServer.println(Protocol.ADDUSER);
        outToServer.println(user);
        try {
            Protocol protocol = Protocol.valueOf(inFromServer.readLine());
            switch (protocol) {
                case NO:
                    return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void editUser(String name){

    }

    public void followUser(String name){

    }

    public ArrayList<Artist> getArtists(String name){
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

    public User logIn(String username, String password) {
        User user = null;
        outToServer.println(Protocol.LOGIN);
        outToServer.println(username);
        outToServer.println(password);
        try {
            Protocol protocol = Protocol.valueOf(inFromServer.readLine());
            switch (protocol) {
                case OK:
                    user = User.parseUser(inFromServer.readLine());
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
}
