package gui.main;

import Exceptions.CantAddChildren;
import data.DataManager;
import gui.popUp.Erreur;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Member;
import model.User;

import java.time.LocalDate;
import java.time.Period;
/**
 * La classe Inscription est utilisée pour gérer le processus d'inscription d'un nouvel utilisateur.
 * Elle fournit une interface utilisateur qui permet à l'utilisateur de saisir son nom d'utilisateur, son mot de passe et sa date de naissance.
 * Si le nom d'utilisateur saisi existe déjà, un message d'erreur est affiché.
 * Si la date de naissance saisie indique que l'utilisateur a moins de 18 ans, un message d'erreur est affiché.
 * Si l'inscription est réussie, l'utilisateur est redirigé vers le menu principal.
 */
public class Inscription {
    /**
     * La méthode start est le point d'entrée principal pour le processus d'inscription.
     * Elle configure l'interface utilisateur et gère le processus d'inscription.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    public void start(Stage primaryStage) {
        Label usernameLabel = new Label("Entrez votre pseudo:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Entrez un mot de passe :");
        PasswordField passwordField = new PasswordField();
        Label birthDateLabel = new Label("Sélectionnez votre date de naissance:");
        DatePicker birthDateField = new DatePicker();
        Button submitButton = new Button("Valider");

        VBox vbox = new VBox(10, usernameLabel, usernameField,passwordLabel,passwordField,
                birthDateLabel, birthDateField, submitButton);
        vbox.setAlignment(Pos.CENTER);

        submitButton.setOnAction(_ -> {
            String pseudo = usernameField.getText();
            String password = passwordField.getText();
            LocalDate birthDate = birthDateField.getValue();

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
                newUser = new Member(pseudo,password, birthDate);
                DataManager.addUser(newUser);
                Accueil mainMenu = new Accueil();
                mainMenu.start(primaryStage);
            } catch (CantAddChildren e) {
                Erreur.afficherErreur("Vous devez avoir au moins 18 ans pour vous inscrire.");
                return;
            }

        });

        Scene scene = new Scene(vbox, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inscription");
        primaryStage.show();
    }


    private boolean isUserMinor(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() < 18;
    }
}
