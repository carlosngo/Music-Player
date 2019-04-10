package dao;

import model.Album;
import model.*;
import util.BlobParser;
import util.BlobToFile;

import static util.DAOUtil.*;

import java.sql.*;
import java.util.*;

import com.mysql.cj.jdbc.util.ResultSetUtil;

import java.io.*;


public class SongDAO implements DataAccessObject {
    private DAOFactory db;

    private static final String SQL_INSERT =
    		"INSERT INTO " + Database.SONG_TABLE + " (FK_ArtistID, FK_AlbumID, Name, Genre, Year, File) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE =
    		"DELETE FROM " + Database.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_UPDATE =
    		"UPDATE " + Database.SONG_TABLE + " SET FK_ArtistID = ?, FK_AlbumID = ?, Name = ?, Genre = ?, Year = ? WHERE PK_SongID = ?";
    private static final String SQL_FIND_BY_ID =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_FIND_BY_ARTIST_ID_ORDER_BY_GENRE =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_ArtistID = ? ORDER BY FK_GenreID";
    private static final String SQL_FIND_BY_ARTIST_ID_ORDER_BY_ALBUM =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_ArtistID = ? ORDER BY FK_AlbumID";
    private static final String SQL_FIND_BY_ARTIST_ID_ORDER_BY_YEAR =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_ArtistID = ? ORDER BY year";
    private static final String SQL_LIST_BY_ID =
    		"SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " ORDER BY PK_SongID";
    private static final String SQL_LIST_BY_ARTIST_ID = 
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE FK_ArtistID = ?";
    private static final String SQL_LIST_BY_GENRE =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE Genre LIKE ?";
    private static final String SQL_LIST_BY_ALBUM =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_AlbumID = ?";
   // private static final String SQL_LIST_BY_FAVORITE =
    //        "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE Favorite = ? AND FK_ArtistID = ?";
    private static final String SQL_LIST_BY_PLAYLIST =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " INNER JOIN " + Database.PLAYLISTSONG_TABLE + " ON " + Database.SONG_TABLE + ".PK_SongID = " + Database.PLAYLISTSONG_TABLE + ".FK_SongID " +
                    "INNER JOIN " + Database.PLAYLIST_TABLE + " ON " + Database.PLAYLISTSONG_TABLE + ".FK_PlaylistID = " + Database.PLAYLIST_TABLE + ".PK_PlaylistID WHERE " + Database.PLAYLIST_TABLE + ".PK_PlaylistID = ?";
    private static final String SQL_LIST_BY_YEAR =
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE Year = ? AND FK_ArtistID = ?";
    private static final String SQL_LIST_BY_PLAYTIME =
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE FK_ArtistID = ? ORDER BY PlayTime DESC";
    private static final String SQL_LIST_BY_ARTIST = 
    		"SELECT * FROM " + Database.SONG_TABLE + " INNER JOIN " + Database.ARTIST_TABLE + " ON " + Database.SONG_TABLE + ".FK_ArtistID = " + Database.ARTIST_TABLE + ".PK_ArtistID WHERE " + 
    		Database.ARTIST_TABLE + ".Name = ?";
    private static final String SQL_SEARCH_BY_KEYWORD = 
    		"SELECT * FROM " + Database.SONG_TABLE + " WHERE Name LIKE ?";
    private static final String SQL_GET_DISTINCT_GENRES = 
    		"SELECT DISTINCT genre FROM " + Database.SONG_TABLE;
    private static final String SQL_LIST_BY_ACCOUNT = 
    		"SELECT * FROM " + Database.SONG_TABLE + " INNER JOIN " + Database.ACCOUNTSONG_TABLE + " ON " + Database.SONG_TABLE + ".PK_SongID = "
    				+ Database.ACCOUNTSONG_TABLE + ".FK_SongID WHERE FK_AccountID = ?";

    private static final String PATH =
            "resources/music/";

    public SongDAO(DAOFactory db) { this.db = db; }

    private Song map(ResultSet rs) throws SQLException{
        ArtistDAO userDAO = new ArtistDAO(db);
        AlbumDAO albumDAO = new AlbumDAO(db);
        ArtistDAO artistDAO = new ArtistDAO(db);
        Song song = new Song();

        String fileName = rs.getString("Name") + rs.getInt("PK_SongID");

        song.setSongId(rs.getInt("PK_SongID"));

        Artist u = userDAO.find(rs.getInt("FK_ArtistID"));
        song.setArtist(u);
//        song.setArtistId(rs.getInt("FK_ArtistID"));
//        song.setAlbumId(rs.getInt("FK_AlbumID"));
        Album a = albumDAO.find(rs.getInt("FK_AlbumID"));
        song.setAlbum(a);

        Artist artist = artistDAO.find(rs.getInt("FK_ArtistID"));
        song.setArtist(artist);

        song.setGenre(rs.getString("Genre"));

        song.setName(rs.getString("Name"));
        song.setYear(rs.getInt("Year"));
       /* song.setFavorite(rs.getBoolean("Favorite"));
        song.setPlayTime(rs.getLong("PlayTime"));
        song.setLastPlayed(rs.getTimestamp("LastPlayed"));
        song.setDateCreated(rs.getTimestamp("DateCreated"));*/
        Blob b = rs.getBlob("File");
        if (b != null) {
            BlobParser.setStrategy(new BlobToFile());
            File dir = new File("resources/songs");
            dir.mkdirs();
            File wav = new File(dir, song.getSongId() + "");
            try {
                wav.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BlobParser.executeStrategy(rs.getBlob("File"), wav);
            song.setWAV(wav);
        }
//        song.setFile(rs.getBlob("File"));
//        song.setFileName(fileName);
        return song;
    }

    public Song find(int id) {
        Song song = null;
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                song = map(rs);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return song;
    }

    public ArrayList<Song> findByGenre(int artistId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ARTIST_ID_ORDER_BY_GENRE);

            statement.setInt(1, artistId);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> findByAlbum(int artistId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ARTIST_ID_ORDER_BY_ALBUM);

            statement.setInt(1, artistId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> findByYear(int artistId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ARTIST_ID_ORDER_BY_YEAR);
            statement.setInt(1, artistId);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> listById() {
        ArrayList<Song> songs = new ArrayList<>();
        
        Object[] values = {};
        
        Connection connection = Database.getConnection();
        try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ID, false, values);) {
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				songs.add(map(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}

		return songs;
    }

    public ArrayList<Song> listByArtistId(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ARTIST_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> listByGenre(String genreName) {
    	ArrayList<Song> songs = new ArrayList<>();
    	
    	Object[] values = {
    			"%"+ genreName+ "%"
    	};
    	Connection connection = Database.getConnection();

    	try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_GENRE, false, values)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> listByPlaylist(int playlistId) {
        ArrayList<Song> songs = new ArrayList<>();
        Object[] values = {
        		playlistId
        };
        Connection con = Database.getConnection();
        try(PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_PLAYLIST, false, values);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                songs.add(map(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    public ArrayList<Song> listByAlbum(int albumId) {
        ArrayList<Song> songs = new ArrayList<>();
        Object[] values = {
        		albumId
        };
        Connection connection = Database.getConnection();
        
        try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ALBUM, false, values);) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

/*    public ArrayList<Song> listFavorites(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_FAVORITE);
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
*/
    public ArrayList<Song> listByPlaytime(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_PLAYTIME);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                songs.add(map(rs));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }
    
    public ArrayList<Song> listByArtist(String name){
    	 ArrayList<Song> songs = new ArrayList<>();
    	 Object[] values = {
    			 name
    	 };
    	 Connection connection = Database.getConnection();
         try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ARTIST, false, values);) {
             ResultSet rs = statement.executeQuery();
             while(rs.next()) {
                 songs.add(map(rs));
             }
         }catch(SQLException e) {
             e.printStackTrace();
         }
         return songs;
    }

    public void create(Song song) throws IllegalArgumentException {
        if (song.getSongId() != -1) {
            throw new IllegalArgumentException("Song is already created, the song ID is not null.");
        }
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            if (song.getArtist() != null)
                statement.setInt(1, song.getArtist().getArtistId());
            else
                statement.setObject(1, null);
            if (song.getAlbum() != null)
                statement.setInt(2, song.getAlbum().getAlbumId());
            else
                statement.setObject(2, null);
            statement.setString(3, song.getName());
            if (song.getGenre() != null)
                statement.setString(4, song.getGenre());
            else
                statement.setObject(4, null);
            statement.setInt(5, song.getYear());
           /* statement.setBoolean(7, song.isFavorite());
            statement.setLong(8, song.getPlayTime());
            if (song.getLastPlayed() != null)
                statement.setTimestamp(9, new Timestamp(song.getLastPlayed().getTime()));
            else {
                statement.setObject(9, null);
            }*/
//            URL resource = getClass().getClassLoader().getResource("songs/" + song.getFileName());
//            File wav = Paths.get(resource.toURI()).toFile();
            File wav = song.getWAV();
            statement.setBinaryStream(6, new FileInputStream(wav));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                song.setSongId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }

        }catch(SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void delete(Song song) {
        try {
            if (song.getSongId() == -1) {
                throw new IllegalArgumentException("Song is not in the database");
            }
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setInt(1, song.getSongId());
            statement.executeUpdate();
            statement.close();

        }catch(SQLException e) {
            e.printStackTrace();

        }
    }

    public void update(Song song) throws IllegalArgumentException {
        try {
            if (song.getSongId() == -1) {
                throw new IllegalArgumentException("Artist is not created yet, the user ID is null.");
            }
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

            if (song.getArtist() != null)
                statement.setInt(1, song.getArtist().getArtistId());
            else
                statement.setObject(1, null);
            if (song.getAlbum() != null)
                statement.setInt(2, song.getAlbum().getAlbumId());
            else
                statement.setObject(2, null);
            statement.setString(3, song.getName());
            if (song.getGenre() != null)
                statement.setString(4, song.getGenre());
            else
                statement.setObject(4, null);
            statement.setInt(5, song.getYear());
            statement.setInt(6, song.getSongId());
            statement.executeUpdate();

            statement.close();

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ArrayList<Song> search(String name){
    	ArrayList<Song> songs = new ArrayList<>();
    	Object[] values = {
    			"%" + name + "%"
    	};
    	Connection connection = Database.getConnection();
    	
    	try(PreparedStatement statement = prepareStatement(connection, SQL_SEARCH_BY_KEYWORD, false, values);){
    		ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                songs.add(map(rs));
            }
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return songs;
    }
    
    public ArrayList<Song> listByAccount(int accountId){
    	ArrayList<Song> songs = new ArrayList<>();
    	Object[] values = {
    			accountId
    	};
    	
    	Connection connection = Database.getConnection();
    	try(PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ACCOUNT, false, values);){
    		ResultSet rs = statement.executeQuery();
    		while(rs.next()) {
    			songs.add(map(rs));
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return songs;
    }
    
    public ArrayList<Song> listByAccount(Account account){
    	return listByAccount(account.getId());
    }
    
    public ArrayList<String> getGenres() {
    	ArrayList<String> genres = new ArrayList<>();
    	Object[] values = {
    			
    	};
    	
    	Connection connection = Database.getConnection();
    	try(PreparedStatement statement = prepareStatement(connection, SQL_GET_DISTINCT_GENRES, false, values);){
    		ResultSet rs = statement.executeQuery();
    		while(rs.next()) {
    			genres.add(rs.getString("genre"));
    		}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return genres;
    }

    public static void main(String[] args) {
//        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "password");
//        SongDAO songDAO = db.getSongDAO();
//
//        //create song
//        JFileChooser fc = new JFileChooser();
//        fc.showOpenDialog(null);
//        File wav = fc.getSelectedFile();
//
////        System.out.println(wav.getName());
//        Song song = new Song();
//        PlaylistSongDAO psDAO = db.getPlaylistSongDAO();
//        PlaylistDAO playlistDAO = db.getPlaylistDAO();
//        Playlist playlist = playlistDAO.find(2);
//        song.setArtistId(12);
//        song.setAlbumId(1);
//        song.setGenreId(1);
//        song.setName("Guitar");
//        song.setYear(2017);
//        song.setFavorite(false);
//        song.setPlayTime(123);
//        song.setLastPlayed(new Date(109, 2, 2));
//        song.setFileName(wav.getName());
//        songDAO.create(song);
//        psDAO.join(playlist, song);

        //find song
//	      Song songFind = songDAO.find(1);

        //update song
//	      song.setName("Song#1Updated");
//	      song.setYear(2012);
//	      song.setFavorite(false);
//	      song.setPlayTime(521);
//	      song.s
// etLastPlayed(new Date(119, 2, 2));
//	      songDAO.update(song);

        //find by album
//	      ArrayList<Song> songs;
//	      songs = songDAO.listByAlbum(1, 12);
//        System.out.println(songs.size());

        //find by genre
//	      songs = songDAO.listFavorites(12);
//    System.out.println(songs.size());

        //find by year
//	      songs = songDAO.findByYear();

        //delete song
//	      songDAO.delete(song);
    }


}


