package model;

import Exceptions.CantAddAdult;
import Exceptions.CantAddChildren;
import Exceptions.CantAddExistingFriend;

import java.time.LocalDate;
/**
 * ChildGuest est une sous-classe de Guest, représentant un utilisateur invité enfant dans le système.
 * Un ChildGuest a un pseudo, un mot de passe, une date de naissance et un utilisateur parent.
 */
public class ChildGuest extends Guest{
    /**
     * Construit un ChildGuest avec le pseudo, le mot de passe, la date de naissance et l'utilisateur parent donnés.
     *
     * @param pseudo         Le pseudo du ChildGuest.
     * @param password       Le mot de passe du ChildGuest.
     * @param date_naissance La date de naissance du ChildGuest.
     * @param parent         L'utilisateur parent du ChildGuest.
     */
    private User Parent;
    public ChildGuest(String pseudo, String password, LocalDate date_naissance, User parent) throws CantAddChildren {
        super(pseudo, password, date_naissance,parent);
        // Vérifier si l'utilisateur a moins de 18 ans
        LocalDate now = LocalDate.now();
        LocalDate eighteenYearsAgo = now.minusYears(18);
        if (date_naissance.isBefore(eighteenYearsAgo)) {
            throw new CantAddChildren("Vous avez plus de 18 ans.");
        }
    }
    /**
     * Ajoute un ami à la liste d'amis du ChildGuest.
     * La méthode lance une exception si l'ami à ajouter est un AdultGuest ou un Member ou si l'ami existe déjà dans la liste d'amis.
     *
     * @param user L'utilisateur à ajouter en tant qu'ami.
     * @throws CantAddAdult Si l'utilisateur à ajouter est un AdultGuest ou un Member.
     * @throws CantAddExistingFriend Si l'utilisateur à ajouter existe déjà dans la liste d'amis.
     */
    @Override
    public void addFriend(User user) throws CantAddAdult, CantAddExistingFriend {
        if(!super.getFriends().contains(user)){
            if(!(user instanceof AdultGuest) && !(user instanceof Member)){
                super.getFriends().add(user);
                user.getFriends().add(this);
            }
            else{
                throw new CantAddAdult("Vous ne pouvez pas ajouter d'adultes");
            }
        }
        else{
            throw new CantAddExistingFriend();
        }
    }

}
