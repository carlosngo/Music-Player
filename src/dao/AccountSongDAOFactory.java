package dao;

public class AccountSongDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new AccountSongDAO(this);
	}

}
