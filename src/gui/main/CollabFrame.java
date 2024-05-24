package gui.main;

import data.SessionManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Blind_test;
import model.Playlist;
import model.User;

/**
 * La classe CollabFrame est utilisée pour ajouter des collaborateurs à une playlist.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de sélectionner des utilisateurs à partir d'une liste et de les ajouter en tant que collaborateurs à la playlist.
 */
public class CollabFrame extends Application {
    private ObservableList<User> collaborators;
    private ObservableList<User> availableUsers;
    private Object item;

    /**
     * Constructeur de la classe CollabFrame.
     * @param item - la playlist à laquelle les collaborateurs seront ajoutés.
     */
    public CollabFrame(Object item){
        this.item = item;
        this.availableUsers = FXCollections.observableArrayList(SessionManager.getCurrentUser().getFriends()); // Assuming DataManager has a method to get all users
    }

    @Override
    public void start(Stage primaryStage) {
        initCollaborators();
        primaryStage.setTitle("Ajout de Collaborateurs");

        BorderPane root = new BorderPane();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Sélectionnez des utilisateurs à ajouter :");

        ListView<User> userListView = new ListView<>(availableUsers);
        userListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ListView<User> collabListView = new ListView<>(collaborators);

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(event -> {
            ObservableList<User> selectedUsers = userListView.getSelectionModel().getSelectedItems();
            addCollaborators(selectedUsers);
        });

        userListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ObservableList<User> selectedUsers = userListView.getSelectionModel().getSelectedItems();
                addCollaborators(selectedUsers);
            }
        });

        // Adding context menu for removing collaborators
        collabListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                User selectedUser = collabListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    ContextMenu contextMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem("Supprimer");
                    removeItem.setOnAction(e -> {
                        removeCollaborator(selectedUser);
                    });
                    contextMenu.getItems().add(removeItem);
                    contextMenu.show(collabListView, event.getScreenX(), event.getScreenY());
                }
            }
        });

        vbox.getChildren().addAll(label, userListView, addButton, new Label("Collaborateurs :"), collabListView);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Ajoute les utilisateurs sélectionnés en tant que collaborateurs à la playlist.
     * @param selectedUsers - les utilisateurs à ajouter en tant que collaborateurs.
     */
    private void addCollaborators(ObservableList<User> selectedUsers) {
        for (User user : selectedUsers) {
            if (!collaborators.contains(user) && !user.equals(SessionManager.getCurrentUser())) {
                collaborators.add(user);
                if (item instanceof Playlist playlist){
                    user.addPlaylist(playlist);
                } else if (item instanceof Blind_test) {
                    Blind_test blindTest = (Blind_test) item;
                    user.addBlindTest(blindTest);
                    }
            }
        }
    }

    /**
     * Supprime un collaborateur de la playlist.
     * @param user - l'utilisateur à supprimer en tant que collaborateur.
     */
    private void removeCollaborator(User user) {
        if (item instanceof Playlist playlist){
            playlist.removeCollaborateur(user);
        } else if (item instanceof Blind_test) {
            Blind_test blindTest = (Blind_test) item;
            blindTest.removeParticipant(user);
        }
    }

    private void initCollaborators( ){
        if (item instanceof Playlist playlist){
            collaborators = playlist.getCollaborateurs();
        } else if (item instanceof Blind_test) {
            Blind_test blindTest = (Blind_test) item;
            collaborators = blindTest.getParticipants();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}