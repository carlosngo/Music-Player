package dao;

public class UserPlaylistDAOFactory extends DAOFactory {

    @Override
    protected DataAccessObject createDAO() {
        return new UserPlaylistDAO(this);
    }
}
