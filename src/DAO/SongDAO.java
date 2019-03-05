package DAO;

import Model.*;
import java.util.*;

public interface SongDAO {
    Song find(int id);

    ArrayList<Song> findByGenre(int userId);

    ArrayList<Song> findByAlbum(int userId);

    ArrayList<Song> findByYear(int userId);

    // list all songs of a genre for a user
    ArrayList<Song> listByGenre(int genreId, int userId);

    // list all songs of a playlist for a user
    ArrayList<Song> listByPlaylist(int playlistId, int userId);

    // list all songs of an album for a user
    ArrayList<Song> listByAlbum(int albumId, int userId);

    // list favorite songs of a user
    ArrayList<Song> listFavorites(int userId);


    void create(Song song);

    void delete(Song song);

    void update(Song song);

}
