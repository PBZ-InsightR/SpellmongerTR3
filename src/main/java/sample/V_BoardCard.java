package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;

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


    //set menubar
        final javafx.scene.control.MenuBar menuBar = Usefull.MenuBar(board);



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

        SetCardPlayOnAction(btnPlay1,button_center1, btnLeft1);
        SetCardPlayOnAction(btnPlay2,button_center2,btnLeft2);

        //Set scene and AlertBox it
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
    private static void SetCardPlayOnAction(Button play, Button btn_center, Button Deck){
        play.setOnAction(e -> {
            if (btn_center.getGraphic() != null) {
                Deck.setGraphic(btn_center.getGraphic());
                btn_center.setGraphic(null);
            } else Usefull.AlertBox("Invalid", "\n Please select a card \n");
        });
    }
}
