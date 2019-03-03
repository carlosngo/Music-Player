package Controller;

import Model.*;

import java.awt.event.*;
import java.util.*;
import javax.media.*;

public class PlayerThread implements ControllerListener, Runnable, ActionListener {
    PlayerController pc;
    ArrayList<Song> songs;
    ArrayList<Song> unplayed;

    public PlayerThread(PlayerController pc, ArrayList<Song> songs) {
        this.pc = pc;
        this.pc.attach(this);
        pc.getPlayerPanel().addControlListener(this);
        this.songs = songs;
        unplayed = (ArrayList<Song>)songs.clone();
    }

    @Override
    public void controllerUpdate(ControllerEvent controllerEvent) {
        if (controllerEvent instanceof EndOfMediaEvent) {
            // end of media actions here
            if (pc.isRepeat()) {

            }
            if (pc.isShuffle()) {

            }
        }

    }

    @Override
    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            if (pc.isRepeat()) {

            }
            if (pc.isShuffle()) {

            }
        } else if (e.getActionCommand().equals("Prev")) {
            if (pc.isRepeat()) {

            }
            if (pc.isShuffle()) {

            }
        }
    }

    private void playSong(Song song) {
        unplayed.remove(song);
//        pc.setMediaLocator(new MediaLocator(song.getURL()),);
    }
}
