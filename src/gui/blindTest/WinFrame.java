package gui.blindTest;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WinFrame extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox(20); // Add spacing between elements
        vbox.setAlignment(Pos.CENTER); // Center elements in the VBox

        // Load an image and create an ImageView object
        Image image = new Image("file:resources/win.png");
        ImageView imageView = new ImageView(image);

        Label label = new Label("Bravo vous avez gagné !");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font and size

        vbox.getChildren().addAll(imageView, label); // Add ImageView and Label to VBox

        Scene scene = new Scene(vbox, 400, 300); // Increase window size
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}