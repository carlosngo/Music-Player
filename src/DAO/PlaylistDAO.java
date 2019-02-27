package DAO;

import Model.*;

public interface PlaylistDAO {

    Playlist find(int playlistId);

    void create(Playlist playlist);

    void delete(Playlist playlist);

    void update(Playlist playlist);

}
