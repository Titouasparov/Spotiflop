package data;

import javafx.beans.property.Property;
import model.Member;
import model.User;

public class SessionManager {
    private static User currentUser;

    private SessionManager() {
        // Empêcher l'instanciation
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
