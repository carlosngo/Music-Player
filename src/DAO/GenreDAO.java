package DAO;

import Model.*;

import static DAO.DAOUtil.*;
import java.sql.*;
import java.util.*;

public class GenreDAO implements DataAccessObject {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM " + DAOFactory.GENRE_TABLE + " WHERE PK_GenreID = ?";
    private static final String SQL_LIST_BY_ID =
            "SELECT DISTINCT * FROM " + DAOFactory.GENRE_TABLE + " INNER JOIN " + DAOFactory.SONG_TABLE + " ON " + DAOFactory.GENRE_TABLE + ".PK_GenreID = " + DAOFactory.SONG_TABLE + ".FK_GenreID WHERE " + DAOFactory.GENRE_TABLE + ".FK_UserID = ? AND " + DAOFactory.SONG_TABLE + ".FK_UserID = ? ORDER BY PK_GenreID";
    private static final String SQL_INSERT =
            "INSERT INTO " + DAOFactory.GENRE_TABLE + " (FK_UserID, Name) VALUES (?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM " + DAOFactory.GENRE_TABLE + " WHERE PK_GenreID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + DAOFactory.GENRE_TABLE + " SET Name = ? WHERE PK_GenreID = ?";
    private static final String SQL_EXIST_GENRE =
            "SELECT PK_GenreID FROM " + DAOFactory.GENRE_TABLE + " WHERE Name = ? AND FK_UserID = ?";

    public GenreDAO(DAOFactory db) {
        this.db = db;
    }

    public Genre find(int genreId) {
        Genre genre = null;
        try (Connection con = db.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_FIND_BY_ID, false, genreId);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                genre = map(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genre;
    }

    public ArrayList<Genre> listById(int userId) {
        Object[] values = {
                userId,
                userId
        };
        ArrayList<Genre> genres = new ArrayList<>();

        try (Connection con = db.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_ID, false, values);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(map(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public Genre findByName(String name, int userId) {
        Object[] values = {
                name,
                userId
        };
        Genre genre = null;
        try (Connection con = db.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_EXIST_GENRE, false, values);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) genre = map(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }


    public void create(Genre genre) throws IllegalArgumentException {
        if (genre.getGenreId() != -1) {
            throw new IllegalArgumentException("Genre is already created, the genre ID is not null.");
        }
        Object[] values = {
                genre.getUser().getUserId(),
                genre.getName()
        };

        try(Connection connection = db.getConnection();
            PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);
        ) {
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                genre.setGenreId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating genre failed, no generated key obtained.");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Genre genre) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, genre.getGenreId());) {
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting genre failed, no rows affected.");
            } else {
                genre.setGenreId(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Genre genre) throws IllegalArgumentException {
        if (genre.getGenreId() == -1) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }
        Object[] values = {
                genre.getName(),
                genre.getGenreId()
        };
        try (Connection con = db.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_UPDATE, false, values);
        ) {
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating genre failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Genre map(ResultSet rs) {
        Genre genre = new Genre();
        UserDAO userDAO = (UserDAO)new UserDAOFactory().createDAO();

        try {
            genre.setGenreId(rs.getInt("PK_GenreID"));
            User u = userDAO.find(rs.getInt("FK_UserID"));
            genre.setUser(u);
            genre.setName(rs.getString("Name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genre;
    }

    public static void main(String[] args) {
//        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
//        GenreDAO genreDAO = db.getGenreDAO();
////        // Create genre.
//        Genre genre = new Genre();
//        genre.setGenreId(4);
//        genre.setUserId(14);
//        genre.setName("Rock");
//        genreDAO.create(genre);
//        System.out.println("Genre successfully created: " + genre);
//
//        // Create another genre.
//        Genre anotherUser = new Genre();
//        anotherUser.setUserId(14);
//        anotherUser.setName("Rock");
//        genreDAO.create(anotherUser);
//        System.out.println("Another genre successfully created: " + anotherUser);
//
//        // Update genre.
//        genre.setName("Metal");
//        genreDAO.update(genre);
//        System.out.println("Genre successfully updated: " + genre);


//         List all users.
//        List<Genre> users = genreDAO.listById(12);
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());

        // Delete genre.
//        genreDAO.delete(genre);
//        System.out.println("Genre successfully deleted: " + genre);
//
//        // Check if email exists.
//        boolean exist = genreDAO.existGenre("Popular");
//        System.out.println("This email should not exist anymore, so this should print false: " + exist);
//
    }
}
