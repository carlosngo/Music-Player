package view;

import controller.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class CategoryPanel extends JPanel {
    protected SongController controller;
    protected JScrollPane scroll;
    protected JPanel block;
    protected GridBagConstraints cons;
    protected int i;
    private boolean isNormalPlaylist = false;
    private JLabel headerName;
    private String category;

    public CategoryPanel(SongController controller, ArrayList<Object> objects){
        if(objects.get(0) instanceof Album)
            setCategory("Albums");
        else if (objects.get(0) instanceof Artist)
            setCategory("Artists");
        else if (objects.get(0) instanceof Playlist)
            setCategory("Playlists");
        else if(objects.get(0) instanceof User)
            setCategory("Listeners");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(0,7)));
        headerName = new JLabel((category).toUpperCase());
        headerName.setFont(new Font("Arial", Font.BOLD, 26));
        headerName.setForeground(Color.white);
        add(headerName);
        add(Box.createRigidArea(new Dimension(0,10)));

        block = new JPanel();
        block.setLayout(new GridBagLayout());
        block.setOpaque(false);
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        scroll = new JScrollPane(block);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,100));

        if(objects.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(Object object : objects){
                addRow(category, object);
            }
            add(scroll);
        }
    }

    public abstract void addRow(String category, Object obj);

    public void update() {
        revalidate();
        repaint();
    }

    public void isNormalPlaylist(boolean normalPlaylist) {
        isNormalPlaylist = normalPlaylist;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}










//package view;
//
//import controller.*;
//import model.*;
//import util.ImageResizer;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class CategoryPanel extends JPanel {
//    private SongController controller;
//    private JLabel headerName;
//    private JScrollPane scroll;
//    private JPanel block;
//    private ArrayList<ArrayList<String>> data;
//    private boolean isNormalPlaylist = false;
//    private ArrayList<Object> listSource;
//
//    private int i;
//    //needs a header name as string and an arraylist of Objects to extract the
//    public CategoryPanel(SongController controller, String category, ArrayList<Object> listSource){
//        //SongController controller, String category, ArrayList<ArrayList<String>> subCategoryList
//        this.listSource = listSource;
//        ArrayList<ArrayList<String>> subCategoryList = new ArrayList<>();
//        ArrayList<String> subCategoryListContent;
//        for(Object obj : listSource){
//            subCategoryListContent = new ArrayList<String>();
//            if(obj instanceof Playlist){
//                Playlist playlist = (Playlist) obj;
//                subCategoryListContent.add(playlist.getName());
//                subCategoryListContent.add(playlist.getAccount().getUserName());
//            }
//            if(obj instanceof Album){
//                Album album = (Album) obj;
//                subCategoryListContent.add(album.getName());
//                subCategoryListContent.add(album.getArtist().getName());
//            }
//            subCategoryList.add(subCategoryListContent);
//        }
//        this.controller = controller;
//        data = subCategoryList;
//
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        //setAlignmentX(Component.LEFT_ALIGNMENT);
//        setOpaque(false);
//        add(Box.createRigidArea(new Dimension(0,7)));
//        headerName = new JLabel((category).toUpperCase());
//        headerName.setFont(new Font("Arial", Font.BOLD, 26));
//        headerName.setForeground(Color.white);
//        add(headerName);
//        add(Box.createRigidArea(new Dimension(0,10)));
//
//        block = new JPanel();
//        block.setLayout(new GridBagLayout());
//        block.setOpaque(false);
//        GridBagConstraints cons = new GridBagConstraints();
//        cons.fill = GridBagConstraints.HORIZONTAL;
//        scroll = new JScrollPane(block);
//        scroll.setOpaque(false);
//        scroll.getViewport().setOpaque(false);
//        scroll.setPreferredSize(new Dimension(50,100));
//        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        if(subCategoryList.isEmpty()){
//            cons.insets = new Insets(10, 10, 2, 10);
//            cons.gridx = 0;
//            cons.gridy = 0;
//            cons.gridwidth = 3;
//            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
//            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//            block.add(emptyLabel, cons);
//        }
//        else{
////            for(i=0; i<subCategoryList.size(); i++){
////                addRow(category, subCategoryList.get(i).get(0), subCategoryList.get(i).get(1));
////            }
//            for(Object object : listSource){
//                addRow(category, object);
//            }
//            add(scroll);
//        }
//
//    }
//
//    public void update() {
//        revalidate();
//        repaint();
//    }
//
//    public void isNormalPlaylist(boolean normalPlaylist) {
//        isNormalPlaylist = normalPlaylist;
//    }
//
//    public void addRow(String category, Object obj) {
//        //String category, String subCategoryName, String subCategoryCreator
//        ArrayList<String> subCategoryListContent = new ArrayList<String>();
//        int artistID = 0;
//        int albumID = 0;
//        int playlistID = 0;
//        Playlist playlist = null;
//        Album album = null;
//        if(obj instanceof Playlist){
//            playlist = (Playlist) obj;
//            subCategoryListContent.add(playlist.getName());
//            subCategoryListContent.add(playlist.getAccount().getUserName());
//            artistID = playlist.getAccount().getId();
//            playlistID = playlist.getPlaylistId();
//        }
//        if(obj instanceof Album){
//            album = (Album) obj;
//            subCategoryListContent.add(album.getName());
//            subCategoryListContent.add(album.getArtist().getName());
//            artistID = album.getArtist().getArtistId();
//            albumID = album.getAlbumId();
//        }
//
//        JButton subOptionButton = new JButton();
//        subOptionButton.setOpaque(false);
//        subOptionButton.setContentAreaFilled(false);
//        subOptionButton.setBorderPainted(false);
//        subOptionButton.setForeground(Color.white);
//
//        JButton creator = new JButton();
//        creator.setOpaque(false);
//        creator.setContentAreaFilled(false);
//        creator.setBorderPainted(false);
//        creator.setForeground(Color.white);
//
//        JButton favPlaylist = new JButton();
//        favPlaylist.setOpaque(false);
//        favPlaylist.setContentAreaFilled(false);
//        favPlaylist.setBorderPainted(false);
//        favPlaylist.setVisible(false);
//        if(category.equals("Playlists")) favPlaylist.setVisible(true);
//        JButton play = new JButton();
//        play.setOpaque(false);
//        play.setContentAreaFilled(false);
//        play.setBorderPainted(false);
//        JButton addToQueue = new JButton();
//        addToQueue.setOpaque(false);
//        addToQueue.setContentAreaFilled(false);
//        addToQueue.setBorderPainted(false);
//        JButton follow = new JButton();
//        follow.setOpaque(false);
//        follow.setContentAreaFilled(false);
//        follow.setBorderPainted(false);
//        JButton remove = new JButton();
//        //remove.setVisible(false);
//        remove.setOpaque(false);
//        remove.setContentAreaFilled(false);
//        remove.setBorderPainted(false);
//        if (category.equals("Genres") || category.equals("Artists")) remove.setVisible(false);
//
//        JButton edit = new JButton();
//        //edit.setVisible(false);
//        edit.setOpaque(false);
//        edit.setContentAreaFilled(false);
//        edit.setBorderPainted(false);
//        if (category.equals("Genres") || category.equals("Artists")) edit.setVisible(false);
//
//        JButton changeCover = new JButton();
//        changeCover.setOpaque(false);
//        changeCover.setContentAreaFilled(false);
//        changeCover.setBorderPainted(false);
//        if (!category.equals("Albums")) changeCover.setVisible(false);
//        try {
//            URL resource = getClass().getClassLoader().getResource("images/delete.png");
//            BufferedImage img = ImageIO.read(resource);
//            remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/edit.png");
//            img = ImageIO.read(resource);
//            edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
//            img = ImageIO.read(resource);
//            play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//
////            if (controller.isFavoritePlaylist(subCategoryName)) {
////                resource = getClass().getClassLoader().getResource("images/cyanStar.png");
////                img = ImageIO.read(resource);
////            } else {
////                resource = getClass().getClassLoader().getResource("images/star.png");
////                img = ImageIO.read(resource);
////            }
////            favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
////            resource = getClass().getClassLoader().getResource("images/changeCover.png");
////            img = ImageIO.read(resource);
//
//            if (playlist.isFollowed()||album.isFollowed()) {
//                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
//                img = ImageIO.read(resource);
//            } else {
//                resource = getClass().getClassLoader().getResource("images/follow.png");
//                img = ImageIO.read(resource);
//            }
//            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/changeCover.png");
//            img = ImageIO.read(resource);
//
//            changeCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
//            img = ImageIO.read(resource);
//            addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        subOptionButton.setText(subCategoryListContent.get(0));
//
//        int finalAlbumID = albumID;
//        int finalPlaylistID = playlistID;
//        int finalArtistID = artistID;
//        subOptionButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                switch (category) {
////                    case "Genres":
////                        controller.showSongsByGenre(subCategoryName);
////                        break;
//                    case "Albums":
//                        controller.showSongsByAlbum(finalAlbumID);
//                        break;
//                    case "Playlists":
//                        controller.showSongsByPlaylist(finalPlaylistID);
//                        break;
////                    case "Years":
////                        controller.showSongsByYear(subCategoryName);
////                        break;
////                    case "Favorite Playlists":
////                        controller.showSongsByPlaylist(subCategoryName);
////                        break;
//                    case "Artists":
//                        controller.showSongsByArtist(finalArtistID);
//                        break;
//                }
//            }
//        });
//
//        creator.setText(subCategoryListContent.get(1));
//
//        int finalArtistID1 = artistID;
//        creator.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                controller.showSongsByArtist(finalArtistID1);
//            }
//        });
//
//        int finalAlbumID1 = albumID;
//        int finalPlaylistID1 = playlistID;
//        int finalArtistID2 = artistID;
//        ActionListener addToQueueListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                switch (category) {
////                    case "Genres":
////                        controller.addSongsInGenreToQueue(subCategoryName);
////                        break;
//                    case "Albums":
//                        controller.addSongsInAlbumToQueue(finalAlbumID1);
//                        break;
//                    case "Playlists":
//                        controller.addSongsInPlaylistToQueue(finalPlaylistID1);
//                        break;
////                    case "Years":
////                        controller.addSongsInYearToQueue(subCategoryName);
////                        break;
////                    case "Favorite Playlists":
////                        controller.addSongsInPlaylistToQueue(subCategoryName);
////                        break;
//                    case "Artists":
//                        controller.addSongsByArtistToQueue(finalArtistID2);
//                        break;
//                }
//            }
//        };
//        addToQueue.addActionListener(addToQueueListener);
//
//        addToQueue.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                addToQueue.setEnabled(true);
//            }
//            public void mouseExited(MouseEvent e) { addToQueue.setEnabled(false); }
//        });
//
//        favPlaylist.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                controller.toggleFavoritePlaylist(subCategoryName);
////
////                try {
////                    URL resource;
////                    BufferedImage img;
////                    if (controller.isFavoritePlaylist(subCategoryName)) {
////                        resource = getClass().getClassLoader().getResource("images/cyanStar.png");
////                        img = ImageIO.read(resource);
////                    } else {
////                        resource = getClass().getClassLoader().getResource("images/star.png");
////                        img = ImageIO.read(resource);
////                    }
////                    favPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
////                } catch (IOException e1) {
////                    e1.printStackTrace();
////                }
//            }
//        });
//
//        Album finalAlbum = album;
//        Playlist finalPlaylist = playlist;
//        Playlist finalPlaylist1 = playlist;
//        Album finalAlbum1 = album;
//        follow.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //controller.toggleFollow(subCategoryName);
//                if(finalPlaylist1 != null) finalPlaylist1.setFollowed(!finalPlaylist1.isFollowed());
//                if(finalAlbum1 != null) finalAlbum1.setFollowed(!finalAlbum1.isFollowed());
//                try {
//                    URL resource;
//                    BufferedImage img;
//                    if (finalAlbum.isFollowed() || finalPlaylist.isFollowed()) {
//                        resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
//                        img = ImageIO.read(resource);
//                    } else {
//                        resource = getClass().getClassLoader().getResource("images/follow.png");
//                        img = ImageIO.read(resource);
//                    }
//                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        });
//
//        int finalPlaylistID2 = playlistID;
//        int finalAlbumID2 = albumID;
//        remove.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int choice = JOptionPane.showConfirmDialog(null,
//                        "Are you sure you want to remove this " + category.toLowerCase().substring(0, category.length() - 2) + "?",
//                        "Remove " + category.toLowerCase(), JOptionPane.YES_NO_OPTION);
//                if (choice == JOptionPane.YES_OPTION) {
////                    controller.remove(category, subCategoryName);
//                    switch (category) {
//                        case "Playlists":
//                            controller.removePlaylist(finalPlaylistID2);
//                            break;
//                        case "Albums":
//                            controller.removeAlbum(finalAlbumID2);
//                            break;
////                        case "Favorite Playlists":
////                            controller.removePlaylist(subCategoryName);
////                            break;
//                    }
//                }
//            }
//        });
//
//        Album finalAlbum2 = album;
//        Playlist finalPlaylist2 = playlist;
//        edit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(finalAlbum2 != null)
//                    controller.openEditCategoryWindow(category, finalAlbum2);
//                if(finalPlaylist2 != null)
//                    controller.openEditCategoryWindow(category, finalPlaylist2);
//            }
//        });
//        if (category.equals("Years")) edit.setVisible(false);
//        if (category.equals("Years")) remove.setVisible(false);
//        int finalAlbumID3 = albumID;
//        int finalPlaylistID3 = playlistID;
//        int finalArtistID3 = artistID;
//        play.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                switch (category) {
//                    case "Albums":
//                        controller.playSongsInAlbum(finalAlbumID3);
//                        break;
//                    case "Playlists":
//                        controller.playSongsInPlaylist(finalPlaylistID3);
//                        break;
////                    case "Genres":
////                        controller.playSongsInGenre(subCategoryName);
////                        break;
////                    case "Years":
////                        controller.playSongsInYear(subCategoryName);
////                        break;
////                    case "Favorite Playlists":
////                        controller.playSongsInPlaylist(subCategoryName);
////                        break;
////                    case "Artists":
////                        controller.playSongsByArtist(finalArtistID3);
////                        break;
//                }
////                        PlayerThread pt = new PlayerThread(controller.getMainController().getPlayerController(), queue);
////                        new Thread(pt).start();
//
//            }
//        });
////        changeCover.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                JFileChooser chooser = new JFileChooser();
////                chooser.setDialogTitle("Import Cover Photo");
//////                chooser.addChoosableFileFilter(new FileNameExtensionFilter(
//////                        "MP3 File", "mp3"));
////                int returnVal = chooser.showOpenDialog(null);
////                if (returnVal == JFileChooser.APPROVE_OPTION) {
////                    File file = chooser.getSelectedFile();
////                    controller.setAlbumCover(subCategoryName, file);
////                }
////            }
////        });
//
//        GridBagConstraints cons = new GridBagConstraints();
//        cons.insets = new Insets(5, 10, 0, 0);
//        cons.gridx = 0;
//        cons.gridy = i;
//        cons.gridwidth = 1;
//        block.add(subOptionButton, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 2;
//        block.add(creator, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 3;
//        block.add(play, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 4;
//        block.add(addToQueue, cons);
//        cons.insets = new Insets(5, 0, 0, 0);
//        cons.gridx = 5;
//        cons.gridwidth = 1;
//        block.add(edit, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 6;
//        block.add(remove, cons);
////        cons.insets = new Insets(5, 0, 0, 10);
////        cons.gridx = 7;
////        block.add(favPlaylist, cons);
////        cons.insets = new Insets(5, 0, 0, 10);
////        cons.gridx = 8;
////        block.add(changeCover, cons);
//    }
//}






//package view;
//
//import controller.*;
//import util.ImageResizer;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class UserArtistListPanel extends JPanel {
//    private SongController controller;
//    private JLabel headerName;
//    private JScrollPane scroll;
//    private JPanel block;
//    private ArrayList<String> data;
//    private boolean isNormalPlaylist = false;
//    String category;
//
//    private int i;
//    //needs a header name as string and an arraylist of arraylist as parameter input for diaplaying the list of [category]
//    public UserArtistListPanel(SongController controller, String usertype, ArrayList<String> peopleList){
//        category = usertype;
//        this.controller = controller;
//        data = peopleList;
//
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        //setAlignmentX(Component.LEFT_ALIGNMENT);
//        setOpaque(false);
//
//        if(peopleList.size() == 0){
//
//        }
//        else{
//
//        }
//        add(Box.createRigidArea(new Dimension(0,7)));
//        headerName = new JLabel((category).toUpperCase());
//        headerName.setFont(new Font("Arial", Font.BOLD, 26));
//        headerName.setForeground(Color.white);
//        add(headerName);
//        add(Box.createRigidArea(new Dimension(0,10)));
//
//        block = new JPanel();
//        block.setLayout(new GridBagLayout());
//        block.setOpaque(false);
//        GridBagConstraints cons = new GridBagConstraints();
//        cons.fill = GridBagConstraints.HORIZONTAL;
//        scroll = new JScrollPane(block);
//        scroll.setOpaque(false);
//        scroll.getViewport().setOpaque(false);
//        scroll.setPreferredSize(new Dimension(50,100));
//        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        if(peopleList.isEmpty()){
//            cons.insets = new Insets(10, 10, 2, 10);
//            cons.gridx = 0;
//            cons.gridy = 0;
//            cons.gridwidth = 3;
//            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
//            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
//            block.add(emptyLabel, cons);
//        }
//        else{
//            for(i=0; i<peopleList.size(); i++){
//                addRow(category, peopleList.get(i));
//            }
//            add(scroll);
//        }
//
//    }
//
//    public void update() {
//        revalidate();
//        repaint();
//    }
//
//    public void isNormalPlaylist(boolean normalPlaylist) {
//        isNormalPlaylist = normalPlaylist;
//    }
//
//    public void addRow(String category, String personName) {
//        JButton subOptionButton = new JButton();
//        subOptionButton.setOpaque(false);
//        subOptionButton.setContentAreaFilled(false);
//        subOptionButton.setBorderPainted(false);
//        subOptionButton.setForeground(Color.white);
//        JButton follow = new JButton();
//        follow.setOpaque(false);
//        follow.setContentAreaFilled(false);
//        follow.setBorderPainted(false);
//        follow.setVisible(false);
//        if(category.equals("Artists")) follow.setVisible(true);
//
//        try {
////            URL resource = getClass().getClassLoader().getResource("images/follow.png");
////            BufferedImage img = ImageIO.read(resource);
//            URL resource;
//            BufferedImage img;
//            if (controller.isFollowed(personName)) {
//                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
//                img = ImageIO.read(resource);
//            } else {
//                resource = getClass().getClassLoader().getResource("images/follow.png");
//                img = ImageIO.read(resource);
//            }
//            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        subOptionButton.setText(personName);
//
//        subOptionButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //show info panel of selected user or artist
//            }
//        });
//
//        follow.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                //follow user/artist
//                //set icon to cyanFollow.png
//            }
//        });
//
//        GridBagConstraints cons = new GridBagConstraints();
//        cons.insets = new Insets(5, 10, 0, 0);
//        cons.gridx = 0;
//        cons.gridy = i;
//        cons.gridwidth = 1;
//        block.add(subOptionButton, cons);
//        cons.insets = new Insets(5, 0, 0, 10);
//        cons.gridx = 3;
//        block.add(follow, cons);
//    }
//}
