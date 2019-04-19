package view;

import model.*;
import controller.*;
import util.ImageResizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

//icon, and header buttons
public class AccountPanel extends JPanel {
    private JButton addSong, addAlbum, welcomeLbl, notif, logIn, signUp, editAccount, logOut;
    private AccountController ac;
    protected JPanel block;
    private GridBagConstraints cons;
    private String name;
    private JPopupMenu settingsMenu;

    public AccountPanel(AccountController ac, User user){
        this.ac = ac;

//        cons = new GridBagConstraints();
//        cons.fill = GridBagConstraints.HORIZONTAL;
//        block = new JPanel();
//        block.setLayout(new GridBagLayout());
//        block.setOpaque(false);
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 38));
        title.setForeground(Color.white);
        title.setMaximumSize(new Dimension(250, 60));
        title.setMinimumSize(new Dimension(250, 60));
        title.setPreferredSize(new Dimension(250, 60));
        try{
            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
            BufferedImage img = ImageIO.read(resource);
            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
            title.setText("iPL4YER");
        }
        catch(Exception e){}
        add(title, BorderLayout.WEST);

        JPanel buttonPnl = new JPanel();
        buttonPnl.setLayout(new BoxLayout(buttonPnl, BoxLayout.X_AXIS));
        buttonPnl.setOpaque(false);

        notif = new JButton();
        notif.setForeground(Color.white);
        notif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component c = (Component) e.getSource();
                ac.openNotifWindow(c, c.getX(), c.getY());
                try{
                    URL resource = getClass().getClassLoader().getResource("images/notification.png");
                    BufferedImage img = ImageIO.read(resource);
                    notif.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                } catch(Exception exception){

                }
            }
        });
        notif.setOpaque(false);
        notif.setContentAreaFilled(false);
        notif.setBorderPainted(false);
//        notif.setMaximumSize(new Dimension(200, 40));
//        notif.setMinimumSize(new Dimension(200, 40));
//        notif.setPreferredSize(new Dimension(200, 40));
        notif.addMouseListener(new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/cyanNotification.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    notif.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//                } catch(Exception exception){
//
//                }
//            }
//            public void mouseExited(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/notification.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    notif.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//                } catch(Exception exception){
//
//                }
//            }
        });
        buttonPnl.add(notif);
        buttonPnl.add(Box.createRigidArea(new Dimension(7,0)));
        welcomeLbl = new JButton(ac.getUser().getAccount().getUserName());
        welcomeLbl.setOpaque(false);
        welcomeLbl.setContentAreaFilled(false);
        welcomeLbl.setBorderPainted(false);
        welcomeLbl.setForeground(Color.WHITE);
//        welcomeLbl.setMaximumSize(new Dimension(150, 40));
//        welcomeLbl.setMinimumSize(new Dimension(150, 40));
//        welcomeLbl.setPreferredSize(new Dimension(150, 40));
        name = ac.getUser().getAccount().getUserName();
        welcomeLbl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ac.openEditAccountWindow();
                PopupMenu();
                settingsMenu.show(welcomeLbl, welcomeLbl.getBounds().x - (welcomeLbl.getBounds().width),
                        welcomeLbl.getBounds().y + (welcomeLbl.getBounds().height));
            }
        });
        welcomeLbl.addMouseListener(new MouseAdapter() {
            Color oldColor = welcomeLbl.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAccount.png");
                    BufferedImage img = ImageIO.read(resource);
                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    welcomeLbl.setText(name);
                } catch(Exception exception){

                }
                welcomeLbl.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/account.png");
                    BufferedImage img = ImageIO.read(resource);
                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    welcomeLbl.setText(name);
                } catch(Exception exception){

                }
                welcomeLbl.setForeground(oldColor);
            }
        });
        buttonPnl.add(welcomeLbl);

        addSong = new JButton();
        addSong.setActionCommand("Add Song");
        addSong.setForeground(Color.white);
        addSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ac.getMc().getSongController().openAddSongWindow();
            }
        });
        addSong.setOpaque(false);
        addSong.setContentAreaFilled(false);
        addSong.setBorderPainted(false);
//        addSong.setMaximumSize(new Dimension(150, 40));
//        addSong.setMinimumSize(new Dimension(150, 40));
//        addSong.setPreferredSize(new Dimension(150, 40));
        addSong.setFont(new Font("Arial", Font.BOLD, 14));
        addSong.addMouseListener(new MouseAdapter() {
            Color oldColor = addSong.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(oldColor);
            }
        });
        if(user instanceof Artist){
            buttonPnl.add(addSong);
            buttonPnl.add(Box.createRigidArea(new Dimension(7,0)));
        }
        addAlbum = new JButton();
        addAlbum.setActionCommand("Add Album");
        addAlbum.setForeground(Color.white);
        addAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ac.getMc().getSongController().openAddAlbumWindow();
            }
        });
        addAlbum.setOpaque(false);
        addAlbum.setContentAreaFilled(false);
        addAlbum.setBorderPainted(false);
//        addAlbum.setMaximumSize(new Dimension(150, 40));
//        addAlbum.setMinimumSize(new Dimension(150, 40));
//        addAlbum.setPreferredSize(new Dimension(150, 40));
        addAlbum.setFont(new Font("Arial", Font.BOLD, 14));
        addAlbum.addMouseListener(new MouseAdapter() {
            Color oldColor = addAlbum.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    addAlbum.setText("Add Album");
                } catch(Exception exception){

                }
                addAlbum.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
                    addAlbum.setText("Add Album");
                } catch(Exception exception){

                }
                addAlbum.setForeground(oldColor);
            }
        });
        if(user instanceof Artist){
            buttonPnl.add(addAlbum);
            buttonPnl.add(Box.createRigidArea(new Dimension(7,0)));
        }

        try{
            URL resource = getClass().getClassLoader().getResource("images/account.png");
            BufferedImage img = ImageIO.read(resource);
            welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            welcomeLbl.setText(name);
            resource = getClass().getClassLoader().getResource("images/addSong.png");
            img = ImageIO.read(resource);
            addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            addSong.setText("Add Song");
            resource = getClass().getClassLoader().getResource("images/addAlbum.png");
            img = ImageIO.read(resource);
            addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            addAlbum.setText("Add Album");
            resource = getClass().getClassLoader().getResource("images/notification.png");
            img = ImageIO.read(resource);
            notif.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//            resource = getClass().getClassLoader().getResource("images/logOut.png");
//            img = ImageIO.read(resource);
//            logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//            logOut.setText("Log Out");
        }
        catch(Exception e){}
        add(buttonPnl, BorderLayout.EAST);
    }

    private void PopupMenu(){

        settingsMenu = new JPopupMenu();
        JMenuItem settings = new JMenuItem("Settings");
        settings.setActionCommand("settings");

        JMenuItem logOut = new JMenuItem("Log Out");
        logOut.setActionCommand("logOut");

        MenuItemListener menuItemListener = new MenuItemListener();

        settings.addActionListener(menuItemListener);
        logOut.addActionListener(menuItemListener);

        settingsMenu.add(settings);
        settingsMenu.add(logOut);

        try{
            URL resource = getClass().getClassLoader().getResource("images/settings.png");
            BufferedImage img = ImageIO.read(resource);
            settings.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            resource = getClass().getClassLoader().getResource("images/logOut.png");
            img = ImageIO.read(resource);
            logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
        }
        catch(Exception e){

        }

    }
    class MenuItemListener implements ActionListener {
        int choice;
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "settings":
                    ac.openEditAccountWindow();
                    break;
                case "logOut":
                    ac.getMc().getDashboard().dispose();
                    ac.logOut();
                    break;
            }
        }
    }

    public void pushNotification() {
        try{
            URL resource = getClass().getClassLoader().getResource("images/cyanHasNotification.png");
            BufferedImage img = ImageIO.read(resource);
            notif.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
        } catch(Exception exception){

        }
    }

    public void update() {
        revalidate();
        repaint();

    }
}
