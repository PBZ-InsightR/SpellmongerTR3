package edu.insightr.spellmonger.Model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Last Modification by
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its playerB
 * <p>
 * There are currently 3 different creatures (Beast) that deals damages to its playerB :
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
 */
public class SpellmongerApp {
    // CARD TYPE NAMES (avoid mistakes)
    public final static String cardNameBear = "Bear";
    public final static String cardNameWolf = "Wolf";
    public final static String cardNameEagle = "Eagle";
    public final static String cardNameHeal = "Heal";
    public final static String cardNamePoison = "Poison";
    public final static String cardNameShield = "Shield";
    public final static ArrayList<String> listOfBeastsName = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private final List<PlayCard> cardPool;
    private final List<PlayCard> graveyard;
    private final List<Player> playersList;
    private final List<PlayCard> cardsOnBoard;
    private Player playerA, playerB;
    private int roundCounter;

    /**
     * Constructor of the class
     *
     * @param playersList : List of players
     *                    Last Modified by : Tara
     */
    public SpellmongerApp(List<String> playersList, int maxLifePoints) {

        this.cardsOnBoard = new ArrayList<>(2);

        this.playersList = createPlayers(playersList, maxLifePoints);
        // this.playersList.remove(1);
        // this.playersList.add(1,createIA("BobAI",maxLifePoints));

        this.playerA = this.playersList.get(0);
        this.playerB = this.playersList.get(1);
        this.roundCounter = 1;
        this.graveyard = new ArrayList<>();

        //Fill the list of possible beasts
        listOfBeastsName.add(cardNameBear);
        listOfBeastsName.add(cardNameEagle);
        listOfBeastsName.add(cardNameWolf);

        // Use the DeckCreator class to fill and shuffle the cards deck
        this.cardPool = DeckCreator.fillCardPool();
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

    public SmartPlayer createIA(String playerNames, int maxLifePoints) {
        return new SmartPlayer(playerNames, maxLifePoints);
    }

    /* ************** Getters *************** */

    /**
     * Get the number of the current round
     *
     * @return the number of the current round
     */
    public int getRoundCounter() {
        return roundCounter;
    }

    /**
     * Get the list of cards in the graveyard
     *
     * @return the of cards in the graveyard (List<PlayCard>)
     */
    public List<PlayCard> getGraveyard() {
        return graveyard;
    }

    /**
     * Returns the current player
     *
     * @return the current player (Player)
     */
    public Player getPlayerA() {
        return this.playerA;
    }

    /**
     * Returns the playerB (the player which is not playing)
     *
     * @return the playerB (Player)
     */
    public Player getPlayerB() {
        return this.playerB;
    }

    /**
     * Returns the list of players
     *
     * @return the list of players
     */
    public Player getPlayer(int playerID) {
        return this.playersList.get(playerID);
    }


    /**
     * Says whether all cards have been played.
     *
     * @return true if the game can continue
     */
    public boolean isThereAnyCardLeft() {
        boolean cardLeft = false;
        for (Player player : this.playersList) {
            if (player.stillHasCards())
                cardLeft = true;
        }
        return cardLeft;
    }

    public String[] getPlayerNames() {
        return new String[]{playersList.get(0).getName(), playersList.get(1).getName()};
    }

    /* ************ End of Getters ************* */


    /* ************** Setters *************** */
    public void setName(String playerNumber, String name) {
        int playerNbr;
        playerNbr = ("P1".equals(playerNumber)) ? 0 : 1;
        this.playersList.get(playerNbr).setName(name);
    }

    /* ************** End of Setters *************** */


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
     * Puts a card on the playboard (called at least one time for each player each turn
     *
     * @param card : the card to be played
     */
    public void playCard(int playerId, PlayCard card) {
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
     * Increment the turns counter
     */
    public void nextTurn() {
        flushPlayedCards();
        /*Player tmp = this.playerB;
        this.playerB = this.playerA;
        this.playerA = tmp;*/
        ++this.roundCounter;

        for (Player player : this.playersList) {
            logger.info("Hand of " + player.getName() + ":");
            String list = "";
            for (PlayCard card : player.getCardsInHand())
                list += card.getName() + ", ";
            logger.info(list);
        }
    }

    /**
     * Distributes all the cards of the card pool to the players
     */
    public void distributeCardAmongPlayers() {
        int numberOfPlayers = this.playersList.size();
        int numberOfCards = this.cardPool.size();

        logger.info("Distributing " + numberOfCards + " cards to " + numberOfPlayers + " players");
        // Check if there is a good number of cards (every player has the same number of cards, and there is
        // no card left
        if (numberOfCards % numberOfPlayers != 0)
            logger.info("The players won't have the same cards number. Changing the size of the card pool is highly suggested!");

        // Each player draws a card until there is no card left
        for (int i = 0; i < numberOfCards; ++i)
            playersList.get(i % numberOfPlayers).drawCardToStack(this);

        logger.info("Each player should have " + playersList.get(0).numberOfCards() + " cards in their Stack.");


        for (Player player : this.playersList) {
            logCards(player.getCardsStack(), "Cards' stack of " + player.getName() + ":");
        }
        // Each player draws 3 cards in their Stack
        this.pop3Cards();

        for (Player player : this.playersList) {
            logCards(player.getCardsInHand(), "Hand of " + player.getName() + ":");
        }

    }

    private void logCards(List<PlayCard> cardList, String cardListInfo) {
        String list = "";
        logger.info(cardListInfo);
        for (PlayCard card : cardList)
            list += card.getName() + ", ";

        logger.info(list);
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


    /**
     * Remove 3 cards from each players stack and add it to their hands
     */
    public void pop3Cards() {
        for (Player player : this.playersList) {
            for (int i = 0; i < 3; i++) {
                player.drawCardFromStack();
            }
        }
    }

    /**
     * @return true if the first player has no cards left
     */
    public boolean playersStacksAreEmpty() {
        return !(this.playersList.get(0).stillHasCards());
    }

    /**
     * Gets the cards from the graveyard, puts it in the card pool and shuffles it
     */
    public void shuffleGraveYardToStack() {
        logger.info("Shuffling graveyard to the cardPool");
        PlayCard card;
        for (int i = 0; i < this.graveyard.size(); i++) {
            card = this.graveyard.get(i);
            this.graveyard.remove(card);
            this.cardPool.add(card);
        }
        Collections.shuffle(this.cardPool);
    }

    public PlayCard getCardsOnBoard(int idPlayer) {
        PlayCard card = this.cardsOnBoard.get(idPlayer);
        return card;
    }

    public void addCardToBoard(PlayCard card) {
        if (this.cardsOnBoard.size() < 2) this.cardsOnBoard.add(card);
    }


    public boolean isBoardFull() {
        boolean board = (this.cardsOnBoard.size() == 2);
        return board;
    }

    /**
     * Returns the last card from the graveyard, or one before depending of the fromEnd given
     *
     * @param fromEnd : the n-th card from the end of the graveyard (0 for the last, 1 for the one before, etc)
     * @return the playcard needed
     */
    public PlayCard getLastCardsGraveyard(int fromEnd) {
        // we need to check if we will look at a card which doesn't exist
        if (fromEnd <= this.graveyard.size() - 1) return this.graveyard.get(this.graveyard.size() - 1 - fromEnd);
        else return  null;
    }

}