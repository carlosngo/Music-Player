package DAO;

import Model.*;

public interface UserDAO {

    User find(long userId);

    User find(String username, String password);

    void create(User user);

    void delete(User user);

    void update(User user);

    boolean existUsername(String username);

    public void changePassword(User user);

}
