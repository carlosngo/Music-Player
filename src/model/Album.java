package model;

import java.util.*;
import java.io.*;

public class Album implements Comparable<Album>, Media {
    private int albumId;
	private String name;
    private Artist artist;
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
	public Artist getArtist() {
		return artist;
	}
	public void setArtist(Artist artist) {
		this.artist = artist;
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
