package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Playlist;
import model.User;

public class AccountDAO implements DataAccessObject {
    private DAOFactory db;
    
    private static final String SQL_INSERT = "INSERT INTO " + Database.ACCOUNT_TABLE + " (PK_AccountID, Username, Password) VALUES (?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + Database.ACCOUNT_TABLE + " WHERE PK_AccountID = ?";
    private static final String SQL_UPDATE = "UPDATE " + Database.ACCOUNT_TABLE + " SET PK_AccountID = ?, Username = ?, Password = ? WHERE PK_AccountID = ?";
    
    
    public AccountDAO(DAOFactory db) {
        this.db = db;
    }
    
    
}
