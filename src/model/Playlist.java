package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Playlist implements Comparable<Playlist>, Media {
    private int playlistId;
    private Account account;
    private String name;
    private ArrayList<Song> songs;
    private Date dateCreated;
    private boolean isFavorite;
	private boolean isFollowed;

    public Playlist() {
        playlistId = -1;
        dateCreated = Calendar.getInstance().getTime();
        songs = new ArrayList<>();
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Account getAccount() {
    	return account;
    }
    
    public void setAccount(Account acc) {
    	this.account = acc;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFollowed(){ return isFollowed; }

    public void setFollowed(boolean followed){ isFollowed = followed; }

    public static Playlist parsePlaylist(String s) {
        Playlist playlist = new Playlist();
        String[] playlistData = s.split("\\|");
        playlist.setPlaylistId(Integer.parseInt(playlistData[0]));
        //User user = new User();
        //user.setUserId(Integer.parseInt(playlistData[1]));
        //playlist.setUser(user);
        Account acc = new Account();
        acc.setId(Integer.parseInt(playlistData[1]));
        playlist.setAccount(acc);
        playlist.setName(playlistData[2]);
        try {

            Date bday = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(playlistData[3]);
            playlist.setDateCreated(bday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getPlaylistId());
        sb.append("|");
        //sb.append(getUser().getUserId());
        sb.append(getAccount().getId());
        sb.append("|");
        sb.append(getName());
        sb.append("|");
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        sb.append(formatter.format(getDateCreated()));
        return sb.toString();
    }

    @Override
    public int compareTo(Playlist o) {
        return o.getName().compareTo(name);
    }
}
