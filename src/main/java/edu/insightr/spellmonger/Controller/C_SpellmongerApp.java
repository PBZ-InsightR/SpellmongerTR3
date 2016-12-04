package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard_P1;
import edu.insightr.spellmonger.View.V_BoardCard_P2;
import edu.insightr.spellmonger.View.V_Menu;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Last modified by Tara
 * Controller of SpellmongerApp (model) :
 *
 * This class manage every interactions from the user and the model
 * The Controller is Observable, that means it will notice the view every time
 * an interaction has changed the model
 *
 *
 */
public class C_SpellmongerApp implements IObservable {

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private SpellmongerApp app; // Correspond to the model
    private List<IObserver> observersList;
    private boolean onePlayerDead;

        // 	Initialize of the variables
    private Player winner, currentPlayer, opponentPlayer;

    private String[] playedCardNames;




    public C_SpellmongerApp(SpellmongerApp model) {

        this.observersList = new ArrayList<>();
        this.app = model; // We create the application

        this.onePlayerDead = false;
        this.winner = null;

        this.currentPlayer = this.app.getCurrentPlayer();
        this.opponentPlayer = this.app.getOpponentPlayer();
        this.playedCardNames=new String[2];

    }


    /**
     * Launches the game
     */
    public void play() {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();
    }

    // The 1st Player to begin (player1) is the current player
    // the player2 is the opponent
    public ArrayList<String> get3Cards(String playerName) {
        PlayCard card1, card2, card3;
        ArrayList<String> cardsName=new ArrayList<>(3);
        String name;

        try {
            if (playerName.equalsIgnoreCase(this.getPlayerNames()[0])) {
                card1 = this.app.getCurrentPlayer().getCardsInHand().get(0);
                card2 = this.app.getCurrentPlayer().getCardsInHand().get(1);
                card3 = this.app.getCurrentPlayer().getCardsInHand().get(2);
            } else {
                card1 = this.app.getOpponentPlayer().getCardsInHand().get(0);
                card2 = this.app.getOpponentPlayer().getCardsInHand().get(1);
                card3 = this.app.getOpponentPlayer().getCardsInHand().get(2);
            }

            name = card1.getName();
            cardsName.add(name);
            name = card2.getName();
            cardsName.add(name);
            name = card3.getName();
            cardsName.add(name);
        } catch (Exception ex) {

            logger.info("\n Error in getting hands of player");
        }


        logger.info(playerName + "\n The view get : " + cardsName);
        return cardsName;
    }

    public void setPlayedCardNames(String cardName, int i){
        playedCardNames[i]=cardName;
    }


    public String getOpponentCard(String playerName){
        String opponentCardName="";
        try {
            if (playerName.equalsIgnoreCase(this.getPlayerNames()[0])) {
                opponentCardName = playedCardNames[1];

            } else {
                opponentCardName = playedCardNames[0];
            }
        }catch (Exception ex) {

            logger.info("\n Error in getting hands of player");
        }
        return opponentCardName;
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

    public int getRoundCounter() {
        return this.app.getRoundCounter();
    }

    public String[] getPlayerNames()
    {
        return this.app.getPlayerNames();
    }

    public void setName(String player, String name) {
        this.app.setName(player, name);
        this.notifyObserver();
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

    /**
     * Display only the view for the menu
     */
    public void displayMenu() {
        for (IObserver o : observersList) {
            if (o instanceof V_Menu) {
                V_Menu menu = (V_Menu) o;
                menu.display();

            }
        }
    }

    /**
     * Display only the view for the board
     */
    public void displayBoard() {

        for (IObserver o : observersList) {
            if (o instanceof V_BoardCard_P1) {
                V_BoardCard_P1 board_p1 = (V_BoardCard_P1) o;
                board_p1.display();
            }

            if (o instanceof V_BoardCard_P2) {
                V_BoardCard_P2 board = (V_BoardCard_P2) o;
                board.display();
            }

        }

    }

}
