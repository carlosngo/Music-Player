package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditAccountWindow extends JFrame implements ActionListener, DocumentListener {
    private JTextField usernameInput, passwordInput;
    private JComboBox mon, day, yr;
    private JButton cancel, saveChanges;

    public EditAccountWindow(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(new Color(0,0,0));

        p.add(Box.createRigidArea(new Dimension(0,15))); // add space
        JLabel title = new JLabel("Create An Account");
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
        usernameInput.getDocument().addDocumentListener(this);
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
        passwordInput.getDocument().addDocumentListener(this);
        passwordInput.setFont(new Font("Arial", Font.BOLD, 22));
        //passwordInput.setBackground(new Color(152,251,152));
        p2.add(passwordInput);
        p2.add(Box.createRigidArea(new Dimension(15,0))); // add space
        p.add(p2);
        p.add(Box.createRigidArea(new Dimension(0,7))); // add space

        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.PLAIN, 20));
        cancel.addActionListener(this);
        cancel.setForeground(Color.white);
        cancel.setBackground(new Color(1,121,150));
        cancel.setOpaque(true);
        cancel.setBorderPainted(false);
        p4.add(cancel);
        p4.add(Box.createRigidArea(new Dimension(10,0))); // add space
        saveChanges = new JButton("Save Changes");
        saveChanges.setEnabled(false);
        saveChanges.setFont(new Font("Arial", Font.PLAIN, 20));
        saveChanges.addActionListener(this);
        saveChanges.setForeground(Color.white);
        saveChanges.setBackground(new Color(1,121,150));
        saveChanges.setOpaque(true);
        saveChanges.setBorderPainted(false);
        p4.add(saveChanges);
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
        if (!usernameInput.getText().trim().equals("")){
            if (!passwordInput.getText().trim().equals("")){
                saveChanges.setEnabled(true);
            }
            else{
                saveChanges.setEnabled(false);
            }
        }
        else{
            saveChanges.setEnabled(false);
        }
        if (e.getActionCommand().equals("Save Changes")){
            //Save Changes
            dispose();
        }
        if (e.getActionCommand().equals("Cancel")){
            dispose();
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            saveChanges.setEnabled(false);
        else
            saveChanges.setEnabled(true);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            saveChanges.setEnabled(false);
        else
            saveChanges.setEnabled(true);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty())
            saveChanges.setEnabled(false);
        else
            saveChanges.setEnabled(true);
    }

    public static void main(String[] args){
        EditAccountWindow eaw = new EditAccountWindow();
    }
}

