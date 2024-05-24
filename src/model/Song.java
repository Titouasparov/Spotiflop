package model;

import data.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * Song représente une chanson.
 * Une chanson a un titre, un auteur, des paroles et un nombre d'écoutes.
 */
public class Song {
    private String titre;
    private String auteur;
    private String paroles;
    private ObservableMap<User, Integer> streams;
    /**
     * Construit une chanson avec l'auteur, le titre et les paroles donnés.
     *
     * @param auteur  L'auteur de la chanson.
     * @param titre   Le titre de la chanson.
     * @param paroles Les paroles de la chanson.
     */
    public Song(String auteur,String titre, String paroles) {
        this.titre = titre;
        this.auteur = auteur;
        this.paroles = paroles;
        streams = FXCollections.observableHashMap();
    }
    /**
     * Retourne le titre de la chanson.
     *
     * @return Le titre de la chanson.
     */
    public String getTitre() {
        return titre;
    }
    /**
     * Retourne l'auteur de la chanson.
     *
     * @return L'auteur de la chanson.
     */
    public String getAuteur() {
        return auteur;
    }
    /**
     * Retourne les paroles de la chanson.
     *
     * @return Les paroles de la chanson.
     */
    public String getParoles() {
        return paroles;
    }
    /**
     * Retourne le nombre d'écoutes de la chanson.
     *
     * @return Le nombre d'écoutes de la chanson.
     */
    public Integer getNbEcoute() {
        return streams.get(SessionManager.getCurrentUser());
    }

    public Integer getNbStreams() {
        return streams.values().stream().mapToInt(Integer::intValue).sum();
    }

    public ObservableMap<User, Integer> getStreams() {
        return streams;
    }

    /**
     * Définit le nombre d'écoutes de la chanson.
     *
     * @param nbEcoute Le nombre d'écoutes à définir.
     */
    public void setNbEcoute(int nbEcoute) {
        this.streams.put(SessionManager.getCurrentUser(), nbEcoute);
    }
    /**
     * Retourne une représentation sous forme de chaîne de la chanson.
     *
     * @return Une représentation sous forme de chaîne de la chanson.
     */
    @Override
    public String toString() {
        return titre;
    }
}
