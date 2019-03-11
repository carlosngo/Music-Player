package Controller;

import DAO.*;
import Model.*;
import View.*;
import Controller.*;

import javax.media.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;


public class PlayerController implements ControllerListener, ActionListener {
    private MainController mc;

    private Player player = null;
    private ArrayList<Song> songs;
    private Stack<Song> played;
    private ArrayList<Song> unplayed;
    private boolean isFinished = false;

    private PlayerPanel pp;

    public PlayerController(MainController mc)
    {
        this.mc = mc;
        clearQueue();
        pp = new PlayerPanel(this);
    }

    public MainController getMainController() {
        return mc;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
        unplayed = (ArrayList<Song>)songs.clone();
        played = new Stack<>();
    }

    public void addSong(Song song) {

        songs.add(song);
        unplayed.add(song);
    }

    public void startPlayer() {
        if (!unplayed.isEmpty()) {
            isFinished = false;
            playSong(getNextSong());
        }
    }

    public void terminate() {
        pp.update(null, "", "", new JLabel());
        System.out.println("Terminating");
        isFinished = true;
        clearQueue();
    }

    @Override
    public void controllerUpdate(ControllerEvent controllerEvent) {
        if (controllerEvent instanceof EndOfMediaEvent) {
            // end of media actions here
            System.out.println("End of media");
            Song nextSong = getNextSong();
            System.out.println("Next song = " + nextSong);
            playSong(nextSong);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            Song nextSong = getNextSong();
            if (nextSong != null) {
//                System.out.println("Next song not null");
                playSong(nextSong);
            }
        } else if (e.getActionCommand().equals("Prev")) {
            if (played.size() < 2) return;
            Song currentSong = played.pop();
            unplayed.add(currentSong);
            Song prevSong = played.pop();
            unplayed.add(prevSong);
            playSong(prevSong);
        }
    }

    public void removeSong(Song song) {
        if (played.peek().equals(song)) { // if the song is currently playing
            Song nextSong = getNextSong();
            playSong(nextSong);
        }
        songs.remove(song);
        unplayed.remove(song);
        played.remove(song);
    }

    private Song getNextSong() {
        Song nextSong = null;
        if (unplayed.isEmpty()) {
            if (isRepeat()) {
                System.out.println("Repeating...");
                played = new Stack<>();
                unplayed = (ArrayList<Song>)songs.clone();
            }
            else {
                return null;
            }
        }
        if (!isFinished) {
            int nextIndex = 0;
            if (isShuffle()) nextIndex = (int)(Math.random() * unplayed.size());
            nextSong = unplayed.get(nextIndex);
            System.out.println("Next song is " + nextSong.getName());
        }
        return nextSong;
    }

    private void playSong(Song song) {
//        System.out.println("Playing song " + song.getName());

        if (song == null) {
            terminate();
            return;
        }
        song.setPlayTime(song.getPlayTime() + 1); // increment play time
        System.out.println("Incremented play count of " + song.getName() + " to " + song.getPlayTime());
        unplayed.remove(song);
        played.push(song);
        File wav = song.getWAV();
        Album a = song.getAlbum();

        try {
            if (a != null) {
                setMediaLocator(new MediaLocator(wav.toURI().toURL()), a.getCover(), song.getName(), a.getName());
            } else {
                setMediaLocator(new MediaLocator(wav.toURI().toURL()), null, song.getName(), "No Album");
            }

        } catch (CannotRealizeException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (NoPlayerException e) {
            e.printStackTrace();
        }

    }
    /**
     * Sets the media locator.  Setting this to a new value effectively
     * discards any Player which may have already existed.
     * @param locator the new MediaLocator object.
     * @throws IOException indicates an IO error in opening the media.
     * @throws NoPlayerException indicates no player was found for the
     * media type.
     * @throws CannotRealizeException indicates an error in realizing the
     * media file or stream.
     */
    public void setMediaLocator(MediaLocator locator, File cover, String title, String artist) throws IOException,
            NoPlayerException, CannotRealizeException {

        // create a new player with the new locator.  This will effectively
        // stop and discard any current player.
        setPlayer(Manager.createRealizedPlayer(locator), cover, title, artist);
    }
    /**
     * Sets the player reference.  Setting this to a new value will discard
     * any Player which already exists.  It will also refresh the contents
     * of the pane with the components for the new player.  A null value will
     * stop the discard the current player and clear the contents of the
     * frame.
     */
    public void setPlayer(Player newPlayer, File cover, String title, String artist) {
        // close the current player
        closeCurrentPlayer();

        player = newPlayer;
        player.addControllerListener(this);
//        if (pt != null) attach(pt);
        // refresh the tabbed pane.\
        pp.update(cover, title, artist, player.getControlPanelComponent());
        player.start();
        if (player == null) return;
    }

    /**
     * Stops and closes the current player if one exists.
     */
    private void closeCurrentPlayer() {
        if (player != null) {
            player.stop();
            player.close();
        }
    }

    public PlayerPanel getPlayerPanel() {
        return pp;
    }

    public boolean isRepeat() {
        return pp.isRepeat();
    }

    public boolean isShuffle() {
        return pp.isShuffle();
    }

    public Player getPlayer() {
        return player;
    }

    public void clearQueue() {
        songs = new ArrayList<>();
        unplayed = new ArrayList<>();
        played = new Stack<>();
    }
    public static void main(String[] args) throws Exception {

        PlayerController pc = new PlayerController(new MainController());

        SongDAO songDAO = (SongDAO) new SongDAOFactory().getDAO();
        ArrayList<Song> queue = new ArrayList<>();
        queue.add(songDAO.find(4));
        queue.add(songDAO.find(5));
        pc.setSongs(queue);
        pc.startPlayer();
//        PlayerThread pt = new PlayerThread(pc, queue);
//        new Thread(pt).start();
//        JFileChooser fc = new JFileChooser();
//        fc.showOpenDialog(null);
//        File wav = fc.getSelectedFile();
//        if (wav != null) {
//            pc.setMediaLocator(new MediaLocator(fc.getSelectedFile().toURI().toURL()), "Song1", "Artist1");
//        } else {
//
//        }
        JFrame frm = new JFrame();
        frm.setContentPane(pc.getPlayerPanel());
        frm.pack();
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
