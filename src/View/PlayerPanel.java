package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

public class PlayerPanel extends JPanel implements ActionListener {
    private JButton volume, repeat, rewind, playOrPause, shuffle, fastforward;
    JLabel titleLbl, albumLbl, genreLbl, artistLbl;

    public PlayerPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(7,0)));
        JPanel albumPanel = new JPanel();
        albumPanel.setPreferredSize(new Dimension(25,25));
        albumPanel.setMinimumSize(new Dimension(25,25));
        albumPanel.setMaximumSize(new Dimension(25,25));
        albumPanel.setOpaque(true);
        albumPanel.setBackground(Color.CYAN);
        add(albumPanel);
        add(Box.createRigidArea(new Dimension(5,0)));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setOpaque(false);
        titleLbl = new JLabel("Title: ");
        titleLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLbl.setForeground(Color.white);
        p1.add(titleLbl);
        albumLbl = new JLabel("Album: ");
        albumLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        albumLbl.setForeground(Color.white);
        p1.add(albumLbl);
        genreLbl = new JLabel("Genre: ");
        genreLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        genreLbl.setForeground(Color.white);
        p1.add(genreLbl);
        artistLbl = new JLabel("Artist: ");
        artistLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        artistLbl.setForeground(Color.white);
        p1.add(artistLbl);
        add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.setOpaque(false);
        //put p3 slider here
        //p2.add(p3 or sliderPnl)
        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p4.setOpaque(false);
        repeat = new JButton();
        repeat.setOpaque(false);
        repeat.setContentAreaFilled(false);
        repeat.setBorderPainted(false);
        p4.add(repeat);
        p4.add(Box.createRigidArea(new Dimension(8,0)));
        rewind = new JButton();
        rewind.setOpaque(false);
        rewind.setContentAreaFilled(false);
        rewind.setBorderPainted(false);
        p4.add(rewind);
        p4.add(Box.createRigidArea(new Dimension(5,0)));
        playOrPause = new JButton();
        playOrPause.setOpaque(false);
        playOrPause.setContentAreaFilled(false);
        playOrPause.setBorderPainted(false);
        p4.add(playOrPause);
        p4.add(Box.createRigidArea(new Dimension(5,0)));
        fastforward = new JButton();
        fastforward.setOpaque(false);
        fastforward.setContentAreaFilled(false);
        fastforward.setBorderPainted(false);
        p4.add(fastforward);
        p4.add(Box.createRigidArea(new Dimension(5,0)));
        shuffle = new JButton("Shuffle");
        shuffle.setOpaque(false);
        shuffle.setContentAreaFilled(false);
        shuffle.setBorderPainted(false);
        p4.add(shuffle);
        p4.add(Box.createRigidArea(new Dimension(0,5)));

        //add(Box.createRigidArea(new Dimension(8,0)));

        //volume slider pnl or p5 here

        try{
            URL resource = getClass().getClassLoader().getResource("imgRepeatBtn.png");
            File img = Paths.get(resource.toURI()).toFile();
            repeat.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("imgBackBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            rewind.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("imgPlayBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            playOrPause.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 20, 20)));
            resource = getClass().getClassLoader().getResource("imgNextBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            fastforward.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("imgShuffleBtn.png");
            img = Paths.get(resource.toURI()).toFile();
            shuffle.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        }catch (Exception e) {
            System.out.println("File not found");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
