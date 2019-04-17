package controller;

import model.*;

import util.AbstractHashGenerator;
import util.MD5HashGenerator;
import view.*;

import java.util.*;

public class AccountController {

	// data
	private User user;

	// views
	private MainController mc;
	private AccountPanel accountPanel;
	private LogInWindow liw;
	private CreateAccountWindow caw;
	private NotificationWindow notifWindow;
	private ViewAccountWindow vaw;
	private EditAccountWindow eaw;
	
	private AbstractHashGenerator hash = new MD5HashGenerator();

	public AccountController(MainController mc) {
		this.mc = mc;
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

	public void openNotifWindow(int x, int y){
		notifWindow = new NotificationWindow();
		notifWindow.show(x,y);
	}

	public void openAddSongWindow() {
		mc.getSongController().openAddSongWindow();
	}

	// logs in the user. check for errors.
	public boolean logIn(String username, String password) {
		user = mc.getClient().logIn(username, password);
		if (user == null) return false;
		System.out.println(user);
		System.out.println(user.getAccount());
		System.out.println("Hi, " + user.getAccount().getUserName());
		accountPanel = new AccountPanel(this, user);
		mc.getSongController().setUser(user);
		mc.getSongController().showAllSongs();
		mc.getSongController().showAlbums();
		mc.openDashboard();
//		mc.getDashboard().update();
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
			Artist artist = new Artist();
			artist.getAccount().setUserName(username);
			artist.getAccount().setPassword(password);
			artist.setFirstName(firstName);
			artist.setLastName(lastName);
			artist.setName(firstName + " " + lastName);
			artist.setGender(gender);
			artist.setBirthday(birthday);
			if(mc.getClient().addArtist(artist)){
				System.out.println("Artist " + artist.getName() + " is created.");

//				logIn(username, password);
			} else {
				return false;
			}
			user = artist;
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
		
		user.getAccount().setUserName(userName);
		user.getAccount().setPassword(password);
//		user.setPassword(hash.generateHash(password));
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setBirthday(birthday);
		if (user instanceof Artist) mc.getClient().updateArtist((Artist)user);
		else mc.getClient().updateUser(user);
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

	public boolean isArtist() {
		return user instanceof Artist;
	}
}
