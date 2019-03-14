package DAO;

import java.sql.*;

public final class Database {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicplayer";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "password";

    public static final String USER_TABLE = "musicplayer.user";
    public static final String USER_COLUMNS =
            "PK_UserID, Username, Password, FirstName, LastName, Gender, Birthday";

    public static final String SONG_TABLE = "musicplayer.song";
    public static final String SONG_COLUMNS =
            "PK_SongID, FK_ArtistID, FK_UserID, FK_AlbumID, Name, Genre, Year, Favorite, PlayTime, LastPlayed, File, DateCreated";

    public static final String PLAYLIST_TABLE = "musicplayer.playlist";
    public static final String PLAYLIST_COLUMNS =
            "PK_PlaylistID, FK_UserID, Name, Favorite, DateCreated";

    public static final String PLAYLISTSONG_TABLE = "musicplayer.playlistsong";
    public static final String PLAYLISTSONG_COLUMNS =
            "FK_PlaylistID, FK_SongID";

    public static final String ALBUM_TABLE = "musicplayer.album";
    public static final String ALBUM_COLUMNS =
            "PK_AlbumID, FK_ArtistID, FK_UserID, Name, Artist, Cover, DateCreated";

    public static final String ARTIST_TABLE = "musicplayer.artist";
    public static final String ARTIST_COLUMNS =
            "PK_ArtistID, Name, Genre";

    private static Connection con = null;

    private Database() { }

    public static Connection getConnection() {
        try {
            if (con == null)
                con = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch(SQLException e) {
            System.out.println("Cannot connect to database.");
        }
        return con;
    }
}
