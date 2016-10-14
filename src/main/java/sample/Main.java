package sample;

import edu.insightr.spellmonger.SpellmongerApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.lang.reflect.Executable;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Logger logger = Logger.getLogger(SpellmongerApp.class);

        URL url = this.getClass().getResource("/sample.fxml");
        logger.info(url);
        try{
            Parent root = FXMLLoader.load(url);
            primaryStage.setTitle("SpellMonger");
            primaryStage.setScene(new Scene(root, 300, 275));

            // la racine est le root




            primaryStage.show();
        }
        catch(Exception ex)
        {
            logger.error("File not found");
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}