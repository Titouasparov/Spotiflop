package gui.friend;

import Exceptions.CantAddException;
import Exceptions.CantAddExistingFriend;
import data.DataManager;
import data.SessionManager;
import gui.popUp.Erreur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.ArrayList;

/**
 * La classe AddAmiFriend est utilisée pour ajouter un ami à l'utilisateur courant.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de saisir le nom de l'ami et de le sélectionner dans une liste de suggestions.
 */
public class AddAmiFriend extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        Label textLabel = new Label("Entrez le nom de votre ami");
        TextField textField = new TextField();
        textField.setPromptText("Entrez le nom de votre ami");

        ListView<String> suggestionListeView = new ListView<>();

        // Définition des actions lors du clic sur un élément de la liste de suggestions
        suggestionListeView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                textField.setText(suggestionListeView.getSelectionModel().getSelectedItem());
            }
            if (e.getClickCount() == 2) {
                User user = DataManager.nameToUser(suggestionListeView.getSelectionModel().getSelectedItem());
                if (user != null && !user.equals(SessionManager.getCurrentUser())) {
                    try {
                        SessionManager.getCurrentUser().addFriend(user);
                    } catch (CantAddException ex) {
                        Erreur.afficherErreur("Vous ne pouvez ajouter que des gens de votre age");
                        return;
                    } catch (CantAddExistingFriend ex) {
                        Erreur.afficherErreur("Ami deja ajoute");
                        return;
                    }
                }
                primaryStage.close();
            }

        });

        // Mise à jour de la liste de suggestions lors de la saisie du texte
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            suggestionListeView.setItems(FXCollections.observableArrayList(filterSuggestion(newValue)));
        });

        vbox.getChildren().addAll(textLabel, textField, suggestionListeView);
        pane.setCenter(vbox);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Add Ami Friend");
        primaryStage.show();
    }

    /**
     * Filtre les suggestions en fonction du texte saisi.
     * @param s - le texte saisi.
     * @return ArrayList<String> - la liste des suggestions filtrées.
     */
    ArrayList<String> filterSuggestion(String s){
        if(s == null || s.isEmpty()) return new ArrayList<>();
        ArrayList<String> suggestions = new ArrayList<>();
        for (User user : DataManager.getUsers()) {
            if(user.getPseudo().toLowerCase().contains(s.toLowerCase()) && !(user.equals(SessionManager.getCurrentUser()))){
                suggestions.add(user.getPseudo());
            }
        }
        return suggestions;
    }

}