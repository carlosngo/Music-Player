package Model;

import java.sql.Blob;
import java.util.*;

public class Song implements Comparable<Song> {
    private int songId;
    private int userId;
    private int albumId;
    private int genreId;
    private String name;
    private int year;
    private boolean favorite;
    private long playTime;
    private Date lastPlayed;
    private String fileName;
    private Blob file;


	public Song() {
	    songId = -1;
    }
    public Song(int songId, int userId, String name, int year) {
        this.songId = songId;
        this.userId = userId;
        this.name = name;
        this.year = year;
    }

    public Date getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(Date lastPlayed) {
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Blob getFile() {
		return file;
	}
    
	public void setFile(Blob file) {
		this.file = file;
	}

    @Override
    public int compareTo(Song o) {
        return Integer.compare(songId, o.getSongId());
    }
}
