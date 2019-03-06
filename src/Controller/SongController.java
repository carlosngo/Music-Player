package Controller;

import Model.*;
import View.*;

import java.util.*;

public class SongController {

    private MainController mc;

    private AddSongWindow asw;
    private EditSongProfileWindow espw;
    private SongPanel sp;
    private CategoryPanel cp;

    public SongController(MainController mc) {
        this.mc = mc;
        showAllSongs();
        showPlaylists();
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

    public AddSongWindow getAddSongWindow() {
        return asw;
    }

    public EditSongProfileWindow getEditSongProfileWindow() {
        return espw;
    }


    public void openAddSongWindow() {
        asw = new AddSongWindow(this);
    }

    public void showMostFrequentlyPlayed() {}

    public void showGenres() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Genre g : mc.getGenres()) {
            subCategories.add(g.getName());
        }
        cp = new CategoryPanel(this, "Genres", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.CATEGORY_PANEL);
    }

    public void showPlaylists() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Playlist p: mc.getPlaylists()) {
            subCategories.add(p.getName());
        }
        cp = new CategoryPanel(this, "Playlists", subCategories);

        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.CATEGORY_PANEL);
    }

    public void showAlbums() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Album a : mc.getAlbums()) {
            subCategories.add(a.getName());
        }
        cp = new CategoryPanel(this, "Albums", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.CATEGORY_PANEL);
    }

    public void showAllSongs() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            data.add(map(s));
        }
        sp = new SongPanel(this, "All Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.SONG_PANEL);
    }

    public void showSongsByAlbum(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Album temp = new Album();
        temp.setName(name);
        Album a = mc.getAlbums().floor(temp);
        for (Song s : mc.getSongs()) {
            if (s.getAlbumId() == a.getAlbumId()) data.add(map(s));
        }
        sp = new SongPanel(this, "Songs by " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.SONG_PANEL);
    }

    public void showSongsByPlaylist(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Playlist temp = new Playlist();
        temp.setName(name);
        Playlist p = mc.getPlaylists().floor(temp);
        PlaylistSong ps = new PlaylistSong();
        ps.setPlaylistId(p.getPlaylistId());
        for (PlaylistSong psong : mc.getBridges()) {
            for (Song s : mc.getSongs()) {
                if (s.getSongId() == psong.getSongId()) data.add(map(s));
            }
        }
        sp = new SongPanel(this, "Songs in " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.SONG_PANEL);
    }

    public void showSongsByGenre(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Genre temp = new Genre();
        temp.setName(name);
        Genre g = mc.getGenres().floor(temp);
        for (Song s : mc.getSongs()) {
            if (s.getGenreId() == g.getGenreId()) data.add(map(s));
        }
        sp = new SongPanel(this, name + " Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.SONG_PANEL);
    }

    public void showFavoriteSongs() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.isFavorite()) data.add(map(s));
        }
        sp = new SongPanel(this, "Favorite Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(Dashboard.SONG_PANEL);
    }

    public ArrayList<String> map (Song s) {
        ArrayList<String> list = new ArrayList<>();
        list.add(s.getName());
        Album a = mc.getAlbumDAO().find(s.getAlbumId());
        list.add(a.getArtist());
        list.add(a.getName());
        list.add(s.getYear() + "");
        list.add(mc.getGenreDAO().find(s.getGenreId()).getName());
        return list;
    }

    public void removeGenre(String name) {

    }

    public void removeAlbum(String name) {

    }

    public void removePlaylist(String name) {

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


}
