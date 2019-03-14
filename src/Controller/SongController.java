package Controller;

import Model.*;
import View.*;

import java.util.*;
import java.io.*;

public class SongController {

    private MainController mc;

    private AddSongWindow asw;
    private AddPlaylistWindow apw;
    private AddToPlaylistWindow atpw;
    private EditSongProfileWindow espw;
    private EditCategoryWindow ecw;
    private SongPanel sp;
    private CategoryPanel cp;

    private ArrayList<Song> displayedSongs;

    public SongController(MainController mc) {
        this.mc = mc;
        displayedSongs = new ArrayList<>();
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

    public void openAddPlaylistWindow() { apw = new AddPlaylistWindow(this); }

    public void openAddToPlaylistWindow(int index) {
        atpw = new AddToPlaylistWindow(this, index);
    }

    public void openEditSongProfileWindow(int index, ArrayList<String> data) {
        espw = new EditSongProfileWindow(this, data, index);
    }

    public void openEditCategoryWindow(String category, String subCategoryName) {
        ecw = new EditCategoryWindow(this, category, subCategoryName);
    }

    public void addPlaylist(String playlistName) {
        Playlist p = new Playlist();
        p.setName(playlistName);
        mc.getPlaylists().add(p);
        cp.addRow("Playlists", playlistName);
    }


    // play song at index of displayedSongs
    public void playSong(int index) {
        ArrayList<Song> queue = new ArrayList<>();
        queue.add(displayedSongs.get(index));
        mc.playSongs(queue);
    }

    // play song at index of displayedSongs
    public void addToQueue(int index) {
        ArrayList<Song> queue = new ArrayList<>();
        mc.getPlayerController().addSong(displayedSongs.get(index));
    }

    public void addToPlaylist(int songIndex, int playlistIndex) {
        ArrayList<Playlist> playlists = new ArrayList<>(mc.getPlaylists());
        Song s = displayedSongs.get(songIndex);
        Playlist p = playlists.get(playlistIndex);
        p.getSongs().add(s);
    }

//    public void addToPlaylist(ArrayList<String> songInfo, Playlist playlist){
//        for (Song s : mc.getAccountController().getSongs()){
//            if(s.getName().equals(songInfo.get(0)) && s.getAlbum().equals(songInfo.get(1))
//                    && s.getYear()==Integer.parseInt(songInfo.get(2)) && s.getGenre().equals(songInfo.get(3))){
//                playlist.getSongs().add(s);
//            }
//        }
//    }


    public void playSongsInGenre(String genreName) {
        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getGenre().equals(genreName)) queue.add(s);
        }
        mc.playSongs(queue);
    }

    public void addSongsInGenreToQueue(String genreName) {
//        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getGenre().equals(genreName)) mc.getPlayerController().addSong(s);
        }
//        mc.playSongs(queue);
    }

    public void playSongsInPlaylist(String playlistName) {
        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        Playlist temp = new Playlist();
        temp.setName(playlistName);
        Playlist p = mc.getPlaylists().floor(temp);
        for (Song s : p.getSongs()) {
            queue.add(s);
        }
        mc.playSongs(queue);
    }

    public void addSongsInPlaylistToQueue(String playlistName) {
//        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        Playlist temp = new Playlist();
        temp.setName(playlistName);
        Playlist p = mc.getPlaylists().floor(temp);
        for (Song s : p.getSongs()) {
            mc.getPlayerController().addSong(s);
        }
//        mc.playSongs(queue);
    }

    public void playSongsInAlbum(String albumName) {
        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getAlbum().getName().equals(albumName)) queue.add(s);
        }
        mc.playSongs(queue);
    }

    public void addSongsInAlbumToQueue(String albumName) {
//        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getAlbum().getName().equals(albumName)) mc.getPlayerController().addSong(s);
        }
//        mc.playSongs(queue);
    }

    public void playSongsInYear(String yr) {
        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getYear() == Integer.parseInt(yr)) queue.add(s);
        }
        mc.playSongs(queue);
    }

    public void addSongsInYearToQueue(String yr) {
//        ArrayList<Song> queue = new ArrayList<>();
        // populate the queue with songs in the genre
        for (Song s : mc.getSongs()) {
            if (s.getYear() == Integer.parseInt(yr)) mc.getPlayerController().addSong(s);
        }
//        mc.playSongs(queue);
    }

    public void showGenres() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (String g : mc.getGenres()) {
            subCategories.add(g);
        }
        cp = new CategoryPanel(this, "Genres", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showPlaylists() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Playlist p: mc.getPlaylists()) {
            subCategories.add(p.getName());
        }
        cp = new CategoryPanel(this, "Playlists", subCategories);

        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showYears() {
        ArrayList<String> subCategories = new ArrayList<>();
        for(Integer y : mc.getYears()){
            if (y != 0) subCategories.add(y.toString());
        }
        cp = new CategoryPanel(this, "Years", subCategories);

        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showAlbums() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Album a : mc.getAlbums()) {
            subCategories.add(a.getName());
        }
        cp = new CategoryPanel(this, "Albums", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showArtists() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (String artist : mc.getArtists()) {
            subCategories.add(artist);
        }
        cp = new CategoryPanel(this, "Artists", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showAllSongs() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        displayedSongs = new ArrayList<>(mc.getSongs());
        for (Song s : displayedSongs) {
            data.add(map(s));
        }
        sp = new SongPanel(this, "All Songs", data);
        if (mc.getDashboard() != null) {
            mc.getDashboard().changeCard(sp);
        }

    }

    public void showMostFrequentlyPlayed() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        displayedSongs = new ArrayList<>(mc.getSongs());
        Collections.sort(displayedSongs, new Comparator<Song>() {
            @Override
            public int compare(Song a, Song b) {
                return Long.compare(b.getPlayTime(), a.getPlayTime());
            }
        });
        for (Song s : displayedSongs) {
            ArrayList<String> list = map(s);
            list.add((int)s.getPlayTime() + "");
            data.add(list);
        }
        sp = new SongPanel(this, "Most Played Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByAlbum(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {

            if (s.getAlbum() != null && s.getAlbum().getName().equals(name)) data.add(map(s));
        }
        sp = new SongPanel(this, "Songs by " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByPlaylist(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Playlist temp = new Playlist();
        temp.setName(name);
        Playlist p = mc.getPlaylists().floor(temp);
        for (Song s : p.getSongs()) {
//            System.out.println(s);
            data.add(map(s));
        }
        sp = new SongPanel(this, "Songs in " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByGenre(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.getGenre() != null && s.getGenre().equals(name)) data.add(map(s));
        }
        sp = new SongPanel(this, name + " Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByYear(String yr) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.getYear() != 0 && s.getYear()==Integer.parseInt(yr)) data.add(map(s));
        }
        sp = new SongPanel(this, yr + " Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showFavoritePlaylists() {
        ArrayList<String> subCategories = new ArrayList<>();
        for (Playlist p: mc.getPlaylists()) {
            if(p.isFavorite()) subCategories.add(p.getName());
        }
        cp = new CategoryPanel(this, "Favorite Playlists", subCategories);

        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showFavoriteSongs() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.isFavorite()) data.add(map(s));
        }
        sp = new SongPanel(this, "Favorite Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public ArrayList<String> map (Song s) {
        ArrayList<String> list = new ArrayList<>();
        list.add(s.getName());
        if (s.getAlbum() != null) {
            list.add(s.getAlbum().getName());
        } else {
            list.add("");
        }
        if (s.getYear() == 0) {
            list.add("");
        } else
            list.add(s.getYear() + "");
        if (s.getGenre() != null)
            list.add(s.getGenre());
        else
            list.add("");
        return list;
    }

    public Album getAlbum(String albumName) {
        Album a = new Album();
        if (albumName.equals("")) return null;
        else a.setName(albumName);
        if (mc.getAlbums().contains(a)) {
            a = mc.getAlbums().floor(a);
        } else {
            mc.getAlbums().add(a);
            if (cp != null)
                cp.addRow("Albums", a.getName());
        }
        return a;
    }

    public String getGenre(String genreName) {
        if (genreName.equals("")) return null;
        if (!mc.getGenres().contains(genreName)) { // if genre does not currently exist
            mc.getGenres().add(genreName); // add the new genre
            if (cp != null)
                cp.addRow("Genres", genreName);
        }
        return mc.getGenres().floor(genreName);
    }

    public void addSong(String songName, String artist, String genreName, String albumName, String year, File wav) {
        Song s = new Song();
        s.setName(songName);
        s.getArtist().setName(artist);
        s.setUser(mc.getAccountController().getUser());

        s.setGenre(getGenre(genreName));
        s.setAlbum(getAlbum(albumName));
        if (!year.equals(""))
            s.setYear(Integer.parseInt(year));
        s.setWAV(wav);
        mc.getSongs().add(s);
        showAllSongs();
        displayedSongs.add(s);
    }

    public void removeSong(int index) {
        Song s = displayedSongs.get(index);
        try {
            mc.getSongDAO().delete(s);
        } catch(IllegalArgumentException e) {

        }
        mc.getSongs().remove(s);
        for (Playlist p : mc.getPlaylists()) {
            p.getSongs().remove(s);
        }
        displayedSongs.remove(s);
        showAllSongs();
        mc.getPlayerController().removeSong(s);
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void updateSong(int songIndex, String title, String album, String artist, String year, String genre){
        Song s = displayedSongs.get(songIndex);
//        System.out.println(s);
        s.setName(title);
        s.setAlbum(getAlbum(album));
//        if (s.getAlbum() != null)
//            s.getAlbum().setName(album);
//        else {
////            Album a = new
//        }
        s.getArtist().setName(artist);
        s.setYear(Integer.parseInt(year));
        s.setGenre(getGenre(genre));
//        s.getGenre().setName(genre);
        showAllSongs();
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void updateGenre(String oldName, String newName) {
        mc.getGenres().remove(oldName);
        mc.getGenres().remove(newName);
        for (Song s : mc.getSongs()) {
            if (s.getGenre().equals(oldName)) s.setGenre(newName);
        }
        showGenres();
    }

    public void updateAlbum(String oldName, String newName) {
        Album temp = new Album();
        temp.setName(oldName);
        Album g = mc.getAlbums().floor(temp);
        g.setName(newName);
        showAlbums();
    }

    public void updatePlaylist(String oldName, String newName) {
        Playlist temp = new Playlist();
        temp.setName(oldName);
        Playlist g = mc.getPlaylists().floor(temp);
        g.setName(newName);
        showPlaylists();
    }

    public void setAlbumCover(String albumName, File cover) {
        Album temp = new Album();
        temp.setName(albumName);
        Album a = mc.getAlbums().floor(temp);
        a.setCover(cover);
    }

    public void removePlaylist(String playlistName) {
        Playlist temp = new Playlist();
        temp.setName(playlistName);
        Playlist p = mc.getPlaylists().floor(temp);
        mc.getPlaylists().remove(p);
        try {
            mc.getPlaylistDAO().delete(p);
        } catch (IllegalArgumentException e) {
        }
        showPlaylists();
    }

    public boolean isFavoritePlaylist(String playlistName) {
        Playlist temp = new Playlist();
        temp.setName(playlistName);
        Playlist p = mc.getPlaylists().floor(temp);
        if (p != null) return p.isFavorite();
        return false;
    }

    public void toggleFavoritePlaylist(String playlistName) {
        Playlist temp = new Playlist();
        temp.setName(playlistName);
        Playlist p = mc.getPlaylists().floor(temp);
        p.setFavorite(!p.isFavorite());
    }
}
