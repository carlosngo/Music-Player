package DAO;

import Model.Genre;

public class GenreDAOJDBC implements GenreDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "";
    private static final String SQL_INSERT = "";
    private static final String SQL_DELETE = "";
    private static final String SQL_UPDATE = "";

    public GenreDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public Genre find(int genreId) {
        return null;
    }

    @Override
    public void create(Genre genre) {

    }

    @Override
    public void delete(Genre genre) {

    }

    @Override
    public void update(Genre genre) {

    }
}
