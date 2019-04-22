package controller;

import net.*;
import model.*;
import view.*;

import javax.swing.*;
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
    private BrowserPanel bp;
    private InfoPanel ip;
    private EditAlbumWindow eaw;
    private Client client;
    private User user;

    public SongController(MainController mc) {
        this.mc = mc;
        client = mc.getClient();
        user = mc.getAccountController().getUser();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SongPanel getSongPanel() {
        return sp;
    }

    public CategoryPanel getCategoryPanel() {
        return cp;
    }

    public BrowserPanel getBrowserPanel() { return bp; }

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
        asw = new AddSongWindow(this, client.getAlbumsByArtist(((Artist)user).getArtistId()));
    }

    public void openAddAlbumWindow() { aaw = new AddAlbumWindow(this); }

    public void openAddPlaylistWindow() { apw = new AddPlaylistWindow(this); }

    public void openAddToPlaylistWindow(int songId) {
        atpw = new AddToPlaylistWindow(this, songId, client.getPlaylistsByAccount(user.getAccount().getId()));
    }

    //i think this should take in songID as parameter, then get the song and feed it to the EditSongProfileWindow
    // along with the list of albums
    public void openEditSongProfileWindow(Song song, int selectedRow) {
        espw = new EditSongProfileWindow(this, client.getAlbumsByArtist(((Artist)user).getArtistId()), song, selectedRow);
    }

    public void openEditPlaylistWindow(String category, Object obj) {
        ecw = new EditCategoryWindow(this, category, obj);
    }

    public void openEditAlbumWindow(SongController sc, Album obj) {
        eaw = new EditAlbumWindow(this, obj);
    }

    // play song at index of displayedSongs
    public void playSong(int index, int songId) {
        try {
            Song song = client.getSong(songId);
            client.followSong(user.getAccount(), song);
            client.playSong(user.getAccount().getId(), songId);
            ArrayList<Song> queue = new ArrayList<>();
            queue.add(song);
            mc.playSongs(queue);
//            sp.editRow(index, map(song));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // play song at index of displayedSongs
    public void addToQueue(int songId) {
        mc.getPlayerController().addSong(client.getSong(songId));
    }

    public void addToPlaylist(int songId, int playlistId) {
        client.addSongToPlaylist(client.getSong(songId), client.getPlaylist(playlistId));
    }

    public void removeFromPlaylist(int songIndex, int songId, int playlistId) {
        client.removeSongFromPlaylist(client.getSong(songId), client.getPlaylist(playlistId));
        sp.deleteRow(songIndex);
    }

    public void removeFromAlbum(int songId) {
        Song song = client.getSong(songId);
        song.setAlbum(new Album());
        client.updateSong(song);
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

    public PlaylistPanel showPlaylists(int accountId) {
        cp = new PlaylistPanel(this, client.getFollowedPlaylists(accountId), "PLAYLISTS");
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (PlaylistPanel)cp;
    }

    public PlaylistPanel showFavoritePlaylists(int accountId) {
        cp = new PlaylistPanel(this, client.getFavoritePlaylists(accountId),"Your Favorite Playlists");
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (PlaylistPanel)cp;
    }

    public AlbumPanel showAlbums(int accountId) {
        cp = new AlbumPanel(this, client.getFollowedAlbums(accountId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (AlbumPanel)cp;
    }

    public AlbumPanel showAlbumsByArtist(int artistId) {
        cp = new AlbumPanel(this, client.getAlbumsByArtist(artistId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (AlbumPanel)cp;
    }

    public ArtistPanel showArtists(int accountId) {
        cp = new ArtistPanel(this, client.getFollowedArtists(accountId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (ArtistPanel) cp;
    }

    public UserPanel showFriends(int accountId) {
        cp = new UserPanel(this, client.getFollowedUsers(accountId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(cp);
        return (UserPanel) cp;
    }

    public SongPanel showAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        songs.addAll(client.getFollowedSongs(user.getAccount().getId()));
        sp = new SongPanel(this, "All Songs", null, songs);
        if (mc.getDashboard() != null) {
            mc.getDashboard().changeCard(sp);
        }
        return sp;
    }

    public SongPanel showSongsFollowedByUser(int userId) {
        User user = client.getUser(userId);
        sp = new SongPanel(this, "Songs followed by "+user.getFirstName().toUpperCase(), null, client.getFollowedSongs(user.getAccount().getId()));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
        return sp;
    }

    public SongPanel showSongsByAlbum(int albumId) {
        Album album = client.getAlbum(albumId);
        sp = new SongPanel(this, "Songs in " + album.getName(), album, client.getSongsInAlbum(albumId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
        return sp;
    }

    public SongPanel showSongsByPlaylist(int playlistId) {
        Playlist playlist = client.getPlaylist(playlistId);
        sp = new SongPanel(this, "Songs in " + playlist.getName(), playlist, client.getSongsInPlaylist(playlistId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
        return sp;
    }

    public SongPanel showSongsByArtist(int artistId) {
        Artist artist = client.getArtist(artistId);
        sp = new SongPanel(this, "Songs by " + artist.getAccount().getUserName(), artist, client.getSongsByArtist(artistId));
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
        return sp;
    }

   public SongPanel showFavoriteSongs() {
       sp = new SongPanel(this, "Your Favorite Songs", null, client.getFavoriteSongs(user.getAccount().getId()));
       if (mc.getDashboard() != null) mc.getDashboard().changeCard(sp);
       return sp;
    }

    public BrowserPanel showBrowserPanel(String keyword) {
        bp = new BrowserPanel(this, keyword);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(bp);
        return bp;
    }

    public InfoPanel showInfo(User user) {
        ip = new InfoPanel(this, user);
        if (mc.getDashboard() != null) mc.getDashboard().changeCard(ip);
        return ip;
    }

//    public InfoPanel showSearchResults(String keyword) {
//        return new InfoPanel(this, keyword);
//    }

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
        for(Album album : client.getFollowedAlbums(user.getAccount().getId())){
            albums.add(album.getName());
        }
        return albums.toArray(new String[albums.size()]);
    }

    public void addSong(String songName, String genre, int albumId, String year, File wav) {
        Song s = new Song();
        s.setName(songName);
        s.setGenre(genre);
        s.setAlbum(client.getAlbum(albumId));
        s.setArtist((Artist)user);
        s.setDateCreated(Calendar.getInstance().getTime());
        try {
            s.setYear(Integer.parseInt(year));
        } catch (NumberFormatException e) {
            s.setYear(0);
        }
        s.setWAV(wav);
        client.addSong(s);
        client.setSongFile(s.getSongId(), wav);
        client.followSong(user.getAccount(),s);
//        showAllSongs();
//        showAllSongs();
    }

    public void addAlbum(String albumName, File cover) {
        Album a = new Album();
        a.setName(albumName);
        if (cover != null) a.setCover(cover);
        a.setArtist((Artist)user);
        a.setDateCreated(Calendar.getInstance().getTime());
        client.addAlbum(a);
        if (cover != null) client.setImageFile(a.getAlbumId(), cover);
        client.followAlbum(user.getAccount(), a);
        showAlbums(user.getAccount().getId());
    }

    public void addPlaylist(String playlistName) {
        Playlist p = new Playlist();
        p.setName(playlistName);
        p.setDateCreated(Calendar.getInstance().getTime());
        p.setAccount(user.getAccount());
        client.addPlaylist(p);
        client.followPlaylist(user.getAccount(), p);
        showPlaylists(user.getAccount().getId());
    }

    public void removeSong(int index, int songId) {
        Song s = client.getSong(songId);
        if (s.getArtist().getArtistId() == ((Artist)user).getArtistId())
            client.deleteSong(s);
        else
            client.unfollowSong(user.getAccount(), s);
        sp.deleteRow(index);
        mc.getPlayerController().removeSong(s);
        mc.getPlayerController().getPlayerPanel().update();
    }

    public void removeAlbum(int albumId) {
        Album a = client.getAlbum(albumId);
        if (a.getArtist().getArtistId() == ((Artist)user).getArtistId()) {
            boolean success = client.deleteAlbum(a);
            if (!success) {
                JOptionPane.showMessageDialog(null, "The album contains one or more songs.",
                        "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        } else client.unfollowAlbum(user.getAccount(), a);
        showAlbums(user.getAccount().getId());
    }

    public void removePlaylist(int playlistId) {
        Playlist p = client.getPlaylist(playlistId);
        if (p.getAccount().getId() == user.getAccount().getId())
            client.deletePlaylist(p);
        else client.unfollowPlaylist(user.getAccount(), p);
        showPlaylists(user.getAccount().getId());
    }

    public void updateSong(int songIndex, int songId, String title, int albumId, String year, String genre){
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
        showAlbums(user.getAccount().getId());
    }

    public void updatePlaylist(int playlistId, String newName) {
        Playlist p = client.getPlaylist(playlistId);
        p.setName(newName);
        client.updatePlaylist(p);
        showPlaylists(user.getAccount().getId());
    }

    public void followSong(Song song) {
        client.followSong(user.getAccount(), song);
    }

    public void followPlaylist(Playlist playlist) {
        client.followPlaylist(user.getAccount(), playlist);
    }

    public void followAlbum(Album album) {
        client.followAlbum(user.getAccount(), album);
        ArrayList<Song> songsInAlbum = client.getSongsInAlbum(album.getAlbumId());
        for (int i = 0; i < songsInAlbum.size(); i++) {
            followSong(songsInAlbum.get(i));
        }
    }

    public void followArtist(Artist artist) {
        client.followArtist(user.getAccount(), artist);
    }

    public void followUser(User user) {
        client.followUser(this.user.getAccount(), user);
    }

    public ArrayList<Song> searchSongs(String keyword) {
        return client.searchSongs(keyword);
    }

    public ArrayList<Playlist> searchPlaylists(String keyword) {
        return client.searchPlaylists(keyword);
    }

    public ArrayList<Album> searchAlbums(String keyword) {
        return client.searchAlbums(keyword);
    }

    public ArrayList<User> searchUsers(String keyword) {
        return client.searchUsers(keyword);
    }

    public ArrayList<Artist> searchArtists(String keyword) {
        return client.searchArtists(keyword);
    }

    public void toggleFavoriteSong(int songId) {
        int accountId = user.getAccount().getId();
        client.toggleFavoriteSong(accountId, songId);
    }

    public boolean isFavoriteSong(int songId) {
        int accountId = user.getAccount().getId();
        return client.isFavoriteSong(accountId, songId);
    }

    public void toggleFavoritePlaylist(int playlistId) {
        int accountId = user.getAccount().getId();
        client.toggleFavoritePlaylist(accountId, playlistId);
    }

    public boolean isFavoritePlaylist(int playlistId) {
        int accountId = user.getAccount().getId();
        return client.isFavoritePlaylist(accountId, playlistId);
    }

    public boolean isFollowingSong(int songId) {
        int accountId = user.getAccount().getId();
        return client.isFollowingSong(accountId, songId);
    }

    public boolean isFollowingAlbum(int albumId) {
        int accountId = user.getAccount().getId();
        return client.isFollowingAlbum(accountId, albumId);
    }

    public boolean isFollowingPlaylist(int playlistId) {
        int accountId = user.getAccount().getId();
        return client.isFollowingPlaylist(accountId, playlistId);
    }

    public boolean isFollowingUser(int accountId) {
        int myAccountId = user.getAccount().getId();
        return client.isFollowingUser(myAccountId, accountId);
    }

    public boolean isFollowingArtist(int accountId) {
        int myAccountId = user.getAccount().getId();
        return client.isFollowingArtist(myAccountId, accountId);
    }

    public void unfollowSong(Song song) {
        client.unfollowSong(user.getAccount(), song);
    }

    public void unfollowPlaylist(Playlist playlist) {
        client.unfollowPlaylist(user.getAccount(), playlist);
    }

    public void unfollowAlbum(Album album) {
        client.unfollowAlbum(user.getAccount(), album);
    }

    public void unfollowUser(User user) {
        client.unfollowUser(this.user.getAccount(), user);
    }

    public void unfollowArtist(Artist artist) {
        client.unfollowArtist(user.getAccount(), artist);
    }

    public User getUser(int accountId) {
        Account account = client.getAccount(accountId);
        return client.logIn(account.getUserName(), account.getPassword());
    }
}
