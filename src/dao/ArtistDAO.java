package dao;

import model.Account;
import model.Artist;
import model.Song;
import model.User;

import static util.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistDAO implements DataAccessObject {
	private DAOFactory db;

	private static final String SQL_INSERT = "INSERT INTO " + Database.ARTIST_TABLE + " (FK_AccountID, FirstName, LastName, Gender, Birthday, Genre) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + Database.ARTIST_TABLE + " SET FK_AccountID = ?, FirstName = ?, LastName = ?, Gender = ?, Birthday = ?, Genre = ? WHERE PK_ArtistID = ?";
	private static final String SQL_DELETE = "DELETE FROM " + Database.ARTIST_TABLE + " WHERE PK_ArtistID = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE PK_ArtistID = ?";
	private static final String SQL_ORDER_BY_ID = "SELECT * FROM " + Database.ARTIST_TABLE + " ORDER BY PK_ArtistID";
	private static final String SQL_LIST_BY_NAME = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE FirstName LIKE ? OR LastName LIKE ?";
	private static final String SQL_LIST_BY_GENRE = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE genre = ?";
	private static final String SQL_EXIST_USERNAME =
			"SELECT * FROM " + Database.ARTIST_TABLE + " INNER JOIN " + Database.ACCOUNT_TABLE + " ON " + Database.ARTIST_TABLE + ".FK_AccountID = "+
					Database.ACCOUNT_TABLE + ".PK_AccountID WHERE " + Database.ACCOUNT_TABLE + ".Username = ?";
//	private static final String SQL_SEARCH_BY_KEYWORD  = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE CONCAT(FirstName,\" \" , LastName) LIKE ?";
	private static final String SQL_SEARCH_BY_KEYWORD  = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE FirstName LIKE ? OR LastName LIKE ? OR CONCAT(FirstName,' ', LastName) LIKE ?";
	private static final String SQL_FIND_BY_ACCOUNTID = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE FK_AccountID = ?" ;
	private static final String SQL_LIST_BY_FOLLOWED_ARTISTS = 
			"SELECT * FROM " + Database.ARTIST_TABLE + " INNER JOIN " + Database.SUBSCRIPTION_TABLE + " ON "+ Database.ARTIST_TABLE + ".FK_AccountID = " +
					Database.SUBSCRIPTION_TABLE + ".FK_SubscribeeID WHERE FK_SubscriberID = ?";

	public ArtistDAO(DAOFactory db) {
		this.db = db;
	}

	public void create(Artist artist) {
		if (artist.getArtistId() != -1) {
			throw new IllegalArgumentException("Artist is already created, the artist ID is not null.");
		}

		Object[] values = {
				artist.getAccount().getId(),
				artist.getFirstName(),
				artist.getLastName(),
				artist.getGender(),
				artist.getBirthday(),
				artist.getGenre()
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				artist.setArtistId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating artist failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Artist artist) throws IllegalArgumentException {
		if (artist.getArtistId() == -1) {
			throw new IllegalArgumentException("Artist is not created yet, the artist ID is null.");
		}

		Object[] values = {
				artist.getAccount().getId(),
				artist.getFirstName(),
				artist.getLastName(),
				artist.getGender(),
				artist.getBirthday(),
				artist.getGenre(),
				artist.getArtistId()
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Updating artist failed, no rows affected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void delete(Artist artist) {
		Object[] values = {
				artist.getArtistId()
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Deleting artist failed, no rows affected.");
			} else {
				artist.setArtistId(-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Artist find(long userId) {
		return find(SQL_FIND_BY_ID, userId);
	}

	public Artist findByAccountID(long accId) {
		return find(SQL_FIND_BY_ACCOUNTID, accId);
	}

	private Artist find(String sql, Object... values) {
		Artist artist = null;
		Connection connection = Database.getConnection();
		try {
			PreparedStatement statement = prepareStatement(connection, sql, false, values);
			ResultSet rs = statement.executeQuery();
			if(rs.next()) {
				artist = map(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artist;
	}

	public ArrayList<Artist> listById(){
		ArrayList<Artist> artists = new ArrayList<>();
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = connection.prepareStatement(SQL_ORDER_BY_ID);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				artists.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return artists;
	}

	public ArrayList<Artist> listByGenre(String genre){
		ArrayList<Artist> artists = new ArrayList<>();
		Connection connection = Database.getConnection();
		try {

			PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_GENRE);
			statement.setString(1, genre);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				artists.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return artists;
	}

	public ArrayList<Artist> listByName(String name){
		ArrayList<Artist> artists = new ArrayList<>();

		Object[] values = {
				"%" + name + "%",
				"%" + name + "%"
		};

		Connection connection = Database.getConnection();
		try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_NAME, false, values)) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				artists.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return artists;
	}

	public ArrayList<Artist> listByFollowedArtists(int accountId) {
		ArrayList<Artist> artists = new ArrayList<>();

		Object[] values = {
				accountId
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_FOLLOWED_ARTISTS, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				artists.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artists;
	}

	public boolean existUsername(String username) {
		Object[] values = {
				username
		};
		boolean exist = false;
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_EXIST_USERNAME, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			exist = resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

	public Artist map(ResultSet rs) throws SQLException {
		AccountDAO accountDAO = (AccountDAO) new AccountDAOFactory().createDAO();
		Artist artist = new Artist();
		artist.setArtistId(rs.getInt("PK_ArtistID"));
		artist.setAccount(accountDAO.find(rs.getInt("FK_AccountID")));
		artist.setFirstName(rs.getString("FirstName"));
		artist.setLastName(rs.getString("LastName"));
		artist.setGender(rs.getString("Gender"));
		artist.setGenre(rs.getString("Genre"));
		artist.setBirthday(rs.getDate("Birthday"));
		return artist;
	}

	public ArrayList<Artist> search(String name){
		ArrayList<Artist> artists = new ArrayList<>();
		Object[] values = {
				"%" + name + "%",
				"%" + name + "%",
				"%" + name + "%"
		};
		
		Connection connection = Database.getConnection();

		try(PreparedStatement statement = prepareStatement(connection, SQL_SEARCH_BY_KEYWORD, false, values);){
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				artists.add(map(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return artists;
	}

	

}
