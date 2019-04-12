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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public abstract class PeoplePanel extends JPanel {
    private JLabel headerName;
    private boolean isNormalPlaylist = false;
    protected SongController controller;
    protected GridBagConstraints cons;
    protected JScrollPane scroll;
    protected JPanel block;
    protected String category;
    protected int i;

    //needs a header name as string and an arraylist of arraylist as parameter input for diaplaying the list of [category]
    public PeoplePanel(SongController controller, String usertype){
        //, ArrayList<String> peopleList
        category = usertype;
        this.controller = controller;

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
        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if(peopleList.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(i=0; i<peopleList.size(); i++){
                addRow(category, peopleList.get(i));
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

    public void addRow(String category, String personName) {
        JButton subOptionButton = new JButton();
        subOptionButton.setOpaque(false);
        subOptionButton.setContentAreaFilled(false);
        subOptionButton.setBorderPainted(false);
        subOptionButton.setForeground(Color.white);
        JButton follow = new JButton();
        follow.setOpaque(false);
        follow.setContentAreaFilled(false);
        follow.setBorderPainted(false);
        follow.setVisible(false);
        if(category.equals("Artists")) follow.setVisible(true);

        try {
//            URL resource = getClass().getClassLoader().getResource("images/follow.png");
//            BufferedImage img = ImageIO.read(resource);
            URL resource;
            BufferedImage img;
            if (controller.isFollowed(personName)) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
            }
            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(personName);

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show info panel of selected user or artist
            }
        });

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //follow user/artist
                //set icon to cyanFollow.png
            }
        });

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5, 10, 0, 0);
        cons.gridx = 0;
        cons.gridy = i;
        cons.gridwidth = 1;
        block.add(subOptionButton, cons);
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 3;
        block.add(follow, cons);
    }
}



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
