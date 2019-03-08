package Model;

import java.util.*;

public class Playlist implements Comparable<Playlist> {
    private int playlistId;
    private int userId;
    private String name;
    private ArrayList<Song> songs;
    private boolean favorite;

    public Playlist() {
        playlistId = -1;
        songs = new ArrayList<>();
    }

    public Playlist(int playlistId, int userId, String name) {
        this.playlistId = playlistId;
        this.userId = userId;
        this.name = name;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    public int compareTo(Playlist o) {
        return o.getName().compareTo(name);
    }
}
