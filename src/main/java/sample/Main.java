package sample;

import edu.insightr.spellmonger.SpellmongerApp;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.log4j.Logger;

import java.lang.reflect.Executable;
import java.net.URL;

public class Main extends Application {

   // Stage Window;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

       // Window = primaryStage;
        primaryStage.setTitle("SpellmongerTR3");
      //  Window.setTitle("Spell");

        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));


        Button button = new Button("",new ImageView(img));

/*
        button.setOnAction(e-> System.out.println("Draw a card"));
*/

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        HBox topMenu = new HBox();
        Button button_card1 = new Button("Card1");
        Button button_card2 = new Button("Card2");
        Button button_card3 = new Button("Card3");
        Button button_card4 = new Button("Card4");
        Button button_card5 = new Button("Card5");
        topMenu.getChildren().addAll(button_card1,button_card2,button_card3,button_card4,button_card5);

        HBox botMenu = new HBox();
        Button button_card6 = new Button("Card6");
        Button button_card7 = new Button("Card7");
        Button button_card8 = new Button("Card8");
        Button button_card9 = new Button("Card9");
        Button button_card10 = new Button("Card10");
        topMenu.getChildren().addAll(button_card6,button_card7,button_card8,button_card9,button_card10);


        StackPane layout =  new StackPane();


        GridPane.setConstraints(topMenu,0,0);
        GridPane.setConstraints(botMenu,0,0);

        GridPane.setConstraints(button, 2, 0 );

        grid.getChildren().addAll(topMenu,botMenu,button);

        Scene scene = new Scene(grid, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}