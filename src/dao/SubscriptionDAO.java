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

public class SubscriptionDAO implements DataAccessObject {
	private DAOFactory db;

	private static final String SQL_INSERT =
			"INSERT INTO " + Database.SUBSCRIPTION_TABLE + " (FK_SubscriberID, FK_SubscribeeID) VALUES (?, ?)";
	private static final String SQL_DELETE =
			"DELETE FROM " + Database.SUBSCRIPTION_TABLE + " WHERE FK_SubscriberID = ? AND FK_SubscribeeID = ?";
	private static final String SQL_LIST_BY_SUBSCRIBER_ID =
			"SELECT FK_SubscribeeID FROM " + Database.SUBSCRIPTION_TABLE + " WHERE FK_SubscriberID = ?";
	private static final String SQL_LIST_BY_SUBSCRIBEE_ID =
			"SELECT FK_SubscriberID FROM " + Database.SUBSCRIPTION_TABLE + " WHERE FK_SubscribeeID = ?";
	
	private static String SQL_FIND = 
			"SELECT * FROM " + Database.SUBSCRIPTION_TABLE + " WHERE FK_SubcriberID = ? AND FK_SubscribeeID = ?";
	
	public SubscriptionDAO(DAOFactory db) {
		this.db = db;
	}

	public void join(Account subscriber, Account subscribee) {
		Object[] values = {
				subscriber.getId(),
				subscribee.getId()
		};
		try {
			Connection con = Database.getConnection();
			PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
			stmt.executeUpdate();
		} catch (SQLException e) {
		}
	}

	public void separate(Account subscriber, Account subscribee) {
		if (subscriber.getId() == -1 || subscribee.getId() == -1)
			return;
		Object[] values = {
				subscriber.getId(),
				subscribee.getId()
		};
		try {
			Connection con = Database.getConnection();

			PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
			stmt.executeUpdate();

		} catch (SQLException e) {
		}
	}

	public ArrayList<Integer> listBySubscriberId(int subscriberId) {
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_SUBSCRIBER_ID , false, subscriberId);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				keys.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return keys;
	}

	public ArrayList<Integer> listBySubscribeeId(int subscribeeId) {
		ArrayList<Integer> keys = new ArrayList<>();
		Connection con = Database.getConnection();
		try (
				PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_SUBSCRIBEE_ID, false, subscribeeId);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				keys.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return keys;
	}
	
	public boolean find(int subscriberId, int subscribeeId) {
		Object[] values = {
			subscriberId,
			subscribeeId
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
