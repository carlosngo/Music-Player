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

public class AccountSongDAO implements DataAccessObject {
	private DAOFactory db;

	private static String SQL_LIST_BY_SONG_ID = "SELECT FK_UserID FROM " + Database.ACCOUNTSONG_TABLE + " WHERE FK_SongID = ?";
	private static String SQL_LIST_BY_USER_ID = "SELECT FK_SongID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_UserID = ?";
	private static String SQL_INSERT =
			"INSERT INTO " + Database.ACCOUNTSONG_TABLE + " (" + Database.ACCOUNTSONG_COLUMNS + ") VALUES (?, ?, ?, ?, ?)";
	private static String SQL_DELETE =
			"DELETE FROM " + Database.ACCOUNTSONG_TABLE + " WHERE FK_UserID = ? AND FK_SongID = ?";

	public AccountSongDAO(DAOFactory db) {
		this.db = db;
	}

	public void join(User u, Song s) {
		Object[] values = {
				u.getUserId(),
				s.getSongId(),
				false,
				null,
				null
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public void separate(User u, Song s) {
		if (u.getUserId() == -1 || s.getSongId() == -1)
			return;
		Object[] values = {
				u.getUserId(),
				s.getSongId()
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

	public ArrayList<Integer> listBySongId(int songId) {
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_SONG_ID, false, songId);
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
