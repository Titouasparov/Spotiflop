package gui.main;

import data.DataManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Song;
import model.User;

import java.io.IOException;

/**
 * La classe Accueil est la classe principale de l'application.
 * Elle affiche l'écran d'accueil et gère les actions des boutons de connexion et d'inscription.
 */
public class Accueil extends Application {

    @Override
    public void start(Stage primaryStage) {
        FlowPane flowPane = new FlowPane();
        primaryStage.setTitle("Spotiflop");
        // Création des éléments visuels
        Label titleLabel = new Label("Bienvenue sur Spotiflop");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;-fx-text-fill: white;");

        Button logInButton = new Button("Se connecter");
        Button signUpButton = new Button("S'inscrire");
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(40));

        // Ajout des éléments à la mise en page
        vbox.getChildren().addAll(titleLabel, logInButton, signUpButton);
        flowPane.getChildren().addAll(vbox);

        // Définition des actions des boutons
        logInButton.setOnAction(_ -> logInPage(primaryStage));
        signUpButton.setOnAction(_ -> signUpPage(primaryStage));

        // Affichage de la scène
        Scene scene = new Scene(flowPane, 400, 200);
        flowPane.setStyle("-fx-background-color: black;");
        vbox.setAlignment(Pos.CENTER);
        flowPane.setAlignment(Pos.CENTER);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Méthode pour afficher la page de connexion.
     * @param primaryStage - le stage principal de l'application.
     */
    private void logInPage(Stage primaryStage) {
        // Mettre en place la page de connexion
        Connexion connexion_page = new Connexion();
        // Cette méthode sera appelée lorsque l'utilisateur clique sur le bouton "Log in"
        connexion_page.start(primaryStage);
    }

    /**
     * Méthode pour afficher la page d'inscription.
     * @param primaryStage - le stage principal de l'application.
     */
    private void signUpPage(Stage primaryStage) {
        // Mettre en place la page d'inscription
        Inscription inscription = new Inscription();
        inscription.start(primaryStage);
        // Cette méthode sera appelée lorsque l'utilisateur clique sur le bouton "Sign up"
    }

    /**
     * Méthode principale de l'application.
     * @param args - les arguments de la ligne de commande.
     * @throws IOException - si une erreur se produit lors du téléchargement des chansons.
     */
    public static void main(String[] args) throws IOException {
        DataManager.downloadSongs("spotify_millsongdata.csv");
        launch(args);
    }
}