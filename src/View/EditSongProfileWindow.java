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

public class EditSongProfileWindow extends JFrame implements ActionListener, DocumentListener {
    private SongController controller;
    private JTextField titleInput, albumInput, genreInput, yearInput, artistInput;
    private JButton cancel, save;
    private String title, album, genre, year, artist;
    private boolean choice;
    private int selectedRow;
    private JComboBox genreChoices;

    public EditSongProfileWindow(SongController controller, ArrayList<String> data, int selectedRow){
        this.controller = controller;
        this.selectedRow = selectedRow;
        choice = false;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));

        JLabel songTitle = new JLabel("Song Profile");
        songTitle.setForeground(Color.white);
        songTitle.setFont(new Font("Arial", Font.PLAIN, 28));
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
        titleInput.getDocument().addDocumentListener(this);
        titleInput.setFont(new Font("Arial", Font.BOLD, 22));
        //titleInput.setEditable(false);
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
        albumInput.getDocument().addDocumentListener(this);
        albumInput.setFont(new Font("Arial", Font.BOLD, 22));
        //albumInput.setEditable(false);
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
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Select a Genre");
        for(String genre : controller.getMainController().getGenres()){
            choices.add(genre);
        }
        String[] sChoices = choices.toArray(new String[choices.size()]);
        genreChoices = new JComboBox(sChoices);
        genreChoices.setEditable(true);
        p3.add(genreChoices);
//        genreInput = new JTextField("" , 15);
//        genreInput.addActionListener(this);
//        genreInput.getDocument().addDocumentListener(this);
//        genreInput.setFont(new Font("Arial", Font.BOLD, 22));
//        p3.add(genreInput);
        p3.add(Box.createRigidArea(new Dimension(15,0)));
        p3.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p6 = new JPanel();
        p6.setOpaque(false);
        p6.setLayout(new BoxLayout(p6, BoxLayout.X_AXIS));
        p6.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel artistLabel = new JLabel("Artist: ");
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        artistLabel.setForeground(Color.white);
        p6.add(artistLabel);
        p6.add(Box.createRigidArea(new Dimension(5,0)));
        artistInput = new JTextField("" , 15);
        artistInput.addActionListener(this);
        artistInput.getDocument().addDocumentListener(this);
        artistInput.setFont(new Font("Arial", Font.PLAIN, 22));
        p6.add(artistInput);
        p6.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(p6);
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
        yearInput.getDocument().addDocumentListener(this);
        yearInput.setFont(new Font("Arial", Font.BOLD, 22));
        //yearInput.setEditable(false);
        //titleInput.setBackground(new Color(152,251,152));
        p4.add(yearInput);
        p4.add(Box.createRigidArea(new Dimension(15,0)));
        p4.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p5 = new JPanel();
        p5.setOpaque(false);
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        p5.add(cancel);
        p5.add(Box.createRigidArea(new Dimension(10,0)));
        save = new JButton("Save");
        save.setAlignmentX(Component.CENTER_ALIGNMENT);
        save.addActionListener(this);
        save.setFont(new Font("Arial", Font.PLAIN, 24));
        save.setForeground(Color.white);
        save.setBackground(new Color(1,121,150));
        save.setOpaque(true);
        save.setBorderPainted(false);
        save.setEnabled(false);
        p5.add(save);
        p.add(p5);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        add(p);

        titleInput.setText(data.get(0));
        albumInput.setText(data.get(1));
        yearInput.setText(data.get(2));
        genreInput.setText(data.get(3));
        setVisible(true);
        setResizable(false);
        //setUndecorated(true);
        /*
        setUndecorated(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dc.closeSettingsWindow();
            }
        });
        setLocation(pt);
        */
        pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cancel){
            dispose();
        }
        if(e.getSource() == save){
            setTitle(titleInput.getText());
            setAlbum(albumInput.getText());
            setYear(yearInput.getText());
            String genre = genreChoices.getSelectedItem().toString();
            setArtist(artistInput.getText());
            choice = true;
            controller.updateSong(selectedRow, getTitle(), getAlbum(), getArtist(), getYear(), getGenre()/*, getDateUploaded()*/);
            dispose();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        if (titleInput.getText().isEmpty() || albumInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        if (titleInput.getText().isEmpty() || albumInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        if (titleInput.getText().isEmpty() || albumInput.getText().isEmpty())
            save.setEnabled(false);
        else
            save.setEnabled(true);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isEdited(){
        return choice;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public static void main(String[] args){
//        EditSongProfileWindow espw = new EditSongProfileWindow();
    }
}
