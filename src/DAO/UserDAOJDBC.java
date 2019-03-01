package DAO;

import Model.User;
import java.sql.*;

public class UserDAOJDBC implements UserDAO {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT " + DAOFactory.USER_COLUMNS + " FROM " + DAOFactory.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT " + DAOFactory.USER_COLUMNS + " FROM " + DAOFactory.USER_TABLE + " WHERE UserName = ? AND Password = ?";
    private static final String SQL_INSERT =
            "INSERT INTO " + DAOFactory.USER_TABLE + " (" + DAOFactory.USER_COLUMNS + ") VALUES (?, ?, ?, ?, ?, ?, ?";
    private static final String SQL_DELETE =
            "DELETE FROM " + DAOFactory.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + DAOFactory.USER_TABLE + " SET UserName = ?, Password = ?, FirstName = ?, LastName = ?, Gender = ?, Birthday = ?";

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

    public static User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("PK_UserID"));
        user.setUserName(rs.getString("UserName"));
        user.setPassword(rs.getString("Password"));
        user.setFirstName(rs.getString("FirstName"));
        user.setLastName(rs.getString("LastName"));
        user.setGender(rs.getString("Gender"));
        user.setBirthday(rs.getDate("Birthday"));
        return user;
    }
}
