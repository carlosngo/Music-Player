package Controller;

import Model.*;
import View.*;

import java.sql.SQLException;
import java.util.*;

public class AccountController {
    // data
    private User user;
    private TreeSet<Genre> genres;
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

    public AccountController(MainController mc) {
        this.mc = mc;
        clearCache();
    	user = new User();
    	accountPanel = new AccountPanel(this);
    }

    public User getUser() {
        return user;
    }

    public TreeSet<Genre> getGenres() {
        return genres;
    }

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
        System.out.println("Hi, " + user.getFirstName());
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
    			user.setUserName(username);
    	    	user.setPassword(password);
    	    	user.setFirstName(firstName);
    	    	user.setLastName(lastName);
    	    	user.setGender(gender);
    	    	user.setBirthday(birthday);
    			mc.getUserDAO().create(user);
    			logIn(user.getUserName(), user.getPassword());
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
        save();
    	user = new User();
        mc.getDashboard().setAccountPanel(new AccountPanel(this));
    	clearCache();
        mc.getSongController().showAllSongs();
        mc.getDashboard().update();
    }

    public void save() {

        for (Genre g : genres) {
            try {
                g.setUser(user);
//                if (mc.getGenreDAO().findByName(g.getName(), g.getUser().getUserId()) == null)
                if (g.getGenreId() == -1)
                    mc.getGenreDAO().create(g);
                else
                    mc.getGenreDAO().update(g);
            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
            }
        }
        for (Playlist p : playlists) {
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
        for (Album a : albums) {
            try {
//                System.out.println(a.getAlbumId());
                a.setUser(user);
//                if (mc.getAlbumDAO().findByName(a.getName(), a.getUser().getUserId()) == null)
                if (a.getAlbumId() == -1)
                    mc.getAlbumDAO().create(a);
                else
                    mc.getAlbumDAO().update(a);
            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
            }
        }

        for (Song s : songs) {
            try {
                s.setUser(user);
                System.out.println(s.getSongId());
                if (s.getSongId() == -1)
                    mc.getSongDAO().create(s);
                else
                    mc.getSongDAO().update(s);

            } catch (IllegalArgumentException e) {
//                System.out.println("");
            }
        }
        System.out.println("Data successfully saved.");

    }

    public void load() {
        genres = new TreeSet(mc.getGenreDAO().listById(user.getUserId()));
        playlists = new TreeSet(mc.getPlaylistDAO().listById(user.getUserId()));
        albums = new TreeSet(mc.getAlbumDAO().listById(user.getUserId()));
        songs = new TreeSet(mc.getSongDAO().listById(user.getUserId()));
        for(Song s : songs) {
            s.setAlbum(albums.floor(s.getAlbum()));
            s.setGenre(genres.floor(s.getGenre()));
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

    // clears the cache
    public void clearCache() {
        songs = new TreeSet<>();
        albums = new TreeSet<>();
        playlists = new TreeSet<>();
        genres = new TreeSet<>();
        if (mc.getPlayerController() != null)
            mc.getPlayerController().terminate();
    }

}
