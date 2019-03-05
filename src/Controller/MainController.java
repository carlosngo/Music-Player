package Controller;

import Model.*;
import DAO.*;
import View.*;

import java.util.*;
import java.io.*;
import java.sql.*;

public class MainController {
    // cache
    private static ArrayList<Genre> genres;
    private static ArrayList<Song> songs;
    private static ArrayList<Album> albums;
    private static ArrayList<Playlist> playlists;

    // controllers
    private AccountController ac;
    private PlayerController pc;

    // daos
    public static UserDAO userDAO;
    public static SongDAO songDAO;
    public static AlbumDAO albumDAO;
    public static PlaylistDAO playlistDAO;
    public static PlaylistSongDAO playlistSongDAO;
    public static GenreDAO genreDAO;

    // views
    private MainScreen ms;

    public MainController() {
        DAOFactory db = new DriverManagerDAOFactory(DAOFactory.DATABASE_URL, DAOFactory.DATABASE_USERNAME, DAOFactory.DATABASE_PASSWORD);
        userDAO = db.getUserDAO();
        songDAO = db.getSongDAO();
        albumDAO = db.getAlbumDAO();
        playlistDAO = db.getPlaylistDAO();
        playlistSongDAO = db.getPlaylistSongDAO();
        genreDAO = db.getGenreDAO();
    }


    // saves all the cached data in the database. only used when guest logs in and chooses to save his data.
    // therefore, assign the newly logged in user's id to all the cached data.
    public void save() {

    }

    // clears the cache
    public static void clearCache() {
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        playlists = new ArrayList<>();
        genres = new ArrayList<>();
    }
}
