package dao;

public class AlbumDAOFactory extends DAOFactory {
    @Override
    protected DataAccessObject createDAO() {
        return new AlbumDAO(this);
    }
}
