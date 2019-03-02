package Controller;

import View.*;
import javax.media.*;
import java.io.*;
import javax.swing.*;

public class PlayerController {
    private Player player = null;
    public PlayerPanel pp;

    public PlayerController() {
        pp = new PlayerPanel(this);


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

        // refresh the tabbed pane.\
        pp.update(title, artist, player.getControlPanelComponent());
        System.out.println("Refreshed.");
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

    public static void main(String[] args) throws Exception {
        JFrame frm = new JFrame();
        PlayerController pc = new PlayerController();
        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        pc.setMediaLocator(new MediaLocator(fc.getSelectedFile().toURI().toURL()), "Song1", "Artist1");
//        fc.showOpenDialog(null);
//        pc.setMediaLocator(new MediaLocator(fc.getSelectedFile().toURI().toURL()), "Song2", "Artist2");
        frm.setContentPane(pc.pp);
        frm.pack();
        frm.setVisible(true);
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
