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

public class AccountPlaylistDAO implements DataAccessObject {

	private static String SQL_LIST_BY_ACCOUNT_ID = "SELECT FK_PlaylistID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_AccountID = ?";
	private static String SQL_LIST_BY_PLAYLIST_ID = "SELECT FK_AccountID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_PlaylistID = ?";
	private static String SQL_INSERT =
			"INSERT INTO " + Database.ACCOUNTPLAYLIST_TABLE + " (" + Database.ACCOUNTPLAYLIST_COLUMNS + ") VALUES (?, ?, ?)";
	private static String SQL_DELETE =
			"DELETE FROM " + Database.ACCOUNTPLAYLIST_TABLE + " WHERE FK_AccountID = ? AND FK_PlaylistID = ?";
	private static String SQL_LIST_BY_USER_FAVORITE = "SELECT * FROM " + Database.ACCOUNTPLAYLIST_TABLE + " WHERE FK_AccountID = ? AND isFavorite = ?";

	private static String SQL_FAVORITE = 
			"SELECT isFavorite FROM " + Database.ACCOUNTPLAYLIST_TABLE + " WHERE FK_AccountID = ? AND FK_PlaylistID = ?";
	
	private static String SQL_UPDATE_IS_FAVORITE = 
			"UPDATE " + Database.ACCOUNTPLAYLIST_TABLE + " SET isFavorite = ? WHERE FK_AccountID = ? AND FK_PlaylistID = ?";

	private static String SQL_FIND = 
			"SELECT * FROM " + Database.ACCOUNTPLAYLIST_TABLE + " WHERE FK_AccountID = ? AND FK_PlaylistID = ?";
	
	
	private DAOFactory db;

	public AccountPlaylistDAO(DAOFactory db) {
		this.db = db;
	}

	public void join(Account acc, Playlist p) {
		Object[] values = {
				acc.getId(),
				p.getPlaylistId(),
				false
		};
		try {
			Connection con = Database.getConnection();
			PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public void separate(Account acc, Playlist p) {
		if (p.getPlaylistId() == -1 || acc.getId() == -1)
			return;
		Object[] values = {
				acc.getId(),
				p.getPlaylistId()
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

	public ArrayList<Integer> listBySongId(int accId) {
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

	public ArrayList<Integer> listByUserFavorites(int accId){
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_USER_FAVORITE, false, accId, true);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				keys.add(rs.getInt(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return keys;
	}
	
	public boolean isFavorite(int accountId, int playlistId) {
		Object[] values = {
				accountId,
				playlistId
		};
		boolean colFavorite = false;
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_FAVORITE, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next() && resultSet.getInt(1) == 1) colFavorite = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colFavorite;
	}
	
	public void toggleFavorite(int accountId, int playlistId) {
		Object[] values = {
				!isFavorite(accountId, playlistId),
				accountId,
				playlistId
		};
		
		try {
            Connection con = Database.getConnection();

            PreparedStatement stmt = prepareStatement(con, SQL_UPDATE_IS_FAVORITE, false, values);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
		
	}

	public boolean find(int accountId, int playlistId) {
		Object[] values = {
			accountId,
			playlistId
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
}
