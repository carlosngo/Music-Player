package dao;

public class UserSongDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new UserSongDAO(this);
	}

}
