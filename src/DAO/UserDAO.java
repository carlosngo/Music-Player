package DAO;

import Model.*;
import java.sql.*;
import java.util.*;

public interface UserDAO {

    User find(long userId) throws SQLException;

    User find(String username, String password) throws SQLException;

    ArrayList<User> listById() throws SQLException;

    void create(User user) throws SQLException;

    void delete(User user) throws SQLException;

    void update(User user) throws SQLException;

    boolean existUserName(String username) throws SQLException;

    public void changePassword(User user) throws SQLException;

}
