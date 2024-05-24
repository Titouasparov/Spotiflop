package gui.blindTest;

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
import model.Blind_test;
import model.Song;

/**
 * La classe AddBlindTestFrame est utilisée pour ajouter des chansons à un Blind test.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de sélectionner un blind test existqnt ou d'en creer un nouveau.
 */
public class AddBlindTestFrame extends Application {
    private ObservableList<Song> songs;
    private Property<ObservableList<Blind_test>> property = new SimpleObjectProperty<>();
    private ObservableList<Blind_test> blind_tests;

    public AddBlindTestFrame(ObservableList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public void start(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 400, 300);

        // Création de la liste des blind tests existants
        ListView<Blind_test> playlistListView = new ListView<>();
        blind_tests = SessionManager.getCurrentUser().getBlind_tests();
        playlistListView.itemsProperty().bind(property);
        property.setValue(blind_tests);
        //Clics sur les blind_tests
        initOnClickEvent(playlistListView, stage);

        // Création du bouton pour créer un nouveau blind test
        Button createButton = new Button("Ajouter à un nouveau blind test");
        createButton.setOnAction(event -> {
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            CreateBlindTestFrame createBlindTestFrame = new CreateBlindTestFrame(songs);
            createBlindTestFrame.start(stage2);
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
        stage.setTitle("Ajouter un Blindtest");
        stage.show();
    }

    /**
     * Initialise l'événement de clic sur les blind test.
     * @param playlistListView - la liste des blind tests.
     * @param stage - le stage principal de l'application.
     */
    private void initOnClickEvent(ListView<Blind_test> playlistListView, Stage stage) {
        playlistListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Blind_test blindTest = playlistListView.getSelectionModel().getSelectedItem();
                if (blindTest != null) {
                    blindTest.addSongs(songs);
                    showConfirmationDialog("Chanson ajoutée", "La chanson a été ajoutée au blind_test.");
                    stage.hide();
                }
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