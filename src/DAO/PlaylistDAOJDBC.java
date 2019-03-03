package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Result;

import Model.Album;
import Model.Playlist;

public class PlaylistDAOJDBC implements PlaylistDAO {

    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = 
    		"SELECT " + DAOFactory.PLAYLIST_COLUMNS +" FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_INSERT = 
    		"INSERT INTO " + DAOFactory.PLAYLIST_TABLE + " VALUES (?, ?, ?, ?)";
    private static final String SQL_DELETE = 
    		"DELETE FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE PK_PlaylistID = ?";
    private static final String SQL_UPDATE = 
    		"UPDATE " + DAOFactory.PLAYLIST_TABLE + " SET FK_UserID = ?, Name = ?, Favorite = ? WHERE PK_PlaylistID = ?";
    private static final String SQL_LIST_BY_ID = 
    		"SELECT " + DAOFactory.PLAYLIST_COLUMNS + " FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE FK_UserID = ?";
    private static final String SQL_LIST_FAVORITES =
    		"SELECT " + DAOFactory.PLAYLIST_COLUMNS + " FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE FK_UserID = ? AND Favorite = ?";
    private static final String SQL_EXIST_PLAYLIST =
    		"SELECT COUNT(*) FROM " + DAOFactory.PLAYLIST_TABLE + " WHERE FK_UserID = ? AND Name = ?"; 
    public PlaylistDAOJDBC(DAOFactory db) {
        this.db = db;
    }
    
    private Playlist map(ResultSet rs) throws SQLException {
    	Playlist playlist = new Playlist();
    	
    	playlist.setPlaylistId(rs.getInt("PK_PlaylistID"));
    	playlist.setUserId(rs.getInt("FK_UserID"));
    	playlist.setName(rs.getString("Name"));
    	playlist.setFavorite(rs.getBoolean("Favorite"));
    	
    	return playlist;
    }

    @Override
    public Playlist find(int playlistId) {
    	Playlist playlist = null;
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);    		
    		statement.setInt(1, playlistId);
    		ResultSet rs = statement.executeQuery();
    		if(rs.next()) {
    			playlist = map(rs);
    		}
    		rs.close();
    		statement.close();
    		
    		return playlist;
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
		return null;
    }

    @Override
    public void create(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT);    		
    		statement.setInt(1, playlist.getPlaylistId());
    		statement.setInt(2, playlist.getUserId());
    		statement.setString(3, playlist.getName());
    		statement.setBoolean(4, playlist.isFavorite());
    		
    		statement.executeUpdate();
    		statement.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void delete(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_DELETE);    		
    		statement.setInt(1, playlist.getPlaylistId());

    		
    		statement.executeUpdate();
    		statement.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void update(Playlist playlist) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);    		
    		statement.setInt(1, playlist.getUserId());
    		statement.setString(2, playlist.getName());
    		statement.setBoolean(3, playlist.isFavorite());
    		statement.setInt(4, playlist.getPlaylistId());
    		
    		statement.executeUpdate();
    		statement.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
	public ArrayList<Playlist> listById(int userId) {
    	ArrayList<Playlist> playlists = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				playlists.add(map(rs));
			}
			
			return playlists;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Playlist> listFavorites(int userId) {
		ArrayList<Playlist> playlists = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_LIST_FAVORITES);
			statement.setInt(1, userId);
			statement.setBoolean(2, true);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				playlists.add(map(rs));
			}
			return playlists;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean existPlaylist(String playlistName, int userId) {
		int count = 0;
		try{
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_EXIST_PLAYLIST);
			statement.setString(1, playlistName);
			statement.setInt(2, userId);
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt(1) > 0) {
					return true;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
    
    public static void main(String[] args) {
        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
        PlaylistDAO playlistDAO = db.getPlaylistDAO();
        
        
        //create playlist
        Playlist playlist1 = new Playlist();
    	playlist1.setPlaylistId(1);
    	playlist1.setUserId(1);
    	playlist1.setName("Playlist#1");
    	playlist1.setFavorite(true);
    	playlistDAO.create(playlist1);
    	
    	
    	
    	//find playlist
    	Playlist playlist2 = playlistDAO.find(1);
    	
    	//update playlist
    	playlist1.setPlaylistId(1);
    	playlist1.setUserId(1);
    	playlist1.setName("Playlist#1Updated");
    	playlist1.setFavorite(false);
    	playlistDAO.update(playlist1);
    	Playlist playlist1updated = playlistDAO.find(1);
    	
    	//delete playlist
    	playlistDAO.delete(playlist1updated);
    }

	
}
