package Controller;

import DAO.*;
import View.*;

import java.util.*;

public class MainController {

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
        ac = new AccountController(this);
        pc = new PlayerController();
        sc = new SongController(this);
        userDAO = db.getUserDAO();
        songDAO = db.getSongDAO();
        albumDAO = db.getAlbumDAO();
        playlistDAO = db.getPlaylistDAO();
        playlistSongDAO = db.getPlaylistSongDAO();
        genreDAO = db.getGenreDAO();
        openDashboard();
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

    // saves all the cached data in the database. only used when guest logs in and chooses to save his data.
    // therefore, assign the newly logged in user's id to all the cached data.
    public void save() {

    }

}
