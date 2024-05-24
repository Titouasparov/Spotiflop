package model;

import Exceptions.CantAddChildren;
import Exceptions.CantAddExistingFriend;

import java.time.LocalDate;
/**
 * AdultGuest est une sous-classe de Guest, représentant un utilisateur invité adulte dans le système.
 * Un AdultGuest a un pseudo, un mot de passe, une date de naissance, et un parent User.
 */
public class AdultGuest extends Guest {
    /**
     * Construit un AdultGuest avec le pseudo, le mot de passe, la date de naissance, et le parent User donnés.
     *
     * @param pseudo         Le pseudo de l'AdultGuest.
     * @param password       Le mot de passe de l'AdultGuest.
     * @param date_naissance La date de naissance de l'AdultGuest.
     * @param parent         Le parent User de l'AdultGuest.
     */
    public AdultGuest(String pseudo, String password, LocalDate date_naissance,User parent) {
        super(pseudo, password, date_naissance,parent);
    }
    /**
     * Ajoute un ami à la liste d'amis de l'AdultGuest.
     * La méthode lance une exception si l'ami à ajouter est un ChildGuest ou si l'ami existe déjà dans la liste d'amis.
     *
     * @param user L'User à ajouter en tant qu'ami.
     * @throws CantAddChildren Si l'User à ajouter est un ChildGuest.
     * @throws CantAddExistingFriend Si l'User à ajouter existe déjà dans la liste d'amis.
     */
    @Override
    public void addFriend(User user) throws CantAddChildren, CantAddExistingFriend {
        if(!super.getFriends().contains(user)){
            if(!(user instanceof ChildGuest)){
                super.getFriends().add(user);
                user.getFriends().add(this);
            }
            else{
                throw new CantAddChildren("Vous ne pouvez pas ajouter d'enfant");
            }
        }
        else {
            throw new CantAddExistingFriend();
        }
    }
}
