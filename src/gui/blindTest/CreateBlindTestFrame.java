package gui.blindTest;

import data.SessionManager;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Blind_test;
import model.Song;
/**
 * La classe CreateBlindTestFrame est utilisée pour créer un nouveau blind test.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de saisir le nom du blind test et d'ajouter des chansons.
 */
public class CreateBlindTestFrame extends Application {
    private ObservableList<Song> songs;
    /**
     * Le constructeur de la classe CreateBlindTestFrame.
     * Il initialise les chansons qui seront ajoutées au nouveau blind test.
     * @param songs - les chansons qui seront ajoutées au nouveau blind test.
     */
    public CreateBlindTestFrame(ObservableList<Song> songs) {
        this.songs = songs;
    }
    /**
     * La méthode start est le point d'entrée principal pour toutes les applications JavaFX.
     * Elle est appelée après que la méthode init ait retourné, et après que le système soit prêt pour que l'application commence à fonctionner.
     * @param stage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
        BorderPane pane = new BorderPane();
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label title = new Label("Choisissez un nom");
        TextField nameQuery = new TextField();
        Button ok = new Button("OK");

        ok.setOnAction(e -> {
            String nom = nameQuery.getText();
            if (!nom.isEmpty() && !isBlindTestExist(nom)) {
                Blind_test blindTest = new Blind_test(nom, SessionManager.getCurrentUser());
                blindTest.addSongs(songs);
                blindTest.addParticipant(SessionManager.getCurrentUser());
                SessionManager.getCurrentUser().addBlindTest(blindTest);
                showConfirmationDialog("Blind test créé", "Le blind test a été créé avec succès.");
                stage.close();
            } else {
                showErrorDialog("Erreur", "Un blind test avec ce nom existe déjà.");
            }
        });

        vbox.getChildren().addAll(title, nameQuery, ok);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);

        Scene scene = new Scene(pane, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Créer un nouveau Blind Test");
        stage.show();
    }
    /**
     * Cette méthode vérifie si un blind test avec le nom donné existe déjà dans les blind tests de l'utilisateur courant.
     * @param nom - le nom du blind test.
     * @return vrai si un blind test avec le nom donné existe déjà, faux sinon.
     */
    private boolean isBlindTestExist(String nom) {
        return SessionManager.getCurrentUser().getBlind_tests().stream().anyMatch(blindTest -> blindTest.getNom().equals(nom));
    }
    /**
     * Cette méthode affiche une boîte de dialogue de confirmation avec le titre et le message donnés.
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
    /**
     * Cette méthode affiche une boîte de dialogue d'erreur avec le titre et le message donnés.
     * @param title - le titre de la boîte de dialogue.
     * @param message - le message de la boîte de dialogue.
     */
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
