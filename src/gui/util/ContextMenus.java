package gui.util;

import data.DataManager;
import data.SessionManager;
import gui.blindTest.AddBlindTestFrame;
import gui.Lyrics.LyricsFrame;
import gui.main.RecommendationFrame;
import gui.playlist.AddPlaylistFrame;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.*;

/**
 * La classe ContextMenus est utilisée pour créer des menus contextuels pour divers éléments de l'application.
 * Elle fournit des méthodes statiques pour créer des menus contextuels pour les chansons, les playlists, les collaborateurs et les amis.
 * Chaque menu contextuel offre différentes options en fonction du contexte. Par exemple, le menu contextuel pour les chansons permet à l'utilisateur d'écouter une chanson, de l'ajouter à une playlist ou de la recommander.
 * Le menu contextuel pour les playlists permet à l'utilisateur de supprimer une playlist ou de se désabonner de celle-ci.
 * Le menu contextuel pour les collaborateurs permet à l'utilisateur de retirer un collaborateur d'une playlist.
 * Le menu contextuel pour les amis permet à l'utilisateur de supprimer un ami.
 */
public class ContextMenus{
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Ajouter à une playlist" est sélectionnée dans le menu contextuel.
     * Elle crée une nouvelle fenêtre AddPlaylistFrame pour les chansons sélectionnées dans la TableView et l'ouvre dans un nouveau Stage.
     * @param addPlaylistItem L'option "Ajouter à une playlist" du menu contextuel.
     * @param table La TableView des chansons.
     */
    public static void setAddPlaylistAction(MenuItem addPlaylistItem, TableView<Song> table) {
        addPlaylistItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null) {
                    Stage stage = new Stage();
                    AddPlaylistFrame addPlaylistFrame = new AddPlaylistFrame(songs);
                    addPlaylistFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Ajouter à une playlist" est sélectionnée dans le menu contextuel.
     * Elle crée une nouvelle fenêtre AddPlaylistFrame pour les chansons sélectionnées dans la ListView et l'ouvre dans un nouveau Stage.
     * @param addPlaylistItem L'option "Ajouter à une playlist" du menu contextuel.
     * @param table La ListView des chansons.
     */
    public static void setAddPlaylistAction(MenuItem addPlaylistItem, ListView<Song> table) {
        addPlaylistItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null) {
                    Stage stage = new Stage();
                    AddPlaylistFrame addPlaylistFrame = new AddPlaylistFrame(songs);
                    addPlaylistFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Ecouter" est sélectionnée dans le menu contextuel.
     * Elle incrémente le nombre d'écoutes de la chanson sélectionnée dans la TableView et ouvre une nouvelle fenêtre LyricsFrame pour cette chanson.
     * @param ecouterItem L'option "Ecouter" du menu contextuel.
     * @param table La TableView des chansons.
     */
    private static void setEcouterAction(MenuItem ecouterItem, TableView<Song> table) {
        ecouterItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Song song = table.getSelectionModel().getSelectedItem();
                if (song != null) {
                    song.setNbEcoute(song.getNbEcoute() + 1);
                    table.refresh();

                    Stage stage = new Stage();
                    LyricsFrame lyricsFrame = new LyricsFrame(song);
                    lyricsFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Ecouter" est sélectionnée dans le menu contextuel.
     * Elle incrémente le nombre d'écoutes de la chanson sélectionnée dans la ListView et ouvre une nouvelle fenêtre LyricsFrame pour cette chanson.
     * @param ecouterItem L'option "Ecouter" du menu contextuel.
     * @param table La ListView des chansons.
     */
    private static void setEcouterAction(MenuItem ecouterItem, ListView<Song> table) {
        ecouterItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Song song = table.getSelectionModel().getSelectedItem();
                if (song != null) {
                    song.setNbEcoute(song.getNbEcoute() + 1);
                    table.refresh();

                    Stage stage = new Stage();
                    LyricsFrame lyricsFrame = new LyricsFrame(song);
                    lyricsFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Recommander" est sélectionnée dans le menu contextuel.
     * Elle crée une nouvelle fenêtre RecommendationFrame pour les chansons sélectionnées dans la TableView et l'ouvre dans un nouveau Stage.
     * @param table La TableView des chansons.
     * @param recommendationItem L'option "Recommander" du menu contextuel.
     */
    private static void setRecommandationAction(TableView<Song> table, MenuItem recommendationItem) {
        recommendationItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null) {
                    Stage stage = new Stage();
                    RecommendationFrame recommendationFrame = new RecommendationFrame(songs);
                    recommendationFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Recommander" est sélectionnée dans le menu contextuel.
     * Elle crée une nouvelle fenêtre RecommendationFrame pour les chansons sélectionnées dans la ListView et l'ouvre dans un nouveau Stage.
     * @param table La ListView des chansons.
     * @param recommendationItem L'option "Recommander" du menu contextuel.
     */
    private static void setRecommandationItem(ListView<Song> table, MenuItem recommendationItem) {
        recommendationItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null) {
                    Stage stage = new Stage();
                    RecommendationFrame recommendationFrame = new RecommendationFrame(songs);
                    recommendationFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode définit l'action à effectuer lorsque l'option "Ajouter à un blind test" est sélectionnée dans le menu contextuel.
     * Elle crée une nouvelle fenêtre AddBlindTestFrame pour les chansons sélectionnées dans la TableView et l'ouvre dans un nouveau Stage.
     * @param table La TableView des chansons.
     * @param addBlindTestItem L'option "Ajouter à un blind test" du menu contextuel.
     */
    private static void setBlindTestItem(TableView<Song> table, MenuItem addBlindTestItem) {
        addBlindTestItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null) {
                    Stage stage = new Stage();
                    AddBlindTestFrame addBlindTestFrame = new AddBlindTestFrame(songs);
                    addBlindTestFrame.start(stage);
                }
            }
        });
    }
    /**
     * Cette méthode crée un menu contextuel pour les chansons dans une TableView.
     * Le menu contextuel comprend des options pour écouter une chanson, l'ajouter à une playlist ou la recommander.
     * @param table La TableView des chansons.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuSongs(TableView<Song> table){

        ContextMenu menu = new ContextMenu();
        MenuItem ecouterItem = new MenuItem("Ecouter");
        MenuItem addPlaylistItem = new MenuItem("Ajouter a une playlist");
        MenuItem addToBlindTestItem = new MenuItem("Ajouter a un blind test");
        MenuItem recommendationItem = new MenuItem("Recommander");

        setEcouterAction(ecouterItem, table);
        setAddPlaylistAction(addPlaylistItem, table);
        setRecommandationAction(table, recommendationItem);
        setBlindTestItem(table, addToBlindTestItem);

        menu.getItems().addAll(ecouterItem,addPlaylistItem,recommendationItem,addToBlindTestItem);
        return menu;
    }



    /**
     * Cette méthode crée un menu contextuel pour les chansons dans une TableView au sein d'une playlist spécifique.
     * Le menu contextuel comprend des options pour écouter une chanson, l'ajouter à une playlist, la recommander ou la supprimer de la playlist.
     * @param table La TableView des chansons.
     * @param playlist La playlist à laquelle appartiennent les chansons.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuSongs(TableView<Song> table, Playlist playlist){

        ContextMenu menu = new ContextMenu();
        MenuItem ecouterItem = new MenuItem("Ecouter");
        MenuItem addPlaylistItem = new MenuItem("Ajouter a une playlist");
        MenuItem deleteFromPlaylist = new MenuItem("Supprimer de la playlist");
        MenuItem addToBlindTestItem = new MenuItem("Ajouter a un blind test");
        MenuItem recommendationItem = new MenuItem("Recommander");

        setEcouterAction(ecouterItem, table);
        setAddPlaylistAction(addPlaylistItem, table);
        deleteFromPlaylist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = table.getSelectionModel().getSelectedItems();
                if (songs != null && !songs.isEmpty()) {
                    playlist.getSongs().removeAll(songs);
                }
            }
        });

        setRecommandationAction(table, recommendationItem);
        setBlindTestItem(table, addToBlindTestItem);

        menu.getItems().addAll(ecouterItem,addPlaylistItem,recommendationItem,deleteFromPlaylist,addToBlindTestItem);
        return menu;
    }
    /**
     * Cette méthode crée un menu contextuel pour les chansons dans une ListView.
     * Le menu contextuel comprend des options pour écouter une chanson, l'ajouter à une playlist, la recommander ou la supprimer.
     * @param listView La ListView des chansons.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuSongs(ListView<Song> listView){

        ContextMenu menu = new ContextMenu();
        MenuItem ecouterItem = new MenuItem("Ecouter");
        MenuItem addPlaylistItem = new MenuItem("Ajouter a une playlist");
        MenuItem recommendationItem = new MenuItem("Recommander");
        MenuItem delReco = new MenuItem("Supprimer");

        setEcouterAction(ecouterItem, listView);
        setAddPlaylistAction(addPlaylistItem, listView);
        setRecommandationItem(listView, recommendationItem);
        delReco.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Song> songs = listView.getSelectionModel().getSelectedItems();
                if (songs != null && !songs.isEmpty()) {
                    SessionManager.getCurrentUser().removeRecommandations(songs);
                }
            }
        });

        menu.getItems().addAll(ecouterItem,addPlaylistItem,recommendationItem,delReco);
        return menu;
    }

    /**
     * Cette méthode crée un menu contextuel pour une ListView de playlists.
     * Le menu contextuel comprend des options pour supprimer une playlist ou se désabonner de celle-ci.
     * @param playlistListView La ListView des playlists.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuPlaylist(ListView<Playlist> playlistListView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delItem = new MenuItem("Supprimer");
        MenuItem unfollowItem = new MenuItem("Se désabonner");

        delItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
                if (!playlist.getSongs().isEmpty()) {
                    SessionManager.getCurrentUser().getPlaylists().remove(playlist);
                    for (User u : DataManager.getUsers()) {
                        u.getPlaylists().remove(playlist);
                    }
                    contextMenu.hide();
                }
            }
        });
        unfollowItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
                if (playlist !=null && !playlist.getSongs().isEmpty()) {
                    SessionManager.getCurrentUser().getPlaylists().remove(playlist);
                    playlist.removeCollaborateur(SessionManager.getCurrentUser());
                    contextMenu.hide();
                }
            }
        });

        playlistListView.setOnContextMenuRequested(event -> {
            Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
            if (playlist != null && SessionManager.getCurrentUser().equals(playlist.getProprietaire())) {
                contextMenu.getItems().setAll(delItem);
            } else {
                contextMenu.getItems().setAll(unfollowItem);
            }
        });
        return contextMenu;
    }
    /**
     * Cette méthode crée un menu contextuel pour une ListView de collaborateurs dans une playlist spécifique.
     * Le menu contextuel comprend une option pour retirer un collaborateur de la playlist.
     * @param userListView La ListView des collaborateurs.
     * @param playlist La playlist à laquelle appartiennent les collaborateurs.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuCollab(ListView<User> userListView, Playlist playlist) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delCollab = new MenuItem("Supprimer");
        contextMenu.getItems().addAll(delCollab);

        delCollab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = userListView.getSelectionModel().getSelectedItem();
                if(user != null){
                    playlist.removeCollaborateur(user);
                    user.removePlaylist(playlist);
                    contextMenu.hide();
                }
            }
        });
        return contextMenu;
    }

    /**
     * Cette méthode crée un menu contextuel pour une ListView de collaborateurs dans un blind test spécifique.
     * Le menu contextuel comprend une option pour retirer un collaborateur du blind test.
     * @param userListView La ListView des collaborateurs.
     * @param blindTest Le blind test auquel appartiennent les collaborateurs.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuParticipants(ListView<User> userListView, Blind_test blindTest) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delCollab = new MenuItem("Supprimer");
        contextMenu.getItems().addAll(delCollab);

        delCollab.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = userListView.getSelectionModel().getSelectedItem();
                if(user != null && !(user.equals(blindTest.getProprietaire()))){
                    blindTest.removeParticipant(user);
                    user.removeBlindTest(blindTest);
                    contextMenu.hide();
                }
            }
        });
        return contextMenu;
    }

    /**
     * Cette méthode crée un menu contextuel pour une ListView d'amis.
     * Le menu contextuel comprend une option pour supprimer un ami.
     * @param userListView La ListView des amis.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuAmis(ListView<User> userListView) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delAmis = new MenuItem("Supprimer");
        contextMenu.getItems().addAll(delAmis);

        delAmis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = userListView.getSelectionModel().getSelectedItem();
                if(user != null){
                    DataManager.unfriend(user);
                }
            }
        });

        return contextMenu;
    }
    /**
     * Cette méthode crée un menu contextuel pour une ListView de blind tests.
     * Le menu contextuel comprend des options pour supprimer un blind test ou se désabonner de celui-ci.
     * @param blindTestListView La ListView des blind tests.
     * @return Le menu contextuel créé.
     */
    public static ContextMenu getContextMenuBlindTest(ListView<Blind_test> blindTestListView){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delItem = new MenuItem("Supprimer");
        MenuItem unfollowItem = new MenuItem("Se désabonner");

        delItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Blind_test blind_test = blindTestListView.getSelectionModel().getSelectedItem();
                if (blind_test != null) {
                    SessionManager.getCurrentUser().getBlind_tests().remove(blind_test);
                    for (User u : DataManager.getUsers()) {
                        u.getBlind_tests().remove(blind_test);
                    }
                    contextMenu.hide();
                }
            }
        });
        unfollowItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Blind_test blind_test = blindTestListView.getSelectionModel().getSelectedItem();
                if (blind_test !=null) {
                    SessionManager.getCurrentUser().getBlind_tests().remove(blind_test);
                    blind_test.removeParticipant(SessionManager.getCurrentUser());
                    contextMenu.hide();
                }
            }
        });
        blindTestListView.setOnContextMenuRequested(event -> {
            Blind_test blind_test = blindTestListView.getSelectionModel().getSelectedItem();
            if (blind_test != null && SessionManager.getCurrentUser().equals(blind_test.getProprietaire())) {
                contextMenu.getItems().setAll(delItem);
            } else {
                contextMenu.getItems().setAll(unfollowItem);
            }
        });

        return contextMenu;
    }
}
