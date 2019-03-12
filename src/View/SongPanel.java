package View;

import Controller.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;

public class SongPanel extends JPanel implements ActionListener{
    SongController controller;
    private JLabel headerName;
    private JScrollPane scroll;
    private JComboBox sortOptions;
    private JPanel tablePnl;
    private JTable categoryTable;
    private JTableHeader tableHeader;
    private int currentRow;
    private MyTableModel model;

    ArrayList<ArrayList<String>> data; //testing

    public SongPanel(SongController controller, String header, ArrayList<ArrayList<String>> _data) {
        this.controller = controller;
//    public SongPanel(String header, ArrayList<ArrayList<Object>> data){

        data = _data;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        if(_data.size()==0){
            JLabel blankMessage = new JLabel("No songs to show.");
            blankMessage.setForeground(Color.white);
            blankMessage.setFont(new Font("Arial", Font.BOLD, 26));
            add(blankMessage);
        }
        else{
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
//            String[] sort = {"(Sort By)","Artist", "Album", "Genre", "Year", "None"};
//            sortOptions = new JComboBox(sort);
//            sortOptions.setForeground(SystemColor.windowText);
//            sortOptions.addActionListener(this);
//            sortOptions.setFont(new Font("Arial", Font.PLAIN, 12));
//            sortOptions.setPreferredSize(new Dimension(100,15));
//            sortOptions.setMinimumSize(new Dimension(100,20));
//            sortOptions.setMaximumSize(new Dimension(100,20));
//            headerPnl.add(sortOptions);
            add(headerPnl);
            add(Box.createRigidArea(new Dimension(0,10)));

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
            categoryTable.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    JTable table = (JTable) me.getSource();
                    Point p = me.getPoint();
                    setCurrentRow(table.rowAtPoint(p));
                }
            });
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

    }

    public void addRow(ArrayList<String> data) {
        model.add(data);
        revalidate();
        repaint();
    }

    public MyTableModel getModel() {
        return model;
    }

    public void setModel(MyTableModel model) {
        this.model = model;
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        MyTableModel model;
//        SongPanel.ActionPaneRenderer renderer;
//
//        if(e.getSource() == sortOptions){
//            JComboBox cb = (JComboBox) e.getSource();
//            String choice = (String) cb.getSelectedItem();
//            switch (choice){
//                case "Artist":
//                    tablePnl.removeAll();
//                    Collections.sort(data, new Comparator<ArrayList<String>>() {
//                        @Override
//                        public int compare(ArrayList<String> one, ArrayList<String> two) {
//                            return one.get(1).compareTo(two.get(1));
//                        }
//                    });
//                    model = new MyTableModel();
//                    for(int i=0 ; i<data.size() ; i++){
//                        model.add(data.get(i));
//                    }
//                    categoryTable = new JTable(model);
//                    renderer = new SongPanel.ActionPaneRenderer();
//                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
//                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
//                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);
//
//                    categoryTable.setForeground(Color.white);
//                    tableHeader = categoryTable.getTableHeader();
//                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//                    tableHeader.setForeground(Color.WHITE);
//                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//                    categoryTable.setOpaque(false);
//                    categoryTable.setRowHeight(30);
//
//                    categoryTable.addMouseListener(new MouseAdapter() {
//                        public void mousePressed(MouseEvent me) {
//                            JTable table = (JTable) me.getSource();
//                            Point p = me.getPoint();
//                            setCurrentRow(table.rowAtPoint(p));
//                        }
//                    });
//
//                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
//                    scroll = new JScrollPane(categoryTable);
//                    scroll.getViewport().setOpaque(false);
//                    scroll.setOpaque(false);
//                    scroll.setPreferredSize(new Dimension(50,60));
//                    tablePnl.add(scroll);
//                    System.out.println(1);
//                    break;
//
//                case "Album":
//                    tablePnl.removeAll();
//                    Collections.sort(data, new Comparator<ArrayList<String>>() {
//                        @Override
//                        public int compare(ArrayList<String> one, ArrayList<String> two) {
//                            return one.get(2).compareTo(two.get(2));
//                        }
//                    });
//                    model = new MyTableModel();
//                    for(int i=0 ; i<data.size() ; i++){
//                        model.add(data.get(i));
//                    }
//                    categoryTable = new JTable(model);
//                    renderer = new SongPanel.ActionPaneRenderer();
//                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
//                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
//                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);
//
//                    categoryTable.setForeground(Color.white);
//                    tableHeader = categoryTable.getTableHeader();
//                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//                    tableHeader.setForeground(Color.WHITE);
//                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//                    categoryTable.setOpaque(false);
//                    categoryTable.setRowHeight(30);
//
//                    categoryTable.addMouseListener(new MouseAdapter() {
//                        public void mousePressed(MouseEvent me) {
//                            JTable table = (JTable) me.getSource();
//                            Point p = me.getPoint();
//                            setCurrentRow(table.rowAtPoint(p));
//                        }
//                    });
//
//                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
//                    scroll = new JScrollPane(categoryTable);
//                    scroll.getViewport().setOpaque(false);
//                    scroll.setOpaque(false);
//                    scroll.setPreferredSize(new Dimension(50,60));
//                    tablePnl.add(scroll);
//                    System.out.println(2);
//                    break;
//                case "Year":
//                    tablePnl.removeAll();
//
//                    Collections.sort(data, new Comparator<ArrayList<String>>() {
//                        @Override
//                        public int compare(ArrayList<String> one, ArrayList<String> two) {
//                            return one.get(3).compareTo(two.get(3));
//                        }
//                    });
//                    model = new MyTableModel();
//                    for(int i=0 ; i<data.size() ; i++){
//                        model.add(data.get(i));
//                    }
//                    categoryTable = new JTable(model);
//
//                    renderer = new SongPanel.ActionPaneRenderer();
//                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
//                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
//                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);
//
//                    categoryTable.setForeground(Color.white);
//                    tableHeader = categoryTable.getTableHeader();
//                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//                    tableHeader.setForeground(Color.WHITE);
//                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//                    categoryTable.setOpaque(false);
//                    categoryTable.setRowHeight(30);
//
//                    categoryTable.addMouseListener(new MouseAdapter() {
//                        public void mousePressed(MouseEvent me) {
//                            JTable table = (JTable) me.getSource();
//                            Point p = me.getPoint();
//                            setCurrentRow(table.rowAtPoint(p));
//                        }
//                    });
//
//                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
//                    scroll = new JScrollPane(categoryTable);
//                    scroll.getViewport().setOpaque(false);
//                    scroll.setOpaque(false);
//                    scroll.setPreferredSize(new Dimension(50,60));
//                    tablePnl.add(scroll);
//                    System.out.println(3);
//                    break;
//                case "Genre":
//                    tablePnl.removeAll();
//                    Collections.sort(data, new Comparator<ArrayList<String>>() {
//                        @Override
//                        public int compare(ArrayList<String> one, ArrayList<String> two) {
//                            return one.get(4).compareTo(two.get(4));
//                        }
//                    });
//                    model = new MyTableModel();
//                    for(int i=0 ; i<data.size() ; i++){
//                        model.add(data.get(i));
//                    }
//                    categoryTable = new JTable(model);
//
//                    renderer = new SongPanel.ActionPaneRenderer();
//                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
//                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
//                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);
//
//                    categoryTable.setForeground(Color.white);
//                    tableHeader = categoryTable.getTableHeader();
//                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//                    tableHeader.setForeground(Color.WHITE);
//                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//                    categoryTable.setOpaque(false);
//                    categoryTable.setRowHeight(30);
//
//                    categoryTable.addMouseListener(new MouseAdapter() {
//                        public void mousePressed(MouseEvent me) {
//                            JTable table = (JTable) me.getSource();
//                            Point p = me.getPoint();
//                            setCurrentRow(table.rowAtPoint(p));
//                        }
//                    });
//
//                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
//                    scroll = new JScrollPane(categoryTable);
//                    scroll.getViewport().setOpaque(false);
//                    scroll.setOpaque(false);
//                    scroll.setPreferredSize(new Dimension(50,60));
//                    tablePnl.add(scroll);
//                    System.out.println(4);
//                    break;
//                case "None":
//                    tablePnl.removeAll();
//                    model = new MyTableModel();
//                    for(int i=0 ; i<data.size() ; i++){
//                        model.add(data.get(i));
//                    }
//                    categoryTable = new JTable(model);
//                    categoryTable = new JTable(model);
//
//                    renderer = new SongPanel.ActionPaneRenderer();
//                    categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
//                    categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
//                    categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);
//
//                    categoryTable.setForeground(Color.white);
//                    tableHeader = categoryTable.getTableHeader();
//                    tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//                    tableHeader.setForeground(Color.WHITE);
//                    tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//                    categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//                    categoryTable.setOpaque(false);
//                    categoryTable.setRowHeight(30);
//
//                    categoryTable.addMouseListener(new MouseAdapter() {
//                        public void mousePressed(MouseEvent me) {
//                            JTable table = (JTable) me.getSource();
//                            Point p = me.getPoint();
//                            setCurrentRow(table.rowAtPoint(p));
//                        }
//                    });
//
//                    categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
//                    scroll = new JScrollPane(categoryTable);
//                    scroll.getViewport().setOpaque(false);
//                    scroll.setOpaque(false);
//                    scroll.setPreferredSize(new Dimension(50,60));
//                    tablePnl.add(scroll);
//                    System.out.println(5);
//                    break;
//            }
//            revalidate();
//            repaint();
//        }
//    }


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
                    value = "Album";
                    break;
                case 3:
                    value = "Year";
                    break;
                case 4:
                    value = "Genre";
                    break;
//                case 5:
//                    value = "Artist";
//                    break;
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
//                case 5:
//                    value = String.class;
//                    break;
            }
            return value;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
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
//                case 5:
//                    value = obj.get(4);
//                    break;
            }
            return value;
        }


        public void setValueAt(Object aValue, int rowIndex, int columnIndex, String title, String album, String year, String genre) {
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
            else {
                ArrayList<String> currentSong = data.get(rowIndex);
                switch (columnIndex){
                    case 1:
                        currentSong.set(0,title);
                        break;
                    case 2:
                        currentSong.set(1,album);
                        break;
                    case 3:
                        currentSong.set(2,year);
                        break;
                    case 4:
                        currentSong.set(3,genre);
                        break;
                }
                fireTableCellUpdated(rowIndex, columnIndex);
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

        public void edit(ArrayList<String> value) {
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
        private JButton fav;
        private String state;

        public ActionsPane() {
            setOpaque(false);
            setLayout(new GridBagLayout());
            play = new JButton();
            play.setEnabled(false);
            play.setOpaque(false);
            play.setContentAreaFilled(false);
            play.setBorderPainted(false);
            play.setActionCommand("play");
//            fav = new JButton();
//            fav.setEnabled(false);
//            fav.setOpaque(false);
//            fav.setContentAreaFilled(false);
//            fav.setBorderPainted(false);
//            fav.setActionCommand("fav");
            addToPLaylist = new JButton();
            addToPLaylist.setEnabled(false);
            addToPLaylist.setOpaque(false);
            addToPLaylist.setContentAreaFilled(false);
            addToPLaylist.setBorderPainted(false);
            addToPLaylist.setActionCommand("add");
            delete = new JButton();
            delete.setEnabled(false);
            delete.setOpaque(false);
            delete.setContentAreaFilled(false);
            delete.setBorderPainted(false);
            delete.setActionCommand("delete");
            edit = new JButton();
            edit.setEnabled(false);
            edit.setOpaque(false);
            edit.setContentAreaFilled(false);
            edit.setBorderPainted(false);
            edit.setActionCommand("edit");

            try{
                URL resource = getClass().getClassLoader().getResource("images/plus.png");
                BufferedImage img = ImageIO.read(resource);
                addToPLaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/delete.png");
                img = ImageIO.read(resource);
                delete.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/edit.png");
                img = ImageIO.read(resource);
                edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
                img = ImageIO.read(resource);
                play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                resource = getClass().getClassLoader().getResource("images/favorite.png");
//                img = Paths.get(resource.toURI()).toFile();
//                fav.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
            catch(Exception e){

            }

            add(play);
            add(addToPLaylist);
            add(delete);
            add(edit);
            //add(fav);

            ActionListener playListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    controller.playSong(currentRow);
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
                    controller.openAddToPlaylistWindow(currentRow);
//                    AddToPlaylistWindow atpw = new AddToPlaylistWindow(controller.getMainController());
//                    if(atpw.isAdd())
//                        controller.addToPlaylist(data.get(currentRow), atpw.getPlaylist());
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
                    int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                            " to delete this song?", "Confirm Delete Song", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION)
                        controller.removeSong(currentRow);
//                    state = e.getActionCommand();
//                    System.out.println("State = " + state);
//                    controller.deleteSong(data.get(currentRow));
//                    model.remove(data.get(currentRow));
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
//                    EditSongProfileWindow espw = new EditSongProfileWindow(data.get(currentRow));
//                    while (!espw.isEdited());
                    controller.openEditSongProfileWindow(currentRow, data.get(currentRow));
//                    if (espw.isEdited()){

//                    }
                    revalidate();
                    repaint();
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
            play.addActionListener(listener);
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
