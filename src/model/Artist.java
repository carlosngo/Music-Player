package model;

import events.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		artist.getAccount().setId(Integer.parseInt(artistData[0]));
		artist.setArtistId(Integer.parseInt(artistData[1]));
		artist.setGenre(artistData[2]);
		artist.setFirstName(artistData[3]);
		artist.setLastName(artistData[4]);
		artist.setGender(artistData[5]);
		try {
			Date bday = new SimpleDateFormat("dd/MM/yyyy").parse(artistData[6]);
			artist.setBirthday(bday);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return artist;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getAccount().getId());
		sb.append("|");
		sb.append(getArtistId());
		sb.append("|");
		sb.append(getGenre());
		sb.append("|");
		sb.append(getFirstName());
		sb.append("|");
		sb.append(getLastName());
		sb.append("|");
		sb.append(getGender());
		sb.append("|");
		sb.append(getBirthday());
		return sb.toString();
	}

	@Override
	public int compareTo(Artist o) {
		return getName().compareTo(o.getName());
	}

}
