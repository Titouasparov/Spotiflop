package gui.friend;

import data.SessionManager;
import gui.util.ContextMenus;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Member;
import model.User;
/**
 * La classe DisplayAmiFrame est utilisée pour afficher une liste d'amis et de membres de la famille de l'utilisateur actuel.
 * Elle étend la classe Application de la bibliothèque JavaFX, ce qui signifie qu'elle représente une application JavaFX.
 * Elle fournit une interface utilisateur qui affiche une liste d'amis et, si l'utilisateur actuel est un membre, une liste de famille.
 * Chaque liste est affichée dans une ListView, et chaque ListView a un menu contextuel qui offre des options pour supprimer un ami ou un membre de la famille.
 */
public class DisplayAmiFrame extends Application {
    private ListView<User> amis;
    private ListView<User> famille;
    private ContextMenu contextMenuAmis;
    private ContextMenu contextMenuFamily;
    /**
     * La méthode start est le point d'entrée principal pour toutes les applications JavaFX.
     * Elle est appelée après que la méthode init ait retourné, et après que le système soit prêt pour que l'application commence à fonctionner.
     * @param stage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage stage) {
        // Création de la disposition principale
        BorderPane root = new BorderPane();
        VBox vbox = new VBox();
        Label titreLabel = new Label("Mes Amis");
        amis = new ListView<>();
        amis.setItems(SessionManager.getCurrentUser().getFriends());

        contextMenuAmis = ContextMenus.getContextMenuAmis(amis);


        vbox.getChildren().addAll(titreLabel, amis);

        if (SessionManager.getCurrentUser() instanceof Member) {
            Label familleLabel = new Label("Famille");
            famille = new ListView<>();
            famille.setItems(((Member) SessionManager.getCurrentUser()).getFamille());
            contextMenuFamily = ContextMenus.getContextMenuAmis(famille);
            initOnClickEvent(famille,contextMenuFamily);
            vbox.getChildren().addAll(familleLabel,famille);
        }


        initOnClickEvent(amis,contextMenuAmis);


        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Mes Amis");
        stage.show();
    }
    /**
     * Cette méthode initialise l'événement de clic pour une ListView.
     * Si l'utilisateur fait un clic droit sur un élément de la ListView, le menu contextuel est affiché.
     * Si l'utilisateur fait un clic gauche sur un élément de la ListView, le menu contextuel est caché.
     * @param list - la ListView pour laquelle initialiser l'événement de clic.
     * @param contextMenu - le menu contextuel à afficher lorsque l'utilisateur fait un clic droit sur un élément de la ListView.
     */
    private void initOnClickEvent(ListView list,ContextMenu contextMenu){
        list.setOnMouseClicked(e->{
            if(e.getButton() == MouseButton.SECONDARY){
                //Un enfant ne peut pas supprimer son parent
                contextMenu.show(list, e.getScreenX(), e.getScreenY());
            }
            if(e.getButton() == MouseButton.PRIMARY){
                contextMenu.hide();
            }
        });

    }

    public static void main(String[] args) {
        // Lancement de l'application JavaFX
        launch(args);
    }
}
