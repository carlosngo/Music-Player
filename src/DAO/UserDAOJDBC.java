package DAO;

import Model.User;

public class UserDAOJDBC implements UserDAO {

    private DAOFactory db;

    public UserDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public User find(long userId) {
        return null;
    }

    @Override
    public User find(String username, String password) {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public boolean existUsername(String username) {
        return false;
    }

    @Override
    public void changePassword(User user) {

    }
}
