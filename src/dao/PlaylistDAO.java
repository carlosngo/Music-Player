package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.*;
import java.util.ArrayList;

import model.*;


public class PlaylistDAO implements DataAccessObject {

	private DAOFactory db;

	private static final String SQL_FIND_BY_ID =
			"SELECT " + Database.PLAYLIST_COLUMNS +" FROM " + Database.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
	private static final String SQL_INSERT =
			"INSERT INTO " + Database.PLAYLIST_TABLE + " (FK_AccountID, Name, DateCreated) VALUES (?, ?, ?)";
	private static final String SQL_DELETE =
			"DELETE FROM " + Database.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
	private static final String SQL_UPDATE =
			"UPDATE " + Database.PLAYLIST_TABLE + " SET FK_AccountID = ?, Name = ?, DateCreated = ? WHERE PK_PlaylistID = ?";
	private static final String SQL_LIST_BY_ACCOUNT_ID =
			"SELECT " + Database.PLAYLIST_COLUMNS + " FROM " + Database.PLAYLIST_TABLE + " WHERE FK_AccountID = ?";
	private static final String SQL_FIND_BY_PLAYLIST_NAME =
			"SELECT * FROM " + Database.PLAYLIST_TABLE + " WHERE Name = ? AND FK_AccountID = ?";
	private static final String SQL_LIST_BY_PLAYLIST_ID = 
			"SELECT " + Database.PLAYLIST_COLUMNS + " FROM " + Database.PLAYLIST_TABLE;


	public PlaylistDAO(DAOFactory db) {
		this.db = db;
	}

	private Playlist map(ResultSet rs) throws SQLException {
		Playlist playlist = new Playlist();
		UserDAO userDAO = (UserDAO) new UserDAOFactory().createDAO();
		playlist.setPlaylistId(rs.getInt("PK_PlaylistID"));
		User u = userDAO.find(rs.getInt("FK_UserID"));
		playlist.setUser(u);
		//        playlist.setUserId(rs.getInt("FK_UserID"));
		playlist.setName(rs.getString("Name"));
		//        playlist.setFavorite(rs.getBoolean("Favorite"));
		playlist.setDateCreated(rs.getTimestamp("DateCreated"));

		return playlist;
	}

	public Playlist find(int playlistId) {
		return find(SQL_FIND_BY_ID, playlistId);
	}
	
	public Playlist findByName(String playlistName, int userId) {
		return find(SQL_FIND_BY_PLAYLIST_NAME, playlistName, userId);
	}
	
	private Playlist find(String sql, Object... values) {
        Playlist playlist = null;
        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, sql, false, values);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                playlist = map(resultSet);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }
	
	

	public void create(Playlist playlist) throws IllegalArgumentException {
		if (playlist.getPlaylistId() != -1) {
			throw new IllegalArgumentException("Song is already created, the song ID is not null.");
		}

		Object[] values = {
				playlist.getUser().getUserId(),
				playlist.getName(),
				playlist.getDateCreated()
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				playlist.setPlaylistId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating playlist/ failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void delete(Playlist playlist) {
		if (playlist.getPlaylistId() == -1) {
			throw new IllegalArgumentException("Playlist not in database.");
		}

		Object[] values = {
				playlist.getPlaylistId()
		};

		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Deleting user failed, no rows affected.");
			} else {
				playlist.setPlaylistId(-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Playlist playlist) throws IllegalArgumentException {
		if (playlist.getPlaylistId() == -1) {
			throw new IllegalArgumentException("User is not created yet, the user ID is null.");
		}

		Object[] values = {
				playlist.getUser().getUserId(),
				playlist.getName(),
				playlist.getPlaylistId()
		};
		Connection connection = Database.getConnection();

		try (
				PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)) {
			int affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Updating playlist failed, no rows affected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Playlist> listById() {
		ArrayList<Playlist> playlists = new ArrayList<>();

		Object[] values = {
				
		};
		
		Connection connection = Database.getConnection();
		try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_PLAYLIST_ID, false, values);) {
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				playlists.add(map(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return playlists;
	}

	public ArrayList<Playlist> listByUserId(int accId) {
		ArrayList<Playlist> playlists = new ArrayList<>();

		Object[] values = {
				accId
		};
		
		Connection connection = Database.getConnection();
		try (PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ACCOUNT_ID, false, values);) {
			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				playlists.add(map(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return playlists;
	}

	/*    public ArrayList<Playlist> listFavorites(int userId) {
        ArrayList<Playlist> playlists = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_FAVORITES);
            statement.setInt(1, userId);
            statement.setBoolean(2, true);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                playlists.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }*/

	

	public static void main(String[] args) {
		//        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
		//        PlaylistDAO playlistDAO = db.getPlaylistDAO();
		//
		//
		//        //create playlist
		//        Playlist playlist1 = new Playlist();
		//        playlist1.setUserId(12);
		//        playlist1.setName("Playlist#1");
		//        playlist1.setFavorite(true);
		//    	playlistDAO.create(playlist1);
		//
		//        ArrayList<Playlist> list = playlistDAO.listFavorites(12);
		//        System.out.println("List of users successfully queried: " + list);


		//find playlist
		//    	Playlist playlist2 = playlistDAO.find(1);

		//update playlist
		//    	playlist1.setPlaylistId(1);
		//    	playlist1.setUserId(12);
		//    	playlist1.setName("Playlist#1Updated");
		//    	playlist1.setFavorite(false);
		//    	playlistDAO.update(playlist1);
		//    	Playlist playlist1updated = playlistDAO.find(1);

		//delete playlist
		//    	playlistDAO.delete(playlist1);
	}


}
