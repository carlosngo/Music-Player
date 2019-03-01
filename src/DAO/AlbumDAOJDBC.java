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

import Model.Album;

public class AlbumDAOJDBC implements AlbumDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = "SELECT * FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_INSERT = "INSERT INTO album VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM album WHERE PK_AlbumID = ?";
    private static final String SQL_UPDATE = "UPDATE album SET FK_UserID = ?, name = ?, artist = ?, cover = ? WHERE PK_AlbumID = ?";

    public AlbumDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    private Album toAlbum(ResultSet rs) throws SQLException, IOException {
		Album album = new Album();	
		
		String fileFormat = ".jpg";
		String path =  "/Desktop/"+ rs.getString(3) + "_" + rs.getInt(1) + fileFormat;
		File file = new File(path);
		
		album.setAlbumId(rs.getInt(1));
		album.setUserId(rs.getInt(2));
		album.setName(rs.getString(3));
		album.setArtist(rs.getString(4));
		album.setCoverPath(path);
		
		byte[] buffer = new byte[1024];
		InputStream input = rs.getBinaryStream(5);
		FileOutputStream output = new FileOutputStream(file);
		while(input.read(buffer) > 0) {
			output.write(buffer);
		}
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
    			album = toAlbum(rs);
    		}
    		rs.close();
    		statement.close();
    		connection.close();
    		System.out.println("[ALBUM] SELECT SUCCESS!");
    		return album;
        }catch(SQLException | IOException e) {
        	System.out.println("[ALBUM] SELECT FAILED!");
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
    		System.out.println("[ALBUM] INSERT SUCCESS!");
    	}catch(SQLException | FileNotFoundException e) {
    		System.out.println("[ALBUM] INSERT FAILED!");
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
			System.out.println("[ALBUM] DELETE SUCCESS!");
		} catch (SQLException e) {
			System.out.println("[ALBUM] DELETE FAILED!");
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
			System.out.println("[ALBUM] UPDATE SUCCESS!");
		} catch (SQLException | FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("[ALBUM] UPDATE FAILED!");
		}
    }
}
