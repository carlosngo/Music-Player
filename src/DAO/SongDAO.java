package DAO;

import Model.*;
import java.util.*;

public interface SongDAO {
    Song find(int id);

    ArrayList<Song> findByGenre();

    ArrayList<Song> findByAlbum();

    ArrayList<Song> findByYear();

    void create(Song song);

    void delete(Song song);

    void update(Song song);

}
