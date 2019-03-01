package DAO;

import Model.Song;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SongDAOJDBC implements SongDAO {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID = 
    		"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_LIST_BY_GENRE = 
    		"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY genre";
    private static final String SQL_LIST_BY_ALBUM = 
    		"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY album";
    private static final String SQL_LIST_BY_YEAR = 
    		"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY year";
    private static final String SQL_GET_FILE_BY_ID = "";
    private static final String SQL_INSERT = 
    		"INSERT INTO " + DAOFactory.SONG_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = 
    		"DELETE FROM " + DAOFactory.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_UPDATE = 
    		"UPDATE " + DAOFactory.SONG_TABLE + " SET FK_UserID = ?, FK_AlbumID = ?, FK_GenreID = ?, Name = ?, Year = ?, Favorite = ?, PlayTime = ?, LastPlayed = ?, File = ? WHERE PK_SongID = ?";

    public SongDAOJDBC(DAOFactory db) { this.db = db; }
    
    private Song map(ResultSet rs) throws SQLException{
    	Song song = new Song();
    	
    	String fileName = rs.getString("Name") + rs.getInt("PK_SongID");
    	
    	song.setSongId(rs.getInt("PK_SongID"));
    	song.setUserId(rs.getInt("FK_UserID"));
    	song.setAlbumId(rs.getInt("FK_AlbumID"));
    	song.setGenreId(rs.getInt("FK_GenreID"));
    	song.setName(rs.getString("Name"));
    	song.setYear(rs.getInt("Year"));
    	song.setFavorite(rs.getBoolean("Favorite"));
    	song.setPlayTime(rs.getLong("PlayTime"));
    	song.setLastPlayed(rs.getDate("LastPlayed"));
    	song.setFileName(fileName);
    	return song;
    }
   
    @Override
    public Song find(int id) {
    	
    	return null;
    }

    @Override
    public ArrayList<Song> findByGenre() {
        return null;
    }

    @Override
    public ArrayList<Song> findByAlbum() {
        return null;
    }

    @Override
    public ArrayList<Song> findByYear() {
        return null;
    }

    public File getFileById(int songId) {
        return null;
    }

    @Override
    public void create(Song song) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
    		
    		statement.setInt(1, song.getSongId());
    		statement.setInt(2, song.getUserId());
    		statement.setInt(3, song.getAlbumId());
    		statement.setInt(4, song.getGenreId());
    		statement.setString(5, song.getName());
    		statement.setInt(6, song.getYear());
    		statement.setBoolean(7, song.isFavorite());
    		statement.setLong(8, song.getPlayTime());
    		statement.setDate(9, (Date) song.getLastPlayed());
    		statement.setBinaryStream(10, new FileInputStream(new File(song.getPath())));
    		
    		statement.executeUpdate();
    		
    		statement.close();
    		connection.close();
    		
    	}catch(SQLException | FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void delete(Song song) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
    		
    		statement.setInt(1, song.getSongId());
    		
    		statement.close();
    		connection.close();
    		
    	}catch(SQLException e) {
    		e.printStackTrace();
    		
    	}
    }

    @Override
    public void update(Song song) {
    	try {
    		Connection connection = db.getConnection();
    		PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
    		
    		statement.setInt(1, song.getUserId());
    		statement.setInt(2, song.getAlbumId());
    		statement.setInt(3, song.getGenreId());
    		statement.setString(4, song.getName());
    		statement.setInt(5, song.getYear());
    		statement.setBoolean(6, song.isFavorite());
    		statement.setLong(7, song.getPlayTime());
    		statement.setDate(8, (Date) song.getLastPlayed());
    		statement.setBinaryStream(9, new FileInputStream(new File(song.getPath())));
    		statement.setInt(10, song.getSongId());
    		
    		statement.executeUpdate();
    		
    		statement.close();
    		connection.close();
    		
    	}catch(SQLException | FileNotFoundException e) {
    		e.printStackTrace();
    	}
    }
}
