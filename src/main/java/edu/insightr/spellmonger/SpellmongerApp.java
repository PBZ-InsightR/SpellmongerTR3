package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Last Modification by Tara 26/09/2016
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its opponentPlayer
 * <p>
 * There are currently 3 different creatures (Beast) that deals damages to its opponentPlayer :
 * Eagle deals 1 damage
 * Wolf deals 2 damages
 * Bear deals 3 damages
 * <p>
 * There are currently 2 different rituals : curse and blessing
 * Curse deals 3 damages
 * Blessing restore 3 life points
 * <p>
 * Each player begins with 20 life points
 * <p>
 * The first player who has no life points loose the game
 *
 * @author Tara Zhong
 * @author Thomas RAIMBAULT
 * @author Paul Bezault
 */
public class SpellmongerApp {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private Player currentPlayer, opponentPlayer;
    private int roundCounter;
    private List<PlayCard> cardPool;
    private List<PlayCard> graveyard;
    private List<Player> playersList;
    private List<PlayCard> cardsOnBoard;


    // CARD TYPE NAMES (avoid mistakes) : have to USE ENUM instead !
    final static String cardNameBear = "Bear";
    final static String cardNameWolf = "Wolf";
    final static String cardNameEagle = "Eagle";
    final static String cardNameHeal = "Heal";
    final static String cardNamePoison = "Poison";
    final static String cardNameShield = "Shield";

    /**
     * Constructor of the class
     *
     * @param playersList     : List of players
     * @param maxNumberOfCard : the number of cards in the deck
     *                        Last Modified by : Hugues
     */
    private SpellmongerApp(List<String> playersList, int maxLifePoints, int maxNumberOfCard) {

        this.playersList = createPlayers(playersList, maxLifePoints);
        this.playersList.remove(1);
        this.playersList.add(1,createIA("BobAI",maxLifePoints));

        this.currentPlayer = this.playersList.get(0);
        this.opponentPlayer = this.playersList.get(1);
        this.roundCounter = 1;
        this.graveyard = new ArrayList<>();


        // Use the DeckCreator class to fill and shuffle the cards deck
        this.cardPool = DeckCreator.fillCardPool(maxNumberOfCard);
    }

    public static void main(String[] args) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 40;

        List<String> playersList = new ArrayList<>();
        playersList.add("Alice");
        playersList.add("Bob");


        // We create the application
        SpellmongerApp app = new SpellmongerApp(playersList, lifePoints, maxNumberOfCards);

        // We start the game
        app.play();
    }

    /**
     * Creates and returns all the players of the game with the names given in the list
     *
     * @param playersNames  : the list of the name of the players
     * @param maxLifePoints : the life points of the players
     * @return the list of the players
     */
    private List<Player> createPlayers(List<String> playersNames, int maxLifePoints) {
        List<Player> playersList = new ArrayList<>();
        for (String name : playersNames)
            playersList.add(new Player(name, maxLifePoints));
        return playersList;
    }
    private SmartPlayer createIA(String playerNames, int maxLifePoints) {
        return  new SmartPlayer(playerNames,maxLifePoints);
    }

    /**
     * Says whether all cards have been played.
     *
     * @return true if the game can continue
     */
    private boolean isThereAnyCardLeft() {
        boolean cardLeft = false;
        for (Player player : this.playersList) {
            if (player.stillHasCards())
                cardLeft = true;
        }
        return cardLeft;
    }

    /**
     * Launches the game
     */
    private void play() {

        // 	Initialize of the variables
        boolean onePlayerDead = false;
        Player winner = null;
        this.cardsOnBoard = new ArrayList<>();

        // Make the players draw cards to play
        this.distributeCardAmongPlayers();
        // Everything is set up, start the game!
        while (!onePlayerDead) {

            // If no one has cards left, the game is ended
            if (!isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
                break;
            }

            logger.info("\n");
            logger.info("***** ROUND " + roundCounter + " *****");

            // Each players chooses a card to play
            playersPlay();
            // The turn is resolved (damage denying, damage dealing, healing, etc)
            resolveTurn();
            //Switch players
            nextTurn();

            if (opponentPlayer.isDead()) {
                winner = currentPlayer;
                onePlayerDead = true;
            }
            if (currentPlayer.isDead()) {
                winner = opponentPlayer;
                onePlayerDead = true;
            }
        }

        if (onePlayerDead) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Graveyard : " + graveyard);
        }
    }

    /**
     * Adds a card to the graveyard
     *
     * @param used_card : the card which ust be put to the graveyard
     */
    private void discard(PlayCard used_card) {
        graveyard.add(used_card);
        logger.info(used_card.getName() + " added to graveyard ");
    }

    /**
     * Makes the player play their turn
     */

    private void playersPlay() {
        currentPlayer.playACard(this);
        opponentPlayer.playACard(this);
    }

    /**
     * Puts a card on the playboard (called at least one time for each player each turn
     *
     * @param card : the card to be played
     */
    void playCard(PlayCard card) {
        this.cardsOnBoard.add(card);
    }


    /**
     * Flushes the list of played cards during the current turn
     */
    private void flushPlayedCards() {
        for (PlayCard card : this.cardsOnBoard)
            discard(card);
        this.cardsOnBoard.clear();
    }

    /**
     * Resolves the turn after the players have played their cards
     */
    private void resolveTurn() {



        PlayCard cardA = this.cardsOnBoard.get(0);
        PlayCard cardB = this.cardsOnBoard.get(1);

        logger.info(currentPlayer.getName() + " puts a [" + cardA + "] to play.");
        logger.info(opponentPlayer.getName() + " puts a [" + cardB + "] to play.");

        // Somebody played a shield, get out unless the other player play a heal card
        //Two Shields
        if (cardNameShield.equals(cardA.getName()) && cardNameShield.equals(cardB.getName())){logger.info("Nothing Happens");}
        // One shield one heal
        else if (cardNameShield.equals(cardA.getName())) {
            if (cardNameHeal.equals(cardB.getName()))
                cardB.activate(this);
        }
        // One shield one heal
        else if (cardNameShield.equals(cardB.getName())) {
            if (cardNameHeal.equals(cardA.getName()))
                cardA.activate(this);
        }
        // Both card are direct spells
        else if (cardA.isDirect() && cardB.isDirect()) {
            cardA.activate(this);
            cardB.activate(this);
        }
        // One out of two is a spell and the other is a beast
        else if ((!cardA.isDirect() && cardB.isDirect() || (cardA.isDirect() && !cardB.isDirect()))){
            cardA.activate(this);
            cardB.activate(this);
        }
        //Both cards are beasts
        else if (cardA.getDamage() < cardB.getDamage())
            currentPlayer.inflictDamages(cardB.getDamage() - cardA.getDamage());
        else if (cardA.getDamage() > cardB.getDamage())
            opponentPlayer.inflictDamages(cardA.getDamage() - cardB.getDamage());
        //else damage compensate

        logger.info(opponentPlayer.getName() + " has " + opponentPlayer.getLifePoints() + " life points and " + currentPlayer.getName() + " has " + currentPlayer.getLifePoints() + " life points ");
    }




    /**
     * Switch players and increment the turns counter
     */
    private void nextTurn() {
        flushPlayedCards();
        Player tmp = this.opponentPlayer;
        this.opponentPlayer = this.currentPlayer;
        this.currentPlayer = tmp;
        ++this.roundCounter;
    }

    /**
     * Distributes all the cards of the card pool to the players
     */
    private void distributeCardAmongPlayers() {
        int numberOfPlayers = this.playersList.size();
        int numberOfCards = this.cardPool.size();

        logger.info("Distributing " + numberOfCards + " cards to " + numberOfPlayers + " players");
        // Check if there is a good number of cards (every player has the same number of cards, and there is
        // no card left
        if (numberOfCards % numberOfPlayers != 0)
            logger.info("The players won't have the same cards number. Changing the size of the card pool is highly suggested!");

        // Each player draws a card until there is no card left
        for (int i = 0; i < numberOfCards; ++i)
            playersList.get(i % numberOfPlayers).drawACard(this);

        logger.info("Each player should have " + playersList.get(0).numberOfCards() + " cards in their hand.");

        for (Player player : this.playersList) {
            logger.info("Hand of " + player.getName() + ":");
            String list = "";
            for (PlayCard card : player.getCardsInHand())
                list += card.getName() + ", ";

            logger.info(list);
        }

    }

    /**
     * Returns the current player
     *
     * @return the current player (Player)
     */
    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Returns the opponentPlayer (the player which is not playing)
     *
     * @return the opponentPlayer (Player)
     */
    Player getOpponentPlayer() {
        return this.opponentPlayer;
    }


    /**
     * Returns the last card and removes it from the deck
     *
     * @return the last card of the deck
     */
    PlayCard popCard() {
        PlayCard card = this.cardPool.get(this.cardPool.size() - 1);
        this.cardPool.remove(card);
        return card;
    }


}