package view;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        super("iPl4yer");
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
        centerPanel.setBackground(Color.BLACK);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        southPanel = controller.getPlayerController().getPlayerPanel();
        contentPane.add(southPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);
        pack();
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.exit();
                System.out.println("Exiting program.");
                super.windowClosing(e);
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
//        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public MainController getController() {
        return controller;
    }

    public AccountPanel getAccountPanel() {
        return northPanel;
    }

    public void setAccountPanel(AccountPanel newPanel) {
        contentPane.remove(northPanel);
        contentPane.add(newPanel, BorderLayout.NORTH);
        northPanel = newPanel;
    }

    public ControlPanel getControlPanel() {
        return westPanel;
    }

    public SongPanel getSongPanel() {
        return songPanel;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return southPanel;
    }

    public void changeCard(Component panelToShow) {
        CardLayout cl = (CardLayout)(centerPanel.getLayout());
        if (panelToShow instanceof SongPanel) {
            SongPanel sp = (SongPanel)panelToShow;
            centerPanel.remove(songPanel);
            centerPanel.add(sp, SONG_PANEL);
            cl.show(centerPanel, SONG_PANEL);
        } else if (panelToShow instanceof CategoryPanel) {
            CategoryPanel cp = (CategoryPanel)panelToShow;
            centerPanel.remove(categoryPanel);
            centerPanel.add(cp, CATEGORY_PANEL);
            cl.show(centerPanel, CATEGORY_PANEL);
        }
        update();
    }
    public void update() {
        contentPane.revalidate();
        contentPane.repaint();
//        centerPanel.revalidate();
//        centerPanel.repaint();
    }

//    public static void main(String[] args){
//        Dashboard caw = new Dashboard();
//    }
}
