package View;

import Controller.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SettingsWindow extends JFrame implements ActionListener {
    private JTextField titleInput, albumInput, genreInput, yearInput;
    private JButton back, addToPlaylist, edit;

    public SettingsWindow(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));

        JLabel songTitle = new JLabel("Song Profile");
        songTitle.setForeground(Color.white);
        songTitle.setFont(new Font("Arial", Font.PLAIN, 45));
        songTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(songTitle);

        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel titleLabel = new JLabel("Title: ");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(titleLabel);
        //p1.add(Box.createRigidArea(new Dimension(5,0))); // add space
        titleInput = new JTextField("" , 15);
        titleInput.addActionListener(this);
        //titleInput.getDocument().addDocumentListener(this);
        titleInput.setFont(new Font("Arial", Font.BOLD, 22));
        titleInput.setEditable(false);
        //titleInput.setBackground(new Color(152,251,152));
        p1.add(titleInput);
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        p1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel albumLabel = new JLabel("Album: ");
        albumLabel.setForeground(Color.WHITE);
        albumLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p2.add(albumLabel);
        //p2.add(Box.createRigidArea(new Dimension(5,0))); // add space
        albumInput = new JTextField("" , 15);
        albumInput.addActionListener(this);
        //albumInput.getDocument().addDocumentListener(this);
        albumInput.setFont(new Font("Arial", Font.BOLD, 22));
        albumInput.setEditable(false);
        //titleInput.setBackground(new Color(152,251,152));
        p2.add(albumInput);
        p2.add(Box.createRigidArea(new Dimension(15,0)));
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        p3.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel genreLabel = new JLabel("Genre: ");
        genreLabel.setForeground(Color.WHITE);
        genreLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p3.add(genreLabel);
        //p2.add(Box.createRigidArea(new Dimension(5,0))); // add space
        genreInput = new JTextField("" , 15);
        genreInput.addActionListener(this);
        //genreInput.getDocument().addDocumentListener(this);
        genreInput.setFont(new Font("Arial", Font.BOLD, 22));
        genreInput.setEditable(false);
        //titleInput.setBackground(new Color(152,251,152));
        p3.add(genreInput);
        p3.add(Box.createRigidArea(new Dimension(15,0)));
        p3.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        p4.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setForeground(Color.WHITE);
        yearLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p4.add(yearLabel);
        //p2.add(Box.createRigidArea(new Dimension(5,0))); // add space
        yearInput = new JTextField("" , 15);
        yearInput.addActionListener(this);
        //yearInput.getDocument().addDocumentListener(this);
        yearInput.setFont(new Font("Arial", Font.BOLD, 22));
        yearInput.setEditable(false);
        //titleInput.setBackground(new Color(152,251,152));
        p4.add(yearInput);
        p4.add(Box.createRigidArea(new Dimension(15,0)));
        p4.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p5 = new JPanel();
        p5.setOpaque(false);
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        //subP3.setLayout(new FlowLayout());
        back = new JButton("Back");
        //cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.addActionListener(this);
        back.setFont(new Font("Arial", Font.PLAIN, 24));
        back.setForeground(Color.white);
        back.setBackground(new Color(1,121,150));
        back.setOpaque(true);
        back.setBorderPainted(false);
        p5.add(back);
        p5.add(Box.createRigidArea(new Dimension(10,0)));
        addToPlaylist = new JButton("Add ToPlaylist");
        addToPlaylist.setAlignmentX(Component.CENTER_ALIGNMENT);
        addToPlaylist.addActionListener(this);
        addToPlaylist.setFont(new Font("Arial", Font.PLAIN, 24));
        addToPlaylist.setForeground(Color.white);
        addToPlaylist.setBackground(new Color(1,121,150));
        addToPlaylist.setOpaque(true);
        addToPlaylist.setBorderPainted(false);
        addToPlaylist.setEnabled(false);
        p5.add(addToPlaylist);
        p5.add(Box.createRigidArea(new Dimension(10,0)));
        edit = new JButton("Edit");
        edit.setAlignmentX(Component.CENTER_ALIGNMENT);
        edit.addActionListener(this);
        edit.setFont(new Font("Arial", Font.PLAIN, 24));
        edit.setForeground(Color.white);
        edit.setBackground(new Color(1,121,150));
        edit.setOpaque(true);
        edit.setBorderPainted(false);
        edit.setEnabled(false);
        p5.add(edit);
        p.add(p5);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        add(p);
        setVisible(true);
        setResizable(false);
        //setUndecorated(true);
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == back){
            dispose();
        }
        if(e.getSource() == addToPlaylist){
            //JOption get text then add to the playlist
            String playlist_name;
            playlist_name = JOptionPane.showInputDialog("Enter playlist name:");
            //check if playlist name is new or not, if new ask if want to create new playlist
            boolean testingOnly = true;
            if(testingOnly){
                int choice = JOptionPane.showConfirmDialog(null, "This playlist is not found in your library.\n"
                        + "Create new playlist?", "New Playlist", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    String new_playlist_name;
                    new_playlist_name = JOptionPane.showInputDialog("Enter playlist name:");
                    //then add the new playlist in the database
                    //then add the song into the new playlist
                    dispose();
                } else if (choice == JOptionPane.NO_OPTION) {
                    //open main screen (not logged in)
                    dispose();
                }
            }
        }
        if(e.getSource() == edit){
            EditSongProfileWindow espw = new EditSongProfileWindow();
            dispose();
        }
    }

    public static void main(String[] args){
        SettingsWindow sw = new SettingsWindow();
    }
}
