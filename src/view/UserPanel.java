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

    public UserPanel(SongController controller, String userCategory, ArrayList<Object> objects) {
        super(controller, userCategory, objects);
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
        follow.setVisible(false);
        follow.setVisible(true);

        try {
            URL resource;
            BufferedImage img;
            if (user.isFollowed()) {
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

        subOptionButton.setText(user.getName());

        subOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showSongsByUser(user.getUserId());
            }
        });

        follow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL resource;
                    BufferedImage img;
                    if (user.isFollowed()) {
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
