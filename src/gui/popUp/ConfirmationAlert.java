package gui.popUp;

import data.DataManager;
import data.SessionManager;
import gui.util.Util;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * La classe ConfirmationAlert est utilisée pour afficher une boîte de dialogue de confirmation à l'utilisateur.
 * Elle fournit une méthode statique pour afficher une boîte de dialogue de confirmation avec un en-tête et un message personnalisés.
 * La boîte de dialogue a deux boutons : "Oui" et "Non". Si l'utilisateur clique sur "Oui", l'utilisateur actuel est supprimé et l'application retourne à l'écran d'accueil.
 * Si l'utilisateur clique sur "Non", l'application retourne au menu principal.
 */
public class ConfirmationAlert {
    public static void afficherConfirmation(Stage primaryStage,String header, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(header);
        alert.setHeaderText(message);

        ButtonType buttonTypeOui = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNon = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeOui) {
            // If the user clicks "Yes"
            DataManager.deleteUser(SessionManager.getCurrentUser());
            Util.retourAccueil(primaryStage);
        }
        else{
            Util.retourMenuP(primaryStage);
        }
    }
}