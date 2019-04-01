package dao;

import model.User;

import java.sql.*;
import java.util.*;

import static util.DAOUtil.*;

public class UserDAO implements DataAccessObject {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM " + Database.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT * FROM " + Database.USER_TABLE + " WHERE UserName = ? AND Password = ?";
    private static final String SQL_LIST_BY_ID =
            "SELECT * FROM " + Database.USER_TABLE + " ORDER BY PK_UserID";
    private static final String SQL_INSERT =
            "INSERT INTO " + Database.USER_TABLE + " (UserName, Password, FirstName, LastName, Gender, Birthday) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM " + Database.USER_TABLE + " WHERE PK_UserID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + Database.USER_TABLE + " SET UserName = ?, Password = ?, FirstName = ?, LastName = ?, Gender = ?, Birthday = ? WHERE PK_UserID = ?";
    private static final String SQL_EXIST_USERNAME =
            "SELECT PK_UserID FROM " + Database.USER_TABLE + " WHERE UserName = ?";

    public UserDAO(DAOFactory db) {
        this.db = db;
    }

    public User find(long userId) {
        return find(SQL_FIND_BY_ID, userId);
    }

    public User find(String email, String password) {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, email, password);
    }

    private User find(String sql, Object... values) {
        User user = null;
        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, sql, false, values);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public ArrayList<User> listById() {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void create(User user) throws IllegalArgumentException {
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

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setUserId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(User user) throws IllegalArgumentException {
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

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(User user) {
        Object[] values = {
                user.getUserId()
        };

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            } else {
                user.setUserId(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existUserName(String userName) {
        Object[] values = {
                userName
        };
        boolean exist = false;
        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_EXIST_USERNAME, false, values);
             ResultSet resultSet = statement.executeQuery()) {
            exist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    public void changePassword(User user) {

    }

    private static User map(ResultSet rs) {
        User user = new User();
        try {
            user.setUserId(rs.getInt("PK_UserID"));
            user.setUserName(rs.getString("UserName"));
            user.setPassword(rs.getString("Password"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setGender(rs.getString("Gender"));
            user.setBirthday(rs.getDate("Birthday"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {
//        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
//        UserDAO userDAO = db.getUserDAO();
//
//        // Create user.
//        User user = new User();
//        user.setUserName("user");
//        user.setPassword("password");
//        userDAO.create(user);
//        System.out.println("User successfully created: " + user);
//
//        // Create another user.
//        User anotherUser = new User();
//        anotherUser.setUserName("bar@foo.com");
//        anotherUser.setPassword("anotherPassword");
//        anotherUser.setFirstName("Bar");
//        anotherUser.setLastName("Foo");
//        anotherUser.setBirthday(new Timestamp(Calendar.getInstance().getTime().getTime()));
//        userDAO.create(anotherUser);
//        System.out.println("Another user successfully created: " + anotherUser);
//
//        // Update user.
//        user.setFirstName("Foo");
//        user.setLastName("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // Update user.
//        user.setFirstName("Foo");
//        user.setLastName("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // List all users.
//        List<User> users = userDAO.listById();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());
//
//        // Delete user.
//        userDAO.delete(user);
//        System.out.println("User successfully deleted: " + user);
//
//        // Check if email exists.
//        boolean exist = userDAO.existUserName("user");
//        System.out.println("This email should not exist anymore, so this should print false: " + exist);
//
//        // Change password.
//        anotherUser.setPassword("newAnotherPassword");
//        userDAO.update(anotherUser);
//        System.out.println("Another user's password successfully changed: " + anotherUser);
//
//        // Get another user by email and password.
//        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
//        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);
//
//        // Delete another user.
//        userDAO.delete(foundAnotherUser);
//        System.out.println("Another user successfully deleted: " + foundAnotherUser);
//
//        // List all users again.
//        users = userDAO.listById();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());

    }
}