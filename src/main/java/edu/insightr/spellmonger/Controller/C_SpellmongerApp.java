package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SmartPlayer;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard_IA;
import edu.insightr.spellmonger.View.V_BoardCard_P2;
import edu.insightr.spellmonger.View.V_Menu;
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
    private final SpellmongerApp app; // Correspond to the model
    private final List<IObserver> observersList;
    private final SmartPlayer playerA;
    private final SmartPlayer playerB;
    private final String[] playedCardNames;
    private boolean onePlayerDead;
    private Player winner;
    private V_BoardCard_P2 viewP1, viewP2;
    private boolean twoPlayers;

    /**
     * Default Constructor for the spellmonger app
     *
     * @param model The link to the model
     */
    C_SpellmongerApp(SpellmongerApp model) {

        this.observersList = new ArrayList<>();
        this.app = model; // We create the application
        this.onePlayerDead = false;
        this.playerA = this.app.getPlayerA();
        this.playerB = this.app.getPlayerB();
        this.playedCardNames = new String[2];

    }

    /**
     * Launches the game with two players
     */
    public void play() {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();

        V_BoardCard_P2 boardCard_P1 = new V_BoardCard_P2(this, 0);
        this.subscribe(boardCard_P1);
        this.viewP1 = boardCard_P1;
        V_BoardCard_P2 boardCard_P2 = new V_BoardCard_P2(this, 1);
        this.subscribe(boardCard_P2);
        this.viewP2 = boardCard_P2;

        this.displayBoard();

        //hide the player 2
        this.viewP2.setVisible(false);

        this.twoPlayers = true;

    }

    /**
     * Launches the game with one real player
     * and one IA.
     */
    public void play_IA() {
        // Make the players draw cards to play
        this.app.distributeCardAmongPlayers();

        V_BoardCard_P2 boardCard_P1 = new V_BoardCard_P2(this, 0);
        this.subscribe(boardCard_P1);

        this.displayBoard();

        this.twoPlayers = false;
        //this.playerB = new SmartPlayer(this.playerB);
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
    public int getPlayerAPoints() {
        return playerA.getLifePoints();
    }

    /**
     * @return return the life points of the opponent player
     */
    public int getPlayerBPoints() {
        return playerB.getLifePoints();
    }

    public void playTurn(int idPlayer, int idPlayedCard) {


        // Store the played card
        Player player = this.app.getPlayer(idPlayer);
        PlayCard card = player.playACard(idPlayedCard);
        this.app.playCard(idPlayer, card);


        // IA
        // If we play against the AI, we don't wait for the playerB and directly ask the AI to play. No need to switch
        // the view, a simple update will be enough
        if(!twoPlayers){
            int id = (playerB.level1());
            logger.info("AI PLAYS : " + id);
            card = playerB.playACard(id);
            this.app.playCard(1, card);
            this.playRound();
        }
        else {
            // If the player is the player B, resolve turn

            if (player == playerB) {
                this.playRound();
            }



            // switch the views
            this.switchViews(idPlayer);

        }
        // update the views
        notifyObserver();


        /*
        Player player = this.app.getPlayer(idPlayer);

        PlayCard card = player.smartPlay(idPlayedCard); // remove the card from the player's hand

        logger.info("PLAYER CARD " + idPlayedCard);
        this.app.playCard(idPlayer, card); // And plays it
        this.playRound();
        */
    }

    private void switchViews(int currentlyVisible) {

        if (twoPlayers) {


            boolean showP1, showP2;
            if (currentlyVisible == 0) {
                showP1 = false;
                showP2 = true;
            } else {
                showP1 = true;
                showP2 = false;
            }

            this.viewP1.setVisible(showP1);
            this.viewP2.setVisible(showP2);
        }
    }


    /**
     * Resolves the turn after the players have played their cards
     */
    public void resolveTurn() {
        PlayCard cardA = this.app.getCardsOnBoard(0);
        PlayCard cardB = this.app.getCardsOnBoard(1);
        logger.info(playerA.getName() + " puts a [" + cardA + "] to play.");
        logger.info(playerB.getName() + " puts a [" + cardB + "] to play.");

        Mediator.getInstance().resolveTurn(this.playerA, this.playerB, cardA, cardB);

        logger.info(playerB.getName() + " has " + playerB.getLifePoints() + " life points and " + playerA.getName() + " has " + playerA.getLifePoints() + " life points ");
    }


    /**
     * Plays a turn
     */
    public void playRound() {
        // Everything is set up, start the game!
        if (!onePlayerDead) {

            if (this.app.isBoardFull()) {
                // If no one has cards left, the game is ended

                logger.info("\n");
                logger.info("***** ROUND " + this.app.getRoundCounter() + " *****");


                // Each players has chosen a card to play
                // The turn is resolved (damage denying, damage dealing, healing, etc)
                this.resolveTurn();

                // Every 3 rounds each players has to draw 3 cards from his stack
                if (0 == (this.app.getRoundCounter() % 3)) {

                    // check if the players need to refill their stack
                    if (this.app.playersStacksAreEmpty()) {
                        logger.info("\n");
                        logger.info("******************************");
                        logger.info("No more cards in the CardPool - Refill");
                        logger.info("******************************");

                        this.app.shuffleGraveYardToStack();
                        this.app.distributeCardAmongPlayers();
                    }

                    this.app.pop3Cards();
                    logger.info(playerA.getCardsInHand());
                }

                this.app.nextTurn();


                if (playerB.isDead()) {
                    winner = playerA;
                    onePlayerDead = true;
                }
                if (playerA.isDead()) {
                    winner = playerB;
                    onePlayerDead = true;
                }


                if (onePlayerDead) {
                    logger.info("\n");
                    logger.info("******************************");
                    logger.info("THE WINNER IS " + winner.getName() + " !!!");
                    logger.info("******************************");
                    logger.info("Graveyard : " + this.app.getGraveyard());
                    // notifyObserver();
                } else {
                    switchViews(1);
                }
            }
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

    /**
     * Get the played card from the player by the view
     *
     * @param idPlayer the id of the player
     * @param idCard   the id of the played card
     */
    public void getCardPlayerFromView(int idPlayer, int idCard) {
        // Once we got the card for a player, we send it to play the turn
        playTurn(idPlayer, idCard);
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

    /**
     * Return the name of the last card played by the player
     *
     * @param id_player : the id of the player
     * @return the name of the card (String)
     */
    public String getLastCardNameInGraveyard(int id_player) {
        //last card of graveyard is playerB, before-last is playerA
        // playerA : id = 0, so we put 1-0 = 1 card before the last
        // playerB : id = 1, so we put 1-1 = 0 card before the last (the last)
        if (this.app.getLastCardsGraveyard(1 - id_player) != null)
            return this.app.getLastCardsGraveyard(1 - id_player).getName();
        else return "";
    }

    /**
     * Returns the number of cards in the hand of the opponent of the player
     * whose view called the function
     *
     * @param id_opponent : the id of the opponent
     * @return the number of cards in its hand
     */
    public int getNbCardOpponent(int id_opponent) {
        Player opponent = this.app.getPlayer(id_opponent);
        return opponent.getCardsInHand().size();
    }

    /**
     * Returns the name of the cards in the hand of the player whose id is given
     *
     * @param id_player : the id of the player
     * @return : the name of the cards (ArrayList)
     */
    public ArrayList<String> getCards(int id_player) {
        Player player = this.app.getPlayer(id_player);
        ArrayList<String> names = new ArrayList<>();
        for (PlayCard card : player.getCardsInHand()) names.add(card.getName());
        return names;
    }

    public boolean playerIsP2(int id_player) {
        return (id_player == 1);
    }
}
