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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_P2 implements IObserver {
    private Image img, img2, img3, bear, eagle, heal, poison, wolf, shield;
    private C_SpellmongerApp controller; // temporary solution

    private String nameP1, nameP2;
    private Label actiontarget = new Label();
    private ArrayList<String> cardNames;
    private ArrayList<Button> card_opponent, cards_current;
    private int round;
    private String playedCard;
    private String opponentCardName;

    private int id_player;


    public V_BoardCard_P2(Stage primaryStage, C_SpellmongerApp controller, int player_id) {

        this.img = new Image(getClass().getResourceAsStream("/img.jpg"));
        this.img2 = new Image(getClass().getResourceAsStream("/img2.jpg"));
        this.img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        this.bear = new Image(getClass().getResourceAsStream("/bear.png"));
        this.eagle = new Image(getClass().getResourceAsStream("/eagle.png"));
        this.heal = new Image(getClass().getResourceAsStream("/heal.png"));
        this.poison = new Image(getClass().getResourceAsStream("/poison.png"));
        this.shield = new Image(getClass().getResourceAsStream("/shield.png"));
        this.wolf = new Image(getClass().getResourceAsStream("/wolf.png"));

        this.controller = controller;
        // nameP1 and P2 can be deleted
        this.nameP1 = controller.getPlayerNames()[0];
        this.nameP2 = controller.getPlayerNames()[1];
        this.id_player = player_id;
        this.cardNames = controller.get3Cards(id_player);
        this.round = 1;

    }


    public void display() {
        Stage V_BoardCard_P2 = new Stage();
        V_BoardCard_P2 = presentation(V_BoardCard_P2);
        try {
            V_BoardCard_P2.setX(900.0);
            V_BoardCard_P2.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public Stage presentation(Stage board) {

        Scene scene;
        BorderPane layout = new BorderPane();
        GridPane gridpane = new GridPane();
        HBox bottomBox = new HBox();

        card_opponent = new ArrayList<>();
        cards_current = new ArrayList<>();

        Button graveyardP1 = new Button();
        Button graveyardP2 = new Button();
        Button btnCenterP1 = new Button();
        Button btnCenterP2 = new Button();
        Button btnPlay = new Button("Play");
        Label labelP1 = new Label(nameP1);
        Label labelP2 = new Label(nameP2);


        // Set id for sylesheet
        bottomBox.setId("mabox");
        actiontarget.setId("monLabel");
        btnCenterP1.setId("playCard");
        btnCenterP2.setId("playCard");
        graveyardP1.setId("playCard");
        graveyardP2.setId("playCard");
        actiontarget.setText(this.nameP1 + " : 20  " + this.nameP2 + " : 20 ");

        board.getIcons().add(new Image("/logo_esilv.png"));
        board.setTitle(controller.getPlayerNames()[id_player]); // Display the player name

        // Name of player

        labelP1.setFont(Font.font("Cambria", 32));
        labelP2.setFont(Font.font("Cambria", 32));


        // Set cards_current for player 1

        for (int i = 0; i < 3; i++) {
            Button button = new Button();
            button.setGraphic(new ImageView(img3));
            button.setId("reverseCard");
            card_opponent.add(button);
        }

        for (int i = 0; i < card_opponent.size(); i++) {
            V_Utilities.SetCardOnAction(card_opponent.get(i), btnCenterP1);
        }


        // Get cards_current for player 2

        cardNames = controller.get3Cards(id_player);


        for (int i = 0; i < cardNames.size(); i++) {
            Button button = new Button();
            button.setGraphic(new ImageView((getImage(cardNames.get(i)))));
            button.setId("playCard");
            cards_current.add(button);
        }

        for (int i = 0; i < cards_current.size(); i++) {
            setCardOnAction(cards_current.get(i), btnCenterP2, i);
        }

        // Set button play
        btnPlay.setOnAction(e -> SetCardPlayOnAction(btnCenterP1, btnCenterP2, graveyardP1, graveyardP2));


        // disposition of elements on the board

        layout.setRight(btnPlay);
        BorderPane.setAlignment(btnPlay, Pos.TOP_RIGHT);

        bottomBox.getChildren().add(actiontarget);
        layout.setBottom(bottomBox);
        BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);


        // Dispose the cards_current on the board
        gridpane.add(card_opponent.get(0), 1, 1);
        gridpane.add(card_opponent.get(1), 2, 1);
        gridpane.add(card_opponent.get(2), 3, 1);
        gridpane.add(graveyardP1, 1, 2);
        gridpane.add(btnCenterP1, 2, 2);
        gridpane.add(graveyardP2, 1, 3);
        gridpane.add(btnCenterP2, 2, 3);
        gridpane.add(cards_current.get(0), 1, 4);
        gridpane.add(cards_current.get(1), 2, 4);
        gridpane.add(cards_current.get(2), 3, 4);

        gridpane.setVgap(5.0);
        gridpane.setHgap(5.0);
        layout.setCenter(gridpane);
        BorderPane.setAlignment(gridpane, Pos.CENTER);

        scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        board.setScene(scene);

        return board;

    }

    private Image getImage(String cardName) {
        Image image;
        switch (cardName) {
            case "Bear":
                image = bear;
                break;
            case "Wolf":
                image = wolf;
                break;
            case "Eagle":
                image = eagle;
                break;
            case "Shield":
                image = shield;
                break;
            case "Poison":
                image = poison;
                break;
            case "Heal":
                image = heal;
                break;
            default:
                image = null;
                break;
        }
        return image;
    }

    private void setCardOnAction(Button card, Button destination, int i) {
        card.setOnAction(e -> {

            // If player clicks on an empty cards_current, get this cards_current back on its place
            if (card.getGraphic() == null) {
                card.setGraphic(destination.getGraphic());
                destination.setGraphic(null);
                playedCard = "";
            } else {

                // If a cards_current is already on the board, get this cards_current back on its place
                if (destination.getGraphic() != null) {
                    int position = 0;
                    for (int j = 0; j < cardNames.size(); j++) {
                        if (playedCard.equals(cardNames.get(j))) position = j;
                    }
                    this.cards_current.get(position).setGraphic(destination.getGraphic());
                }

                destination.setGraphic(card.getGraphic());
                playedCard = cardNames.get(i);

                card.setGraphic(null);
            }
        });
    }


    //Function when button play pressed : tranfers cards_current only on both field to their Graveyard respective
    private void SetCardPlayOnAction(Button btn_centerP1, Button btn_centerP2, Button graveyardP1, Button graveyardP2) {

        if (btn_centerP1.getGraphic() != null && btn_centerP2.getGraphic() != null) {
            graveyardP1.setGraphic(btn_centerP1.getGraphic());
            graveyardP2.setGraphic(btn_centerP2.getGraphic());
            btn_centerP1.setGraphic(null);
            btn_centerP2.setGraphic(null);
            controller.playTurn();

        } else V_Utilities.AlertBox("Invalid", "\n Please Card on both Field \n");

        // Have to be moved to controller
        if (round % 3 == 0) {
            cardNames = controller.get3Cards(id_player);

            for (int i = 0; i < cards_current.size(); i++) {
                cards_current.get(i).setGraphic(new ImageView((getImage(cardNames.get(i)))));
            }
            for (int i = 0; i < card_opponent.size(); i++) {
                card_opponent.get(i).setGraphic(new ImageView(img3));
            }

        }
        round++;
    }


    public void updatePlayerName() {
        this.nameP1 = controller.getPlayerNames()[0];
        this.nameP2 = controller.getPlayerNames()[1];
    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            updatePlayerName();//  and update data for view
            controller.setPlayedCardNames(playedCard, 1);
            // this.opponentCardName=controller.getOpponentCard(nameP2);


            // For example controller.getNames and update data for view
        }

    }
}


