package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

/**
 * Created by antho on 19/10/2016.
 * Draws the board
 */
public class V_BoardCard {

    public static void display(Image img, Image img2, Image img3){
        Stage board = new Stage();
        board.getIcons().add(new Image("/lofo_esilv.png"));
        board.initModality(Modality.APPLICATION_MODAL);
        board.setFullScreen(true);
        board.setTitle("SpellMonger");


        final javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();

        final javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("File");
        final javafx.scene.control.Menu editMenu = new javafx.scene.control.Menu("Edit");
        final javafx.scene.control.Menu helpMenu = new javafx.scene.control.Menu("Help");

//add fullscreen option
        final javafx.scene.control.MenuItem FullScreen = new javafx.scene.control.MenuItem("FullScreen");
        fileMenu.getItems().setAll(FullScreen);
        FullScreen.setOnAction(e-> board.setFullScreen(true));
//add about option
        final javafx.scene.control.MenuItem About = new javafx.scene.control.MenuItem("About..");
        helpMenu.getItems().setAll(About);
        About.setOnAction(e->AlertBox.display("About..", "Program did by Anthony, Stanislas, Sibel, Tara, Vincent"));
        // add exit
        final javafx.scene.control.MenuItem Exit = new javafx.scene.control.MenuItem("Exit");
        fileMenu.getItems().add(Exit);
        Exit.setOnAction(e->board.close());
//add menu to menubar
        menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);




        //Set 3 button with image
        Button btnLeft1 = new Button();
        btnLeft1.setGraphic(new ImageView(img2));
        Button btnLeft2 = new Button();
        btnLeft2.setGraphic(new ImageView(img2));

        // Sets button position to the middle
        Button button_center1 = new Button();
        Button button_center2 = new Button();
        Button btnPlay1 = new Button("Play1");
        Button btnPlay2 = new Button("Play2");

        // Sets Hbox for both top and bottom
        HBox topMenu = new HBox();
        for(int i=0; i<6; i++){
            Button button_card_c = new Button("", new ImageView(img));
            topMenu.getChildren().addAll(button_card_c);
            SetCardOnAction(button_card_c, button_center1);
        }
        //add menubar plus cards
        VBox vbox_items = new VBox();
        vbox_items.getChildren().addAll(menuBar,topMenu);

        HBox botMenu = new HBox();
        for(int i=0; i<6; i++){
            Button button_card_c = new Button("", new ImageView(img3));
            botMenu.getChildren().addAll(button_card_c);
            SetCardOnAction(button_card_c, button_center2);

        }


        //Layout setup
        BorderPane layout =  new BorderPane();

        VBox leftMenu = new VBox();
        VBox rightMenu = new VBox();
        VBox centerMenu = new VBox();


        layout.setTop(vbox_items);
        BorderPane.setAlignment(vbox_items, Pos.TOP_CENTER);

        layout.setBottom(botMenu);
        BorderPane.setAlignment(botMenu, Pos.BOTTOM_CENTER);

        leftMenu.getChildren().addAll(btnLeft1, btnLeft2);
        layout.setLeft(leftMenu);
        layout.setAlignment(leftMenu, Pos.CENTER_LEFT);

        rightMenu.getChildren().addAll(btnPlay1, btnPlay2);
        layout.setRight(rightMenu);
        layout.setAlignment(rightMenu, Pos.CENTER_RIGHT);

        centerMenu.getChildren().addAll(button_center1, button_center2);
        layout.setCenter(centerMenu);
        BorderPane.setAlignment(centerMenu, Pos.CENTER);



        //Set button play

        SetCardPlayOnAction(btnPlay1,button_center1);
        SetCardPlayOnAction(btnPlay2,button_center2);

        //Set scene and display it
        Scene scene = new Scene(layout);
        board.setScene(scene);
        board.showAndWait();

    }

    private static void SetCardOnAction(Button card, Button destination) {
        card.setOnAction(e -> {
            Button Temps = new Button();
            Temps.setText(destination.getText());
            Temps.setGraphic(destination.getGraphic());
            destination.setGraphic(card.getGraphic());
            card.setGraphic(Temps.getGraphic());
        });
    }
    private static void SetCardPlayOnAction(Button play, Button btn_center){
        play.setOnAction(e -> {
            if (btn_center.getGraphic() != null) {
                play.setGraphic(btn_center.getGraphic());
                btn_center.setGraphic(null);
            } else AlertBox.display("Invalid", "\n Please select a card \n");
        });
    }
}
