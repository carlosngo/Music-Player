package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ControlPanel extends JPanel implements ActionListener {
    private SongController controller;
    private JButton mostFrqntlyPlyd, playlists, artists, albums, songs, genres, years, addPlaylist;

    public ControlPanel(SongController controller){
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));

//        JPanel buttonsPnl = new JPanel();
//        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.Y_AXIS));
//        buttonsPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
//        buttonsPnl.setOpaque(false);
        mostFrqntlyPlyd = new JButton();
        mostFrqntlyPlyd.setActionCommand("Most Frequently Played");
        //mostFrqntlyPlyd.setAlignmentX(Component.LEFT_ALIGNMENT);
        mostFrqntlyPlyd.addActionListener(this);
        mostFrqntlyPlyd.setForeground(Color.white);
        mostFrqntlyPlyd.setOpaque(false);
        mostFrqntlyPlyd.setContentAreaFilled(false);
        mostFrqntlyPlyd.setBorderPainted(false);
        mostFrqntlyPlyd.setMaximumSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setMinimumSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setPreferredSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setFont(new Font("Arial", Font.BOLD, 14));
        mostFrqntlyPlyd.addMouseListener(new MouseAdapter() {
            Color oldColor = mostFrqntlyPlyd.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanMostPlayed.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/mostPlayed.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(mostFrqntlyPlyd);
        add(mostFrqntlyPlyd);
        songs = new JButton();
        songs.setActionCommand("Songs");
        //songs.setAlignmentX(Component.LEFT_ALIGNMENT);
        songs.setForeground(Color.white);
        songs.addActionListener(this);
        songs.setOpaque(false);
        songs.setContentAreaFilled(false);
        songs.setBorderPainted(false);
        songs.setMaximumSize(new Dimension(200, 40));
        songs.setMinimumSize(new Dimension(200, 40));
        songs.setPreferredSize(new Dimension(200, 40));
        songs.setFont(new Font("Arial", Font.BOLD, 14));
        songs.addMouseListener(new MouseAdapter() {
            Color oldColor = songs.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanSongs.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/songs.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(songs);
        add(songs);
        playlists = new JButton();
        playlists.setActionCommand("Playlists");
        //playlists.setAlignmentX(Component.LEFT_ALIGNMENT);
        playlists.setForeground(Color.white);
        playlists.addActionListener(this);
        playlists.setOpaque(false);
        playlists.setContentAreaFilled(false);
        playlists.setBorderPainted(false);
        playlists.setMaximumSize(new Dimension(200, 40));
        playlists.setMinimumSize(new Dimension(200, 40));
        playlists.setPreferredSize(new Dimension(200, 40));
        playlists.setFont(new Font("Arial", Font.BOLD, 14));
        playlists.addMouseListener(new MouseAdapter() {
            Color oldColor = playlists.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanPlaylists.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/playlists.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(playlists);
        add(playlists);
       
     
        albums = new JButton();
        albums.setActionCommand("Albums");
        //albums.setAlignmentX(Component.LEFT_ALIGNMENT);
        albums.setForeground(Color.white);
        albums.addActionListener(this);
        albums.setOpaque(false);
        albums.setContentAreaFilled(false);
        albums.setBorderPainted(false);
        albums.setMaximumSize(new Dimension(200, 40));
        albums.setMinimumSize(new Dimension(200, 40));
        albums.setPreferredSize(new Dimension(200, 40));
        albums.setFont(new Font("Arial", Font.BOLD, 14));
        albums.addMouseListener(new MouseAdapter() {
            Color oldColor = albums.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAlbums.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/albums.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(albums);
        add(albums);
        genres = new JButton();
        genres.setActionCommand("Genres");
        //genres.setAlignmentX(Component.LEFT_ALIGNMENT);
        genres.setForeground(Color.white);
        genres.addActionListener(this);
        genres.setOpaque(false);
        genres.setContentAreaFilled(false);
        genres.setBorderPainted(false);
        genres.setMaximumSize(new Dimension(200, 40));
        genres.setMinimumSize(new Dimension(200, 40));
        genres.setPreferredSize(new Dimension(200, 40));
        genres.setFont(new Font("Arial", Font.BOLD, 14));
        genres.addMouseListener(new MouseAdapter() {
            Color oldColor = genres.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanGenre.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/genre.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(genres);
        add(genres);
//        JPanel addPlaylistPnl = new JPanel();
//        addPlaylistPnl.setLayout(new BoxLayout(addPlaylistPnl, BoxLayout.X_AXIS));
//        addPlaylistPnl.setOpaque(false);
        addPlaylist = new JButton();
        addPlaylist.setActionCommand("Add Playlist");
        //addPlaylist.setAlignmentX(Component.LEFT_ALIGNMENT);
        addPlaylist.setForeground(Color.white);
        addPlaylist.addActionListener(this);
        addPlaylist.setOpaque(false);
        addPlaylist.setContentAreaFilled(false);
        addPlaylist.setBorderPainted(false);
        addPlaylist.setMaximumSize(new Dimension(200, 40));
        addPlaylist.setMinimumSize(new Dimension(200, 40));
        addPlaylist.setPreferredSize(new Dimension(200, 40));
        addPlaylist.setFont(new Font("Arial", Font.BOLD, 14));
        addPlaylist.addMouseListener(new MouseAdapter() {
            Color oldColor = addPlaylist.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddPlaylist.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addPlaylist.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(addPlaylist);
        add(addPlaylist);

        try{
            URL resource = getClass().getClassLoader().getResource("images/mostPlayed.png");
            File img = Paths.get(resource.toURI()).toFile();
            mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            mostFrqntlyPlyd.setText("Most Played");
            resource = getClass().getClassLoader().getResource("images/songs.png");
            img = Paths.get(resource.toURI()).toFile();
            songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            songs.setText("Songs");
            resource = getClass().getClassLoader().getResource("images/playlists.png");
            img = Paths.get(resource.toURI()).toFile();
            playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            playlists.setText("Playlists");
            
            resource = getClass().getClassLoader().getResource("images/albums.png");
            img = Paths.get(resource.toURI()).toFile();
            albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            albums.setText("Albums");
            resource = getClass().getClassLoader().getResource("images/genre.png");
            img = Paths.get(resource.toURI()).toFile();
            genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            genres.setText("Genres");
            resource = getClass().getClassLoader().getResource("images/addPlaylist.png");
            img = Paths.get(resource.toURI()).toFile();
            addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            addPlaylist.setText("Add Playlist");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //add(buttonsPnl);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == mostFrqntlyPlyd){
            controller.showMostFrequentlyPlayed();
        }
        if(e.getSource() == playlists){
            controller.showPlaylists();
        }
        if(e.getSource() == albums){
            controller.showAlbums();
        }
        if(e.getSource() == songs){
            controller.showAllSongs();
        }
        if(e.getSource() == genres){
            controller.showGenres();
        }
        if(e.getSource() == addPlaylist){
            //controller.showPlaylists();
            AddPlaylistWindow apw = new AddPlaylistWindow();
        }
    }
}
