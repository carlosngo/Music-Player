package DAO;

import static DAO.DAOUtil.*;

import Model.Playlist;
import Model.Song;
import java.sql.*;

public class PlaylistSongDAOJDBC implements PlaylistSongDAO {

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
}
