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
            add(headerPnl);
            add(Box.createRigidArea(new Dimension(0,10)));

            tablePnl = new JPanel();
            tablePnl.setLayout(new BoxLayout(tablePnl, BoxLayout.Y_AXIS));
            tablePnl.setOpaque(false);
            model = new MyTableModel();
            for(int i=0 ; i<_data.size() ; i++){
                model.add(_data.get(i));
            }
            categoryTable = new JTable(model);
            SongPanel.ActionPaneRenderer renderer = new SongPanel.ActionPaneRenderer();
            categoryTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
            categoryTable.getColumnModel().getColumn(0).setCellEditor(new SongPanel.ActionEditor());
            categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);


//            SongPanel.ActionPaneRenderer2 kebabRenderer = new SongPanel.ActionPaneRenderer2();
//            categoryTable.getColumnModel().getColumn(8).setCellRenderer(kebabRenderer);
//            categoryTable.getColumnModel().getColumn(8).setCellEditor(new SongPanel.ActionEditor());
//            categoryTable.setRowHeight(renderer.getTableCellRendererComponent(categoryTable, null, true, true, 0, 0).getPreferredSize().height);


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
                    value = "Artist";
                    break;
                case 4:
                    value = "Year";
                    break;
                case 5:
                    value = "Genre";
                    break;
                case 6:
                    value = "Date Uploaded";
                    break;
                case 7:
                    value = "Times Played";
                    break;
                case 8:
                    value = "Kebab";
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
                case 6:
                    value = String.class;
                    break;
                case 7:
                    value = String.class;
                    break;
                case 8:
                    value = JPanel.class;
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
            return 9;
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
                case 6:
                    value = obj.get(5);
                    break;
                case 7:
                    value = obj.get(6);
                    break;
                case 8:
                    break;
            }
            return value;
        }


        public void setValueAt(Object aValue, int rowIndex, int columnIndex, String title, String album, String year, String genre, String dateUploaded, String timesPlayed) {
            if (columnIndex == 6) {

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
                        currentSong.set(2,album);
                        break;
                    case 4:
                        currentSong.set(3,year);
                        break;
                    case 5:
                        currentSong.set(4,genre);
                        break;
                    case 6:
                        currentSong.set(5,dateUploaded);
                        break;
                    case 7:
                        currentSong.set(6,timesPlayed);
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
        private JButton addToPLaylist, addToQueue;
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
            addToQueue = new JButton();
            addToQueue.setEnabled(false);
            addToQueue.setOpaque(false);
            addToQueue.setContentAreaFilled(false);
            addToQueue.setBorderPainted(false);
            addToQueue.setActionCommand("queue");
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
                URL resource = getClass().getClassLoader().getResource("images/cyanPlus.png");
                BufferedImage img = ImageIO.read(resource);
                addToPLaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));
                resource = getClass().getClassLoader().getResource("images/delete.png");
                img = ImageIO.read(resource);
                delete.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));
                resource = getClass().getClassLoader().getResource("images/edit.png");
                img = ImageIO.read(resource);
                edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));
                resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
                img = ImageIO.read(resource);
                play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));
                resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
                img = ImageIO.read(resource);
                addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));
            }
            catch(Exception e){

            }

            add(play);
            add(addToQueue);
            add(addToPLaylist);
            add(delete);
            add(edit);

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

            ActionListener addToQueueListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    controller.addToQueue(currentRow);
                }
            };
            addToQueue.addActionListener(addToQueueListener);

            addToQueue.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    addToQueue.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) { addToQueue.setEnabled(false); }
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
            addToQueue.addActionListener(listener);
            addToPLaylist.addActionListener(listener);
            delete.addActionListener(listener);
            edit.addActionListener(listener);
        }

        public String getState() {
            return state;
        }
    }

    public class ActionsPane2 extends JPanel {

        private JButton kebab;
        private String state;

        public ActionsPane2() {
            setOpaque(false);
            setLayout(new GridBagLayout());
            kebab = new JButton();
            kebab.setEnabled(false);
            kebab.setOpaque(false);
            kebab.setContentAreaFilled(false);
            kebab.setBorderPainted(false);
            kebab.setActionCommand("actions");

            try{
                URL resource = getClass().getClassLoader().getResource("images/cyanPlus.png");
                BufferedImage img = ImageIO.read(resource);
                kebab.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 10, 10)));;
            }
            catch(Exception e){

            }

            add(kebab);

            ActionListener kebabListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    //open JPopup here (delete, edit, add to playlist)
                }
            };
            kebab.addActionListener(kebabListener);

            kebab.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    kebab.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    kebab.setEnabled(false);
                }
            });
        }

        public void addActionListener(ActionListener listener) {
            kebab.addActionListener(listener);
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

    public class ActionPaneRenderer2 extends DefaultTableCellRenderer {

        private SongPanel.ActionsPane2 actionsPane;

        public ActionPaneRenderer2() {
            actionsPane = new SongPanel.ActionsPane2();
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

    public class ActionEditor2 extends AbstractCellEditor implements TableCellEditor {

        private SongPanel.ActionsPane2 actionsPane;

        public ActionEditor2() {
            actionsPane = new SongPanel.ActionsPane2();
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
