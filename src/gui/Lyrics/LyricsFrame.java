package gui.Lyrics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Song;
/**
 * La classe LyricsFrame est utilisée pour afficher les paroles d'une chanson.
 * Elle étend la classe Application de la bibliothèque JavaFX, ce qui signifie qu'elle représente une application JavaFX.
 * Elle fournit une interface utilisateur qui affiche les paroles de la chanson dans un LyricsPane.
 */
public class LyricsFrame extends Application {
    private Song song;
    /**
     * Le constructeur de la classe LyricsFrame.
     * Il initialise la chanson dont les paroles sont affichées.
     * @param song - la chanson dont les paroles sont affichées.
     */
    public LyricsFrame(Song song) {
        this.song = song;
    }
    /**
     * La méthode start est le point d'entrée principal pour toutes les applications JavaFX.
     * Elle est appelée après que la méthode init ait retourné, et après que le système soit prêt pour que l'application commence à fonctionner.
     * @param stage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        LyricsPane pane = new LyricsPane(song);
        pane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Lyrics");
        stage.show();
    }
}
