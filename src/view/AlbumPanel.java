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

public class AlbumPanel extends CategoryPanel {

    public AlbumPanel(SongController controller, ArrayList<Object> objects) {
        super(controller, objects);
    }

    public void addRow(String category, Object obj) {
        Album album = (Album) obj;

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
        remove.setOpaque(false);
        remove.setContentAreaFilled(false);
        remove.setBorderPainted(false);
        JButton edit = new JButton();
        edit.setOpaque(false);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);
        JButton changeCover = new JButton();
//        changeCover.setOpaque(false);
//        changeCover.setContentAreaFilled(false);
//        changeCover.setBorderPainted(false);
//        if (!category.equals("Albums")) changeCover.setVisible(false);

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
            if (album.isFollowed()) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
            }
            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/changeCover.png");
            img = ImageIO.read(resource);
            changeCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
            img = ImageIO.read(resource);
            addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(album.getName());

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByAlbum(album.getAlbumId());
            }
        });

        creator.setText(album.getArtist().getName());

        creator.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByArtist(album.getArtist().getArtistId());
            }
        });

        ActionListener addToQueueListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addSongsInAlbumToQueue(album.getAlbumId());
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
                controller.followAlbum(album);
                try {
                    URL resource;
                    BufferedImage img;
                    if (album.isFollowed()) {
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
                    controller.removeAlbum(album.getAlbumId());
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openEditCategoryWindow(category, album);
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playSongsInAlbum(album.getAlbumId());
            }
        });
//        changeCover.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser = new JFileChooser();
//                chooser.setDialogTitle("Import Cover Photo");
////                chooser.addChoosableFileFilter(new FileNameExtensionFilter(
////                        "MP3 File", "mp3"));
//                int returnVal = chooser.showOpenDialog(null);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = chooser.getSelectedFile();
//                    controller.setAlbumCover(subCategoryName, file);
//                }
//            }
//        });

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
