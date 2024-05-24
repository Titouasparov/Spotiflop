package gui.popUp;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * La classe Erreur est utilisée pour afficher des messages d'erreur à l'utilisateur.
 * Elle fournit une méthode statique pour afficher un message d'erreur dans une boîte de dialogue d'alerte.
 */
public class Erreur {
    /**
     * Affiche un message d'erreur dans une boîte de dialogue d'alerte.
     * La boîte de dialogue a le titre "Erreur" et l'en-tête "Erreur".
     * @param message - le message d'erreur à afficher.
     */
    public static void afficherErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Erreur");
        alert.setContentText(message);

        alert.showAndWait();
    }
}