package gui.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Playlist;
/**
 * La classe MenuPlaylistCellFactory est utilisée pour personnaliser l'affichage des objets Playlist dans une ListView.
 * Elle implémente l'interface Callback, qui est utilisée pour créer des objets ListCell à la demande.
 * Lorsqu'un objet Playlist est ajouté à la ListView, la méthode call de MenuPlaylistCellFactory est appelée, et une nouvelle ListCell est créée.
 * La ListCell utilise la méthode updateItem pour mettre à jour son affichage en fonction de l'objet Playlist.
 */
public class MenuPlaylistCellFactory implements Callback<ListView<Playlist>, ListCell<Playlist>> {
    /**
     * La méthode call est appelée lorsqu'une nouvelle ListCell est nécessaire.
     * Elle retourne une nouvelle ListCell qui affiche un objet Playlist.
     * @param playlistListView - la ListView qui a besoin d'une nouvelle ListCell.
     * @return une nouvelle ListCell qui affiche un objet Playlist.
     */
    @Override
    public ListCell<Playlist> call(ListView<Playlist> playlistListView) {
        return new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist playlist, boolean empty) {
                super.updateItem(playlist, empty);
                if (empty || playlist == null) {
                    setText(null);
                } else {
                    String option = "";
                    if (!(playlist.getCollaborateurs()).isEmpty()) {
                        option = " (Collaborative) ";
                    }
                    System.out.println("Playlist: " + playlist.toString());
                    System.out.println("Collaborateurs: " + playlist.getCollaborateurs());
                    setText(option+playlist.toString() + " (" + (playlist.getSongs().size()) + ")");
                }
            }
        };
    };
}
