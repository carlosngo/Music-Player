package Model;

public class PlaylistSong implements Comparable<PlaylistSong> {
    private int songId;
    private int playlistId;

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public int compareTo(PlaylistSong o) {
        return Integer.compare(playlistId, o.playlistId);
    }
}
