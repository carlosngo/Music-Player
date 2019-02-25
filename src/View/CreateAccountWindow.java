package View;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class CreateAccountWindow extends JFrame implements ActionListener, DocumentListener {
    private JTextField usernameInput, passwordInput;
    private JComboBox mon, day, yr;
    private JButton cancel, createAccount;

    public CreateAccountWindow(){
        init();
    }

    public void init(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0,78,56));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Create An Account");
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Abril Fatface", Font.BOLD, 28));
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
        usernameInput.getDocument().addDocumentListener(this);
        usernameInput.setFont(new Font("Arial", Font.BOLD, 22));
        usernameInput.setBackground(new Color(152,251,152));
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
        passwordInput.getDocument().addDocumentListener(this);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
        passwordInput.setBackground(new Color(152,251,152));
        p2.add(passwordInput);
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p3 = new JPanel();
        p3.setOpaque(false);
        p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
        p3.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel date = new JLabel("Date (MM/DD/YY):");
        date.setForeground(Color.white);
        date.setFont(new Font("Arial", Font.BOLD, 22));
        p3.add(date);
        String[] months = {"Month", "January", "February", "March", "April",
                "May", "June", "July", "August", "September",
                "October", "November", "December"};
        mon = new JComboBox(months);
        mon.setBackground(new Color(152,251,152));
        mon.addActionListener(this);
        p3.add(mon);
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {
            days[i] = Integer.toString(i);
        }
        day = new JComboBox(days);
        day.setBackground(new Color(152,251,152));
        day.addActionListener(this);
        p3.add(day);
        String[] years = new String[101];
        years[0] = "Year";
        for (int i = 1; i <= 100; i++) {
            years[i] = Integer.toString(1999 + i);
        }
        yr = new JComboBox(years);
        yr.setBackground(new Color(152,251,152));
        yr.addActionListener(this);
        p3.add(yr);
        p3.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Abril Fatface", Font.PLAIN, 20));
        cancel.addActionListener(this);
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        p4.add(cancel);
        p4.add(Box.createRigidArea(new Dimension(10,0))); // add space
        createAccount = new JButton("Create Account");
        createAccount.setEnabled(false);
        createAccount.setFont(new Font("Abril Fatface", Font.PLAIN, 20));
        createAccount.addActionListener(this);
        createAccount.setForeground(Color.white);
        createAccount.setBackground(new Color(1,121,150));
        createAccount.setOpaque(true);
        createAccount.setBorderPainted(false);
        p4.add(createAccount);
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
        if (!usernameInput.getText().trim().equals("")) {
            if (!passwordInput.getText().trim().equals("")) {
                if (yr.getSelectedIndex() != 0) {
                    if (mon.getSelectedIndex() != 0) {
                        if (day.getSelectedIndex() != 0) {
                            createAccount.setEnabled(true);
                        } else {
                            createAccount.setEnabled(false);
                        }
                    } else {
                        createAccount.setEnabled(false);
                    }
                } else {
                    createAccount.setEnabled(false);
                }
            } else {
                createAccount.setEnabled(false);
            }
        } else {
            createAccount.setEnabled(false);
        }
        if (e.getActionCommand().equals("Create Account")){
            //create account
        }
        if (e.getActionCommand().equals("Cancel")){
            UnregisteredMainScreen caw = new UnregisteredMainScreen();
            dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
            yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
            yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
            yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

    public static void main(String[] args){
        CreateAccountWindow caw = new CreateAccountWindow();
    }
}

