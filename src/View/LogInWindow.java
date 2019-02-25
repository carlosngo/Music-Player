package View;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class LogInWindow extends JFrame implements ActionListener, DocumentListener {
    //Controller controller;
    private JTextField usernameInput, passwordInput;
    private JButton logIn, cancel;

    public LogInWindow(/*Controller controller*/) {
        //this.controller = controller;
        initHomeScreen();
    }

    public void initHomeScreen() {
        //JPanel motherPnl = new JPanel();
        //motherPnl.setLayout(new OverlayLayout(motherPnl));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        //p.setBackground(new Color(0,78,56));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Log In");
        //title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Abril Fatface", Font.BOLD, 30));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,20))); // add space

        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        //subP.setLayout(new FlowLayout());
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel nameLabel = new JLabel("Username: ");
        //nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(nameLabel);
        //p1.add(Box.createRigidArea(new Dimension(5,0))); // add space
        usernameInput = new JTextField("" , 15);
        usernameInput.addActionListener(this);
        usernameInput.getDocument().addDocumentListener(this);
        usernameInput.setFont(new Font("Arial", Font.BOLD, 22));
        //usernameInput.setBackground(new Color(152,251,152));
        p1.add(usernameInput);
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        //subP2.setLayout(new FlowLayout());
        p.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel pwLabel = new JLabel("Password: ");
        //pwLabel.setForeground(Color.WHITE);
        pwLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p2.add(pwLabel);
        //p2.add(Box.createRigidArea(new Dimension(5,0))); // add space
        passwordInput = new JTextField("" , 15);
        passwordInput.addActionListener(this);
        passwordInput.getDocument().addDocumentListener(this);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
       //passwordInput.setBackground(new Color(152,251,152));
        p2.add(passwordInput);
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        //subP3.setLayout(new FlowLayout());
        cancel = new JButton("Cancel");
        //cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        //cancel.setForeground(Color.white);
        //cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        //cancel.setBorderPainted(false);
        p3.add(cancel);
        p3.add(Box.createRigidArea(new Dimension(10,0))); // add space
        logIn = new JButton("Log In");
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.addActionListener(this);
        logIn.setFont(new Font("Arial", Font.BOLD, 24));
        //logIn.setForeground(Color.white);
        //logIn.setBackground(new Color(1,121,150));
        logIn.setOpaque(true);
        //logIn.setBorderPainted(false);
        logIn.setEnabled(false);
        p3.add(logIn);
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        //p.setAlignmentX(0.5f);
        //p.setAlignmentY(0.5f);
        //motherPnl.add(p);

        //add(motherPnl);
        add(p);
        pack();
        setVisible(true);
        setResizable(false);
        //setUndecorated(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void actionPerformed (ActionEvent e){
        if(e.getActionCommand().equals("Log In")){
            //usernameInput.getText() and check username
            //passwordInput.getText() and check password
            //open joption window if username is unsused
            boolean testingOnly = true;
            if(testingOnly){
                int choice = JOptionPane.showConfirmDialog(null, "This username is does not match any known accounts.\n"
                        + "Do you want to create an account?", "New Username", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    CreateAccountWindow caw = new CreateAccountWindow();
                    dispose();
                } else if (choice == JOptionPane.NO_OPTION) {
                    //open main screen (not logged in)
                    dispose();
                }
            }
            dispose();
            //open main screen (logged in)
        }
        else if(e.getActionCommand().equals("Cancel")){
            UnregisteredMainScreen ums = new UnregisteredMainScreen();
            dispose();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }


   public static void main(String[] args){
        LogInWindow l = new LogInWindow();
   }

}