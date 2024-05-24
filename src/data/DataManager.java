package data;

import Exceptions.CantAddChildren;
import Exceptions.CantUnfriendParent;
import gui.popUp.Erreur;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * La classe DataManager gère les utilisateurs et les chansons de l'application.
 */
public class DataManager {
    // Liste des utilisateurs
    private static final ObservableList<User> users = FXCollections.observableArrayList();
    // Liste des chansons
    private static final ArrayList<Song> songs = new ArrayList<>();

    static {
        // Ajouter un écouteur pour mettre à jour les écoutes de chaque utilisateur
        users.addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        for (User user : change.getAddedSubList()) {
                            for (Song song : songs) {
                                song.getStreams().put(user, 0);
                            }
                        }
                    }
                }
            }
        });
    }

    public static void generatePeople(){
        // Création d'un utilisateur par défaut
        LocalDate defaultBirthDate = LocalDate.of(1990, 1, 1);
        // Générer 5 utilisateurs
        for (int i = 1; i <= 5; i++) {
            try {
                // Créer un utilisateur avec un nom unique
                User user = new Member("user" + i, "", defaultBirthDate);
                users.add(user);
            } catch (CantAddChildren e) {
                throw new RuntimeException(e);
            }
        }
        SessionManager.setCurrentUser(users.get(0));
    }
    /**
     * Retourne la liste des utilisateurs.
     * @return ArrayList<User> - la liste des utilisateurs.
     */
    public static ObservableList<User> getUsers() {
        return users;
    }

    /**
     * Retourne la liste des chansons.
     * @return ArrayList<Song> - la liste des chansons.
     */
    public static ArrayList<Song> getSongs() {
        return songs;
    }

    public static ObservableList<Song> selectRandomSongs(int n){
        ObservableList<Song> randomSongs = observableArrayList();
        for (int i = 0; i < n; i++) {
            int randomIndex = (int) (Math.random() * songs.size());
            randomSongs.add(songs.get(randomIndex));
        }
        return randomSongs;
    }

    /**
     * Ajoute un utilisateur à la liste des utilisateurs.
     * @param u - l'utilisateur à ajouter.
     */
    public static void addUser(User u) {
        users.add(u);
    }


    /**
     * Vérifie si un utilisateur existe dans la liste des utilisateurs.
     * @param pseudo - le pseudo de l'utilisateur à vérifier.
     * @return boolean - vrai si l'utilisateur existe, faux sinon.
     */
    public static boolean isUserExists(String pseudo) {
        List<User> users = DataManager.getUsers();
        for (User user : users) {
            if (user.getPseudo().equals(pseudo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne un utilisateur à partir de son pseudo.
     * @param pseudo - le pseudo de l'utilisateur à récupérer.
     * @return User - l'utilisateur récupéré, null si non trouvé.
     */
    public static User getUser(String pseudo) {
        List<User> users = DataManager.getUsers();
        for (User user : users) {
            if (user.getPseudo().equals(pseudo)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Supprime un utilisateur et met à jour les listes d'amis et de collaborateurs des autres utilisateurs.
     * @param userToDelete - l'utilisateur à supprimer.
     */
    public static void deleteUser(User userToDelete) {
        List<User> usersToRemoveFromFriends = new ArrayList<>();

        for (User u : users) {
            Iterator<Playlist> playlistIterator = u.getPlaylists().iterator();
            while (playlistIterator.hasNext()) {
                Playlist p = playlistIterator.next();
                if (p != null) {
                    p.removeCollaborateur(userToDelete);
                    if (p.getProprietaire().equals(userToDelete)) {
                        playlistIterator.remove();
                    }
                }
            }
            Iterator<Blind_test> blindTestIterator = u.getBlind_tests().iterator();
            while (blindTestIterator.hasNext()) {
                Blind_test b = blindTestIterator.next();
                if (b != null) {
                    b.removeParticipant(userToDelete);
                    if (b.getProprietaire().equals(userToDelete)) {
                        blindTestIterator.remove();
                    }
                }
            }
            if (u.getFriends().contains(userToDelete)) {
                usersToRemoveFromFriends.add(u);
            }
        }

        for (User u : usersToRemoveFromFriends) {
            u.getFriends().remove(userToDelete);
            if(u instanceof Member){
                Member member = (Member)u;
                member.getFamille().remove(userToDelete);
            }
        }

        users.remove(userToDelete);
    }

    /**
     * Cette méthode permet de retirer un utilisateur de la liste d'amis de l'utilisateur courant.
     * Si l'utilisateur à retirer est un "Guest" et que l'utilisateur courant est son parent, alors l'utilisateur est supprimé.
     * Sinon, l'utilisateur est simplement retiré de la liste d'amis de l'utilisateur courant.
     * De plus, cette méthode supprime toutes les collaborations entre l'utilisateur courant et l'utilisateur à retirer.
     * Cela inclut le retrait de l'utilisateur courant en tant que collaborateur sur toutes les playlists de l'utilisateur à retirer, et vice versa.
     * De même, l'utilisateur courant est retiré en tant que participant à tous les blind tests de l'utilisateur à retirer, et vice versa.
     * @param user - l'utilisateur à retirer de la liste d'amis de l'utilisateur courant.
     */
    public static void unfriend(User user){
        try {
            SessionManager.getCurrentUser().removeFriend(user);
        } catch (CantUnfriendParent e) {
            Erreur.afficherErreur("Vous ne pouvez pas retirer le chef de famille");
            return;
        }
        //On supprime les collaborations
        for (Playlist playlist : user.getPlaylists()) {
            if (playlist.getProprietaire().equals(user)) {
                playlist.removeCollaborateur(SessionManager.getCurrentUser());
            }
        }
        for (Playlist playlist : SessionManager.getCurrentUser().getPlaylists()) {
            if (playlist.getProprietaire().equals(SessionManager.getCurrentUser())) {
                playlist.removeCollaborateur(user);
            }
        }
        for (Blind_test blind_test : user.getBlind_tests()) {
            if (blind_test.getProprietaire().equals(user)) {
                blind_test.removeParticipant(SessionManager.getCurrentUser());
            }
        }
        for (Blind_test blind_test : SessionManager.getCurrentUser().getBlind_tests()) {
            if (blind_test.getProprietaire().equals(SessionManager.getCurrentUser())) {
                blind_test.removeParticipant(user);
            }
        }
        if(user instanceof Guest){
            Guest guest = (Guest)user;
            if(guest.getParent().equals(SessionManager.getCurrentUser())){
                DataManager.deleteUser(user);
            }
            else{
                user.getFriends().remove(SessionManager.getCurrentUser());
            }
        }
        else{
            user.getFriends().remove(SessionManager.getCurrentUser());
        }
    }

    /**
     * Télécharge les chansons à partir d'un fichier CSV.
     * @param fileName - le nom du fichier CSV.
     * @throws IOException - si une erreur se produit lors de la lecture du fichier.
     */
    public static void downloadSongs(String fileName) throws IOException {
        String filePath = "C:\\Users\\beauv\\IdeaProjects\\Spotiflop\\resources\\" + fileName;
        StringBuilder content = new StringBuilder();

        // Lire tout le fichier dans une seule chaîne
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first){
                    first = false;
                    continue;
                }
                else{
                    content.append(line).append("\n");
                }
            }
        }


        // Parse la chaîne pour obtenir les quadruplets
        List<String[]> L = CSVParser.parseCSV(content.toString());
        for (String[] s : L) {
            Song song = new Song(s[0],s[1],s[3]);
            songs.add(song);
        }
    }

    /**
     * Filtre les chansons en fonction d'un texte de recherche.
     * @param songs - la liste des chansons à filtrer.
     * @param searchText - le texte de recherche.
     * @return ObservableList<Song> - la liste des chansons filtrées.
     */
    public static ObservableList<Song> filterData(ArrayList<Song> songs, String searchText, String type) {
        // Créer une liste temporaire pour stocker les chansons filtrées
        ObservableList<Song> filteredList = observableArrayList();

        // Parcourir toutes les chansons
        for (Song song : songs) {
            if (type == "Titre" && song.getTitre().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(song);
            }
            else if (type == "Artiste" && song.getAuteur().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(song);
            }
            else if (type == "Paroles" && song.getParoles().toLowerCase().contains(searchText.toLowerCase())){
                filteredList.add(song);
            }
        }
        return filteredList;
    }

    /**
     * Vérifie si une playlist existe déjà pour l'utilisateur courant.
     * @param nom - le nom de la playlist à vérifier.
     * @return boolean - vrai si la playlist existe, faux sinon.
     */
    public static boolean isPlaylistExist(String nom) {
        for (Playlist p : SessionManager.getCurrentUser().getPlaylists()){
            if (p.getTitre().equals(nom)){
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne les pseudos de tous les utilisateurs.
     * @return ArrayList<String> - la liste des pseudos des utilisateurs.
     */
    public static ArrayList<String> getUsersNames (){
        ArrayList<String> usersNames = new ArrayList<>();
        for (User user : users) {
            usersNames.add(user.getPseudo());
        }
        return usersNames;
    }

    /**
     * Retourne un utilisateur à partir de son pseudo.
     * @param pseudo - le pseudo de l'utilisateur à récupérer.
     * @return User - l'utilisateur récupéré, null si non trouvé.
     */
    public static User nameToUser(String pseudo) {
        for (User user : users) {
            if (user.getPseudo().equals(pseudo)) {
                return user;
            }
        }
        return null;
    }
}