package DAO;

import Model.*;
import java.sql.*;
import java.util.*;

public interface GenreDAO {

    Genre find(int genreId) throws SQLException;

    ArrayList<Genre> listById(int userId) throws SQLException;

    boolean existGenre(String name, int userId) throws SQLException;

    void create(Genre genre) throws SQLException;

    void delete(Genre genre) throws SQLException;

    void update(Genre genre) throws SQLException;

}
