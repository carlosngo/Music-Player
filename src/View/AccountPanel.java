package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import Model.*;

//icon, and header buttons
public class AccountPanel extends JPanel implements ActionListener {
    private JButton addSongs, logIn, signUp, viewAccount, logOut;
    private MainScreen ms;

    public AccountPanel(MainScreen ms){
        this.ms = ms;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));
        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 38));
        title.setForeground(Color.white);
        add(title);
        try{
            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
            File img = Paths.get(resource.toURI()).toFile();
            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
            title.setText("iPL4YER");
        }
        catch(Exception e){}

        add(Box.createRigidArea(new Dimension(220,0)));
        addSongs = new JButton("Add Songs");
        //addSongs.setForeground(new Color(65,105,225));
        addSongs.setForeground(Color.white);
        addSongs.setOpaque(false);
        addSongs.setContentAreaFilled(false);
        addSongs.setBorderPainted(false);
//        addSongs.setMaximumSize(new Dimension(120, 40));
//        addSongs.setMinimumSize(new Dimension(120, 40));
//        addSongs.setPreferredSize(new Dimension(120, 40));
        addSongs.setFont(new Font("Arial", Font.BOLD, 14));
        addSongs.addActionListener(this);
        addSongs.addMouseListener(new MouseAdapter() {
            Color oldColor = addSongs.getForeground();
            public void mouseEntered(MouseEvent e) {
                addSongs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                addSongs.setForeground(oldColor);
            }
        });
        add(addSongs);
        //add(Box.createRigidArea(new Dimension(5,0)));

        logIn = new JButton("Log In");
        //logIn.setForeground(new Color(65,105,225));
        logIn.setForeground(Color.white);
        logIn.setOpaque(false);
        logIn.setContentAreaFilled(false);
        logIn.setBorderPainted(false);
        //logIn.setMaximumSize(new Dimension(90, 40));
        //logIn.setMinimumSize(new Dimension(90, 40));
        //logIn.setPreferredSize(new Dimension(90, 40));
        logIn.setFont(new Font("Arial", Font.BOLD, 14));
        logIn.addMouseListener(new MouseAdapter() {
            Color oldColor = logIn.getForeground();
            public void mouseEntered(MouseEvent e) {
                logIn.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                logIn.setForeground(oldColor);
            }
        });
        logIn.addActionListener(this);
        add(logIn);
        //add(Box.createRigidArea(new Dimension(5,0)));

        signUp = new JButton("Sign Up");
        //signUp.setForeground(new Color(65,105,225));
        signUp.setForeground(Color.white);
        signUp.setOpaque(false);
        signUp.setContentAreaFilled(false);
        signUp.setBorderPainted(false);
//        signUp.setMaximumSize(new Dimension(100, 40));
//        signUp.setMinimumSize(new Dimension(100, 40));
//        signUp.setPreferredSize(new Dimension(100, 40));
        signUp.setFont(new Font("Arial", Font.BOLD, 14));
        signUp.addMouseListener(new MouseAdapter() {
            Color oldColor = signUp.getForeground();
            public void mouseEntered(MouseEvent e) {
                signUp.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                signUp.setForeground(oldColor);
            }
        });
        signUp.addActionListener(this);
        add(Box.createRigidArea(new Dimension(15,0)));
        add(signUp);
    }

    public AccountPanel(MainScreen ms, User user){
        this.ms = ms;
        ms.setTitle(user.getUserName() + "'s iPl4yer");
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));
        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 38));
        title.setForeground(Color.white);
        try{
            URL resource = getClass().getClassLoader().getResource("images/imgLogoWhite.png");
            File img = Paths.get(resource.toURI()).toFile();
            title.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 55, 55)));
            title.setText("iPL4YER");
        }
        catch(Exception e){}
        add(title);

        add(Box.createRigidArea(new Dimension(220,0)));
        addSongs = new JButton("Add Songs");
        //addSongs.setForeground(new Color(65,105,225));
        addSongs.setForeground(Color.white);
        addSongs.setOpaque(false);
        addSongs.setContentAreaFilled(false);
        addSongs.setBorderPainted(false);
//        addSongs.setMaximumSize(new Dimension(120, 40));
//        addSongs.setMinimumSize(new Dimension(120, 40));
//        addSongs.setPreferredSize(new Dimension(120, 40));
        addSongs.setFont(new Font("Arial", Font.BOLD, 14));
        addSongs.addActionListener(this);
        addSongs.addMouseListener(new MouseAdapter() {
            Color oldColor = addSongs.getForeground();
            public void mouseEntered(MouseEvent e) {
                addSongs.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                addSongs.setForeground(oldColor);
            }
        });
        add(addSongs);
        //add(Box.createRigidArea(new Dimension(5,0)));

        viewAccount = new JButton("View Account");
        //logOut.setForeground(new Color(65,105,225));
        viewAccount.setForeground(Color.white);
        viewAccount.setOpaque(false);
        viewAccount.setContentAreaFilled(false);
        viewAccount.setBorderPainted(false);
        //logIn.setMaximumSize(new Dimension(90, 40));
        //logIn.setMinimumSize(new Dimension(90, 40));
        //logIn.setPreferredSize(new Dimension(90, 40));
        viewAccount.setFont(new Font("Arial", Font.BOLD, 14));
        viewAccount.addMouseListener(new MouseAdapter() {
            Color oldColor = viewAccount.getForeground();
            public void mouseEntered(MouseEvent e) {
                viewAccount.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                viewAccount.setForeground(oldColor);
            }
        });
        viewAccount.addActionListener(this);
        add(viewAccount);

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
                logOut.setForeground(new Color(0,255,255));
            }
            public void mouseExited(MouseEvent e) {
                logOut.setForeground(oldColor);
            }
        });
        logOut.addActionListener(this);
        add(logOut);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSongs){
            AddSongWindow asw = new AddSongWindow();
        }
        if(e.getSource() == logIn){
            LogInWindow l = new LogInWindow();
        }
        if(e.getSource() == signUp){
            CreateAccountWindow caw = new CreateAccountWindow();
        }
    }
}
