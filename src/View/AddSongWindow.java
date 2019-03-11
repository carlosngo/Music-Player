package View;

import Controller.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddSongWindow extends JFrame implements ActionListener, DocumentListener {
    SongController controller;
    JTextField songTitleInput, genreInput, artistInput, yearInput;
    JButton selectFile, cancel, saveSong;
    JPanel fileReaderPnl;
    JLabel selectedFileName;
    File selectedFile;

    public AddSongWindow(SongController controller){
        this.controller = controller;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));
        p.add(Box.createRigidArea(new Dimension(0,10)));

        JPanel titlePnl = new JPanel();
        titlePnl.setLayout(new BoxLayout(titlePnl, BoxLayout.Y_AXIS));
        titlePnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePnl.setOpaque(false);
        JLabel title = new JLabel("Add Song");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.white);
        titlePnl.add(title);
        p.add(titlePnl);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        p1.setOpaque(false);
        JLabel songTitleLabel = new JLabel("Title: ");
        songTitleLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        songTitleLabel.setForeground(Color.white);
        p1.add(songTitleLabel);
        p1.add(Box.createRigidArea(new Dimension(5,0)));
        songTitleInput = new JTextField("",15);
        songTitleInput.addActionListener(this);
        songTitleInput.getDocument().addDocumentListener(this);
        songTitleInput.setFont(new Font("Arial", Font.PLAIN, 22));
        p1.add(songTitleInput);
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel genreLabel = new JLabel("Genre: ");
        genreLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        genreLabel.setForeground(Color.white);
        p2.add(genreLabel);
        genreInput = new JTextField("" , 15);
        genreInput.addActionListener(this);
        genreInput.getDocument().addDocumentListener(this);
        genreInput.setFont(new Font("Arial", Font.PLAIN, 22));
        p2.add(genreInput);
        p2.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        p3.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel artistLabel = new JLabel("Artist: ");
        artistLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        artistLabel.setForeground(Color.white);
        p3.add(artistLabel);
        p3.add(Box.createRigidArea(new Dimension(5,0)));
        artistInput = new JTextField("" , 15);
        artistInput.addActionListener(this);
        artistInput.getDocument().addDocumentListener(this);
        artistInput.setFont(new Font("Arial", Font.PLAIN, 22));
        p3.add(artistInput);
        p3.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        p4.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        yearLabel.setForeground(Color.white);
        p4.add(yearLabel);
        p4.add(Box.createRigidArea(new Dimension(5,0)));
        yearInput = new JTextField("" , 15);
        yearInput.addActionListener(this);
        yearInput.getDocument().addDocumentListener(this);
        yearInput.setFont(new Font("Arial", Font.PLAIN, 22));
        p4.add(yearInput);
        p4.add(Box.createRigidArea(new Dimension(15,0)));
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        fileReaderPnl = new JPanel();
        fileReaderPnl.setLayout(new BoxLayout(fileReaderPnl, BoxLayout.X_AXIS));
        //fileReaderPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileReaderPnl.setOpaque(false);
        selectFile = new JButton("Select File");
        selectFile.setFont(new Font("Arial", Font.PLAIN, 24));
        selectFile.setForeground(Color.white);
        selectFile.setBackground(new Color(1,121,150));
        selectFile.setOpaque(true);
        selectFile.setBorderPainted(false);
        selectFile.addActionListener(this);
        selectFile.setEnabled(false);
        fileReaderPnl.add(selectFile);
        selectedFileName = new JLabel();
        selectedFileName.setFont(new Font("Arial", Font.PLAIN, 22));
        selectedFileName.setForeground(Color.white);
        fileReaderPnl.add(Box.createRigidArea(new Dimension(5,0)));
        fileReaderPnl.add(selectedFileName);
        p.add(fileReaderPnl);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.setOpaque(false);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        p5.add(cancel);
        p5.add(Box.createRigidArea(new Dimension(10,0)));
        saveSong = new JButton("Add Song");
        saveSong.setFont(new Font("Arial", Font.PLAIN, 22));
        saveSong.setForeground(Color.white);
        saveSong.setBackground(new Color(1,121,150));
        saveSong.setOpaque(true);
        saveSong.setBorderPainted(false);
        saveSong.addActionListener(this);
        saveSong.setEnabled(false);
        p5.add(saveSong);
        p5.add(Box.createRigidArea(new Dimension(0,7)));
        p.add(p5);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectFile){
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Import Song");
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                    "MP3 File", "mp3"));
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filename = file.getName();
                String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
                selectedFile = file;
                //read song file here

                if(filename.length() > 10)
                    selectedFileName.setText("" + filename.substring(0,10) + "...");
                else
                    selectedFileName.setText(""+filename);
                saveSong.setEnabled(true);
            }
            else
                JOptionPane.showMessageDialog(null,"Invalid file","Error loading file", JOptionPane.INFORMATION_MESSAGE);

        }
        if(e.getSource() == saveSong){
            String songTitle = songTitleInput.getText();
            String genre = genreInput.getText();
            String artist= artistInput.getText();
            String year = yearInput.getText();

            //update database with info
            //title = songTitle
            //genre = genre
            //artist = artist
            // year = year
//            System.out.println("title:" + songTitle);
//            System.out.println("genre:" + genre);
//            System.out.println("artist:" + artist);
//            System.out.println("year:" + year);
            controller.addSong(songTitle, genre, artist, year, selectedFile);
            dispose();
        }
        if(e.getSource() == cancel){
            dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (songTitleInput.getText().isEmpty())
            selectFile.setEnabled(false);
        else
            selectFile.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (songTitleInput.getText().isEmpty())
            selectFile.setEnabled(false);
        else
            selectFile.setEnabled(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (songTitleInput.getText().isEmpty())
            selectFile.setEnabled(false);
        else
            selectFile.setEnabled(true);
    }

//    public static void main(String[] args){
//        AddSongWindow asw = new AddSongWindow();
//    }
}
