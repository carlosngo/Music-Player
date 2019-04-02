package events;

import model.Song;

public class PlayEvent {
    Object source;
    Song songPlayed;

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Song getSongPlayed() {
        return songPlayed;
    }

    public void setSongPlayed(Song songPlayed) {
        this.songPlayed = songPlayed;
    }
}
