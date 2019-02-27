package DAO;

import java.sql.*;

public abstract class DAOFactory {

    public static final String USER_TABLE = "musicplayer.User";
    public static final String USER_COLUMNS =
            "PK_UserID, Username, Password, FirstName, LastName, Birthday";

    public static final String SONG_TABLE = "musicplayer.Song";
    public static final String SONG_COLUMNS =
            "PK_SongID, FK_UserID, FK_AlbumID, FK_GenreID, Name, Year, Favorite, PlayTime, LastPlayed, Path";

    public static final String PLAYLIST_TABLE = "musicplayer.Playlist";
    public static final String PLAYLIST_COLUMNS =
            "PK_PlaylistID, FK_UserID, Name, Favorite";

    public static final String GENRE_TABLE = "musicplayer.Genre";
    public static final String GENRE_COLUMNS =
            "PK_GenreID, FK_UserID, Name";

    public static final String ALBUM_TABLE = "musicplayer.Album";
    public static final String ALBUM_COLUMNS =
            "PK_AlbumID, FK_UserID, Name, Artist, Cover";

    public abstract Connection getConnection() throws SQLException;

    public abstract UserDAO getUserDAO();

    public abstract SongDAO getSongDAO();

    public abstract GenreDAO getGenreDAO();

    public abstract PlaylistDAO getPlaylistDAO();

    public abstract AlbumDAO getAlbumDAO();

}