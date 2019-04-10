package controller;

import net.*;
import model.*;
import view.*;

import java.util.*;
import java.io.*;

public class SongController {

    private final String[] DEFAULT_GENRES = {"Pop", "OPM", "Hip-hop", "RnB", "Rock", "Reggae", "Indie",
            "Electro-Dance", "Punk", "Jazz", "Classical", "Latin", "Folk/Acoustic", "Metal", "Country", "Funk" };

    private MainController mc;

    private AddSongWindow asw;
    private AddAlbumWindow aaw;
    private AddPlaylistWindow apw;
    private AddToPlaylistWindow atpw;
    private EditSongProfileWindow espw;
    private EditCategoryWindow ecw;
    private SongPanel sp;
    private CategoryPanel cp;
    private UserArtistListPanel uap;

    private Client client = mc.getClient();


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

    public void openAddAlbumWindow() { aaw = new AddAlbumWindow(this); }

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
        p.setDateCreated(Calendar.getInstance().getTime());
        p.setAccount(mc.getAccountController().getUser().getAccount());
        client.addPlaylist(p);
        showPlaylists();
    }

    public void addAlbum(String albumName) {
        Album a = new Album();
        a.setName(albumName);
        a.setDateCreated(Calendar.getInstance().getTime());
        a.setArtist((Artist)mc.getAccountController().getUser());
        client.addAlbum(a);
        showAlbums();
    }

    // play song at index of displayedSongs
    public void playSong(int songId) {
        client.playSong(mc.getAccountController().getUser().getAccount().getId(), songId);
        ArrayList<Song> queue = new ArrayList<>();
        queue.add(client.getSong(songId));
        mc.playSongs(queue);
        sp.editRow(index, map(displayedSongs.get(index)));
    }

    // play song at index of displayedSongs
    public void addToQueue(int songId) {
        mc.getPlayerController().addSong(client.getSong(songId));
    }

    public void addToPlaylist(int songId, int playlistId) {
        client.addSongToPlaylist(client.getSong(songId), client.getPlaylist(playlistId));
    }

    public void removeFromPlaylist(int songId, int playlistId) {
        client.removeSongFromPlaylist(client.getSong(songId), client.getPlaylist(playlistId));
        sp.deleteRow(songIndex);
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
        ArrayList<Song> queue = client.getSongsWithGenre(genreName);
        mc.playSongs(queue);
    }

    public void addSongsInGenreToQueue(String genreName) {
        for (Song s : client.getSongsWithGenre(genreName)) mc.getPlayerController().addSong(s);
    }

    public void playSongsInPlaylist(int playlistId) {
        ArrayList<Song> queue = client.getSongsInPlaylist(playlistId);
        mc.playSongs(queue);
    }

    public void addSongsInPlaylistToQueue(int playlistId) {
        for (Song s : client.getSongsInPlaylist(playlistId)) mc.getPlayerController().addSong(s);
    }

    public void playSongsInAlbum(int albumId) {
        ArrayList<Song> queue = client.getSongsInAlbum(albumId);
        mc.playSongs(queue);
    }

    public void addSongsInAlbumToQueue(int albumId) {
        for (Song s : client.getSongsInAlbum(albumId)) mc.getPlayerController().addSong(s);
    }

    public void playSongsByArtist(int artistId) {
        ArrayList<Song> queue = client.getSongsByArtist(artistId);
        mc.playSongs(queue);
    }

    public void addSongsByArtistToQueue(int artistId) {
        for (Song s : client.getSongsByArtist(artistId)) mc.getPlayerController().addSong(s);
    }

    public void showPlaylists() {
        ArrayList<ArrayList<String>> subCategories = new ArrayList<ArrayList<String>>();
        ArrayList<String> subCategoriesContent;
        for (Playlist p : client.getPlaylistsByAccount(mc.getAccountController().getUser().getAccount().getId())) {
            subCategoriesContent = new ArrayList<String>();
            subCategoriesContent.add(p.getName());
            subCategoriesContent.add(p.getAccount().getUserName());
            subCategories.add(subCategoriesContent);
        }
        cp = new CategoryPanel(this, "Playlists", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showAlbums() {
        ArrayList<ArrayList<String>> subCategories = new ArrayList<ArrayList<String>>();
        ArrayList<String> subCategoriesContent;
        for (Album a : client.getAlbumsByAccount(mc.getAccountController().getUser().getAccount().getId())) {
            subCategoriesContent = new ArrayList<String>();
            subCategoriesContent.add(a.getName());
            subCategoriesContent.add(a.getArtist().getAccount().getUserName());
            subCategories.add(subCategoriesContent);
        }
        cp = new CategoryPanel(this, "Albums", subCategories);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
    }

    public void showArtists() {
        TreeSet<String> subCategories = new TreeSet<>();
        for (Artist a : client.getFollowedArtists(mc.getAccountController().getUser().getAccount().getId())) {
            subCategories.add(a.getName());
        }
        uap = new UserArtistListPanel(this, "Artists", new ArrayList(subCategories));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(uap);
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

    /*public void showMostFrequentlyPlayed() {
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
*/
    public void showSongsByAlbum(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : mc.getSongs()) {

            if (s.getAlbum() != null && s.getAlbum().getName().equals(name)) {
                data.add(map(s));
                songs.add(s);
            }
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, "Songs by " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByPlaylist(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        Playlist temp = new Playlist();
        temp.setName(name);
        Playlist p = mc.getPlaylists().floor(temp);
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : p.getSongs()) {

            data.add(map(s));
            songs.add(s);
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, "Songs in " + name, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByGenre(String name) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.getGenre() != null && s.getGenre().equals(name)) {
                data.add(map(s));
                songs.add(s);
            }
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, name + " Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByYear(String yr) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.getYear() != 0 && s.getYear()==Integer.parseInt(yr)) {
                data.add(map(s));
                songs.add(s);
            }
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, yr + " Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByArtist(String artistName) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.getArtist() != null && s.getArtist().getName().equals(artistName)) {
                data.add(map(s));
                songs.add(s);
            }
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, "Songs by " + artistName, data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

   /* public void showFavoriteSongs() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<Song> songs = new ArrayList<>();
        for (Song s : mc.getSongs()) {
            if (s.isFavorite()) {
                data.add(map(s));
                songs.add(s);
            }
        }
        displayedSongs = new ArrayList<>(songs);
        sp = new SongPanel(this, "Favorite Songs", data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }*/

    public ArrayList<String> map (Song s) {
        ArrayList<String> list = new ArrayList<>();
        list.add(s.getName());
        if (s.getAlbum() != null) {
            list.add(s.getAlbum().getName());
        } else {
            list.add("");
        }
        if (s.getArtist() != null) {
            list.add(s.getArtist().getName());
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
       /* list.add(s.getDateCreated().toString());
        list.add(s.getPlayTime() + "");*/
        return list;
    }

    public String[] getAllPossibleGenres() {
        TreeSet<String> genres = mc.getGenres();
        for (int i = 0; i < DEFAULT_GENRES.length; i++) genres.add(DEFAULT_GENRES[i]);
        return genres.toArray(new String[genres.size()]);
    }

    public String[] getAllPossibleAlbums() {
        TreeSet<String> albums = new TreeSet<String>();
        for(Album album : mc.getAlbums()){
            albums.add(album.getName());
        }
        return albums.toArray(new String[albums.size()]);
    }

    public Artist getArtist(String artistName) {
//        Artist a = new Artist();
//        if (artistName.equals("")) return null;
//        else a.setName(artistName);
//        if (mc.getArtists().contains(a)) {
//            a = mc.getArtists().floor(a);
//        } else {
//            mc.getArtists().add(a);
//            if (cp != null) {
//                cp.addRow("Arists", a.getName());
//            }
//        }
//        return a;

        Artist a = new Artist();
        if (artistName.equals("")) return null;
        else a.setName(artistName);
        if (mc.getArtists().contains(a)) {
            a = mc.getArtists().floor(a);
        } else {
            mc.getArtists().add(a);
            if (uap != null) {
                uap.addRow("Arists", a.getName());
            }
        }
        return a;
    }

    public Album getAlbum(String albumName) {
        Album a = new Album();
        if (albumName.equals("")) return null;
        else a.setName(albumName);
        if (mc.getAlbums().contains(a)) {
            a = mc.getAlbums().floor(a);
        } else {
            mc.getAlbums().add(a);
            a.setDateCreated(Calendar.getInstance().getTime());
            if (cp != null)
                cp.addRow("Albums", a.getName(), a.getArtist().getName());
        }
        return a;
    }

    public String getGenre(String genreName) {
        if (genreName == null || genreName.equals("")) return null;
        if (!mc.getGenres().contains(genreName)) { // if genre does not currently exist
            mc.getGenres().add(genreName); // add the new genre
            if (cp != null)
                cp.addRow("Genres", genreName, "");

        }
        return mc.getGenres().floor(genreName);
    }

    public void addSong(String songName, String genreName, String albumName, String artistName, String year, File wav) {
        Song s = new Song();
        s.setName(songName);
//        s.setUser(mc.getAccountController().getUser());

        s.setGenre(getGenre(genreName));
        s.setAlbum(getAlbum(albumName));
        s.setArtist(getArtist(artistName));
//        s.setDateCreated(Calendar.getInstance().getTime());
        if (!year.equals(""))
            s.setYear(Integer.parseInt(year));
        s.setWAV(wav);
        mc.getSongs().add(s);
        displayedSongs.add(s);
        showAllSongs();
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
        sp.deleteRow(index);
        mc.getPlayerController().removeSong(s);
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void removeAlbum(String albumName) {
        Album temp = new Album();
        temp.setName(albumName);
        Album a = mc.getAlbums().floor(temp);
        mc.getAlbums().remove(a);
        for (Song s : mc.getSongs()) {
            if (s.getAlbum() != null && s.getAlbum().equals(a)) {
                s.setAlbum(null);
            }
        }
        try {
            mc.getAlbumDAO().delete(a);
        } catch (IllegalArgumentException e) {
        }
        showAlbums();
    }
    public void updateSong(int songIndex, String title, String album, String artist, String year, String genre){
        Song s = displayedSongs.get(songIndex);
        System.out.println("Updating the song " + s.getName());
        s.setName(title);
        s.setAlbum(getAlbum(album));
        s.setArtist(getArtist(artist));
        if (year.equals("")) s.setYear(0);
        else s.setYear(Integer.parseInt(year));
        s.setGenre(getGenre(genre));
        displayedSongs.set(songIndex, s);
        sp.editRow(songIndex, map(s));
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

    public void updateArtist(String oldName, String newName) {
        Artist temp = new Artist();
        temp.setName(oldName);
        Artist a = mc.getArtists().floor(temp);
        a.setName(newName);
        showArtists();
    }


    public void setAlbumCover(String albumName, File cover) {
        Album temp = new Album();
        temp.setName(albumName);
        Album a = mc.getAlbums().floor(temp);
        a.setCover(cover);
        mc.getPlayerController().getPlayerPanel().update();
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

//    public void toggleFollow(String objectKind, String objectName) {
//        switch (objectKind){
//            case "artist":
//                Artist tempArtist = new Artist();
//                tempArtist.setName(objectName);
//                Artist a = mc.getArtists().floor(tempArtist);
//                a.setFollow(!a.setFollow());
//                break;
//            case "user":
//                User tempUser = new User();
//                tempUser.setName(objectName);
//                User u = mc.getUsers().floor(tempUser);
//                u.setFollow(!u.setFollow());
//                break;
//            case "album":
//                Album tempAlbum = new Album();
//                tempAlbum.setName(objectName);
//                Album album = mc.getAlbums().floor(tempAlbum);
//                album.setFollow(!album.setFollow());
//                break;
//            case "playlist":
//                Playlist temp = new Playlist();
//                temp.setName(objectName);
//                Playlist p = mc.getPlaylists().floor(temp);
//                p.setFollow(!p.setFollow());
//                break;
//        }
//    }
}
