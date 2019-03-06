package DAO;

import Model.*;
import java.util.*;
;
public interface PlaylistSongDAO {
    void join(Playlist p, Song s);

    ArrayList<Integer> listByPlaylistId(int playlistId);

    ArrayList<Integer> listBySongId(int songId);
}
