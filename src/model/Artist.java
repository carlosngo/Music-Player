package model;

import events.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Artist extends User implements Comparable<Artist>{
	private int artistId;
	private String genre;

	public Artist() {
		super();
		genre = " ";
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

	public static Artist parseArtist(String s) {
		Artist user = new Artist();
		String[] biodata = s.split("\\|");
		user.getAccount().setId(Integer.parseInt(biodata[0]));
		user.setArtistId(Integer.parseInt(biodata[1]));
		user.setFirstName(biodata[2]);
		user.setLastName(biodata[3]);
		user.setGender(biodata[4]);
		try {
			Date bday = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(biodata[5]);
			user.setBirthday(bday);
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("parse exception");
		}
		user.setGenre(biodata[6]);
		return user;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(getAccount().getId());
		sb.append("|");
		sb.append(getArtistId());
		sb.append("|");
		sb.append(getFirstName());
		sb.append("|");
		sb.append(getLastName());
		sb.append("|");
		sb.append(getGender());
		sb.append("|");
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
		sb.append(formatter.format(getBirthday()));
		sb.append("|");
		sb.append(getGenre());
		return sb.toString();
	}

	@Override
	public int compareTo(Artist o) {
		return getName().compareTo(o.getName());
	}

}
