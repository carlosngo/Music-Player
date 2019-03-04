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

//icon, and header buttons
public class RegisteredAccountPanel extends JPanel implements ActionListener {
    private JButton addSongs, logOut;

    public RegisteredAccountPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));
        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 38));
        title.setForeground(Color.white);
        add(title);
        try{
            URL resource = getClass().getClassLoader().getResource("imgLogoWhite.png");
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
        if(e.getSource() == logOut){
            MainScreen ums = new MainScreen();
            //dispose registered screen
        }
    }
}
