package Controller;

import Model.*;
import View.*;

import java.util.ArrayList;

public class SongController {

    private MainController mc;

    private ArrayList<Genre> genres;
    private ArrayList<Song> songs;
    private ArrayList<Album> albums;
    private ArrayList<Playlist> playlists;

    private AddSongWindow asw;
    private EditSongProfileWindow espw;
    private SongPanel sp;
    private CategoryPanel cp;

    public SongController(MainController mc) {
        this.mc = mc;
        sp = new SongPanel(this, "Songs");
        cp = null;
    }
    public SongPanel getSongPanel() {
        return sp;
    }

    public void setSongPanel(SongPanel sp) {
        this.sp = sp;
    }

    public void setCategoryPanel(CategoryPanel cp) {
        this.cp = cp;
    }

    public CategoryPanel getCategoryPanel() {
        return cp;
    }

    public MainController getMainController() {
        return mc;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Album> getAlbums() {
        return albums;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public AddSongWindow getAddSongWindow() {
        return asw;
    }

    public EditSongProfileWindow getEditSongProfileWindow() {
        return espw;
    }


    public void openAddSongWindow() {
        asw = new AddSongWindow(this);
    }

    public ArrayList<String> getSubCategories(String category) {
        ArrayList<String> subCategories = new ArrayList<>();
        switch(category) {
            case "Genres":
//                for (Song s : )
                break;
            case "Playlists":
                break;
            case "Albums":
                break;

        }
        return subCategories;
    }

    public ArrayList<ArrayList<Object>> getData(String category) {
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        switch(category) {
            case "Genres":
//                for (Song s : )
                break;
            case "Playlists":
                break;
            case "Albums":
                break;
        }
        return data;
    }

    public void remove(String category, String name) {
        switch(category) {
            case "Genres":
//                for (Song s : )
                break;
            case "Playlists":
                break;
            case "Albums":
                break;

        }
    }

    // clears the cache
    public void clearCache() {
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        playlists = new ArrayList<>();
        genres = new ArrayList<>();
    }
}
