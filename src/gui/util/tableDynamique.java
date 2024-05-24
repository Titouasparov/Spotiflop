package gui.util;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import model.Playlist;
import model.Song;

import static javafx.collections.FXCollections.observableArrayList;
/**
 * La classe tableDynamique étend la classe TableView et est utilisée pour afficher une liste d'objets Song.
 * Elle fournit une interface utilisateur qui affiche le titre, l'artiste et le nombre de streams de chaque chanson dans la liste.
 * L'utilisateur peut sélectionner plusieurs chansons à la fois.
 * La classe tableDynamique comprend également un menu contextuel qui offre des options pour interagir avec les chansons sélectionnées.
 */
public class tableDynamique  extends TableView<Song>{

    private ContextMenu contextMenu;
    /**
     * Le constructeur de la classe tableDynamique.
     * Il initialise les colonnes de la TableView et définit le mode de sélection sur multiple.
     */
    public tableDynamique() {

        // Création des colonnes de la TableView
        TableColumn<Song, String> artistColumn = new TableColumn<>("Artiste");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));

        TableColumn<Song, String> titleColumn = new TableColumn<>("Titre");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<Song, Integer> nbStreams = new TableColumn<>("Nombre de streams");
        nbStreams.setCellValueFactory(cell -> {
            ObservableValue<Integer> nbStreamsProperty = new SimpleObjectProperty<>(cell.getValue().getNbStreams());
            return nbStreamsProperty;
        });

        TableColumn<Song, String> nbEcoutes = new TableColumn<>("Nombre d'écoutes");
        nbEcoutes.setCellValueFactory(new PropertyValueFactory<>("nbEcoute"));

        setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Ajout des colonnes à la TableView
        getColumns().addAll(titleColumn,artistColumn,nbStreams,nbEcoutes);
    }
    /**
     * Cette méthode initialise le menu contextuel pour la TableView.
     * Le menu contextuel offre des options pour interagir avec les chansons sélectionnées.
     */
    public void initContextMenu(){
        contextMenu = ContextMenus.getContextMenuSongs(this);
        initOnClickEvent();
    }
    /**
     * Cette méthode initialise le menu contextuel pour la TableView avec une playlist spécifique.
     * Le menu contextuel offre des options pour interagir avec les chansons sélectionnées et la playlist spécifique.
     * @param playlist - la playlist spécifique.
     */
    public void initContextMenu(Playlist playlist){
        contextMenu = ContextMenus.getContextMenuSongs(this,playlist);
        initOnClickEvent();
    }
    /**
     * Cette méthode initialise l'événement de clic pour la TableView.
     * Si l'utilisateur fait un clic droit sur la TableView, le menu contextuel est affiché.
     * Si l'utilisateur fait un clic gauche sur la TableView, le menu contextuel est caché.
     */
    private void initOnClickEvent(){
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(this, event.getScreenX(), event.getScreenY());
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.hide();
            }
        });
    }
}
