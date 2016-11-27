package edu.insightr.spellmonger.View;

import edu.insightr.spellmonger.Controller.C_SpellmongerApp;
import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_P implements IObserver {
    static Stage primaryStage;
    Image img;
    Image img2;
    Image img3;
    C_SpellmongerApp controller; // temporary solution


    public V_BoardCard_P(Stage primaryStage, C_SpellmongerApp controller) {

        //set Image
        Image img = new Image(getClass().getResourceAsStream("/img.jpg"));
        Image img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        Image img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));

        this.img = img;
        this.img2 = img2;
        this.img3 = img3;
        this.controller = controller;
      V_BoardCard_P.primaryStage = primaryStage;
    }


    public void display() {
        Stage V_BoardCard_P1 = new Stage();
        V_BoardCard_P1 = presentation( V_BoardCard_P1 ," V_BoardCard_P1 ");
        Stage V_BoardCard_P2 = new Stage();
        V_BoardCard_P2 = presentation( V_BoardCard_P2 ," V_BoardCard_P2 ");
            try {
                V_BoardCard_P1 .show();
                V_BoardCard_P2.show();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }

    public Stage presentation(Stage board,String titre){



        board.getIcons().add(new Image("/logo_esilv.png"));
      //  board.initModality(Modality.APPLICATION_MODAL);
        board.setTitle(titre);

        //Set 2 graveyard
        Button graveyardP1 = new Button();
        graveyardP1.setGraphic(new ImageView(img2));
        Button graveyardP2 = new Button();
        graveyardP2.setGraphic(new ImageView(img2));

        // Sets button position to the middle
        Button btnCenterP1 = new Button();
        Button btnCenterP2 = new Button();
        Button btnPlay = new Button("Play");

        // Name of player
        Label Player1 = new Label(controller.getPlayerNames()[0]);
        Label Player2 = new Label(controller.getPlayerNames()[1]);
        Player1.setFont(Font.font("Cambria", 32));
        Player2.setFont(Font.font("Cambria", 32));

        //Card From View Manager filling the Board
        int n = 3;
        Button[] player1Cards = V_Utilities.CreateCardArray(n, img);
        Button[] player2Cards = V_Utilities.CreateCardArray(n, img3);

        // Sets Hbox for both top and bottom
        HBox topMenu = new HBox();
        topMenu.getChildren().addAll(player1Cards);
        topMenu.getChildren().addAll(Player1);
        for (int i = 0; i < n; i++) {
            V_Utilities.SetCardOnAction(player1Cards[i], btnCenterP1);
        }

        HBox botMenu = new HBox();
        botMenu.getChildren().addAll(player2Cards);
        botMenu.getChildren().addAll(Player2);
        for (int i = 0; i < n; i++) {
            V_Utilities.SetCardOnAction(player2Cards[i], btnCenterP2);
        }

        //add menubar plus cards
       VBox vbox_items = new VBox();
        vbox_items.getChildren().addAll(topMenu);

        //Layout setup
        BorderPane layout = new BorderPane();

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
        btnPlay.setOnAction(e -> SetCardPlayOnAction(btnCenterP1, btnCenterP2, graveyardP1, graveyardP2));
        // V_BoardCard_P2.primaryStage.setScene(new Scene(new Button("my second window")));

        Scene scene = new Scene(layout);
        board.setScene(scene);

        return board;

    }













    //Function when button play pressed : tranfers cards only on both field to their Graveyard respective
    public void SetCardPlayOnAction(Button btn_centerP1, Button btn_centerP2, Button graveyardP1, Button graveyardP2) {

        if (btn_centerP1.getGraphic() != null && btn_centerP2.getGraphic() != null) {
            graveyardP1.setGraphic(btn_centerP1.getGraphic());
            graveyardP2.setGraphic(btn_centerP2.getGraphic());
            btn_centerP1.setGraphic(null);
            btn_centerP2.setGraphic(null);
            controller.playTurn();

            // controller.nothing();
        } else V_Utilities.AlertBox("Invalid", "\n Please Card on both Field \n");
    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            // For example controller.getNames and update data for view
        }

    }
}


