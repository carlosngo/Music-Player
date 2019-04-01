package dao;

public class ArtistDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new ArtistDAO(this);
	}

}
