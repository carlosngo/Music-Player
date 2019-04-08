package dao;

public class SubscriptionDAOFactory extends DAOFactory{

	@Override
	protected DataAccessObject createDAO() {
		return new SubscriptionDAO(this);
	}

}
