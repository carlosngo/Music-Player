package DAO;

import Controller.*;

import static DAO.DAOUtil.prepareStatement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Model.Album;
import Model.Song;
import Model.User;

public class AlbumDAO implements DataAccessObject {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_INSERT = "INSERT INTO album (FK_UserID, Name, Artist, Cover) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_UPDATE = "UPDATE album SET FK_UserID = ?, Name = ?, Artist = ?, Cover = ? WHERE PK_AlbumID = ?";
    private static final String SQL_LIST_BY_ID = "SELECT * FROM " + Database.ALBUM_TABLE + " WHERE FK_UserID = ?";
    private static final String SQL_EXIST_ALBUM = "SELECT * FROM " + Database.ALBUM_TABLE + " WHERE Name = ? AND FK_UserID = ?";
    private static final String SQL_LIST_BY_ARTIST = "SELECT * FROM " + Database.ALBUM_TABLE + " INNER JOIN " + Database.ARTIST_TABLE 
    		+ " ON " + Database.ALBUM_TABLE + ".FK_ArtistID = " + Database.ARTIST_TABLE + ".PK_ArtistID WHERE " + Database.ARTIST_TABLE + ".Name = ?";
    
    
    private static final String PATH = "resources/images/";

    public AlbumDAO(DAOFactory db) {
        this.db = db;
    }

    private Album map(ResultSet rs) throws SQLException {
        Album album = new Album();
        UserDAO userDAO = (UserDAO) new UserDAOFactory().createDAO();
        String fileFormat = ".jpg";
        String fileName = rs.getString("Name") + rs.getInt("PK_AlbumID") + fileFormat;

        album.setAlbumId(rs.getInt("PK_AlbumID"));
        User u = userDAO.find(rs.getInt("FK_UserID"));
        album.setUser(u);
        album.setName(rs.getString("Name"));
        album.setArtist(rs.getString("Artist"));
//        album.setFile(rs.getBlob("Cover"));
//        album.setCoverPath(fileName);
        if (rs.getBlob("Cover") != null) {
            BlobParser.setStrategy(new BlobToFile());
            File dir = new File("resources/images");
            dir.mkdirs();
            File img = new File(dir, album.getName());
            try {
                img.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BlobParser.executeStrategy(rs.getBlob("Cover"), img);
            album.setCover(img);
        } else {
            album.setCover(null);
        }

        return album;
    }

    public Album find(int albumId) {
        Album album = null;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setInt(1, albumId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                album = map(rs);
            }

            rs.close();
            statement.close();
            return album;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void create(Album album) throws IllegalArgumentException{
        if (album.getAlbumId() != -1) {
            throw new IllegalArgumentException("Song is already created, the song ID is not null.");
        }
        try {
//            URL resource = getClass().getClassLoader().getResource("images/" + album.getCoverPath());
//            File img = Paths.get(resource.toURI()).toFile();
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, album.getUser().getUserId());
            statement.setString(2, album.getName());
            statement.setString(3, album.getArtist());

            File img = album.getCover();
            if (img != null) statement.setBinaryStream(4, new FileInputStream(img));
            else statement.setBinaryStream(4, null);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                album.setAlbumId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }
            statement.close();
        }catch(SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(Album album) {

        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);

            statement.setInt(1, album.getAlbumId());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Album album) throws IllegalArgumentException {
        try {
            if (album.getAlbumId() == -1) {
                throw new IllegalArgumentException("User is not created yet, the user ID is null.");
            }
//            URL resource = getClass().getClassLoader().getResource("images/" + album.getCoverPath());
//            File img = Paths.get(resource.toURI()).toFile();
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
//            System.out.println(album.getAlbumId());
//            System.out.println(album.getName());
            statement.setInt(1, album.getUser().getUserId());
            statement.setString(2, album.getName());
            statement.setString(3, album.getArtist());
            File img = album.getCover();
            if (img != null) statement.setBinaryStream(4, new FileInputStream(img));
            else statement.setBinaryStream(4, null);
            statement.setInt(5, album.getAlbumId());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Album> listById(int userId) {
        ArrayList<Album> albums = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                albums.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return albums;
    }

    public Album findByName(String albumName, int userId) {
        Album album = null;
        try{
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_EXIST_ALBUM);
            statement.setString(1, albumName);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                album = map(rs);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return album;
    }
    
    public ArrayList<Album> listByArtistName(String name){
    	 Object[] values = {
    			 name
    	 };
         ArrayList<Album> albums = new ArrayList<>();
         try {
             Connection con = Database.getConnection();
             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_ARTIST, false, values);
             ResultSet rs = stmt.executeQuery();
             while (rs.next()) {
                 albums.add(map(rs));
             }
         } catch(SQLException e) {
             e.printStackTrace();
         }
         return albums;
    }

    public static void main(String[] args) {
//            DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
//            AlbumDAO userDAO = db.getAlbumDAO();

        // Create user.
//        Album album = new Album();

//        album.setAlbumId(2);
//        album.setName("Carlos's Album");
//        album.setCoverPath("nocover.jpg");
//        album.setArtist("Carlos");
//        album.setUserId(12);

//        userDAO.create(album);
//        System.out.println("User successfully created: " + album);

        // Create another user.
//        Album album2 = new Album();
//
//        album2.setName("Album2");
//        album2.setCoverPath("nocover.jpg");
//        album2.setArtist("Carlos");
//        album2.setUserId(12);
//        userDAO.create(album2);
//        System.out.println("Another user successfully created: " + album2);

//         Update user.
//        album.setArtist("Johanna");
//        userDAO.update(album);
//        System.out.println("User successfully updated: " + album);

        // Update user.
//        user.setFirstName("Foo");
//        user.setLastName("Bar");
//        userDAO.update(album);
//        System.out.println("User successfully updated: " + album);

//         List all albums of a user.
//        List<Album> users = userDAO.listById(14);
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());

//         Delete user.
//        userDAO.delete(album);
//        System.out.println("User successfully deleted: " + album);

        // Check if email exists.
//        boolean exist = userDAO.existAlbum("Album1", 12);
//        System.out.println("This email should not exist anymore, so this should print false: " + exist);

        // Change password.
//        anotherUser.setPassword("newAnotherPassword");
//        userDAO.update(anotherUser);
//        System.out.println("Another user's password successfully changed: " + anotherUser);

        // Get another user by email and password.
//        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
//        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);

        // Delete another user.
//        userDAO.delete(foundAnotherUser);
//        System.out.println("Another user successfully deleted: " + foundAnotherUser);

        // List all users again.
//        users = userDAO.listById();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());

    }
}



