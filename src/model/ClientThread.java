package model;

import javax.swing.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ClientThread implements Runnable, Observer {
    // Network variables to communicate to client
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Started thread for client.");
        Server server = Server.getInstance();
        String messageFromClient;
        try {
            while (!server.isShutDown() && (messageFromClient = in.readLine()) != null) {
                System.out.println("Received the message: " + messageFromClient + " from client.");

                StringBuilder reply = new StringBuilder();
                if (messageFromClient.startsWith("GETSONGS")) {
                    server.getSongs();
                } else if (messageFromClient.startsWith("ADDSONG")) {
                    server.addSong();
                } else if (messageFromClient.equals("DELETESONG")) {
                    server.deleteSong();
//
                } else if (messageFromClient.startsWith("EDITSONG")) {
                    server.editSong();

                } else if (messageFromClient.startsWith("PLAYSONG")) {
                    server.playSong();

                } else if (messageFromClient.startsWith("FOLLOWSONG")) {
                    server.followSong();

                } else if (messageFromClient.startsWith("GETPLAYLISTS")) {
                    server.getPlaylists();

                } else if (messageFromClient.startsWith("ADDPLAYLIST")) {
                    server.addPlaylist();

                } else if (messageFromClient.startsWith("DELETEPLAYLIST")) {
                    server.deletePlaylist();

                } else if (messageFromClient.startsWith("EDITPLAYLIST")) {
                    server.editPlaylist();

                } else if (messageFromClient.startsWith("PLAYPLAYLIST")) {
                    server.playPlaylist();

                } else if (messageFromClient.startsWith("FOLLOWPLAYLIST")) {
                    server.followPlaylist();

                } else if (messageFromClient.startsWith("GETALBUMS")) {
                    server.getAlbums();

                }else if (messageFromClient.startsWith("ADDALBUM")) {
                    server.addAlbum();

                }else if (messageFromClient.startsWith("DELETEALBUM")) {
                    server.deleteAlbum();

                }else if (messageFromClient.startsWith("EDITALBUM")) {
                    server.editAlbum();

                }else if (messageFromClient.startsWith("PLAYALBUM")) {
                    server.playAlbum();

                }else if (messageFromClient.startsWith("FOLLOWALBUM")) {
                    server.followAlbum();

                }else if (messageFromClient.startsWith("GETUSERS")) {
                    server.getUsers();

                }else if (messageFromClient.startsWith("ADDUSER")) {
                    server.addUser();

                }else if (messageFromClient.startsWith("EDITUSER")) {
                    server.editUser();

                }else if (messageFromClient.startsWith("FOLLOWUSER")) {
                    server.followUser();

                }else if (messageFromClient.startsWith("GETARTISTS")) {
                    server.getArtists();

                }else if (messageFromClient.startsWith("ADDARTIST")) {
                    server.addArtist();

                }else if (messageFromClient.startsWith("EDITARTIST")) {
                    server.editArtist();

                }else if (messageFromClient.startsWith("FOLLOWARTIST")) {
                    server.followArtist();

                }else if (messageFromClient.startsWith("GETIMAGEFILE")) {
                    server.getImageFile();

                }else if (messageFromClient.startsWith("SETIMAGEFILE")) {
                    server.setImageFile();

                }else if (messageFromClient.startsWith("GETSONGFILE")) {
                    server.getSongFile();

                }else if (messageFromClient.startsWith("SETSONGFILE")) {
                    server.setSongFile();

                }else if (messageFromClient.startsWith("LOGIN")) {
                    server.login();

                }else if (messageFromClient.startsWith("LOGOUT")) {
                    server.logout();

                }else if (messageFromClient.startsWith("SEARCH")) {
                    server.getSongs();

                }
                reply.append("END");
                System.out.println("Sending the following message to the client: " + reply.toString());
                out.println(reply.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.unregisterClientThread(this);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String message = (String) arg;
        System.out.println("ClientThread: Received the broadcast " + message);
        out.println(message);
    }
}
