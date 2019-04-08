package dao;

public class SubscriptionDAO implements DataAccessObject {
	private DAOFactory db;
	public SubscriptionDAO(DAOFactory db) {
		this.db = db;
	}
}
