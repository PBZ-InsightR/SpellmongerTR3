package sample;

import edu.insightr.spellmonger.SpellmongerApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.lang.reflect.Executable;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.setTitle("SpellmongerTR3");

        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));

        Button button = new Button("",new ImageView(img));
        Button button2 = new Button("", new ImageView(img3));
        Button button3 = new Button("", new ImageView(img2));

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("card draw");
            }
        }); //this handle anonymous inner classes

        HBox layout = new HBox();
        layout.getChildren().addAll(button, button2, button3);
        // layout.getChildren().add(button2);

        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        //Logger logger = Logger.getLogger(SpellmongerApp.class);

      //  URL url = this.getClass().getResource("/sample.fxml");
       // logger.info(url);
      //  try{
      //      Parent root = FXMLLoader.load(url); // la racine est le root
      //      primaryStage.setTitle("SpellMonger");
      //      primaryStage.setScene(new Scene(root, 300, 275));
      //      primaryStage.show();
      //  }
      //  catch(Exception ex)
       // {
      //      logger.error("File not found");
      //  }
    }



    //private ObservableList<Player> personData = FXCollections.observableArrayList();




}