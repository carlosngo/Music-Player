package util;

import model.Song;

import java.util.*;

public class SongIterator extends Iterator {
    private SongQueue queue;
    private boolean isRepeat;
    private boolean isShuffle;
    private boolean isFinished;

    public SongIterator(SongQueue queue) {
        this.queue = queue;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void setRepeat(boolean isRepeat) {
        this.isRepeat = isRepeat;
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    @Override
    public Object first() {
        return null;
    }

    @Override
    public Object next() {
        Song nextSong = null;
        if (queue.getUnplayedSongs().isEmpty()) {
            if (isRepeat) {
                System.out.println("Repeating...");
                queue.getPlayedSongs().clear();
                queue.setUnplayedSongs((ArrayList<Song>)queue.getAllSongs().clone());
            }
            else {
                isFinished = true;
                return null;
            }
        }
        if (!isFinished) {
            int nextIndex = 0;
            if (isShuffle) nextIndex = (int)(Math.random() * queue.getUnplayedSongs().size());
            nextSong = queue.getUnplayedSongs().get(nextIndex);
            System.out.println("Next song is " + nextSong.getName());
        }
        return nextSong;
    }

    @Override
    public boolean isDone() {
        return isFinished;
    }

    @Override
    public Object currentItem() {
        if (queue.getPlayedSongs().isEmpty()) return null;
        return queue.getPlayedSongs().peek();
    }
}
