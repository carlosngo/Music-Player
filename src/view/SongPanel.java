package view;

import controller.*;
import model.*;
import util.ImageResizer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;

public class SongPanel extends JPanel implements ActionListener{
    SongController controller;
    private JLabel headerName;
    private String header;
    private JScrollPane scroll;
    private JComboBox sortOptions;
    private JPanel tablePnl;
    private JTable categoryTable;
    private JTableHeader tableHeader;
    private int currentRow;
    private MyTableModel model;
    private int objID;
    private ArrayList<ArrayList<String>> biodata;
    private ArrayList<ArrayList<String>> data;
    private ArrayList<Song> songs;

    public SongPanel(SongController controller, String header, Object obj, ArrayList<Song> songs) {
        //SongController controller, String header, ArrayList<ArrayList<String>> _data
        this.controller = controller;
        this.songs = songs;
        if( obj instanceof Playlist){
            Playlist playlist = (Playlist) obj;
            objID = playlist.getPlaylistId();
        }else if( obj instanceof Album){
            Album album = (Album) obj;
            objID = album.getAlbumId();
        }else if( obj instanceof Artist){
            Artist artist = (Artist) obj;
            objID = artist.getArtistId();
        }
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> _dataContent;
        for(Song s : songs){
            _dataContent = new ArrayList<String>();
            _dataContent.add(s.getName());
            _dataContent.add(s.getAlbum().getName());
            _dataContent.add(s.getArtist().getName());
            _dataContent.add(""+s.getYear());
            _dataContent.add(s.getGenre());
            _dataContent.add(""+s.getDateCreated());
            _dataContent.add(""+s.getPlayTime());
            data.add(_dataContent);
        }

        biodata = new ArrayList<ArrayList<String>>();
        ArrayList<String> biodataContent;
        for(Song s : songs){
            biodataContent = new ArrayList<String>();
            biodataContent.add(s.getName());
            biodataContent.add(s.getAlbum().getName());
            biodataContent.add(s.getArtist().getName());
            biodataContent.add(""+s.getYear());
            biodataContent.add(s.getGenre());
            biodataContent.add(""+s.getDateCreated());
            biodataContent.add(""+s.getPlayTime());
            biodataContent.add(""+ s.getSongId()); //index 7
            biodata.add(biodataContent);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        if(data.size()==0){
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
            model = new MyTableModel();

            Collections.sort(data, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> one, ArrayList<String> two) {
                    return one.get(0).compareTo(two.get(0));
                }
            });

            for(int i=0 ; i<data.size() ; i++){
                model.add(data.get(i));
            }
            categoryTable = new JTable(model){
                public Component prepareRenderer(
                        TableCellRenderer renderer, int row, int column)
                {
                    Component c = super.prepareRenderer(renderer, row, column);

                    if (!isRowSelected(row))
                        c.setBackground(Color.BLACK);

                    return c;
                }
            };
            categoryTable.setShowGrid(false);
            categoryTable.setIntercellSpacing(new Dimension(0, 0));
            //categoryTable.setShowVerticalLines(false);
            //categoryTable.setShowHorizontalLines(false);
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
            //Border border = BorderFactory.createLineBorder(new Color(65,15,225), 3, false);
            //tableHeader.setBorder(border);
            tableHeader.setBackground(new Color(65,15,225)); // change the Background color
            tableHeader.setForeground(Color.WHITE);
            tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
//        categoryTable.setPreferredSize(new Dimension(50,150));
//        categoryTable.setMinimumSize(new Dimension(50,150));
//        categoryTable.setMaximumSize(new Dimension(50,150));
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

    public void deleteRow(int index) {
        model.remove(index);
        revalidate();
        repaint();
    }

    public void editRow(int index, ArrayList<String> data) {
        model.edit(index, data);
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

    public void update(ArrayList<ArrayList<String>> biodata, ArrayList<ArrayList<String>> data, int sortBaseIndex){
        MyTableModel model;
        SongPanel.ActionPaneRenderer renderer;
        tablePnl.removeAll();
        Collections.sort(data, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> one, ArrayList<String> two) {
                return one.get(sortBaseIndex).compareTo(two.get(sortBaseIndex));
            }
        });
        Collections.sort(biodata, new Comparator<ArrayList<String>>() {
            @Override
            public int compare(ArrayList<String> one, ArrayList<String> two) {
                return one.get(sortBaseIndex).compareTo(two.get(sortBaseIndex));
            }
        });
        model = new MyTableModel();
        for(int i=0 ; i<data.size() ; i++){
            model.add(data.get(i));
        }
        categoryTable = new JTable(model)
        {
            public Component prepareRenderer(
                    TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);

                if (isRowSelected(row))
                    c.setBackground(Color.LIGHT_GRAY);
                if (!isRowSelected(row))
                    c.setBackground(Color.BLACK);

                return c;
            }
        };
        categoryTable.setShowGrid(false);
        renderer = new SongPanel.ActionPaneRenderer();
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

        categoryTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                setCurrentRow(table.rowAtPoint(p));
            }
        });

        categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(false); }});
        scroll = new JScrollPane(categoryTable);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,60));
        tablePnl.add(scroll);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sortOptions){
            JComboBox cb = (JComboBox) e.getSource();
            String choice = (String) cb.getSelectedItem();
            switch (choice){
                case "Artist":
                    update(biodata, data, 1);
                    System.out.println(1);
                    break;

                case "Album":
                    update(biodata, data, 2);
                    System.out.println(2);
                    break;
                case "Year":
                    update(biodata, data, 3);
                    System.out.println(3);
                    break;
                case "Genre":
                    update(biodata, data, 4);
                    System.out.println(4);
                    break;
                case "None":
                    update(biodata, data, 0);
                    System.out.println(0);
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
//                case 8:
//                    value = "Kebab";
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
                case 5:
                    value = String.class;
                    break;
                case 6:
                    value = String.class;
                    break;
                case 7:
                    value = String.class;
                    break;
//                case 8:
//                    value = JPanel.class;
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
            return 8;
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
//                case 8:
//                    break;
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

        public void remove(int index) {
            data.remove(index);
            fireTableRowsInserted(index, index);
        }

        public void edit(int index, ArrayList<String> value) {
//            int startIndex = data.indexOf(value);
//            System.out.println("startIndex = " + startIndex);
//            data.remove(value);
            data.set(index, value);
            fireTableRowsInserted(index, index);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }
    }

    public class ActionsPane extends JPanel {

        private JButton play, fav;
        private JButton addToPLaylist, addToQueue;
        private JButton delete;
        private JButton edit;
        private JButton kebab;
        private String state;
        private JPopupMenu settingsMenu = new JPopupMenu();

        public ActionsPane() {
            setOpaque(false);
            setLayout(new GridBagLayout());
            play = new JButton();
            play.setEnabled(false);
            play.setOpaque(false);
            play.setContentAreaFilled(false);
            play.setBorderPainted(false);
            play.setActionCommand("play");
            fav = new JButton();
            fav.setEnabled(false);
            fav.setOpaque(false);
            fav.setContentAreaFilled(false);
            fav.setBorderPainted(false);
            fav.setActionCommand("fav");
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
            kebab = new JButton();
            kebab.setEnabled(false);
            kebab.setOpaque(false);
            kebab.setContentAreaFilled(false);
            kebab.setBorderPainted(false);
            kebab.setActionCommand("settings");


            try{
                URL resource = getClass().getClassLoader().getResource("images/cyanPlus.png");
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
                resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
                img = ImageIO.read(resource);
                addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/cyanFavSongs.png");
                img = ImageIO.read(resource);
                fav.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/cyanKebab.png");
                img = ImageIO.read(resource);
                kebab.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
            catch(Exception e){

            }

              add(play);
//              add(fav);
              add(kebab);
//            add(addToQueue);
//            add(addToPLaylist);
//            add(delete);
//            add(edit);

            ActionListener playListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    state = e.getActionCommand();
                    System.out.println("State = " + state);
                    controller.playSong(currentRow, currentRow);
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

//            ActionListener favListener = new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    state = e.getActionCommand();
//                    System.out.println("State = " + state);
//                    controller.playSong(currentRow);
//                }
//            };
//            fav.addActionListener(favListener);
//
//            fav.addMouseListener(new MouseAdapter() {
//                public void mouseEntered(MouseEvent e) {
//                    fav.setEnabled(true);
//                }
//                public void mouseExited(MouseEvent e) {
//                    fav.setEnabled(false);
//                }
//            });

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
                    Song songForEdit = null;
                    for(Song song : songs){
                        if(song.getSongId() == Integer.parseInt(biodata.get(currentRow-1).get(7))) songForEdit = song;
                    }
                    controller.openEditSongProfileWindow(songForEdit);
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

            ActionListener kebabListener = new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
//                    System.out.println("Clicked kebab");
                    PopupMenu();
                    settingsMenu.show(kebab, kebab.getBounds().x - (kebab.getBounds().width), kebab.getBounds().y
                            + (kebab.getBounds().height));
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
            play.addActionListener(listener);
            addToQueue.addActionListener(listener);
            addToPLaylist.addActionListener(listener);
            delete.addActionListener(listener);
            edit.addActionListener(listener);
            kebab.addActionListener(listener);
        }

        public String getState() {
            return state;
        }

        private void PopupMenu(){

            settingsMenu = new JPopupMenu();
            JMenuItem addToQueue = new JMenuItem("Add to queue");
            addToQueue.setActionCommand("queue");

            JMenuItem add_to_playlist = new JMenuItem("Add to playlist");
            add_to_playlist.setActionCommand("add");

            JMenuItem removeFromPlaylist = new JMenuItem("Remove from playlist");
            removeFromPlaylist.setActionCommand("removeFromPlaylist");

            JMenuItem removeFromAlbum = new JMenuItem("Remove from album");
            removeFromPlaylist.setActionCommand("removeFromAlbum");

            JMenuItem edit = new JMenuItem("Edit");
            edit.setActionCommand("edit");

            JMenuItem delete = new JMenuItem("Delete");
            delete.setActionCommand("delete");

//            JMenuItem follow = new JMenuItem("Follow");
//            follow.setActionCommand("follow");

            MenuItemListener menuItemListener = new MenuItemListener();

            addToQueue.addActionListener(menuItemListener);
            add_to_playlist.addActionListener(menuItemListener);
            removeFromPlaylist.addActionListener(menuItemListener);
            removeFromAlbum.addActionListener(menuItemListener);
            edit.addActionListener(menuItemListener);
            delete.addActionListener(menuItemListener);
            //follow.addActionListener(menuItemListener);

            settingsMenu.add(addToQueue);
            settingsMenu.add(add_to_playlist);
            String[] split = headerName.getText().split(" ");
            if (split.length > 1 && split[1].equals("IN")){
                settingsMenu.add(removeFromPlaylist);
                //settingsMenu.add(removeFromAlbum);
            }
            //settingsMenu.add(removeFromAlbum);
            settingsMenu.add(edit);
            settingsMenu.add(delete);
            //settingsMenu.add(follow);

//        mainFrame.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//                editMenu.show(mainFrame, e.getX(), e.getY());
//            }
//        });
//        mainFrame.add(editMenu);
//        mainFrame.setVisible(true);

            try{
                URL resource = getClass().getClassLoader().getResource("images/cyanPlus.png");
                BufferedImage img = ImageIO.read(resource);
                add_to_playlist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/delete.png");
                img = ImageIO.read(resource);
                delete.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/edit.png");
                img = ImageIO.read(resource);
                edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/cyanQueueIcon.png");
                img = ImageIO.read(resource);
                addToQueue.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/cyanRemoveFromPlaylist.png");
                img = ImageIO.read(resource);
                removeFromPlaylist.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/violetRemoveFromAlbum.png");
                img = ImageIO.read(resource);
                removeFromAlbum.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
//                resource = getClass().getClassLoader().getResource("images/songFollow.png");
//                img = ImageIO.read(resource);
//                follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
            }
            catch(Exception e){

            }

        }
        class MenuItemListener implements ActionListener {
            int choice;
            public void actionPerformed(ActionEvent e) {
                //statusLabel.setText(e.getActionCommand() + " MenuItem clicked.");
                switch (e.getActionCommand()){
                    case "queue":
                        controller.addToQueue(currentRow);
                        break;
                    case "add":
                        controller.openAddToPlaylistWindow(currentRow);
                        break;
                    case "removeFromPlaylist":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to remove this song?", "Confirm Remove Song from Playlist", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
//                            System.out.println( headerName.getText().substring(9, headerName.getText().length() - 1).toLowerCase());
                            //int songId, int playlistId
                            controller.removeFromPlaylist(Integer.parseInt(biodata.get(currentRow-1).get(7)), objID);
                        }
                        break;
                    case "removeFromAlbum":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to remove this song?", "Confirm Remove Song from Album", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
//                            System.out.println( headerName.getText().substring(9, headerName.getText().length() - 1).toLowerCase());
                            controller.removeFromAlbum(Integer.parseInt(biodata.get(currentRow-1).get(7)));
                        }
                        break;
                    case "edit":
                        Song songForEdit = null;
                        for(Song song : songs){
                            if(song.getSongId() == Integer.parseInt(biodata.get(currentRow-1).get(7))) songForEdit = song;
                        }
                        controller.openEditSongProfileWindow(songForEdit);
                        break;
                    case "delete":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to delete this song?", "Confirm Delete Song", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION)
                            controller.removeSong(currentRow);
                        break;
//                    case "follow":
//                        //follow
//                        break;
                }
            }
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
