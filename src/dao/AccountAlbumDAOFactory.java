package dao;

public class AccountAlbumDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new AccountAlbumDAO(this);
	}

}
