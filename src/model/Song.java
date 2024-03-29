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
    private Date dateCreated;
    private File wav;
    private boolean isFollowed;

    // client-specific variables
    private boolean favorite;
    private long playTime;
    private Date lastPlayed;



	public Song() {

	    songId = -1;
	    album = new Album();
	    artist = new Artist();
	    genre = "";
	    name = "";
        dateCreated = Calendar.getInstance().getTime();
        favorite = false;
        playTime = 0;

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

    public boolean isFollowed(){ return isFollowed; }

    public void setFollowed(boolean followed){ isFollowed = followed; }

    public Date getDateCreated() {
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

    public File getWAV() {
        return wav;
    }

    public void setWAV(File wav) {
        this.wav = wav;
    }

    public static Song parseSong(String s) {
        Song song = new Song();
	    try {
            System.out.println(s);
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
            song.setFavorite(Boolean.parseBoolean(songdata[6]));
            song.setPlayTime(Long.parseLong(songdata[7]));
            try {
                Date lp = null;
                if (!songdata[8].equals("null"))
                    lp = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(songdata[8]);
                song.setLastPlayed(lp);
                Date dc = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(songdata[9]);
                song.setDateCreated(dc);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
	        e.printStackTrace();
        }
	    return song;
    }

    @Override
    public String toString(){
	    try {
            StringBuilder sb = new StringBuilder();
            sb.append(getSongId());
            sb.append("|");
            sb.append(getAlbum().getAlbumId());
            sb.append("|");
            sb.append(getArtist().getArtistId());
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
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            if (getLastPlayed() == null) sb.append("null");
            else sb.append(formatter.format(getLastPlayed()));
            sb.append("|");
            sb.append(formatter.format(getDateCreated()));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.getName());
    }
}
