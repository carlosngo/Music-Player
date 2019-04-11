package controller;

import net.*;
import model.*;
import view.*;

import javax.xml.bind.annotation.XmlType;
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
        client.followSong(mc.getAccountController().getUser().getAccount(), client.getSong(songId));
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
        for (Song s : client.getSongsByAccount(mc.getAccountController().getUser().getAccount().getId())) {
            data.add(map(s));
        }
        sp = new SongPanel(this, "All Songs", data);
        if (mc.getDashboard() != null) {
            mc.getDashboard().changeCard(sp);
        }
    }


    public void showSongsByAlbum(int albumId) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : client.getSongsInAlbum(albumId)) data.add(map(s));
        sp = new SongPanel(this, "Songs in " + client.getAlbum(albumId).getName(), data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByPlaylist(int playlistId) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : client.getSongsInPlaylist(playlistId)) data.add(map(s));
        sp = new SongPanel(this, "Songs in " + client.getPlaylist(playlistId).getName(), data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

    public void showSongsByArtist(int artistId) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (Song s : client.getSongsByArtist(artistId)) data.add(map(s));
        sp = new SongPanel(this, "Songs by " + client.getArtist(artistId).getName(), data);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
    }

   public void showFavoriteSongs() {
       ArrayList<ArrayList<String>> data = new ArrayList<>();
       for (Song s : client.getFavoriteSongs(mc.getAccountController().getUser().getAccount().getId())) data.add(map(s));
       sp = new SongPanel(this, "Your Favorite Songs", data);
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
        return DEFAULT_GENRES;
    }

    public String[] getAllPossibleAlbums() {
        TreeSet<String> albums = new TreeSet<String>();
        for(Album album : client.getAlbumsByAccount(mc.getAccountController().getUser().getAccount().getId())){
            albums.add(album.getName());
        }
        return albums.toArray(new String[albums.size()]);
    }

    public void addSong(String songName, String genre, int albumId, String year, File wav) {
        Song s = new Song();
        s.setName(songName);
        s.setGenre(genre);
        s.setAlbum(client.getAlbum(albumId));
        s.setArtist((Artist)mc.getAccountController().getUser());
        s.setDateCreated(Calendar.getInstance().getTime());
        if (!year.equals(""))
            s.setYear(Integer.parseInt(year));
        s.setWAV(wav);
        client.addSong(s);
        client.setSongFile(s.getSongId(), wav);
        showAllSongs();
    }

    public void addAlbum(String albumName, File cover) {
        Album a = new Album();
        a.setName(albumName);
        a.setCover(cover);
        a.setArtist((Artist)mc.getAccountController().getUser());
        a.setDateCreated(Calendar.getInstance().getTime());
        client.addAlbum(a);
        client.setImageFile(a.getAlbumId(), cover);
    }

    public void removeSong(int songId) {
        Song s = client.getSong(songId);
        if (s.getArtist().getArtistId() == ((Artist)mc.getAccountController().getUser()).getArtistId())
            client.deleteSong(s);
        else
            client.unfollowSong(mc.getAccountController().getUser().getAccount(), s);
        sp.deleteRow(index);
        mc.getPlayerController().removeSong(s);
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void removeAlbum(int albumId) {
        Album a = client.getAlbum(albumId);
        if (a.getArtist().getArtistId() == ((Artist)mc.getAccountController().getUser()).getArtistId())
            client.deleteAlbum(a);
        else client.unfollowAlbum(mc.getAccountController().getUser().getAccount(), a);
        showAlbums();
    }

    public void removePlaylist(int playlistId) {
        Playlist p = client.getPlaylist(playlistId);
        if (p.getAccount().getId() == mc.getAccountController().getUser().getAccount().getId())
            client.deletePlaylist(p);
        else client.unfollowPlaylist(mc.getAccountController().getUser().getAccount(), p);
        showPlaylists();
    }

    public void updateSong(int songId, String title, int albumId, String year, String genre){
        Song s = client.getSong(songId);
        System.out.println("Updating the song " + s.getName());
        s.setName(title);
        s.setAlbum(client.getAlbum(albumId));
        if (year.equals("")) s.setYear(0);
        else s.setYear(Integer.parseInt(year));
        s.setGenre(genre);
        client.updateSong(s);
        sp.editRow(songIndex, map(s));
        showAllSongs();
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void updateAlbum(int albumId, String newName, File newCover) {
        Album album = client.getAlbum(albumId);
        album.setName(newName);
        album.setCover(newCover);
        client.updateAlbum(album);
        client.setImageFile(albumId, newCover);
        showAlbums();
    }

    public void updatePlaylist(int playlistId, String newName) {
        Playlist p = client.getPlaylist(playlistId);
        p.setName(newName);
        client.updatePlaylist(p);
        showPlaylists();
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
