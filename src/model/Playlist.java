package model;

import java.util.*;

public class Playlist implements Comparable<Playlist> {
    private int playlistId;
    private User user;
    private String name;
    private ArrayList<Song> songs;
    private boolean favorite;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append();
        sb.append("|");
        sb.append();
        sb.append("|");
        sb.append();
        sb.append("|");
        sb.append();
        sb.append("|");
    }

    @Override
    public int compareTo(Playlist o) {
        return o.getName().compareTo(name);
    }
}
