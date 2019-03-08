package Controller;

import Model.*;
import DAO.*;

import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;
import javax.media.*;
import java.io.*;

public class PlayerThread implements ControllerListener, Runnable, ActionListener {
    private PlayerController pc;
    private ArrayList<Song> songs;
    private Stack<Song> played;
    private ArrayList<Song> unplayed;
    private boolean isFinished = false;

    public PlayerThread(PlayerController pc, ArrayList<Song> songs) {
        this.songs = songs;
        unplayed = (ArrayList<Song>)songs.clone();
        played = new Stack<>();
        this.pc = pc;
        if (this.pc.getPlayer() == null) {
            playSong(unplayed.get(0));
        }
        this.pc.attach(this);
        pc.getPlayerPanel().addControlListener(this);

    }

    @Override
    public void controllerUpdate(ControllerEvent controllerEvent) {
        if (controllerEvent instanceof EndOfMediaEvent) {
            // end of media actions here
            if (unplayed.isEmpty()) {
                if (pc.isRepeat()) {
                    System.out.println("Repeating...");
                    played = new Stack<>();
                    unplayed = (ArrayList<Song>)songs.clone();
                }
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
        while (!isFinished);
    }

    public void terminate() { isFinished = true; }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Next")) {
            if (unplayed.isEmpty()) return;
            int nextIndex = 0;
            if (pc.isShuffle()) nextIndex = (int)(Math.random() * unplayed.size());
            Song nextSong = unplayed.get(nextIndex);
            playSong(nextSong);
        } else if (e.getActionCommand().equals("Prev")) {
            if (played.size() < 2) return;
            Song currentSong = played.pop();
            unplayed.add(currentSong);
            Song prevSong = played.pop();
            unplayed.add(prevSong);
            playSong(prevSong);
        }
    }

    private void playSong(Song song) {
        System.out.println("Playing song " + song.getName());
        unplayed.remove(song);
        played.push(song);
        BlobParser.setStrategy(new BlobToFile());
        File dir = new File("resources/songs");
        dir.mkdirs();
        File wav = new File(dir,song.getSongId() + "");
        try {
            wav.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BlobParser.executeStrategy(song.getFile(), wav);
        AlbumDAO albumDAO = pc.getMainController().getAlbumDAO();
        String coverName = albumDAO.find(song.getAlbumId()).getName();
        try {

            pc.setMediaLocator(new MediaLocator(wav.toURI().toURL()), song.getName(), coverName);
        } catch (CannotRealizeException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (NoPlayerException e) {
            e.printStackTrace();
        }

    }
}
