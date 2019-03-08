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

public class PlayerController {
    private MainController mc;
    private Player player = null;
    private PlayerPanel pp;
    private PlayerThread pt;


    public PlayerController(MainController mc)
    {
        this.mc = mc;
        pp = new PlayerPanel(this);
    }

    public MainController getMainController() {
        return mc;
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
    public void setMediaLocator(MediaLocator locator, String title, String artist) throws IOException,
            NoPlayerException, CannotRealizeException {

        // create a new player with the new locator.  This will effectively
        // stop and discard any current player.
        setPlayer(Manager.createRealizedPlayer(locator), title, artist);
    }
    /**
     * Sets the player reference.  Setting this to a new value will discard
     * any Player which already exists.  It will also refresh the contents
     * of the pane with the components for the new player.  A null value will
     * stop the discard the current player and clear the contents of the
     * frame.
     */
    public void setPlayer(Player newPlayer, String title, String artist) {
        // close the current player
        closeCurrentPlayer();

        player = newPlayer;
        if (pt != null) attach(pt);
        // refresh the tabbed pane.\
        pp.update(title, artist, player.getControlPanelComponent());
        player.start();
        if (player == null) return;
    }

    public void attach(PlayerThread pt) {
        if (this.pt != null) {
            player.removeControllerListener(this.pt);
            this.pt.terminate();
        }
        this.pt = pt;
        player.addControllerListener(pt);
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

    public static void main(String[] args) throws Exception {

//        PlayerController pc = new PlayerController();
//        DAOFactory db = new DriverManagerDAOFactory(DAOFactory.DATABASE_URL, DAOFactory.DATABASE_USERNAME, DAOFactory.DATABASE_PASSWORD);
//        SongDAO songDAO = db.getSongDAO();
//        ArrayList<Song> queue = new ArrayList<>();
//        queue.add(songDAO.find(4));
//        queue.add(songDAO.find(5));
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
//        JFrame frm = new JFrame();
//        frm.setContentPane(pc.getPlayerPanel());
//        frm.pack();
//        frm.setVisible(true);
//        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
