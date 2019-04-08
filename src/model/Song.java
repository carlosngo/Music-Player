package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

public class Song implements Comparable<Song>, Media {
    // global variables
    private int songId;
    private Album album;
    private Artist artist;
    private String genre;
    private String name;
    private int year;
    private File wav;

    // client-specific variables
    /*private boolean favorite;
    private long playTime;
    private Date lastPlayed;
    private Date dateCreated;*/



	public Song() {
	    songId = -1;
    }


    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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

/*    public Date getDateCreated() {
    	return dateCreated;
    }
    
    public void setDateCreated(Date dateCreated) {
    	this.dateCreated = dateCreated;
    }
    public Date getLastPlayed() {
    	return lastPlayed;
    }
    
    public void setLastPlayed(Date lastPlayed) {
    	this.lastPlayed = lastPlayed;
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
*/
    public File getWAV() {
        return wav;
    }

    public void setWAV(File wav) {
        this.wav = wav;
    }

    public static Song parseSong(String s) {
	    Song song = new Song();
	    String[] songdata = s.split("\\|");
	    song.setSongId(Integer.parseInt(songdata[0]));
        Album album = new Album();
        album.setAlbumId(Integer.parseInt(songdata[1]));
	    song.setAlbum(album);
        Artist artist = new Artist();
        artist.setArtistId(Integer.parseInt(songdata[2]));
        song.setArtist(artist);
        song.setGenre(songdata[3]);
        song.setName(songdata[4]);
        song.setYear(Integer.parseInt(songdata[5]));
       /* song.setFavorite(Boolean.parseBoolean(songdata[6]));
        song.setPlayTime(Long.parseLong(songdata[7]));
        try {
            Date lp = new SimpleDateFormat("dd/MM/yyyy").parse(songdata[8]);
            song.setLastPlayed(lp);
            Date dc = new SimpleDateFormat("dd/MM/yyyy").parse(songdata[9]);
            song.setDateCreated(dc);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

	    return song;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getSongId());
        sb.append("|");
//        sb.append(getUser().);
//        sb.append("|");
        sb.append(getAlbum().getAlbumId());
        sb.append("|");
        sb.append(getArtist().getArtistId());
        sb.append("|");
        sb.append(getGenre());
        sb.append("|");
        sb.append(getName());
        sb.append("|");
        sb.append(getYear());
       /* sb.append("|");
        sb.append(isFavorite());
        sb.append("|");
        sb.append(getPlayTime());
        sb.append("|");
        sb.append(getLastPlayed());
        sb.append("|");
        sb.append(getDateCreated());*/
        return sb.toString();
    }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.getName());
    }
}
