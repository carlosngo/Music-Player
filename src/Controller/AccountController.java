package Controller;

import Model.*;
import View.*;

import java.sql.SQLException;
import java.util.*;

import DAO.DAOFactory;
import DAO.DriverManagerDAOFactory;
import DAO.UserDAO;

public class AccountController {
    private User user;
    private AccountPanel accountPanel;


    public AccountController() {
    	user = new User();
    }

    // logs in the user. check for errors.
    public void logIn(String username, String password) {
    
    	try {
    		user = MainController.userDAO.find(username, password);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    
    }

    // registers the user. check for errors.
    public void register(String username, String password, String firstName, String lastName, String gender, Date birthday) {
    	
    	
    	
    	try {
    		if(!MainController.userDAO.existUserName(username)){
    			user.setUserName(username);
    	    	user.setPassword(password);
    	    	user.setFirstName(firstName);
    	    	user.setLastName(lastName);
    	    	user.setGender(gender);
    	    	user.setBirthday(birthday);
    			MainController.userDAO.create(user);
    		}
		} catch (SQLException | IllegalArgumentException e) {
			e.printStackTrace();
		}
    }

    // logs out the user. clear the cache
    public void logOut() {
    	user = null;
    	MainController.clearCache();
    }
}
