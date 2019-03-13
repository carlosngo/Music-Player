package DAO;

public abstract class DAOFactory {

    protected abstract DataAccessObject createDAO();

    public DataAccessObject getDAO() {
        return createDAO();
    }
}