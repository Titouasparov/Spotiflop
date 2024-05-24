package gui.main;

import Exceptions.CantAddChildren;
import Exceptions.CantAddExistingFriend;
import data.DataManager;
import data.SessionManager;
import gui.popUp.Erreur;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ChildGuest;
import model.Member;
import model.User;

import java.time.LocalDate;
/**
 * La classe InscriptionEnfantFrame est utilisée pour gérer le processus d'inscription d'un nouvel utilisateur enfant.
 * Elle fournit une interface utilisateur qui permet à l'utilisateur de saisir le nom d'utilisateur de l'enfant, son mot de passe et sa date de naissance.
 * Si le nom d'utilisateur saisi existe déjà, un message d'erreur est affiché.
 * Si l'inscription est réussie, l'utilisateur enfant est ajouté à la famille de l'utilisateur actuel.
 */
public class InscriptionEnfantFrame extends Application {
    /**
     * La méthode start est le point d'entrée principal pour le processus d'inscription.
     * Elle configure l'interface utilisateur et gère le processus d'inscription.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    public void start(Stage primaryStage) {
        FlowPane flowPane = new FlowPane();
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        Label nameLabel = new Label("Choisissez un nom : ");
        TextField nameField = new TextField();
        Label passwordLabel = new Label("Choisissez un mot de passe : ");
        PasswordField passwordField = new PasswordField();
        Label dateLabel = new Label("Rentrez la date de naissance de l'enfant : ");
        DatePicker datePicker = new DatePicker();
        Button okButton = new Button("Ok");

        vBox.getChildren().addAll(nameLabel, nameField, passwordLabel, passwordField, dateLabel, datePicker,okButton);

        okButton.setOnAction(e->{
            String pseudo = nameField.getText();
            String password = passwordField.getText();
            LocalDate birthDate = datePicker.getValue();

            if (pseudo.isEmpty() || birthDate == null) {
                Erreur.afficherErreur("Veuillez remplir tous les champs.");
                return;
            }

            if (DataManager.isUserExists(pseudo)) {
                Erreur.afficherErreur("Le pseudo existe déjà. Veuillez en choisir un autre.");
                return;
            }
            User newUser = null;
            try {
                newUser = new ChildGuest(pseudo,password, birthDate,SessionManager.getCurrentUser());
            } catch (CantAddChildren ex) {
                Erreur.afficherErreur("L'utilisateur doit etre un enfant");
                return;
            }

            DataManager.addUser(newUser);
            Member currentMember = (Member) SessionManager.getCurrentUser();
            try {
                currentMember.addFamilyMember(newUser);
            } catch (CantAddExistingFriend ex) {
                Erreur.afficherErreur("Deja dans votre famille");
            }
            primaryStage.close();
        });

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription");
        primaryStage.show();
    }

}
