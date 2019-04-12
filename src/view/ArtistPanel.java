package view;

import controller.SongController;
import model.Artist;
import util.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ArtistPanel extends CategoryPanel {
    private ArrayList<Artist> data;

    public ArtistPanel(SongController controller, String userCategory, ArrayList<Artist> peopleList) {
        super(controller, userCategory);

        this.controller = controller;
        data = peopleList;

        if(peopleList.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No " + userCategory.toLowerCase() + " to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(Object object : peopleList){
                addRow(userCategory, object);
            }
            add(scroll);
        }

    }

    public void addRow(String category, Object object) { JButton subOptionButton = new JButton();
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


