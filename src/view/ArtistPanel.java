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

    public ArtistPanel(SongController controller, ArrayList<Artist> objects) {
        super(controller, "Artists");

        if(objects.size() == 0){
            JLabel blankMessage = new JLabel("No artists to show.");
            blankMessage.setForeground(Color.white);
            blankMessage.setFont(new Font("Arial", Font.BOLD, 22));
            headerPnl.add(blankMessage, BorderLayout.CENTER);
//            cons.insets = new Insets(10, 10, 2, 10);
//            cons.gridx = 0;
//            cons.gridy = 0;
//            cons.gridwidth = 3;
//            //JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
//            JLabel emptyLabel = new JLabel("No artists to display.");
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

    public void addRow(String category, Object object) {
        Artist artist = (Artist) object;

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
        follow.setVisible(true);
        JButton play = new JButton();
        play.setOpaque(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        play.setVisible(false);

        try {
//            URL resource = getClass().getClassLoader().getResource("images/follow.png");
//            BufferedImage img = ImageIO.read(resource);
            URL resource;
            BufferedImage img;
            if (artist.isFollowed()) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
            }
            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
            img = ImageIO.read(resource);
            play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(artist.getName());

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByArtist(artist.getArtistId());
            }
        });

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.followArtist(artist);
                try {
                    URL resource;
                    BufferedImage img;
                    if (artist.isFollowed()) {
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

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.playSongsByArtist(artist.getArtistId());
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
        block.add(play, cons);
        if(artist.getAccount().getId() != controller.getMainController().getAc().getUser().getAccount().getId()){
            cons.insets = new Insets(5, 0, 0, 10);
            cons.gridx = 4;
            block.add(follow, cons);
        }
    }
}


