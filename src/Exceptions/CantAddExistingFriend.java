package Exceptions;

public class CantAddExistingFriend extends Exception {
    public CantAddExistingFriend() {
        super("Can't add existing friend");
    }
}
