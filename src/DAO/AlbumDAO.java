package DAO;

import Model.*;
import java.util.*;

public interface AlbumDAO {

    Album find(int albumId);

    // list all albums of a user
    ArrayList<Album> listById(int userId);

    // check if album name exists for a certain user
    boolean existAlbum(String albumName, int userId);

    void create(Album album);

    void delete(Album album);

    void update(Album album);

}
