package gui.Lyrics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Song;
/**
 * La classe LyricsPane est utilisée pour afficher les paroles d'une chanson.
 * Elle fournit une interface utilisateur qui affiche les paroles de la chanson dans un ScrollPane.
 * Les paroles sont affichées dans un nœud Text, qui est ajouté à un ScrollPane pour permettre à l'utilisateur de faire défiler les paroles.
 * Le ScrollPane est ajouté à un VBox, qui est ajouté à un FlowPane, qui est défini comme la racine de la scène.
 */
public class LyricsPane extends FlowPane{
    private Song song;
    private Text lyrics = new Text();

    /**
     * Le constructeur de la classe LyricsPane.
     * Il initialise la chanson dont les paroles seront affichées.
     * @param song - la chanson dont les paroles seront affichées.
     */
    public LyricsPane(Song song) {
        this.song = song;
        init();
    }
    public LyricsPane(String l) {
        this.song = null;
        lyrics.setText(l);
        init();
    }
    public LyricsPane() {
        this.song = null;
        init();
    }

    /**
     * La méthode init est le point d'entrée principal pour la LyricsPane.
     * Elle configure l'interface utilisateur et affiche les paroles de la chanson.
     */
    public void init() {
        if (song != null) {
            lyrics.setText(song.getParoles());
        } else {
            lyrics.setText("");
        }
        VBox lyricsBox = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(lyrics);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        lyricsBox.getChildren().add(scrollPane);
        lyricsBox.setPadding(new Insets(30,30,30,30));
        lyricsBox.setMaxSize(800, 600);

        this.setAlignment(Pos.CENTER);
        lyrics.setStyle("-fx-font-size: 16px;");

        this.getChildren().add(lyricsBox);
    }

    public void setLyrics(String s) {
        lyrics.setText(s);
    }
}
