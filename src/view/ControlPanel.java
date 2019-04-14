package view;

import controller.*;
import util.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ControlPanel extends JPanel implements ActionListener {
    private SongController controller;
    private JButton search, mostFrqntlyPlyd, playlists, artists, albums, songs, genres, years, addPlaylist, favSongs,
            favPlaylists, addAlbum, addSong, friends;

    public ControlPanel(SongController controller/*, boolean isArtist*/){
        this.controller = controller;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));

        search = new JButton();
        search.setActionCommand("Search");
        search.addActionListener(this);
        search.setForeground(Color.white);
        search.setOpaque(false);
        search.setContentAreaFilled(false);
        search.setBorderPainted(false);
        search.setMaximumSize(new Dimension(200, 40));
        search.setMinimumSize(new Dimension(200, 40));
        search.setPreferredSize(new Dimension(200, 40));
        search.setFont(new Font("Arial", Font.BOLD, 14));
        search.addMouseListener(new MouseAdapter() {
            Color oldColor = search.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanBrowse.png");
                    BufferedImage img = ImageIO.read(resource);
                    search.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    search.setText("Search");
                } catch(Exception exception){

                }
                search.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/browse.png");
                    BufferedImage img = ImageIO.read(resource);
                    search.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    search.setText("Search");
                } catch(Exception exception){

                }
                search.setForeground(oldColor);
            }
        });
        add(search);

        mostFrqntlyPlyd = new JButton();
        mostFrqntlyPlyd.setActionCommand("Most Frequently Played");
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
                    BufferedImage img = ImageIO.read(resource);
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/mostPlayed.png");
                    BufferedImage img = ImageIO.read(resource);
                    mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    mostFrqntlyPlyd.setText("Most Played");
                } catch(Exception exception){

                }
                mostFrqntlyPlyd.setForeground(oldColor);
            }
        });
        //add(mostFrqntlyPlyd);

        favPlaylists = new JButton();
        favPlaylists.setActionCommand("Favorite Playlists");
        favPlaylists.addActionListener(this);
        favPlaylists.setForeground(Color.white);
        favPlaylists.setOpaque(false);
        favPlaylists.setContentAreaFilled(false);
        favPlaylists.setBorderPainted(false);
        favPlaylists.setMaximumSize(new Dimension(200, 40));
        favPlaylists.setMinimumSize(new Dimension(200, 40));
        favPlaylists.setPreferredSize(new Dimension(200, 40));
        favPlaylists.setFont(new Font("Arial", Font.BOLD, 14));
        favPlaylists.addMouseListener(new MouseAdapter() {
            Color oldColor = favPlaylists.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanStar.png");
                    BufferedImage img = ImageIO.read(resource);
                    favPlaylists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    favPlaylists.setText("Favorite Playlists");
                } catch(Exception exception){

                }
                favPlaylists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/star.png");
                    BufferedImage img = ImageIO.read(resource);
                    favPlaylists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    favPlaylists.setText("Favorite Playlists");
                } catch(Exception exception){

                }
                favPlaylists.setForeground(oldColor);
            }
        });
        //add(favPlaylists);

        favSongs = new JButton();
        favSongs.setActionCommand("Favorite Songs");
        favSongs.addActionListener(this);
        favSongs.setForeground(Color.white);
        favSongs.setOpaque(false);
        favSongs.setContentAreaFilled(false);
        favSongs.setBorderPainted(false);
        favSongs.setMaximumSize(new Dimension(200, 40));
        favSongs.setMinimumSize(new Dimension(200, 40));
        favSongs.setPreferredSize(new Dimension(200, 40));
        favSongs.setFont(new Font("Arial", Font.BOLD, 14));
        favSongs.addMouseListener(new MouseAdapter() {
            Color oldColor = favSongs.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                    BufferedImage img = ImageIO.read(resource);
                    favSongs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    favSongs.setText("Favorite Songs");
                } catch(Exception exception){

                }
                favSongs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/favSongs.png");
                    BufferedImage img = ImageIO.read(resource);
                    favSongs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    favSongs.setText("Favorite Songs");
                } catch(Exception exception){

                }
                favSongs.setForeground(oldColor);
            }
        });
        add(favSongs);

        songs = new JButton();
        songs.setActionCommand("Songs");
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
                    BufferedImage img = ImageIO.read(resource);
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/songs.png");
                    BufferedImage img = ImageIO.read(resource);
                    songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    songs.setText("Songs");
                } catch(Exception exception){

                }
                songs.setForeground(oldColor);
            }
        });
        add(songs);

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
                    BufferedImage img = ImageIO.read(resource);
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/albums.png");
                    BufferedImage img = ImageIO.read(resource);
                    albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    albums.setText("Albums");
                } catch(Exception exception){

                }
                albums.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(albums);
        add(albums);

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
                    BufferedImage img = ImageIO.read(resource);
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/playlists.png");
                    BufferedImage img = ImageIO.read(resource);
                    playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    playlists.setText("Playlists");
                } catch(Exception exception){

                }
                playlists.setForeground(oldColor);
            }
        });
        //buttonsPnl.add(playlists);
        add(playlists);

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
                    BufferedImage img = ImageIO.read(resource);
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/genre.png");
                    BufferedImage img = ImageIO.read(resource);
                    genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    genres.setText("Genres");
                } catch(Exception exception){

                }
                genres.setForeground(oldColor);
            }
        });
        //add(genres);

        artists = new JButton();
        artists.setActionCommand("Artists");
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
                    URL resource = getClass().getClassLoader().getResource("images/cyanArtist.png");
                    BufferedImage img = ImageIO.read(resource);
                    artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    artists.setText("Artists");
                } catch(Exception exception){

                }
                artists.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/artists.png");
                    BufferedImage img = ImageIO.read(resource);
                    artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    artists.setText("Artists");
                } catch(Exception exception){

                }
                artists.setForeground(oldColor);
            }
        });
        add(artists);

        friends = new JButton();
        friends.setActionCommand("Friends");
        friends.setForeground(Color.white);
        friends.addActionListener(this);
        friends.setOpaque(false);
        friends.setContentAreaFilled(false);
        friends.setBorderPainted(false);
        friends.setMaximumSize(new Dimension(200, 40));
        friends.setMinimumSize(new Dimension(200, 40));
        friends.setPreferredSize(new Dimension(200, 40));
        friends.setFont(new Font("Arial", Font.BOLD, 14));
        friends.addMouseListener(new MouseAdapter() {
            Color oldColor = friends.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanFriends.png");
                    BufferedImage img = ImageIO.read(resource);
                    friends.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    friends.setText("Friends");
                } catch(Exception exception){

                }
                friends.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/friends.png");
                    BufferedImage img = ImageIO.read(resource);
                    friends.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    friends.setText("Friends");
                } catch(Exception exception){

                }
                friends.setForeground(oldColor);
            }
        });
        add(friends);

        years = new JButton();
        years.setActionCommand("Years");
        years.setForeground(Color.white);
        years.addActionListener(this);
        years.setOpaque(false);
        years.setContentAreaFilled(false);
        years.setBorderPainted(false);
        years.setMaximumSize(new Dimension(200, 40));
        years.setMinimumSize(new Dimension(200, 40));
        years.setPreferredSize(new Dimension(200, 40));
        years.setFont(new Font("Arial", Font.BOLD, 14));
        years.addMouseListener(new MouseAdapter() {
            Color oldColor = years.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanYear.png");
                    BufferedImage img = ImageIO.read(resource);
                    years.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    years.setText("Years");
                } catch(Exception exception){

                }
                years.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/year.png");
                    BufferedImage img = ImageIO.read(resource);
                    years.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    years.setText("Years");
                } catch(Exception exception){

                }
                years.setForeground(oldColor);
            }
        });
        //add(years);

        addPlaylist = new JButton();
        addPlaylist.setActionCommand("Add Playlist");
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
                    BufferedImage img = ImageIO.read(resource);
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addPlaylist.png");
                    BufferedImage img = ImageIO.read(resource);
                    addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addPlaylist.setText("Add Playlist");
                } catch(Exception exception){

                }
                addPlaylist.setForeground(oldColor);
            }
        });
        add(addPlaylist);

        addAlbum = new JButton();
        addAlbum.setActionCommand("Add Album");
        addAlbum.setForeground(Color.white);
        addAlbum.addActionListener(this);
        addAlbum.setOpaque(false);
        addAlbum.setContentAreaFilled(false);
        addAlbum.setBorderPainted(false);
        addAlbum.setMaximumSize(new Dimension(200, 40));
        addAlbum.setMinimumSize(new Dimension(200, 40));
        addAlbum.setPreferredSize(new Dimension(200, 40));
        addAlbum.setFont(new Font("Arial", Font.BOLD, 14));
        addAlbum.addMouseListener(new MouseAdapter() {
            Color oldColor = addAlbum.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addAlbum.setText("Add Playlist");
                } catch(Exception exception){

                }
                addAlbum.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addAlbum.setText("Add Playlist");
                } catch(Exception exception){

                }
                addAlbum.setForeground(oldColor);
            }
        });
//        if(isArtist)
//            add(addAlbum);

        addSong = new JButton();
        addSong.setActionCommand("Add Song");
        addSong.setForeground(Color.white);
        addSong.addActionListener(this);
        addSong.setOpaque(false);
        addSong.setContentAreaFilled(false);
        addSong.setBorderPainted(false);
        addSong.setMaximumSize(new Dimension(200, 40));
        addSong.setMinimumSize(new Dimension(200, 40));
        addSong.setPreferredSize(new Dimension(200, 40));
        addSong.setFont(new Font("Arial", Font.BOLD, 14));
        addSong.addMouseListener(new MouseAdapter() {
            Color oldColor = addSong.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(oldColor);
            }
        });
//        if(isArtist)
//            add(addSong);

        try{
            URL resource = getClass().getClassLoader().getResource("images/mostPlayed.png");
            BufferedImage img = ImageIO.read(resource);
            mostFrqntlyPlyd.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            mostFrqntlyPlyd.setText("Most Played");
            resource = getClass().getClassLoader().getResource("images/browse.png");
            img = ImageIO.read(resource);
            search.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            search.setText("Search");
            resource = getClass().getClassLoader().getResource("images/songs.png");
            img = ImageIO.read(resource);
            songs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            songs.setText("Songs");
            resource = getClass().getClassLoader().getResource("images/playlists.png");
            img = ImageIO.read(resource);
            playlists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            playlists.setText("Playlists");
            resource = getClass().getClassLoader().getResource("images/albums.png");
            img = ImageIO.read(resource);
            albums.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            albums.setText("Albums");
            resource = getClass().getClassLoader().getResource("images/addPlaylist.png");
            img = ImageIO.read(resource);
            addPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            addPlaylist.setText("Add Playlist");
            resource = getClass().getClassLoader().getResource("images/favSongs.png");
            img = ImageIO.read(resource);
            favSongs.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            favSongs.setText("Favorite Songs");
            resource = getClass().getClassLoader().getResource("images/artists.png");
            img = ImageIO.read(resource);
            artists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            artists.setText("Artists");
            resource = getClass().getClassLoader().getResource("images/friends.png");
            img = ImageIO.read(resource);
            friends.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            friends.setText("Friends");
//            resource = getClass().getClassLoader().getResource("images/addSong.png");
//            img = ImageIO.read(resource);
//            addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            addSong.setText("Add Song");
//            resource = getClass().getClassLoader().getResource("images/addAlbum.png");
//            img = ImageIO.read(resource);
//            addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            addAlbum.setText("Add Album");
//            resource = getClass().getClassLoader().getResource("images/genre.png");
//            img = ImageIO.read(resource);
//            genres.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            genres.setText("Genres");
//            resource = getClass().getClassLoader().getResource("images/star.png");
//            img = ImageIO.read(resource);
//            favPlaylists.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            favPlaylists.setText("Favorite Playlist");
//            resource = getClass().getClassLoader().getResource("images/year.png");
//            img = ImageIO.read(resource);
//            years.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            years.setText("Years");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == search){
            controller.showBrowserPanel();
        }
        if(e.getSource() == favSongs){
            controller.showFavoriteSongs();
        }
        if(e.getSource() == songs){
            controller.showAllSongs();
        }
        if(e.getSource() == albums){
            controller.showAlbums();
        }
        if(e.getSource() == playlists){
            controller.showPlaylists();
        }
        if(e.getSource() == artists){
            controller.showArtists();
        }
        if(e.getSource() == friends){
            controller.showFriends();
        }
        if(e.getSource() == addPlaylist){
            controller.openAddPlaylistWindow();
//            AddPlaylistWindow apw = new AddPlaylistWindow();
        }
//        if(e.getSource() == addSong){
//            controller.getMainController().getAc().openAddSongWindow();
//        }
//        if(e.getSource() == addAlbum){
//            controller.openAddAlbumWindow();
//        }
//        if(e.getSource() == mostFrqntlyPlyd){
//            controller.showMostFrequentlyPlayed();
//        }
//        if(e.getSource() == favPlaylists){
//            controller.showFavoritePlaylists();
//        }
//        if(e.getSource() == years){
//            controller.showYears();
//        }
//        if(e.getSource() == genres){
//            controller.showGenres();
//        }
    }
}
