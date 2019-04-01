package dao;

import static util.DAOUtil.*;

import model.Playlist;
import model.Song;

import java.sql.*;
import java.util.ArrayList;

public class PlaylistSongDAO implements DataAccessObject {

    private static String SQL_LIST_BY_SONG_ID = "SELECT FK_PlaylistID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_SongID = ?";
    private static String SQL_LIST_BY_PLAYLIST_ID = "SELECT FK_SongID FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_PlaylistID = ?";
    private static String SQL_INSERT =
            "INSERT INTO " + Database.PLAYLISTSONG_TABLE + " (" + Database.PLAYLISTSONG_COLUMNS + ") VALUES (?, ?)";
    private static String SQL_DELETE =
            "DELETE FROM " + Database.PLAYLISTSONG_TABLE + " WHERE FK_PlaylistID = ? AND FK_SongID = ?";
    private DAOFactory db;

    public PlaylistSongDAO(DAOFactory db) {
        this.db = db;
    }

    public void join(Playlist p, Song s) {
        Object[] values = {
                p.getPlaylistId(),
                s.getSongId()
        };
        try {
            Connection con = Database.getConnection();

            PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public void separate(Playlist p, Song s) {
        if (p.getPlaylistId() == -1 || s.getSongId() == -1)
            return;
        Object[] values = {
                p.getPlaylistId(),
                s.getSongId()
        };
        try {
            Connection con = Database.getConnection();

            PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values);
            stmt.executeUpdate();

        } catch (SQLException e) {
        }
    }

    public ArrayList<Integer> listByPlaylistId(int playlistId) {
        ArrayList<Integer> keys = new ArrayList<>();
        Connection con = Database.getConnection();
        try (
             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_PLAYLIST_ID, false, playlistId);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                keys.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return keys;
    }

    public ArrayList<Integer> listBySongId(int songId) {
        ArrayList<Integer> keys = new ArrayList<>();
        Connection con = Database.getConnection();
        try (
             PreparedStatement stmt = prepareStatement(con, SQL_LIST_BY_SONG_ID, false, songId);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                keys.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return keys;
    }
}
