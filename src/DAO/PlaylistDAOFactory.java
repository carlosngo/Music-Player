package DAO;

public class PlaylistDAOFactory extends DAOFactory {
    @Override
    protected DataAccessObject createDAO() {
        return new PlaylistDAO(this);
    }
}
