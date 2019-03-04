package View;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CategoryPanel extends JPanel {
    private JLabel headerName;
    private JScrollPane scroll;

    //needs a header name as string and an arraylist of arraylist as parameter input for diaplaying the list of [category]
    public CategoryPanel(String header, ArrayList<ArrayList<Object>> rowsInput){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        add(Box.createRigidArea(new Dimension(0,7)));
//        JPanel headerPnl = new JPanel();
//        headerPnl.setLayout(new BoxLayout(headerPnl, BoxLayout.Y_AXIS));
//        headerPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerName = new JLabel(header.toUpperCase());
        headerName.setFont(new Font("Arial", Font.BOLD, 26));
        headerName.setForeground(Color.white);
        add(headerName);
//        headerPnl.add(headerName);
//        add(headerPnl);
        add(Box.createRigidArea(new Dimension(0,10)));

        String[] rowheader = {header, "Number of songs"};
        String[][] rows = new String[rowsInput.size()][2];
        for(int i=0;i<rowsInput.size();i++){
            for(int j=0;j<2;j++){
                rows[i][j] = rowsInput.get(i).get(j).toString();
            }
        }
        JTable categoryTable = new JTable(rows, rowheader);
        categoryTable.setForeground(Color.white);
        JTableHeader tableHeader = categoryTable.getTableHeader();
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
        scroll.setPreferredSize(new Dimension(50,100));
        add(scroll);
    }
}
