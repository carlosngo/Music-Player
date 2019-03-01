package DAO;

import Model.User;
import java.sql.*;
import java.util.*;

import static DAO.DAOUtil.*;

public class UserDAOJDBC implements UserDAO {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM " + DAOFactory.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM " + DAOFactory.USER_TABLE + " WHERE UserName = ? AND Password = ?";
    private static final String SQL_LIST_BY_ID =
            "SELECT * FROM " + DAOFactory.USER_TABLE + " ORDER BY PK_UserID";
    private static final String SQL_INSERT =
            "INSERT INTO " + DAOFactory.USER_TABLE + " (UserName, Password, FirstName, LastName, Gender, Birthday) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM " + DAOFactory.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + DAOFactory.USER_TABLE + " SET UserName = ?, Password = ?, FirstName = ?, LastName = ?, Gender = ?, Birthday = ? WHERE PK_UserID = ?";
    private static final String SQL_EXIST_USERNAME =
            "SELECT PK_UserID FROM " + DAOFactory.USER_TABLE + " WHERE UserName = ?";

    public UserDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public User find(long userId) throws SQLException {
        return find(SQL_FIND_BY_ID, userId);
    }

    @Override
    public User find(String email, String password) throws SQLException {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, email, password);
    }

    private User find(String sql, Object... values) throws SQLException {
        User user = null;
        Connection connection = db.getConnection();
        PreparedStatement statement = prepareStatement(connection, sql, false, values);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            user = map(resultSet);
        }
        return user;
    }

    @Override
    public ArrayList<User> listById() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = db.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            users.add(map(resultSet));
        }
        return users;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, SQLException {
        if (user.getUserId() != -1) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }

        Object[] values = {
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getBirthday()
        };

        Connection connection = db.getConnection();
        PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setUserId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("Creating user failed, no generated key obtained.");
        }

    }

    @Override
    public void update(User user) throws SQLException {
        if (user.getUserId() == -1) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }

        Object[] values = {
                user.getUserName(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getGender(),
                user.getBirthday(),
                user.getUserId()
        };

        Connection connection = db.getConnection();
        PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Updating user failed, no rows affected.");
        }

    }

    @Override
    public void delete(User user) throws SQLException {
        Object[] values = {
                user.getUserId()
        };
        Connection connection = db.getConnection();
        PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deleting user failed, no rows affected.");
        } else {
            user.setUserId(-1);
        }
    }

    @Override
    public boolean existUserName(String userName) throws SQLException {
        Object[] values = {
                userName
        };
        boolean exist;
        Connection connection = db.getConnection();
        PreparedStatement statement = prepareStatement(connection, SQL_EXIST_USERNAME, false, values);
        ResultSet resultSet = statement.executeQuery();
        exist = resultSet.next();
        return exist;
    }

    @Override
    public void changePassword(User user) throws SQLException {

    }

    private static User map(ResultSet rs) throws SQLException {
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

    public static void main(String[] args) throws SQLException {
        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
        UserDAO userDAO = db.getUserDAO();

        // Create user.
        User user = new User();
        user.setUserName("user");
        user.setPassword("password");
        userDAO.create(user);
        System.out.println("User successfully created: " + user);

        // Create another user.
        User anotherUser = new User();
        anotherUser.setUserName("bar@foo.com");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setFirstName("Bar");
        anotherUser.setLastName("Foo");
        anotherUser.setBirthday(new Timestamp(Calendar.getInstance().getTime().getTime()));
        userDAO.create(anotherUser);
        System.out.println("Another user successfully created: " + anotherUser);

        // Update user.
        user.setFirstName("Foo");
        user.setLastName("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // Update user.
        user.setFirstName("Foo");
        user.setLastName("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // List all users.
        List<User> users = userDAO.listById();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());

        // Delete user.
        userDAO.delete(user);
        System.out.println("User successfully deleted: " + user);

        // Check if email exists.
        boolean exist = userDAO.existUserName("user");
        System.out.println("This email should not exist anymore, so this should print false: " + exist);

        // Change password.
        anotherUser.setPassword("newAnotherPassword");
        userDAO.update(anotherUser);
        System.out.println("Another user's password successfully changed: " + anotherUser);

        // Get another user by email and password.
        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);

        // Delete another user.
        userDAO.delete(foundAnotherUser);
        System.out.println("Another user successfully deleted: " + foundAnotherUser);

        // List all users again.
        users = userDAO.listById();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());

    }
}
