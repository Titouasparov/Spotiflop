package gui.main;

import data.SessionManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Song;
import model.User;
import javafx.geometry.Insets;
/**
 * La classe RecommendationFrame est utilisée pour recommander des chansons à des amis.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de sélectionner un ami à partir d'une liste.
 * Lorsqu'un ami est sélectionné, les chansons recommandées sont ajoutées à la liste des recommandations de l'ami.
 */
public class RecommendationFrame extends Application {
    private ListView<User> friends = new ListView<>();
    private ObservableList<Song> recommandations = FXCollections.observableArrayList();
    /**
     * Constructeur de la classe RecommendationFrame.
     * @param recommandations - les chansons à recommander.
     */
    public RecommendationFrame(ObservableList<Song> recommandations) {
        this.recommandations = recommandations;
    }

    @Override
    public void start(Stage stage){

        BorderPane pane = new BorderPane();
        VBox vbox = new VBox();
        Label recommandationLabel = new Label("Choisissez un ami");
        recommandationLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        friends.setItems(SessionManager.getCurrentUser().getFriends());
        initOnClickAction(stage);

        vbox.getChildren().addAll(recommandationLabel, friends);
        pane.setCenter(vbox);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(30);

        stage.setScene(new Scene(pane, 800, 600));
        stage.setTitle("Recommander un titre");
        stage.show();
    }
    /**
     * Initialise l'action à effectuer lorsqu'un ami est sélectionné.
     * Lorsqu'un ami est sélectionné, les chansons recommandées sont ajoutées à la liste des recommandations de l'ami.
     * @param stage - le stage principal pour cette application.
     */
    private void initOnClickAction(Stage stage) {
        friends.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                User friend = friends.getSelectionModel().getSelectedItem();
                if (friend != null) {
                    friend.addRecommandations(recommandations);
                    stage.close();
                }
            }
        });

    }
}
