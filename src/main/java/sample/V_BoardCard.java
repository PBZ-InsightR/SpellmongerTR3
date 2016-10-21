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
        Button button_card1 = new Button("", new ImageView(img));
        Button button_card2 = new Button("",new ImageView(img));
        Button button_card3 = new Button("",new ImageView(img));
        Button button_card4 = new Button("",new ImageView(img));
        Button button_card5 = new Button("",new ImageView(img));
        topMenu.getChildren().addAll(button_card1,button_card2,button_card3,button_card4,button_card5);
        VBox vbox_items = new VBox();
        vbox_items.getChildren().addAll(menuBar,topMenu);

        HBox botMenu = new HBox();
        Button button_card6 = new Button("", new ImageView(img3));
        Button button_card7 = new Button("", new ImageView(img3));
        Button button_card8 = new Button("", new ImageView(img3));
        Button button_card9 = new Button("", new ImageView(img3));
        Button button_card10 = new Button("", new ImageView(img3));
        botMenu.getChildren().addAll(button_card6,button_card7,button_card8,button_card9,button_card10);

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

        SetCardOnAction(button_card1, button_center1);
        SetCardOnAction(button_card2, button_center1);
        SetCardOnAction(button_card3, button_center1);
        SetCardOnAction(button_card4, button_center1);
        SetCardOnAction(button_card5, button_center1);
        SetCardOnAction(button_card6, button_center2);
        SetCardOnAction(button_card7, button_center2);
        SetCardOnAction(button_card8, button_center2);
        SetCardOnAction(button_card9, button_center2);
        SetCardOnAction(button_card10, button_center2);

        btnPlay1.setOnAction(e -> {
            if (button_center1.getGraphic() != null) {
                btnLeft1.setGraphic(button_center1.getGraphic());
                button_center1.setGraphic(null);
            } else AlertBox.display("Invalid", "Please select a card");
        });

        btnPlay2.setOnAction(e -> {
            if (button_center2.getGraphic() != null) {
                btnLeft2.setGraphic(button_center2.getGraphic());
                button_center2.setGraphic(null);
            } else AlertBox.display("Invalid", "Please select a card");
        });

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
}
