package View;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import java.awt.BorderLayout;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class UnregisteredMainScreen extends JFrame implements ActionListener{
    private JButton logIn, signUp,
            mostFrqntlyPlyd, playlists, artists, albums, songs, genres, years,
            volume, repeat, rewind, playOrPause, shuffle, fastforward;
    private JLabel option;
    private JPanel block, p6;
    private JScrollPane scroll;
    private GridBagConstraints cons;


    //sample arraylist
    private ArrayList<String> samplelist;

    public UnregisteredMainScreen(){
        init();
        samplelist = new ArrayList<String>(5);
        samplelist.add("suboption 0");
        samplelist.add("suboption 1");
        samplelist.add("suboption 2");
        samplelist.add("suboption 3");
        samplelist.add("suboption 4");
        samplelist.add("suboption 5");
    }

    public void init(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(true);
        //p.setBackground(new Color(0,78,56));

        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
        p1.setOpaque(false);
        JLabel title = new JLabel("iPl4yer");
        title.setFont(new Font("Arial", Font.PLAIN, 45));
        p1.add(title);
        p1.add(Box.createRigidArea(new Dimension(250,0)));
        logIn = new JButton("Log In");
        logIn.addActionListener(this);
        p1.add(logIn);
        p1.add(Box.createRigidArea(new Dimension(10,0)));
        signUp = new JButton("Sign Up");
        signUp.addActionListener(this);
        p1.add(signUp);
        p.add(p1);

        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
        p2.setOpaque(false);
        p.add(p2);


        Border border = BorderFactory.createLineBorder(Color.black); // line for the border
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        p3.setOpaque(false);
        p3.setBorder(border);
        mostFrqntlyPlyd = new JButton("Most Frequently Played");
        mostFrqntlyPlyd.addActionListener(this);
        mostFrqntlyPlyd.setOpaque(false);
        mostFrqntlyPlyd.setContentAreaFilled(false);
        mostFrqntlyPlyd.setBorderPainted(false);
        p3.add(mostFrqntlyPlyd);
        playlists = new JButton("Playlists");
        playlists.addActionListener(this);
        playlists.setOpaque(false);
        playlists.setContentAreaFilled(false);
        playlists.setBorderPainted(false);
        p3.add(playlists);
        artists = new JButton("Artists");
        artists.addActionListener(this);
        artists.setOpaque(false);
        artists.setContentAreaFilled(false);
        artists.setBorderPainted(false);
        p3.add(artists);
        albums = new JButton("Albums");
        albums.addActionListener(this);
        albums.setOpaque(false);
        albums.setContentAreaFilled(false);
        albums.setBorderPainted(false);
        p3.add(albums);
        songs = new JButton("Songs");
        songs.addActionListener(this);
        songs.setOpaque(false);
        songs.setContentAreaFilled(false);
        songs.setBorderPainted(false);
        p3.add(songs);
        genres = new JButton("Genres");
        genres.addActionListener(this);
        genres.setOpaque(false);
        genres.setContentAreaFilled(false);
        genres.setBorderPainted(false);
        p3.add(genres);
        years = new JButton("Years");
        years.addActionListener(this);
        years.setOpaque(false);
        years.setContentAreaFilled(false);
        years.setBorderPainted(false);
        p3.add(years);
        p3.setBorder(border);
        p2.add(p3);

        JPanel p4 = new JPanel();
        p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
        p4.setOpaque(false);
        p4.setBorder(border);
        p2.add(p4);

        JPanel p5 = new JPanel();
        p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
        p5.setOpaque(false);
        //the text here changes acdg to user's selection
        //"Most Frequestly Played" is default choice
        option = new JLabel("Most Frequestly Played");
        p5.add(option);
        //p5.setBorder(border);
        p4.add(p5);

        p6 = new JPanel();
        p6.setLayout(new BoxLayout(p6, BoxLayout.Y_AXIS));
        p6.setOpaque(false);
        //block = new JPanel();
        //block.setLayout(new GridBagLayout());
        //GridBagConstraints cons = new GridBagConstraints();
        //cons.fill = GridBagConstraints.HORIZONTAL;
        //scroll = new JScrollPane(block);
        //scroll.setPreferredSize(new Dimension(100,100));
        //this one should get a "list" of songs (to know the times this thing should loop)
        //this is a test array, needs to be replaced with the real thing pa
        ArrayList<String> songList = new ArrayList<String>();
        songList.add("Tomorrow");
        songList.add("Departure");
        songList.add("Jet Set Run");
        songList.add("You Can Be A Hero Too");
        songList.add("Orth Waltz");
        songList.add("Neo Arcadia");
        songList.add("Crash");
        songList.add("Freesia");
        songList.add("Cannonball");
        songList.add("Falling Down");
        songList.add("Nothing Beats");
        p6.add(displaySongList(songList));
        ArrayList<String> list = new ArrayList<String>(5);
        list.add("suboption 0");
        list.add("suboption 1");
        list.add("suboption 2");
        list.add("suboption 3");
        list.add("suboption 4");
        list.add("suboption 5");
        //p6.remove(displaySongList(songList));
        //p6.add(displaySubOptionList(list, "playlist"));
        //p6.add(displaySubOptionList(list, "playlist"));
        p4.add(p6);

        JPanel p7 = new JPanel();
        p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));
        p7.setOpaque(false);
        p7.add(Box.createRigidArea(new Dimension(7,0)));
        repeat = new JButton("R");
        p7.add(repeat);
        p7.add(Box.createRigidArea(new Dimension(8,0)));
        rewind = new JButton("<<");
        p7.add(rewind);
        p7.add(Box.createRigidArea(new Dimension(5,0)));
        playOrPause = new JButton("|> / ||");
        p7.add(playOrPause);
        p7.add(Box.createRigidArea(new Dimension(5,0)));
        fastforward = new JButton(">>");
        p7.add(fastforward);
        p7.add(Box.createRigidArea(new Dimension(8,0)));
        p4.add(p7);

        JPanel p8 = new JPanel();
        p8.setLayout(new BoxLayout(p8, BoxLayout.Y_AXIS));
        p8.setOpaque(false);
        p8.add(Box.createRigidArea(new Dimension(0,5)));
        volume = new JButton("V");
        p8.add(volume);
        p8.add(Box.createRigidArea(new Dimension(0,3)));
        shuffle = new JButton("Shuffle");
        p8.add(shuffle);
        p8.add(Box.createRigidArea(new Dimension(0,5)));
        p7.add(p8);

        add(p);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    public JScrollPane displaySongList(ArrayList<String> list){
        block = new JPanel();
        block.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        scroll = new JScrollPane(block);
        scroll.setPreferredSize(new Dimension(100,100));
        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if(list.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No songs to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(int i=0; i<list.size(); i++){
                //JLabel titleLabel = new JLabel();
                //titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                JButton songTitleButton = new JButton();
                songTitleButton.setOpaque(false);
                songTitleButton.setContentAreaFilled(false);
                songTitleButton.setBorderPainted(false);
                JButton remove = new JButton();
                remove.setOpaque(false);
                remove.setContentAreaFilled(false);
                remove.setBorderPainted(false);
                JButton share = new JButton();
                share.setOpaque(false);
                share.setContentAreaFilled(false);
                share.setBorderPainted(false);
                JButton settings = new JButton();
                settings.setOpaque(false);
                settings.setContentAreaFilled(false);
                settings.setBorderPainted(false);
                try {
                    URL resource = getClass().getClassLoader().getResource("delete.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    resource = getClass().getClassLoader().getResource("share.png");
                    img = Paths.get(resource.toURI()).toFile();
                    share.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    resource = getClass().getClassLoader().getResource("settings.png");
                    img = Paths.get(resource.toURI()).toFile();
                    settings.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String songTitle = list.get(i);
                //titleLabel.setText(songTitle);
                songTitleButton.setText(songTitle);
                songTitleButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //get selected song name
                        //set as song being played and play it
                    }
                });
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to remove this song?",
                                "Remove" +" hello "+ "Song", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            //remove item;
                        }
                    }
                });
                share.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to share this song?",
                                "Share song", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            //share item;
                        }
                    }
                });
                settings.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SettingsWindow sw = new SettingsWindow();
                    }
                });
                cons.insets = new Insets(5, 10, 0, 0);
                cons.gridx = 0;
                cons.gridy = i;
                cons.gridwidth = 1;
                //block.add(titleLabel, cons);
                block.add(songTitleButton, cons);
                cons.insets = new Insets(5, 0, 0, 0);
                cons.gridx = 2;
                cons.gridwidth = 1;
                block.add(share, cons);
                cons.insets = new Insets(5, 0, 0, 10);
                cons.gridx = 3;
                block.add(remove, cons);
                cons.insets = new Insets(5, 0, 0, 10);
                cons.gridx = 4;
                block.add(settings, cons);
            }
        }
        return scroll;
    }

    public JScrollPane displaySubOptionList(ArrayList<String> subOptionlist, String optionName){
        block = new JPanel();
        block.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        scroll = new JScrollPane(block);
        scroll.setPreferredSize(new Dimension(100,100));
        //scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if(subOptionlist.isEmpty()){
            cons.insets = new Insets(10, 10, 2, 10);
            cons.gridx = 0;
            cons.gridy = 0;
            cons.gridwidth = 3;
            JLabel emptyLabel = new JLabel("No songs to display.");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            block.add(emptyLabel, cons);
        }
        else{
            for(int i=0; i<subOptionlist.size(); i++){
                //JLabel subOptionLabel = new JLabel();
                //subOptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                JButton subOptionButton = new JButton();
                subOptionButton.setOpaque(false);
                subOptionButton.setContentAreaFilled(false);
                subOptionButton.setBorderPainted(false);
                JButton remove = new JButton();
                remove.setOpaque(false);
                remove.setContentAreaFilled(false);
                remove.setBorderPainted(false);
                JButton share = new JButton();
                share.setOpaque(false);
                share.setContentAreaFilled(false);
                share.setBorderPainted(false);
                JButton settings = new JButton();
                settings.setOpaque(false);
                settings.setContentAreaFilled(false);
                settings.setBorderPainted(false);
                try {
                    URL resource = getClass().getClassLoader().getResource("delete.png");
                    File img = Paths.get(resource.toURI()).toFile();
                    remove.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                    resource = getClass().getClassLoader().getResource("share.png");
                    img = Paths.get(resource.toURI()).toFile();
                    share.setIcon(new ImageIcon(ImageResizer.resizeImage(img, 15, 15)));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String chosenSubOption = subOptionlist.get(i);
                //subOptionLabel.setText(chosenSubOption);
                subOptionButton.setText(chosenSubOption);
                subOptionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //get list (songList) of songs of selected suboption
                        //displaySongList(songList);
                    }
                });
                remove.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to remove this " +optionName + "?",
                                "Remove" + optionName, JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            //remove item;
                        }
                    }
                });
                share.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to share this " +optionName + "?",
                                "Share" + optionName, JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_OPTION) {
                            //share item;
                        }
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
                block.add(share, cons);
                cons.insets = new Insets(5, 0, 0, 10);
                cons.gridx = 3;
                block.add(remove, cons);
            }
        }
        return scroll;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == logIn){
            LogInWindow l = new LogInWindow();
            dispose();
        }
        if(e.getSource() == signUp){
            CreateAccountWindow caw = new CreateAccountWindow();
            dispose();
        }
        if(e.getSource() == mostFrqntlyPlyd){
            //show list of most frequently played songs
        }
        if(e.getSource() == playlists){
            //show list of playlists
            ArrayList<String> list = new ArrayList<String>(5);
            list.add("suboption 0");
            list.add("suboption 1");
            list.add("suboption 2");
            list.add("suboption 3");
            list.add("suboption 4");
            list.add("suboption 5");
            p6.remove(scroll);
            p6.add(displaySubOptionList(list, "playlist"));
        }
        if(e.getSource() == mostFrqntlyPlyd){
            //show list of most frequently played songs
            option.setText("Most Frequently Played");
        }
        if(e.getSource() == playlists){
            //show list of playlists
            ArrayList<String> list = new ArrayList<String>(5);
            list.add("item 0");
            list.add("item 1");
            list.add("item 2");
            list.add("item 3");
            list.add("item 4");
            list.add("item 5");
            p6.remove(scroll);
            p6.add(displaySubOptionList(list, "playlist"));
            option.setText("Playlists");
        }
        if(e.getSource() == artists){
            //show list of artists
            p6.add(displaySubOptionList(samplelist, "artists"));
            option.setText("Artists");
        }
        if(e.getSource() == albums){
            //show list of albums
            p6.add(displaySubOptionList(samplelist, "albums"));
            option.setText("Albums");
        }
        if(e.getSource() == songs){
            //show list of songs
            p6.add(displaySongList(samplelist));
            option.setText("All Songs");
        }
        if(e.getSource() == genres){
            //show list of genres
            p6.add(displaySubOptionList(samplelist, "genres"));
            option.setText("Genres");
        }
        if(e.getSource() == years){
            //show list of years
            p6.add(displaySubOptionList(samplelist, "years"));
            option.setText("Years");
        }
        if(e.getSource() == volume){
            //volume stuff
        }
        if(e.getSource() == rewind){
            //get prev song?
        }
        if(e.getSource() == playOrPause){
            //pause current song
        }
        if(e.getSource() == fastforward){
            //get next song
        }
        if(e.getSource() == repeat){
            //replay current song
        }
        if(e.getSource() == shuffle){
            //shuffle
        }
    }

    public static void main(String[] args){
        UnregisteredMainScreen caw = new UnregisteredMainScreen();
    }
}
