package DAO;

import Model.Song;

import java.util.ArrayList;

public class SongDAOJDBC implements SongDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "";
    private static final String SQL_LIST_BY_GENRE = "";
    private static final String SQL_LIST_BY_ALBUM = "";
    private static final String SQL_LIST_BY_YEAR = "";
    private static final String SQL_INSERT = "";
    private static final String SQL_DELETE = "";
    private static final String SQL_UPDATE = "";

    public SongDAOJDBC(DAOFactory dn) { this.db = db; }

    @Override
    public Song find(int id) {
        return null;
    }

    @Override
    public ArrayList<Song> findByGenre() {
        return null;
    }

    @Override
    public ArrayList<Song> findByAlbum() {
        return null;
    }

    @Override
    public ArrayList<Song> findByYear() {
        return null;
    }

    @Override
    public void create(Song song) {

    }

    @Override
    public void delete(Song song) {

    }

    @Override
    public void update(Song song) {

    }
}
