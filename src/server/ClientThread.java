package server;

import model.*;
import java.util.*;
import java.io.*;
import java.net.*;

public class ClientThread implements Runnable, Observer {
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
    public void update(Observable o, Object arg) {

    }

    @Override
    public void run() {System.out.println("Started thread for client.");
        Server server = Server.getInstance();
        String messageFromClient;
        try {
            while (!server.isShutDown() && (messageFromClient = in.readLine()) != null) {
                System.out.println("Received the message: " + messageFromClient + " from client.");

                StringBuilder reply = new StringBuilder();
                if (messageFromClient.startsWith("GETSONGS")) {
                    ArrayList<Song> songs = server.getSongs();
                    reply.append(songs.size());
                    reply.append("\n");
                    for (int i = 0; i < songs.size(); i++) {
                        reply.append(songs.get(i).toString());
                        reply.append("\n");
                    }

                } else if (messageFromClient.startsWith("ADDSONG")) {
                    server.addSong();
                } else if (messageFromClient.equals("DELETESONG")) {
                    server.deleteSong(messageFromClient.substring(10));
                } else if (messageFromClient.startsWith("EDITSONG")) {
                    server.editSong(messageFromClient.substring(8));

                } else if (messageFromClient.startsWith("PLAYSONG")) {
                    server.playSong(messageFromClient.substring(8));

                } else if (messageFromClient.startsWith("FOLLOWSONG")) {
                    server.followSong(messageFromClient.substring(10));

                } else if (messageFromClient.startsWith("GETPLAYLISTS")) {
                    ArrayList<Playlist> playlists = server.getPlaylists();
                    reply.append(playlists.size());
                    reply.append("\n");
                    for (int i = 0; i < playlists.size(); i++) {
                        reply.append(playlists.get(i).toString());
                        reply.append("\n");
                    }
                } else if (messageFromClient.startsWith("ADDPLAYLIST")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(messageFromClient.substring(11).trim());
                    sb.append("\n");
                    messageFromClient = in.readLine();
                    while (!messageFromClient.equals("END")) {
                        System.out.println(messageFromClient);
                        sb.append(messageFromClient);
                        sb.append("\n");
                        messageFromClient = in.readLine();
                    }
                    System.out.println("Going to add playlist:\n" + sb.toString());
                    Playlist playlist = Playlist.parsePlaylist(sb.toString());
                    server.addPlaylist(playlist);
                } else if (messageFromClient.startsWith("DELETEPLAYLIST")) {
                    server.deletePlaylist(messageFromClient.substring(14));

                } else if (messageFromClient.startsWith("EDITPLAYLIST")) {
                    server.editPlaylist(messageFromClient.substring(12));

                } else if (messageFromClient.startsWith("PLAYPLAYLIST")) {
                    server.playPlaylist(messageFromClient.substring(12));

                } else if (messageFromClient.startsWith("FOLLOWPLAYLIST")) {
                    server.followPlaylist(messageFromClient.substring(14));

                } else if (messageFromClient.startsWith("GETALBUMS")) {
                    server.getAlbums();

                }else if (messageFromClient.startsWith("ADDALBUM")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(messageFromClient.substring(8).trim());
                    sb.append("\n");
                    messageFromClient = in.readLine();
                    while (!messageFromClient.equals("END")) {
                        System.out.println(messageFromClient);
                        sb.append(messageFromClient);
                        sb.append("\n");
                        messageFromClient = in.readLine();
                    }
                    System.out.println("Going to add quiz:\n" + sb.toString());
                    Album album = Album.parseAlbum(sb.toString());
                    server.addAlbum(album);
                }else if (messageFromClient.startsWith("DELETEALBUM")) {
                    server.deleteAlbum(messageFromClient.substring(11));

                }else if (messageFromClient.startsWith("EDITALBUM")) {
                    server.editAlbum(messageFromClient.substring(9));

                }else if (messageFromClient.startsWith("PLAYALBUM")) {
                    server.playAlbum(messageFromClient.substring(9));

                }else if (messageFromClient.startsWith("FOLLOWALBUM")) {
                    server.followAlbum(messageFromClient.substring(11));

                }else if (messageFromClient.startsWith("GETUSERS")) {
                    server.getUsers();

                }else if (messageFromClient.startsWith("ADDUSER")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(messageFromClient.substring(7).trim());
                    sb.append("\n");
                    messageFromClient = in.readLine();
                    while (!messageFromClient.equals("END")) {
                        System.out.println(messageFromClient);
                        sb.append(messageFromClient);
                        sb.append("\n");
                        messageFromClient = in.readLine();
                    }
                    System.out.println("Going to add quiz:\n" + sb.toString());
                    User user = User.parseUser(sb.toString());
                    server.addUser(user);
                }else if (messageFromClient.startsWith("EDITUSER")) {
                    server.editUser(messageFromClient.substring(8));

                }else if (messageFromClient.startsWith("FOLLOWUSER")) {
                    server.followUser(messageFromClient.substring(10));

                }else if (messageFromClient.startsWith("GETARTISTS")) {
                    server.getArtists();

                }else if (messageFromClient.startsWith("ADDARTIST")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(messageFromClient.substring(9).trim());
                    sb.append("\n");
                    messageFromClient = in.readLine();
                    while (!messageFromClient.equals("END")) {
                        System.out.println(messageFromClient);
                        sb.append(messageFromClient);
                        sb.append("\n");
                        messageFromClient = in.readLine();
                    }
                    System.out.println("Going to add quiz:\n" + sb.toString());
                    Artist artist = Artist.parseArtist(sb.toString());
                    server.addArtist(artist);
                }else if (messageFromClient.startsWith("EDITARTIST")) {
                    server.editArtist(messageFromClient.substring(10));

                }else if (messageFromClient.startsWith("FOLLOWARTIST")) {
                    server.followArtist(messageFromClient.substring(12));

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
}
