package gui.cellFactory;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Playlist;
/**
 * La classe AddPlaylistCellFactory est utilisée pour personnaliser l'affichage des objets Playlist dans une ListView.
 * Elle implémente l'interface Callback, qui est utilisée pour créer des objets ListCell à la demande.
 * Lorsqu'un objet Playlist est ajouté à la ListView, la méthode call de AddPlaylistCellFactory est appelée, et une nouvelle ListCell est créée.
 * La ListCell utilise la méthode updateItem pour mettre à jour son affichage en fonction de l'objet Playlist.
 */
public class AddPlaylistCellFactory implements Callback<ListView<Playlist>, ListCell<Playlist>> {
    /**
     * La méthode call est appelée lorsqu'une nouvelle ListCell est nécessaire.
     * Elle retourne une nouvelle ListCell qui affiche un objet Playlist.
     * @param listView - la ListView qui a besoin d'une nouvelle ListCell.
     * @return une nouvelle ListCell qui affiche un objet Playlist.
     */
    @Override
    public ListCell<Playlist> call(ListView<Playlist> listView) {
        return new ListCell<Playlist>() {
            /**
             * The updateItem method is called to update the display of the ListCell.
             * If the Playlist object is null or the ListCell is empty, it sets the text of the ListCell to null.
             * Otherwise, it sets the text of the ListCell to the string representation of the Playlist object.
             * @param playlist - the Playlist object to display.
             * @param empty - whether the ListCell is empty.
             */
            @Override
            protected void updateItem(Playlist playlist, boolean empty) {
                super.updateItem(playlist, empty);
                if (empty || playlist == null) {
                    setText(null);
                } else {
                    setText(playlist.toString()+"("+(playlist.getSongs().size())+")"); // Assurez-vous que Playlist.toString() affiche le nom et le nombre de chansons
                }
            }
        };
    }
}
