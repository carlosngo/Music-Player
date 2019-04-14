package dao;

import java.sql.*;

public final class Database {
    public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/musicplayer";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "password";

    public static final String USER_TABLE = "musicplayer.user";
    public static final String USER_COLUMNS =
            "user.PK_UserID, user.FK_AccountID, user.FirstName, user.LastName, user.Gender, user.Birthday";

    public static final String SONG_TABLE = "musicplayer.song";
    public static final String SONG_COLUMNS =
            "song.PK_SongID, song.FK_ArtistID, song.FK_AlbumID, song.Name, song.Genre, song.Year, song.File";

    public static final String PLAYLIST_TABLE = "musicplayer.playlist";
    public static final String PLAYLIST_COLUMNS =
            "playlist.PK_PlaylistID, playlist.FK_AccountID, playlist.Name, playlist.DateCreated";

    public static final String PLAYLISTSONG_TABLE = "musicplayer.playlistsong";
    public static final String PLAYLISTSONG_COLUMNS =
            "playlistsong.FK_PlaylistID, playlistsong.FK_SongID";

    public static final String ALBUM_TABLE = "musicplayer.album";
    public static final String ALBUM_COLUMNS =
            "album.PK_AlbumID, album.FK_ArtistID, album.Name, album.Cover, album.DateCreated";

    public static final String ARTIST_TABLE = "musicplayer.artist";
    public static final String ARTIST_COLUMNS =
            "artist.PK_ArtistID, artist.FK_AccountID, artist.Name, artist.Genre";

    public static final String ACCOUNT_TABLE = "musicplayer.account";
    public static final String ACCOUNT_COLUMNS =
    		"account.PK_AccountID, account.Username, account.Password";
    
    public static final String SUBSCRIPTION_TABLE = "musicplayer.subscription";
    public static final String SUBSCRIPTION_COLUMNS =
    		"subscription.FK_SubscriberID, subscription.FK_SubscribeeID";
   
    public static final String ACCOUNTPLAYLIST_TABLE = "musicplayer.accountplaylist";
    public static final String ACCOUNTPLAYLIST_COLUMNS = 
    		"accountplaylist.FK_AccountID, accountplaylist.FK_PlaylistID, accountplaylist.isFavorite";
    
    public static final String ACCOUNTALBUM_TABLE = "musicplayer.accountalbum";
    public static final String ACCOUNTALBUM_COLUMNS = 
    		"accountalbum.FK_AccountID, accountalbum.FK_AlbumID, accountalbum.isFavorite";
    
    public static final String ACCOUNTSONG_TABLE = "musicplayer.accountsong";
    public static final String ACCOUNTSONG_COLUMNS = 
    		"accountsong.FK_AccountID, accountsong.FK_SongID, accountsong.isFavorite, accountsong.playTime, accountsong.LastPlayed";

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
