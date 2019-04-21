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
    private String header;

    public InfoPanel(SongController controller, User user){
        this.controller = controller;
        //header = user.getName().toUpperCase();
        if (user instanceof Artist) {
            sp = controller.showSongsByArtist(((Artist) user).getArtistId());
            albumPnl = controller.showAlbumsByArtist(((Artist)user).getArtistId());
        } else {
            sp = controller.showSongsFollowedByUser(user.getUserId());
            albumPnl = controller.showAlbums(user.getAccount().getId());
        }
        playlistPnl = controller.showPlaylists(user.getAccount().getId());
        artistPnl = controller.showArtists(user.getAccount().getId());
        userPnl = controller.showFriends(user.getAccount().getId());
        init();
    }

    public InfoPanel(SongController controller, String keyword) {
        this.controller = controller;
        //header = "SEARCH RESULTS";
        sp = new SongPanel(controller, "Songs", null, controller.searchSongs(keyword));
        playlistPnl = new PlaylistPanel(controller, controller.searchPlaylists(keyword), "PLAYLISTS");
        albumPnl = new AlbumPanel(controller, controller.searchAlbums(keyword));
        artistPnl = new ArtistPanel(controller, controller.searchArtists(keyword));
        userPnl = new UserPanel(controller, controller.searchUsers(keyword));
        init();
    }

    public void init() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        JPanel subPanelsCase = new JPanel();
        JLabel headerLbl = new JLabel(header);
        headerLbl.setForeground(Color.WHITE);
        headerLbl.setFont(new Font("Arial", Font.BOLD, 22));
        subPanelsCase.add(headerLbl);
        subPanelsCase.setLayout(new BoxLayout(subPanelsCase, BoxLayout.Y_AXIS));
        subPanelsCase.setOpaque(false);
        subPanelsCase.add(sp);
        subPanelsCase.add(playlistPnl);
        subPanelsCase.add(albumPnl);
        subPanelsCase.add(artistPnl);
        subPanelsCase.add(userPnl);
        JScrollPane scroll = new JScrollPane(subPanelsCase);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        add(scroll);
    }
}
