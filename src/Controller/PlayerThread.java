package Controller;

import Model.*;

import java.awt.event.*;
import java.util.*;
import javax.media.*;

public class PlayerThread implements ControllerListener, Runnable, ActionListener {
    PlayerController pc;
    ArrayList<Song> songs;
    Stack<Song> played;
    ArrayList<Song> unplayed;
    boolean isFinished = false;

    public PlayerThread(PlayerController pc, ArrayList<Song> songs) {
        this.pc = pc;
        this.pc.attach(this);
        pc.getPlayerPanel().addControlListener(this);
        this.songs = songs;
        unplayed = (ArrayList<Song>)songs.clone();
        played = new Stack<>();
    }

    @Override
    public void controllerUpdate(ControllerEvent controllerEvent) {
        if (controllerEvent instanceof EndOfMediaEvent) {
            // end of media actions here
            if (unplayed.isEmpty()) {
                if (pc.isRepeat()) unplayed = (ArrayList<Song>)songs.clone();
                else isFinished = true;
            }
            if (!isFinished) {
                int nextIndex = 0;
                if (pc.isShuffle()) nextIndex = (int)(Math.random() * unplayed.size());
                Song nextSong = unplayed.get(nextIndex);
                playSong(nextSong);
            }
        }

    }

    @Override
    public void run() {
        playSong(songs.get(0));
        while (!isFinished);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            if (unplayed.isEmpty()) return;
            int nextIndex = 0;
            if (pc.isShuffle()) nextIndex = (int)(Math.random() * unplayed.size());
            Song nextSong = unplayed.get(nextIndex);
            playSong(nextSong);
        } else if (e.getActionCommand().equals("Prev")) {
            if (played.empty()) return;
            Song nextSong = played.pop();
            unplayed.add(nextSong);
            playSong(nextSong);
        }
    }

    private void playSong(Song song) {
        unplayed.remove(song);
        played.push(song);
//        pc.setMediaLocator(new MediaLocator(song.getURL()),);
    }
}
