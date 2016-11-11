package edu.insightr.spellmonger.View;
/**
 * Created by Yasmeen on 09/11/2016.
 * Launches the window
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/spellmongerApp.fxml"));
        primaryStage.setTitle("SpellmongerApp");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

}