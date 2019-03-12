package View;

import Controller.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class PlayerPanel extends JPanel implements ActionListener {

    private PlayerController pc;
    private JButton repeat, shuffle, prev, next, favSong;
    private JLabel albumCover, titleLbl, artistLbl;
    private Component controlPnl;
    private JPanel p3;
    private boolean isShuffle, isRepeat, isFavorite;


    public PlayerPanel(PlayerController pc){
        this.pc = pc;
        isShuffle = false;
        isRepeat = false;
        isFavorite = false;
//        setLayout(new BorderLayout());
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);
        setBackground(Color.BLACK);
        add(Box.createRigidArea(new Dimension(7,0)));

//        JPanel p1 = new JPanel(new BorderLayout());

        albumCover = new JLabel("");
        albumCover.setOpaque(true);
//        p1.add(albumCover, BorderLayout.WEST);
        add(albumCover);
        add(Box.createRigidArea(new Dimension(10,0)));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setOpaque(false);
        titleLbl = new JLabel("");
        titleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLbl.setForeground(Color.white);
        p1.add(titleLbl);

        p1.add(Box.createRigidArea(new Dimension(0, 5)));
        artistLbl = new JLabel("");
        artistLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        artistLbl.setForeground(Color.white);
        p1.add(artistLbl);
        p1.setMinimumSize(new Dimension(125, 40));
        p1.setPreferredSize(new Dimension(125, 40));
        p1.setMaximumSize(new Dimension(125, 40));
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.setOpaque(false);

        favSong = new JButton();
        favSong.setActionCommand("favSong");
        favSong.setOpaque(false);
        favSong.setContentAreaFilled(false);
        favSong.setBorderPainted(false);
        favSong.addActionListener(this);
        add(favSong);
        add(Box.createRigidArea(new Dimension(5,0)));

        prev = new JButton();
        prev.setActionCommand("Prev");
        prev.setOpaque(false);
        prev.setContentAreaFilled(false);
        prev.setBorderPainted(false);
        prev.addActionListener(pc);
        add(prev);
        add(Box.createRigidArea(new Dimension(5,0)));

        next = new JButton();
        next.setActionCommand("Next");
        next.setOpaque(false);
        next.setContentAreaFilled(false);
        next.setBorderPainted(false);
        next.addActionListener(pc);
        add(next);
        add(Box.createRigidArea(new Dimension(5,0)));

        p3 = new JPanel();
        controlPnl = new JLabel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.add(controlPnl);
        p3.setMinimumSize(new Dimension(220, 25));
        p3.setPreferredSize(new Dimension(220, 25));
        p3.setMaximumSize(new Dimension(220, 25));
        add(p3);

        repeat = new JButton();
        repeat.setActionCommand("Repeat");
        repeat.setOpaque(false);
        repeat.setContentAreaFilled(false);
        repeat.setBorderPainted(false);
        repeat.addActionListener(this);
        add(repeat);
        add(Box.createRigidArea(new Dimension(8,0)));

        shuffle = new JButton();
        shuffle.setActionCommand("Shuffle");
        shuffle.setOpaque(false);
        shuffle.setContentAreaFilled(false);
        shuffle.setBorderPainted(false);
        shuffle.addActionListener(this);
        add(shuffle);
        add(Box.createRigidArea(new Dimension(0,5)));

        try{
            URL resource;
            File img;
            resource = getClass().getClassLoader().getResource("images/imgRepeatBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgBackBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            prev.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            playOrPause.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 20)));
            resource = getClass().getClassLoader().getResource("images/imgNextBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            next.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgShuffleBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/nocover.jpg");
            img = Paths.get(resource.toURI()).toFile();
            albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
            if (!isFavorite) {
                resource = getClass().getClassLoader().getResource("images/favSongs.png");
                img = Paths.get(resource.toURI()).toFile();
                favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            } else {
                resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                img = Paths.get(resource.toURI()).toFile();
                favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
        }catch (URISyntaxException e) {
            System.out.println("File not found");
        }
        update(new JLabel());
        setMinimumSize(new Dimension(650, 55));
        setPreferredSize(new Dimension(650, 55));
        setMaximumSize(new Dimension(650, 55));
    }

    public void update() {
        if (pc.getCurrentSong() != null) {
            titleLbl.setText(pc.getCurrentSong().getName());
            if (pc.getCurrentSong().getAlbum() != null) {
                artistLbl.setText(pc.getCurrentSong().getAlbum().getName());
                File cover = pc.getCurrentSong().getAlbum().getCover();
                try {
                    if (cover == null) {
                        URL resource = getClass().getClassLoader().getResource("images/nocover.jpg");
                        File img = Paths.get(resource.toURI()).toFile();
                        albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
                    } else {
                        albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(cover, 35, 35)));
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            isFavorite = pc.getCurrentSong().isFavorite();
            URL resource;
            File img;
            try {
                if (isFavorite) {
                    resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                    img = Paths.get(resource.toURI()).toFile();
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    resource = getClass().getClassLoader().getResource("images/favSongs.png");
                    img = Paths.get(resource.toURI()).toFile();
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
         }
    }

    public void update(Component controlPnl) {
        if (controlPnl instanceof JLabel) {
            titleLbl.setText("");
            artistLbl.setText("");
            try {
                URL resource = getClass().getClassLoader().getResource("images/nocover.jpg");
                File img = Paths.get(resource.toURI()).toFile();
                albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            repeat.setEnabled(false);
            shuffle.setEnabled(false);
            next.setEnabled(false);
            prev.setEnabled(false);
            favSong.setEnabled(false);
        } else {
            update();
            repeat.setEnabled(true);
            shuffle.setEnabled(true);
            next.setEnabled(true);
            prev.setEnabled(true);
            favSong.setEnabled(true);
        }
        if (this.controlPnl != null) p3.remove(this.controlPnl);
        if (controlPnl != null) p3.add(controlPnl);
        this.controlPnl = controlPnl;
        revalidate();
        repaint();
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        URL resource;
        File img;

        try {
            if (e.getActionCommand().equals("Repeat")) {
                isRepeat = !isRepeat;
                System.out.println("Pressed Repeat");
                if (isRepeat) {
                    resource = getClass().getClassLoader().getResource("images/imgRepeatBtnPressed.png");
                    img = Paths.get(resource.toURI()).toFile();
                    repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));

                } else {
                    resource = getClass().getClassLoader().getResource("images/imgRepeatBtn.png");
                    img = Paths.get(resource.toURI()).toFile();
                    repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } else if (e.getActionCommand().equals("Shuffle")) {
                isShuffle = !isShuffle;
                if (isShuffle) {
                    resource = getClass().getClassLoader().getResource("images/imgShuffleBtnPressed.png");
                    img = Paths.get(resource.toURI()).toFile();
                    shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    resource = getClass().getClassLoader().getResource("images/imgShuffleBtn.png");
                    img = Paths.get(resource.toURI()).toFile();
                    shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } else if (e.getActionCommand().equals("favSong")) {
                pc.getCurrentSong().setFavorite(!pc.getCurrentSong().isFavorite());
                isFavorite = pc.getCurrentSong().isFavorite();
                if (isFavorite) {
                    System.out.println("Unfavorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                    img = Paths.get(resource.toURI()).toFile();
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    System.out.println("Favorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/favSongs.png");
                    img = Paths.get(resource.toURI()).toFile();
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }

            }

        }catch (URISyntaxException ex) {
            System.out.println("File not found");
        }
    }
}
