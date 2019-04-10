package model;

import events.*;
import java.util.*;

public class Artist extends User implements Comparable<Artist>{
	private int artistId;
	private String genre;
	private boolean isFollowed;

	public Artist() {
		super();
		artistId = -1;
	}

	public int getArtistId() {
		return artistId;
	}

	public void setArtistId(int artistId) {
		this.artistId = artistId;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean isFollowed(){ return isFollowed; }

	public void setFollowed(boolean followed){ isFollowed = followed; }

	public static Artist parseArtist(String s) {
		Artist artist = new Artist();
		String[] artistData = s.split("\\|");
		artist.setArtistId(Integer.parseInt(artistData[0]));
		artist.setGenre(artistData[1]);
		return artist;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getArtistId());
		sb.append("|");
		sb.append(getGenre());
		return sb.toString();
	}

	@Override
	public int compareTo(Artist o) {
		return getName().compareTo(o.getName());
	}

}
