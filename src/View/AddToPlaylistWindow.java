package View;

import Controller.*;

import Controller.MainController;
import Model.Playlist;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddToPlaylistWindow extends JFrame implements ActionListener, DocumentListener {
    private SongController controller;
    private JTextField nameInput;
    private JButton save, cancel;
    private String name;
    private JComboBox playlistChoices;
    private boolean confirmation;
    private int songIndex;

    public AddToPlaylistWindow(SongController controller, int index) {
        this.controller = controller;
        songIndex = index;
        confirmation = false;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
//        JLabel title = new JLabel("Playlist");
//        title.setForeground(Color.WHITE);
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
//        title.setFont(new Font("Arial", Font.BOLD, 30));
//        p.add(title);
//        p.add(Box.createRigidArea(new Dimension(0,20)));

        JPanel inputPnl = new JPanel();
        inputPnl.setLayout(new BoxLayout(inputPnl, BoxLayout.X_AXIS));
        inputPnl.add(Box.createRigidArea(new Dimension(15,0)));
        inputPnl.setOpaque(false);
        JLabel inputLabel = new JLabel("Playlist Name: ");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        inputLabel.setForeground(Color.white);
        inputPnl.add(inputLabel);
        inputPnl.add(Box.createRigidArea(new Dimension(5,0)));
//        nameInput = new JTextField("",10);
//        nameInput.addActionListener(this);
//        nameInput.getDocument().addDocumentListener(this);
//        nameInput.setFont(new Font("Arial", Font.PLAIN, 22));
//        inputPnl.add(nameInput);
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Select a Playlist");
        for(Playlist playlist : controller.getMainController().getPlaylists()){
                choices.add(playlist.getName());
        }
        String[] sChoices = choices.toArray(new String[choices.size()]);
        playlistChoices = new JComboBox(sChoices);
        inputPnl.add(playlistChoices);
        playlistChoices.addActionListener(this);
        inputPnl.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(inputPnl);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel buttonsPnl = new JPanel();
        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.X_AXIS));
        buttonsPnl.setOpaque(false);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        buttonsPnl.add(cancel);
        buttonsPnl.add(Box.createRigidArea(new Dimension(10,0)));
        save = new JButton("Add to Playlist");
        save.setFont(new Font("Arial", Font.PLAIN, 22));
        save.setForeground(Color.white);
        save.setBackground(new Color(1,121,150));
        save.setOpaque(true);
        save.setBorderPainted(false);
        save.addActionListener(this);
        save.setEnabled(false);
        buttonsPnl.add(save);
        buttonsPnl.add(Box.createRigidArea(new Dimension(0,7)));
        p.add(buttonsPnl);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        add(p);
        pack();
        setVisible(true);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        //setUndecorated(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public boolean isAdd(){
        return confirmation;
    }

    public Playlist getPlaylist() {
        for(Playlist playlist : controller.getMainController().getPlaylists()){
            if(playlist.getName().equals(getName())){
                return playlist;
            }
        }
        return null;
    }

    public void actionPerformed (ActionEvent e){

        if (playlistChoices.getSelectedIndex() != 0)
            save.setEnabled(true);
        else
            save.setEnabled(false);

        if(e.getSource() == cancel){
            dispose();
        }
        if(e.getSource() == save){
            setName((String) playlistChoices.getSelectedItem());
            confirmation = true;
            controller.addToPlaylist(songIndex, playlistChoices.getSelectedIndex() - 1);
            dispose();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        if (playlistChoices.getSelectedIndex() == 0)
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        if (playlistChoices.getSelectedIndex() == 0)
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        if (playlistChoices.getSelectedIndex() == 0)
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

//    public static void main(String[] args){
//        AddToPlaylistWindow apw = new AddToPlaylistWindow();
//    }

}

