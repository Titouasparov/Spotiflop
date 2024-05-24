package gui.popUp;

import data.SessionManager;
import gui.Lyrics.LyricsFrame;
import gui.util.ContextMenus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Blind_test;
import model.Song;
/**
 * La classe NotifFrame est utilisée pour afficher les recommandations de chansons et les nouveaux blind tests à l'utilisateur.
 * Elle fournit une interface utilisateur qui affiche deux listes : une pour les recommandations de chansons et une pour les nouveaux blind tests.
 * L'utilisateur peut interagir avec ces listes en utilisant le clic droit pour afficher un menu contextuel, ou en double-cliquant sur un élément pour afficher les paroles de la chanson.
 */
public class NotifFrame extends Application {
    private ObservableList<Song> recommandations = FXCollections.observableArrayList();
    private ObservableList<Blind_test> blind_tests = FXCollections.observableArrayList();
    private ListView<Song> recommandationList = new ListView<>();
    private ListView<Blind_test> blindTestList = new ListView<>();
    private ContextMenu contextMenuReco = new ContextMenu();
    /**
     * La méthode start est le point d'entrée principal pour la NotifFrame.
     * Elle configure l'interface utilisateur et affiche les recommandations de chansons et les nouveaux blind tests.
     * @param stage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        FlowPane pane = new FlowPane();
        VBox recommandationBox = new VBox();
        VBox blindTestBox = new VBox();
        HBox hbox = new HBox();
        Label recommandationLabel = new Label("Recommandations");
        Label blindTestLabel = new Label("Nouveaux blind tests");

        recommandations = SessionManager.getCurrentUser().getRecommandations();
        recommandationList.setItems(recommandations);
        recommandationList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        blind_tests = SessionManager.getCurrentUser().getBlind_testsReco();
        blindTestList.setItems(blind_tests);

        contextMenuReco = ContextMenus.getContextMenuSongs(recommandationList);

        recommandationBox.getChildren().addAll(recommandationLabel, recommandationList);
        blindTestBox.getChildren().addAll(blindTestLabel, blindTestList);
        hbox.getChildren().addAll(recommandationBox, blindTestBox);
        pane.getChildren().add(hbox);

        initOnClickEvent();
        recommandationBox.setSpacing(10);
        recommandationBox.setMaxSize(300, 400);

        blindTestBox.setSpacing(10);
        blindTestBox.setMaxSize(300, 400);

        hbox.setSpacing(20);
        hbox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        recommandationBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        blindTestBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));

        pane.setAlignment(javafx.geometry.Pos.CENTER);

        pane.setPrefWrapLength(200);
        pane.setVgap(10);
        pane.setHgap(10);
        Scene scene = new Scene(pane, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Cette méthode initialise les événements de clic pour la ListView qui affiche les recommandations de chansons.
     * Si l'utilisateur fait un double-clic sur une chanson dans la ListView, les paroles de la chanson sont affichées.
     * Si l'utilisateur fait un clic droit sur une chanson dans la ListView, le menu contextuel est affiché.
     * Si l'utilisateur fait un clic gauche sur une chanson dans la ListView, le menu contextuel est caché.
     */
    private void initOnClickEvent(){
        recommandationList.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2){
                Song song = recommandationList.getSelectionModel().getSelectedItem();
                if (song != null) {
                    Stage stage = new Stage();
                    new LyricsFrame(song);
                }
            }
            if(e.getButton().equals(MouseButton.SECONDARY)){
                if (recommandationList.getSelectionModel().getSelectedItems() != null ){
                    contextMenuReco.show(recommandationList, e.getScreenX(), e.getScreenY());
                }
            }
            if(e.getButton().equals(MouseButton.PRIMARY)){
                contextMenuReco.hide();
            }
        });
    }
}
