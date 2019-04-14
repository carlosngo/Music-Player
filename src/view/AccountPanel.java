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
    private JButton addSong, addAlbum, logIn, signUp, editAccount, logOut;
    private AccountController ac;

    public AccountPanel(AccountController ac, User user){
        this.ac = ac;
        setLayout(new BorderLayout());
        setOpaque(false);
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 38));
        title.setForeground(Color.white);
        try{
            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
            BufferedImage img = ImageIO.read(resource);
            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
            title.setText("iPL4YER");
        }
        catch(Exception e){}
        add(title, BorderLayout.WEST);

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
        addSong.setMaximumSize(new Dimension(200, 40));
        addSong.setMinimumSize(new Dimension(200, 40));
        addSong.setPreferredSize(new Dimension(200, 40));
        addSong.setFont(new Font("Arial", Font.BOLD, 14));
        addSong.addMouseListener(new MouseAdapter() {
            Color oldColor = addSong.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addSong.png");
                    BufferedImage img = ImageIO.read(resource);
                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addSong.setText("Add Song");
                } catch(Exception exception){

                }
                addSong.setForeground(oldColor);
            }
        });


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
        addAlbum.setMaximumSize(new Dimension(200, 40));
        addAlbum.setMinimumSize(new Dimension(200, 40));
        addAlbum.setPreferredSize(new Dimension(200, 40));
        addAlbum.setFont(new Font("Arial", Font.BOLD, 14));
        addAlbum.addMouseListener(new MouseAdapter() {
            Color oldColor = addAlbum.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAddAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addAlbum.setText("Add Playlist");
                } catch(Exception exception){

                }
                addAlbum.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/addAlbum.png");
                    BufferedImage img = ImageIO.read(resource);
                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    addAlbum.setText("Add Playlist");
                } catch(Exception exception){

                }
                addAlbum.setForeground(oldColor);
            }
        });

        JButton welcomeLbl = new JButton(ac.getUser().getUserName());
        welcomeLbl.setOpaque(false);
        welcomeLbl.setContentAreaFilled(false);
        welcomeLbl.setBorderPainted(false);
        welcomeLbl.setForeground(Color.WHITE);
//        welcomeLbl.setEnabled(false);
        welcomeLbl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ac.openEditAccountWindow();
            }
        });
        welcomeLbl.addMouseListener(new MouseAdapter() {
            Color oldColor = welcomeLbl.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanAccount.png");
                    BufferedImage img = ImageIO.read(resource);
                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    welcomeLbl.setText(ac.getUser().getUserName());
                } catch(Exception exception){

                }
                welcomeLbl.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/account.png");
                    BufferedImage img = ImageIO.read(resource);
                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    welcomeLbl.setText(ac.getUser().getUserName());
                } catch(Exception exception){

                }
                welcomeLbl.setForeground(oldColor);
            }
        });

//        welcomeLbl.setFont(new Font("Arial", Font.BOLD, 14));
        //p.add(welcomeLbl, BorderLayout.CENTER);
        //add(Box.createRigidArea(new Dimension(5,0)));

//        editAccount = new JButton("Edit Account");
//        //logOut.setForeground(new Color(65,105,225));
//        editAccount.setForeground(Color.white);
//        editAccount.setOpaque(false);
//        editAccount.setContentAreaFilled(false);
//        editAccount.setBorderPainted(false);
//        //logIn.setMaximumSize(new Dimension(90, 40));
//        //logIn.setMinimumSize(new Dimension(90, 40));
//        //logIn.setPreferredSize(new Dimension(90, 40));
//        editAccount.setFont(new Font("Arial", Font.BOLD, 14));
//        editAccount.addMouseListener(new MouseAdapter() {
//            Color oldColor = editAccount.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                editAccount.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                editAccount.setForeground(oldColor);
//            }
//        });
//        editAccount.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.openEditAccountWindow();
//            }
//        });
//        add(editAccount);
        //p.add(addSongs, BorderLayout.WEST);

        logOut = new JButton("Log Out");
        //logOut.setForeground(new Color(65,105,225));
        logOut.setForeground(Color.white);
        logOut.setOpaque(false);
        logOut.setContentAreaFilled(false);
        logOut.setBorderPainted(false);
        //logIn.setMaximumSize(new Dimension(90, 40));
        //logIn.setMinimumSize(new Dimension(90, 40));
        //logIn.setPreferredSize(new Dimension(90, 40));
        logOut.setFont(new Font("Arial", Font.BOLD, 14));
        logOut.addMouseListener(new MouseAdapter() {
            Color oldColor = logOut.getForeground();
            public void mouseEntered(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/cyanLogOut.png");
                    BufferedImage img = ImageIO.read(resource);
                    logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    logOut.setText("Log Out");
                } catch(Exception exception){

                }
                logOut.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                try{
                    URL resource = getClass().getClassLoader().getResource("images/logOut.png");
                    BufferedImage img = ImageIO.read(resource);
                    logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    logOut.setText("Log Out");
                } catch(Exception exception){

                }
                logOut.setForeground(oldColor);
            }
        });
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ac.logOut();
            }
        });
//        add(logOut);
        //p.add(logOut, BorderLayout.EAST);

        try{
            URL resource = getClass().getClassLoader().getResource("images/account.png");
            BufferedImage img = ImageIO.read(resource);
            welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            resource = getClass().getClassLoader().getResource("images/addSong.png");
            img = ImageIO.read(resource);
            addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            resource = getClass().getClassLoader().getResource("images/addAlbum.png");
            img = ImageIO.read(resource);
            addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
            resource = getClass().getClassLoader().getResource("images/logOut.png");
            img = ImageIO.read(resource);
            logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
        }
        catch(Exception e){}

        JPanel buttonsPnl = new JPanel();
        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.X_AXIS));
        buttonsPnl.setOpaque(false);
        if(user instanceof Artist){
            buttonsPnl.add(addSong);
            buttonsPnl.add(addAlbum);
        }

        buttonsPnl.add(welcomeLbl);
        buttonsPnl.add(logOut);
        p.add(buttonsPnl, BorderLayout.EAST);

        add(p, BorderLayout.EAST);
    }


    public void update() {
        revalidate();
        repaint();

    }

//    public AccountPanel(AccountController ac, User user){
//        this.ac = ac;
//        setLayout(new BorderLayout());
//        setOpaque(false);
//        JPanel p = new JPanel(new BorderLayout());
//        p.setOpaque(false);
//        JLabel title = new JLabel();
//        title.setFont(new Font("Arial", Font.PLAIN, 38));
//        title.setForeground(Color.white);
//        try{
//            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
//            BufferedImage img = ImageIO.read(resource);
//            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
//            title.setText("iPL4YER");
//        }
//        catch(Exception e){}
//        add(title, BorderLayout.WEST);
//
//        addSong = new JButton();
//        addSong.setActionCommand("Add Song");
//        addSong.setForeground(Color.white);
//        addSong.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.getMc().getSongController().openAddSongWindow();
//            }
//        });
//        addSong.setOpaque(false);
//        addSong.setContentAreaFilled(false);
//        addSong.setBorderPainted(false);
//        addSong.setMaximumSize(new Dimension(200, 40));
//        addSong.setMinimumSize(new Dimension(200, 40));
//        addSong.setPreferredSize(new Dimension(200, 40));
//        addSong.setFont(new Font("Arial", Font.BOLD, 14));
//        addSong.addMouseListener(new MouseAdapter() {
//            Color oldColor = addSong.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/cyanAddSong.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    addSong.setText("Add Song");
//                } catch(Exception exception){
//
//                }
//                addSong.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/addSong.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    addSong.setText("Add Song");
//                } catch(Exception exception){
//
//                }
//                addSong.setForeground(oldColor);
//            }
//        });
//
//
//        addAlbum = new JButton();
//        addAlbum.setActionCommand("Add Album");
//        addAlbum.setForeground(Color.white);
//        addAlbum.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.getMc().getSongController().openAddAlbumWindow();
//            }
//        });
//        addAlbum.setOpaque(false);
//        addAlbum.setContentAreaFilled(false);
//        addAlbum.setBorderPainted(false);
//        addAlbum.setMaximumSize(new Dimension(200, 40));
//        addAlbum.setMinimumSize(new Dimension(200, 40));
//        addAlbum.setPreferredSize(new Dimension(200, 40));
//        addAlbum.setFont(new Font("Arial", Font.BOLD, 14));
//        addAlbum.addMouseListener(new MouseAdapter() {
//            Color oldColor = addAlbum.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/cyanAddAlbum.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    addAlbum.setText("Add Playlist");
//                } catch(Exception exception){
//
//                }
//                addAlbum.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/addAlbum.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    addAlbum.setText("Add Playlist");
//                } catch(Exception exception){
//
//                }
//                addAlbum.setForeground(oldColor);
//            }
//        });
//
//        JButton welcomeLbl = new JButton(ac.getUser().getUserName());
//        welcomeLbl.setOpaque(false);
//        welcomeLbl.setContentAreaFilled(false);
//        welcomeLbl.setBorderPainted(false);
//        welcomeLbl.setForeground(Color.WHITE);
////        welcomeLbl.setEnabled(false);
//        welcomeLbl.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.openEditAccountWindow();
//            }
//        });
//        welcomeLbl.addMouseListener(new MouseAdapter() {
//            Color oldColor = welcomeLbl.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/cyanAccount.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    welcomeLbl.setText(ac.getUser().getUserName());
//                } catch(Exception exception){
//
//                }
//                welcomeLbl.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/account.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    welcomeLbl.setText(ac.getUser().getUserName());
//                } catch(Exception exception){
//
//                }
//                welcomeLbl.setForeground(oldColor);
//            }
//        });
//
////        welcomeLbl.setFont(new Font("Arial", Font.BOLD, 14));
//        //p.add(welcomeLbl, BorderLayout.CENTER);
//        //add(Box.createRigidArea(new Dimension(5,0)));
//
////        editAccount = new JButton("Edit Account");
////        //logOut.setForeground(new Color(65,105,225));
////        editAccount.setForeground(Color.white);
////        editAccount.setOpaque(false);
////        editAccount.setContentAreaFilled(false);
////        editAccount.setBorderPainted(false);
////        //logIn.setMaximumSize(new Dimension(90, 40));
////        //logIn.setMinimumSize(new Dimension(90, 40));
////        //logIn.setPreferredSize(new Dimension(90, 40));
////        editAccount.setFont(new Font("Arial", Font.BOLD, 14));
////        editAccount.addMouseListener(new MouseAdapter() {
////            Color oldColor = editAccount.getForeground();
////            public void mouseEntered(MouseEvent e) {
////                editAccount.setForeground(new Color(0,255,255));
////            }
////            public void mouseExited(MouseEvent e) {
////                editAccount.setForeground(oldColor);
////            }
////        });
////        editAccount.addActionListener(new ActionListener() {
////            @Override
////            public void actionPerformed(ActionEvent e) {
////                ac.openEditAccountWindow();
////            }
////        });
////        add(editAccount);
//        //p.add(addSongs, BorderLayout.WEST);
//
//        logOut = new JButton("Log Out");
//        //logOut.setForeground(new Color(65,105,225));
//        logOut.setForeground(Color.white);
//        logOut.setOpaque(false);
//        logOut.setContentAreaFilled(false);
//        logOut.setBorderPainted(false);
//        //logIn.setMaximumSize(new Dimension(90, 40));
//        //logIn.setMinimumSize(new Dimension(90, 40));
//        //logIn.setPreferredSize(new Dimension(90, 40));
//        logOut.setFont(new Font("Arial", Font.BOLD, 14));
//        logOut.addMouseListener(new MouseAdapter() {
//            Color oldColor = logOut.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/cyanLogOut.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    logOut.setText("Log Out");
//                } catch(Exception exception){
//
//                }
//                logOut.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                try{
//                    URL resource = getClass().getClassLoader().getResource("images/logOut.png");
//                    BufferedImage img = ImageIO.read(resource);
//                    logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                    logOut.setText("Log Out");
//                } catch(Exception exception){
//
//                }
//                logOut.setForeground(oldColor);
//            }
//        });
//        logOut.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.logOut();
//            }
//        });
////        add(logOut);
//        //p.add(logOut, BorderLayout.EAST);
//
//        try{
//            URL resource = getClass().getClassLoader().getResource("images/account.png");
//            BufferedImage img = ImageIO.read(resource);
//            welcomeLbl.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//            resource = getClass().getClassLoader().getResource("images/addSong.png");
//            img = ImageIO.read(resource);
//            addSong.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//            resource = getClass().getClassLoader().getResource("images/addAlbum.png");
//            img = ImageIO.read(resource);
//            addAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//            resource = getClass().getClassLoader().getResource("images/logOut.png");
//            img = ImageIO.read(resource);
//            logOut.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 25, 25)));
//        }
//        catch(Exception e){}
//
//        JPanel buttonsPnl = new JPanel();
//        buttonsPnl.setLayout(new BoxLayout(buttonsPnl, BoxLayout.X_AXIS));
//        buttonsPnl.setOpaque(false);
//        if(user instanceof Artist){
//            buttonsPnl.add(addSong);
//            buttonsPnl.add(addAlbum);
//        }
//
//        buttonsPnl.add(welcomeLbl);
//        buttonsPnl.add(logOut);
//        p.add(buttonsPnl, BorderLayout.EAST);
//
//        add(p, BorderLayout.EAST);
//    }





//    public AccountPanel(AccountController ac){
//        this.ac = ac;
//        setLayout(new BorderLayout());
////        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//        setOpaque(false);
////        add(Box.createRigidArea(new Dimension(15,0)));
//        JLabel title = new JLabel();
//        title.setFont(new Font("Arial", Font.PLAIN, 38));
//        title.setForeground(Color.white);
////        add(title);
//        try{
//            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
//            BufferedImage img = ImageIO.read(resource);
//            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
//            title.setText("iPL4YER");
//        }
//        catch(Exception e){}
//
//        add(title, BorderLayout.WEST);
////        add(Box.createRigidArea(new Dimension(220,0)));
//        JPanel p = new JPanel(new BorderLayout());
//        p.setOpaque(false);
//        addSongs = new JButton("Add Songs");
//        //addSongs.setForeground(new Color(65,105,225));
////        Border b = BorderFactory.createLineBorder(Color.WHITE, 2, true);
////        b.getBorderInsets().set(0, 5, 0, 5);
////        addSongs.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
//
//        addSongs.setForeground(Color.white);
//        addSongs.setOpaque(false);
//        addSongs.setContentAreaFilled(false);
//        addSongs.setBorderPainted(false);
////        addSongs.setMaximumSize(new Dimension(120, 40));
////        addSongs.setMinimumSize(new Dimension(120, 40));
////        addSongs.setPreferredSize(new Dimension(120, 40));
//        addSongs.setFont(new Font("Arial", Font.BOLD, 14));
//        addSongs.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.openAddSongWindow();
//            }
//        });
//        addSongs.addMouseListener(new MouseAdapter() {
//            Color oldColor = addSongs.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                addSongs.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                addSongs.setForeground(oldColor);
//            }
//        });
////        add(addSongs);
//        //p.add(addSongs, BorderLayout.EAST);
//        //add(Box.createRigidArea(new Dimension(5,0)));
//
//        logIn = new JButton("Log In");
//        //logIn.setForeground(new Color(65,105,225));
//        logIn.setForeground(Color.white);
//        logIn.setOpaque(false);
//        logIn.setContentAreaFilled(false);
//        logIn.setBorderPainted(false);
//        //logIn.setMaximumSize(new Dimension(90, 40));
//        //logIn.setMinimumSize(new Dimension(90, 40));
//        //logIn.setPreferredSize(new Dimension(90, 40));
//        logIn.setFont(new Font("Arial", Font.BOLD, 14));
//        logIn.addMouseListener(new MouseAdapter() {
//            Color oldColor = logIn.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                logIn.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                logIn.setForeground(oldColor);
//            }
//        });
//        logIn.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.openLogInWindow();
//            }
//        });
////        add(logIn);
//        p.add(logIn, BorderLayout.CENTER);
//        //add(Box.createRigidArea(new Dimension(5,0)));
//
//        signUp = new JButton("Sign Up");
//        //signUp.setForeground(new Color(65,105,225));
//        signUp.setForeground(Color.white);
//        signUp.setOpaque(false);
//        signUp.setContentAreaFilled(false);
//        signUp.setBorderPainted(false);
////        signUp.setMaximumSize(new Dimension(100, 40));
////        signUp.setMinimumSize(new Dimension(100, 40));
////        signUp.setPreferredSize(new Dimension(100, 40));
//        signUp.setFont(new Font("Arial", Font.BOLD, 14));
//        signUp.addMouseListener(new MouseAdapter() {
//            Color oldColor = signUp.getForeground();
//            public void mouseEntered(MouseEvent e) {
//                signUp.setForeground(new Color(0,255,255));
//            }
//            public void mouseExited(MouseEvent e) {
//                signUp.setForeground(oldColor);
//            }
//        });
//        signUp.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ac.openCreateAccountWindow();
//            }
//        });
////        add(Box.createRigidArea(new Dimension(15,0)));
////        add(signUp);
//        p.add(signUp, BorderLayout.WEST);
//        add(p, BorderLayout.EAST);
//        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//    }
}
