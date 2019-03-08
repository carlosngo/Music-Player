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
    private boolean isShuffle, isRepeat;


    public PlayerPanel(PlayerController pc){
        this.pc = pc;
        isShuffle = false;
        isRepeat = false;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(true);
        setBackground(Color.BLACK);
        add(Box.createRigidArea(new Dimension(7,0)));

        albumCover = new JLabel("");
        albumCover.setOpaque(true);
        add(albumCover);
        add(Box.createRigidArea(new Dimension(10,0)));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setOpaque(false);
        titleLbl = new JLabel("");
        titleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLbl.setForeground(Color.white);
        p1.add(titleLbl);
        p1.add(Box.createRigidArea(new Dimension(0, 5)));
        artistLbl = new JLabel("");
        artistLbl.setFont(new Font("Arial", Font.PLAIN, 12));
        artistLbl.setForeground(Color.white);
        p1.add(artistLbl);
        p1.setMinimumSize(new Dimension(125, 40));
        p1.setPreferredSize(new Dimension(125, 40));
        p1.setMaximumSize(new Dimension(125, 40));
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.setOpaque(false);

        prev = new JButton();
        prev.setActionCommand("Prev");
        prev.setOpaque(false);
        prev.setContentAreaFilled(false);
        prev.setBorderPainted(false);
        prev.addActionListener(pc);
        add(prev);
        add(Box.createRigidArea(new Dimension(5,0)));

        next = new JButton();
        next.setActionCommand("Next");
        next.setOpaque(false);
        next.setContentAreaFilled(false);
        next.setBorderPainted(false);
        next.addActionListener(pc);
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

        repeat = new JButton();
        repeat.setActionCommand("Repeat");
        repeat.setOpaque(false);
        repeat.setContentAreaFilled(false);
        repeat.setBorderPainted(false);
        repeat.addActionListener(this);
        add(repeat);
        add(Box.createRigidArea(new Dimension(8,0)));

        shuffle = new JButton();
        shuffle.setActionCommand("Shuffle");
        shuffle.setOpaque(false);
        shuffle.setContentAreaFilled(false);
        shuffle.setBorderPainted(false);
        shuffle.addActionListener(this);
        add(shuffle);
        add(Box.createRigidArea(new Dimension(0,5)));

        try{
            URL resource;
            File img;
            resource = getClass().getClassLoader().getResource("images/imgRepeatBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgBackBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            prev.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//            resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
//            img = Paths.get(resource.toURI()).toFile();
//            playOrPause.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 20)));
            resource = getClass().getClassLoader().getResource("images/imgNextBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            next.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/imgShuffleBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/nocover.jpg");
            img = Paths.get(resource.toURI()).toFile();
            albumCover.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 35, 35)));
        }catch (URISyntaxException e) {
            System.out.println("File not found");
        }
        setMinimumSize(new Dimension(650, 55));
        setPreferredSize(new Dimension(650, 55));
        setMaximumSize(new Dimension(650, 55));
    }

    public void update(String title, String artist, Component controlPnl) {
        titleLbl.setText(title);
        artistLbl.setText(artist);
        if (this.controlPnl != null) p3.remove(this.controlPnl);
        if (controlPnl != null) p3.add(controlPnl);
        this.controlPnl = controlPnl;
        revalidate();
        repaint();
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Repeat")) {
            isRepeat = !isRepeat;
            System.out.println("Pressed Repeat");
            if (isRepeat) {
                // set image to repeat pressed
            } else {
                // set image to repeat not pressed
            }
        } else if (e.getActionCommand().equals("Shuffle")) {
            isShuffle = !isShuffle;
            if (isShuffle) {
                // set image to shuffle pressed
            } else {
                // set image to shuffle not pressed
            }
        }
    }
}
