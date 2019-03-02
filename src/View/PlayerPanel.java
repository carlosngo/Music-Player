package View;

import Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class PlayerPanel extends JPanel implements ActionListener {

    private PlayerController pc;
    private JButton repeat, shuffle, prev, next;
    private JLabel albumCover, titleLbl, artistLbl;
    private Component controlPnl;
    private JPanel p3;

    public PlayerPanel(PlayerController pc){
        this.pc = pc;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setOpaque(false);
        add(Box.createRigidArea(new Dimension(7,0)));

        albumCover = new JLabel();
        albumCover.setOpaque(true);
        add(albumCover);
        add(Box.createRigidArea(new Dimension(10,0)));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
//        p1.setOpaque(false);
        titleLbl = new JLabel();
        titleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
//        titleLbl.setForeground(Color.white);
        p1.add(titleLbl);
        p1.add(Box.createRigidArea(new Dimension(0, 5)));
        artistLbl = new JLabel();
        artistLbl.setFont(new Font("Arial", Font.PLAIN, 12));
//        artistLbl.setForeground(Color.white);
        p1.add(artistLbl);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
//        p2.setOpaque(false);

        prev = new JButton("Prev");
//        rewind.setOpaque(false);
        prev.setContentAreaFilled(false);
        prev.setBorderPainted(false);
        add(prev);
        add(Box.createRigidArea(new Dimension(5,0)));

        next = new JButton("Next");
//        rewind.setOpaque(false);
        next.setContentAreaFilled(false);
        next.setBorderPainted(false);
        add(next);
        add(Box.createRigidArea(new Dimension(5,0)));

        p3 = new JPanel();
        controlPnl = new JLabel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.add(controlPnl);
        p3.setMinimumSize(new Dimension(220, 25));
        p3.setPreferredSize(new Dimension(220, 25));
        p3.setMaximumSize(new Dimension(220, 25));
        add(p3);

        repeat = new JButton("Repeat");
//        repeat.setOpaque(false);
        repeat.setContentAreaFilled(false);
        repeat.setBorderPainted(false);
        add(repeat);
        add(Box.createRigidArea(new Dimension(8,0)));

        shuffle = new JButton("Shuffle");
//        shuffle.setOpaque(false);
        shuffle.setContentAreaFilled(false);
        shuffle.setBorderPainted(false);
        add(shuffle);
        add(Box.createRigidArea(new Dimension(0,5)));

        try{
            URL resource;
            File img;
//            URL resource = getClass().getClassLoader().getResource("imgRepeatBtn.png");
//            File img = Paths.get(resource.toURI()).toFile();
//            repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("imgBackBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            rewind.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("imgPlayBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            playOrPause.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 20)));
//            resource = getClass().getClassLoader().getResource("imgNextBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            fastforward.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("imgShuffleBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("nocover.jpg");
            img = Paths.get(resource.toURI()).toFile();
            albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
        }catch (URISyntaxException e) {
            System.out.println("File not found");
        }
        setMinimumSize(new Dimension(600, 45));
        setPreferredSize(new Dimension(600, 45));
        setMaximumSize(new Dimension(600, 45));
    }

    public void update(String title, String artist, Component controlPnl) {
        titleLbl.setText(title);
        artistLbl.setText(artist);
        p3.remove(this.controlPnl);
        p3.add(controlPnl);
        this.controlPnl = controlPnl;
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
