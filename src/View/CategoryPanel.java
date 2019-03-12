package View;

import Controller.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
        if(isNormalPlaylist) favPlaylist.setVisible(true);
        JButton play = new JButton();
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        JButton remove = new JButton();
        //remove.setVisible(false);
        remove.setOpaque(false);
        remove.setContentAreaFilled(false);
        remove.setBorderPainted(false);
        JButton edit = new JButton();
        //edit.setVisible(false);
        edit.setOpaque(false);
        edit.setContentAreaFilled(false);
        edit.setBorderPainted(false);

        try {
            URL resource = getClass().getClassLoader().getResource("images/delete.png");
            File img = Paths.get(resource.toURI()).toFile();
            remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/edit.png");
            img = Paths.get(resource.toURI()).toFile();
            edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/star.png");
            img = Paths.get(resource.toURI()).toFile();
            favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

//                block.addMouseListener(new MouseAdapter() {
//                    public void mouseEntered(MouseEvent e) {
//                        remove.setVisible(true);
//                        edit.setVisible(true);
//                    }
//                    public void mouseExited(MouseEvent e) {
//                        remove.setVisible(false);
//                        edit.setVisible(false);
//                    }
//                });

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
                }
            }
        });

        favPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Playlist playlist : controller.getMainController().getPlaylists())
                    if(playlist.getName().equals(subCategoryName)) playlist.setFavorite(true);
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to remove this " + category.toLowerCase() + "?",
                        "Remove " + category.toLowerCase(), JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
//                    controller.remove(category, subCategoryName);
                    System.out.println(category.toLowerCase() + "removed."); //test
                }
            }
        });

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCategoryWindow ecw = new EditCategoryWindow(category);
//                        if(ecw.getIsChanged()){
//                            subOptionButton.setText(ecw.getNewName());
//                            ecw.dispose();
//                        }
                if((ecw.getNewName() != null) && (ecw.getIsChanged()))
                    subOptionButton.setText(ecw.getNewName());
            }
        });
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
                }
//                        PlayerThread pt = new PlayerThread(controller.getMainController().getPlayerController(), queue);
//                        new Thread(pt).start();

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
        cons.gridx = 2;
        block.add(favPlaylist, cons);
    }
}
