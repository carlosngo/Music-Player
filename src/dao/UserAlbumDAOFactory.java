package dao;

public class UserAlbumDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new UserAlbumDAO(this);
	}

}
