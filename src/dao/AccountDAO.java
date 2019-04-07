package dao;

public class AccountDAO implements DataAccessObject {
    private DAOFactory db;
    
    private static final String SQL_INSERT = "";
    private static final String SQL_DELETE = "";
    
    public AccountDAO(DAOFactory db) {
        this.db = db;
    }
}
