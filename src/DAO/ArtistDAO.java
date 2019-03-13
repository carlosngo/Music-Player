package DAO;

public class ArtistDAO implements DataAccessObject {
	private DAOFactory db;
	
	private static final String SQL_FIND_BY_ID = "";
	private static final String SQL_FIND_BY_NAME = "";
	private static final String SQL_ORDER_BY_GENRE = "";

	public ArtistDAO(DAOFactory db) {
        this.db = db;
    }
	
	
}
