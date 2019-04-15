package view;

import controller.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author User
 */
public class LogInWindow extends JFrame implements ActionListener, DocumentListener {
    AccountController controller;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JButton logIn, cancel, createAccount;

    public LogInWindow(AccountController controller) {
        this.controller = controller;
        initHomeScreen();
    }

    public void initHomeScreen() {
        //JPanel motherPnl = new JPanel();
        //motherPnl.setLayout(new OverlayLayout(motherPnl));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Log In iPl4yer");
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,20)));

        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        //subP.setLayout(new FlowLayout());
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel nameLabel = new JLabel("Username: ");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(nameLabel);
        //p1.add(Box.createRigidArea(new Dimension(5,0))); // add space
        usernameInput = new JTextField("" , 15);
        usernameInput.addActionListener(this);
        usernameInput.getDocument().addDocumentListener(this);
        usernameInput.setFont(new Font("Arial", Font.BOLD, 22));
        //usernameInput.setBackground(new Color(152,251,152));
        p1.add(usernameInput);
        p1.add(Box.createRigidArea(new Dimension(15,0)));
        p1.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        //subP2.setLayout(new FlowLayout());
        p.add(Box.createRigidArea(new Dimension(15,0)));
        JLabel pwLabel = new JLabel("Password: ");
        pwLabel.setForeground(Color.WHITE);
        pwLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p2.add(pwLabel);
        //p2.add(Box.createRigidArea(new Dimension(5,0))); // add space
        passwordInput = new JPasswordField("" , 15);
        passwordInput.addActionListener(this);
        passwordInput.getDocument().addDocumentListener(this);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
       //passwordInput.setBackground(new Color(152,251,152));
        p2.add(passwordInput);
        p2.add(Box.createRigidArea(new Dimension(15,0)));
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        cancel.setFont(new Font("Arial", Font.PLAIN, 24));
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
//        p3.add(cancel);
//        p3.add(Box.createRigidArea(new Dimension(10,0))); // add space
        createAccount = new JButton("Register");
        createAccount.addActionListener(this);
        createAccount.setFont(new Font("Arial", Font.PLAIN, 24));
        createAccount.setForeground(Color.white);
        createAccount.setBackground(new Color(1,121,150));
        createAccount.setOpaque(true);
        createAccount.setBorderPainted(false);
        p3.add(createAccount);
        p3.add(Box.createRigidArea(new Dimension(10,0)));
        logIn = new JButton("Log In");
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logIn.addActionListener(this);
        logIn.setFont(new Font("Arial", Font.BOLD, 24));
        logIn.setForeground(Color.white);
        logIn.setBackground(new Color(1,121,150));
        logIn.setOpaque(true);
        logIn.setBorderPainted(false);
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void actionPerformed (ActionEvent e){
        if(e.getActionCommand().equals("Log In")){
            String pw = new String(passwordInput.getPassword());
            boolean success = controller.logIn(usernameInput.getText(), pw);
            if (!success) {
                JOptionPane.showMessageDialog(null, "Username and password do not match.",
                        "Authentication Error", JOptionPane.ERROR_MESSAGE);
                usernameInput.setText("");
                passwordInput.setText("");
                return;
            }
            dispose();
        }
        else if(e.getActionCommand().equals("Cancel")){
            dispose();
        }
        else if(e.getActionCommand().equals("Register")){
            controller.openCreateAccountWindow();
        }
    }

    public void insertUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.equals(""))
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }

    public void removeUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.equals(""))
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }

    public void changedUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.equals(""))
            logIn.setEnabled(false);
        else
            logIn.setEnabled(true);
    }


//   public static void main(String[] args){
//        LogInWindow l = new LogInWindow();
//   }

}
