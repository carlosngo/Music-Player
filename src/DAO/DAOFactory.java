package DAO;

import java.sql.*;

public abstract class DAOFactory {

    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicplayer";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "zerovit098";

    public static final String USER_TABLE = "musicplayer.user";
    public static final String USER_COLUMNS =
            "PK_UserID, Username, Password, FirstName, LastName, Gender, Birthday";

    public static final String SONG_TABLE = "musicplayer.song";
    public static final String SONG_COLUMNS =
            "PK_SongID, FK_UserID, FK_AlbumID, FK_GenreID, Name, Year, Favorite, PlayTime, LastPlayed, File";

    public static final String PLAYLIST_TABLE = "musicplayer.playlist";
    public static final String PLAYLIST_COLUMNS =
            "PK_PlaylistID, FK_UserID, Name, Favorite";

    public static final String PLAYLISTSONG_TABLE = "musicplayer.playlistsong";
    public static final String PLAYLISTSONG_COLUMNS =
            "FK_PlaylistID, FK_SongID";

    public static final String GENRE_TABLE = "musicplayer.genre";
    public static final String GENRE_COLUMNS =
            "PK_GenreID, FK_UserID, Name";

    public static final String ALBUM_TABLE = "musicplayer.album";
    public static final String ALBUM_COLUMNS =
            "PK_AlbumID, FK_UserID, Name, Artist, Cover";

    protected abstract DataAccessObject createDAO();

    public DataAccessObject getDAO() {
        return createDAO();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }
}