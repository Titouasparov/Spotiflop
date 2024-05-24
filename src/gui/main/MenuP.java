package gui.main;

import data.DataManager;
import data.SessionManager;
import gui.blindTest.MenuBlindTestFrame;
import gui.cellFactory.MenuPlaylistCellFactory;
import gui.playlist.PlaylistFrame;
import gui.friend.AddAmiFriend;
import gui.friend.DisplayAmiFrame;
import gui.popUp.ConfirmationAlert;
import gui.popUp.NotifFrame;
import gui.util.ContextMenus;
import gui.util.Util;
import gui.util.tableDynamique;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

import static javafx.collections.FXCollections.observableArrayList;
/**
 * La classe MenuP est utilisée pour afficher le menu principal de l'application.
 * Elle fournit une interface utilisateur qui permet à l'utilisateur de naviguer à travers son compte et les fonctionnalités sociales, de rechercher des chansons, et de gérer ses playlists.
 * Le menu principal comprend une barre de menu avec des options pour voir le profil de l'utilisateur, se désinscrire, se déconnecter, ajouter un ami, ajouter un enfant, et voir les amis.
 * Il comprend également une barre de recherche pour rechercher des chansons et une vue de liste pour afficher les playlists de l'utilisateur.
 */
public class MenuP extends Application {
    private final Property<ObservableList<Song>> songProperty = new SimpleObjectProperty<ObservableList<Song>>();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    private ContextMenu contextMenuPlaylist = new ContextMenu();
    private ContextMenu contextMenuBlindTest = new ContextMenu();
    private final ListView<Playlist> playlistListView = new ListView<>();
    private final ListView<Blind_test> blindTestListView = new ListView<>();
    private TextField searchField;

    /**
     * La méthode start est le point d'entrée principal pour le MenuP.
     * Elle met en place l'interface utilisateur et affiche le menu principal.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage primaryStage) {
        if(!(SessionManager.getCurrentUser().getRecommandations().isEmpty()
                || !(SessionManager.getCurrentUser().getBlind_testsReco().isEmpty()))){

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            NotifFrame notifFrame = new NotifFrame();
            notifFrame.start(stage);
        }

        primaryStage.setTitle("Menu");
        MenuBar menuBar = new MenuBar();

        // Création des menus
        Menu monCompteMenu = new Menu("Mon compte");
        Menu socialMenu = new Menu("Social");

        // Ajout des éléments de menu à "Mon compte"
        MenuItem item1 = new MenuItem("Profil");
        MenuItem item2 = new MenuItem("Se désinscrire");
        MenuItem item3 = new MenuItem("Se déconnecter");
        monCompteMenu.getItems().addAll(item1, item2, item3);

        // Ajout des éléments de menu à "Social"
        MenuItem item6 = new MenuItem("Ajouter un ami");
        MenuItem item5 = new MenuItem("Ajouter un enfant");
        MenuItem item4 = new MenuItem("Amis");

        if(SessionManager.getCurrentUser() instanceof ChildGuest){
            socialMenu.getItems().addAll(item4, item6);
        }
        else{
            socialMenu.getItems().addAll(item4, item6,item5);
        }
        // Action du menu
        item2.setOnAction(_ -> {
            ConfirmationAlert.afficherConfirmation(primaryStage,"Confirmation",
                    "Etes-vous sûr de vouloir vous désinscrire ?");
        });
        item3.setOnAction(_ -> Util.retourAccueil(primaryStage));
        item5.setOnAction(_ -> {
            Stage stage = new Stage();
            InscriptionEnfantFrame inscriptionEnfantFrame = new InscriptionEnfantFrame();
            inscriptionEnfantFrame.start(stage);
        });
        item4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                DisplayAmiFrame displayAmiFrame = new DisplayAmiFrame();
                displayAmiFrame.start(stage);
            }
        });
        item6.setOnAction(e->{
            Stage stage = new Stage();
            AddAmiFriend addAmiFriend = new AddAmiFriend();
            addAmiFriend.start(stage);
        });


        // Ajout des menus à la barre de menu
        menuBar.getMenus().addAll(monCompteMenu, socialMenu);

        //creation du tableau
        tableDynamique table = new tableDynamique();
        table.initContextMenu();
        table.itemsProperty().bind(songProperty);
        songProperty.setValue(FXCollections.observableArrayList(DataManager.getSongs()));

        //label
        Label searchLabel = new Label("Rechercher un son");

        // Création de la barre de recherche
        VBox vBox = new VBox();
        HBox leftHBox = new HBox();
        VBox blindTestBox = new VBox();
        VBox searchBox = new VBox();
        VBox playlistsBox = new VBox();
        HBox searchHBox = new HBox();

        //label
        Label playlistsLabel = new Label("Mes playlists");
        Label blindtestLabel = new Label("Mes blind tests");

        // Création de la liste des playlists existantes
        playlists = SessionManager.getCurrentUser().getPlaylists();

        playlists.addListener(playlistListChangeListener);
        playlistListView.setCellFactory(new MenuPlaylistCellFactory());
        playlistListView.setItems(playlists);

        // Création de la liste des blind tests existants
        ObservableList<Blind_test> blind_tests = SessionManager.getCurrentUser().getBlind_tests();
        blindTestListView.setItems(blind_tests);


        contextMenuPlaylist = ContextMenus.getContextMenuPlaylist(playlistListView);
        contextMenuBlindTest = ContextMenus.getContextMenuBlindTest(blindTestListView);


        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Titre","Artiste","Paroles");
        comboBox.selectionModelProperty().getValue().selectedItemProperty().addListener(comboBoxListener);
        searchField = initTextField(comboBox);
        searchField.setText("");
        comboBox.getSelectionModel().selectFirst();


        searchHBox.getChildren().addAll(searchField, comboBox);
        searchBox.getChildren().addAll(searchLabel, searchHBox);
        playlistsBox.getChildren().addAll(playlistsLabel,playlistListView);
        blindTestBox.getChildren().addAll(blindtestLabel,blindTestListView);
        leftHBox.getChildren().addAll(playlistsBox, blindTestBox);
        vBox.getChildren().addAll(searchBox, leftHBox);

        leftHBox.setAlignment(Pos.TOP_LEFT);
        leftHBox.setSpacing(20);
        vBox.setPadding(new Insets(10));
        searchBox.setPadding(new Insets(10));
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(table);
        root.setLeft(vBox);

        initOnClickEvent();
        // Création de la scène
        Scene scene = new Scene(root, 1380, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * Cette méthode initialise le champ de texte pour la barre de recherche.
     * Elle met en place un écouteur pour la propriété de texte du champ de texte, de sorte que la vue de table est mise à jour avec les résultats de la recherche lorsque l'utilisateur tape dans la barre de recherche.
     * @return le champ de texte initialisé.
     */
    private TextField initTextField(ComboBox<String> comboBox) {
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher...");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filtrer les données de la TableView en fonction du texte entré
            songProperty.setValue(DataManager.filterData(DataManager.getSongs(),newValue,comboBox.getValue()));
        });
        return searchField;
    }
    /**
     * Cette méthode initialise l'événement de clic pour la vue de liste de la playlist.
     * Si l'utilisateur double-clique sur une playlist dans la vue de liste, le cadre de la playlist est affiché.
     * Si l'utilisateur clique avec le bouton droit sur une playlist dans la vue de liste, le menu contextuel est affiché.
     * Si l'utilisateur clique avec le bouton gauche sur une playlist dans la vue de liste, le menu contextuel est caché.
     */
    private void initOnClickEvent() {

        playlistListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
                if (playlist!=null){
                    Stage stage = new Stage();
                    PlaylistFrame playlistFrame = new PlaylistFrame(playlist);
                    playlistFrame.start(stage);
                }
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (playlistListView.getSelectionModel().getSelectedItem() != null) {
                    contextMenuPlaylist.show(playlistListView, event.getScreenX(), event.getScreenY());
                }
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenuPlaylist.hide();
            }
        });
        blindTestListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Blind_test blindTest = blindTestListView.getSelectionModel().getSelectedItem();
                if (blindTest!=null){
                    Stage stage = new Stage();
                    MenuBlindTestFrame blindTestFrame = new MenuBlindTestFrame(blindTest);
                    blindTestFrame.start(stage);
                }
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (blindTestListView.getSelectionModel().getSelectedItem() != null) {
                    contextMenuBlindTest.show(blindTestListView, event.getScreenX(), event.getScreenY());
                }
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenuBlindTest.hide();
            }
        });
    }
    /**
     * Cette méthode ajoute des écouteurs aux listes de chansons et de collaborateurs de chaque playlist dans la liste de playlists de l'utilisateur courant.
     * Ces écouteurs sont déclenchés lorsque des modifications sont apportées à ces listes, par exemple lorsqu'une chanson est ajoutée ou supprimée d'une playlist, ou lorsqu'un collaborateur est ajouté ou supprimé d'une playlist.
     * Lorsque la liste de chansons est modifiée, la méthode `refresh` de `playlistListView` est appelée pour mettre à jour l'affichage de la liste de playlists.
     * L'écouteur `changeCollab` est déclenché lorsque la liste de collaborateurs d'une playlist est modifiée. Lorsque cela se produit, la méthode `refresh` de `playlistListView` est appelée pour mettre à jour l'affichage de la liste de playlists.
     */
    private void playlistChangeListener(){
        if (!playlists.isEmpty()){
            for (Playlist p : playlists){
                p.getCollaborateurs().addListener(changeCollab);
                p.getSongs().addListener(new ListChangeListener<Song>() {
                    @Override
                    public void onChanged(Change<? extends Song> c) {
                        playlistListView.refresh();
                    }
                });
            }
        }
    }

    ListChangeListener<User> changeCollab = new ListChangeListener<>() {
        @Override
        public void onChanged(Change<? extends User> c) {
            playlistListView.refresh();
        }
    };

    ListChangeListener<Playlist> playlistListChangeListener = new ListChangeListener<>() {
        @Override
        public void onChanged(Change<? extends Playlist> c) {
            playlistChangeListener();
        }
    };

    ChangeListener<String> comboBoxListener = (observable, oldValue, newValue) -> {
        songProperty.setValue(DataManager.filterData(DataManager.getSongs(),searchField.getText(),newValue));
    };



    public static void main(String[] args) throws IOException {
        DataManager.downloadSongs("spotify_millsongdata.csv");
        DataManager.generatePeople();
        launch(args);
    }
}
