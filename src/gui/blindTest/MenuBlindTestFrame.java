package gui.blindTest;

import gui.cellFactory.ParticipantCellFactory;
import gui.main.CollabFrame;
import gui.util.ContextMenus;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Blind_test;
import model.User;
/**
 * La classe MenuBlindTestFrame est utilisée pour afficher le menu d'un blind test.
 * Elle affiche une interface utilisateur qui permet à l'utilisateur de voir les participants du blind test, de choisir le mode de jeu et de commencer le jeu.
 */
public class MenuBlindTestFrame extends Application {
    private ListView<User> participantsListView;
    private Blind_test blindTest;
    private ContextMenu contextMenu;
    /**
     * Le constructeur de la classe MenuBlindTestFrame.
     * Il initialise la liste des participants et le blind test.
     * @param blindTest - le blind test.
     */
    public MenuBlindTestFrame(Blind_test blindTest) {
        this.participantsListView = new ListView<>();
        this.blindTest = blindTest;
    }
    /**
     * La méthode start est le point d'entrée principal pour toutes les applications JavaFX.
     * Elle est appelée après que la méthode init ait retourné, et après que le système soit prêt pour que l'application commence à fonctionner.
     * @param primaryStage - le stage principal pour cette application, sur lequel la scène de l'application peut être définie.
     */
    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        VBox participantsVBox = new VBox();
        Label participantsLabel = new Label("Participants");
        participantsListView.setItems(blindTest.getParticipants());
        participantsListView.setCellFactory(new ParticipantCellFactory(blindTest));
        contextMenu = ContextMenus.getContextMenuParticipants(participantsListView, blindTest);
        Button addParticipantButton = new Button("Ajouter un participant");

        VBox gameModeVBox = new VBox();
        Label gameModeLabel = new Label("Mode de jeu");
        RadioButton gameModeTitreRadioButton = new RadioButton("par titre");
        RadioButton gameModeAuteurRadioButton = new RadioButton("par auteur");
        RadioButton gameModeTitreAuteurRadioButton = new RadioButton("les 2");
        ToggleGroup gameModeToggleGroup = new ToggleGroup();
        gameModeTitreRadioButton.setToggleGroup(gameModeToggleGroup);
        gameModeAuteurRadioButton.setToggleGroup(gameModeToggleGroup);
        gameModeTitreAuteurRadioButton.setToggleGroup(gameModeToggleGroup);
        Button startGameButton = new Button("Commencer le jeu");

        participantsVBox.getChildren().addAll(participantsLabel, participantsListView, addParticipantButton);
        gameModeVBox.getChildren().addAll(gameModeLabel, gameModeTitreRadioButton, gameModeAuteurRadioButton, gameModeTitreAuteurRadioButton, startGameButton);

        root.setLeft(participantsVBox);
        root.setCenter(gameModeVBox);

        gameModeVBox.setSpacing(20);
        participantsVBox.setSpacing(20);
        participantsVBox.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gameModeVBox.setAlignment(Pos.CENTER);
        participantsVBox.setAlignment(Pos.CENTER);

        initOnClickEvent();
        initStartButton(startGameButton, gameModeToggleGroup, primaryStage);
        initAddParticipantButton(addParticipantButton);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blind Test : "+ blindTest.getNom() +" de "+ blindTest.getProprietaire().getPseudo());
        primaryStage.show();
    }
    /**
     * Cette méthode initialise le bouton de démarrage du jeu.
     * Elle définit l'action à effectuer lorsque l'utilisateur clique sur le bouton de démarrage du jeu.
     * @param startGameButton - le bouton de démarrage du jeu.
     * @param gameModeToggleGroup - le groupe de boutons radio pour le mode de jeu.
     * @param primaryStage - le stage principal de l'application.
     */
    private void initStartButton(Button startGameButton, ToggleGroup gameModeToggleGroup,Stage primaryStage) {
        startGameButton.setOnAction(e -> {
            ToggleButton selectedRadioButton = (ToggleButton) gameModeToggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                String gameMode = selectedRadioButton.getText();
                if(gameMode.equals("par titre")) {
                    blindTest.setMode(Blind_test.mode.TITRE);
                } else if(gameMode.equals("par auteur")) {
                    blindTest.setMode(Blind_test.mode.INTERPRETE);
                } else if(gameMode.equals("les 2")) {
                    blindTest.setMode(Blind_test.mode.TITRE_INTERPRETE);
                }
                BlindTestFrame blindTestFrame = new BlindTestFrame(blindTest);
                blindTestFrame.start(primaryStage);
            }
        });
    }
    /**
     * Cette méthode initialise l'événement de clic sur la liste des participants.
     * Elle définit l'action à effectuer lorsque l'utilisateur clique sur un participant dans la liste des participants.
     */
    private void initOnClickEvent(){
        participantsListView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                User selectedUser = participantsListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    contextMenu.show(participantsListView, event.getScreenX(), event.getScreenY());
                }
            }
        });
    }
    /**
     * Cette méthode initialise le bouton d'ajout de participant.
     * Elle définit l'action à effectuer lorsque l'utilisateur clique sur le bouton d'ajout de participant.
     * @param addParticipantButton - le bouton d'ajout de participant.
     */
    private void initAddParticipantButton(Button addParticipantButton) {
        addParticipantButton.setOnAction(e -> {
            CollabFrame collabFrame = new CollabFrame(blindTest);
            collabFrame.start(new Stage());
        });
    }
}

