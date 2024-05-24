package model;
import Exceptions.CantAddException;
import Exceptions.CantAddExistingFriend;
import Exceptions.CantUnfriendParent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
/**
 * User est une classe abstraite qui représente un utilisateur dans le système.
 * Un User a un pseudo, un mot de passe, une date de naissance, une liste d'amis, une liste de playlists, et une liste de recommandations.
 */
public abstract class User {
    private final String pseudo;
    private final String password;
    private final LocalDate date_naissance;
    private final ObservableList<User> friends;
    private final ObservableList<Playlist> playlists;
    private final ObservableList<Song> recommandations;
    private final ObservableList<Blind_test> blind_testsReco;
    private final ObservableList<Blind_test> blind_tests;


    /**
     * Construit un User avec le pseudo, le mot de passe, et la date de naissance donnés.
     *
     * @param pseudo         Le pseudo de l'User.
     * @param password       Le mot de passe de l'User.
     * @param date_naissance La date de naissance de l'User.
     */
    public User(String pseudo, String password, LocalDate date_naissance) {
        this.pseudo = pseudo;
        this.password = password;
        this.date_naissance = date_naissance;
        blind_testsReco = FXCollections.observableArrayList();
        blind_tests = FXCollections.observableArrayList();
        this.playlists = FXCollections.observableArrayList();
        this.friends = FXCollections.observableArrayList();
        this.recommandations = FXCollections.observableArrayList();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof User other)) {
            return false;
        }
        return Objects.equals(other.pseudo, this.pseudo) && Objects.equals(other.password, this.password)
                && other.date_naissance == this.date_naissance && other.friends.equals(this.friends)
                && other.recommandations.equals(this.recommandations);
    }
    /**
     * Retourne le mot de passe de l'User.
     *
     * @return Le mot de passe de l'User.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Retourne la liste des playlists de l'User.
     *
     * @return La liste des playlists de l'User.
     */
    public ObservableList<Song> getRecommandations() {
        return recommandations;
    }

    /**
     * Add a song to the recommendation list.
     */
    public void addRecommandation(Song song) {
        if (!recommandations.contains(song)) {
            recommandations.add(song);
        }
    }

    public void removeRecommandation(Song song) {
        recommandations.remove(song);
    }
    public void removeRecommandations(ObservableList<Song> songs) {
        ArrayList<Song> songsToRemove = new ArrayList<>();
        for (Song song : songs) {
            if (this.recommandations.contains(song)) {
                songsToRemove.add(song);
            }
        }
        this.recommandations.removeAll(songsToRemove);
    }

    /**
     * Ajoute une liste de chansons à la liste des recommandations de l'utilisateur.
     * Cette méthode parcourt la liste fournie de chansons et ajoute chaque chanson à la liste des recommandations de l'utilisateur.
     * Avant d'ajouter une chanson, elle vérifie si la chanson est déjà dans la liste des recommandations de l'utilisateur pour éviter les doublons.
     *
     * @param songs La liste des chansons à ajouter à la liste des recommandations de l'utilisateur.
     */
    public void addRecommandations(ObservableList<Song> songs) {
        for (Song song : songs) {
            addRecommandation(song);
        }
    }

    /**
     * Supprime un ami de la liste d'amis de l'utilisateur.
     * Cette méthode est abstraite et doit être implémentée dans les sous-classes.
     *
     * @param user L'utilisateur à supprimer de la liste d'amis.
     * @throws CantUnfriendParent Si l'utilisateur à supprimer est un parent de l'utilisateur.
     */
    public abstract void removeFriend(User user) throws CantUnfriendParent;

    /**
     * Ajoute un ami à la liste d'amis de l'utilisateur.
     * Cette méthode est abstraite et doit être implémentée dans les sous-classes.
     *
     * @param user L'utilisateur à ajouter à la liste d'amis.
     * @throws CantAddException Si l'utilisateur ne peut pas être ajouté comme ami.
     * @throws CantAddExistingFriend Si l'utilisateur est déjà un ami.
     */
    public abstract void addFriend(User user) throws CantAddException, CantAddExistingFriend;
    /**
     * Retourne la liste des playlists de l'utilisateur.
     *
     * @return La liste des playlists de l'utilisateur.
     */
    public ObservableList<Playlist> getPlaylists() {
        return playlists;
    }
    /**
     * Ajoute un Blind_test à la liste des Blind_test de l'utilisateur.
     *
     * @param blind_test Le Blind_test à ajouter.
     */
    public void addBlindTest(Blind_test blind_test){
        blind_tests.add(blind_test);
    }
    /**
     * Ajoute un Blind_test à la liste des recommandations de Blind_test de l'utilisateur.
     *
     * @param blind_test Le Blind_test à ajouter aux recommandations.
     * @return La liste des recommandations de Blind_test de l'utilisateur après l'ajout.
     */
    public ObservableList<Blind_test> addBlindTestReco(Blind_test blind_test){
        blind_testsReco.add(blind_test);
        return blind_testsReco;
    }
    /**
     * Retourne la liste des amis de l'utilisateur.
     *
     * @return La liste des amis de l'utilisateur.
     */
    public ObservableList<User> getFriends() {
        return friends;
    }
    /**
     * Retourne le mot de passe de l'utilisateur.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retourne la liste des recommandations de Blind_test de l'utilisateur.
     *
     * @return La liste des recommandations de Blind_test de l'utilisateur.
     */
    public ObservableList<Blind_test> getBlind_testsReco() {
        return blind_testsReco;
    }
    /**
     * Retourne la liste des Blind_test de l'utilisateur.
     *
     * @return La liste des Blind_test de l'utilisateur.
     */
    public ObservableList<Blind_test> getBlind_tests() {
        return blind_tests;
    }

    @Override
    public String toString() {
        return pseudo + String.format(" (%s) ",this.getClass().getSimpleName());
    }
    /**
     * Supprime un Blind_test de la liste des Blind_test de l'utilisateur.
     *
     * @param blind_test Le Blind_test à supprimer.
     */
    public void removeBlindTest(Blind_test blind_test){
        blind_tests.remove(blind_test);
    }

    /**
     * Ajoute une playlist à la liste des playlists de l'utilisateur.
     *
     * @param playlist La playlist à ajouter.
     */
    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
    /**
     * Supprime une playlist de la liste des playlists de l'utilisateur.
     *
     * @param playlist La playlist à supprimer.
     */
    public void removePlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }
    /**
     * Returns the score of the user for a specific Blind_test.
     *
     * @param blind_test The Blind_test for which to get the score.
     * @return The score of the user for the specified Blind_test.
     */
    public int getScore(Blind_test blind_test){
        int score = 0;
        for (Blind_test b : blind_tests){
            if (b.equals(blind_test)){
                if (b.getScores().containsKey(this)){
                    score = b.getScores().get(this).get();
                }
            }
        }
        return score;
    }
}
