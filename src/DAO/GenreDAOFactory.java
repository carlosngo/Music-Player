package DAO;

public class GenreDAOFactory extends DAOFactory {
    @Override
    protected DataAccessObject createDAO() {
        return new GenreDAO(this);
    }
}
