package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Interfaces.IObservable;
import edu.insightr.spellmonger.Interfaces.IObserver;
import edu.insightr.spellmonger.Model.M_PlayCard;
import edu.insightr.spellmonger.Model.M_Player;
import edu.insightr.spellmonger.Model.M_SmartPlayer;
import edu.insightr.spellmonger.Model.M_SpellmongerApp;
import edu.insightr.spellmonger.View.V_BoardCard_Player;
import edu.insightr.spellmonger.View.V_Menu;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Last modified by Stan
 * Controller of M_SpellmongerApp (model) :
 * <p>
 * This class manage every interactions from the user and the model
 * The Controller is Observable, that means it will notice the view every time
 * an interaction has changed the model
 */
public class C_SpellmongerApp implements IObservable {

    private static final Logger logger = Logger.getLogger(M_SpellmongerApp.class);
    private final M_SpellmongerApp app; // Correspond to the model
    private final List<IObserver> observersList;
    private final M_SmartPlayer playerA;
    private final M_SmartPlayer playerB;
    private final String[] playedCardNames;
    private boolean onePlayerDead;
    private M_Player winner;
    private V_BoardCard_Player viewP1, viewP2;
    private boolean twoPlayers;

    /**
     * Default Constructor for the spellmonger app
     *
     * @param model The link to the model
     */
    C_SpellmongerApp(M_SpellmongerApp model) {

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

        V_BoardCard_Player boardCard_P1 = new V_BoardCard_Player(this, 0);
        this.subscribe(boardCard_P1);
        this.viewP1 = boardCard_P1;
        V_BoardCard_Player boardCard_P2 = new V_BoardCard_Player(this, 1);
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

        V_BoardCard_Player boardCard_P1 = new V_BoardCard_Player(this, 0);
        this.subscribe(boardCard_P1);

        this.displayBoard();

        this.twoPlayers = false;
        //this.playerB = new M_SmartPlayer(this.playerB);
    }

    /**
     * Draw three cards for the players.
     * The 1st M_Player to begin (player1) is the current player
     * the player2 is the opponent
     *
     * @param id_player the id of the player you want to draw card for
     * @return an array of string containing the card names for the display
     */
    public ArrayList<String> get3Cards(int id_player) {
        M_PlayCard card1, card2, card3;
        ArrayList<String> cardsName = new ArrayList<>(3);
        String name;

        M_Player MPlayer = this.app.getPlayer(id_player);
        card1 = MPlayer.getCardsInHand().get(0);
        card2 = MPlayer.getCardsInHand().get(1);
        card3 = MPlayer.getCardsInHand().get(2);

        name = card1.getName();
        cardsName.add(name);
        name = card2.getName();
        cardsName.add(name);
        name = card3.getName();
        cardsName.add(name);

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
     * @return return the life points of the opponent player
     */
    public int getPlayerLifePoints(int idPlayer) {
        return this.app.getPlayer(idPlayer).getLifePoints();
    }


    /**
     * Function called (with a transitional function) by the views. The views gives the number of the player, and the number of the card
     * the player chose to play. Then, it shows the view of the next player if we are in multiplayer, or
     * launches the AI if we are in singleplayer.
     *
     * @param idPlayer     : the id of the player
     * @param idPlayedCard : the if of the played card
     */
    private void playTurn(int idPlayer, int idPlayedCard) {

        // Store the played card
        M_Player MPlayer = this.app.getPlayer(idPlayer);
        M_PlayCard card = MPlayer.playACard(idPlayedCard);
        this.app.playCard(card);

        // IA
        // If we play against the AI, we don't wait for the playerB and directly ask the AI to play. No need to switch
        // the view, a simple update will be enough
        if (!twoPlayers) {
            int id = (playerB.level1());
            logger.info("AI PLAYS : " + id);
            card = playerB.playACard(id);
            this.app.playCard(card);
            this.playRound();

            // TWO PLAYERS
            // If we play with two players, playRound() only if the MPlayer who played is the MPlayer 2 (it's the last one
            // to play)
        } else {
            // If the MPlayer is the MPlayer B, resolve turn

            if (MPlayer == playerB) {
                this.playRound();
            }

            // switch the views
            this.switchViews(idPlayer);

        }
        // update the views
        notifyObserver();


        /*
        M_Player MPlayer = this.app.getPlayer(idPlayer);

        M_PlayCard card = MPlayer.smartPlay(idPlayedCard); // remove the card from the MPlayer's hand

        logger.info("PLAYER CARD " + idPlayedCard);
        this.app.playCard(idPlayer, card); // And plays it
        this.playRound();
        */
    }

    /**
     * Switch the views between P1 and P2
     *
     * @param currentlyVisible : the number of the player whose view is currently shown
     */
    private void switchViews(int currentlyVisible) {

        // switchViews() should only be used if we are in a multiplayer context. But we check it anyway,
        // since it's no use to switch to a view which is not used
        // (it could also create bugs)

        if (twoPlayers) {
            boolean showP1, showP2;

            // Depending of the id of the currently shown view, decide which one has to be shown, and which one has to be
            // hidden
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
    private void resolveTurn() {
        // Retrieve the played cards
        M_PlayCard cardA = this.app.getCardOnBoardOf(0);
        M_PlayCard cardB = this.app.getCardOnBoardOf(1);

        // Log them
        logger.info(playerA.getName() + " puts a [" + cardA + "] to play.");
        logger.info(playerB.getName() + " puts a [" + cardB + "] to play.");

        // Ask the mediator to resolve the turn (telling which players take damages, and how much)
        C_Mediator.getInstance().resolveTurn(this.playerA, this.playerB, cardA, cardB);

        // Log the state of the players
        logger.info(playerB.getName() + " has " + playerB.getLifePoints() + " life points and " + playerA.getName() + " has " + playerA.getLifePoints() + " life points ");
    }


    /**
     * Plays a round. Also checks if the players need to draw cards, or refill their stack
     */
    private void playRound() {
        // Everything is set up, start the game!

        // If it has been declared that a player is dead, don't try to play the round
        if (!onePlayerDead) {

            // playRound() should not be called if the board is not full, but check anyway since it will bug if
            // we play a round with missing cards
            if (this.app.isBoardFull()) {

                logger.info("\n");
                logger.info("***** ROUND " + this.app.getRoundCounter() + " *****");


                // Each players has chosen a card to play
                // The turn is resolved (damage denying, damage dealing, healing, etc)
                this.resolveTurn();

                // Every 3 rounds each players has to draw 3 cards from his stack
                if (0 == (this.app.getRoundCounter() % 3)) {
                    if (!this.app.playersStacksAreEmpty()) {
                        this.app.pop3Cards();
                    }
                    logger.info(playerA.getCardsInHand());
                }

                this.app.nextTurn();

                // check if the players need to refill their stack
                if (this.app.playersStacksAreEmpty() && this.app.playersHandsAreEmpty()) {
                    logger.info("\n");
                    logger.info("******************************");
                    logger.info("No more cards in the CardPool - Refill");
                    logger.info("******************************");

                    // If the players need to refill their stack, we take the cards from the graveyard and
                    // shuffle them into the card pool. Then, we distribute the cards amoung the players so they
                    // have cards to draw
                    this.app.shuffleGraveYardToStack();
                    this.app.distributeCardAmongPlayers();
                }


                // We check if a player is dead.
                if (playerB.isDead()) {
                    winner = playerA;
                    onePlayerDead = true;
                }
                if (playerA.isDead()) {
                    winner = playerB;
                    onePlayerDead = true;
                }
                // If one of the players is dead, we end the game
                if (onePlayerDead) {
                    logger.info("\n");
                    logger.info("******************************");
                    logger.info("THE WINNER IS " + winner.getName() + " !!!");
                    logger.info("******************************");
                    logger.info("Graveyard : " + this.app.getGraveyard());

                    // Stop the game and close windows
                    viewP1.endGame(winner.getName());
                    viewP1.close();
                    viewP2.close();

                    // notifyObserver();
                } else {
                    this.notifyObserver(); // update views (life points)
                    switchViews(1);
                }
            }
        }
    }

    /**
     * @return an array containing the players name
     */
    public String[] getPlayerNames() {
        return this.app.getPlayerNames();
    }

    /**
     * Set the name of the wanted player (whose current name has been given)
     *
     * @param player the player name
     * @param name   the new name
     */
    public void setName(String player, String name) {
        this.app.setName(player, name);
        this.notifyObserver();
    }

    /**
     * Only displays the view for the menu
     */
    void displayMenu() {
        observersList.stream().filter(o -> o instanceof V_Menu).forEach(o -> {
            V_Menu menu = (V_Menu) o;
            menu.display();

        });
    }

    /**
     * Sets the card played by the player whose id has been given
     *
     * @param idPlayer the id of the player
     * @param idCard   the id of the played card
     */
    public void setCardPlayerFromView(int idPlayer, int idCard) {
        // Once we got the card for a player, we send it to play the turn
        playTurn(idPlayer, idCard);
    }

    /**
     * Returns the name of the card played by the opponent of the player whose id has been given
     *
     * @param id : the id of the player
     * @return the name of card
     */
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
     * Only displays the view for the board
     */
    private void displayBoard() {


        observersList.stream().filter(o -> o instanceof V_BoardCard_Player).forEach(o -> {
            V_BoardCard_Player board = (V_BoardCard_Player) o;
            board.display();
        });

        /*
        for (IObserver o : observersList) {
            if (o instanceof V_BoardCard_Player) {
                V_BoardCard_Player board = (V_BoardCard_Player) o;
                board.display();
            }
        }
         */

    }


    @Override
    public boolean subscribe(IObserver observer) {
        return !this.observersList.contains(observer) && this.observersList.add(observer);
    }

    /*
    @Override
    public boolean unsubscribe(IObserver observer) {
        return this.observersList.contains(observer) && this.observersList.remove(observer);
    }*/

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
        M_Player opponent = this.app.getPlayer(id_opponent);
        return opponent.getCardsInHand().size();
    }

    /**
     * Returns the name of the cards in the hand of the player whose id is given
     *
     * @param id_player : the id of the player
     * @return : the name of the cards (ArrayList)
     */
    public ArrayList<String> getCards(int id_player) {
        // Optimized
        M_Player MPlayer = this.app.getPlayer(id_player);
        return MPlayer.getCardsInHand().stream().map(M_PlayCard::getName).collect(Collectors.toCollection(ArrayList::new));

        /*
        Not optimized :
        M_Player MPlayer = this.app.getPlayer(id_player);
        ArrayList<String> names = new ArrayList<>();
        for (M_PlayCard card : MPlayer.getCardsInHand()) names.add(card.getName());
        return names;
         */
    }

    /**
     * Checks if the player whose id is given is the M_Player 2. Returns true if it is the case.
     *
     * @param id_player : the id of the player
     * @return : true if it is the player 2
     */
    public boolean playerIsP2(int id_player) {
        return (id_player == 1);
    }
}
