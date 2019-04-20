package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Album;
import model.User;

public class AccountAlbumDAO implements DataAccessObject {
	private DAOFactory db;
	
	private static final String SQL_INSERT = "INSERT INTO " + Database.ACCOUNTALBUM_TABLE + " (" + Database.ACCOUNTALBUM_COLUMNS + ") VALUES (?, ?, ?)";
	private static final String SQL_DELETE = "DELETE FROM " + Database.ACCOUNTALBUM_TABLE + " WHERE FK_AccountID = ? AND FK_AlbumID = ?";
	private static final String SQL_LIST_BY_ACCOUNT_ID = "SELECT FK_AlbumID FROM " + Database.ACCOUNTALBUM_TABLE + " WHERE FK_AccountID = ?";
	private static final String SQL_LIST_BY_ALBUM_ID = "SELECT FK_AccountID FROM " + Database.ACCOUNTALBUM_TABLE + " WHERE FK_AlbumID = ?";
	private static String SQL_FIND = 
			"SELECT * FROM " + Database.ACCOUNTALBUM_TABLE + " WHERE FK_AccountID = ? AND FK_AlbumID = ?";
 	
	public AccountAlbumDAO(DAOFactory db) {
		this.db = db;
	}
	
	public void join(Account acc, Album a) {
		Object[] values = {
				acc.getId(),
				a.getAlbumId(),
				null
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public void separate(Account acc, Album a) {
		if (acc.getId() == -1 || a.getAlbumId() == -1)
			return;
		Object[] values = {
				acc.getId(),
				a.getAlbumId()
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public ArrayList<Integer> listByAccountId(int accId) {
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
	
	public boolean find(int accountId, int albumId) {
		Object[] values = {
			accountId,
			albumId
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
