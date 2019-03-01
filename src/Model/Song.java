package Model;

import java.util.*;

public class Song {
    private int songId;
    private int userId;
    private int albumId;
    private int genreId;
    private String name;
    private int year;
    private boolean favorite;
    private long playTime;
    private Calendar lastPlayed;
    private String path;

    public Song() { }
    public Song(int songId, int userId, String name, int year) {
        this.songId = songId;
        this.userId = userId;
        this.name = name;
        this.year = year;
    }

    public Calendar getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Calendar lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(long playTime) {
        this.playTime = playTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
