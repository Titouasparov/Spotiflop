package gui.playlist;

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
import model.Playlist;
import model.Song;
/**
 * La classe AddPlaylistFrame est utilisée pour ajouter des chansons à une playlist.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de sélectionner une playlist existante ou de créer une nouvelle playlist.
 */
public class CreatePlaylistFrame extends Application {
    private ObservableList<Song> songs;
    public CreatePlaylistFrame(ObservableList<Song> songs) {
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
            if (!nom.isEmpty() && !isPlaylistExist(nom)) {
                Playlist playlist = new Playlist(nom, SessionManager.getCurrentUser());
                playlist.getSongs().addAll(songs);
                SessionManager.getCurrentUser().addPlaylist(playlist);
                showConfirmationDialog("Playlist créée", "La playlist a été créée avec succès.");
                stage.close();
            } else {
                showErrorDialog("Erreur", "Une playlist avec ce nom existe déjà.");
            }
        });

        vbox.getChildren().addAll(title, nameQuery, ok);
        vbox.setAlignment(Pos.CENTER);
        pane.setCenter(vbox);

        Scene scene = new Scene(pane, 300, 150);
        stage.setScene(scene);
        stage.setTitle("Créer une nouvelle Playlist");
        stage.show();
    }
    /**
     * Cette méthode vérifie si une playlist existe déjà avec le nom donné.
     * @param nom - le nom de la playlist à vérifier.
     * @return vrai si une playlist avec ce nom existe déjà, faux sinon.
     */
    private boolean isPlaylistExist(String nom) {
        return SessionManager.getCurrentUser().getPlaylists().stream()
                .anyMatch(playlist -> playlist.getTitre().equals(nom));
    }
    /**
     * Affiche une boîte de dialogue de confirmation.
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
