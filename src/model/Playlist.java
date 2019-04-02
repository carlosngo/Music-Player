package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Playlist implements Comparable<Playlist>, Media {
    private int playlistId;
    private User user;
    private String name;
    private ArrayList<Song> songs;
    private Date dateCreated;

    public Playlist() {
        playlistId = -1;
        songs = new ArrayList<>();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Playlist parsePlaylist(String s) throws ParseException {
        Playlist playlist = new Playlist();
        String[] playlistData = s.split("\\|");
        playlist.setPlaylistId(Integer.parseInt(playlistData[0]));
        User user = new User();
        user.setUserId(Integer.parseInt(playlistData[1]));
        playlist.setUser(user);
        playlist.setName(playlistData[2]);
        Date dc = new SimpleDateFormat("dd/MM/yyyy").parse(playlistData[3]);
        playlist.setDateCreated(dc);
        return playlist;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getPlaylistId());
        sb.append("|");
        sb.append(getUser().getUserId());
        sb.append("|");
        sb.append(getName());
        sb.append("|");
        sb.append(getDateCreated());
        return sb.toString();
    }

    @Override
    public int compareTo(Playlist o) {
        return o.getName().compareTo(name);
    }
}
