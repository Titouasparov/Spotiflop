package model;

import Exceptions.CantAddChildren;
import Exceptions.CantAddExistingFriend;
import data.DataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
/**
 * Member est une sous-classe de User, représentant un membre utilisateur dans le système.
 * Un Member a un pseudo, un mot de passe, une date de naissance et une liste observable de User représentant la famille.
 */
public class Member extends User {
    private ObservableList<User> famille;
    /**
     * Construit un Member avec le pseudo, le mot de passe et la date de naissance donnés.
     * Vérifie si l'utilisateur a plus de 18 ans.
     *
     * @param pseudo         Le pseudo du Member.
     * @param password       Le mot de passe du Member.
     * @param date_naissance La date de naissance du Member.
     */
    public Member(String pseudo, String password, LocalDate date_naissance) throws CantAddChildren {
        super(pseudo, password, date_naissance);
        famille = FXCollections.observableArrayList();
        LocalDate now = LocalDate.now();
        LocalDate eighteenYearsAgo = now.minusYears(18);
        if (date_naissance.isAfter(eighteenYearsAgo)) {
            throw new CantAddChildren("Vous avez moins de 18");
        }
    }
    /**
     * Supprime un ami de la liste d'amis du Member.
     * Si l'ami est un membre de la famille, il est également supprimé de la liste de la famille.
     *
     * @param user L'utilisateur à supprimer de la liste d'amis.
     */
    @Override
    public void removeFriend(User user) {
        if (famille.contains(user)) {
            famille.remove(user);
        }
        getFriends().remove(user);
    }
    /**
     * Ajoute un ami à la liste d'amis du Member.
     * Lance une exception si l'ami à ajouter est un ChildGuest ou s'il existe déjà dans la liste d'amis.
     *
     * @param user L'utilisateur à ajouter en tant qu'ami.
     * @throws CantAddChildren Si l'utilisateur à ajouter est un ChildGuest.
     * @throws CantAddExistingFriend Si l'utilisateur à ajouter existe déjà dans la liste d'amis.
     */
    @Override
    public void addFriend(User user) throws CantAddChildren, CantAddExistingFriend {
        if(!((user) instanceof ChildGuest)){
            if(getFriends().contains(user)){
                throw new CantAddExistingFriend();
            }
            else{
                getFriends().add(user);
                user.getFriends().add(this);
            }
        }
        else{
            throw new CantAddChildren("Vous ne pouvez pas ajouter d'enfant");
        }
    }
    /**
     * Ajoute un membre à la liste de la famille du Member.
     * Lance une exception si le membre à ajouter existe déjà dans la liste de la famille.
     *
     * @param user L'utilisateur à ajouter à la liste de la famille.
     * @throws CantAddExistingFriend Si l'utilisateur à ajouter existe déjà dans la liste de la famille.
     */
    public void addFamilyMember(User user) throws CantAddExistingFriend {
        if (famille.contains(user)) {
            throw new CantAddExistingFriend();
        }
        else{
            famille.add(user);
            getFriends().add(user);
            user.getFriends().add(this);
        }
    }
    /**
     * Retourne la liste de la famille du Member.
     *
     * @return La liste de la famille du Member.
     */
    public ObservableList<User> getFamille() {
        return famille;
    }
}
