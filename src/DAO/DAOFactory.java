package DAO;

import java.sql.*;

public abstract class DAOFactory {

    public abstract Connection getConnection() throws SQLException;

    public abstract UserDAO getUserDAO();

    public abstract SongDAO getSongDAO();

    public abstract GenreDAO getGenreDAO();

    public abstract PlaylistDAO getPlaylistDAO();

    public abstract AlbumDAO getAlbumDAO();

}