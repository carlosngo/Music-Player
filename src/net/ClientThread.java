package net;

import events.*;
import model.*;
import util.FileUtil;
import util.Protocol;

import java.util.*;
import java.io.*;
import java.net.*;

public class ClientThread implements Runnable, UploadListener, PlayListener {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Server server = Server.getInstance();

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
    public void run() {
        System.out.println("Started thread for client.");
        String messageFromClient;
        try {
            while (!server.isShutDown() && (messageFromClient = in.readLine()) != null) {
                System.out.println("Received the message: " + messageFromClient + " from client.");
                Song song;
                Playlist playlist;
                Album album;
                User user;
                Artist artist;
                Account account;
                Protocol protocol = Protocol.valueOf(messageFromClient);
                out.println(protocol);
                StringBuilder reply = new StringBuilder();
                switch (protocol) {
                    case GETACCOUNT:
                        reply.append(server.getAccount(Integer.parseInt(in.readLine())));
                        break;
                    case GETSONG:
                        reply.append(server.getSong(Integer.parseInt(in.readLine())));
                        break;
                    case GETSONGS:
                        ArrayList<Song> songs = server.getSongs();
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case GETSONGSBYARTIST:
                        int artistId = Integer.parseInt(in.readLine());
                        songs = server.getSongsByArtist(artistId);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case GETFOLLOWEDSONGS:
                        int accountId = Integer.parseInt(in.readLine());
                        songs = server.getFollowedSongs(accountId);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case GETSONGSINALBUM:
                        int albumId = Integer.parseInt(in.readLine());
                        songs = server.getSongsInAlbum(albumId);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case GETSONGSINPLAYLIST:
                        int playlistId = Integer.parseInt(in.readLine());
                        songs = server.getSongsInPlaylist(playlistId);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case GETFAVORITESONGS:
                        accountId = Integer.parseInt(in.readLine());
                        songs = server.getFavoriteSongs(accountId);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case ADDSONG:
                        song = Song.parseSong(in.readLine());
                        if (server.addSong(song)) {
                            reply.append("OK\n");
                            reply.append(song.getSongId());
                        } else reply.append("NO");
                        break;
                    case ADDSONGTOPLAYLIST:
                        song = Song.parseSong(in.readLine());
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.addSongToPlaylist(song, playlist);
                        break;
                    case DELETESONG:
                        song = Song.parseSong(in.readLine());
                        server.deleteSong(song);
                        break;
                    case REMOVESONGFROMPLAYLIST:
                        song = Song.parseSong(in.readLine());
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.removeSongFromPlaylist(song, playlist);
                        break;
                    case UPDATESONG:
                        song = Song.parseSong(in.readLine());
                        server.updateSongData(song);
                        break;
                    case PLAYSONG:
                        server.playSong(server.getAccount(Integer.parseInt(in.readLine())),
                                server.getSong(Integer.parseInt(in.readLine())));
                        break;
                    case FOLLOWSONG:
                        account = Account.parseAccount(in.readLine());
                        song = Song.parseSong(in.readLine());
                        server.followSong(account, song);
                        break;
                    case UNFOLLOWSONG:
                        account = Account.parseAccount(in.readLine());
                        song = Song.parseSong(in.readLine());
                        server.unfollowSong(account, song);
                        break;
                    case ISFOLLOWINGSONG:
                        accountId = Integer.parseInt(in.readLine());
                        int songId = Integer.parseInt(in.readLine());
                        reply.append(server.isFollowingSong(accountId, songId));
                        break;
                    case TOGGLEFAVORITESONG:
                        accountId = Integer.parseInt(in.readLine());
                        songId = Integer.parseInt(in.readLine());
                        server.toggleFavoriteSong(accountId, songId);
                        break;
                    case ISFAVORITESONG:
                        accountId = Integer.parseInt(in.readLine());
                        songId = Integer.parseInt(in.readLine());
                        reply.append(server.isFavoriteSong(accountId, songId));
                        break;
                    case GETPLAYLIST:
                        reply.append(server.getPlaylist(Integer.parseInt(in.readLine())));
                        break;
                    case GETPLAYLISTS:
                        ArrayList<Playlist> playlists = server.getPlaylists();
                        reply.append(playlists.size());
                        for (int i = 0; i < playlists.size(); i++) {
                            reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case GETPLAYLISTSBYACCOUNT:
                        accountId = Integer.parseInt(in.readLine());
                        playlists = server.getPlaylistsByAccount(accountId);
                        reply.append(playlists.size());
                        for (int i = 0; i < playlists.size(); i++) {
                            reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case GETFOLLOWEDPLAYLISTS:
                        accountId = Integer.parseInt(in.readLine());
                        playlists = server.getFollowedPlaylists(accountId);
                        reply.append(playlists.size());
                        for (int i = 0; i < playlists.size(); i++) {
                            reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case GETFAVORITEPLAYLISTS:
                        accountId = Integer.parseInt(in.readLine());
                        playlists = server.getFavoritePlaylists(accountId);
                        reply.append(playlists.size());
                        for (int i = 0; i < playlists.size(); i++) {
                            reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case ADDPLAYLIST:
                        playlist = Playlist.parsePlaylist(in.readLine());
                        if (server.addPlaylist(playlist)) {
                            reply.append("OK\n");
                            reply.append(playlist.getPlaylistId());
                        } else reply.append("NO");
                        break;
                    case DELETEPLAYLIST:
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.deletePlaylist(playlist);
                        break;
                    case UPDATEPLAYLIST:
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.updatePlaylist(playlist);
                        break;
                    case FOLLOWPLAYLIST:
                        account = Account.parseAccount(in.readLine());
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.followPlaylist(account, playlist);
                        break;
                    case UNFOLLOWPLAYLIST:
                        account = Account.parseAccount(in.readLine());
                        playlist = Playlist.parsePlaylist(in.readLine());
                        server.unfollowPlaylist(account, playlist);
                        break;
                    case ISFOLLOWINGPLAYLIST:
                        accountId = Integer.parseInt(in.readLine());
                        playlistId = Integer.parseInt(in.readLine());
                        reply.append(server.isFollowingPlaylist(accountId, playlistId));
                        break;
                    case TOGGLEFAVORITEPLAYLIST:
                        accountId = Integer.parseInt(in.readLine());
                        playlistId = Integer.parseInt(in.readLine());
                        server.toggleFavoritePlaylist(accountId, playlistId);
                        break;
                    case ISFAVORITEPLAYLIST:
                        accountId = Integer.parseInt(in.readLine());
                        playlistId = Integer.parseInt(in.readLine());
                        reply.append(server.isFavoritePlaylist(accountId, playlistId));
                        break;
                    case GETALBUM:
                        reply.append(server.getAlbum(Integer.parseInt(in.readLine())));
                        break;
                    case GETALBUMS:
                        ArrayList<Album> albums = server.getAlbums();
                        reply.append(albums.size());
                        for (int i = 0; i < albums.size(); i++) {
                            reply.append("\n");
                            reply.append(albums.get(i).toString());
                        }
                        break;
                    case GETALBUMSBYARTIST:
                        artistId = Integer.parseInt(in.readLine());
                        albums = server.getAlbumsByArtist(artistId);
                        reply.append(albums.size());
                        for (int i = 0; i < albums.size(); i++) {
                            reply.append("\n");
                            reply.append(albums.get(i).toString());
                        }
                        break;
                    case GETFOLLOWEDALBUMS:
                        accountId = Integer.parseInt(in.readLine());
                        albums = server.getFollowedAlbums(accountId);
                        reply.append(albums.size());
                        for (int i = 0; i < albums.size(); i++) {
                            reply.append("\n");
                            reply.append(albums.get(i).toString());
                        }
                        break;
                    case ADDALBUM:
                        album = Album.parseAlbum(in.readLine());
                        if (server.addAlbum(album)) {
                            reply.append("OK\n");
                            reply.append(album.getAlbumId());
                        } else reply.append("NO");
                        break;
                    case DELETEALBUM:
                        album = Album.parseAlbum(in.readLine());
                        out.println(server.deleteAlbum(album));
                        break;
                    case UPDATEALBUM:
                        album = Album.parseAlbum(in.readLine());
                        server.updateAlbum(album);
                        break;
                    case FOLLOWALBUM:
                        account = Account.parseAccount(in.readLine());
                        album = Album.parseAlbum(in.readLine());
                        server.followAlbum(account, album);
                        break;
                    case UNFOLLOWALBUM:
                        account = Account.parseAccount(in.readLine());
                        album = Album.parseAlbum(in.readLine());
                        server.unfollowAlbum(account, album);
                        break;
                    case ISFOLLOWINGALBUM:
                        accountId = Integer.parseInt(in.readLine());
                        albumId = Integer.parseInt(in.readLine());
                        reply.append(server.isFollowingAlbum(accountId, albumId));
                        break;
                    case GETUSER:
                        reply.append(server.getUser(Integer.parseInt(in.readLine())));
                        break;
                    case GETUSERS:
                        ArrayList<User> users = server.getUsers();
                        reply.append(users.size());
                        for (int i = 0; i < users.size(); i++) {
                            reply.append("\n");
                            reply.append(users.get(i).toString());
                        }
                        break;
                    case GETFOLLOWEDUSERS:
                        users = server.getFollowedUsers(Integer.parseInt(in.readLine()));
                        reply.append(users.size());
                        for (int i = 0; i < users.size(); i++) {
                            reply.append("\n");
                            reply.append(users.get(i).toString());
                        }
                        break;
                    case ADDUSER:
                        user = User.parseUser(in.readLine());
                        account = Account.parseAccount(in.readLine());
                        user.setAccount(account);
                        if (server.addUser(user)) {
                            reply.append("OK\n");
                            reply.append(user.getUserId());
                            reply.append("\n");
                            reply.append(user.getAccount().getId());
                        } else reply.append(Protocol.NO);
                        break;
                    case UPDATEUSER:
                        user = User.parseUser(in.readLine());
                        server.updateUser(user);
                        break;
                    case FOLLOWUSER:
                        account = Account.parseAccount(in.readLine());
                        user = User.parseUser(in.readLine());
                        server.followUser(account, user);
                        break;
                    case UNFOLLOWUSER:
                        account = Account.parseAccount(in.readLine());
                        user = User.parseUser(in.readLine());
                        server.unfollowUser(account, user);
                        break;
                    case ISFOLLOWINGUSER:
                        accountId = Integer.parseInt(in.readLine());
                        reply.append(server.isFollowingUser(accountId, Integer.parseInt(in.readLine())));
                        break;
                    case GETARTIST:
                        reply.append(server.getArtist(Integer.parseInt(in.readLine())));
                        break;
                    case GETARTISTS:
                        ArrayList<Artist> artists = server.getArtists();
                        reply.append(artists.size());
                        for (int i = 0; i < artists.size(); i++) {
                            reply.append("\n");
                            reply.append(artists.get(i).toString());
                        }
                        break;
                    case GETFOLLOWEDARTISTS:
                        artists = server.getFollowedArtists(Integer.parseInt(in.readLine()));
                        reply.append(artists.size());
                        for (int i = 0; i < artists.size(); i++) {
                            reply.append("\n");
                            reply.append(artists.get(i).toString());
                        }
                        break;
                    case ADDARTIST:
                        artist = Artist.parseArtist(in.readLine());
                        System.out.println(artist);
                        account = Account.parseAccount(in.readLine());
                        System.out.println(account);
                        artist.setAccount(account);
                        if (server.addArtist(artist)) {
                            reply.append("OK\n");
                            reply.append(artist.getArtistId());
                            reply.append("\n");
                            reply.append(artist.getAccount().getId());
                        } else reply.append(Protocol.NO);
                        break;
                    case UPDATEARTIST:
                        artist = Artist.parseArtist(in.readLine());
                        server.updateArtist(artist);
                        break;
                    case FOLLOWARTIST:
                        account = Account.parseAccount(in.readLine());
                        artist = Artist.parseArtist(in.readLine());
                        server.followArtist(account, artist);
                        break;
                    case UNFOLLOWARTIST:
                        account = Account.parseAccount(in.readLine());
                        artist = Artist.parseArtist(in.readLine());
                        server.unfollowArtist(account, artist);
                        break;
                    case ISFOLLOWINGARTIST:
                        accountId = Integer.parseInt(in.readLine());
                        reply.append(server.isFollowingArtist(accountId, Integer.parseInt(in.readLine())));
                        break;
                    case GETIMAGEFILE:
                        album = server.getAlbum(Integer.parseInt(in.readLine()));
                        File img = album.getCover();
                        FileUtil.uploadFile(socket, out, img);
                        break;
                    case SETIMAGEFILE:
                        albumId = Integer.parseInt(in.readLine());
                        File dir = new File("resources/images");
                        dir.mkdirs();
                        img = new File(dir, albumId + ".png");
                        FileUtil.downloadFile(socket, in, img);
                        server.setImageFile(albumId, img);
                        break;
                    case GETSONGFILE:
                        song = server.getSong(Integer.parseInt(in.readLine()));
                        System.out.println("Sending song " + song + " to the client.");
                        File wav = song.getWAV();
                        FileUtil.uploadFile(socket, out, wav);
                        break;
                    case SETSONGFILE:
                        songId = Integer.parseInt(in.readLine());
                        dir = new File("resources/songs");
                        dir.mkdirs();
                        wav = new File(dir, songId + ".wav");
                        FileUtil.downloadFile(socket, in, wav);
                        server.setSongFile(songId, wav);
                        break;
                    case LOGIN:
                        String username = in.readLine();
                        String password = in.readLine();
                        user = server.login(username, password, this);
                        if (user == null) reply.append(Protocol.NO);
                        else {
                            reply.append(Protocol.OK);
                            reply.append("\n");
                            reply.append(user);
                            System.out.println(user);
                            reply.append("\n");
                            reply.append(user.getAccount());
                        }
                        break;
                    case LOGOUT:
                        int userId = Integer.parseInt(in.readLine());
                        server.logout(userId);
                        break;
                    case SEARCHSONGS:
                        String keyword = in.readLine();
                        songs = server.searchSongs(keyword);
                        reply.append(songs.size());
                        for (int i = 0; i < songs.size(); i++) {
                            reply.append("\n");
                            reply.append(songs.get(i).toString());
                        }
                        break;
                    case SEARCHPLAYLISTS:
                        keyword = in.readLine();
                        playlists = server.searchPlaylists(keyword);
                        reply.append(playlists.size());
                        for (int i = 0; i < playlists.size(); i++) {
                            reply.append("\n");
                            reply.append(playlists.get(i).toString());
                        }
                        break;
                    case SEARCHALBUMS:
                        keyword = in.readLine();
                        albums = server.searchAlbums(keyword);
                        reply.append(albums.size());
                        for (int i = 0; i < albums.size(); i++) {
                            reply.append("\n");
                            reply.append(albums.get(i).toString());
                        }
                        break;
                    case SEARCHUSERS:
                        keyword = in.readLine();
                        users = server.searchUsers(keyword);
                        reply.append(users.size());
                        for (int i = 0; i < users.size(); i++) {
                            reply.append("\n");
                            reply.append(users.get(i).toString());
                        }
                        break;
                    case SEARCHARTISTS:
                        keyword = in.readLine();
                        artists = server.searchArtists(keyword);
                        reply.append(artists.size());
                        for (int i = 0; i < artists.size(); i++) {
                            reply.append("\n");
                            reply.append(artists.get(i).toString());
                        }
                        break;
                    case OK:
                        break;
                    case NO:
                        break;
                    case UPLOADEVENT:
                        break;
                    case PLAYEVENT:
                        break;
                }
                if (reply.length() > 0) {
                    System.out.println("Sending the following message to the client: " + reply.toString());
                    out.println(reply.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    public void listen(PlayEvent e) {
        out.println(Protocol.PLAYEVENT);
        Account source = (Account)e.getSource();
        Song song = e.getSongPlayed();
        StringBuilder sb = new StringBuilder();
        sb.append(source.getUserName());
        sb.append(" just played ");
        sb.append(song.getName());
        sb.append(" by ");
        sb.append(song.getArtist().getName());
        sb.append(".");
        out.println(sb.toString());
    }

    @Override
    public void listen(UploadEvent e) {
        System.out.println("Received a broadcast from the server.");
        out.println(Protocol.UPLOADEVENT);
        Account source = (Account)e.getSource();
        Media media = e.getMediaUploaded();
        StringBuilder sb = new StringBuilder();
        sb.append(source.getUserName());
        sb.append(" has uploaded a");
        if (media instanceof Song) {
            sb.append(" song named ");
            sb.append(((Song) media).getName());
        } else if (media instanceof Album) {
            sb.append("n album named ");
            sb.append(((Album) media).getName());
        } else {
            sb.append(" playlist named ");
            sb.append(((Playlist) media).getName());
        }
        sb.append("! Check it out!");
        System.out.println("Broadcasting the message: ");
        System.out.println(sb.toString());
        out.println(sb.toString());
    }
}
