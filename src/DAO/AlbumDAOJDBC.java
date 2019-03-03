package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Album;

public class AlbumDAOJDBC implements AlbumDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_INSERT = "INSERT INTO album VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_UPDATE = "UPDATE album SET FK_UserID = ?, name = ?, artist = ?, cover = ? WHERE PK_AlbumID = ?";
    private static final String SQL_LIST_BY_ID = "SELECT * FROM " + DAOFactory.ALBUM_TABLE + " WHERE FK_UserID = ?";
    private static final String SQL_EXIST_ALBUM = "SELECT COUNT(*) FROM " + DAOFactory.ALBUM_TABLE + " WHERE Name = ? AND FK_UserID = ?";
    
    private static final String PATH = "resources/images/";
    public AlbumDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    private Album map(ResultSet rs) throws SQLException {
		Album album = new Album();	
		
		String fileFormat = ".jpg";
		String fileName = rs.getString("Name") + rs.getInt("PK_AlbumID") + fileFormat;
		
		album.setAlbumId(rs.getInt("PK_AlbumID"));
		album.setUserId(rs.getInt("FK_UserID"));
		album.setName(rs.getString("Name"));
		album.setArtist(rs.getString("Artist"));
		album.setFile(rs.getBlob("Cover"));
		album.setCoverPath(fileName);
		
	
		return album;
	}
    
    @Override
    public Album find(int albumId) {
    	Album album = null;
        try {	
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
    		statement.setInt(1, albumId);
    		ResultSet rs = statement.executeQuery();
    		
    		if(rs.next()) {
    			album = map(rs);
    		}
    		
    		rs.close();
    		statement.close();
    		connection.close();
    		return album;
        }catch(SQLException e) {
        	e.printStackTrace();
        	return null;
        }
    }

    @Override
    public void create(Album album) {
    	try {
    		File file = new File(album.getCoverPath());
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
    		
    		statement.setInt(1, album.getAlbumId());
    		statement.setInt(2, album.getUserId());
    		statement.setString(3, album.getName());
    		statement.setString(4, album.getArtist());
    		statement.setBinaryStream(5, new FileInputStream(file));
    		statement.executeUpdate();
    		
    		statement.close();
    		connection.close();
    	}catch(SQLException | FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void delete(Album album) {
		
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
			
			statement.setInt(1, album.getAlbumId());
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
    }

    @Override
    public void update(Album album) {
    	try {
    		File file = new File(album.getCoverPath());
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

			statement.setInt(1, album.getUserId());
			statement.setString(2, album.getName());
			statement.setString(3, album.getArtist());
			statement.setBinaryStream(4, new FileInputStream(file));
			statement.setInt(5, album.getAlbumId());
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	@Override
	public ArrayList<Album> listById(int userId) {
		ArrayList<Album> albums = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
			statement.setInt(1, userId);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				albums.add(map(rs));
			}
			
			return albums;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean existAlbum(String albumName, int userId) {
		int count = 0;
		try{
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_EXIST_ALBUM);
			statement.setString(1, albumName);
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
}
