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

        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));

        //Set 3 button with image
        Button button_img1 = new Button("",new ImageView(img));

        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(img2));

        Button button_img3 = new Button("",new ImageView(img3));

        //Set button at middle
        Button button_center = new Button();
        Button btnPlay = new Button("Play");
/*
        button.setOnAction(e-> System.out.println("Draw a card"));
*/
//set a grip pane not use for moment
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);


//set Hbox for both top and bottom
        HBox topMenu = new HBox();
        Button button_card1 = new Button("", new ImageView(img));
        Button button_card2 = new Button("",new ImageView(img));
        Button button_card3 = new Button("",new ImageView(img));
        Button button_card4 = new Button("",new ImageView(img));
        Button button_card5 = new Button("",new ImageView(img));
        topMenu.getChildren().addAll(button_card1,button_card2,button_card3,button_card4,button_card5);

        HBox botMenu = new HBox();
        Button button_card6 = new Button("Card6");
        Button button_card7 = new Button("Card7");
        Button button_card8 = new Button("Card8");
        Button button_card9 = new Button("Card9");
        Button button_card10 = new Button("Card10");
        topMenu.getChildren().addAll(button_card6,button_card7,button_card8,button_card9,button_card10);



//Set layout
        BorderPane layout =  new BorderPane();

        layout.setTop(topMenu);
        BorderPane.setAlignment(topMenu, Pos.TOP_CENTER);

        layout.setBottom(botMenu);
        BorderPane.setAlignment(botMenu, Pos.TOP_CENTER);

        layout.setCenter(button_center);



        layout.setLeft(btnLeft);
        BorderPane.setAlignment(btnLeft, Pos.CENTER);

        layout.setRight(btnPlay);
        BorderPane.setAlignment(btnPlay, Pos.CENTER);



        //set Action
        button_card1.setOnAction(e->{
            Button Temps = new Button();
//set temps as button 2
            Temps.setText(button_center.getText());
            Temps.setGraphic(button_center.getGraphic());
            //Set button 2 as button press
            button_center.setGraphic(button_card1.getGraphic());
//set button 1
            button_card1.setGraphic(Temps.getGraphic());
        });

        button_card2.setOnAction(e->{
            Button Temps = new Button();
            Temps.setText(button_center.getText());
            Temps.setGraphic(button_center.getGraphic());
            button_center.setGraphic(button_card2.getGraphic());
            button_card2.setGraphic(Temps.getGraphic());
        });

        button_card3.setOnAction(e->{
            Button Temps = new Button();
            Temps.setText(button_center.getText());
            Temps.setGraphic(button_center.getGraphic());
            button_center.setGraphic(button_card3.getGraphic());
            button_card3.setGraphic(Temps.getGraphic());
        });

        button_card4.setOnAction(e->{
            Button Temps = new Button();
            Temps.setText(button_center.getText());
            Temps.setGraphic(button_center.getGraphic());
            button_center.setGraphic(button_card4.getGraphic());
            button_card4.setGraphic(Temps.getGraphic());
        });

        button_card5.setOnAction(e->{
            Button Temps = new Button();
            Temps.setText(button_center.getText());
            Temps.setGraphic(button_center.getGraphic());
            button_center.setGraphic(button_card5.getGraphic());
            button_card5.setGraphic(Temps.getGraphic());
        });

btnPlay.setOnAction(e->{
    btnLeft.setGraphic(button_center.getGraphic());
    button_center.setGraphic(null);
});
        //GridPane.setConstraints(topMenu,0,0);
        //GridPane.setConstraints(botMenu,0,0);

       // GridPane.setConstraints(button, 2, 0 );

        //grid.getChildren().addAll(topMenu,botMenu,button);

        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }


}