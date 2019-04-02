package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Song implements Comparable<Song> {
    private int songId;
    private User user;
    private Album album;
    private Artist artist;
    private String genre;
    private String name;
    private int year;
    private boolean favorite;
    private long playTime;
    private Date lastPlayed;
    private Date dateCreated;
    private File wav;


	public Song() {
	    songId = -1;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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

    public File getWAV() {
        return wav;
    }

    public void setWAV(File wav) {
        this.wav = wav;
    }

    public static Song parseSong(String s) throws ParseException {
	    Song song = new Song();
	    String[] songdata = s.split("\\|");
	    song.setSongId(Integer.parseInt(songdata[0]));
//	    song.setUser();
//	    song.setAlbum();
//      song.setArtist();
        song.setGenre(songdata[1]);
        song.setName(songdata[2]);
        song.setYear(Integer.parseInt(songdata[3]));
        song.setFavorite(Boolean.parseBoolean(songdata[4]));
        song.setPlayTime(Long.parseLong(songdata[5]));
        Date lp = new SimpleDateFormat("dd/MM/yyyy").parse(songdata[6]);
        song.setLastPlayed(lp);
        Date dc = new SimpleDateFormat("dd/MM/yyyy").parse(songdata[7]);
        song.setDateCreated(dc);
        File file = new File(songdata[8]);
        song.setWAV(file);
	    return song;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getSongId());
        sb.append("|");
        sb.append(getUser());
        sb.append("|");
        sb.append(getAlbum());
        sb.append("|");
        sb.append(getArtist());
        sb.append("|");
        sb.append(getGenre());
        sb.append("|");
        sb.append(getName());
        sb.append("|");
        sb.append(getYear());
        sb.append("|");
        sb.append(isFavorite());
        sb.append("|");
        sb.append(getPlayTime());
        sb.append("|");
        sb.append(getLastPlayed());
        sb.append("|");
        sb.append(getDateCreated());
        sb.append("|");
        sb.append(getWAV());
        return sb.toString();
    }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.getName());
    }
}
