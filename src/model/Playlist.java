package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
/**
 * Playlist représente une playlist de chansons.
 * Une Playlist a un nom, un propriétaire, une liste observable de chansons, un indicateur de collaboration et une liste observable de collaborateurs.
 */
public class Playlist {
    private String nom;
    private User proprietaire;
    private ObservableList<Song> songs;
    private boolean collaborative;
    private ObservableList<User> collaborateurs;
    /**
     * Construit une Playlist avec le nom et le propriétaire donnés.
     *
     * @param nom         Le nom de la Playlist.
     * @param proprietaire Le propriétaire de la Playlist.
     */
    public Playlist(String nom, User proprietaire) {
        this.nom = nom;
        this.proprietaire = proprietaire;
        this.songs = FXCollections.observableArrayList();;
        this.collaborateurs = FXCollections.observableArrayList();;
    }
    /**
     * Retourne la liste des collaborateurs de la Playlist.
     *
     * @return La liste des collaborateurs de la Playlist.
     */
    public ObservableList<User> getCollaborateurs() {
        return collaborateurs;
    }

    @Override
    public String toString() {
        return nom;
    }
    /**
     * Ajoute une chanson à la Playlist.
     *
     * @param s La chanson à ajouter à la Playlist.
     */
    public void addSong(Song s) {
        songs.add(s);
    }
    /**
     * Supprime une chanson de la Playlist.
     *
     * @param s La chanson à supprimer de la Playlist.
     */
    public void removeSong(Song s) {
        songs.remove(s);
    }
    /**
     * Retourne la liste des chansons de la Playlist.
     *
     * @return La liste des chansons de la Playlist.
     */
    public ObservableList<Song> getSongs() {
        return songs;
    }
    /**
     * Ajoute un collaborateur à la Playlist.
     *
     * @param u L'utilisateur à ajouter en tant que collaborateur.
     */
    public void addCollaborateur(User u){
        collaborateurs.add(u);
    }
    /**
     * Retourne le propriétaire de la Playlist.
     *
     * @return Le propriétaire de la Playlist.
     */
    public User getProprietaire() {
        return proprietaire;
    }
    /**
     * Retourne le titre de la Playlist.
     *
     * @return Le titre de la Playlist.
     */

    public String getTitre() {
        return nom;
    }
    /**
     * Supprime un collaborateur de la Playlist.
     *
     * @param user L'utilisateur à supprimer de la liste des collaborateurs.
     */
    public void removeCollaborateur(User user) {
        collaborateurs.remove(user);
    }
}
