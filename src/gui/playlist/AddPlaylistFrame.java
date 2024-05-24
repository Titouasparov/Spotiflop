package gui.playlist;

import gui.cellFactory.AddPlaylistCellFactory;
import data.SessionManager;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Playlist;
import model.Song;

/**
 * La classe AddPlaylistFrame est utilisée pour ajouter des chansons à une playlist.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de sélectionner une playlist existante ou de créer une nouvelle playlist.
 */
public class AddPlaylistFrame extends Application {
    private ObservableList<Song> songs;
    private Property<ObservableList<Playlist>> property = new SimpleObjectProperty<>();
    private ObservableList<Playlist> playlists;

    public AddPlaylistFrame(ObservableList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void start(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 300);

        // Création de la liste des playlists existantes
        ListView<Playlist> playlistListView = new ListView<>();
        playlists = SessionManager.getCurrentUser().getPlaylists();
        playlistListView.itemsProperty().bind(property);
        property.setValue(playlists);
        //Clics sur les playlists
        initOnClickEvent(playlistListView, stage);
        playlistListView.setCellFactory(new AddPlaylistCellFactory());

        // Création du bouton pour créer une nouvelle playlist
        Button createButton = new Button("Ajouter à une nouvelle playlist");
        createButton.setOnAction(event -> {
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            CreatePlaylistFrame createPlaylistFrame = new CreatePlaylistFrame(songs);
            createPlaylistFrame.start(stage2);
        });

        // Mise en page
        VBox vbox = new VBox(10);
        ScrollPane scrollPane = new ScrollPane(playlistListView);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setContent(playlistListView);
        vbox.getChildren().addAll(scrollPane, createButton);
        vbox.setPadding(new Insets(10));


        root.setCenter(vbox);

        stage.setScene(scene);
        stage.setTitle("Ajouter une Playlist");
        stage.show();
    }

    /**
     * Initialise l'événement de clic sur les playlists.
     * @param playlistListView - la liste des playlists.
     * @param stage - le stage principal de l'application.
     */
    private void initOnClickEvent(ListView<Playlist> playlistListView, Stage stage) {
        playlistListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Playlist playlist = playlistListView.getSelectionModel().getSelectedItem();
                playlist.getSongs().addAll(songs);
                showConfirmationDialog("Chanson ajoutée", "La chanson a été ajoutée à la playlist.");
                stage.hide();
            }
        });
    }

    /**
     * Affiche une boîte de dialogue de confirmation.
     * @param title - le titre de la boîte de dialogue.
     * @param message - le message de la boîte de dialogue.
     */
    private void showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}