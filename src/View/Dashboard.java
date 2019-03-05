package View;

import Controller.*;

import javax.media.MediaLocator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Dashboard extends JFrame {
    public final static String SONG_PANEL = "Song Panel";
    public final static String CATEGORY_PANEL = "Category Panel";
    private MainController controller;
    private JPanel contentPane;
    private AccountPanel northPanel;
    private ControlPanel westPanel;
    private JPanel centerPanel;
    private SongPanel songPanel;
    private CategoryPanel categoryPanel;
    private PlayerPanel southPanel;


    public Dashboard(MainController controller){
        this.controller = controller;
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setOpaque(true);
        contentPane.setBackground(new Color(0,0,0));

        northPanel = controller.getAccountController().getAccountPanel();
        contentPane.add(northPanel, BorderLayout.NORTH);

        westPanel = new ControlPanel(controller.getSongController());
        contentPane.add(westPanel, BorderLayout.WEST);

        Border border = BorderFactory.createLineBorder(Color.white); // line for the border
        centerPanel = new JPanel();
        centerPanel.setLayout(new CardLayout());

        songPanel = controller.getSongController().getSongPanel();
        categoryPanel = controller.getSongController().getCategoryPanel();

        centerPanel.add(songPanel, SONG_PANEL);
        centerPanel.add(categoryPanel, CATEGORY_PANEL);

        southPanel = controller.getPlayerController().getPlayerPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public void update(String panelToShow) {
        CardLayout cl = (CardLayout)(centerPanel.getLayout());
        cl.show(centerPanel, panelToShow);
        contentPane.revalidate();
        contentPane.repaint();
    }

//    public static void main(String[] args){
//        Dashboard caw = new Dashboard();
//    }
}
