package view;

import controller.SongController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddAlbumWindow extends JFrame implements ActionListener, DocumentListener {
    private SongController controller;
    private JTextField nameInput;
    private JButton save, cancel, selectFile;
    private JPanel fileReaderPnl;
    private JLabel selectedFileName;
    private File selectedFile;

    public AddAlbumWindow(SongController controller) {
        this.controller = controller;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0, 0, 0));

        p.add(Box.createRigidArea(new Dimension(0, 15))); // add space
        JLabel title = new JLabel("Add Album");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel inputPnl = new JPanel();
        inputPnl.setLayout(new BoxLayout(inputPnl, BoxLayout.X_AXIS));
        inputPnl.add(Box.createRigidArea(new Dimension(15, 0)));
        inputPnl.setOpaque(false);
        JLabel inputLabel = new JLabel("Album Name: ");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        inputLabel.setForeground(Color.white);
        inputPnl.add(inputLabel);
        inputPnl.add(Box.createRigidArea(new Dimension(5, 0)));
        nameInput = new JTextField("", 10);
        nameInput.addActionListener(this);
        nameInput.getDocument().addDocumentListener(this);
        nameInput.setFont(new Font("Arial", Font.PLAIN, 22));
        inputPnl.add(nameInput);
        inputPnl.add(Box.createRigidArea(new Dimension(15, 0)));
        p.add(inputPnl);
        p.add(Box.createRigidArea(new Dimension(0, 7)));

        fileReaderPnl = new JPanel();
        fileReaderPnl.setLayout(new BoxLayout(fileReaderPnl, BoxLayout.X_AXIS));
        //fileReaderPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileReaderPnl.setOpaque(false);
        selectFile = new JButton("Select Cover Photo");
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

        JPanel buttonsPnl = new JPanel();
        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.X_AXIS));
        buttonsPnl.setOpaque(false);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1, 121, 150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        buttonsPnl.add(cancel);
        buttonsPnl.add(Box.createRigidArea(new Dimension(10, 0)));
        save = new JButton("Save");
        save.setFont(new Font("Arial", Font.PLAIN, 22));
        save.setForeground(Color.white);
        save.setBackground(new Color(1, 121, 150));
        save.setOpaque(true);
        save.setBorderPainted(false);
        save.addActionListener(this);
        save.setEnabled(false);
        buttonsPnl.add(save);
        buttonsPnl.add(Box.createRigidArea(new Dimension(0, 7)));
        p.add(buttonsPnl);
        p.add(Box.createRigidArea(new Dimension(0, 7)));

        add(p);
        pack();
        setVisible(true);
        setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        //setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectFile){
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Choose Cover Photo");
//            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
//                    "MP3 File", "mp3"));
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                String filename = file.getName();
                String extension = filename.substring(filename.lastIndexOf('.'), filename.length());
                selectedFile = file;
                if(filename.length() > 10)
                    selectedFileName.setText("" + filename.substring(0,10) + "...");
                else
                    selectedFileName.setText(""+filename);
            }
            else
                JOptionPane.showMessageDialog(null,"Invalid file","Error loading file", JOptionPane.INFORMATION_MESSAGE);

        }
        if (e.getSource() == cancel) {
            dispose();
        }
        if (e.getSource() == save) {
            if(selectedFile != null) controller.addAlbum(nameInput.getText(), selectedFile);
            else controller.addAlbum(nameInput.getText());
            dispose();
        }
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
}