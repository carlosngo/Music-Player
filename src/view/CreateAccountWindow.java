package view;
import controller.AccountController;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class CreateAccountWindow extends JFrame implements ActionListener, DocumentListener {
    private AccountController controller;
    private JTextField usernameInput, firstNameInput, lastNameInput;
    private JPasswordField passwordInput;
    private JComboBox mon, day, yr, gender, userType;
    private JButton cancel, createAccount;

    public CreateAccountWindow(AccountController controller){
        this.controller = controller;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Create Account");
        title.setForeground(Color.white);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0,20))); // add space


        JPanel p1 = new JPanel();
        p1.setOpaque(false);
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.white);
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(firstNameLabel);
        firstNameInput = new JTextField("" , 10);
        firstNameInput.addActionListener(this);
        //firstNameInput.setEditable(false);
        firstNameInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p1.add(firstNameInput);
        p1.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p1);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p2 = new JPanel();
        p2.setOpaque(false);
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.white);
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p2.add(lastNameLabel);
        lastNameInput = new JTextField("" , 10);
        lastNameInput.addActionListener(this);
        //lastNameInput.setEditable(false);
        lastNameInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p2.add(lastNameInput);
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
        mon.setFont(new Font("Arial", Font.PLAIN, 16));
        //mon.setBackground(new Color(152,251,152));
        //mon.setEnabled(false);
        mon.addActionListener(this);
        p3.add(mon);
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {
            days[i] = Integer.toString(i);
        }
        day = new JComboBox(days);
        day.setFont(new Font("Arial", Font.PLAIN, 16));
        //day.setEnabled(false);
        //day.setBackground(new Color(152,251,152));
        day.addActionListener(this);
        p3.add(day);
        String[] years = new String[101];
        years[0] = "Year";
        for (int i = 1; i <= 100; i++) {
            years[i] = Integer.toString(1999 + i);
        }
        yr = new JComboBox(years);
        yr.setFont(new Font("Arial", Font.PLAIN, 16));
        //yr.setEnabled(false);
        //yr.setBackground(new Color(152,251,152));
        yr.addActionListener(this);
        p3.add(yr);
        p3.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p3);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        p4.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel genderLbl = new JLabel("Gender:");
        genderLbl.setForeground(Color.white);
        genderLbl.setFont(new Font("Arial", Font.BOLD, 22));
        p4.add(genderLbl);
        String[] genderList = {"Choose gender", "M", "F"};
        gender = new JComboBox(genderList);
        gender.setFont(new Font("Arial", Font.PLAIN, 16));
        //gender.setEnabled(false);
        //gender.setBackground(new Color(152,251,152));
        gender.addActionListener(this);
        p4.add(gender);
        p4.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel P8 = new JPanel();
        P8.setOpaque(false);
        P8.setLayout(new BoxLayout(P8, BoxLayout.X_AXIS));
        P8.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel userTypeLbl = new JLabel("User Type:");
        userTypeLbl.setForeground(Color.white);
        userTypeLbl.setFont(new Font("Arial", Font.BOLD, 22));
        P8.add(userTypeLbl);
        String[] typeList = {"Choose user type", "Listener", "Artist"};
        userType = new JComboBox(typeList);
        userType.setFont(new Font("Arial", Font.PLAIN, 16));
        //userType.setEnabled(false);
        //userType.setBackground(new Color(152,251,152));
        userType.addActionListener(this);
        P8.add(userType);
        P8.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(P8);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p5 = new JPanel();
        p5.setOpaque(false);
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p5.add(usernameLabel);
        usernameInput = new JTextField("" , 10);
        usernameInput.addActionListener(this);
        //usernameInput.setEditable(false);
        usernameInput.setFont(new Font("Arial", Font.BOLD, 22));
        p5.add(usernameInput);
        p5.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p5);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p6 = new JPanel();
        p6.setOpaque(false);
        p6.setLayout(new BoxLayout(p6, BoxLayout.X_AXIS));
        p6.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p6.add(passwordLabel);
//        passwordInput = new JTextField("" , 10);
//        passwordInput.addActionListener(this);
//        //passwordInput.setEditable(false);
//        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
//        //passwordInput.setBackground(new Color(152,251,152));
//        p6.add(passwordInput);
        passwordInput = new JPasswordField("" , 15);
        passwordInput.addActionListener(this);
        passwordInput.getDocument().addDocumentListener(this);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p6.add(passwordInput);
        p6.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p6);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p7 = new JPanel();
        p7.setOpaque(false);
        p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));
        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 20));
        cancel.addActionListener(this);
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        p7.add(cancel);
        p7.add(Box.createRigidArea(new Dimension(10,0))); // add space
        createAccount = new JButton("Create Account");
        createAccount.setFont(new Font("Arial", Font.PLAIN, 20));
        createAccount.addActionListener(this);
        createAccount.setForeground(Color.white);
        createAccount.setBackground(new Color(1,121,150));
        createAccount.setOpaque(true);
        createAccount.setBorderPainted(false);
        createAccount.setEnabled(false);
        p7.add(createAccount);
        p.add(p7);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (!usernameInput.getText().trim().equals("") && !firstNameInput.getText().trim().equals("")
                && !lastNameInput.getText().trim().equals("") && !pw.equals("") ) {
            if ((gender.getSelectedIndex() != 0) && (userType.getSelectedIndex() != 0)) {
                if (yr.getSelectedIndex() != 0) {
                    if (mon.getSelectedIndex() != 0) {
                        if (day.getSelectedIndex() != 0 ) {
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

        if (e.getSource() == createAccount){
            Calendar c = Calendar.getInstance();
            c.set(yr.getSelectedIndex() + 2000, mon.getSelectedIndex() - 1, day.getSelectedIndex());
            boolean success = controller.register(usernameInput.getText(), pw, firstNameInput.getText(),
                    lastNameInput.getText(), (String)gender.getSelectedItem(), (String) userType.getSelectedItem(), c.getTime());
            if (!success) {
                JOptionPane.showMessageDialog(null, "Username already exists.",
                        "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        }
        if (e.getSource() == cancel){
            dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
            yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
                yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        String pw = new String(passwordInput.getPassword());
        if (usernameInput.getText().isEmpty() || pw.isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
                yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0)
            createAccount.setEnabled(false);
        else
            createAccount.setEnabled(true);
    }

//    public static void main(String[] args){
//        CreateAccountWindow caw = new CreateAccountWindow();
//    }
}

