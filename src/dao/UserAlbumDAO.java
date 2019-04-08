package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Album;
import model.User;

public class UserAlbumDAO implements DataAccessObject {
	private DAOFactory db;
	
	private static final String SQL_INSERT = "INSERT INTO " + Database.USERALBUM_TABLE + " (" + Database.USERALBUM_COLUMNS + ") VALUES (?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM " + Database.USERALBUM_TABLE + " WHERE FK_AlbumID = ? AND FK_UserID = ?";
	private static final String SQL_LIST_BY_USER_ID = "SELECT FK_AlbumID FROM " + Database.USERALBUM_TABLE + " WHERE FK_UserID = ?";
	private static final String SQL_LIST_BY_ALBUM_ID = "SELECT FK_UserID FROM " + Database.USERALBUM_TABLE + " WHERE FK_AlbumID = ?";
 	
	public UserAlbumDAO(DAOFactory db) {
		this.db = db;
	}
	
	public void join(User u, Album a) {
		Object[] values = {
				u.getUserId(),
				a.getAlbumId()
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public void separate(User u, Album a) {
		if (u.getUserId() == -1 || a.getAlbumId() == -1)
			return;
		Object[] values = {
				u.getUserId(),
				a.getAlbumId()
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public ArrayList<Integer> listByUserId(int userId) {
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

	public ArrayList<Integer> listBySongId(int albumId) {
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_ALBUM_ID, false, albumId);
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
