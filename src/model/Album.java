package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Album implements Comparable<Album>, Media {
    private int albumId;
    private Artist artist;
	private String name;
    private File cover;
    private Date dateCreated;
    private boolean isFollowed;

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

    public boolean isFollowed(){ return isFollowed; }

    public void setFollowed(boolean followed){ isFollowed = followed; }

    public static Album parseAlbum(String s) {
		Album album = new Album();
		String[] albumData = s.split("\\|");
		album.setAlbumId(Integer.parseInt(albumData[0]));
		album.setName(albumData[1]);
		Artist artist = new Artist();
		artist.setArtistId(Integer.parseInt(albumData[2]));
		album.setArtist(artist);
		try {
			Date dc = new SimpleDateFormat("dd/MM/yyyy").parse(albumData[3]);
			album.setDateCreated(dc);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return album;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getAlbumId());
		sb.append("|");
		sb.append(getName());
		sb.append("|");
		sb.append(getArtist().getArtistId());
		sb.append("|");
		sb.append(getDateCreated());
		return sb.toString();
	}

    @Override
	public int compareTo(Album o) {
		return name.compareTo(o.getName());
	}
}
