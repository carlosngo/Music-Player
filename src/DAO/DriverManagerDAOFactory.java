package DAO;

import java.sql.*;

public class DriverManagerDAOFactory extends DAOFactory {
    private String url;
    private String username;
    private String password;

    public DriverManagerDAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public UserDAO getUserDAO() { return new UserDAOJDBC(this); }

    @Override
    public SongDAO getSongDAO() { return new SongDAOJDBC(this); }

    @Override
    public GenreDAO getGenreDAO() { return new GenreDAOJDBC(this); }

    @Override
    public PlaylistDAO getPlaylistDAO() { return new PlaylistDAOJDBC(this); }

    @Override
    public AlbumDAO getAlbumDAO() { return new AlbumDAOJDBC(this); }

    public PlaylistSongDAO getPlaylistSongDAO() { return new PlaylistSongDAOJDBC(this); }
}
