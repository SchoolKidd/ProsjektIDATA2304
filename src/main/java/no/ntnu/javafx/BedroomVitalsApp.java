package no.ntnu.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

/**
 * Starts the visual representation of the project.
 */
public class BedroomVitalsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = new File("src/main/resources/BedroomVitalsApp.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
