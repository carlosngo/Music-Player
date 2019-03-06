package DAO;

import static DAO.DAOUtil.*;

import Model.Playlist;
import Model.Song;
import java.sql.*;
import java.util.ArrayList;

public class PlaylistSongDAOJDBC implements PlaylistSongDAO {

    private static String SQL_LIST_BY_SONG_ID = "SELECT FK_PlaylistID FROM " + DAOFactory.PLAYLISTSONG_TABLE + " WHERE FK_SongID = ?";
    private static String SQL_LIST_BY_PLAYLIST_ID = "SELECT FK_SongID FROM " + DAOFactory.PLAYLISTSONG_TABLE + " WHERE FK_PlaylistID = ?";
    private static String SQL_INSERT =
            "INSERT INTO " + DAOFactory.PLAYLISTSONG_TABLE + " (" + DAOFactory.PLAYLISTSONG_COLUMNS + ") VALUES (?, ?)";

    private DAOFactory db;

    public PlaylistSongDAOJDBC(DAOFactory db) {
        this.db = db;
    }

    @Override
    public void join(Playlist p, Song s) {
        Object[] values = {
                p.getPlaylistId(),
                s.getSongId()
        };
        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = prepareStatement(con, SQL_INSERT, false, values);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public ArrayList<Integer> listByPlaylistId(int playlistId) {
        ArrayList<Integer> keys = new ArrayList<>();
        try (Connection con = db.getConnection();
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

    @Override
    public ArrayList<Integer> listBySongId(int songId) {
        ArrayList<Integer> keys = new ArrayList<>();
        try (Connection con = db.getConnection();
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
