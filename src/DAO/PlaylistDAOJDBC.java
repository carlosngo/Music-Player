package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Playlist;

public class PlaylistDAOJDBC implements PlaylistDAO {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = 
    		"SELECT " + DAOFactory.PLAYLIST_COLUMNS +" FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_INSERT = 
    		"INSERT INTO " + DAOFactory.PLAYLIST_TABLE + " VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = 
    		"DELETE FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_UPDATE = 
    		"UPDATE " + DAOFactory.PLAYLIST_TABLE + " SET FK_UserID = ?, Name = ?, Favorite = ? WHERE PK_PlaylistID = ?";

    public PlaylistDAOJDBC(DAOFactory db) {
        this.db = db;
    }
    
    private Playlist map(ResultSet rs) throws SQLException {
    	Playlist playlist = new Playlist();
    	
    	playlist.setPlaylistId(rs.getInt("PK_PlaylistID"));
    	playlist.setUserId(rs.getInt("FK_UserID"));
    	playlist.setName(rs.getString("Name"));
    	playlist.setFavorite(rs.getBoolean("Favorite"));
    	
    	return playlist;
    }

    @Override
    public Playlist find(int playlistId) {
    	Playlist playlist = null;
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);    		
    		statement.setInt(1, playlistId);
    		ResultSet rs = statement.executeQuery();
    		if(rs.next()) {
    			playlist = map(rs);
    		}
    		rs.close();
    		statement.close();
    		connection.close();
    		
    		return playlist;
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
		return null;
    }

    @Override
    public void create(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT);    		
    		statement.setInt(1, playlist.getPlaylistId());
    		statement.setInt(2, playlist.getUserId());
    		statement.setString(3, playlist.getName());
    		statement.setBoolean(4, playlist.isFavorite());
    		
    		statement.executeUpdate();
    		statement.close();
    		connection.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void delete(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_DELETE);    		
    		statement.setInt(1, playlist.getPlaylistId());

    		
    		statement.executeUpdate();
    		statement.close();
    		connection.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void update(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);    		
    		statement.setInt(1, playlist.getUserId());
    		statement.setString(2, playlist.getName());
    		statement.setBoolean(3, playlist.isFavorite());
    		statement.setInt(4, playlist.getPlaylistId());
    		
    		statement.executeUpdate();
    		statement.close();
    		connection.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}
