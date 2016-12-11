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
 * Defines the IA view
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_IA implements IObserver {
    private Image img3;
    private Image bear;
    private Image eagle;
    private Image heal;
    private Image poison;
    private Image wolf;
    private Image shield;
    private C_SpellmongerApp controller; // temporary solution

    private String name_current, name_opponent;
    private Label actionTarget = new Label();
    private ArrayList<String> cardNames;
    private ArrayList<Button> card_opponent, cards_current;
    private int round;
    private String playedCard, opponentCard;
    private int points_opponent, points_current;


    private int id_player, id_opponent;


    public V_BoardCard_IA(Stage primaryStage, C_SpellmongerApp controller, int player_id) {

        this.img3 = new Image(getClass().getResourceAsStream("/img3.jpg"));
        this.bear = new Image(getClass().getResourceAsStream("/bear.png"));
        this.eagle = new Image(getClass().getResourceAsStream("/eagle.png"));
        this.heal = new Image(getClass().getResourceAsStream("/heal.png"));
        this.poison = new Image(getClass().getResourceAsStream("/poison.png"));
        this.shield = new Image(getClass().getResourceAsStream("/shield.png"));
        this.wolf = new Image(getClass().getResourceAsStream("/wolf.png"));

        this.controller = controller;
        // nameP1 and P2 can be deleted
        this.id_player = player_id;
        if(id_player == 0) id_opponent = 1;
        else id_opponent = 0;

        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];

        this.points_current=20;
        this.points_opponent=20;

        this.cardNames = controller.get3Cards(id_player);
        this.round = 1;

    }


    public void display() {
        Stage V_BoardCard_P2 = new Stage();
        V_BoardCard_P2 = presentation(V_BoardCard_P2);
        try {
            if(id_player==0) V_BoardCard_P2.setX(900.0);
            else V_BoardCard_P2.setX(300.0);

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
        Label labelP1 = new Label(name_current);
        Label labelP2 = new Label(name_opponent);


        // Set id for stylesheet
        bottomBox.setId("mabox");
        actionTarget.setId("monLabel");
        btnCenterP1.setId("playCard");
        btnCenterP2.setId("playCard");
        graveyardP1.setId("playCard");
        graveyardP2.setId("playCard");
        actionTarget.setText(this.name_current + " : " + points_current + "  " + this.name_opponent + " : " + points_opponent + " ");

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
            V_Utilities.getInstance().SetCardOnAction(card_opponent.get(i), btnCenterP1);
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

        bottomBox.getChildren().add(actionTarget);
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


    //Function when button play pressed : transfers cards_current only on both field to their Graveyard respective
    private void SetCardPlayOnAction(Button btn_centerP1, Button btn_centerP2, Button graveyardP1, Button graveyardP2) {

        if (btn_centerP1.getGraphic() != null && btn_centerP2.getGraphic() != null) {
            graveyardP1.setGraphic(btn_centerP1.getGraphic());
            graveyardP2.setGraphic(btn_centerP2.getGraphic());
            btn_centerP1.setGraphic(null);
            btn_centerP2.setGraphic(null);
            controller.playTurn();

        } else V_Utilities.getInstance().AlertBox("Invalid", "\n Please Card on both Field \n");

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


    private void updatePlayerName() {
        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];
    }

    private void updateLifePoints() {
        this.points_current = controller.getPlayerPoints();
        this.points_opponent = controller.getOpponentPoints();
    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            C_SpellmongerApp controller = (C_SpellmongerApp) o;
            updatePlayerName();//  and update data for view
            updateLifePoints();
            controller.setPlayedCardNames(playedCard, id_player);
            // this.opponentCardName=controller.getOpponentCard(nameP2);



            // For example controller.getNames and update data for view
        }

    }
}