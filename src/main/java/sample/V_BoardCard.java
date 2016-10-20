package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by antho on 19/10/2016.
 * Draws the board
 */
public class V_BoardCard {

    public static void display(Image img, Image img2, Image img3, Image img3_2, Image img3_3, Image img3_4, Image img3_5){

        Stage board = new Stage();
        board.getIcons().add(new Image("/lofo_esilv.png"));
        board.initModality(Modality.APPLICATION_MODAL);
        board.setMinHeight(500);
        board.setMinWidth(1000);
        board.setTitle("SpellMonger");

        //Set 3 button with image
        Button btnLeft = new Button();
        btnLeft.setGraphic(new ImageView(img2));

        // Sets button position to the middle
        Button button_center = new Button();
        Button btnPlay = new Button("Play");

        // Sets Hbox for both top and bottom
        HBox topMenu = new HBox();
        Button button_card1 = new Button("", new ImageView(img));
        Button button_card2 = new Button("",new ImageView(img));
        Button button_card3 = new Button("",new ImageView(img));
        Button button_card4 = new Button("",new ImageView(img));
        Button button_card5 = new Button("",new ImageView(img));
        topMenu.getChildren().addAll(button_card1,button_card2,button_card3,button_card4,button_card5);

        HBox botMenu = new HBox();
        Button button_card6 = new Button("", new ImageView(img3));
        Button button_card7 = new Button("", new ImageView(img3_2));
        Button button_card8 = new Button("", new ImageView(img3_3));
        Button button_card9 = new Button("", new ImageView(img3_4));
        Button button_card10 = new Button("", new ImageView(img3_5));
        botMenu.getChildren().addAll(button_card6,button_card7,button_card8,button_card9,button_card10);

        //Layout setup
        BorderPane layout =  new BorderPane();

        layout.setTop(topMenu);
        BorderPane.setAlignment(topMenu, Pos.TOP_CENTER);

        layout.setBottom(botMenu);
        BorderPane.setAlignment(botMenu, Pos.BOTTOM_CENTER);

        layout.setCenter(button_center);

        layout.setLeft(btnLeft);
        BorderPane.setAlignment(btnLeft, Pos.CENTER);

        layout.setRight(btnPlay);
        BorderPane.setAlignment(btnPlay, Pos.CENTER);

        SetCardOnAction(button_card1, button_center);
        SetCardOnAction(button_card2, button_center);
        SetCardOnAction(button_card3, button_center);
        SetCardOnAction(button_card4, button_center);
        SetCardOnAction(button_card5, button_center);
        SetCardOnAction(button_card6, button_center);
        SetCardOnAction(button_card7, button_center);
        SetCardOnAction(button_card8, button_center);
        SetCardOnAction(button_card9, button_center);
        SetCardOnAction(button_card10, button_center);

        btnPlay.setOnAction(e -> {
            if (button_center.getGraphic() != null) {
                btnLeft.setGraphic(button_center.getGraphic());
                button_center.setGraphic(null);
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
