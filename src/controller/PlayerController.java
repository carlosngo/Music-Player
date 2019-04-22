package controller;

import dao.*;
import model.*;
import net.Client;
import util.*;
import view.*;

import javax.media.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class PlayerController implements ControllerListener, ActionListener {
    private MainController mc;

    private Client client;
    private Player player = null;
    private SongQueue queue;
    private SongIterator iterator;
    private boolean isFinished = false;

    private PlayerPanel pp;

    public PlayerController(MainController mc)
    {
        this.mc = mc;
        client = mc.getClient();
        queue = new SongQueue();
        iterator = (SongIterator)queue.createIterator();
        pp = new PlayerPanel(this);
    }

    public MainController getMainController() {
        return mc;
    }

    public void setSongs(ArrayList<Song> songs) {
        queue.getAllSongs().clear();
        queue.getAllSongs().addAll(songs);
        queue.getUnplayedSongs().clear();
        queue.getUnplayedSongs().addAll(queue.getAllSongs());
    }

    public void addSong(Song song) {
        queue.getAllSongs().add(song);
        queue.getUnplayedSongs().add(song);
        if(queue.getPlayedSongs().isEmpty()) startPlayer();
    }

    public void startPlayer() {
        if (!queue.getUnplayedSongs().isEmpty()) {
            iterator.setFinished(false);
            playSong((Song)iterator.next());
        }
    }

    public void terminate() {
        pp.update(new JLabel());
        System.out.println("Terminating");
        closeCurrentPlayer();
        queue.clear();
    }

    @Override
    public void controllerUpdate(ControllerEvent controllerEvent) {
        if (controllerEvent instanceof EndOfMediaEvent) {
            // end of media actions here
            System.out.println("End of media");
            Song nextSong = (Song)iterator.next();
            System.out.println("Next song = " + nextSong);
            playSong(nextSong);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            Song nextSong = (Song)iterator.next();
            if (nextSong != null) {
                playSong(nextSong);
            }
        } else if (e.getActionCommand().equals("Prev")) {
            if (queue.getPlayedSongs().size() < 2) return;
            Song currentSong = queue.getPlayedSongs().pop();
            queue.getUnplayedSongs().add(currentSong);
            Song prevSong = queue.getPlayedSongs().pop();
            queue.getUnplayedSongs().add(prevSong);
            playSong(prevSong);
        }
    }

    public void removeSong(Song song) {
        if (!queue.getPlayedSongs().isEmpty() && queue.getPlayedSongs().peek().equals(song)) { // if the song is currently playing
            Song nextSong = (Song)iterator.next();
            playSong(nextSong);
        }
        queue.getAllSongs().remove(song);
        queue.getUnplayedSongs().remove(song);
        queue.getPlayedSongs().remove(song);
    }

    public Song getCurrentSong() {
        return (Song) iterator.currentItem();
    }

    private void playSong(Song song) {
        if (song == null) {
            terminate();
            return;
        }
        queue.getUnplayedSongs().remove(song);
        queue.getPlayedSongs().push(song);
        File wav = client.getSongFile(song.getSongId());
        Album a = song.getAlbum();
        try {
            setMediaLocator(new MediaLocator(wav.toURI().toURL()));
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
    public void setMediaLocator(MediaLocator locator) throws IOException,
            NoPlayerException, CannotRealizeException {

        // create a new player with the new locator.  This will effectively
        // stop and discard any current player.
        setPlayer(Manager.createRealizedPlayer(locator));
    }
    /**
     * Sets the player reference.  Setting this to a new value will discard
     * any Player which already exists.  It will also refresh the contents
     * of the pane with the components for the new player.  A null value will
     * stop the discard the current player and clear the contents of the
     * frame.
     */
    public void setPlayer(Player newPlayer) {
        // close the current player
        closeCurrentPlayer();

        player = newPlayer;
        player.addControllerListener(this);
//        if (pt != null) attach(pt);
        // refresh the tabbed pane.\
        pp.update(player.getControlPanelComponent());
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

    public void setRepeat(boolean isRepeat) {
        iterator.setRepeat(isRepeat);
    }

    public void setShuffle(boolean isShuffle) {
        iterator.setShuffle(isShuffle);
    }

    public Player getPlayer() {
        return player;
    }

    public static void main(String[] args) throws Exception {

        PlayerController pc = new PlayerController(new MainController());

        SongDAO songDAO = (SongDAO) new SongDAOFactory().getDAO();
        ArrayList<Song> queue = new ArrayList<>();
        queue.add(songDAO.find(18));
//        queue.add(songDAO.find(5));
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
