package DAO;

import Model.Album;
import Model.Song;
import static DAO.DAOUtil.*;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.io.*;


public class SongDAOJDBC implements SongDAO {
	private DAOFactory db;

	private static final String SQL_FIND_BY_ID = 
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " WHERE PK_SongID = ?";
	private static final String SQL_FIND_BY_GENRE = 
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY FK_GenreID";
	private static final String SQL_FIND_BY_ALBUM = 
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY FK_AlbumID";
	private static final String SQL_FIND_BY_YEAR = 
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " ORDER BY year";
	private static final String SQL_INSERT = 
			"INSERT INTO " + DAOFactory.SONG_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = 
			"DELETE FROM " + DAOFactory.SONG_TABLE + " WHERE PK_SongID = ?";
	private static final String SQL_UPDATE = 
			"UPDATE " + DAOFactory.SONG_TABLE + " SET FK_UserID = ?, FK_AlbumID = ?, FK_GenreID = ?, Name = ?, Year = ?, Favorite = ?, PlayTime = ?, LastPlayed = ? WHERE PK_SongID = ?";
	private static final String SQL_LIST_BY_GENRE =
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " WHERE FK_GenreID = ? AND FK_UserID = ?";
	private static final String SQL_LIST_BY_ALBUM =
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " WHERE FK_AlbumID = ? AND FK_UserID = ?";
	private static final String SQL_LIST_BY_FAVORITE =
			"SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " WHERE Favorite = ? AND FK_UserID = ?";
	private static final String SQL_LIST_BY_PLAYLIST =
            "SELECT " + DAOFactory.SONG_COLUMNS + " FROM " + DAOFactory.SONG_TABLE + " INNER JOIN " + DAOFactory.PLAYLISTSONG_TABLE + " ON " + DAOFactory.SONG_TABLE + ".PK_SongID = " + DAOFactory.PLAYLISTSONG_TABLE + ".FK_SongID " +
                    "INNER JOIN " + DAOFactory.PLAYLIST_TABLE + " ON " + DAOFactory.PLAYLISTSONG_TABLE + ".FK_PlaylistID = " + DAOFactory.PLAYLIST_TABLE + ".PK_PlaylistID WHERE " + DAOFactory.SONG_TABLE + ".FK_UserID = ? AND " + DAOFactory.PLAYLIST_TABLE + ".PK_PlaylistID = ?";
 	private static final String PATH =
			"resources/music/";

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
		song.setFile(rs.getBlob("File"));
		song.setFileName(fileName);
		return song;
	}

	@Override
	public Song find(int id) {
		Song song = null;
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);

			statement.setInt(1, id);

			ResultSet rs = statement.executeQuery();

			if(rs.next()) {
				song = map(rs);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Song> findByGenre() {
		ArrayList<Song> songs = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_GENRE);


			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				songs.add(map(rs));
			}
			
			return songs;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;    
	}

	@Override
	public ArrayList<Song> findByAlbum() {
		ArrayList<Song> songs = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ALBUM);


			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				songs.add(map(rs));
			}
			
			return songs;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;    
	}

	@Override
	public ArrayList<Song> findByYear() {
		ArrayList<Song> songs = new ArrayList<>();
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_YEAR);


			ResultSet rs = statement.executeQuery();

			while(rs.next()) {
				songs.add(map(rs));
			}
			
			return songs;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;    
	}

    @Override
    public ArrayList<Song> listByGenre(int genreId, int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_GENRE);
            statement.setInt(1, genreId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }

            return songs;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Song> listByPlaylist(int playlistId, int userId) {
        // TODO Auto-generated method stub
        Object[] values = {
                userId,
                playlistId
        };
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_PLAYLIST, false, values);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(map(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    @Override
    public ArrayList<Song> listByAlbum(int albumId, int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ALBUM);
            statement.setInt(1, albumId);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }

            return songs;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Song> listFavorites(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = db.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_FAVORITE);
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }

            return songs;
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
	public void create(Song song) {
        if (song.getSongId() != -1) {
            throw new IllegalArgumentException("Song is already created, the song ID is not null.");
        }
		try {
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

			statement.setInt(1, song.getUserId());
			statement.setInt(2, song.getAlbumId());
			statement.setInt(3, song.getGenreId());
			statement.setString(4, song.getName());
			statement.setInt(5, song.getYear());
			statement.setBoolean(6, song.isFavorite());
			statement.setLong(7, song.getPlayTime());
			statement.setDate(8, (Date) song.getLastPlayed());
            URL resource = getClass().getClassLoader().getResource("songs/" + song.getFileName());
            File wav = Paths.get(resource.toURI()).toFile();
			statement.setBinaryStream(9, new FileInputStream(wav));
			statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                song.setSongId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }

		}catch(SQLException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
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

		}catch(SQLException e) {
			e.printStackTrace();

		}
	}

	@Override
	public void update(Song song) {
		try {
            if (song.getSongId() == -1) {
                throw new IllegalArgumentException("User is not created yet, the user ID is null.");
            }
			Connection connection = db.getConnection();
			PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

			statement.setInt(1, song.getUserId());
			statement.setInt(2, song.getAlbumId());
			statement.setInt(3, song.getGenreId());
			statement.setString(4, song.getName());
			statement.setInt(5, song.getYear());
			statement.setBoolean(6, song.isFavorite());
			statement.setLong(7, song.getPlayTime());
			statement.setDate(8, (Date) song.getLastPlayed());
			statement.setInt(9, song.getSongId());

			statement.executeUpdate();

			statement.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		  DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "zerovit098");
	      SongDAO songDAO = db.getSongDAO();
	      
	      //create song
	      Song song = new Song();
	      song.setSongId(1);
	      song.setUserId(1);
	      song.setAlbumId(1);
	      song.setGenreId(1);
	      song.setName("Song#1");
	      song.setYear(2019);
	      song.setFavorite(true);
	      song.setPlayTime(123);
	      song.setLastPlayed(new Date(109, 2, 2));
	      song.setFileName("/hello.wav");
	      songDAO.create(song);
	      
	      //find song
	      Song songFind = songDAO.find(1);
	      
	      //update song
	      song.setName("Song#1Updated");
	      song.setYear(2012);
	      song.setFavorite(false);
	      song.setPlayTime(521);
	      song.setLastPlayed(new Date(119, 2, 2));
	      songDAO.update(song);
	      
	      //find by album
	      ArrayList<Song> songs;
	      songs = songDAO.findByAlbum();
	      
	      //find by genre
	      songs = songDAO.findByGenre();
	      
	      //find by year
	      songs = songDAO.findByYear();
	      
	      //delete song
	      songDAO.delete(song);
	}

	
}
