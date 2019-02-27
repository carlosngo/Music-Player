package DAO;

import Model.Album;

public class AlbumDAOJDBC implements AlbumDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "";
    private static final String SQL_INSERT = "";
    private static final String SQL_DELETE = "";
    private static final String SQL_UPDATE = "";

    public AlbumDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public Album find(int albumId) {
        return null;
    }

    @Override
    public void create(Album album) {

    }

    @Override
    public void delete(Album album) {

    }

    @Override
    public void update(Album album) {

    }
}
