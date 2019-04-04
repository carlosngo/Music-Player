package dao;

import model.Artist;

import static util.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ArtistDAO implements DataAccessObject {
	private DAOFactory db;

	private static final String SQL_INSERT = "INSERT INTO " + Database.ARTIST_TABLE + " (Name, Genre) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE " + Database.ARTIST_TABLE + " SET Name = ?, Genre = ? WHERE PK_ArtistID = ?";
	private static final String SQL_DELETE = "DELETE FROM " + Database.ARTIST_TABLE + " WHERE PK_ArtistID = ?";
	private static final String SQL_FIND_BY_ID = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE PK_ArtistID = ?";
	private static final String SQL_ORDER_BY_ID = "SELECT * FROM " + Database.ARTIST_TABLE + " ORDER BY PK_ArtistID";
	private static final String SQL_LIST_BY_NAME = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE Name = ?";
	private static final String SQL_LIST_BY_GENRE = "SELECT * FROM " + Database.ARTIST_TABLE + " WHERE genre = ?";

	public ArtistDAO(DAOFactory db) {
		this.db = db;
	}

	public void create(Artist artist) {
		if (artist.getArtistId() != -1) {
			throw new IllegalArgumentException("Artist is already created, the artist ID is not null.");
		}

		Object[] values = {
				artist.getName(),
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
				artist.getName(),
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


	public Artist find(String sql, Object... values) {
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
		Connection connection = Database.getConnection();
		try {

			PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_NAME);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				artists.add(map(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return artists;
	}

	public boolean existUsername(String username) {
		return false;
	}

	public Artist map(ResultSet rs) throws SQLException {
		Artist artist = new Artist();

		artist.setArtistId(rs.getInt("PK_ArtistID"));
		artist.setName(rs.getString("Name"));
		artist.setGenre(rs.getString("Genre"));

		return artist;
	}



}
