package gui.main;

import data.DataManager;
import data.SessionManager;
import gui.util.Util;
import gui.popUp.Erreur;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.util.Objects;/**
 * La classe Connexion est utilisée pour gérer le processus de connexion de l'application.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de saisir son nom d'utilisateur et son mot de passe.
 * Si les identifiants saisis correspondent à un utilisateur existant dans le système, l'utilisateur est connecté et le menu principal est affiché.
 * Si les identifiants saisis ne correspondent à aucun utilisateur existant, un message d'erreur est affiché.
 */
public class Connexion {

    public void start(Stage primaryStage) {
        // Create the user interface elements
        Label label = new Label("Enter your username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Enter your password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Log In");
        Button backButton = new Button("Back");

        // Layout the user interface
        VBox vbox = new VBox(10, label, usernameField, passwordLabel, passwordField, loginButton, backButton);
        vbox.setAlignment(Pos.CENTER);

        // Create the scene
        Scene scene = new Scene(vbox, 300, 250);

        // Add an event handler for the login button
        loginButton.setOnAction(_ -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            logIn(primaryStage, username, password);
        });

        // Add an event handler for the back button
        backButton.setOnAction(_ -> Util.retourAccueil(primaryStage));

        // Configure the stage
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Tente de connecter l'utilisateur avec le nom d'utilisateur et le mot de passe donnés.
     * Si les identifiants correspondent à un utilisateur existant, l'utilisateur est connecté et le menu principal est affiché.
     * Si les identifiants ne correspondent à aucun utilisateur existant, un message d'erreur est affiché.
     *
     * @param primaryStage - la scène principale de l'application.
     * @param username     - le nom d'utilisateur saisi.
     * @param password     - le mot de passe saisi.
     */
    private void logIn(Stage primaryStage, String username, String password) {
        boolean connexionApproved = false;
        User user = DataManager.getUser(username);
        if (user != null) {
            if (Objects.equals(password, user.getPassword())) {
                connexionApproved = true;
                SessionManager.setCurrentUser(user);
            }
        }
        if (connexionApproved) {
            // Go to the main page
            MenuP mainMenu = new MenuP();
            mainMenu.start(primaryStage);
        } else {
            Erreur.afficherErreur("Login error");
        }
    }
}

