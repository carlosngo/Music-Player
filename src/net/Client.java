package net;

import util.FileUtil;
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

    public void startConnection() throws IOException {
        socket = new Socket(Server.IP_ADDRESS, Server.PORT_NUMBER);
        outToServer = new PrintWriter(socket.getOutputStream(), true);
        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void closeConnection() throws IOException {
        try {
            outToServer.close();
            inFromServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        outToServer.println(Protocol.ADDSONG);
        outToServer.println(song);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void deleteSong(Song song){
        outToServer.println(Protocol.DELETESONG);
        outToServer.println(song);
    }

    public void updateSong(Song song){
        outToServer.println(Protocol.UPDATESONG);
        outToServer.println(song);
    }

    public void playSong(int songId){
        outToServer.println(Protocol.PLAYSONG);
        outToServer.println(songId);
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

    public boolean addPlaylist(Playlist playlist){
        outToServer.println(Protocol.ADDPLAYLIST);
        outToServer.println(playlist);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
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

    public void playPlaylist(Playlist playlist){

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

    public boolean addAlbum(Album album){
        outToServer.println(Protocol.ADDALBUM);
        outToServer.println(album);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
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
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateUser(User user){
        outToServer.println(Protocol.UPDATEUSER);
        outToServer.println(user);
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

    public boolean addArtist(Artist artist){
        outToServer.println(Protocol.ADDARTIST);
        outToServer.println(artist);
        try {
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void updateArtist(Artist artist){
        outToServer.println(Protocol.UPDATEARTIST);
        outToServer.println(artist);
    }

    public void followArtist(Artist artist){

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
        outToServer.println(Protocol.SETIMAGEFILE);
        outToServer.println(albumId);
        FileUtil.uploadFile(socket, inFromServer, outToServer, img);
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



    public static void main(String[] args) {
        try {
            Client c = new Client();
            c.startConnection();
            c.getSongFile(22);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }
}
