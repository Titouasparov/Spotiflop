package model;

import Exceptions.CantUnfriendParent;

import java.time.LocalDate;
/**
 * Guest est une sous-classe abstraite de User, représentant un utilisateur invité dans le système.
 * Un Guest a un pseudo, un mot de passe, une date de naissance, et un parent User.
 */
public abstract class Guest extends User{
    private User parent;
    /**
     * Construit un Guest avec le pseudo, le mot de passe, la date de naissance, et le parent User donnés.
     *
     * @param pseudo         Le pseudo du Guest.
     * @param password       Le mot de passe du Guest.
     * @param date_naissance La date de naissance du Guest.
     * @param parent         Le parent User du Guest.
     */
    public Guest(String pseudo, String password, LocalDate date_naissance,User parent) {
        super(pseudo, password, date_naissance);
        this.parent = parent;
    }
    /**
     * Supprime un ami de la liste d'amis du Guest.
     * La méthode lance une exception si l'ami à supprimer est le parent du Guest.
     *
     * @param user L'User à supprimer de la liste d'amis.
     * @throws CantUnfriendParent Si l'User à supprimer est le parent du Guest.
     */
    @Override
    public void removeFriend(User user) throws CantUnfriendParent {
        if (!user.equals(parent)) {
            getFriends().remove(user);
            user.getFriends().remove(this);
        }
        else{
            throw new CantUnfriendParent("Vous ne pouvez pas supprimer le leader de famille");
        }
    }

    public void setParent(User parent) {
        this.parent = parent;
    }

    public User getParent() {
        return parent;
    }
}
