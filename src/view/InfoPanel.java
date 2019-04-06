package view;

import controller.SongController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel {
    private SongPanel sp;
    private CategoryPanel playlistPnl, albumPnl;
    private UserArtistListPanel artistPnl;

    public InfoPanel(SongController sc, String songPnlHeader, String playlistPnlHeader, String albumPnlHeader, String artistPnlHeader,
                     ArrayList<ArrayList<String>> songsData, ArrayList<String> playlists, ArrayList<String> albums,
                     ArrayList<String> artists){

        sp = new SongPanel(sc, songPnlHeader, songsData);
        playlistPnl = new CategoryPanel(sc, playlistPnlHeader, playlists);
        albumPnl = new CategoryPanel(sc, albumPnlHeader, albums);
        artistPnl = new UserArtistListPanel(sc, artistPnlHeader, artists);

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
        JScrollPane scroll = new JScrollPane(subPanelsCase);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setPreferredSize(new Dimension(50,60));
        add(scroll);
    }
}
