package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

//icon, and header buttons
public class AccountPanel extends JPanel implements ActionListener {
    private JButton addSongs, logIn, signUp;

    public AccountPanel(){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
        add(Box.createRigidArea(new Dimension(15,0)));
        JLabel title = new JLabel();
        title.setFont(new Font("Arial", Font.PLAIN, 45));
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
        addSongs.setForeground(new Color(65,105,225));
        addSongs.setOpaque(false);
        addSongs.setContentAreaFilled(false);
        addSongs.setBorderPainted(false);
        addSongs.setMaximumSize(new Dimension(100, 40));
        addSongs.setMinimumSize(new Dimension(100, 40));
        addSongs.setPreferredSize(new Dimension(100, 40));
        addSongs.setFont(new Font("Arial", Font.BOLD, 14));
        addSongs.addActionListener(this);
        add(addSongs);
        add(Box.createRigidArea(new Dimension(5,0)));

        logIn = new JButton("Log In");
        logIn.setForeground(new Color(65,105,225));
        logIn.setOpaque(false);
        logIn.setContentAreaFilled(false);
        logIn.setBorderPainted(false);
        logIn.setMaximumSize(new Dimension(100, 40));
        logIn.setMinimumSize(new Dimension(100, 40));
        logIn.setPreferredSize(new Dimension(100, 40));
        logIn.setFont(new Font("Arial", Font.BOLD, 14));
        logIn.addActionListener(this);
        add(logIn);
        add(Box.createRigidArea(new Dimension(5,0)));

        signUp = new JButton("Sign Up");
        signUp.setForeground(new Color(65,105,225));
        signUp.setOpaque(false);
        signUp.setContentAreaFilled(false);
        signUp.setBorderPainted(false);
        signUp.setMaximumSize(new Dimension(100, 40));
        signUp.setMinimumSize(new Dimension(100, 40));
        signUp.setPreferredSize(new Dimension(100, 40));
        signUp.setFont(new Font("Arial", Font.BOLD, 14));
        signUp.addActionListener(this);
        add(Box.createRigidArea(new Dimension(15,0)));
        add(signUp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addSongs){
            //add songs
        }
        if(e.getSource() == logIn){
            LogInWindow l = new LogInWindow();
        }
        if(e.getSource() == signUp){
            CreateAccountWindow caw = new CreateAccountWindow();
        }
    }
}
