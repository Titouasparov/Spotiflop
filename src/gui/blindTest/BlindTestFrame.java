package gui.blindTest;

import data.DataManager;
import data.SessionManager;
import gui.Lyrics.LyricsPane;
import gui.cellFactory.ParticipantCellFactory;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Blind_test;
import model.Song;
import model.User;

import java.util.ArrayList;
import java.util.Collections;

/**
 * La classe BlindTestFrame est utilisée pour gérer le déroulement d'un blind test.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de participer à un blind test.
 * L'utilisateur peut deviner le titre, l'interprète ou le titre et l'interprète d'une chanson en fonction du mode du blind test.
 * L'utilisateur a un certain nombre de chances pour deviner correctement. Si l'utilisateur épuise toutes ses chances, il perd le blind test.
 * Si l'utilisateur devine correctement toutes les chansons du blind test, il gagne.
 * La classe BlindTestFrame gère également le score de l'utilisateur et l'affichage des paroles des chansons.
 */
public class BlindTestFrame extends Application {
    private Blind_test blindTest;
    private SimpleIntegerProperty score;
    private SimpleIntegerProperty nb_erreurs;
    private SimpleIntegerProperty nb_rounds;
    private SimpleIntegerProperty nb_rounds_max;
    private SimpleIntegerProperty nb_erreurs_max;
    private LyricsPane lyricsPane;
    private ObservableMap<String, Pair<String,String>> rounds;
    private ListView<User> participantsListView;
    private String currentTitre;
    private SimpleStringProperty scoreText;
    private SimpleStringProperty nbViesText;
    private SimpleStringProperty wrong;
    private String winnerText;
    private ObservableList<String> titresInterpretes;


    public BlindTestFrame(Blind_test blindTest) {
        this.nb_erreurs = new SimpleIntegerProperty(0);
        this.nb_rounds = new SimpleIntegerProperty(0);
        this.score = new SimpleIntegerProperty(0);
        this.blindTest = blindTest;
        this.nb_rounds_max = new SimpleIntegerProperty(blindTest.getRounds().size());
        this.nb_erreurs_max = new SimpleIntegerProperty(blindTest.getRounds().size()/3);
        this.lyricsPane = new LyricsPane();
        this.rounds = FXCollections.observableHashMap();
        this.rounds.putAll(blindTest.getRounds());
        this.participantsListView = new ListView<>();
        this.currentTitre = "";
        this.scoreText = new SimpleStringProperty();
        this.nbViesText = new SimpleStringProperty();
        this.wrong = new SimpleStringProperty();
        this.winnerText = "";
        this.titresInterpretes = FXCollections.observableArrayList();
    }
    /**
     * Cette méthode est utilisée pour démarrer le blind test.
     * Elle initialise l'interface utilisateur et démarre le premier tour du blind test.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage primaryStage){
        BorderPane root = new BorderPane();
        VBox vCenterBox = new VBox();
        HBox guessBox = new HBox();

        lyricsPane = new LyricsPane();
        participantsListView.setItems(blindTest.getParticipants());
        participantsListView.setCellFactory(new ParticipantCellFactory(blindTest));

        TextField guessField = new TextField();

        RadioButton prop1 = new RadioButton();
        RadioButton prop2 = new RadioButton();
        RadioButton prop3 = new RadioButton();
        ToggleGroup group = new ToggleGroup();
        prop1.setToggleGroup(group);
        prop2.setToggleGroup(group);
        prop3.setToggleGroup(group);


        Button guessButton = new Button("Valider");
        Label scoreLabel = new Label();
        scoreLabel.textProperty().bind(scoreText);
        Label nbErreursLabel = new Label("");
        nbErreursLabel.textProperty().bind(nbViesText);
        Label errorLabel = new Label("");
        errorLabel.textProperty().bind(wrong);
        errorLabel.setStyle("-fx-text-fill: red");

        if(blindTest.getMode() == Blind_test.mode.TITRE_INTERPRETE){
            guessBox.getChildren().addAll(prop1,prop2,prop3, guessButton);
        }
        else{
            guessBox.getChildren().addAll(guessField, guessButton);
        }
        vCenterBox.getChildren().addAll(lyricsPane, guessBox, errorLabel,scoreLabel, nbErreursLabel);
        vCenterBox.setSpacing(10);
        vCenterBox.setAlignment(javafx.geometry.Pos.CENTER);

        guessBox.setAlignment(javafx.geometry.Pos.CENTER);

        setGameMode(guessButton, guessField, group, primaryStage);

        guessBox.setSpacing(10);
        root.setCenter(vCenterBox);
        root.setLeft(participantsListView);
        Scene scene = new Scene(root, 1100, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blind Test");

        refreshLabels();
        startRound(guessField, prop1, prop2, prop3);

        primaryStage.setTitle("Blind Test" + " - " + blindTest.getNom());

        // Obtenir la taille de l'écran
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Positionner la fenêtre au milieu de l'écran
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
        primaryStage.show();
    }
    /**
     * Cette méthode est utilisée pour démarrer un nouveau tour du blind test.
     * Elle sélectionne une chanson aléatoire et affiche ses paroles.
     * @param guessField - le champ de texte où l'utilisateur peut entrer sa devinette.
     * @param prop1 - le premier choix de réponse pour le mode titre et interprète.
     * @param prop2 - le deuxième choix de réponse pour le mode titre et interprète.
     * @param prop3 - le troisième choix de réponse pour le mode titre et interprète.
     */
    private void startRound(TextField guessField, RadioButton prop1, RadioButton prop2, RadioButton prop3) {
        currentTitre = selectRandomTitre();
        refreshWinnerText();
        guessField.setText(winnerText);
        System.out.println(winnerText);
        titresInterpretes = getRandomTitreInterpretes();
        initRadioButtons(titresInterpretes, prop1, prop2, prop3);
        lyricsPane.setLyrics(rounds.get(currentTitre).getValue());
    }


    private String selectRandomTitre() {
        int i = (int)(Math.random()*rounds.size());
        String titre = (String) rounds.keySet().toArray()[i];
        return titre;
    }
    /**
     * Cette méthode est utilisée pour définir le mode de jeu du blind test.
     * Elle définit l'action à effectuer lorsque l'utilisateur clique sur le bouton de validation.
     * @param confirmButton - le bouton de validation.
     * @param guessField - le champ de texte où l'utilisateur peut entrer sa devinette.
     * @param group - le groupe de boutons radio pour le mode titre et interprète.
     * @param primaryStage - le stage principal de l'application.
     */
    private void setGameMode(Button confirmButton, TextField guessField,ToggleGroup group, Stage primaryStage) {
        confirmButton.setOnAction(e -> {
            String guessText = new String();
            if(blindTest.getMode() == Blind_test.mode.TITRE_INTERPRETE){
                guessText = ((RadioButton) group.getSelectedToggle()).getText().toLowerCase();
            }
            else{
                guessText = guessField.getText().toLowerCase();
            }
            if (guessText.equals(winnerText))
            {
                wrong.setValue("");
                if (nb_rounds.getValue() < nb_rounds_max.getValue()) {
                    nb_rounds.setValue(nb_rounds.getValue() + 1);
                    score.setValue(score.getValue() + 1);
                    refreshLabels();
                    rounds.remove(currentTitre);
                    if (nb_rounds.getValue() < nb_rounds_max.getValue()){
                        startRound(guessField, (RadioButton) group.getToggles().get(0), (RadioButton) group.getToggles().get(1), (RadioButton) group.getToggles().get(2));
                    }
                    else {
                        primaryStage.close();
                        win();
                    }
                }
            } else {
                nb_erreurs.setValue(nb_erreurs.getValue() + 1);
                refreshLabels();
                wrong.setValue("Mauvaise réponse vous n'avez plus que " + (nb_erreurs_max.getValue()-nb_erreurs.getValue()) + " chances");
                if (nb_erreurs.getValue() >= nb_erreurs_max.getValue()) {
                    saveScore();
                    primaryStage.close();
                    Stage loseStage = new Stage();
                    LoseFrame loseFrame = new LoseFrame();
                    loseFrame.start(loseStage);
                }
            }
        });
    }
    /**
     * Cette méthode est utilisée pour initialiser les boutons radio pour le mode titre et interprète.
     * Elle définit le texte de chaque bouton radio avec un titre et un interprète aléatoires.
     * @param titresInterpretes - la liste des titres et interprètes à utiliser pour les boutons radio.
     * @param prop1 - le premier bouton radio.
     * @param prop2 - le deuxième bouton radio.
     * @param prop3 - le troisième bouton radio.
     */
    private void initRadioButtons(ObservableList<String> titresInterpretes, RadioButton prop1, RadioButton prop2, RadioButton prop3) {
        prop1.setText(titresInterpretes.get(0));
        prop2.setText(titresInterpretes.get(1));
        prop3.setText(titresInterpretes.get(2));
    }
    /**
     * Cette méthode est utilisée pour rafraîchir les labels de l'interface utilisateur.
     * Elle met à jour le score, le nombre de chances restantes et le message d'erreur.
     */
    private void refreshLabels(){
        this.nbViesText.setValue("Nombre de chances : " + (nb_erreurs_max.getValue()-nb_erreurs.getValue()) + " / " + nb_erreurs_max.getValue());
        this.scoreText.setValue("Score : " + score.getValue() + " / " + nb_rounds_max.getValue());
    }

    private void refreshWinnerText(){
        String winner = new String();
        switch (blindTest.getMode()) {
            case TITRE:
                winner = currentTitre;
                break;
            case INTERPRETE:
                winner = rounds.get(currentTitre).getKey();
                break;
            case TITRE_INTERPRETE:
                winner = currentTitre + " - " + rounds.get(currentTitre).getKey();
                break;
        }
        winnerText = winner.toLowerCase();
    }

    private void win() {
        saveScore();
        Stage winStage = new Stage();
        WinFrame winFrame = new WinFrame();
        winFrame.start(winStage);
    }

    private ObservableList<String> getRandomTitreInterpretes() {
        ObservableList<String> titresInterpretes = FXCollections.observableArrayList();
        ObservableList<Song> randomSongs = DataManager.selectRandomSongs(3);
        String titreInterprete1 = currentTitre + " - " + rounds.get(currentTitre).getKey();
        String titreInterprete2 = randomSongs.getFirst().toString() + " - " + randomSongs.getFirst().getAuteur();
        String titreInterprete3 = randomSongs.get(1).toString() + " - " + randomSongs.get(1).getAuteur();
        titresInterpretes.addAll(titreInterprete1, titreInterprete2, titreInterprete3);
        Collections.shuffle(titresInterpretes);
        return titresInterpretes;
    }


    private void saveScore() {
        blindTest.getScores().put(SessionManager.getCurrentUser(),  new SimpleIntegerProperty(score.getValue()));
    }
}
