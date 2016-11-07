package edu.insightr.spellmonger.View;

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

/**
 * Created by antho on 19/10/2016.
 * Draws the board
 */
public class V_BoardCard {
    Image img;
    Image img2;
    Image img3;
    Image logo_go;
    static Stage primaryStage;

    V_BoardCard(Image img, Image img2, Image img3, Image logo_go, Stage primaryStage) {
        this.img = img;
        this.img2 = img2;
        this.img3 = img3;
        this.logo_go = logo_go;
        V_BoardCard.primaryStage = primaryStage;
    }

    public void display() {

        primaryStage.close();
        Stage board = new Stage();
        board.getIcons().add(new Image("/logo_esilv.png"));
        board.initModality(Modality.APPLICATION_MODAL);
        board.setFullScreen(true);
        board.setTitle("SpellMonger");


        //set menubar
        final javafx.scene.control.MenuBar menuBar = Usefull.MenuBar(board);

        //Set 2 graveyard
        Button graveyardP1 = new Button();
        graveyardP1.setGraphic(new ImageView(img2));
        Button graveyardP2 = new Button();
        graveyardP2.setGraphic(new ImageView(img2));

        // Sets button position to the middle
        Button btnCenterP1 = new Button();
        Button btnCenterP2 = new Button();
        Button btnPlay = new Button("Play");



       //Card From Controller filling the Board
        int n =10;
        Button[] CardP1 = CreateCard(n, img);
        Button[] CardP2 = CreateCard(n, img3);

        // Sets Hbox for both top and bottom
        HBox topMenu = new HBox();
        topMenu.getChildren().addAll(CardP1);
        for(int i=0;i<n;i++) {
        SetCardOnAction(CardP1[i],btnCenterP1);
        }

        HBox botMenu = new HBox();
        botMenu.getChildren().addAll(CardP2);
        for(int i=0;i<n;i++) {
        SetCardOnAction(CardP2[i],btnCenterP2);
        }

        //add menubar plus cards
        VBox vbox_items = new VBox();
        vbox_items.getChildren().addAll(menuBar,topMenu);

        //Layout setup
        BorderPane layout =  new BorderPane();

        VBox leftMenu = new VBox();
        VBox rightMenu = new VBox();
        VBox centerMenu = new VBox();


        layout.setTop(vbox_items);
        BorderPane.setAlignment(vbox_items, Pos.TOP_CENTER);

        layout.setBottom(botMenu);
        BorderPane.setAlignment(botMenu, Pos.BOTTOM_CENTER);

        leftMenu.getChildren().addAll(graveyardP1, graveyardP2);
        layout.setLeft(leftMenu);
        BorderPane.setAlignment(leftMenu, Pos.CENTER_LEFT);

        rightMenu.getChildren().addAll(btnPlay);
        layout.setRight(rightMenu);
        BorderPane.setAlignment(rightMenu, Pos.CENTER_RIGHT);

        centerMenu.getChildren().addAll(btnCenterP1, btnCenterP2);
        layout.setCenter(centerMenu);
        BorderPane.setAlignment(centerMenu, Pos.CENTER);

        //Set button play

        SetCardPlayOnAction(btnPlay,btnCenterP1,btnCenterP2,graveyardP1,graveyardP2);


        //Set scene and AlertBox it
        Scene scene = new Scene(layout);
        board.setScene(scene);
        board.show();


    }
// Function When card choose in hand : Transfer card to the field
    private static void SetCardOnAction(Button card, Button destination) {
        card.setOnAction(e -> {
            Button Temps = new Button();
            Temps.setGraphic(destination.getGraphic());
            destination.setGraphic(card.getGraphic());
            card.setGraphic(Temps.getGraphic());
        });
    }

    //Function when button play pressed : tranfers cards only on both field to their Graveyard respective
    private static void SetCardPlayOnAction(Button play, Button btn_centerP1,Button btn_centerP2, Button graveyardP1,Button graveyardP2){
        play.setOnAction(e -> {
            if (btn_centerP1.getGraphic() != null && btn_centerP2.getGraphic() != null) {
                graveyardP1.setGraphic(btn_centerP1.getGraphic());
                graveyardP2.setGraphic(btn_centerP2.getGraphic());
                btn_centerP1.setGraphic(null);
                btn_centerP2.setGraphic(null);
            } else Usefull.AlertBox("Invalid", "\n Please Card on both Field \n");
        });
    }

    public static Button[] CreateCard(int n, Image img) {
        Button tab[]= new Button[n];
        for (int i = 0; i < n; i++) {
            tab[i] = new Button("", new ImageView(img));
        }
        return tab;
    }
}
