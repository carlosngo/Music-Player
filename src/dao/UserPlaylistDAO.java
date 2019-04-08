package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Playlist;
import model.Song;
import model.User;

public class UserPlaylistDAO implements DataAccessObject {
	
	private static String SQL_LIST_BY_USER_ID = "SELECT FK_PlaylistID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_UserID = ?";
    private static String SQL_LIST_BY_PLAYLIST_ID = "SELECT FK_UserID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_PlaylistID = ?";
    private static String SQL_INSERT =
            "INSERT INTO " + Database.USERPLAYLIST_TABLE + " (" + Database.USERPLAYLIST_COLUMNS + ") VALUES (?, ?)";
    private static String SQL_DELETE =
            "DELETE FROM " + Database.USERPLAYLIST_TABLE + " WHERE FK_PlaylistID = ? AND FK_UserID = ?";
    
    private static String SQL_LIST_BY_FAVORITE = "";
	
	private DAOFactory db;
	
	public UserPlaylistDAO(DAOFactory db) {
        this.db = db;
    }
	
	 public void join(Playlist p, User u) {
	        Object[] values = {
	                p.getPlaylistId(),
	                u.getUserId()
	        };
	        try {
	            Connection con = Database.getConnection();

	            PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	        }
	    }

	    public void separate(Playlist p, User u) {
	        if (p.getPlaylistId() == -1 || u.getUserId() == -1)
	            return;
	        Object[] values = {
	                p.getPlaylistId(),
	                u.getUserId()
	        };
	        try {
	            Connection con = Database.getConnection();

	            PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
	            stmt.executeUpdate();

	        } catch (SQLException e) {
	        }
	    }

	    public ArrayList<Integer> listByPlaylistId(int playlistId) {
	        ArrayList<Integer> keys = new ArrayList<>();
	        Connection con = Database.getConnection();
	        try (
	             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_PLAYLIST_ID, false, playlistId);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                keys.add(rs.getInt(1));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return keys;
	    }

	    public ArrayList<Integer> listBySongId(int userId) {
	        ArrayList<Integer> keys = new ArrayList<>();
	        Connection con = Database.getConnection();
	        try (
	             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_USER_ID, false, userId);
	             ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                keys.add(rs.getInt(1));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return keys;
	    }
	    
	    
	    
	
}
