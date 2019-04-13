package view;

import controller.SongController;
import model.*;
import util.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PlaylistPanel extends CategoryPanel {

    public PlaylistPanel(SongController controller, ArrayList<Object> objects) {
        super(controller, objects);
    }

    public void addRow(String category, Object obj) {
        Playlist playlist = (Playlist) obj;

        JButton subOptionButton = new JButton();
        subOptionButton.setOpaque(false);
        subOptionButton.setContentAreaFilled(false);
        subOptionButton.setBorderPainted(false);
        subOptionButton.setForeground(Color.white);
        JButton creator = new JButton();
        creator.setOpaque(false);
        creator.setContentAreaFilled(false);
        creator.setBorderPainted(false);
        creator.setForeground(Color.white);
//        JButton favPlaylist = new JButton();
//        favPlaylist.setOpaque(false);
//        favPlaylist.setContentAreaFilled(false);
//        favPlaylist.setBorderPainted(false);
//        favPlaylist.setVisible(false);
//        if(category.equals("Playlists")) favPlaylist.setVisible(true);
        JButton play = new JButton();
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        JButton addToQueue = new JButton();
        addToQueue.setOpaque(false);
        addToQueue.setContentAreaFilled(false);
        addToQueue.setBorderPainted(false);
        JButton follow = new JButton();
        follow.setOpaque(false);
        follow.setContentAreaFilled(false);
        follow.setBorderPainted(false);
        JButton remove = new JButton();
        //remove.setVisible(false);
        remove.setOpaque(false);
        remove.setContentAreaFilled(false);
        remove.setBorderPainted(false);
        JButton edit = new JButton();
        edit.setOpaque(false);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);

        try {
            URL resource = getClass().getClassLoader().getResource("images/delete.png");
            BufferedImage img = ImageIO.read(resource);
            remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/edit.png");
            img = ImageIO.read(resource);
            edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
            img = ImageIO.read(resource);
            play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));

//            if (controller.isFavoritePlaylist(subCategoryName)) {
//                resource = getClass().getClassLoader().getResource("images/cyanStar.png");
//                img = ImageIO.read(resource);
//            } else {
//                resource = getClass().getClassLoader().getResource("images/star.png");
//                img = ImageIO.read(resource);
//            }
//            favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/changeCover.png");
//            img = ImageIO.read(resource);

            if (playlist.isFollowed()) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
            }
            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
            img = ImageIO.read(resource);
            addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(playlist.getName());

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByPlaylist(playlist.getPlaylistId());
            }
        });

        creator.setText(playlist.getAccount().getUserName());

        creator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByArtist(playlist.getAccount().getId());
            }
        });

        ActionListener addToQueueListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addSongsInPlaylistToQueue(playlist.getPlaylistId());
            }
        };
        addToQueue.addActionListener(addToQueueListener);

        addToQueue.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addToQueue.setEnabled(true);
            }
            public void mouseExited(MouseEvent e) { addToQueue.setEnabled(false); }
        });

//        favPlaylist.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                controller.toggleFavoritePlaylist(subCategoryName);
//
//                try {
//                    URL resource;
//                    BufferedImage img;
//                    if (controller.isFavoritePlaylist(subCategoryName)) {
//                        resource = getClass().getClassLoader().getResource("images/cyanStar.png");
//                        img = ImageIO.read(resource);
//                    } else {
//                        resource = getClass().getClassLoader().getResource("images/star.png");
//                        img = ImageIO.read(resource);
//                    }
//                    favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playlist.setFollowed(!playlist.isFollowed());
                try {
                    URL resource;
                    BufferedImage img;
                    if (playlist.isFollowed()) {
                        resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                        img = ImageIO.read(resource);
                    } else {
                        resource = getClass().getClassLoader().getResource("images/follow.png");
                        img = ImageIO.read(resource);
                    }
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to remove this " + category.toLowerCase().substring(0, category.length() - 2) + "?",
                        "Remove " + category.toLowerCase(), JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    controller.removePlaylist(playlist.getPlaylistId());
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openEditCategoryWindow(category, playlist);
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playSongsInPlaylist(playlist.getPlaylistId());
            }
        });

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5, 10, 0, 0);
        cons.gridx = 0;
        cons.gridy = i;
        cons.gridwidth = 1;
        block.add(subOptionButton, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 2;
        block.add(creator, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 3;
        block.add(play, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 4;
        block.add(addToQueue, cons);
        cons.insets = new Insets(5, 0, 0, 0);
        cons.gridx = 5;
        cons.gridwidth = 1;
        block.add(edit, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 6;
        block.add(remove, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 7;
//        block.add(favPlaylist, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 8;
//        block.add(changeCover, cons);
    }

}
