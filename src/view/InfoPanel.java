package view;

import controller.SongController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel {
    private SongController controller;
    private SongPanel sp;
    private CategoryPanel playlistPnl, albumPnl, artistPnl, userPnl;

    public InfoPanel(SongController controller, User user){
        this.controller = controller;
        if (user instanceof Artist) {
            sp = controller.showSongsByArtist(((Artist) user).getArtistId());
            albumPnl = controller.showAlbumsByArtist(((Artist)user).getArtistId());
        } else {
            sp = controller.showSongsFollowedByUser(user.getUserId());
            albumPnl = controller.showAlbums();
        }
        playlistPnl = controller.showPlaylists();
        artistPnl = controller.showArtists();
        userPnl = controller.showFriends();
        init();
    }

    public InfoPanel(SongController controller, String keyword) {
        this.controller = controller;
        sp = new SongPanel(controller, "Songs", null, controller.searchSongs(keyword));
        playlistPnl = new PlaylistPanel(controller, controller.searchPlaylists(keyword));
        albumPnl = new AlbumPanel(controller, controller.searchAlbums(keyword));
        artistPnl = new ArtistPanel(controller, controller.searchArtists(keyword));
        userPnl = new UserPanel(controller, controller.searchUsers(keyword));
        init();
    }

    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setOpaque(false);
        JPanel subPanelsCase = new JPanel();
        subPanelsCase.setLayout(new BoxLayout(subPanelsCase, BoxLayout.Y_AXIS));
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
