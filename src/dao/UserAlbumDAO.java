package dao;

public class UserAlbumDAO implements DataAccessObject {
	private DAOFactory db;
	
	private static final String SQL_INSERT = "";
	private static final String SQL_DELETE = "";
	private static final String SQL_LIST_BY_USER_ID = "";
	private static final String SQL_LIST_BY_ALBUM_ID = "";
 	
	public UserAlbumDAO(DAOFactory db) {
		this.db = db;
	}
}
