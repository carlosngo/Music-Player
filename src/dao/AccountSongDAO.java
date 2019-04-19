package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Playlist;
import model.Song;
import model.User;

public class AccountSongDAO implements DataAccessObject {
	private DAOFactory db;

	private static String SQL_LIST_BY_SONG_ID = "SELECT FK_AccountID FROM " + Database.ACCOUNTSONG_TABLE + " WHERE FK_SongID = ?";
	private static String SQL_LIST_BY_ACCOUNT_ID = "SELECT FK_SongID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_AccountID = ?";
	private static String SQL_INSERT =
			"INSERT INTO " + Database.ACCOUNTSONG_TABLE + " (" + Database.ACCOUNTSONG_COLUMNS + ") VALUES (?, ?, ?, ?, ?)";
	private static String SQL_DELETE =
			"DELETE FROM " + Database.ACCOUNTSONG_TABLE + " WHERE FK_AccountID = ? AND FK_SongID = ?";
	
	private static String SQL_FAVORITE = 
			"SELECT isFavorite FROM " + Database.ACCOUNTSONG_TABLE + "WHERE FK_AccountID = ? AND FK_SongID = ?";
	
	private static String SQL_UPDATE_IS_FAVORITE = 
			"UPDATE " + Database.ACCOUNTSONG_TABLE + "SET isFavorite = ? WHERE FK_AccountID = ? AND FK_SongID = ?";
	
	private static String SQL_FIND = 
			"SELECT * FROM " + Database.ACCOUNTSONG_TABLE + "WHERE FK_AccountID = ? AND FK_SongID = ?";
	
	private static String SQL_UPDATE = 
			"UPDATE " + Database.ACCOUNTSONG_TABLE + "SET playTime = playTime + 1, LastPlayed = CURRENT_TIMESTAMP() WHERE FK_AccountID = ? AND FK_SongID = ?";
	

	public AccountSongDAO(DAOFactory db) {
		this.db = db;
	}

	public void join(Account acc, Song s) {
		Object[] values = {
				acc.getId(),
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

	public void separate(Account acc, Song s) {
		if (acc.getId() == -1 || s.getSongId() == -1)
			return;
		Object[] values = {
				acc.getId(),
				s.getSongId()
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public ArrayList<Integer> listByUserId(int accId) {
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_ACCOUNT_ID, false, accId);
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
	
	public boolean isFavorite(int accountId, int songId) {
		Object[] values = {
				accountId,
				songId
		};
		boolean colFavorite = false;
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_FAVORITE, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			colFavorite = resultSet.getBoolean(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colFavorite;
	}
	
	public void toggleFavorite(int accountId, int songId) {
		Object[] values = {
				!isFavorite(accountId, songId),
				accountId,
				songId
		};
		
		try {
            Connection con = Database.getConnection();

            PreparedStatement stmt = prepareStatement(con, SQL_UPDATE_IS_FAVORITE, false, values);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
		
	}
	
	public boolean find(int accountId, int songId) {
		Object[] values = {
			accountId,
			songId
		};
		
		boolean found = false;
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_FIND, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			found = resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return found;
	}
	
	public void playSong(int accountId, int songId) {
	
		Object[] values = {
				accountId,
				songId
		};
		
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values);
				ResultSet resultSet = statement.executeQuery()) {
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
}	
