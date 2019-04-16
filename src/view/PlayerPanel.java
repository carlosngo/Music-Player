package view;

import controller.*;
import util.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class PlayerPanel extends JPanel implements ActionListener {

    private PlayerController pc;
    private JButton repeat, shuffle, prev, next, favSong, follow;
    private JLabel albumCover, titleLbl, artistLbl;
    private Component controlPnl;
    private JPanel p3;
    private boolean isShuffle, isRepeat, isFavorite, isFollowed;


    public PlayerPanel(PlayerController pc){
        this.pc = pc;
        isShuffle = false;
        isRepeat = false;
        isFavorite = false;

        isFollowed = false;

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

//        follow = new JButton();
//        follow.setActionCommand("follow");
//        follow.setOpaque(false);
//        follow.setContentAreaFilled(false);
//        follow.setBorderPainted(false);
//        follow.addActionListener(this);
//        add(follow);
//        add(Box.createRigidArea(new Dimension(5,0)));

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
            BufferedImage img;
            resource = getClass().getClassLoader().getResource("images/imgRepeatBtn.png");
            img = ImageIO.read(resource);
            repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgBackBtn.png");
            img = ImageIO.read(resource);
            prev.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgNextBtn.png");
            img = ImageIO.read(resource);
            next.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgShuffleBtn.png");
            img = ImageIO.read(resource);
            shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/nocover.jpg");
            img = ImageIO.read(resource);
            albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
            if (!isFavorite) {
                resource = getClass().getClassLoader().getResource("images/favSongs.png");
                img = ImageIO.read(resource);
                favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            } else {
                resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                img = ImageIO.read(resource);
                favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        update(new JLabel());
        setMinimumSize(new Dimension(650, 55));
        setPreferredSize(new Dimension(650, 55));
        setMaximumSize(new Dimension(650, 55));
    }

    public void update() {
        if (pc.getCurrentSong() != null) {
            titleLbl.setText(pc.getCurrentSong().getName());
            if (pc.getCurrentSong().getArtist() != null) {
                artistLbl.setText(pc.getCurrentSong().getArtist().getName());
            } else {
                artistLbl.setText("Unknown Artist");
            }
            File cover = null;
            if (pc.getCurrentSong().getAlbum() != null) {
                cover = pc.getCurrentSong().getAlbum().getCover();
            }
            try {
                if (cover == null) {
                    URL resource = getClass().getClassLoader().getResource("images/nocover.jpg");
                    BufferedImage img = ImageIO.read(resource);
                    albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
                } else {
                    albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(cover, 35, 35)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            isFavorite = pc.getCurrentSong().isFavorite();
            URL resource;
            BufferedImage img;
            try {
                if (isFavorite) {
                    resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                    img = ImageIO.read(resource);
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    resource = getClass().getClassLoader().getResource("images/favSongs.png");
                    img = ImageIO.read(resource);
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } catch (IOException e) {
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
                BufferedImage img = ImageIO.read(resource);
                albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
            } catch (IOException e) {
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
        BufferedImage img;

        try {
            if (e.getActionCommand().equals("Repeat")) {
                isRepeat = !isRepeat;
                System.out.println("Pressed Repeat");
                if (isRepeat) {
                    resource = getClass().getClassLoader().getResource("images/imgRepeatBtnPressed.png");
                    img = ImageIO.read(resource);
                    repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));

                } else {
                    resource = getClass().getClassLoader().getResource("images/imgRepeatBtn.png");
                    img = ImageIO.read(resource);
                    repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } else if (e.getActionCommand().equals("Shuffle")) {
                isShuffle = !isShuffle;
                if (isShuffle) {
                    resource = getClass().getClassLoader().getResource("images/imgShuffleBtnPressed.png");
                    img = ImageIO.read(resource);
                    shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    resource = getClass().getClassLoader().getResource("images/imgShuffleBtn.png");
                    img = ImageIO.read(resource);
                    shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            } else if (e.getActionCommand().equals("favSong")) {
                pc.getCurrentSong().setFavorite(!pc.getCurrentSong().isFavorite());
                isFavorite = pc.getCurrentSong().isFavorite();
                if (isFavorite) {
                    System.out.println("Unfavorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                    img = ImageIO.read(resource);
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    System.out.println("Favorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/favSongs.png");
                    img = ImageIO.read(resource);
                    favSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }

            } else if (e.getActionCommand().equals("follow")) {
                pc.getCurrentSong().setFollowed(!pc.getCurrentSong().isFollowed());
                isFollowed = pc.getCurrentSong().isFollowed();
                if (isFollowed) {
                    System.out.println("Unfavorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                    img = ImageIO.read(resource);
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    System.out.println("Favorited" + pc.getCurrentSong().getName());
                    resource = getClass().getClassLoader().getResource("images/follow.png");
                    img = ImageIO.read(resource);
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
