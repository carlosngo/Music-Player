package DAO;

public class PlaylistSongDAOFactory extends DAOFactory {

    @Override
    protected DataAccessObject createDAO() {
        return new PlaylistSongDAO(this);
    }
}
