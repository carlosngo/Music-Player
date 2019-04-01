package dao;

import model.Album;
import model.*;
import util.BlobParser;
import util.BlobToFile;

import static util.DAOUtil.*;

import java.sql.*;
import java.util.*;
import java.io.*;


public class SongDAO implements DataAccessObject {
    private DAOFactory db;

    private static final String SQL_FIND_BY_ID =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_FIND_BY_GENRE =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_UserID = ? ORDER BY FK_GenreID";
    private static final String SQL_FIND_BY_ALBUM =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_UserID = ? ORDER BY FK_AlbumID";
    private static final String SQL_FIND_BY_YEAR =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_UserID = ? ORDER BY year";
    private static final String SQL_INSERT =
            "INSERT INTO " + Database.SONG_TABLE + " (FK_ArtistID, FK_UserID, FK_AlbumID, Name, Genre, Year, Favorite, PlayTime, LastPlayed, File, DateCreated) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE =
            "DELETE FROM " + Database.SONG_TABLE + " WHERE PK_SongID = ?";
    private static final String SQL_UPDATE =
            "UPDATE " + Database.SONG_TABLE + " SET FK_ArtistID = ?, FK_UserID = ?, FK_AlbumID = ?, Name = ?, Genre = ?, Year = ?, Favorite = ?, PlayTime = ?, LastPlayed = ? WHERE PK_SongID = ?";
    private static final String SQL_LIST_BY_ID =
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE FK_UserID = ?";
    private static final String SQL_LIST_BY_GENRE =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_GenreID = ? AND FK_UserID = ?";
    private static final String SQL_LIST_BY_ALBUM =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE FK_AlbumID = ? AND FK_UserID = ?";
    private static final String SQL_LIST_BY_FAVORITE =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " WHERE Favorite = ? AND FK_UserID = ?";
    private static final String SQL_LIST_BY_PLAYLIST =
            "SELECT " + Database.SONG_COLUMNS + " FROM " + Database.SONG_TABLE + " INNER JOIN " + Database.PLAYLISTSONG_TABLE + " ON " + Database.SONG_TABLE + ".PK_SongID = " + Database.PLAYLISTSONG_TABLE + ".FK_SongID " +
                    "INNER JOIN " + Database.PLAYLIST_TABLE + " ON " + Database.PLAYLISTSONG_TABLE + ".FK_PlaylistID = " + Database.PLAYLIST_TABLE + ".PK_PlaylistID WHERE " + Database.SONG_TABLE + ".FK_UserID = ? AND " + Database.PLAYLIST_TABLE + ".PK_PlaylistID = ?";
    private static final String SQL_LIST_BY_YEAR =
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE Year = ? AND FK_UserID = ?";
    private static final String SQL_LIST_BY_PLAYTIME =
            "SELECT * FROM " + Database.SONG_TABLE + " WHERE FK_UserID = ? ORDER BY PlayTime DESC";
    private static final String SQL_LIST_BY_ARTIST = 
    		"SELECT * FROM " + Database.SONG_TABLE + " INNER JOIN " + Database.ARTIST_TABLE + " ON " + Database.SONG_TABLE + ".FK_ArtistID = " + Database.ARTIST_TABLE + ".PK_ArtistID WHERE " + 
    		Database.ARTIST_TABLE + ".Name = ?";
    private static final String PATH =
            "resources/music/";

    public SongDAO(DAOFactory db) { this.db = db; }

    private Song map(ResultSet rs) throws SQLException{
        UserDAO userDAO = new UserDAO(db);
        AlbumDAO albumDAO = new AlbumDAO(db);
        ArtistDAO artistDAO = new ArtistDAO(db);
        Song song = new Song();

        String fileName = rs.getString("Name") + rs.getInt("PK_SongID");

        song.setSongId(rs.getInt("PK_SongID"));

        User u = userDAO.find(rs.getInt("FK_UserID"));
        song.setUser(u);
//        song.setUserId(rs.getInt("FK_UserID"));
//        song.setAlbumId(rs.getInt("FK_AlbumID"));
        Album a = albumDAO.find(rs.getInt("FK_AlbumID"));
        song.setAlbum(a);

        Artist artist = artistDAO.find(rs.getInt("FK_ArtistID"));
        song.setArtist(artist);

        song.setGenre(rs.getString("Genre"));

        song.setName(rs.getString("Name"));
        song.setYear(rs.getInt("Year"));
        song.setFavorite(rs.getBoolean("Favorite"));
        song.setPlayTime(rs.getLong("PlayTime"));
        song.setLastPlayed(rs.getTimestamp("LastPlayed"));
        song.setDateCreated(rs.getTimestamp("DateCreated"));
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

    public ArrayList<Song> findByGenre(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_GENRE);

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

    public ArrayList<Song> findByAlbum(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ALBUM);

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

    public ArrayList<Song> findByYear(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();

            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_YEAR);
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

    public ArrayList<Song> listById(int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ID);
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

    public ArrayList<Song> listByGenre(int genreId, int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_GENRE);
            statement.setInt(1, genreId);
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

    public ArrayList<Song> listByPlaylist(int playlistId, int userId) {
        // TODO Auto-generated method stub
        Object[] values = {
                userId,
                playlistId
        };
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection con = Database.getConnection();
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

    public ArrayList<Song> listByAlbum(int albumId, int userId) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_BY_ALBUM);
            statement.setInt(1, albumId);
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

    public ArrayList<Song> listFavorites(int userId) {
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
    	Object[] values = {
    		name
    	};
    	 ArrayList<Song> songs = new ArrayList<>();
         try {
             Connection connection = Database.getConnection();
             PreparedStatement statement = prepareStatement(connection, SQL_LIST_BY_ARTIST, false, values);
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
            statement.setInt(2, song.getUser().getUserId());
            if (song.getAlbum() != null)
                statement.setInt(3, song.getAlbum().getAlbumId());
            else
                statement.setObject(3, null);
            statement.setString(4, song.getName());
            if (song.getGenre() != null)
                statement.setString(5, song.getGenre());
            else
                statement.setObject(5, null);
            statement.setInt(6, song.getYear());
            statement.setBoolean(7, song.isFavorite());
            statement.setLong(8, song.getPlayTime());
            if (song.getLastPlayed() != null)
                statement.setTimestamp(9, new Timestamp(song.getLastPlayed().getTime()));
            else {
                statement.setObject(9, null);
            }
//            URL resource = getClass().getClassLoader().getResource("songs/" + song.getFileName());
//            File wav = Paths.get(resource.toURI()).toFile();
            File wav = song.getWAV();
            statement.setBinaryStream(10, new FileInputStream(wav));

            statement.setTimestamp(11, new Timestamp(song.getDateCreated().getTime()));
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
                throw new IllegalArgumentException("User is not created yet, the user ID is null.");
            }
            Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

            if (song.getArtist() != null)
                statement.setInt(1, song.getArtist().getArtistId());
            else
                statement.setObject(1, null);
            statement.setInt(2, song.getUser().getUserId());
            if (song.getAlbum() != null)
                statement.setInt(3, song.getAlbum().getAlbumId());
            else
                statement.setObject(3, null);
            statement.setString(4, song.getName());
            if (song.getGenre() != null)
                statement.setString(5, song.getGenre());
            else
                statement.setObject(5, null);
            statement.setInt(6, song.getYear());
            statement.setBoolean(7, song.isFavorite());
            statement.setLong(8, song.getPlayTime());
            if (song.getLastPlayed() != null)
                statement.setTimestamp(9, new Timestamp(song.getLastPlayed().getTime()));
            else {
                statement.setObject(9, null);
            }
            statement.setInt(10, song.getSongId());
            statement.executeUpdate();

            statement.close();

        }catch(SQLException e) {
            e.printStackTrace();
        }
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
//        song.setUserId(12);
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


