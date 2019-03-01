package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class ControlPanel extends JPanel implements ActionListener {
    private JButton mostFrqntlyPlyd, playlists, artists, albums, songs, genres, years, addPlaylist;

    public ControlPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        //add(Box.createRigidArea(new Dimension(15,0)));

        mostFrqntlyPlyd = new JButton();
        mostFrqntlyPlyd.setAlignmentX(Component.LEFT_ALIGNMENT);
        mostFrqntlyPlyd.addActionListener(this);
        mostFrqntlyPlyd.setForeground(Color.white);
        mostFrqntlyPlyd.setOpaque(false);
        mostFrqntlyPlyd.setContentAreaFilled(false);
        mostFrqntlyPlyd.setBorderPainted(false);
        mostFrqntlyPlyd.setMaximumSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setMinimumSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setPreferredSize(new Dimension(200, 40));
        mostFrqntlyPlyd.setFont(new Font("Arial", Font.BOLD, 14));
        add(mostFrqntlyPlyd);
        songs = new JButton();
        songs.setAlignmentX(Component.LEFT_ALIGNMENT);
        songs.setForeground(Color.white);
        songs.addActionListener(this);
        songs.setOpaque(false);
        songs.setContentAreaFilled(false);
        songs.setBorderPainted(false);
        songs.setMaximumSize(new Dimension(200, 40));
        songs.setMinimumSize(new Dimension(200, 40));
        songs.setPreferredSize(new Dimension(200, 40));
        songs.setFont(new Font("Arial", Font.BOLD, 14));
        add(songs);
        playlists = new JButton();
        playlists.setAlignmentX(Component.LEFT_ALIGNMENT);
        playlists.setForeground(Color.white);
        playlists.addActionListener(this);
        playlists.setOpaque(false);
        playlists.setContentAreaFilled(false);
        playlists.setBorderPainted(false);
        playlists.setMaximumSize(new Dimension(200, 40));
        playlists.setMinimumSize(new Dimension(200, 40));
        playlists.setPreferredSize(new Dimension(200, 40));
        playlists.setFont(new Font("Arial", Font.BOLD, 14));
        add(playlists);
        artists = new JButton();
        artists.setAlignmentX(Component.LEFT_ALIGNMENT);
        artists.setForeground(Color.white);
        artists.addActionListener(this);
        artists.setOpaque(false);
        artists.setContentAreaFilled(false);
        artists.setBorderPainted(false);
        artists.setMaximumSize(new Dimension(200, 40));
        artists.setMinimumSize(new Dimension(200, 40));
        artists.setPreferredSize(new Dimension(200, 40));
        artists.setFont(new Font("Arial", Font.BOLD, 14));
        add(artists);
        albums = new JButton();
        albums.setAlignmentX(Component.LEFT_ALIGNMENT);
        albums.setForeground(Color.white);
        albums.addActionListener(this);
        albums.setOpaque(false);
        albums.setContentAreaFilled(false);
        albums.setBorderPainted(false);
        albums.setMaximumSize(new Dimension(200, 40));
        albums.setMinimumSize(new Dimension(200, 40));
        albums.setPreferredSize(new Dimension(200, 40));
        albums.setFont(new Font("Arial", Font.BOLD, 14));
        add(albums);
        genres = new JButton();
        genres.setAlignmentX(Component.LEFT_ALIGNMENT);
        genres.setForeground(Color.white);
        genres.addActionListener(this);
        genres.setOpaque(false);
        genres.setContentAreaFilled(false);
        genres.setBorderPainted(false);
        genres.setMaximumSize(new Dimension(200, 40));
        genres.setMinimumSize(new Dimension(200, 40));
        genres.setPreferredSize(new Dimension(200, 40));
        genres.setFont(new Font("Arial", Font.BOLD, 14));
        add(genres);
//        JPanel addPlaylistPnl = new JPanel();
//        addPlaylistPnl.setLayout(new BoxLayout(addPlaylistPnl, BoxLayout.X_AXIS));
//        addPlaylistPnl.setOpaque(false);
        addPlaylist = new JButton();
        addPlaylist.setAlignmentX(Component.LEFT_ALIGNMENT);
        addPlaylist.setForeground(Color.white);
        addPlaylist.addActionListener(this);
        addPlaylist.setOpaque(false);
        addPlaylist.setContentAreaFilled(false);
        addPlaylist.setBorderPainted(false);
        addPlaylist.setMaximumSize(new Dimension(200, 40));
        addPlaylist.setMinimumSize(new Dimension(200, 40));
        addPlaylist.setPreferredSize(new Dimension(200, 40));
        addPlaylist.setFont(new Font("Arial", Font.BOLD, 14));
//        addPlaylistPnl.setBorder(border);
//        addPlaylistPnl.add(addPlaylist);
        add(addPlaylist);
        /*
        years = new JButton("Years");
        years.setForeground(Color.white);
        years.addActionListener(this);
        years.setOpaque(false);
        years.setContentAreaFilled(false);
        years.setBorderPainted(false);
        add(years);
        */

        try{
            URL resource = getClass().getClassLoader().getResource("mostPlayed.png");
            File img = Paths.get(resource.toURI()).toFile();
            mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            mostFrqntlyPlyd.setText("Most Played");
            resource = getClass().getClassLoader().getResource("songs.png");
            img = Paths.get(resource.toURI()).toFile();
            songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            songs.setText("Songs");
            resource = getClass().getClassLoader().getResource("playlists.png");
            img = Paths.get(resource.toURI()).toFile();
            playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            playlists.setText("Playlists");
            resource = getClass().getClassLoader().getResource("artists.png");
            img = Paths.get(resource.toURI()).toFile();
            artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            artists.setText("Artists");
            resource = getClass().getClassLoader().getResource("albums.png");
            img = Paths.get(resource.toURI()).toFile();
            albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            albums.setText("Albums");
            resource = getClass().getClassLoader().getResource("genre.png");
            img = Paths.get(resource.toURI()).toFile();
            genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            genres.setText("Genres");
            resource = getClass().getClassLoader().getResource("addPlaylist.png");
            img = Paths.get(resource.toURI()).toFile();
            addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            addPlaylist.setText("Add Playlist");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mostFrqntlyPlyd){

        }
        if(e.getSource() == playlists){

        }
        if(e.getSource() == artists){

        }
        if(e.getSource() == albums){

        }
        if(e.getSource() == songs){

        }
        if(e.getSource() == genres){

        }
        if(e.getSource() == addPlaylist){

        }
    }
}
