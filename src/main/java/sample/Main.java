package sample;

import edu.insightr.spellmonger.SpellmongerApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
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
        primaryStage.getIcons().add(new Image("/lofo_esilv.png"));
        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        Image img3_2 = new Image(getClass().getResourceAsStream("/img3_2.jpg"));
        Image img3_3 = new Image(getClass().getResourceAsStream("/img3_3.jpg"));
        Image img3_4 = new Image(getClass().getResourceAsStream("/img3_4.jpg"));
        Image img3_5 = new Image(getClass().getResourceAsStream("/img3_5.jpg"));
        //Set the go for open Window V_BoardCard
        Button go = new Button("GO");






        //add button and set scene
       StackPane layout = new StackPane();
        layout.getChildren().add(go);
        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        go.setOnAction(e-> V_BoardCard.display(img,img2,img3,img3_2,img3_3,img3_4,img3_5));


    }


}