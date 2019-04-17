package view;


import java.awt.*;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NotificationWindow extends JFrame{

    private JScrollPane jScrollPane1;
    private JTable table;
    private DefaultTableModel dtm;
    private MouseAdapter mad;
    public boolean hasViewed;
    
    public NotificationWindow() {
        initLayout();
        initComponents();
        initDesign();
        hasViewed = true;
        //initAddUIControls -> use if the notifications needs to be clickable
    }
    
         
    public void append(String notification){
          Object[] o = {" ".concat(notification)};
          dtm.insertRow(0, o);
          hasViewed = false;
    }
    
    public void show(int x, int y) {
        setVisible(true);
        hasViewed = true;
        this.setLocation(x, y);
    }

    public boolean hasViewed(){
        return this.hasViewed;
    }
   
    //=======================================================
    private void initLayout(){
        setFocusable(false);
        setSize(200,200);
        setUndecorated(false);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
    }         
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        table = new javax.swing.JTable();
        dtm= new DefaultTableModel();
        dtm.addColumn("");
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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
