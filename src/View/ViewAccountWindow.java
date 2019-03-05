package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewAccountWindow extends JFrame implements ActionListener {
    private JTextField usernameInput, passwordInput;
    private JComboBox mon, day, yr;
    private JButton back, editAccount;

    public ViewAccountWindow(){
        init();
    }

    public void init(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Account");
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,20))); // add space

        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(usernameLabel);
        usernameInput = new JTextField("" , 20);
        usernameInput.addActionListener(this);
        usernameInput.setEditable(false);
        usernameInput.setFont(new Font("Arial", Font.BOLD, 22));
        //usernameInput.setBackground(new Color(152,251,152));
        p1.add(usernameInput);
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p2.add(passwordLabel);
        passwordInput = new JTextField("" , 20);
        passwordInput.addActionListener(this);
        passwordInput.setEditable(false);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p2.add(passwordInput);
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.addActionListener(this);
        back.setForeground(Color.white);
        back.setBackground(new Color(1,121,150));
        back.setOpaque(true);
        back.setBorderPainted(false);
        p4.add(back);
        p4.add(Box.createRigidArea(new Dimension(10,0))); // add space
        editAccount = new JButton("Edit Account");
        editAccount.setFont(new Font("Arial", Font.PLAIN, 20));
        editAccount.addActionListener(this);
        editAccount.setForeground(Color.white);
        editAccount.setBackground(new Color(1,121,150));
        editAccount.setOpaque(true);
        editAccount.setBorderPainted(false);
        p4.add(editAccount);
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Edit Account")){
            EditAccountWindow eaw = new EditAccountWindow();
        }
        if (e.getActionCommand().equals("Back")){
            dispose();
        }
    }

    public static void main(String[] args){
        ViewAccountWindow vaw = new ViewAccountWindow();
    }
}

