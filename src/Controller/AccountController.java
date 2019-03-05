package Controller;

import Model.*;
import View.*;

import java.sql.SQLException;
import java.util.*;

public class AccountController {
    private User user;
    private MainController mc;
    private AccountPanel accountPanel;
    private LogInWindow liw;
    private CreateAccountWindow caw;
    private ViewAccountWindow vaw;
    private EditAccountWindow eaw;

    public AccountController(MainController mc) {
        this.mc = mc;
    	user = null;
    	accountPanel = new AccountPanel(this);
    }

    public User getUser() {
        return user;
    }

    public MainController getMc() {
        return mc;
    }

    public AccountPanel getAccountPanel() {
        return accountPanel;
    }

    public LogInWindow getLiw() {
        return liw;
    }

    public CreateAccountWindow getCaw() {
        return caw;
    }

    public ViewAccountWindow getVaw() {
        return vaw;
    }

    public void openLogInWindow() {
        liw = new LogInWindow(this);
    }

    public void openCreateAccountWindow() {
        caw = new CreateAccountWindow(this);
    }

    public void openViewAccountWindow() {
        vaw = new ViewAccountWindow(this);
    }

    public void openEditAccountWindow() {
        eaw = new EditAccountWindow(this);
    }

    public void openAddSongWindow() {
        mc.getSongController().openAddSongWindow();
    }

    // logs in the user. check for errors.
    public boolean logIn(String username, String password) {

        user = mc.getUserDAO().find(username, password);
        if (user == null) return false;

		accountPanel = new AccountPanel(this, user);
    	accountPanel.update();
//		mc.getDashboard().update();
        return true;
    }

    // registers the user. check for errors.
    public boolean register(String username, String password, String firstName, String lastName, String gender, Date birthday) {
    	
    	
    	
    	try {
    		if(mc.getUserDAO().existUserName(username)){
    			user.setUserName(username);
    	    	user.setPassword(password);
    	    	user.setFirstName(firstName);
    	    	user.setLastName(lastName);
    	    	user.setGender(gender);
    	    	user.setBirthday(birthday);
    			mc.getUserDAO().create(user);
    		} else {
    			return false;
    		}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }

    // logs out the user. clear the cache
    public void logOut() {
    	user = null;
    	mc.clearCache();
    }

}
