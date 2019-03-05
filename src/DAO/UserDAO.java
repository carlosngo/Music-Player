package DAO;

import Model.*;
import java.sql.*;
import java.util.*;

public interface UserDAO {

    User find(long userId);

    User find(String username, String password);

    ArrayList<User> listById();

    void create(User user) throws IllegalArgumentException;

    void delete(User user);

    void update(User user) throws IllegalArgumentException;

    boolean existUserName(String username);

    public void changePassword(User user);

}
