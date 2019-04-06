package controller;

import model.*;
import util.AbstractHashGenerator;
import util.MD5HashGenerator;
import view.*;

import javax.swing.*;
import java.util.*;

public class UserController {

	// data
	private User user;
	private TreeSet<String> genres;
	private TreeSet<Artist> artists;
	private TreeSet<Song> songs;
	private TreeSet<Album> albums;
	private TreeSet<Playlist> playlists;

	// views
	private MainController mc;
	private AccountPanel accountPanel;
	private LogInWindow liw;
	private CreateAccountWindow caw;
	private ViewAccountWindow vaw;
	private EditAccountWindow eaw;
	
	private AbstractHashGenerator hash = new MD5HashGenerator();

	public UserController(MainController mc) {
		this.mc = mc;
		clearCache();
		user = new User();
		accountPanel = new AccountPanel(this);
	}

	public User getUser() {
		return user;
	}

	public TreeSet<String> getGenres() { return genres; }

	public TreeSet<Artist> getArtists() { return artists; }

	public TreeSet<Song> getSongs() {
		return songs;
	}

	public TreeSet<Album> getAlbums() {
		return albums;
	}

	public TreeSet<Playlist> getPlaylists() {
		return playlists;
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
		user = mc.getUserDAO().find(username,hash.generateHash(password));
		if (user == null) return false;
		System.out.println("Hi, " + user.getName());
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to save your current data " +
				"to your account?", "Save Data", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			save();
		}
		mc.getDashboard().setAccountPanel(new AccountPanel(this, user));
		load();
		mc.getSongController().showAllSongs();
		mc.getDashboard().update();
		return true;
	}

	// registers the user. check for errors.
	public boolean register(String username, String password, String firstName, String lastName, String gender, Date birthday) {
		try {
			if(!mc.getUserDAO().existUserName(username)){
				System.out.println(user);
				user.setUserName(username);
				user.setPassword(hash.generateHash(password));
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setGender(gender);
				user.setBirthday(birthday);
				mc.getUserDAO().create(user);
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
		System.out.println("Goodbye, " + user.getName());
		save();
		user = new User();
		mc.getDashboard().setAccountPanel(new AccountPanel(this));
		clearCache();
		mc.getSongController().showAllSongs();
		mc.getDashboard().update();
	}


	public void save() {
		if (user.getUserId() != -1) {
			mc.getUserDAO().update(user);
			for (Song s : songs) {
				Album album = s.getAlbum();
				Artist artist = s.getArtist();

				try {
//					s.setUser(user);
					if (artist != null) {
						if (artist.getArtistId() == -1) mc.getArtistDAO().create(artist);
						else mc.getArtistDAO().update(artist);
					}
					if (album != null) {
//						album.setUser(user);
						if (album.getAlbumId() == -1) mc.getAlbumDAO().create(album);
						else mc.getAlbumDAO().update(album);
					}
					if (s.getSongId() == -1)
						mc.getSongDAO().create(s);
					else
						mc.getSongDAO().update(s);

				} catch (IllegalArgumentException e) {
					//                System.out.println("");
				}
			}
			for (Playlist p : playlists) {
				System.out.println("Saving " + p.getName());
				try {
					p.setUser(user);
					if (p.getPlaylistId() == -1)
						mc.getPlaylistDAO().create(p);
					else
						mc.getPlaylistDAO().update(p);
					for (Song s : p.getSongs()) {
						mc.getPlaylistSongDAO().join(p, s);
					}
				} catch (IllegalArgumentException e) {
					//                e.printStackTrace();
				}
			}
//			for (Album a : albums) {
//				try {
//					//                System.out.println(a.getAlbumId());
//					a.setUser(user);
//					//                if (mc.getAlbumDAO().findByName(a.getName(), a.getUser().getUserId()) == null)
//					if (a.getAlbumId() == -1)
//						mc.getAlbumDAO().create(a);
//					else
//						mc.getAlbumDAO().update(a);
//				} catch (IllegalArgumentException e) {
//					//                e.printStackTrace();
//				}
//			}


			System.out.println("Data successfully saved.");
		}
	}

	public void load() {
		genres = new TreeSet<>();
		artists = new TreeSet<>();
		playlists = new TreeSet(mc.getPlaylistDAO().listByUserId(user.getUserId()));
		albums = new TreeSet(mc.getAlbumDAO().listByUserId(user.getUserId()));
//		songs = new TreeSet(mc.getSongDAO().listByUserId(user.getUserId()));
		for(Song s : songs) {
			if (s.getAlbum() != null) {
				s.setAlbum(albums.floor(s.getAlbum()));
			}
			if (s.getGenre() != null) {
				genres.add(s.getGenre());
			}
			if (s.getArtist() != null) {
			    artists.add(s.getArtist());
            }
		}
		for (Playlist p : playlists) {
			for (Integer i : mc.getPlaylistSongDAO().listByPlaylistId(p.getPlaylistId())) {
				for (Song s : songs) {
					if (s.getSongId() == i) {
						p.getSongs().add(s);
					}
				}
			}
		}

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

	// clears the cache
	public void clearCache() {
		songs = new TreeSet<>();
		albums = new TreeSet<>();
		playlists = new TreeSet<>();
		genres = new TreeSet<>();
		artists = new TreeSet<>();
		if (mc.getPlayerController() != null)
			mc.getPlayerController().terminate();
	}

}
