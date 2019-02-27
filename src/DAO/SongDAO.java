package DAO;

import Model.*;

public interface SongDAO {
    Song find(int id);

    void create(Song song);

    void delete(Song song);

    void update(Song song);

}
