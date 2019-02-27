package DAO;

import Model.*;

public interface GenreDAO {

    Genre find(int genreId);

    void create(Genre genre);

    void delete(Genre genre);

    void update(Genre genre);

}
