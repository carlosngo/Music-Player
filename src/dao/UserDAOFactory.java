package dao;

public class UserDAOFactory extends DAOFactory {
    @Override
    protected DataAccessObject createDAO() {
        return new UserDAO(this);
    }
}
