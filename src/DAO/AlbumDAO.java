package DAO;

import Model.*;

public interface AlbumDAO {

    Album find(int albumId);

    void create(Album album);

    void delete(Album album);

    void update(Album album);

}
