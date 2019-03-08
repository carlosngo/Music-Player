package DAO;

import java.sql.*;

public interface DAOFactory {

    String DATABASE_URL = "jdbc:mysql://localhost:3306/musicplayer";
    String DATABASE_USERNAME = "root";
    String DATABASE_PASSWORD = "password";

    String USER_TABLE = "musicplayer.user";
    String USER_COLUMNS =
            "PK_UserID, Username, Password, FirstName, LastName, Gender, Birthday";

    String SONG_TABLE = "musicplayer.song";
    String SONG_COLUMNS =
            "PK_SongID, FK_UserID, FK_AlbumID, FK_GenreID, Name, Year, Favorite, PlayTime, LastPlayed, File";

    String PLAYLIST_TABLE = "musicplayer.playlist";
    String PLAYLIST_COLUMNS =
            "PK_PlaylistID, FK_UserID, Name, Favorite";

    String PLAYLISTSONG_TABLE = "musicplayer.playlistsong";
    String PLAYLISTSONG_COLUMNS =
            "FK_PlaylistID, FK_SongID";

    String GENRE_TABLE = "musicplayer.genre";
    String GENRE_COLUMNS =
            "PK_GenreID, FK_UserID, Name";

    String ALBUM_TABLE = "musicplayer.album";
    String ALBUM_COLUMNS =
            "PK_AlbumID, FK_UserID, Name, Artist, Cover";

    static DAOFactory getInstance() {
        return null;
    }

    Connection getConnection() throws SQLException;

    UserDAO getUserDAO();

    SongDAO getSongDAO();

    GenreDAO getGenreDAO();

    PlaylistDAO getPlaylistDAO();

    AlbumDAO getAlbumDAO();

    PlaylistSongDAO getPlaylistSongDAO();


}