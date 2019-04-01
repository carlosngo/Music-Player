package controller;

import model.*;
import dao.*;
import view.*;

import java.util.*;

public class MainController {

    // controllers
    private AccountController ac;
    private PlayerController pc;
    private SongController sc;

    // daos
    private UserDAOFactory userDAOFactory;
    private SongDAOFactory songDAOFactory;
    private AlbumDAOFactory albumDAOFactory;
    private PlaylistDAOFactory playlistDAOFactory;
    private PlaylistSongDAOFactory playlistSongDAOFactory;
    private ArtistDAOFactory artistDAOFactory;

    // views
    private static Dashboard dashboard;

    public MainController() {
        userDAOFactory = new UserDAOFactory();
        songDAOFactory = new SongDAOFactory();
        albumDAOFactory = new AlbumDAOFactory();
        playlistDAOFactory = new PlaylistDAOFactory();
        playlistSongDAOFactory = new PlaylistSongDAOFactory();
        artistDAOFactory = new ArtistDAOFactory();
        ac = new AccountController(this);
        pc = new PlayerController(this);
        sc = new SongController(this);
        openDashboard();


    }

    public void exit() {
        ac.save();
        pc.terminate();
    }

    public TreeSet<Artist> getArtists() { return ac.getArtists(); }

    public TreeSet<String> getGenres() {
        return ac.getGenres();
    }

    public TreeSet<Song> getSongs() {
        return ac.getSongs();
    }

    public TreeSet<Album> getAlbums() {
        return ac.getAlbums();
    }

    public ArrayList<Integer> getYears(){
        TreeSet<Integer> yrs = new TreeSet<>();
        for(Song s : getSongs()){
            yrs.add(s.getYear());
        }
        return new ArrayList<>(yrs);
    }

    public TreeSet<Playlist> getPlaylists() {
        return ac.getPlaylists();
    }

    public AccountController getAc() {
        return ac;
    }

    public PlayerController getPc() {
        return pc;
    }

    public SongController getSc() {
        return sc;
    }

    public void openDashboard() {
        dashboard = new Dashboard(this);
    }

    public AccountController getAccountController() {
        return ac;
    }

    public PlayerController getPlayerController() {
        return pc;
    }

    public SongController getSongController() {
        return sc;
    }

    public UserDAO getUserDAO() {
        return (UserDAO) userDAOFactory.getDAO();
    }

    public SongDAO getSongDAO() {
        return (SongDAO) songDAOFactory.getDAO();
    }

    public AlbumDAO getAlbumDAO() {
        return (AlbumDAO) albumDAOFactory.getDAO();
    }

    public PlaylistDAO getPlaylistDAO() {
        return (PlaylistDAO) playlistDAOFactory.getDAO();
    }

    public PlaylistSongDAO getPlaylistSongDAO() {
        return (PlaylistSongDAO) playlistSongDAOFactory.getDAO();
    }

    public ArtistDAO getArtistDAO() { return (ArtistDAO) artistDAOFactory.getDAO(); }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void playSongs(ArrayList<Song> songs) {
        pc.setSongs(songs);
        pc.startPlayer();
    }
    // saves all the cached data in the database.

}