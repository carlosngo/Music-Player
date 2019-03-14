package Model;

import java.util.*;
import java.io.*;

public class Album implements Comparable<Album> {
    private int albumId;
    private User user;
    private String name;
    private String artist;
    private File cover;
    private Date dateCreated;

	public Album() {
    	albumId = -1;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getCover() {
		return cover;
	}

	public void setCover(File cover) {
		this.cover = cover;
	}

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
	public int compareTo(Album o) {
		return name.compareTo(o.getName());
	}
}
