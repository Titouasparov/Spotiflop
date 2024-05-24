package model;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.util.Pair;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
/**
 * La classe Blind_test est utilisée pour gérer les blind tests dans l'application.
 * Un Blind_test a un nom, des rounds, un mode, une longueur de paroles, des scores, un propriétaire et des participants.
 */
public class Blind_test {
    private String nom;
    private ObservableMap<String, Pair<String,String>> rounds;
    private mode mode;
    private int lyricsLenght;
    private ObservableMap<User, IntegerProperty> scores;
    private User proprietaire;
    private ObservableList<User> participants;
    /**
     * Le constructeur de la classe Blind_test.
     * Il initialise le nom et le propriétaire du Blind_test.
     * @param nom - le nom du Blind_test.
     * @param proprietaire - le propriétaire du Blind_test.
     */
    public Blind_test(String nom, User proprietaire) {
        this.nom = nom;
        this.rounds = FXCollections.observableHashMap();
        this.scores = FXCollections.observableHashMap();
        this.participants = FXCollections.observableArrayList();
        this.proprietaire = proprietaire;
        this.lyricsLenght = 300;
    }
    /**
     * La méthode toString retourne le nom du Blind_test.
     * @return Le nom du Blind_test.
     */
    @Override
    public String toString() {
        return nom;
    }
    /**
     * La méthode getParticipants retourne la liste des participants du Blind_test.
     * @return La liste des participants du Blind_test.
     */
    public ObservableList<User> getParticipants() {
        return participants;
    }
    /**
     * La méthode addSongs ajoute une liste de chansons aux rounds du Blind_test.
     * @param songs La liste de chansons à ajouter.
     */
    public void addSongs(ObservableList<Song> songs) {
        for (Song song : songs) {
            addSong(song);
        }
    }
    /**
     * La méthode getScores retourne les scores des utilisateurs du Blind_test.
     * @return Les scores des utilisateurs du Blind_test.
     */
    public ObservableMap<User, IntegerProperty> getScores() {
        return scores;
    }

    public enum mode{
        TITRE,
        INTERPRETE,
        TITRE_INTERPRETE;
    }
    /**
     * La méthode addSong ajoute une chanson aux rounds du Blind_test.
     * @param song La chanson à ajouter.
     */
    public void addSong(Song song){
        rounds.put(song.getTitre(), new Pair<>(song.getAuteur(), getRandomLyrics(song)));
    }
    /**
     * La méthode getRandomLyrics retourne un extrait aléatoire des paroles d'une chanson.
     * @param song La chanson dont on veut extraire les paroles.
     * @return Un extrait aléatoire des paroles de la chanson.
     */
    private String getRandomLyrics(Song song){
        String lyrics = song.getParoles();
        if (lyricsLenght > lyrics.length()) {
            return lyrics;
        }
        int start = (int)(Math.random()*(lyrics.length()-lyricsLenght));
        lyrics = lyrics.substring(start, start + lyricsLenght);
        return lyrics;
    }
    /**
     * La méthode removeParticipant retire un utilisateur de la liste des participants du Blind_test.
     * @param user L'utilisateur à retirer.
     */
    public void removeParticipant(User user){

        participants.remove(user);
    }
    /**
     * La méthode getProprietaire retourne le propriétaire du Blind_test.
     * @return Le propriétaire du Blind_test.
     */
    public User getProprietaire() {
        return proprietaire;
    }
    /**
     * La méthode setMode définit le mode du Blind_test.
     * @param mode Le mode à définir.
     */
    public void setMode(mode mode){
        this.mode = mode;
    }
    /**
     * La méthode removeSong retire une chanson des rounds du Blind_test.
     * @param song La chanson à retirer.
     */
    public void removeSong(Song song){
        rounds.remove(song.getTitre());
    }
    /**
     * La méthode setLyricsLenght définit la longueur des paroles du Blind_test.
     * @param lyricsLenght La longueur des paroles à définir.
     */
    public void setLyricsLenght(int lyricsLenght){
        this.lyricsLenght = lyricsLenght;
    }
    /**
     * La méthode getRounds retourne les rounds du Blind_test.
     * @return Les rounds du Blind_test.
     */
    public ObservableMap<String, Pair<String, String>> getRounds() {
        return rounds;
    }
    /**
     * La méthode getNom retourne le nom du Blind_test.
     * @return Le nom du Blind_test.
     */
    public String getNom() {
        return nom;
    }
    /**
     * La méthode getMode retourne le mode du Blind_test.
     * @return Le mode du Blind_test.
     */
    public mode getMode() {
        return mode;
    }
    /**
     * La méthode addParticipant ajoute un utilisateur à la liste des participants du Blind_test.
     * @param user L'utilisateur à ajouter.
     */
    public void addParticipant(User user){
        participants.add(user);
    }
    /**
     * La méthode equals vérifie si deux Blind_test sont égaux.
     * @param o L'objet à comparer avec le Blind_test courant.
     * @return true si les deux Blind_test sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blind_test blindTest = (Blind_test) o;
        return lyricsLenght == blindTest.lyricsLenght && Objects.equals(getNom(), blindTest.getNom()) && Objects.equals(getRounds(), blindTest.getRounds()) && getMode() == blindTest.getMode() && Objects.equals(scores, blindTest.scores) && Objects.equals(getProprietaire(), blindTest.getProprietaire()) && Objects.equals(getParticipants(), blindTest.getParticipants());
    }
}
