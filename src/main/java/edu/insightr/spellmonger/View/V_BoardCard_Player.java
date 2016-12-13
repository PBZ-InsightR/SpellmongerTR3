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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Defines the M_Player view
 * Created by Triton on 27/11/2016.
 */
public class V_BoardCard_Player implements IObserver {
    private final Image reverseCard;
    private final Image bear;
    private final Image eagle;
    private final Image heal;
    private final Image poison;
    private final Image wolf;
    private final Image shield;
    private final C_SpellmongerApp controller;
    private final Label actiontarget;
    private final int id_player;
    private final int id_opponent;
    private Stage V_BoardCard_Player;
    private String name_current, name_opponent;
    private ArrayList<String> cardNames;
    private ArrayList<Label> card_opponent;
    private ArrayList<Button> cards_current;
    private Button graveyardOpponent, graveyardPlayer, btnCenterPlayer;
    private Label lblCenterOpponent;
    private String playedCard, opponentCard;
    private int points_opponent, points_current;


    public V_BoardCard_Player(C_SpellmongerApp controller, int player_id) {

        this.reverseCard = new Image(getClass().getResourceAsStream("/img3.jpg"));
        this.bear = new Image(getClass().getResourceAsStream("/bear.png"));
        this.eagle = new Image(getClass().getResourceAsStream("/eagle.png"));
        this.heal = new Image(getClass().getResourceAsStream("/heal.png"));
        this.poison = new Image(getClass().getResourceAsStream("/poison.png"));
        this.shield = new Image(getClass().getResourceAsStream("/shield.png"));
        this.wolf = new Image(getClass().getResourceAsStream("/wolf.png"));

        this.controller = controller;

        this.id_player = player_id;
        if (id_player == 0) id_opponent = 1;
        else id_opponent = 0;

        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];

        this.points_current = 20;
        this.points_opponent = 20;
        this.opponentCard = "";
        this.playedCard = "";

        this.cardNames = controller.get3Cards(id_player);
        this.V_BoardCard_Player = new Stage();
        this.actiontarget = new Label();
    }


    /**
     * Display the view (sets up the look before showing it)
     */
    public void display() {

        V_BoardCard_Player = presentation(V_BoardCard_Player);
        try {
            if (id_player == 0) V_BoardCard_Player.setX(900.0);
            else V_BoardCard_Player.setX(300.0);
            V_BoardCard_Player.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Hides or shows the view depending of the value given
     *
     * @param setVisible : true if the view has to be shown, false otherwise
     */
    public void setVisible(boolean setVisible) {
        if (setVisible) V_BoardCard_Player.show();
        else V_BoardCard_Player.hide();
    }

    /**
     * Sets up the look and feel of the view
     *
     * @param board : the board to work on
     * @return : the board after the changes
     */
    private Stage presentation(Stage board) {

        Scene scene;
        BorderPane layout = new BorderPane();
        GridPane gridpane = new GridPane();
        HBox bottomBox = new HBox();

        card_opponent = new ArrayList<>();
        cards_current = new ArrayList<>();

        graveyardOpponent = new Button();
        graveyardPlayer = new Button();
        lblCenterOpponent = new Label();
        btnCenterPlayer = new Button();
        Button btnPlay = new Button("Play");


        // Set id for stylesheet
        bottomBox.setId("bottombox");
        lblCenterOpponent.setId("playCard");
        btnCenterPlayer.setId("playCard");
        graveyardOpponent.setId("playCard");
        graveyardPlayer.setId("playCard");


        actiontarget.setText(this.name_current + " : " + points_current + "  " + this.name_opponent + " : " + points_opponent + " ");
        board.getIcons().add(new Image("/logo_esilv.png"));
        board.setTitle(controller.getPlayerNames()[id_player]); // Display the player name


        //Look of buttonplay
        btnPlay.setPrefSize(100, 50);
        btnPlay.setFont(Font.font("Cambria", 20));

        //Look of graveyards
        this.graveyardOpponent.setDisable(true);
        this.graveyardPlayer.setDisable(true);


        // Set cards_current for player 1

        for (int i = 0; i < 3; i++) {
            Label card_opp = new Label();
            card_opp.setGraphic(new ImageView(reverseCard));
            card_opp.setId("reverseCard");
            card_opponent.add(card_opp);
        }


        // Get cards_current for player 2

        cardNames = controller.get3Cards(id_player);


        for (String cardName : cardNames) {
            Button button = new Button();
            button.setGraphic(new ImageView((getImage(cardName))));
            button.setId("playCard");
            cards_current.add(button);
        }

        for (int i = 0; i < cards_current.size(); i++) {
            setCardOnAction(cards_current.get(i), btnCenterPlayer, i);
        }

        // Set button play
        btnPlay.setOnAction(e -> SetCardPlayOnAction());


        // disposition of elements on the board

        layout.setRight(btnPlay);
        BorderPane.setAlignment(btnPlay, Pos.CENTER_RIGHT);

        bottomBox.getChildren().add(actiontarget);
        layout.setBottom(bottomBox);
        BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);


        // Dispose the cards_current on the board
        gridpane.add(card_opponent.get(0), 1, 1);
        gridpane.add(card_opponent.get(1), 2, 1);
        gridpane.add(card_opponent.get(2), 3, 1);
        gridpane.add(graveyardOpponent, 1, 2);
        gridpane.add(lblCenterOpponent, 2, 2);
        gridpane.add(graveyardPlayer, 1, 3);
        gridpane.add(btnCenterPlayer, 2, 3);
        gridpane.add(cards_current.get(0), 1, 4);
        gridpane.add(cards_current.get(1), 2, 4);
        gridpane.add(cards_current.get(2), 3, 4);

        gridpane.setVgap(5.0);
        gridpane.setHgap(5.0);
        layout.setCenter(gridpane);
        BorderPane.setAlignment(gridpane, Pos.CENTER);

        if (!("".equals(this.opponentCard))) {
            lblCenterOpponent.setGraphic(new ImageView(reverseCard));
        }

        scene = new Scene(layout);
        scene.getStylesheets().add("style.css");
        board.setScene(scene);

        return board;

    }

    /**
     * Returns the image associated with the name of the card.
     *
     * @param cardName : the name of the card
     * @return the corresponding image
     */
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

    /**
     * Set the action of a card. If it is clicked on it :
     * - Goes to the center if it is in the hand of the player
     * - Goes back to its place if it is already on the center
     *
     * @param card        : the card which needs an action to be set
     * @param destination : the destination (the center)
     * @param i           : the id of the card
     */
    private void setCardOnAction(Button card, Button destination, int i) {
        card.setOnAction(e -> {

            opponentCard = null;
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
                controller.setPlayedCardNames(playedCard, id_player);

                card.setGraphic(null);
            }
        });
    }


    /**
     * Set the action of the central button
     */
    private void SetCardPlayOnAction() {

        if (btnCenterPlayer.getGraphic() != null) {
            lblCenterOpponent.setGraphic(null);
            btnCenterPlayer.setGraphic(null);
            this.opponentCard = controller.getOpponentCard(id_player);

            this.name_current = controller.getPlayerNames()[id_player];
            this.name_opponent = controller.getPlayerNames()[id_opponent];

            this.points_current = controller.getPlayerLifePoints(id_player);
            this.points_opponent = controller.getPlayerLifePoints(id_opponent);

            // Getting the position of playedCard
            int position = 0;
            for (int j = 0; j < cardNames.size(); j++) {
                if (playedCard.equals(cardNames.get(j))) position = j;
            }
            this.controller.setCardPlayerFromView(id_player, position);


        } else AlertBox("Invalid", "\n Please choose a Card \n");

    }


    /**
     * Show who the winner is
     */
    public void endGame(String winner){
        AlertBox("End of Game", "\n The winner is : "+ winner +"\n\n\n");
    }

    /**
     * Close window
     */
    public void close(){
        this.close();
    }

    private void AlertBox(String title, String message) {
        Stage window = new Stage();
        window.getIcons().add(new Image("/logo_esilv.png"));
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button yesBtn = new Button("ok");
        yesBtn.setOnAction(e -> window.close());

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(label, yesBtn);

        Scene scene = new Scene(vbox);
        window.setScene(scene);
        window.showAndWait();
    }


    /**
     * Update the name of the players
     */
    private void updatePlayerName() {
        this.name_current = controller.getPlayerNames()[id_player];
        this.name_opponent = controller.getPlayerNames()[id_opponent];
    }

    /**
     * Updates the life points labels
     */
    private void updateLifePoints() {
        this.points_current = controller.getPlayerLifePoints(id_player);
        this.points_opponent = controller.getPlayerLifePoints(id_opponent);
        actiontarget.setText(this.name_current + " : " + points_current + "  " + this.name_opponent + " : " + points_opponent + " ");
    }

    /**
     * Update the cards of the player and the opponent.
     * The view cannot know what the opponent has in their hand. However, they are oware of the number
     * of cards they have in their hand, and if they are waiting for the player to chose a card (in the
     * case of P2)
     */
    private void updateCards() {
        // OPPONENT

        // first, empty all cards
        for (Label card : card_opponent) {
            card.setGraphic(null);
        }

        // then show as many cards as the opponent has
        int nbCardOpponent = this.controller.getNbCardOpponent(id_opponent);
        int i = 1;
        for (Label card : card_opponent) {
            if (i <= nbCardOpponent) {
                card.setGraphic(new ImageView(reverseCard));
                i++;
            }
        }
        // Check if we are the player 2. If it is the case, then show a reverse card on the board
        if (this.controller.playerIsP2(id_player)) this.lblCenterOpponent.setGraphic(new ImageView(reverseCard));

        // update current
        // first, empty all cards
        for (Button card : cards_current) {
            card.setGraphic(null);
        }

        // then show the cards in hand
        cardNames = controller.getCards(id_player);
        for (i = 0; i < cardNames.size(); i++) {
            cards_current.get(i).setGraphic(new ImageView((getImage(cardNames.get(i)))));
        }
    }

    /**
     * Updates the graveyard of the player and the opponent.
     */
    private void updateGraveyards() {

        // update the graveyard of P1
        String cardName = controller.getLastCardNameInGraveyard(id_opponent);
        if (!cardName.equals("")) this.graveyardOpponent.setGraphic(new ImageView((getImage(cardName))));

        // update the graveyard of P2
        cardName = controller.getLastCardNameInGraveyard(id_player);
        if (!cardName.equals("")) this.graveyardPlayer.setGraphic(new ImageView((getImage(cardName))));


    }

    /**
     * Function that update the view (INCOMPLETE)
     */
    @Override
    public void update(IObservable o) {
        if (o instanceof C_SpellmongerApp) {
            // That's probably a bug.
            // C_SpellmongerApp controller = (C_SpellmongerApp) o;
            updatePlayerName();//  and update data for view
            updateLifePoints();
            updateCards();
            updateGraveyards();


            // For example controller.getNames and update data for view
        }

    }


}


