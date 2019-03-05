package View;

import Controller.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongPanel extends JPanel implements ActionListener{
    SongController controller;
    private JLabel headerName;
    private JScrollPane scroll;
    private JComboBox sortOptions;
    private JPanel tablePnl;
    private JTable categoryTable;
    private JTableHeader tableHeader;
    private String[] rowheader;
    private String[][] rows;

    ArrayList<ArrayList<Object>> testList; //testing

    public SongPanel(SongController controller, String header) {
        this.controller = controller;
//    public SongPanel(String header, ArrayList<ArrayList<Object>> rowsInput){

        ArrayList<ArrayList<Object>> rowsInput = null;
        testList = rowsInput;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        add(Box.createRigidArea(new Dimension(0,7)));
        JPanel headerPnl = new JPanel();
        headerPnl.setLayout(new BoxLayout(headerPnl, BoxLayout.X_AXIS));
        headerPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPnl.setOpaque(false);
        headerPnl.add(Box.createRigidArea(new Dimension(15,0)));
        headerName = new JLabel(header.toUpperCase());
        headerName.setFont(new Font("Arial", Font.BOLD, 26));
        headerName.setForeground(Color.white);
        headerPnl.add(headerName);
        headerPnl.add(Box.createRigidArea(new Dimension(230,0)));
        String[] sort = {"(Sort By)","Artist", "Album", "Genre", "Year", "None"};
        sortOptions = new JComboBox(sort);
        sortOptions.setForeground(SystemColor.windowText);
        sortOptions.addActionListener(this);
        sortOptions.setFont(new Font("Arial", Font.PLAIN, 12));
        sortOptions.setPreferredSize(new Dimension(100,15));
        sortOptions.setMinimumSize(new Dimension(100,20));
        sortOptions.setMaximumSize(new Dimension(100,20));
        headerPnl.add(sortOptions);
        add(headerPnl);
        add(Box.createRigidArea(new Dimension(0,10)));

        tablePnl = new JPanel();
        tablePnl.setLayout(new BoxLayout(tablePnl, BoxLayout.Y_AXIS));
        tablePnl.setOpaque(false);
        String[] rowheader = {"Title", "Artist", "Album", "Year", "Genre"};
        String[][] rows = new String[rowsInput.size()][5];
        for(int i=0;i<rowsInput.size();i++){
            for(int j=0;j<5;j++){
                rows[i][j] = rowsInput.get(i).get(j).toString();
            }
        }
        categoryTable = new JTable(rows, rowheader);
        categoryTable.setForeground(Color.white);
        tableHeader = categoryTable.getTableHeader();
        tableHeader.setBackground(new Color(65,15,225)); // change the Background color
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//        categoryTable.setPreferredSize(new Dimension(50,100));
//        categoryTable.setMinimumSize(new Dimension(50,100));
//        categoryTable.setMaximumSize(new Dimension(50,100));
        categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
        categoryTable.setOpaque(false);
        categoryTable.setRowHeight(30);
        //categoryTable.getColumn(0).setPreferredWidth(50);
        //categoryTable.getColumn(1).setPreferredWidth(50);
        categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
            setOpaque(false);
        }});
        //((DefaultTableCellRenderer)categoryTable.getDefaultRenderer(Object.class)).setOpaque(false);
        //categoryTable.setShowGrid(false);
        scroll = new JScrollPane(categoryTable);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,60));
        tablePnl.add(scroll);
        add(tablePnl);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sortOptions){
            JComboBox cb = (JComboBox) e.getSource();
            String choice = (String) cb.getSelectedItem();
            switch (choice){
                case "Artist":
                    tablePnl.removeAll();
                    String[] rowheader = {"Title", "Artist", "Album", "Year", "Genre"};
                    String[][] rows = new String[testList.size()][5];
                    for(int i=0;i<testList.size();i+=2){
                        for(int j=0;j<5;j++){
                            rows[i][j] = testList.get(i).get(j).toString();
                        }
                    }
                    categoryTable = new JTable(rows, rowheader);
                    categoryTable.setForeground(Color.white);
                    tableHeader = categoryTable.getTableHeader();
                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
                    tableHeader.setForeground(Color.WHITE);
                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryTable.setOpaque(false);
                    categoryTable.setRowHeight(30);
                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
                    scroll = new JScrollPane(categoryTable);
                    scroll.getViewport().setOpaque(false);
                    scroll.setOpaque(false);
                    scroll.setPreferredSize(new Dimension(50,60));
                    tablePnl.add(scroll);
                    System.out.println(1);
                    break;

                case "Album":
                    tablePnl.removeAll();
                    String[] rowheader1 = {"Title", "Artist", "Album", "Year", "Genre"};
                    String[][] rows1 = new String[testList.size()][5];
                    for(int i=0;i<testList.size();i+=3){
                        for(int j=0;j<5;j++){
                            rows1[i][j] = testList.get(i).get(j).toString();
                        }
                    }
                    JTable categoryTable = new JTable(rows1, rowheader1);
                    categoryTable.setForeground(Color.white);
                    tableHeader = categoryTable.getTableHeader();
                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
                    tableHeader.setForeground(Color.WHITE);
                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryTable.setOpaque(false);
                    categoryTable.setRowHeight(30);
                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
                    scroll = new JScrollPane(categoryTable);
                    scroll.getViewport().setOpaque(false);
                    scroll.setOpaque(false);
                    scroll.setPreferredSize(new Dimension(50,60));
                    tablePnl.add(scroll);
                    System.out.println(2);
                    break;
                case "Year":
                    tablePnl.removeAll();
                    String[] rowheader3 = {"Title", "Artist", "Album", "Year", "Genre"};
                    String[][] rows3 = new String[testList.size()][5];
                    for(int i=0;i<testList.size();i+=4){
                        for(int j=0;j<5;j++){
                            rows3[i][j] = testList.get(i).get(j).toString();
                        }
                    }
                    categoryTable = new JTable(rows3, rowheader3);
                    categoryTable.setForeground(Color.white);
                    tableHeader = categoryTable.getTableHeader();
                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
                    tableHeader.setForeground(Color.WHITE);
                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryTable.setOpaque(false);
                    categoryTable.setRowHeight(30);
                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
                    scroll = new JScrollPane(categoryTable);
                    scroll.getViewport().setOpaque(false);
                    scroll.setOpaque(false);
                    scroll.setPreferredSize(new Dimension(50,60));
                    tablePnl.add(scroll);
                    System.out.println(3);
                    break;
                case "Genre":
                    tablePnl.removeAll();
                    String[] rowheader4 = {"Title", "Artist", "Album", "Year", "Genre"};
                    String[][] rows4 = new String[testList.size()][5];
                    for(int i=0;i<testList.size();i+=5){
                        for(int j=0;j<5;j++){
                            rows4[i][j] = testList.get(i).get(j).toString();
                        }
                    }
                    categoryTable = new JTable(rows4, rowheader4);
                    categoryTable.setForeground(Color.white);
                    tableHeader = categoryTable.getTableHeader();
                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
                    tableHeader.setForeground(Color.WHITE);
                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryTable.setOpaque(false);
                    categoryTable.setRowHeight(30);
                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
                    scroll = new JScrollPane(categoryTable);
                    scroll.getViewport().setOpaque(false);
                    scroll.setOpaque(false);
                    scroll.setPreferredSize(new Dimension(50,60));
                    tablePnl.add(scroll);
                    System.out.println(4);
                    break;
                case "None":
                    tablePnl.removeAll();
                    String[] rowheader5 = {"Title", "Artist", "Album", "Year", "Genre"};
                    String[][] rows5 = new String[testList.size()][5];
                    for(int i=0;i<testList.size();i++){
                        for(int j=0;j<5;j++){
                            rows5[i][j] = testList.get(i).get(j).toString();
                        }
                    }
                    categoryTable = new JTable(rows5, rowheader5);
                    categoryTable.setForeground(Color.white);
                    tableHeader = categoryTable.getTableHeader();
                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
                    tableHeader.setForeground(Color.WHITE);
                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
                    categoryTable.setOpaque(false);
                    categoryTable.setRowHeight(30);
                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
                    scroll = new JScrollPane(categoryTable);
                    scroll.getViewport().setOpaque(false);
                    scroll.setOpaque(false);
                    scroll.setPreferredSize(new Dimension(50,60));
                    tablePnl.add(scroll);
                    System.out.println(5);
                    break;
            }
            revalidate();
            repaint();
        }
    }
}
