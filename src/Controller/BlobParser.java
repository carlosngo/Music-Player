package Controller;

import java.io.*;
import java.sql.*;

import DAO.DAOFactory;
import DAO.DriverManagerDAOFactory;
import DAO.SongDAO;
import Model.Song;

public class BlobParser {
    private static BlobStrategy bs;

    public static void setStrategy(BlobStrategy bs) {
        BlobParser.bs = bs;
    }
    
    public static void executeStrategy(Blob blob, File file) {
        bs.execute(blob, file);
    }
    
    
    public static void main(String[] args) {
        DAOFactory db = new DriverManagerDAOFactory("jdbc:mysql://localhost:3306/musicplayer", "root", "zerovit098");
        SongDAO songDAO = db.getSongDAO();
        
        Song song = songDAO.find(1);
//        BlobParser.setStrategy(new BlobToFile("resources/music/", song.getFileName(), ".wav"));
//        BlobParser.executeStrategy(song.getFile());
    }
}
