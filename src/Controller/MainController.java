package Controller;

import Model.*;
import DAO.*;
import View.*;

import java.util.*;

public class MainController {

    // cache
    private TreeSet<Genre> genres;
    private TreeSet<Song> songs;
    private TreeSet<Album> albums;
    private TreeSet<Playlist> playlists;
    private TreeSet<PlaylistSong> bridges;

    // controllers
    private AccountController ac;
    private PlayerController pc;
    private SongController sc;

    // daos
    private UserDAO userDAO;
    private SongDAO songDAO;
    private AlbumDAO albumDAO;
    private PlaylistDAO playlistDAO;
    private PlaylistSongDAO playlistSongDAO;
    private GenreDAO genreDAO;

    // views
    private static Dashboard dashboard;

    public MainController() {
        DAOFactory db = new DriverManagerDAOFactory(DAOFactory.DATABASE_URL, DAOFactory.DATABASE_USERNAME, DAOFactory.DATABASE_PASSWORD);
        clearCache();
        ac = new AccountController(this);
        pc = new PlayerController();
        sc = new SongController(this);
        openDashboard();
        userDAO = db.getUserDAO();
        songDAO = db.getSongDAO();
        albumDAO = db.getAlbumDAO();
        playlistDAO = db.getPlaylistDAO();
        playlistSongDAO = db.getPlaylistSongDAO();
        genreDAO = db.getGenreDAO();
    }

    public TreeSet<PlaylistSong> getBridges() {
        return bridges;
    }

    public TreeSet<Genre> getGenres() {
        return genres;
    }

    public TreeSet<Song> getSongs() {
        return songs;
    }

    public TreeSet<Album> getAlbums() {
        return albums;
    }

    public TreeSet<Playlist> getPlaylists() {
        return playlists;
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
        return userDAO;
    }

    public SongDAO getSongDAO() {
        return songDAO;
    }

    public AlbumDAO getAlbumDAO() {
        return albumDAO;
    }

    public PlaylistDAO getPlaylistDAO() {
        return playlistDAO;
    }

    public PlaylistSongDAO getPlaylistSongDAO() {
        return playlistSongDAO;
    }

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    // saves all the cached data in the database.
    public void save() {
        for (Song s : songs) {
            try {
                songDAO.create(s);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        for (Genre g : genres) {
            try {
                genreDAO.create(g);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        for (Playlist p : playlists) {
            try {
                playlistDAO.create(p);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        for (Album a : albums) {
            try {
                albumDAO.create(a);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

    }

    public void load() {
        genres = new TreeSet(genreDAO.listById(ac.getUser().getUserId()));
        playlists = new TreeSet(playlistDAO.listById(ac.getUser().getUserId()));
        albums = new TreeSet(albumDAO.listById(ac.getUser().getUserId()));
        songs = new TreeSet(songDAO.listById(ac.getUser().getUserId()));

    }

    // clears the cache
    public void clearCache() {
        songs = new TreeSet<>();
        albums = new TreeSet<>();
        playlists = new TreeSet<>();
        genres = new TreeSet<>();
        bridges = new TreeSet<>();
    }

}
