package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard_IA;
import edu.insightr.spellmonger.View.V_BoardCard_P2;
import edu.insightr.spellmonger.View.V_Menu;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Last modified by Stan
 * Controller of SpellmongerApp (model) :
 * <p>
 * This class manage every interactions from the user and the model
 * The Controller is Observable, that means it will notice the view every time
 * an interaction has changed the model
 */
public class C_SpellmongerApp implements IObservable {

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private SpellmongerApp app; // Correspond to the model
    private List<IObserver> observersList;
    private boolean onePlayerDead;
    private Player winner, currentPlayer, opponentPlayer;
    private String[] playedCardNames;

    /**
     * Default Constructor for the spellmonger app
     *
     * @param model The link to the model
     */
    C_SpellmongerApp(SpellmongerApp model) {

        this.observersList = new ArrayList<>();
        this.app = model; // We create the application

        this.onePlayerDead = false;

        this.currentPlayer = this.app.getCurrentPlayer();
        this.opponentPlayer = this.app.getOpponentPlayer();
        this.playedCardNames = new String[2];

    }

    /**
     * Launches the game with two players
     *
     * @param primaryStage The top container of the window.
     */
    public void play(Stage primaryStage) {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();

        V_BoardCard_P2 boardCard_P1 = new V_BoardCard_P2(this, 0);
        this.subscribe(boardCard_P1);
        V_BoardCard_P2 boardCard_P2 = new V_BoardCard_P2(this, 1);
        this.subscribe(boardCard_P2);

        this.displayBoard();

    }

    /**
     * Launches the game with one real player
     * and one IA.
     *
     * @param primaryStage The top container of the window.
     */
    public void play_IA(Stage primaryStage) {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();

        V_BoardCard_IA boardCard_IA = new V_BoardCard_IA(this, 0);
        this.subscribe(boardCard_IA);

        this.displayBoard();
    }

    /**
     * Draw three cards for the players.
     * The 1st Player to begin (player1) is the current player
     * the player2 is the opponent
     *
     * @param id_player the id of the player you want to draw card for
     * @return an array of string containing the card names for the display
     */
    public ArrayList<String> get3Cards(int id_player) {
        PlayCard card1, card2, card3;
        ArrayList<String> cardsName = new ArrayList<>(3);
        String name;

        Player player = this.app.getPlayer(id_player);
        card1 = player.getCardsInHand().get(0);
        card2 = player.getCardsInHand().get(1);
        card3 = player.getCardsInHand().get(2);

        name = card1.getName();
        cardsName.add(name);
        name = card2.getName();
        cardsName.add(name);
        name = card3.getName();
        cardsName.add(name);

        logger.info(player.getName() + "\n The view get : " + cardsName);
        return cardsName;
    }

    /**
     * Set the name of the cards
     *
     * @param cardName the string containing the name
     * @param i        the index of the card that was played
     */
    public void setPlayedCardNames(String cardName, int i) {
        playedCardNames[i] = cardName;
    }

    /**
     * @return the health of the current player
     */
    public int getPlayerPoints() {
        return currentPlayer.getLifePoints();
    }

    /**
     * @return return the life points of the opponent player
     */
    public int getOpponentPoints() {
        return opponentPlayer.getLifePoints();
    }

    /**
     * Plays a turn
     */
    public void playTurn() {
        // Everything is set up, start the game!
        if (!onePlayerDead) {


            // If no one has cards left, the game is ended
            if (!this.app.isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
            } else {
                logger.info("\n");
                logger.info("***** ROUND " + this.app.getRoundCounter() + " *****");


                // Each players chooses a card to play
                this.app.playersPlay();
                // The turn is resolved (damage denying, damage dealing, healing, etc)
                this.app.resolveTurn();

                // Every 3 rounds each players has to draw 3 cards from his stack
                if (0 == (this.app.getRoundCounter() % 3)) {

                    // check if the players need to refill their stack
                    if (this.app.playersStacksAreEmpty()) this.app.shuffleGraveYardToStack();

                    this.app.pop3Cards();
                    logger.info(currentPlayer.getCardsInHand());
                }


                //Switch players
                this.app.nextTurn();


                if (opponentPlayer.isDead()) {
                    winner = currentPlayer;
                    onePlayerDead = true;
                }
                if (currentPlayer.isDead()) {
                    winner = opponentPlayer;
                    onePlayerDead = true;
                }
            }
        }

        if (onePlayerDead) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Graveyard : " + this.app.getGraveyard());
            // notifyObserver();
        }
    }

    /**
     * @return an array containing the player names
     */
    public String[] getPlayerNames() {
        return this.app.getPlayerNames();
    }

    /**
     * Set the name of the wanted player
     *
     * @param player the player name
     * @param name   the new name
     */
    public void setName(String player, String name) {
        this.app.setName(player, name);
        this.notifyObserver();
    }

    /**
     * Display only the view for the menu
     */
    void displayMenu() {
        observersList.stream().filter(o -> o instanceof V_Menu).forEach(o -> {
            V_Menu menu = (V_Menu) o;
            menu.display();

        });
    }

    public String getOpponentCard(int id) {
        String opponentCardName = "";
        try {
            if (id == 0) {
                opponentCardName = playedCardNames[1];

            } else {
                opponentCardName = playedCardNames[0];
            }
        } catch (Exception ex) {

            logger.info("\n Error in getting hands of player");
        }
        return opponentCardName;
    }

    /**
     * Display only the view for the board
     */
    private void displayBoard() {

        for (IObserver o : observersList) {
            if (o instanceof V_BoardCard_P2) {
                V_BoardCard_P2 board = (V_BoardCard_P2) o;
                board.display();
            }

            if (o instanceof V_BoardCard_IA) {
                V_BoardCard_IA board = (V_BoardCard_IA) o;
                board.display();
            }


        }

    }

    @Override
    public boolean subscribe(IObserver observer) {
        return !this.observersList.contains(observer) && this.observersList.add(observer);
    }

    @Override
    public boolean unsubscribe(IObserver observer) {
        return this.observersList.contains(observer) && this.observersList.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (IObserver o : observersList)
            o.update(this);
    }
}
