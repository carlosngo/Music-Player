package DAO;

import Model.*;
import java.util.*;

public interface PlaylistDAO {

    Playlist find(int playlistId);

    // list all playlists of a user
    ArrayList<Playlist> listById(int userId);

    // list all favorite playlists of a user
    ArrayList<Playlist> listFavorites(int userId);

    // checks if a playlist already exists for a user
    Playlist findByName(String playlistName, int userId);

    void create(Playlist playlist) throws IllegalArgumentException;

    void delete(Playlist playlist);

    void update(Playlist playlist) throws IllegalArgumentException;

}
