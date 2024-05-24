package gui.util;

import gui.main.Accueil;
import gui.main.MenuP;
import javafx.stage.Stage;

/**
 * La classe Util fournit des méthodes utilitaires pour naviguer entre différents stages dans l'application.
 * Elle comprend des méthodes pour retourner à l'écran d'accueil et au menu principal.
 */
public class Util {
    /**
     * La méthode retourAccueil est utilisée pour naviguer vers l'écran d'accueil.
     * Elle crée une nouvelle instance de la classe Accueil et la démarre avec le stage principal donné.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    public static void retourAccueil(Stage primaryStage) {
        Accueil accueil = new Accueil();
        accueil.start(primaryStage);
    }
    /**
     * La méthode retourMenuP est utilisée pour naviguer vers le menu principal.
     * Elle crée une nouvelle instance de la classe MenuP et la démarre avec le stage principal donné.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    public static void retourMenuP(Stage primaryStage) {
        MenuP m = new MenuP();
        m.start(primaryStage);
    }

}
