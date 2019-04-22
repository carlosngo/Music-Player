package view;

import controller.*;
import model.*;

import net.Client;
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
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private int objID;
    private ArrayList<ArrayList<String>> biodata;
    private ArrayList<ArrayList<String>> data;
    private ArrayList<Song> songs;
    private Object songContainer;

    public SongPanel(SongController controller, String header, Object obj, ArrayList<Song> songs) {
        try {
            this.controller = controller;
            this.songs = songs;
            songContainer = obj;
            if (obj instanceof Playlist) {
                Playlist playlist = (Playlist) obj;
                objID = playlist.getPlaylistId();
            } else if (obj instanceof Album) {
                Album album = (Album) obj;
                objID = album.getAlbumId();
            } else if (obj instanceof Artist) {
                Artist artist = (Artist) obj;
                objID = artist.getArtistId();
            }
            data = new ArrayList<ArrayList<String>>();
            ArrayList<String> _dataContent;
            for (int i = 0; i < songs.size(); i++) {
                Song s = songs.get(i);
                System.out.println(s);
                _dataContent = new ArrayList<String>();
                _dataContent.add(s.getName());
                _dataContent.add(s.getAlbum().getName());
                _dataContent.add(s.getArtist().getName());
                if (s.getYear() == 0) _dataContent.add("");
                else _dataContent.add("" + s.getYear());
                _dataContent.add(s.getGenre());
                _dataContent.add("" + s.getDateCreated()); //5
                _dataContent.add("" + s.getPlayTime());
                data.add(_dataContent);
            }

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < data.get(i).size(); j++) {
                    System.out.print(data.get(i).get(j) + " ");
                }
                System.out.println();
            }
            biodata = new ArrayList<ArrayList<String>>();
            ArrayList<String> biodataContent;
            for (Song s : songs) {
                biodataContent = new ArrayList<String>();
                biodataContent.add(s.getName());
                biodataContent.add(s.getAlbum().getName());
                biodataContent.add(s.getArtist().getName());
                if (s.getYear() == 0) biodataContent.add("");
                else biodataContent.add("" + s.getYear());
                biodataContent.add(s.getGenre());
                biodataContent.add("" + s.getDateCreated()); //5
                biodataContent.add("" + s.getPlayTime());
                biodataContent.add("" + s.getSongId()); //8
                biodata.add(biodataContent);
            }

            //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            //setAlignmentX(Component.LEFT_ALIGNMENT);
            setLayout(new BorderLayout());
            setOpaque(false);

            if (data.size() == 0) {
                JPanel headerPnl = new JPanel();
                headerPnl.setLayout(new BorderLayout());
                headerPnl.setOpaque(false);
                headerName = new JLabel();
                if (obj instanceof User) headerName.setText("Songs followed by " + ((User) obj).getAccount().getUserName());
                else if (obj instanceof Artist) headerName.setText("Songs created by " + ((Artist) obj).getAccount().getUserName());
                else if (obj instanceof Playlist) headerName.setText("Songs in " + ((Playlist) obj).getName());
                else if (obj instanceof Album) headerName.setText("Songs in " + ((Album) obj).getName());


                headerName.setFont(new Font("Arial", Font.BOLD, 26));
                headerName.setForeground(Color.white);
                //headerPnl.add(headerName, BorderLayout.WEST);
                //headerPnl.add(Box.createRigidArea(new Dimension(230,0)));
                headerPnl.add(headerName, BorderLayout.NORTH);
                JLabel blankMessage = new JLabel("No songs to show.");
                blankMessage.setForeground(Color.white);
                blankMessage.setFont(new Font("Arial", Font.BOLD, 22));
                headerPnl.add(blankMessage, BorderLayout.CENTER);
                add(headerPnl, BorderLayout.NORTH);
            } else {
                JPanel headerPnl = new JPanel();
                headerPnl.setLayout(new BorderLayout());
                headerPnl.setOpaque(false);
                headerName = new JLabel(header.toUpperCase());
                headerName.setFont(new Font("Arial", Font.BOLD, 26));
                headerName.setForeground(Color.white);
                headerPnl.add(headerName, BorderLayout.WEST);

                String[] sort = {"(Sort By)", "Title", "Artist", "Album", "Genre", "Year", "Date Uploaded", "None"};
                sortOptions = new JComboBox(sort);
                sortOptions.setForeground(SystemColor.windowText);
                sortOptions.addActionListener(this);
                sortOptions.setFont(new Font("Arial", Font.PLAIN, 12));
                sortOptions.setPreferredSize(new Dimension(100, 15));
                sortOptions.setMinimumSize(new Dimension(100, 20));
                sortOptions.setMaximumSize(new Dimension(100, 20));
                headerPnl.add(sortOptions, BorderLayout.EAST);
                add(headerPnl, BorderLayout.NORTH);

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

                Collections.sort(biodata, new Comparator<ArrayList<String>>() {
                    @Override
                    public int compare(ArrayList<String> one, ArrayList<String> two) {
                        return one.get(0).compareTo(two.get(0));
                    }
                });

                for (int i = 0; i < data.size(); i++) {
                    model.add(data.get(i));
                }
                categoryTable = new JTable(model) {
                    public Component prepareRenderer(
                            TableCellRenderer renderer, int row, int column) {
                        Component c = super.prepareRenderer(renderer, row, column);
                        if (isRowSelected(row))
                            c.setBackground(Color.LIGHT_GRAY);
                        else
                            c.setBackground(Color.BLACK);
                        return c;
                    }
                };
                categoryTable.setRowSelectionAllowed(true);
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
                tableHeader.setBackground(new Color(65, 15, 225)); // change the Background color
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
                    setOpaque(true);
                }});
                //((DefaultTableCellRenderer)categoryTable.getDefaultRenderer(Object.class)).setOpaque(false);
                //categoryTable.setShowGrid(false);
                scroll = new JScrollPane(categoryTable);
                scroll.getViewport().setOpaque(false);
                scroll.setOpaque(false);
                scroll.setPreferredSize(new Dimension(1200, 300));
                scroll.setMaximumSize(new Dimension(1200, 300));
                scroll.setMinimumSize(new Dimension(1200, 300));
                tablePnl.add(scroll);
                add(tablePnl, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    public void update(ArrayList<ArrayList<String>> biodata, ArrayList<ArrayList<String>> data, int sortBaseIndex){
        MyTableModel model;
        SongPanel.ActionPaneRenderer renderer;
        tablePnl.removeAll();
        if(sortBaseIndex == 5){
            Collections.sort(data, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> one, ArrayList<String> two) {
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(one.get(5));
                        date2 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(two.get(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);
                }
            });
            Collections.sort(biodata, new Comparator<ArrayList<String>>() {
                @Override
                public int compare(ArrayList<String> one, ArrayList<String> two) {
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(one.get(5));
                        date2 = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(two.get(5));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    return date1.compareTo(date2);            }
            });
        }else{
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
        }

        model = new MyTableModel();
        for(int i=0 ; i<data.size() ; i++){
            model.add(data.get(i));
        }
        categoryTable = new JTable(model){
            public Component prepareRenderer(
                    TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row))
                    c.setBackground(Color.LIGHT_GRAY);
                else
                    c.setBackground(Color.BLACK);
                return c;
            }
        };
        categoryTable.setBackground(Color.BLACK);
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
        categoryTable.setOpaque(true);
        categoryTable.setRowHeight(30);

        categoryTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                setCurrentRow(table.rowAtPoint(p));
            }
        });

        categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{ setOpaque(true); }});
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
                case "Title":
                    update(biodata, data, 0);
                    break;
                case "Artist":
                    update(biodata, data, 2);
                    break;
                case "Album":
                    update(biodata, data, 1);
                    break;
                case "Year":
                    update(biodata, data, 3);
                    break;
                case "Genre":
                    update(biodata, data, 4);
                    break;
                case "Date Uploaded":
                    update(biodata, data, 5);
                    break;
                case "None":
                    update(biodata, data, 0);
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
//            for (int i = 0; i < obj.size(); i++) {
//                System.out.print(obj.get(i) + " ");
//            }
//            System.out.println();
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
            data.set(index, value);
            fireTableRowsInserted(index, index);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 0;
        }
    }

    public class ActionsPane extends JPanel {

        private JButton play;
        private JButton kebab;
        private JButton follow;
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
            follow = new JButton();
            follow.setEnabled(false);
            follow.setOpaque(false);
            follow.setContentAreaFilled(false);
            follow.setBorderPainted(false);
            follow.setActionCommand("follow");
            kebab = new JButton();
            kebab.setEnabled(false);
            kebab.setOpaque(false);
            kebab.setContentAreaFilled(false);
            kebab.setBorderPainted(false);
            kebab.setActionCommand("settings");

            try{
                URL resource = getClass().getClassLoader().getResource("images/imgPlayBtn.png");
                BufferedImage img = ImageIO.read(resource);
                play.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                resource = getClass().getClassLoader().getResource("images/cyanKebab.png");
                img = ImageIO.read(resource);
                kebab.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                if (controller.isFollowingSong(Integer.parseInt(biodata.get(currentRow).get(7)))) {
                    resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                    img = ImageIO.read(resource);
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } else {
                    resource = getClass().getClassLoader().getResource("images/follow.png");
                    img = ImageIO.read(resource);
                    follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            add(play);
            add(follow);
            add(kebab);
            if(songs.get(currentRow).getArtist().getAccount().getId() == controller.getMainController().getAc().getUser().getAccount().getId())
            follow.setVisible(false);

            ActionListener playListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    System.out.println("Playing song at row " + currentRow + " with ID " + songs.get(currentRow).getSongId());
                    controller.playSong(currentRow, Integer.parseInt(biodata.get(currentRow).get(7)));
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

            follow.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    follow.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    follow.setEnabled(false);
                }
            });

            ActionListener followListener = new ActionListener() {
                public void actionPerformed(ActionEvent ev) {
                    Song song = Client.getInstance().getSong(Integer.parseInt(biodata.get(currentRow).get(7)));
                    if (controller.isFollowingSong(song.getSongId())) {
                        controller.unfollowSong(song);
                        try {
                            URL resource = getClass().getClassLoader().getResource("images/follow.png");
                            BufferedImage img = ImageIO.read(resource);
                            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        controller.followSong(song);
                        try {
                            URL resource = getClass().getClassLoader().getResource("images/cyanFollow.png");
                            BufferedImage img = ImageIO.read(resource);
                            follow.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            follow.addActionListener(followListener);

            kebab.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    kebab.setEnabled(true);
                }
                public void mouseExited(MouseEvent e) {
                    kebab.setEnabled(false);
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
            follow.addActionListener(listener);
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
            removeFromAlbum.setActionCommand("removeFromAlbum");

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
            if ((songContainer instanceof Playlist) && ((Playlist) songContainer).getAccount().getId() == controller.getMainController().getAc().getUser().getAccount().getId())

                settingsMenu.add(removeFromPlaylist);
            //settingsMenu.add(removeFromAlbum);
            if(songs.get(currentRow).getArtist().getAccount().getId() == controller.getMainController().getAc().getUser().getAccount().getId()){
               if(songContainer instanceof Album)
                  settingsMenu.add(removeFromAlbum);
               if(songContainer instanceof Playlist)
                  settingsMenu.add(removeFromPlaylist);
               settingsMenu.add(edit);
               settingsMenu.add(delete);
            }
            
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
                        controller.addToQueue(Integer.parseInt(biodata.get(currentRow).get(7)));
                        break;
                    case "add":
                        controller.openAddToPlaylistWindow(Integer.parseInt(biodata.get(currentRow).get(7)));
                        break;
                    case "removeFromPlaylist":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to remove this song?", "Confirm Remove Song from Playlist", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
//                            System.out.println( headerName.getText().substring(9, headerName.getText().length() - 1).toLowerCase());
                            //int songId, int playlistId
                            controller.removeFromPlaylist(currentRow, Integer.parseInt(biodata.get(currentRow).get(7)), objID);
                        }
                        break;
                    case "removeFromAlbum":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to remove this song?", "Confirm Remove Song from Album", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
//                            System.out.println( headerName.getText().substring(9, headerName.getText().length() - 1).toLowerCase());
                            controller.removeFromAlbum(Integer.parseInt(biodata.get(currentRow).get(7)));
                        }
                        break;
                    case "edit":
                        Song songForEdit = null;
                        for(Song song : songs){
                            if(song.getSongId() == Integer.parseInt(biodata.get(currentRow).get(7))) songForEdit = song;
//                            System.out.println(song.toString());
                        }
                        controller.openEditSongProfileWindow(songForEdit, currentRow);
                        break;
                    case "delete":
                        choice = JOptionPane.showConfirmDialog(null, "Are you sure you want" +
                                " to delete this song?", "Confirm Delete Song", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION)
                            controller.removeSong(currentRow, Integer.parseInt(biodata.get(currentRow).get(7)));
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
                //table.setBackground(Color.LIGHT_GRAY);
                actionsPane.setBackground(table.getSelectionBackground());
            } else {
                //table.setBackground(Color.BLACK);
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
