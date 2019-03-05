package DAO;

import Model.*;
import java.sql.*;
import java.util.*;

public interface GenreDAO {

    Genre find(int genreId);

    ArrayList<Genre> listById(int userId);

    Genre findByName(String name, int userId);

    void create(Genre genre) throws IllegalArgumentException;

    void delete(Genre genre);

    void update(Genre genre) throws IllegalArgumentException;

}
