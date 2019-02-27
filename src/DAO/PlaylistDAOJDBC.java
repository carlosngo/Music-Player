package DAO;

import Model.Playlist;

public class PlaylistDAOJDBC implements PlaylistDAO {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "";
    private static final String SQL_INSERT = "";
    private static final String SQL_DELETE = "";
    private static final String SQL_UPDATE = "";

    public PlaylistDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public Playlist find(int playlistId) {
        return null;
    }

    @Override
    public void create(Playlist playlist) {

    }

    @Override
    public void delete(Playlist playlist) {

    }

    @Override
    public void update(Playlist playlist) {

    }
}
