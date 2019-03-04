package View;

import javax.swing.*;
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
    private JPanel block;
    private MainScreen ms;

    //needs a header name as string and an arraylist of arraylist as parameter input for diaplaying the list of [category]
    public CategoryPanel(MainScreen mainscreen, String category, ArrayList<String> subCategoryList){
        ms = mainscreen;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);

        add(Box.createRigidArea(new Dimension(0,7)));
//        JPanel headerPnl = new JPanel();
//        headerPnl.setLayout(new BoxLayout(headerPnl, BoxLayout.Y_AXIS));
//        headerPnl.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerName = new JLabel((category+"s").toUpperCase());
        headerName.setFont(new Font("Arial", Font.BOLD, 26));
        headerName.setForeground(Color.white);
        add(headerName);
//        headerPnl.add(headerName);
//        add(headerPnl);
        add(Box.createRigidArea(new Dimension(0,10)));

//        String[] rowheader = {header, "Number of songs"};
//        String[][] rows = new String[rowsInput.size()][2];
//        for(int i=0;i<rowsInput.size();i++){
//            for(int j=0;j<2;j++){
//                rows[i][j] = rowsInput.get(i).get(j).toString();
//            }
//        }
//        JTable categoryTable = new JTable(rows, rowheader);
//        categoryTable.setForeground(Color.white);
//        JTableHeader tableHeader = categoryTable.getTableHeader();
//        tableHeader.setBackground(new Color(65,15,225)); // change the Background color
//        tableHeader.setForeground(Color.WHITE);
//        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
////        categoryTable.setPreferredSize(new Dimension(50,100));
////        categoryTable.setMinimumSize(new Dimension(50,100));
////        categoryTable.setMaximumSize(new Dimension(50,100));
//        categoryTable.setFont(new Font("Arial", Font.BOLD, 14));
//        categoryTable.setOpaque(false);
//        categoryTable.setRowHeight(30);
//        //categoryTable.getColumn(0).setPreferredWidth(50);
//        //categoryTable.getColumn(1).setPreferredWidth(50);
//        categoryTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
//            setOpaque(false);
//        }});
//        //((DefaultTableCellRenderer)categoryTable.getDefaultRenderer(Object.class)).setOpaque(false);
//        //categoryTable.setShowGrid(false);
//        scroll = new JScrollPane(categoryTable);
//        scroll.getViewport().setOpaque(false);
//        scroll.setOpaque(false);
//        scroll.setPreferredSize(new Dimension(50,100));
//        add(scroll);

        block = new JPanel();
        block.setLayout(new GridBagLayout());
        block.setOpaque(false);
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        scroll = new JScrollPane(block);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,100));
        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if(subCategoryList.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No " + category.toLowerCase() + " to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(int i=0; i<subCategoryList.size(); i++){
                JButton subOptionButton = new JButton();
                subOptionButton.setOpaque(false);
                subOptionButton.setContentAreaFilled(false);
                subOptionButton.setBorderPainted(false);
                subOptionButton.setForeground(Color.white);
                JButton remove = new JButton();
                //remove.setVisible(false);
                remove.setOpaque(false);
                remove.setContentAreaFilled(false);
                remove.setBorderPainted(false);
                JButton edit = new JButton();
                //edit.setVisible(false);
                edit.setOpaque(false);
                edit.setContentAreaFilled(false);
                edit.setBorderPainted(false);
                JButton settings = new JButton();
                settings.setOpaque(false);
                settings.setContentAreaFilled(false);
                settings.setBorderPainted(false);

                try {
                    URL resource = getClass().getClassLoader().getResource("images/delete.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    resource = getClass().getClassLoader().getResource("images/edit.png");
                    img = Paths.get(resource.toURI()).toFile();
                    edit.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

//                block.addMouseListener(new MouseAdapter() {
//                    public void mouseEntered(MouseEvent e) {
//                        remove.setVisible(true);
//                        edit.setVisible(true);
//                    }
//                    public void mouseExited(MouseEvent e) {
//                        remove.setVisible(false);
//                        edit.setVisible(false);
//                    }
//                });

                String chosenSubOption = subCategoryList.get(i);
                subOptionButton.setText(chosenSubOption);
                subOptionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //test 2D arraylist only
                        int var = 0;
                        ArrayList<Object> miniRow = new ArrayList<Object>();
                        ArrayList<ArrayList<Object>> rowsInput = new ArrayList<ArrayList<Object>>();
                        for(int i=0;i<20;i++){
                            for(int j=0;j<5;j++){
                                miniRow.add(var);
                                var++;
                            }
                            rowsInput.add(miniRow);
                            miniRow = new ArrayList<Object>();
                        }
                        ms.getDisplayPanel().removeAll();
                        ms.getDisplayPanel().add(new SongPanel(category + " Songs", rowsInput));
                        //System.out.println(12345);
                        revalidate();
                        repaint();
                    }
                });
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to remove this " + category.toLowerCase() + "?",
                                "Remove " + category.toLowerCase(), JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            System.out.println(category.toLowerCase() + "removed."); //test
                        }
                    }
                });
                edit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EditCategoryWindow ecw = new EditCategoryWindow(category);
//                        if(ecw.getIsChanged()){
//                            subOptionButton.setText(ecw.getNewName());
//                            ecw.dispose();
//                        }
                        if((ecw.getNewName() != null) && (ecw.getIsChanged()))
                            subOptionButton.setText(ecw.getNewName());
                    }
                });
                cons.insets = new Insets(5, 10, 0, 0);
                cons.gridx = 0;
                cons.gridy = i;
                cons.gridwidth = 1;
                //block.add(titleLabel, cons);
                block.add(subOptionButton, cons);
                cons.insets = new Insets(5, 0, 0, 0);
                cons.gridx = 2;
                cons.gridwidth = 1;
                block.add(edit, cons);
                cons.insets = new Insets(5, 0, 0, 10);
                cons.gridx = 3;
                block.add(remove, cons);
            }
            add(scroll);
        }

    }
}
