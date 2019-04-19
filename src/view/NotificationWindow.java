package view;


import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.*;

public class NotificationWindow extends JFrame{

    private GridBagConstraints gbc;
    private JPanel head;
    private JLabel header;
    private JScrollPane jScrollPane1;
    private JPanel pnl;

    public void showWindow(Component invoker, int x, int y){
        this.setLocation(invoker.getLocationOnScreen().x+x+40, invoker.getLocationOnScreen().y+y+30);
        if(this.pnl.getComponentCount() == 0){
            append(" no new notifications yet");
        }
        if(this.pnl.getComponentCount() < 4){
            for(int i = pnl.getComponentCount()-1; i < 4; i++){
                append("");
            }
        }
        this.setVisible(true);
    }

    public void append(String Text){
        JLabel jb = new JLabel(" "+Text);
        jb.setFont(new Font("Segoe UI", 1, 12));
        jb.setForeground(new Color(155,155,155, 255));
        jb.setBorder(BorderFactory.createEtchedBorder(Color.darkGray, Color.black));
        pnl.add(jb, gbc);
        pnl.revalidate();
    }

    public void clear(){
        pnl.removeAll();
    }
    //=======================================
    public NotificationWindow() {
        initComponents();
        setWindowListener();
    }

    private void initComponents() {


        head = new JPanel();
        header = new JLabel();
        jScrollPane1 = new JScrollPane();
        pnl = new JPanel();
        this.setPreferredSize(new Dimension(190,210));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        head.setLayout(new java.awt.BorderLayout());

        header.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        header.setText("  Notifications");
        header.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        header.setPreferredSize(new java.awt.Dimension(100, 30));
        header.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        head.add(header, java.awt.BorderLayout.CENTER);

        getContentPane().add(head, java.awt.BorderLayout.PAGE_START);

        java.awt.GridBagLayout pnlLayout = new java.awt.GridBagLayout();
        pnlLayout.columnWidths = new int[] {10};
        pnlLayout.rowHeights = new int[] {10};
        pnlLayout.columnWeights = new double[] {10.0};
        pnlLayout.rowWeights = new double[] {0.0};
        pnl.setLayout(pnlLayout);
        jScrollPane1.setViewportView(pnl);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 15;
        pnl.setBackground(new Color(25,25,25,255));
        header.setForeground(new Color(145,145,145,255));
        head.setBackground(new Color(5,5,5,255));
        jScrollPane1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
        pack();

    }
    private void setWindowListener(){
        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent we) {

            }

            @Override
            public void windowLostFocus(WindowEvent we) {
                clear();
                dispose();
            }
        });
    }
}
