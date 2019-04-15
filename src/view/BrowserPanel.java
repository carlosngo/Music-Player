package view;

import controller.SongController;
import model.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowserPanel extends JPanel implements ActionListener, DocumentListener {

    private InfoPanel infoPnl, newInfoPnl;
    private JTextField input;
    private JButton search;
    private JLabel searchResultsTitle;
    private JPanel card;
    private SongController controller;
    //private CategoryPanel userPnl, newUserPnl;

    //isInputted is true if the browser page is used as a search results page
    public BrowserPanel(SongController sc, User user, boolean isInputted){
        controller = sc;
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        infoPnl = new InfoPanel(sc, user);

        JPanel searchBarPnl = new JPanel();
        searchBarPnl.setLayout(new BoxLayout(searchBarPnl, BoxLayout.X_AXIS));
        searchBarPnl.setOpaque(false);
        input = new JTextField("" , 10);
        input.addActionListener(this);
        input.getDocument().addDocumentListener(this);
        input.setFont(new Font("Arial", Font.BOLD, 22));
        searchBarPnl.add(input);
        search = new JButton("Search");
        search.setFont(new Font("Arial", Font.BOLD, 22));
        search.addActionListener(this);
        search.setForeground(Color.white);
        search.setBackground(new Color(1,121,150));
        search.setOpaque(true);
        search.setBorderPainted(false);
        search.setEnabled(false);
        searchBarPnl.add(search);
        add(searchBarPnl);
        add(Box.createRigidArea(new Dimension(0,7)));
        searchResultsTitle = new JLabel("Search Results");
        searchResultsTitle.setForeground(Color.white);
        searchResultsTitle.setFont(new Font("Arial", Font.BOLD, 22));
        searchResultsTitle.setVisible(false);
        add(searchResultsTitle);

        card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        //card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setOpaque(false);
        card.add(infoPnl);
        add(card);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == search){
            searchResultsTitle.setVisible(true);
            card.removeAll();
            newInfoPnl = new InfoPanel(controller, input.getText());
            card.add(newInfoPnl);
            revalidate();
            repaint();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        if (input.getText().isEmpty())
            search.setEnabled(false);
        else
            search.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        if (input.getText().isEmpty())
            search.setEnabled(false);
        else
            search.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        if (input.getText().isEmpty())
            search.setEnabled(false);
        else
            search.setEnabled(true);
    }

//    //isInputted is true if the browser page is used as a search results page
//    public BrowserPanel(SongController sc, String songPnlHeader, String playlistPnlHeader, String albumPnlHeader, String artistPnlHeader,
//                        ArrayList<ArrayList<String>> songsData, ArrayList<String> playlists, ArrayList<String> albums,
//                        ArrayList<String> artists, ArrayList<String> users, boolean isInputted){
//
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        //setAlignmentX(Component.LEFT_ALIGNMENT);
//        setOpaque(false);
//
//        infoPnl = new InfoPanel(sc, songPnlHeader, playlistPnlHeader, albumPnlHeader, artistPnlHeader, songsData, playlists,
//                albums, artists);
//        userPnlHeader = "Users";
//        userPnl = new UserArtistListPanel(sc, userPnlHeader, users);
//
//        JPanel searchBarPnl = new JPanel();
//        searchBarPnl.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        searchBarPnl.setOpaque(false);
//        input = new JTextField("" , 10);
//        input.addActionListener(this);
//        input.getDocument().addDocumentListener(this);
//        input.setFont(new Font("Arial", Font.BOLD, 22));
//        searchBarPnl.add(input);
//        search = new JButton("Search");
//        search.setFont(new Font("Arial", Font.BOLD, 22));
//        search.addActionListener(this);
//        search.setForeground(Color.white);
//        search.setBackground(new Color(1,121,150));
//        search.setOpaque(true);
//        search.setBorderPainted(false);
//        search.setEnabled(false);
//        searchBarPnl.add(search);
//        add(searchBarPnl);
//        add(Box.createRigidArea(new Dimension(0,7)));
//        searchResultsTitle = new JLabel("Search Results");
//        searchResultsTitle.setForeground(Color.white);
//        searchResultsTitle.setFont(new Font("Arial", Font.BOLD, 22));
//        searchResultsTitle.setVisible(false);
//        add(searchResultsTitle);
//
//        card = new JPanel();
//        card.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        //card.setAlignmentX(Component.LEFT_ALIGNMENT);
//        card.setOpaque(false);
//        card.add(infoPnl);
//        card.add(userPnl);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if (e.getSource() == search){
//            //use input.getText() to get search results
//            searchResultsTitle.setVisible(true);
//            card.removeAll();
////            newInfoPnl = new InfoPanel(songPnlHeader, playlistPnlHeader, albumPnlHeader, artistPnlHeader, songsData, playlists,
////                    albums, artists);
////            newUserPnl = new UserArtistListPanel(userPnlHeader, users);
//            card.add(newInfoPnl);
//            card.add(newUserPnl);
//            revalidate();
//            repaint();
//        }
//    }
//
//    public void insertUpdate(DocumentEvent e) {
//        if (input.getText().isEmpty())
//            search.setEnabled(false);
//        else
//            search.setEnabled(true);
//    }
//
//    public void removeUpdate(DocumentEvent e) {
//        if (input.getText().isEmpty())
//            search.setEnabled(false);
//        else
//            search.setEnabled(true);
//    }
//
//    public void changedUpdate(DocumentEvent e) {
//        if (input.getText().isEmpty())
//            search.setEnabled(false);
//        else
//            search.setEnabled(true);
//    }
}
