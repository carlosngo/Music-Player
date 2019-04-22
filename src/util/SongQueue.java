package util;

import model.Song;
import java.util.ArrayList;
import java.util.Stack;

public class SongQueue extends Queue {
    private ArrayList<Song> songs;
    private ArrayList<Song> unplayed;
    private Stack<Song> played;

    public SongQueue() {
        songs = new ArrayList<>();
        unplayed = new ArrayList<>();
        played = new Stack<>();
    }

    @Override
    public Iterator createIterator() {
        return new SongIterator(this);
    }

    public ArrayList<Song> getAllSongs() {
        return songs;
    }

    public ArrayList<Song> getUnplayedSongs() {
        return unplayed;
    }

    public Stack<Song> getPlayedSongs() {
        return played;
    }

    public void setAllSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public void setUnplayedSongs(ArrayList<Song> unplayed) {
        this.unplayed = unplayed;
    }

    public void setPlayedSongs(Stack<Song> played) {
        this.played = played;
    }

    public void clear() {
        songs.clear();
        unplayed.clear();
        played.clear();
    }
}
