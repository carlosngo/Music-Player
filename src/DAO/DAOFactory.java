package DAO;

import java.sql.*;

public abstract class DAOFactory {

    protected abstract DataAccessObject createDAO();

    public DataAccessObject getDAO() {
        return createDAO();
    }
}