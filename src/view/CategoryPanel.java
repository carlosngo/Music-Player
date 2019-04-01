package view;

import controller.*;

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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CategoryPanel extends JPanel {
    private SongController controller;
    private JLabel headerName;
    private JScrollPane scroll;
    private JPanel block;
    private ArrayList<String> data;
    private boolean isNormalPlaylist = false;

    private int i;
    //needs a header name as string and an arraylist of arraylist as parameter input for diaplaying the list of [category]
    public CategoryPanel(SongController controller, String category, ArrayList<String> subCategoryList){
        this.controller = controller;
        data = subCategoryList;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        if(subCategoryList.size() == 0){

        }
        else{

        }
        add(Box.createRigidArea(new Dimension(0,7)));
        headerName = new JLabel((category).toUpperCase());
        headerName.setFont(new Font("Arial", Font.BOLD, 26));
        headerName.setForeground(Color.white);
        add(headerName);
        add(Box.createRigidArea(new Dimension(0,10)));

        block = new JPanel();
        block.setLayout(new GridBagLayout());
        block.setOpaque(false);
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        scroll = new JScrollPane(block);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,100));
        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if(subCategoryList.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(i=0; i<subCategoryList.size(); i++){
                addRow(category, subCategoryList.get(i));
            }
            add(scroll);
        }

    }

    public void update() {
        revalidate();
        repaint();
    }

    public void isNormalPlaylist(boolean normalPlaylist) {
        isNormalPlaylist = normalPlaylist;
    }

    public void addRow(String category, String subCategoryName) {
        JButton subOptionButton = new JButton();
        subOptionButton.setOpaque(false);
        subOptionButton.setContentAreaFilled(false);
        subOptionButton.setBorderPainted(false);
        subOptionButton.setForeground(Color.white);
        JButton favPlaylist = new JButton();
        favPlaylist.setOpaque(false);
        favPlaylist.setContentAreaFilled(false);
        favPlaylist.setBorderPainted(false);
        favPlaylist.setVisible(false);
        if(category.equals("Playlists")) favPlaylist.setVisible(true);
        JButton play = new JButton();
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        JButton addToQueue = new JButton();
        addToQueue.setOpaque(false);
        addToQueue.setContentAreaFilled(false);
        addToQueue.setBorderPainted(false);
        JButton remove = new JButton();
        //remove.setVisible(false);
        remove.setOpaque(false);
        remove.setContentAreaFilled(false);
        remove.setBorderPainted(false);
        if (category.equals("Genres") || category.equals("Artists")) remove.setVisible(false);

        JButton edit = new JButton();
        //edit.setVisible(false);
        edit.setOpaque(false);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);
        if (category.equals("Genres") || category.equals("Artists")) edit.setVisible(false);

        JButton changeCover = new JButton();
        changeCover.setOpaque(false);
        changeCover.setContentAreaFilled(false);
        changeCover.setBorderPainted(false);
        if (!category.equals("Albums")) changeCover.setVisible(false);
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

            if (controller.isFavoritePlaylist(subCategoryName)) {
                resource = getClass().getClassLoader().getResource("images/cyanStar.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/star.png");
                img = ImageIO.read(resource);
            }
            favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/changeCover.png");
            img = ImageIO.read(resource);
            changeCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
            img = ImageIO.read(resource);
            addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(subCategoryName);

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (category) {
                    case "Genres":
                        controller.showSongsByGenre(subCategoryName);
                        break;
                    case "Albums":
                        controller.showSongsByAlbum(subCategoryName);
                        break;
                    case "Playlists":
                        controller.showSongsByPlaylist(subCategoryName);
                        break;
                    case "Years":
                        controller.showSongsByYear(subCategoryName);
                        break;
                    case "Favorite Playlists":
                        controller.showSongsByPlaylist(subCategoryName);
                        break;
                    case "Artists":
                        controller.showSongsByArtist(subCategoryName);
                        break;
                }
            }
        });

        ActionListener addToQueueListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (category) {
                    case "Genres":
                        controller.addSongsInGenreToQueue(subCategoryName);
                        break;
                    case "Albums":
                        controller.addSongsInAlbumToQueue(subCategoryName);
                        break;
                    case "Playlists":
                        controller.addSongsInPlaylistToQueue(subCategoryName);
                        break;
                    case "Years":
                        controller.addSongsInYearToQueue(subCategoryName);
                        break;
                    case "Favorite Playlists":
                        controller.addSongsInPlaylistToQueue(subCategoryName);
                        break;
                    case "Artists":
                        controller.addSongsByArtistToQueue(subCategoryName);
                        break;
                }
            }
        };
        addToQueue.addActionListener(addToQueueListener);

        addToQueue.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                addToQueue.setEnabled(true);
            }
            public void mouseExited(MouseEvent e) { addToQueue.setEnabled(false); }
        });

        favPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.toggleFavoritePlaylist(subCategoryName);

                try {
                    URL resource;
                    BufferedImage img;
                    if (controller.isFavoritePlaylist(subCategoryName)) {
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

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to remove this " + category.toLowerCase().substring(0, category.length() - 2) + "?",
                        "Remove " + category.toLowerCase(), JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
//                    controller.remove(category, subCategoryName);
                    switch (category) {
                        case "Playlists":
                            controller.removePlaylist(subCategoryName);
                            break;
                        case "Albums":
                            controller.removeAlbum(subCategoryName);
                            break;
                        case "Favorite Playlists":
                            controller.removePlaylist(subCategoryName);
                            break;
                    }
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openEditCategoryWindow(category, subCategoryName);
            }
        });
        if (category.equals("Years")) edit.setVisible(false);
        if (category.equals("Years")) remove.setVisible(false);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (category) {
                    case "Genres":
                        controller.playSongsInGenre(subCategoryName);
                        break;
                    case "Albums":
                        controller.playSongsInAlbum(subCategoryName);
                        break;
                    case "Playlists":
                        controller.playSongsInPlaylist(subCategoryName);
                        break;
                    case "Years":
                        controller.playSongsInYear(subCategoryName);
                        break;
                    case "Favorite Playlists":
                        controller.playSongsInPlaylist(subCategoryName);
                        break;
                    case "Artists":
                        controller.playSongsByArtist(subCategoryName);
                        break;
                }
//                        PlayerThread pt = new PlayerThread(controller.getMainController().getPlayerController(), queue);
//                        new Thread(pt).start();

            }
        });
        changeCover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Import Song");
                chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                        "MP3 File", "mp3"));
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    controller.setAlbumCover(subCategoryName, file);
                }
            }
        });

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5, 10, 0, 0);
        cons.gridx = 0;
        cons.gridy = i;
        cons.gridwidth = 1;
        block.add(subOptionButton, cons);
        cons.insets = new Insets(5, 0, 0, 0);
        cons.gridx = 4;
        cons.gridwidth = 1;
        block.add(edit, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 5;
        block.add(remove, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 2;
        block.add(play, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 6;
        block.add(favPlaylist, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 6;
        block.add(changeCover, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 3;
        block.add(addToQueue, cons);
    }
}