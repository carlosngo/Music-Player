package View;

import Controller.PlayerController;

import javax.media.MediaLocator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Dashboard extends JFrame implements ActionListener {
    private JPanel displayPanel;

    //sample arraylist
    private ArrayList<String> samplelist;

    public Dashboard(){
        samplelist = new ArrayList<String>(5);
        samplelist.add("suboption 0");
        samplelist.add("suboption 1");
        samplelist.add("suboption 2");
        samplelist.add("suboption 3");
        samplelist.add("suboption 4");
        samplelist.add("suboption 5");

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        p.setBackground(new Color(0,0,0));
        p.add(Box.createRigidArea(new Dimension(0,10)));


            //AccountPanel acntPnl = new AccountPanel(this, new User());
            AccountPanel acntPnl = new AccountPanel(this);
            p.add(acntPnl);

        p.add(Box.createRigidArea(new Dimension(0,7)));

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.setOpaque(false);
        p.add(p2);


        p2.add(Box.createRigidArea(new Dimension(15,0)));
        ControlPanel cp = new ControlPanel(this);
        p2.add(cp);
        Border border = BorderFactory.createLineBorder(Color.white); // line for the border
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.setOpaque(false);
        p3.setBorder(border);

        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        displayPanel.setOpaque(false);
        displayPanel.setBorder(border);
        p2.add(displayPanel);

        int var = 0;
        ArrayList<Object> miniRow = new ArrayList<Object>();
        ArrayList<ArrayList<Object>> rowsInput = new ArrayList<ArrayList<Object>>();
        for(int i=0;i<20;i++){
            for(int j=0;j<5;j++){
                miniRow.add(var);
                var++;
            }
            rowsInput.add(miniRow);
            miniRow = new ArrayList<Object>();
        }
        System.out.println(rowsInput.size());
        displayPanel.add(new SongPanel("Most Played", rowsInput));


        JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        PlayerController pc = new PlayerController();
        try{
            pc.setMediaLocator(new MediaLocator(fc.getSelectedFile().toURI().toURL()), "Song1", "Artist1");
            p.add(pc.getPlayerPanel());
        }catch(Exception e){}
        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public JPanel getDisplayPanel(){
        return displayPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args){
        Dashboard caw = new Dashboard();
    }
}
