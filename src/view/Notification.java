package view;


import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Notification extends JFrame{

    private JScrollPane jScrollPane1;
    private JTable table;
    private DefaultTableModel dtm;
    private MouseAdapter mad;
    public boolean hasViewed;
    
    public Notification() {
        initLayout();
        initComponents();
        initDesign();
        hasViewed = false;
        //initAddUIControls -> use if the notifications needs to be clickable
    }
    
         
    public void append(String notification){
          Object[] o = {" ".concat(notification)};
          dtm.insertRow(0, o);
          hasViewed = false;
    }
    
    public void show(Component invoker, int x, int y){
        show(invoker, x, y);
        setVisible(true);
        hasViewed = true;
    }
    
    public void hideNotif(){
       setVisible(false);
       dtm.setRowCount(0);
    }
    
    public boolean hasViewed(){
        return hasViewed;
    }
   
    //=======================================================
    private void initLayout(){
        setFocusable(false);
        setSize(400,400);
        setUndecorated(true);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
       // this.setLayout(new BoxLayou);
    }         
    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        table = new javax.swing.JTable();
        dtm= new DefaultTableModel();
        dtm.addColumn("Notifications");
        table.setModel(dtm);
        table.setFocusable(false);
        table.setSurrendersFocusOnKeystroke(true);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        table.setEnabled(false);
        //this.add(jScrollPane1);
        
        getContentPane().add(jScrollPane1);
        setSize(330, 460);
    }                     

    private void initDesign() {
        table.setGridColor(new Color(204, 204, 204));
        table.setRowHeight(80);
        table.setBackground(Color.darkGray);
        table.setForeground(Color.white);
        ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
        table.getTableHeader().setBackground(Color.gray);
         table.getTableHeader().setFont(new Font(Font.SERIF,  Font.BOLD, 20));
        table.getTableHeader().setBorder(null);
        table.getTableHeader().setFocusable(false);
        table.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        this.setBackground(Color.darkGray);
        this.jScrollPane1.getViewport().setBackground(Color.darkGray);
        this.jScrollPane1.setBackground(Color.darkGray);
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        //putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setBackground(new Color(0,0,0,0));
        //setBorder(null);
        setRootPaneCheckingEnabled(false);
        //((BasicInternalFrameUI) getUI()).setNorthPane(null);
        setRootPaneCheckingEnabled(true);
    }
    
    private void addUIControls(){
        mad = new MouseAdapter() {
            
            
        };
        
        addMouseListener(mad);
    }

//    public static void main(String args[]){
//        Notification n = new Notification();
//        n.show(90,90);
//        n.append("hello world");
//    }
}
