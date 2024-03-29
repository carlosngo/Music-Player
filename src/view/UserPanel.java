package view;

import controller.SongController;
import model.*;
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

public class UserPanel extends CategoryPanel {
    private ArrayList<User> data;

    public UserPanel(SongController controller, ArrayList<User> objects) {
        super(controller,"Friends");


        if(objects.size() == 0){
            JLabel blankMessage = new JLabel("No friends to show.");
            blankMessage.setForeground(Color.white);
            blankMessage.setFont(new Font("Arial", Font.BOLD, 22));
            headerPnl.add(blankMessage, BorderLayout.CENTER);
            //scroll.removeAll();
//            cons.insets = new Insets(10, 10, 2, 10);
//            cons.gridx = 0;
//            cons.gridy = 0;
//            cons.gridwidth = 3;
//            //JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
//            JLabel emptyLabel = new JLabel("No users to display.");
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
        User user = (User) object;

        JButton subOptionButton = new JButton();
        subOptionButton.setOpaque(false);
        subOptionButton.setContentAreaFilled(false);
        subOptionButton.setBorderPainted(false);
        subOptionButton.setForeground(Color.white);
        JButton follow = new JButton();
        follow.setOpaque(false);
        follow.setContentAreaFilled(false);
        follow.setBorderPainted(false);
        follow.setToolTipText("Follow Listener");
//        follow.setVisible(true);
//        JButton play = new JButton();
//        play.setOpaque(false);
//        play.setContentAreaFilled(false);
//        play.setBorderPainted(false);
//        play.setVisible(false);

        try {
            URL resource;
            BufferedImage img;
            if (controller.isFollowingUser(user.getAccount().getId())) {
                resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                img = ImageIO.read(resource);
                follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            } else {
                resource = getClass().getClassLoader().getResource("images/follow.png");
                img = ImageIO.read(resource);
                follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
//            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
//            img = ImageIO.read(resource);
//            play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        subOptionButton.setText(user.getName());

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showInfo(user);
            }
        });

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL resource;
                    BufferedImage img;
                    if (!controller.isFollowingUser(user.getAccount().getId())) {
                        controller.followUser(user);
                        resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                        img = ImageIO.read(resource);
                    } else {
                        controller.unfollowUser(user);
                        resource = getClass().getClassLoader().getResource("images/follow.png");
                        img = ImageIO.read(resource);
                    }
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

//        play.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                controller.playSongsByUser(user.getUserId());
//            }
//        });

        GridBagConstraints cons = new GridBagConstraints();
        cons.insets = new Insets(5, 10, 0, 0);
        cons.gridx = 0;
        cons.gridy = i;
        cons.gridwidth = 1;
        block.add(subOptionButton, cons);
        /*if(user.getAccount().getId() != controller.getMainController().getAc().getUser().getAccount().getId()){
            cons.insets = new Insets(5, 0, 0, 10);
            cons.gridx = 3;
            block.add(follow, cons);
        }*/
        cons.insets = new Insets(5, 0, 0, 10);
        cons.gridx = 3;
        block.add(follow, cons);
        if(user.getAccount().getId() == controller.getMainController().getAc().getUser().getAccount().getId()) {
            follow.setVisible(false);
        }
    }
}
