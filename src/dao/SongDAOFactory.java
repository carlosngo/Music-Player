package dao;

public class SongDAOFactory extends DAOFactory {
    @Override
    protected DataAccessObject createDAO() {
        return new SongDAO(this);
    }
}
