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

    public PlaylistPanel(SongController controller, ArrayList<Playlist> objects, String header) {
        super(controller, header);

        if(objects.size() == 0){
            JLabel blankMessage = new JLabel("No playlists to show.");
            blankMessage.setForeground(Color.white);
            blankMessage.setFont(new Font("Arial", Font.BOLD, 22));
            headerPnl.add(blankMessage, BorderLayout.CENTER);
//            cons.insets = new Insets(10, 10, 2, 10);
//            cons.gridx = 0;
//            cons.gridy = 0;
//            cons.gridwidth = 3;
//            //JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
//            JLabel emptyLabel = new JLabel("No playlists to display.");
//            emptyLabel.setForeground(Color.white);
//            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//            //block.add(emptyLabel, cons);
//            add(emptyLabel);
        }
        else{
            for(i=0 ; i<objects.size() ; i++){
                addRow(category, objects.get(i));
            }
            add(scroll, BorderLayout.CENTER);
        }
    }

    public void addRow(String category, Object obj) {
        Playlist playlist = (Playlist) obj;

        int accountId = playlist.getAccount().getId();
        User user = controller.getUser(accountId);
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
        JButton favPlaylist = new JButton();
        favPlaylist.setOpaque(false);
        favPlaylist.setContentAreaFilled(false);
        favPlaylist.setBorderPainted(false);
        favPlaylist.setForeground(Color.white);
        favPlaylist.setVisible(false);
        favPlaylist.setToolTipText("Add Playlist to Favorite Playlists");
//        if(category.equals("Playlists")) favPlaylist.setVisible(true);
        JButton play = new JButton();
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        play.setToolTipText("Play Songs in Playlist");
        JButton addToQueue = new JButton();
        addToQueue.setOpaque(false);
        addToQueue.setContentAreaFilled(false);
        addToQueue.setBorderPainted(false);
        addToQueue.setToolTipText("Add Playlist to Queue");
        JButton follow = new JButton();
        follow.setOpaque(false);
        follow.setContentAreaFilled(false);
        follow.setBorderPainted(false);
        follow.setToolTipText("Follow");
        JButton remove = new JButton();
        //remove.setVisible(false);
        remove.setOpaque(false);
        remove.setContentAreaFilled(false);
        remove.setBorderPainted(false);
        remove.setToolTipText("Delete Playlist");
        JButton edit = new JButton();
        edit.setOpaque(false);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);
        edit.setToolTipText("Edit Playlist");

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
            if (controller.isFavoritePlaylist(playlist.getPlaylistId())) {
                resource = getClass().getClassLoader().getResource("images/cyanStar.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/star.png");
                img = ImageIO.read(resource);
            }
            favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            if (controller.isFollowingPlaylist(playlist.getPlaylistId())) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
                favPlaylist.setVisible(true);
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
                favPlaylist.setVisible(false);
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

        creator.setText(user.getName());

        creator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showInfo(user);
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

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL resource;
                    BufferedImage img;
                    if (controller.isFollowingPlaylist(playlist.getPlaylistId())) {
                        controller.unfollowPlaylist(playlist);
                        resource = getClass().getClassLoader().getResource("images/follow.png");
                        img = ImageIO.read(resource);
                    } else {
                        controller.followPlaylist(playlist);
                        resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                        img = ImageIO.read(resource);
                        favPlaylist.setVisible(true);
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
                controller.openEditPlaylistWindow(category, playlist);
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playSongsInPlaylist(playlist.getPlaylistId());
            }
        });

        favPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.toggleFavoritePlaylist(playlist.getPlaylistId());

                try {
                    URL resource;
                    BufferedImage img;
                    if (controller.isFavoritePlaylist(playlist.getPlaylistId())) {
                        resource = getClass().getClassLoader().getResource("images/cyanStar.png");
                        img = ImageIO.read(resource);
                    } else {
                        resource = getClass().getClassLoader().getResource("images/star.png");
                        img = ImageIO.read(resource);
                    }
                    favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
//        block.add(play, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 4;
        block.add(favPlaylist, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 5;
        block.add(addToQueue, cons);
        if(playlist.getAccount().getId() != controller.getMainController().getAc().getUser().getAccount().getId()){
            cons.insets = new Insets(5, 0, 0, 10);
            cons.gridx = 6;
            block.add(follow, cons);
        }
        if(playlist.getAccount().getId() == controller.getMainController().getAc().getUser().getAccount().getId()){
            cons.insets = new Insets(5, 0, 0, 0);
            cons.gridx = 7;
            cons.gridwidth = 1;
            block.add(edit, cons);
            cons.insets = new Insets(5, 0, 0, 10);
            cons.gridx = 8;
            block.add(remove, cons);
        }
    }

}
