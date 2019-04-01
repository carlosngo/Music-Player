package dao;

import java.sql.*;
import java.util.ArrayList;

import model.*;


public class PlaylistDAO implements DataAccessObject {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT " + Database.PLAYLIST_COLUMNS +" FROM " + Database.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_INSERT =
            "INSERT INTO " + Database.PLAYLIST_TABLE + " (FK_UserID, Name, Favorite, DateCreated) VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM " + Database.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + Database.PLAYLIST_TABLE + " SET FK_UserID = ?, Name = ?, Favorite = ? WHERE PK_PlaylistID = ?";
    private static final String SQL_LIST_BY_ID =
            "SELECT " + Database.PLAYLIST_COLUMNS + " FROM " + Database.PLAYLIST_TABLE + " WHERE FK_UserID = ?";
    private static final String SQL_LIST_FAVORITES =
            "SELECT " + Database.PLAYLIST_COLUMNS + " FROM " + Database.PLAYLIST_TABLE + " WHERE FK_UserID = ? AND Favorite = ?";
    private static final String SQL_EXIST_PLAYLIST =
            "SELECT * FROM " + Database.PLAYLIST_TABLE + " WHERE FK_UserID = ? AND Name = ?";

    public PlaylistDAO(DAOFactory db) {
        this.db = db;
    }

    private Playlist map(ResultSet rs) throws SQLException {
        Playlist playlist = new Playlist();
        UserDAO userDAO = (UserDAO) new UserDAOFactory().createDAO();
        playlist.setPlaylistId(rs.getInt("PK_PlaylistID"));
        User u = userDAO.find(rs.getInt("FK_UserID"));
        playlist.setUser(u);
//        playlist.setUserId(rs.getInt("FK_UserID"));
        playlist.setName(rs.getString("Name"));
        playlist.setFavorite(rs.getBoolean("Favorite"));
        playlist.setDateCreated(rs.getTimestamp("DateCreated"));

        return playlist;
    }

    public Playlist find(int playlistId) {
        Playlist playlist = null;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setInt(1, playlistId);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                playlist = map(rs);
            }
            rs.close();
            statement.close();

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    public void create(Playlist playlist) throws IllegalArgumentException {
        if (playlist.getPlaylistId() != -1) {
            throw new IllegalArgumentException("Song is already created, the song ID is not null.");
        }
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, playlist.getUser().getUserId());
            statement.setString(2, playlist.getName());
            statement.setBoolean(3, playlist.isFavorite());
            statement.setTimestamp(4, new Timestamp(playlist.getDateCreated().getTime()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                playlist.setPlaylistId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Playlist playlist) {
        try {
            if (playlist.getPlaylistId() == -1) {
                throw new IllegalArgumentException("Playlist not in database.");
            }
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, playlist.getPlaylistId());


            statement.executeUpdate();
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Playlist playlist) throws IllegalArgumentException {
        try {
            if (playlist.getPlaylistId() == -1) {
                throw new IllegalArgumentException("User is not created yet, the user ID is null.");
            }
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setInt(1, playlist.getUser().getUserId());
            statement.setString(2, playlist.getName());
            statement.setBoolean(3, playlist.isFavorite());
            statement.setInt(4, playlist.getPlaylistId());


            statement.executeUpdate();
            statement.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Playlist> listById(int userId) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                playlists.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public ArrayList<Playlist> listFavorites(int userId) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_FAVORITES);
            statement.setInt(1, userId);
            statement.setBoolean(2, true);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                playlists.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public Playlist findByName(String playlistName, int userId) {
        Playlist playlist = null;
        try{
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_EXIST_PLAYLIST);
            statement.setString(1, playlistName);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                playlist = map(rs);
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    public static void main(String[] args) {
//        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
//        PlaylistDAO playlistDAO = db.getPlaylistDAO();
//
//
//        //create playlist
//        Playlist playlist1 = new Playlist();
//        playlist1.setUserId(12);
//        playlist1.setName("Playlist#1");
//        playlist1.setFavorite(true);
//    	playlistDAO.create(playlist1);
//
//        ArrayList<Playlist> list = playlistDAO.listFavorites(12);
//        System.out.println("List of users successfully queried: " + list);


        //find playlist
//    	Playlist playlist2 = playlistDAO.find(1);

        //update playlist
//    	playlist1.setPlaylistId(1);
//    	playlist1.setUserId(12);
//    	playlist1.setName("Playlist#1Updated");
//    	playlist1.setFavorite(false);
//    	playlistDAO.update(playlist1);
//    	Playlist playlist1updated = playlistDAO.find(1);

        //delete playlist
//    	playlistDAO.delete(playlist1);
    }


}
