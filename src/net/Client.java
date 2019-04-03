package net;

import util.Protocol;
import model.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.*;


public class Client {
    private static final Client singleton = new Client();
    // Network variables to communicate with the server
    private Socket socket;
    private PrintWriter outToServer;
    private BufferedReader inFromServer;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private Client() { }

    public static Client getInstance() { return singleton; }

    public User logIn(String username, String password) {
        User user = null;
        outToServer.println(Protocol.LOGIN);
        outToServer.println(username);
        outToServer.println(password);
        try {
            Protocol protocol = Protocol.valueOf(inFromServer.readLine());
            switch (protocol) {
                case OK:
                    user = User.parseUser(inFromServer.readLine());
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void logOut(int userId) {
        outToServer.println(Protocol.LOGOUT);
        outToServer.println(userId);
    }
}
