package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class ControlPanel extends JPanel implements ActionListener {
    private JButton mostFrqntlyPlyd, playlists, artists, albums, songs, genres, years, addPlaylist;

    public ControlPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));

        JPanel buttonsPnl = new JPanel();
        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.Y_AXIS));
        buttonsPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonsPnl.setOpaque(false);
        mostFrqntlyPlyd = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanMostPlayed.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("mostPlayed.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(oldColor);
            }
        });
        buttonsPnl.add(mostFrqntlyPlyd);
        songs = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanSongs.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("songs.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(oldColor);
            }
        });
        buttonsPnl.add(songs);
        playlists = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanPlaylists.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("playlists.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(oldColor);
            }
        });
        buttonsPnl.add(playlists);
        artists = new JButton();
        //artists.setAlignmentX(Component.LEFT_ALIGNMENT);
        artists.setForeground(Color.white);
        artists.addActionListener(this);
        artists.setOpaque(false);
        artists.setContentAreaFilled(false);
        artists.setBorderPainted(false);
        artists.setMaximumSize(new Dimension(200, 40));
        artists.setMinimumSize(new Dimension(200, 40));
        artists.setPreferredSize(new Dimension(200, 40));
        artists.setFont(new Font("Arial", Font.BOLD, 14));
        artists.addMouseListener(new MouseAdapter() {
            Color oldColor = artists.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("cyanArtist.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    artists.setText("Artists");
                } catch(Exception exception){

                }
                artists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("artists.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    artists.setText("Artists");
                } catch(Exception exception){

                }
                artists.setForeground(oldColor);
            }
        });
        buttonsPnl.add(artists);
        albums = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanAlbums.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("albums.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(oldColor);
            }
        });
        buttonsPnl.add(albums);
        genres = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanGenre.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("genre.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(oldColor);
            }
        });
        buttonsPnl.add(genres);
//        JPanel addPlaylistPnl = new JPanel();
//        addPlaylistPnl.setLayout(new BoxLayout(addPlaylistPnl, BoxLayout.X_AXIS));
//        addPlaylistPnl.setOpaque(false);
        addPlaylist = new JButton();
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
                    URL resource = getClass().getClassLoader().getResource("cyanAddPlaylist.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("addPlaylist.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(oldColor);
            }
        });
//        addPlaylistPnl.setBorder(border);
//        addPlaylistPnl.add(addPlaylist);
        buttonsPnl.add(addPlaylist);
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
        add(buttonsPnl);
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
