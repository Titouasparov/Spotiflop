package gui.cellFactory;

import data.DataManager;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Blind_test;
import model.User;

import java.util.Comparator;
/**
 * La classe ParticipantCellFactory est utilisée pour personnaliser l'affichage des objets User dans une ListView.
 * Elle implémente l'interface Callback, qui est utilisée pour créer des objets ListCell à la demande.
 * Lorsqu'un objet User est ajouté à la ListView, la méthode call de ParticipantCellFactory est appelée, et une nouvelle ListCell est créée.
 * La ListCell utilise la méthode updateItem pour mettre à jour son affichage en fonction de l'objet User.
 */
public class ParticipantCellFactory implements Callback<ListView<User>, ListCell<User>> {
    private Blind_test blindTest;
    /**
     * Le constructeur de la classe ParticipantCellFactory.
     * Il initialise le blind test.
     * @param blindTest - le blind test.
     */
    public ParticipantCellFactory(Blind_test blindTest) {
        this.blindTest = blindTest;
    }
    /**
     * La méthode call est appelée lorsqu'une nouvelle ListCell est nécessaire.
     * Elle retourne une nouvelle ListCell qui affiche un objet User.
     * @param userListView - la ListView qui a besoin d'une nouvelle ListCell.
     * @return une nouvelle ListCell qui affiche un objet User.
     */
    @Override
    public ListCell<User> call(ListView<User> userListView) {
        userListView.getItems().sort(Comparator.comparing(user-> -user.getScore(blindTest)));
        return new ListCell<User>(){
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    int score = user.getScore(blindTest);
                    setText(user.getPseudo() + " / Score = " + score );
                }
            }
        };
    }
}
