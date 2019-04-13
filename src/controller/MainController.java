package controller;

import net.*;
import model.*;
import dao.*;
import view.*;

import java.util.*;

public class MainController {

    // controllers
    private AccountController ac;
    private PlayerController pc;
    private SongController sc;

    private Client client;

    // views
    private static Dashboard dashboard;

    public MainController() {
        client = Client.getInstance();
        client.startConnection();
        ac = new AccountController(this);
        pc = new PlayerController(this);
        sc = new SongController(this);
        ac.openLogInWindow();
    }

    public void exit() {
        pc.terminate();
    }

    public Client getClient() { return client; }

    public AccountController getAc() {
        return ac;
    }

    public PlayerController getPc() {
        return pc;
    }

    public SongController getSc() {
        return sc;
    }

    public void openDashboard() {
        dashboard = new Dashboard(this);
    }

    public AccountController getAccountController() {
        return ac;
    }

    public PlayerController getPlayerController() {
        return pc;
    }

    public SongController getSongController() {
        return sc;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void playSongs(ArrayList<Song> songs) {
        pc.setSongs(songs);
        pc.startPlayer();
    }
    // saves all the cached data in the database.

}
