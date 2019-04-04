package net;

import events.*;
import model.*;
import util.Protocol;

import java.text.ParseException;
import java.util.*;
import java.io.*;
import java.net.*;

public class ClientThread implements Runnable, UploadListener, PlayListener {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {System.out.println("Started thread for client.");
        Server server = Server.getInstance();
        String messageFromClient;
        try {
            while (!server.isShutDown() && (messageFromClient = in.readLine()) != null) {
                System.out.println("Received the message: " + messageFromClient + " from client.");
                Song song;
                Playlist playlist;
                Album album;
                User user;
                Artist artist;
                StringBuilder sb;
                Protocol protocol = Protocol.valueOf(messageFromClient);
                StringBuilder reply = new StringBuilder();
                switch (protocol) {
                    case GETSONGS:
                        ArrayList<Song> songs = server.getSongs();
                        reply.append(songs.size());
                        reply.append("\n");
                        for (int i = 0; i < songs.size(); i++) {
                            if (i > 0) reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case ADDSONG:
                        song = Song.parseSong(in.readLine());
                        if (server.addSong(song)) reply.append("OK");
                        else reply.append("NO");
                        break;
                    case DELETESONG:
                        song = Song.parseSong(in.readLine());
                        server.deleteSong(song);
                        break;
                    case EDITSONG:
                        song = Song.parseSong(in.readLine());
                        server.editSong(song);
                        break;
                    case PLAYSONG:
                        server.playSong(messageFromClient.substring(8));
                        break;
                    case FOLLOWSONG:
                        server.followSong(messageFromClient.substring(10));
                        break;
                    case GETPLAYLISTS:
                        ArrayList<Playlist> playlists = server.getPlaylists();
                        reply.append(playlists.size());
                        reply.append("\n");
                        for (int i = 0; i < playlists.size(); i++) {
                            if (i > 0) reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case ADDPLAYLIST:
                        playlist = Playlist.parsePlaylist(in.readLine());
                        if (server.addPlaylist(playlist)) reply.append("OK");
                        else reply.append("NO");
                        break;
                    case DELETEPLAYLIST:
                        server.deletePlaylist(messageFromClient.substring(14));
                        break;
                    case EDITPLAYLIST:
                        server.editPlaylist(messageFromClient.substring(12));
                        break;
                    case PLAYPLAYLIST:
                        server.playPlaylist(messageFromClient.substring(12));
                        break;
                    case FOLLOWPLAYLIST:
                        server.followPlaylist(messageFromClient.substring(14));
                        break;
                    case GETALBUMS:
                        ArrayList<Album> albums = server.getAlbums();
                        reply.append(albums.size());
                        reply.append("\n");
                        for (int i = 0; i < albums.size(); i++) {
                            if (i > 0) reply.append("\n");
                            reply.append(albums.get(i).toString());
                        }
                        break;
                    case ADDALBUM:
                        album = Album.parseAlbum(in.readLine());
                        if (server.addAlbum(album)) reply.append("OK");
                        else reply.append("NO");
                        break;
                    case DELETEALBUM:
                        server.deleteAlbum(messageFromClient.substring(11));
                        break;
                    case EDITALBUM:
                        server.editAlbum(messageFromClient.substring(9));
                        break;
                    case PLAYALBUM:
                        server.playAlbum(messageFromClient.substring(9));
                        break;
                    case FOLLOWALBUM:
                        server.followAlbum(messageFromClient.substring(11));
                        break;
                    case GETUSERS:
                        ArrayList<User> users = server.getUsers();
                        reply.append(users.size());
                        reply.append("\n");
                        for (int i = 0; i < users.size(); i++) {
                            if (i > 0) reply.append("\n");
                            reply.append(users.get(i).toString());
                        }
                        break;
                    case ADDUSER:
                        user = User.parseUser(in.readLine());
                        if (server.addUser(user)) reply.append(Protocol.OK);
                        else reply.append(Protocol.NO);
                        break;
                    case EDITUSER:
                        server.editUser(messageFromClient.substring(8));
                        break;
                    case FOLLOWUSER:
                        server.followUser(messageFromClient.substring(10));
                        break;
                    case GETARTISTS:
                        ArrayList<Artist> artists = server.getArtists();
                        reply.append(artists.size());
                        reply.append("\n");
                        for (int i = 0; i < artists.size(); i++) {
                            if (i > 0) reply.append("\n");
                            reply.append(artists.get(i).toString());
                        }                       
                        break;
                    case ADDARTIST:
                        artist = Artist.parseArtist(in.readLine());
                        if (server.addArtist(artist)) reply.append(Protocol.OK);
                        else reply.append(Protocol.NO);
                        break;
                    case EDITARTIST:
                        server.editArtist(messageFromClient.substring(10));

                        break;
                    case FOLLOWARTIST:
                        server.followArtist(messageFromClient.substring(12));
                        break;
                    case GETIMAGEFILE:
                        server.getImageFile();
                        break;
                    case SETIMAGEFILE:
                        server.setImageFile();
                        break;
                    case GETSONGFILE:
                        server.getSongFile();

                        break;
                    case SETSONGFILE:
                        server.setSongFile();

                        break;
                    case LOGIN:
                        String username = in.readLine();
                        String password = in.readLine();
                        user = server.login(username, password, this);
                        if (user == null) reply.append(Protocol.NO);
                        else {
                            reply.append(Protocol.OK);
                            reply.append("\n");
                            reply.append(user.toString());
                        }
                        break;
                    case LOGOUT:
                        int userId = Integer.parseInt(in.readLine());
                        server.logout(userId);
                        break;
                    case SEARCH:
                        break;
                }
                if (reply.length() > 0) {
                    System.out.println("Sending the following message to the client: " + reply.toString());
                    out.println(reply.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void listen(PlayEvent e) {

    }

    @Override
    public void listen(UploadEvent e) {

    }
}
