package Model;

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

                } else if (messageFromClient.startsWith("ADDSONG")) {

                } else if (messageFromClient.equals("DELETESONG")) {
//
                } else if (messageFromClient.startsWith("EDITSONG")) {

                } else if (messageFromClient.startsWith("PLAYSONG")) {

                } else if (messageFromClient.startsWith("FOLLOWSONG")) {

                } else if (messageFromClient.startsWith("GETPLAYLISTS")) {

                } else if (messageFromClient.startsWith("ADDPLAYLIST")) {

                } else if (messageFromClient.startsWith("DELETEPLAYLIST")) {

                } else if (messageFromClient.startsWith("EDITPLAYLIST")) {

                } else if (messageFromClient.startsWith("PLAYPLAYLIST")) {

                } else if (messageFromClient.startsWith("FOLLOWPLAYLIST")) {

                } else if (messageFromClient.startsWith("GETALBUMS")) {

                }else if (messageFromClient.startsWith("ADDALBUM")) {

                }else if (messageFromClient.startsWith("DELETEALBUM")) {

                }else if (messageFromClient.startsWith("EDITALBUM")) {

                }else if (messageFromClient.startsWith("PLAYALBUM")) {

                }else if (messageFromClient.startsWith("FOLLOWALBUM")) {

                }else if (messageFromClient.startsWith("GETUSERS")) {

                }else if (messageFromClient.startsWith("ADDUSER")) {

                }else if (messageFromClient.startsWith("EDITUSER")) {

                }else if (messageFromClient.startsWith("FOLLOWUSER")) {

                }else if (messageFromClient.startsWith("GETARTISTS")) {

                }else if (messageFromClient.startsWith("ADDARTIST")) {

                }else if (messageFromClient.startsWith("EDITARTIST")) {

                }else if (messageFromClient.startsWith("FOLLOWARTIST")) {

                }else if (messageFromClient.startsWith("GETIMAGEFILE")) {

                }else if (messageFromClient.startsWith("SETIMAGEFILE")) {

                }else if (messageFromClient.startsWith("GETSONGFILE")) {

                }else if (messageFromClient.startsWith("SETSONGFILE")) {

                }else if (messageFromClient.startsWith("LOGIN")) {

                }else if (messageFromClient.startsWith("LOGOUT")) {

                }else if (messageFromClient.startsWith("SEARCH")) {

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
