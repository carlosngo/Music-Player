package controller;

import model.*;
import util.AbstractHashGenerator;
import util.MD5HashGenerator;
import view.*;

import javax.swing.*;
import java.util.*;

public class AccountController {

	// data
	private User user;

	// views
	private MainController mc;
	private AccountPanel accountPanel;
	private LogInWindow liw;
	private CreateAccountWindow caw;
	private ViewAccountWindow vaw;
	private EditAccountWindow eaw;
	
	private AbstractHashGenerator hash = new MD5HashGenerator();

	public AccountController(MainController mc) {
		this.mc = mc;
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

	public void openEditAccountWindow() {
		eaw = new EditAccountWindow(this);
	}

	public void openAddSongWindow() {
		mc.getSongController().openAddSongWindow();
	}

	// logs in the user. check for errors.
	public boolean logIn(String username, String password) {
		user = mc.getClient().logIn(username, password);
		if (user == null) return false;
		System.out.println("Hi, " + user.getFirstName());
		mc.getDashboard().setAccountPanel(new AccountPanel(this, user));
		mc.getSongController().showAllSongs();
		mc.getDashboard().update();
		return true;
	}

	// registers the user. check for errors.
	public boolean registerAsListener(String username, String password, String firstName, String lastName, String gender, Date birthday) {
		try {
			user = new User();
			user.getAccount().setUserName(username);
			user.getAccount().setPassword(password);
//			user.setPassword(hash.generateHash(password));
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setGender(gender);
			user.setBirthday(birthday);
			if(mc.getClient().addUser(user)){
				System.out.println("User " + user.getFirstName() + " is created.");
				logIn(username, password);
			} else {
				return false;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean registerAsArtist(String username, String password, String firstName, String lastName, String gender, Date birthday) {
		try {
			user = new Artist();
			user.getAccount().setUserName(username);
			user.getAccount().setPassword(password);
//			user.setPassword(hash.generateHash(password));
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setGender(gender);
			user.setBirthday(birthday);
			if(mc.getClient().addArtist((Artist)user)){
				System.out.println("Artist " + user.getFirstName() + " is created.");
				logIn(username, password);
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
		System.out.println("Goodbye, " + user.getFirstName());
		user = null;
		openLogInWindow();
	}

	public void updateUser(String userName, String password, String firstName, String lastName, String gender, Date birthday){
		
		user.setUserName(userName);
		user.setPassword(hash.generateHash(password));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setBirthday(birthday);
		mc.getAccountController().getAccountPanel().update();
		/*        Song s = displayedSongs.get(songIndex);
//        System.out.println(s);
        s.setName(title);
        s.setAlbum(getAlbum(album));
//        if (s.getAlbum() != null)
//            s.getAlbum().setName(album);
//        else {
////            Album a = new
//        }

        s.setYear(Integer.parseInt(year));
        s.setGenre(getGenre(genre));
//        s.getGenre().setName(genre);
        showAllSongs();
        mc.getPlayerController().getPlayerPanel().update();*/
	}
}
