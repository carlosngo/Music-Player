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

    }

    public void editSong(Song song){

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
            if (Protocol.valueOf(inFromServer.readLine()) == Protocol.NO) return false;
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

    public void editArtist(String name){

    }

    public void followArtist(String name){

    }

    public void getImageFile(){

    }

    public void setImageFile(){

    }

    public void getSongFile(int songId){
        outToServer.println(Protocol.GETSONGFILE);
        outToServer.println(songId);
        try {

            int fileSize = Integer.parseInt(inFromServer.readLine());

            byte[] mybytearray = new byte[fileSize + 1000];
            InputStream is = socket.getInputStream();
            File dir = new File("resources/songs");
            dir.mkdirs();
            File wav = new File(dir, songId + "");
            wav.createNewFile();
            FileOutputStream fos = new FileOutputStream(wav);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            System.out.println("Getting a " + fileSize + " bytes file from the server.");
            int bytesRead = is.read(mybytearray, 0, mybytearray.length);
            int current = bytesRead;

            do {
                System.out.println(current);
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length - current));
                if (bytesRead >= 0) current += bytesRead;
            } while (bytesRead > -1 && current < fileSize);

            bos.write(mybytearray, 0, current);
            bos.flush();
            System.out.println("File " + wav
                    + " downloaded (" + current + " bytes read)");
        } catch (IOException e) {
            e.printStackTrace();
            outToServer.println(Protocol.NO);
        }
        outToServer.println(Protocol.OK);
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
