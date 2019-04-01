package view;

import model.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class UserAccountWindow extends JFrame implements ActionListener, DocumentListener {
    private JTextField usernameInput, passwordInput, firstNameInput, lastNameInput;
    private JComboBox mon, day ,yr, gender;
    private JButton back, editAccount, cancel, cancelEdit, createAccount, saveChanges;
    private User user;
    private JPanel buttonPnl;

    //view and edit account
    public UserAccountWindow(User user){
        this.user = user;
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
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.white);
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p1.add(firstNameLabel);
        firstNameInput = new JTextField("" , 20);
        firstNameInput.setText(user.getFirstName());
        firstNameInput.addActionListener(this);
        firstNameInput.setEditable(false);
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
        lastNameInput = new JTextField("" , 15);
        lastNameInput.setText(user.getLastName());
        lastNameInput.addActionListener(this);
        lastNameInput.setEditable(false);
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
//        //mon.setBackground(new Color(152,251,152));
//        SimpleDateFormat simpleDateformat = new SimpleDateFormat("MM"); // two digit numerical represenation
//        mon.setSelectedIndex(Integer.parseInt(simpleDateformat.format(user.getBirthday())));
        Calendar c = Calendar.getInstance();
        c.setTime(user.getBirthday());
        int month = c.get(Calendar.MONTH) + 1;
        mon.setSelectedIndex(month);
        mon.setEnabled(false);
        mon.addActionListener(this);
        p3.add(mon);
        String[] days = new String[32];
        days[0] = "Day";
        for (int i = 1; i <= 31; i++) {
            days[i] = Integer.toString(i);
        }
        day = new JComboBox(days);
        day.setEnabled(false);
        //day.setBackground(new Color(152,251,152));
        int nDate = c.get(Calendar.DAY_OF_MONTH) + 1;
        day.setSelectedIndex(nDate);
        day.addActionListener(this);
        p3.add(day);
        String[] years = new String[101];
        years[0] = "Year";
        for (int i = 1; i <= 100; i++) {
            years[i] = Integer.toString(1999 + i);
        }
        yr = new JComboBox(years);
        yr.setSelectedItem("" + c.get(Calendar.YEAR));
        yr.setEnabled(false);
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
        JLabel birthdayLbl = new JLabel("Gender:");
        birthdayLbl.setForeground(Color.white);
        birthdayLbl.setFont(new Font("Arial", Font.BOLD, 22));
        p4.add(birthdayLbl);
        String[] genderList = {"Male", "Female"};
        gender = new JComboBox(genderList);
        gender.setSelectedItem(user.getGender());
        gender.setEnabled(false);
        //gender.setBackground(new Color(152,251,152));
        gender.addActionListener(this);
        p4.add(gender);
        p4.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p4);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p5 = new JPanel();
        p5.setOpaque(false);
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.add(Box.createRigidArea(new Dimension(15,0))); // add space
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        p5.add(usernameLabel);
        usernameInput = new JTextField("" , 20);
        usernameInput.addActionListener(this);
        usernameInput.setText(user.getUserName());
        usernameInput.setEditable(false);
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
        passwordInput = new JTextField("" , 20);
        passwordInput.addActionListener(this);
        passwordInput.setText(user.getPassword());
        passwordInput.setEditable(false);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p6.add(passwordInput);
        p6.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p6);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        buttonPnl = new JPanel();
        buttonPnl.setOpaque(false);
        buttonPnl.setLayout(new BoxLayout(buttonPnl, BoxLayout.X_AXIS));
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.addActionListener(this);
        back.setForeground(Color.white);
        back.setBackground(new Color(1,121,150));
        back.setOpaque(true);
        back.setBorderPainted(false);
        buttonPnl.add(back);
        buttonPnl.add(Box.createRigidArea(new Dimension(10,0))); // add space
        editAccount = new JButton("Edit Account");
        editAccount.setFont(new Font("Arial", Font.PLAIN, 20));
        editAccount.addActionListener(this);
        editAccount.setForeground(Color.white);
        editAccount.setBackground(new Color(1,121,150));
        editAccount.setOpaque(true);
        editAccount.setBorderPainted(false);
        buttonPnl.add(editAccount);
        p.add(buttonPnl);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    //create account
    public UserAccountWindow(){
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
        String[] genderList = {"Choose gender", "Male", "Female"};
        gender = new JComboBox(genderList);
        gender.setFont(new Font("Arial", Font.PLAIN, 16));
        //gender.setEnabled(false);
        //gender.setBackground(new Color(152,251,152));
        gender.addActionListener(this);
        p4.add(gender);
        p4.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p4);
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
        passwordInput = new JTextField("" , 10);
        passwordInput.addActionListener(this);
        //passwordInput.setEditable(false);
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
        p7.add(createAccount);
        p.add(p7);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public JPanel editPnl(){
        buttonPnl = new JPanel();
        buttonPnl.setOpaque(false);
        buttonPnl.setLayout(new BoxLayout(buttonPnl, BoxLayout.X_AXIS));
        cancelEdit = new JButton("Cancel");
        cancelEdit.setFont(new Font("Arial", Font.PLAIN, 20));
        cancelEdit.addActionListener(this);
        cancelEdit.setForeground(Color.white);
        cancelEdit.setBackground(new Color(1,121,150));
        cancelEdit.setOpaque(true);
        cancelEdit.setBorderPainted(false);
        buttonPnl.add(cancelEdit);
        buttonPnl.add(Box.createRigidArea(new Dimension(10,0))); // add space
        saveChanges = new JButton("Save Changes");
        saveChanges.setFont(new Font("Arial", Font.PLAIN, 20));
        saveChanges.addActionListener(this);
        saveChanges.setForeground(Color.white);
        saveChanges.setBackground(new Color(1,121,150));
        saveChanges.setOpaque(true);
        saveChanges.setBorderPainted(false);
        buttonPnl.add(saveChanges);
        return buttonPnl;
    }

    public JPanel viewPnl(){
        buttonPnl = new JPanel();
        buttonPnl.setOpaque(false);
        buttonPnl.setLayout(new BoxLayout(buttonPnl, BoxLayout.X_AXIS));
        back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 20));
        back.addActionListener(this);
        back.setForeground(Color.white);
        back.setBackground(new Color(1,121,150));
        back.setOpaque(true);
        back.setBorderPainted(false);
        buttonPnl.add(back);
        buttonPnl.add(Box.createRigidArea(new Dimension(10,0))); // add space
        editAccount = new JButton("Edit Account");
        editAccount.setFont(new Font("Arial", Font.PLAIN, 20));
        editAccount.addActionListener(this);
        editAccount.setForeground(Color.white);
        editAccount.setBackground(new Color(1,121,150));
        editAccount.setOpaque(true);
        editAccount.setBorderPainted(false);
        buttonPnl.add(editAccount);
        return buttonPnl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createAccount){
            //create new account
            dispose();
        }
        if (e.getSource() == cancel){
            dispose();
        }
        if (e.getSource() == editAccount){
            buttonPnl.removeAll();
            buttonPnl.add(editPnl());
            mon.setEnabled(true);
            day.setEnabled(true);
            yr.setEnabled(true);
            gender.setEnabled(true);
            usernameInput.setEditable(true);
            passwordInput.setEditable(true);
            firstNameInput.setEditable(true);
            lastNameInput.setEditable(true);
        }
        if (e.getSource() == back){
            //MainScreen caw = new MainScreen();
            dispose();
        }
        if (e.getSource() == saveChanges){
            mon.setEnabled(false);
            day.setEnabled(false);
            yr.setEnabled(false);
            gender.setEnabled(false);
            usernameInput.setEditable(false);
            passwordInput.setEditable(false);
            firstNameInput.setEditable(false);
            lastNameInput.setEditable(false);
            buttonPnl.removeAll();
            buttonPnl.add(viewPnl());
        }
        if (e.getSource() == cancelEdit){
            mon.setEnabled(false);
            day.setEnabled(false);
            yr.setEnabled(false);
            gender.setEnabled(false);
            usernameInput.setEditable(false);
            passwordInput.setEditable(false);
            firstNameInput.setEditable(false);
            lastNameInput.setEditable(false);
            buttonPnl.removeAll();
            buttonPnl.add(viewPnl());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
                yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0){
            saveChanges.setEnabled(false);
            createAccount.setEnabled(false);
        }
        else{
            saveChanges.setEnabled(true);
            createAccount.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
                yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0){
            saveChanges.setEnabled(false);
            createAccount.setEnabled(false);
        }
        else{
            saveChanges.setEnabled(true);
            createAccount.setEnabled(true);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty() ||
                firstNameInput.getText().isEmpty() || lastNameInput.getText().isEmpty() ||
                yr.getSelectedIndex() == 0 || mon.getSelectedIndex() == 0 || day.getSelectedIndex() == 0){
            saveChanges.setEnabled(false);
            createAccount.setEnabled(false);
        }
        else{
            saveChanges.setEnabled(true);
            createAccount.setEnabled(true);
        }
    }
}
