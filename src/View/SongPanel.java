package View;

import Controller.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;

public class SongPanel extends JPanel implements ActionListener{
    SongController controller;
    private MyTableModel model;
    private JLabel headerName;
    private JScrollPane scroll;
    private JComboBox sortOptions;
    private JPanel tablePnl;
    private JTable categoryTable;
    private JTableHeader tableHeader;
    private String[] rowheader;
    private String[][] rows;

    ArrayList<ArrayList<String>> data; //testing

    public SongPanel(SongController controller, String header, ArrayList<ArrayList<String>> _data) {
        this.controller = controller;
//    public SongPanel(String header, ArrayList<ArrayList<Object>> data){

        data = _data;
        setLayout(new BorderLayout());
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        if(_data.size()==0){
            JLabel blankMessage = new JLabel("No songs to show.");
            blankMessage.setForeground(Color.white);
            blankMessage.setFont(new Font("Arial", Font.BOLD, 26));
//            add(blankMessage);
            add(blankMessage, BorderLayout.NORTH);
        }
        else{
            add(Box.createRigidArea(new Dimension(0,7)));
            JPanel headerPnl = new JPanel();
            headerPnl.setLayout(new BorderLayout());
//            headerPnl.setLayout(new BoxLayout(headerPnl, BoxLayout.X_AXIS));
//            headerPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
            headerPnl.setOpaque(false);
//            headerPnl.add(Box.createRigidArea(new Dimension(15,0)));
            headerName = new JLabel(header.toUpperCase());
            headerName.setFont(new Font("Arial", Font.BOLD, 26));
            headerName.setForeground(Color.white);

            headerPnl.add(headerName, BorderLayout.WEST);
//            headerPnl.add(headerName);
//            headerPnl.add(Box.createRigidArea(new Dimension(230,0)));
            String[] sort = {"(Sort By)","Artist", "Album", "Genre", "Year", "None"};
            sortOptions = new JComboBox(sort);
            sortOptions.setForeground(SystemColor.windowText);
            sortOptions.addActionListener(this);
            sortOptions.setFont(new Font("Arial", Font.PLAIN, 12));
//            sortOptions.setPreferredSize(new Dimension(100,15));
//            sortOptions.setMinimumSize(new Dimension(100,20));
//            sortOptions.setMaximumSize(new Dimension(100,20));
//            headerPnl.add(sortOptions);
            headerPnl.add(sortOptions, BorderLayout.EAST);
            add(headerPnl, BorderLayout.NORTH);
//            add(headerPnl);
//            add(Box.createRigidArea(new Dimension(0,10)));

            tablePnl = new JPanel();
            tablePnl.setLayout(new BoxLayout(tablePnl, BoxLayout.Y_AXIS));
            tablePnl.setOpaque(false);
//            String[] rowheader = {"Title", "Artist", "Album", "Year", "Genre"};
//            String[][] rows = new String[data.size()][5];
//            for(int i=0;i<data.size();i++){
//                for(int j=0;j<5;j++){
//                    rows[i][j] = data.get(i).get(j).toString();
//                }
//            }
//            categoryTable = new JTable(rows, rowheader);
            model = new MyTableModel();
            for(int i=0 ; i<_data.size() ; i++){
                model.add(_data.get(i));
            }
            categoryTable = new JTable(model);
            SongPanel.ActionPaneRenderer renderer = new SongPanel.ActionPaneRenderer();
            categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
            categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
            categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);

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
//            add(tablePnl);
            add(tablePnl, BorderLayout.CENTER);
        }

    }

    public void addRow(ArrayList<String> data) {
        model.add(data);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MyTableModel model;
        if(e.getSource() == sortOptions){
            JComboBox cb = (JComboBox) e.getSource();
            String choice = (String) cb.getSelectedItem();
            switch (choice){
                case "Artist":
                    tablePnl.removeAll();
//                    String[] rowheader = {"Title", "Artist", "Album", "Year", "Genre"};
//                    String[][] rows = new String[data.size()][5];
//                    for(int i = 0; i< data.size(); i+=2){
//                        for(int j=0;j<5;j++){
//                            rows[i][j] = data.get(i).get(j).toString();
//                        }
//                    }
//                    categoryTable = new JTable(rows, rowheader);
                    Collections.sort(data, new Comparator<ArrayList<String>>() {
                        @Override
                        public int compare(ArrayList<String> one, ArrayList<String> two) {
                            return one.get(1).compareTo(two.get(1));
                        }
                    });
                    model = new MyTableModel();
                    for(int i=0 ; i<data.size() ; i++){
                        model.add(data.get(i));
                    }
                    categoryTable = new JTable(model);
                    SongPanel.ActionPaneRenderer renderer = new SongPanel.ActionPaneRenderer();
                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);

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
//                    String[] rowheader1 = {"Title", "Artist", "Album", "Year", "Genre"};
//                    String[][] rows1 = new String[data.size()][5];
//                    for(int i = 0; i< data.size(); i+=3){
//                        for(int j=0;j<5;j++){
//                            rows1[i][j] = data.get(i).get(j).toString();
//                        }
//                    }
//                    JTable categoryTable = new JTable(rows1, rowheader1);
                    Collections.sort(data, new Comparator<ArrayList<String>>() {
                        @Override
                        public int compare(ArrayList<String> one, ArrayList<String> two) {
                            return one.get(2).compareTo(two.get(2));
                        }
                    });
                    model = new MyTableModel();
                    for(int i=0 ; i<data.size() ; i++){
                        model.add(data.get(i));
                    }
                    categoryTable = new JTable(model);
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
//                    String[] rowheader3 = {"Title", "Artist", "Album", "Year", "Genre"};
//                    String[][] rows3 = new String[data.size()][5];
//                    for(int i = 0; i< data.size(); i+=4){
//                        for(int j=0;j<5;j++){
//                            rows3[i][j] = data.get(i).get(j).toString();
//                        }
//                    }
//                    categoryTable = new JTable(rows3, rowheader3);

                    Collections.sort(data, new Comparator<ArrayList<String>>() {
                        @Override
                        public int compare(ArrayList<String> one, ArrayList<String> two) {
                            return one.get(3).compareTo(two.get(3));
                        }
                    });
                    model = new MyTableModel();
                    for(int i=0 ; i<data.size() ; i++){
                        model.add(data.get(i));
                    }
                    categoryTable = new JTable(model);
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
//                    String[] rowheader4 = {"Title", "Artist", "Album", "Year", "Genre"};
//                    String[][] rows4 = new String[data.size()][5];
//                    for(int i = 0; i< data.size(); i+=5){
//                        for(int j=0;j<5;j++){
//                            rows4[i][j] = data.get(i).get(j).toString();
//                        }
//                    }
//                    categoryTable = new JTable(rows4, rowheader4);

                    Collections.sort(data, new Comparator<ArrayList<String>>() {
                        @Override
                        public int compare(ArrayList<String> one, ArrayList<String> two) {
                            return one.get(4).compareTo(two.get(4));
                        }
                    });
                    model = new MyTableModel();
                    for(int i=0 ; i<data.size() ; i++){
                        model.add(data.get(i));
                    }
                    categoryTable = new JTable(model);
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
                    String[][] rows5 = new String[data.size()][5];
                    for(int i = 0; i< data.size(); i++){
                        for(int j=0;j<5;j++){
                            rows5[i][j] = data.get(i).get(j).toString();
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


    public class MyTableModel extends AbstractTableModel {

        private ArrayList<ArrayList<String>> data;

        public MyTableModel() {
            data = new ArrayList<ArrayList<String>>();
        }

        @Override
        public String getColumnName(int column) {
            String value = null;
            switch (column) {
                case 0:
                    value = "Actions";
                    break;
                case 1:
                    value = "Title";
                    break;
                case 2:
                    value = "Artist";
                    break;
                case 3:
                    value = "Album";
                    break;
                case 4:
                    value = "Year";
                    break;
                case 5:
                    value = "Genre";
                    break;
            }
            return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class value = Object.class;
            switch (columnIndex) {
                case 0:
                    value = JPanel.class;
                    break;
                case 1:
                    value = String.class;
                    break;
                case 2:
                    value = String.class;
                    break;
                case 3:
                    value = String.class;
                    break;
                case 4:
                    value = String.class;
                    break;
                case 5:
                    value = String.class;
                    break;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ArrayList<String> obj = data.get(rowIndex);
            Object value = null;
            switch (columnIndex) {
                case 0:
                    break;
                case 1:
                    value = obj.get(0);
                    break;
                case 2:
                    value = obj.get(1);
                    break;
                case 3:
                    value = obj.get(2);
                    break;
                case 4:
                    value = obj.get(3);
                    break;
                case 5:
                    value = obj.get(4);
                    break;
            }
            return value;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex == 5) {

                System.out.println(aValue);

                ArrayList<String> value = data.get(rowIndex);
                if ("play".equals(aValue)) {
                    System.out.println("Play");
                }
                else if ("delete".equals(aValue)) {
                    System.out.println("Delete");
                }
                else {
                    System.out.println("Add to Playlist");
                }
                fireTableCellUpdated(rowIndex, columnIndex);
                remove(value);

            }
        }

        public void add(ArrayList<String> value) {
            int startIndex = getRowCount();
            data.add(value);
            fireTableRowsInserted(startIndex, getRowCount() - 1);
        }

        public void remove(ArrayList<String> value) {
            int startIndex = data.indexOf(value);
            System.out.println("startIndex = " + startIndex);
            data.remove(value);
            fireTableRowsInserted(startIndex, startIndex);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }
    }

    public class ActionsPane extends JPanel {

        private JButton play;
        private JButton addToPLaylist;
        private JButton delete;
        private JButton edit;
        private String state;

        public ActionsPane() {
            setLayout(new GridBagLayout());
            play = new JButton();
            play.setOpaque(false);
            play.setContentAreaFilled(false);
            play.setBorderPainted(false);
            play.setActionCommand("play");
            addToPLaylist = new JButton();
            addToPLaylist.setOpaque(false);
            addToPLaylist.setContentAreaFilled(false);
            addToPLaylist.setBorderPainted(false);
            addToPLaylist.setActionCommand("add");
            delete = new JButton();
            delete.setOpaque(false);
            delete.setContentAreaFilled(false);
            delete.setBorderPainted(false);
            delete.setActionCommand("delete");
            edit = new JButton();
            edit.setOpaque(false);
            edit.setContentAreaFilled(false);
            edit.setBorderPainted(false);
            edit.setActionCommand("edit");

            try{
                URL resource = getClass().getClassLoader().getResource("images/plus.png");
                File img = Paths.get(resource.toURI()).toFile();
                addToPLaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/delete.png");
                img = Paths.get(resource.toURI()).toFile();
                delete.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/edit.png");
                img = Paths.get(resource.toURI()).toFile();
                edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
                img = Paths.get(resource.toURI()).toFile();
                play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
            catch(Exception e){

            }

            add(play);
            add(addToPLaylist);
            add(delete);
            add(edit);

            ActionListener playListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    //add the following:
                    //currently paying song should stop
                    //selected song should play
                    //see PlayerController's commented out main function
                }
            };
            play.addActionListener(playListener);

            play.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    play.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    play.setEnabled(false);
                }
            });


            ActionListener addToPlaylistListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    AddToPlaylistWindow atpw = new AddToPlaylistWindow();
                    //String playlistName = atpw.getName();
                    //add song to "atpw.getName();" playlist
                }
            };
            addToPLaylist.addActionListener(addToPlaylistListener);

            addToPLaylist.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    addToPLaylist.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    addToPLaylist.setEnabled(false);
                }
            });


            ActionListener deleteListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    //get selected song and delete it from database
                }
            };
            delete.addActionListener(deleteListener);

            delete.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    delete.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    delete.setEnabled(false);
                }
            });


            ActionListener editListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    EditSongProfileWindow eaw = new EditSongProfileWindow();
//                    update song profile in database if eaw.isEdited() returns true
//                    updated title = eaw.getTitle()
//                    updated album = eaw.getAlbum()
//                    updated year = eaw.getYear()
//                    updated genre = eaw.getGenre()
                }
            };
            edit.addActionListener(editListener);

            edit.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    edit.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    edit.setEnabled(false);
                }
            });
        }

        public void addActionListener(ActionListener listener) {
            addToPLaylist.addActionListener(listener);
            delete.addActionListener(listener);
            edit.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }

    public class ActionPaneRenderer extends DefaultTableCellRenderer {

        private SongPanel.ActionsPane actionsPane;

        public ActionPaneRenderer() {
            actionsPane = new SongPanel.ActionsPane();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                actionsPane.setBackground(table.getSelectionBackground());
            } else {
                actionsPane.setBackground(table.getBackground());
            }
            return actionsPane;
        }
    }

    public class ActionEditor extends AbstractCellEditor implements TableCellEditor {

        private SongPanel.ActionsPane actionsPane;

        public ActionEditor() {
            actionsPane = new SongPanel.ActionsPane();
            actionsPane.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            stopCellEditing();
                        }
                    });
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return actionsPane.getState();
        }

        @Override
        public boolean isCellEditable(EventObject e) {
            return true;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                actionsPane.setBackground(table.getSelectionBackground());
            } else {
                actionsPane.setBackground(table.getBackground());
            }
            return actionsPane;
        }
    }

}

