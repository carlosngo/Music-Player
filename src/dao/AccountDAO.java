package dao;

import static util.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Playlist;
import model.User;

public class AccountDAO implements DataAccessObject {
    private DAOFactory db;
    
    private static final String SQL_INSERT = "INSERT INTO " + Database.ACCOUNT_TABLE + " (Username, Password) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM " + Database.ACCOUNT_TABLE + " WHERE PK_AccountID = ?";
    private static final String SQL_UPDATE = "UPDATE " + Database.ACCOUNT_TABLE + " SET Username = ?, Password = ? WHERE PK_AccountID = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM " + Database.ACCOUNT_TABLE + " WHERE PK_AccountID = ?";
    private static final String SQL_FIND_BY_USERNAME_PASSWORD = "SELECT * FROM " + Database.ACCOUNT_TABLE + " WHERE Username = ? AND Password = ?";
    private static final String SQL_EXIST_USERNAME =
			"SELECT PK_AccountID FROM " + Database.ACCOUNT_TABLE + " WHERE UserName = ?";
    
    public AccountDAO(DAOFactory db) {
        this.db = db;
    }
    
    private Account map(ResultSet rs) {
    	Account account = new Account();
    	try {
			account.setId(rs.getInt("PK_AccountID"));
			account.setUserName(rs.getString("Username"));
			account.setPassword(rs.getString("Password"));
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	return account;
    }
    
    public Account find(long id) {
        return find(SQL_FIND_BY_ID, id);
    }

    public Account find(String username, String password) {
        return find(SQL_FIND_BY_USERNAME_PASSWORD, username, password);
    }

    private Account find(String sql, Object... values) {
        Account account = null;
        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, sql, false, values);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                account = map(resultSet);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    
    public void insert(Account account) throws IllegalArgumentException {
    	if(account.getId() != -1) {
    		throw new IllegalArgumentException("Account is already created");
    	}
    	
    	Object[] values = {
    			account.getUserName(),
    			account.getPassword()
        };

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_INSERT, true, values);) {
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                account.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    }
    
    
    public void delete(Account account) {
        Object[] values = {
        		account.getId()
        };

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            } else {
                account.setId(-1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Account account) throws IllegalArgumentException {
        if (account.getId() == -1) {
            throw new IllegalArgumentException("User is not created yet, the user ID is null.");
        }
        System.out.println("Updating the account to " + account);
        Object[] values = {
        		account.getUserName(),
        		account.getPassword(),
                account.getId()
        };

        Connection connection = Database.getConnection();
        try (
             PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public boolean existUserName(String userName) {
		Object[] values = {
				userName
		};
		boolean exist = false;
		Connection connection = Database.getConnection();
		try (
				PreparedStatement statement = prepareStatement(connection, SQL_EXIST_USERNAME, false, values);
				ResultSet resultSet = statement.executeQuery()) {
			exist = resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}

}
