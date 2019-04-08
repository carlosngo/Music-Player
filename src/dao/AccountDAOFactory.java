package dao;

public class AccountDAOFactory extends DAOFactory {
	protected DataAccessObject createDAO() {
        return new AccountDAO(this);
    }
}
