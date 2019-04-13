package view;

import controller.SongController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel {
    private SongPanel sp;
    private CategoryPanel playlistPnl, albumPnl, artistPnl, userPnl;

    public InfoPanel(SongController sc, String songPanelHeaer, Object objectForSongPanel, ArrayList<Song> songsData,
                     ArrayList<Object> playlists, ArrayList<Object> albums, ArrayList<Object> artists,
                     ArrayList<Object> users){

        sp = new SongPanel(sc, songPanelHeaer, objectForSongPanel, songsData);
        playlistPnl = new PlaylistPanel(sc, playlists);
        albumPnl = new AlbumPanel(sc, albums);
        artistPnl = new ArtistPanel(sc, artists);
        userPnl = new UserPanel(sc, users);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
        JPanel subPanelsCase = new JPanel();
        subPanelsCase.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        subPanelsCase.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPanelsCase.setOpaque(false);
        subPanelsCase.add(sp);
        subPanelsCase.add(playlistPnl);
        subPanelsCase.add(albumPnl);
        subPanelsCase.add(artistPnl);
        subPanelsCase.add(userPnl);
        JScrollPane scroll = new JScrollPane(subPanelsCase);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,60));
        add(scroll);
    }
}
