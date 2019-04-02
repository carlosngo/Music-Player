package model;

import events.*;
import java.util.*;

public class Artist extends User implements Comparable<Artist>{
	private int artistId;
	private String genre;

	public Artist() {
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


	@Override
	public int compareTo(Artist o) {
		return getName().compareTo(o.getName());
	}

}
