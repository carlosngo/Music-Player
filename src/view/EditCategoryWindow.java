package view;

import controller.SongController;
import model.Album;
import model.Playlist;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditCategoryWindow extends JFrame implements ActionListener, DocumentListener {
    private SongController controller;
    private JTextField nameInput;
    private JButton save, cancel;
    private String category, oldName;
    private boolean isChanged;
    private int objID, artistID;
    private File file;

    public EditCategoryWindow(SongController controller, String category, Object obj) {
        this.controller = controller;
        this.category = category;
        String name;
        if(obj instanceof Album){
            Album album = (Album) obj;
            name = album.getName();
            objID = album.getAlbumId();
            artistID = album.getArtist().getArtistId();
            file = album.getCover();
        }
        else{
            Playlist playlist = (Playlist) obj;
            name = playlist.getName();
            objID = playlist.getPlaylistId();
            artistID = playlist.getAccount().getId();
        }
        oldName = name;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Edit " + category +" Name");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,20)));

        JPanel inputPnl = new JPanel();
        inputPnl.setLayout(new BoxLayout(inputPnl, BoxLayout.X_AXIS));
        inputPnl.add(Box.createRigidArea(new Dimension(15,0)));
        inputPnl.setOpaque(false);
        JLabel inputLabel = new JLabel("New Name: ");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        inputLabel.setForeground(Color.white);
        inputPnl.add(inputLabel);
        inputPnl.add(Box.createRigidArea(new Dimension(5,0)));
        nameInput = new JTextField(name,10);
        nameInput.addActionListener(this);
        nameInput.getDocument().addDocumentListener(this);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 22));
        inputPnl.add(nameInput);
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
        save = new JButton("Save");
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public boolean getIsChanged(){
        return isChanged;
    }

    public void actionPerformed (ActionEvent e){
        if(e.getSource() == cancel){
            dispose();
        }
        if(e.getSource() == save){
            switch (category) {
//                case "Genres":
//                    controller.updateGenre(oldName, nameInput.getText());
//                    break;
                case "Albums":
                    controller.updateAlbum(objID, nameInput.getText(), file);
                    break;
                case "Playlists":
                    controller.updatePlaylist(objID, nameInput.getText());
                    break;
//                case "Years":
////                    controller.updateYear(oldName, nameInput.getText());
//                    break;
//                case "Favorite Playlists":
//                    controller.updatePlaylist(oldName, nameInput.getText());
            }
            dispose();
        }
    }

    public String getNewName(){
        return nameInput.getText();
    }

    public void insertUpdate(DocumentEvent e) {
        if (nameInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        if (nameInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        if (nameInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }


//    public static void main(String[] args){
//        EditCategoryWindow epw = new EditCategoryWindow("[sub category]");
//    }

}

