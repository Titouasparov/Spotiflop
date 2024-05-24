package gui.playlist;

import gui.main.CollabFrame;
import gui.util.ContextMenus;
import gui.util.tableDynamique;
import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Playlist;
import model.Song;
import model.User;
/**
 * La classe PlaylistFrame est utilisée pour afficher les détails d'une playlist.
 * Elle fournit une interface utilisateur qui affiche le propriétaire de la playlist, les collaborateurs et les chansons de la playlist.
 * Le propriétaire et les collaborateurs sont affichés dans une ListView sur le côté gauche du cadre.
 * Les chansons sont affichées dans un tableau sur le côté droit du cadre.
 * L'utilisateur peut ajouter des collaborateurs à la playlist en cliquant sur le bouton "Ajouter des collaborateurs".
 */
public class PlaylistFrame extends Application {
    private Playlist playlist;
    private ContextMenu contextMenuCollab;
    private Property<ObservableList<Song>> property = new SimpleObjectProperty<>();
    private Property<ObservableList<User>> property2 = new SimpleObjectProperty<>();
    /**
     * Le constructeur de la classe PlaylistFrame.
     * Il initialise la playlist qui sera affichée.
     * @param playlist - la playlist qui sera affichée.
     */
    public PlaylistFrame(Playlist playlist) {
        this.playlist = playlist;
    }
    /**
     * La méthode start est le point d'entrée principal pour la PlaylistFrame.
     * Elle configure l'interface utilisateur et affiche les détails de la playlist.
     * @param stage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle(playlist.getTitre());
        BorderPane root = new BorderPane();
        VBox leftBox = new VBox();
        VBox ownerBox = new VBox();
        VBox collabBox = new VBox();

        // Labels with bold titles
        Label ownerLabel = new Label(String.format("Propriétaire : %s", playlist.getProprietaire().toString()));
        ownerLabel.setStyle("-fx-font-weight: bold;");

        Label collabLabel = new Label("Collaborateurs :");
        collabLabel.setStyle("-fx-font-weight: bold;");

        ListView<User> peopleList = new ListView<>();
        peopleList.itemsProperty().bind(property2);
        property2.setValue(playlist.getCollaborateurs());
        contextMenuCollab = ContextMenus.getContextMenuCollab(peopleList, playlist);

        tableDynamique table = new tableDynamique();
        table.initContextMenu(playlist);
        table.itemsProperty().bind(property);
        property.setValue(playlist.getSongs());

        Button addCollabButton = new Button("Ajouter des collaborateurs");
        addCollabButton.setStyle("-fx-font-weight: bold;");

        addCollabButton.setOnAction(e->{
            Stage stage2 = new Stage();
            CollabFrame collabFrame = new CollabFrame(playlist);
            collabFrame.start(stage2);
        });


        ownerBox.getChildren().add(ownerLabel);
        collabBox.getChildren().addAll(collabLabel, peopleList, addCollabButton);

        leftBox.getChildren().addAll(ownerBox, collabBox);
        leftBox.setSpacing(10);
        leftBox.setPadding(new Insets(10, 10, 10, 10));

        collabBox.setAlignment(Pos.CENTER);
        collabBox.setSpacing(10);
        root.setLeft(leftBox);
        root.setCenter(table);

        initOnClickEvents(peopleList);
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Cette méthode initialise les événements de clic pour la ListView qui affiche les collaborateurs.
     * Si l'utilisateur fait un clic droit sur un collaborateur dans la ListView, le menu contextuel est affiché.
     * Si l'utilisateur fait un clic gauche sur un collaborateur dans la ListView, le menu contextuel est caché.
     * @param peopleList - la ListView pour laquelle initialiser les événements de clic.
     */
    private void initOnClickEvents(ListView<User> peopleList) {
        peopleList.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenuCollab.show(peopleList, event.getScreenX(), event.getScreenY());
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenuCollab.hide();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
