package dao;

public class AccountPlaylistDAOFactory extends DAOFactory {

    @Override
    protected DataAccessObject createDAO() {
        return new AccountPlaylistDAO(this);
    }
}
