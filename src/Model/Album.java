package Model;

import java.sql.Blob;
import java.io.*;

public class Album implements Comparable<Album> {
    private int albumId;
    private int userId;
    private String name;
    private String artist;
    private File cover;

	public Album() {
    	albumId = -1;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
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
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public File getCover() {
		return cover;
	}

	public void setCover(File cover) {
		this.cover = cover;
	}

	@Override
	public int compareTo(Album o) {
		return name.compareTo(o.getName());
	}
}
