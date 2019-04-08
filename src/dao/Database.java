package dao;

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
            "PK_SongID, FK_ArtistID, FK_UserID, FK_AlbumID, Name, Genre, Year, File";

    public static final String PLAYLIST_TABLE = "musicplayer.playlist";
    public static final String PLAYLIST_COLUMNS =
            "PK_PlaylistID, FK_UserID, Name, DateCreated";

    public static final String PLAYLISTSONG_TABLE = "musicplayer.playlistsong";
    public static final String PLAYLISTSONG_COLUMNS =
            "FK_PlaylistID, FK_SongID";

    public static final String ALBUM_TABLE = "musicplayer.album";
    public static final String ALBUM_COLUMNS =
            "PK_AlbumID, FK_ArtistID, FK_UserID, Name, Artist, Cover, DateCreated";

    public static final String ARTIST_TABLE = "musicplayer.artist";
    public static final String ARTIST_COLUMNS =
            "PK_ArtistID, Name, Genre";

    public static final String ACCOUNT_TABLE = "musicplayer.account";
    public static final String ACCOUNT_COLUMNS =
    		"PK_AccountID, Username, Password";
    
    public static final String SUBSCRIPTION_TABLE = "musicplayer.subscription";
    public static final String SUBSCRIPTION_COLUMNS =
    		"FK_SubscriberID, FK_SubscribeeID";
    public static final String USERPLAYLIST_TABLE = "musicplayer.userplaylist";
    public static final String USERPLAYLIST_COLUMNS = 
    		"FK_UserID, FK_PlaylistID, isFavorite";
    
    public static final String USERALBUM_TABLE = "musicplayer.useralbum";
    public static final String USERALBUM_COLUMNS = 
    		"FK_UserID, FK_AlbumID, isFavorite";
    
    public static final String USERSONG_TABLE = "musicplayer.useralbum";
    public static final String USERSONG_COLUMNS = 
    		"FK_UserID, FK_SongID, isFavorite, playTime, LastPlayed";

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
