package edu.insightr.spellmonger.Model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Last Modification by
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its playerB
 * <p>
 * There are currently 3 different creatures (M_Beast) that deals damages to its playerB :
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
public class M_SpellmongerApp {
    public final static String cardNameHeal = "Heal";
    public final static String cardNamePoison = "Poison";
    public final static String cardNameShield = "Shield";
    public final static ArrayList<String> listOfBeastsName = new ArrayList<>();
    // CARD TYPE NAMES (avoid mistakes)
    final static String cardNameBear = "Bear";
    final static String cardNameWolf = "Wolf";
    final static String cardNameEagle = "Eagle";
    private static final Logger logger = Logger.getLogger(M_SpellmongerApp.class);
    private final List<M_PlayCard> cardPool;
    private final List<M_PlayCard> graveyard;
    //private final List<M_Player> playersList;
    private final List<M_SmartPlayer> playersList;
    private final List<M_PlayCard> cardsOnBoard;
    //private M_Player playerA, playerB;
    private M_SmartPlayer playerA, playerB;
    private int roundCounter;

    /**
     * Constructor of the class
     *
     * @param playersList : List of players
     *                    Last Modified by : Tara
     */
    public M_SpellmongerApp(List<String> playersList, int maxLifePoints) {

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

        // Use the M_DeckCreator class to fill and shuffle the cards deck
        this.cardPool = M_DeckCreator.getInstance().fillCardPool();
    }

    /**
     * Creates and returns all the players of the game with the names given in the list
     *
     * @param playersNames  : the list of the name of the players
     * @param maxLifePoints : the life points of the players
     * @return the list of the players
     */
    private List<M_SmartPlayer> createPlayers(List<String> playersNames, int maxLifePoints) {
        return playersNames.stream().map(name -> new M_SmartPlayer(name, maxLifePoints)).collect(Collectors.toList());
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
     * @return the of cards in the graveyard (List<M_PlayCard>)
     */
    public List<M_PlayCard> getGraveyard() {
        return graveyard;
    }

    /**
     * Returns the current player
     *
     * @return the current player (M_Player)
     */
    public M_SmartPlayer getPlayerA() {
        return this.playerA;
    }

    /**
     * Returns the playerB (the player which is not playing)
     *
     * @return the playerB (M_Player)
     */
    public M_SmartPlayer getPlayerB() {
        return this.playerB;
    }

    /**
     * Returns the list of players
     *
     * @return the list of players
     */
    public M_SmartPlayer getPlayer(int playerID) {
        return this.playersList.get(playerID);
    }


    /**
     * Returns an array containing the name of the players
     *
     * @return : the names
     */
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
    private void discard(M_PlayCard used_card) {
        graveyard.add(used_card);
        logger.info(used_card.getName() + " added to graveyard ");
    }


    /**
     * Puts a card on the playboard (called at least one time for each player each turn
     *
     * @param card : the card to be played
     */
    public void playCard(M_PlayCard card) {
        this.cardsOnBoard.add(card);
    }

    /**
     * Flushes the list of played cards during the current turn
     */
    private void flushPlayedCards() {
        this.cardsOnBoard.forEach(this::discard);
        this.cardsOnBoard.clear();
    }

    /**
     * Increment the turns counter
     */
    public void nextTurn() {
        flushPlayedCards();
        /*M_Player tmp = this.playerB;
        this.playerB = this.playerA;
        this.playerA = tmp;*/
        ++this.roundCounter;

        for (M_Player MPlayer : this.playersList) {
            logger.info("Hand of " + MPlayer.getName() + ":");
            String list = "";
            for (M_PlayCard card : MPlayer.getCardsInHand())
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

        logger.info("Each player should have " + playersList.get(0).getnumberOfCards() + " cards in their Stack.");


        for (M_Player MPlayer : this.playersList) {
            logCards(MPlayer.getCardsStack(), "Cards' stack of " + MPlayer.getName() + ":");
        }
        // Each player draws 3 cards in their Stack
        this.pop3Cards();

        for (M_Player MPlayer : this.playersList) {
            logCards(MPlayer.getCardsInHand(), "Hand of " + MPlayer.getName() + ":");
        }

    }

    /**
     * Takes the list of the card and their info, then logs it in a nice way
     *
     * @param cardList     : the list of the cards
     * @param cardListInfo : the info of the cards
     */
    private void logCards(List<M_PlayCard> cardList, String cardListInfo) {
        String list = "";
        logger.info(cardListInfo);
        for (M_PlayCard card : cardList)
            list += card.getName() + ", ";

        logger.info(list);
    }

    /**
     * Returns the last card and removes it from the deck
     *
     * @return the last card of the deck
     */
    M_PlayCard popCard() {
        M_PlayCard card = this.cardPool.get(this.cardPool.size() - 1);
        this.cardPool.remove(card);
        return card;
    }


    /**
     * Remove 3 cards from each players stack and add it to their hands
     */
    public void pop3Cards() {
        for (M_Player MPlayer : this.playersList) {
            for (int i = 0; i < 3; i++) {
                MPlayer.drawCardFromStack();
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
        M_PlayCard card;
        for (int i = 0; i < this.graveyard.size(); i++) {
            card = this.graveyard.get(i);
            this.graveyard.remove(card);
            this.cardPool.add(card);
        }
        Collections.shuffle(this.cardPool);
    }


    /**
     * Return the card played by the player whose id has been given
     *
     * @param idPlayer : if id of the player
     * @return the card played
     */
    public M_PlayCard getCardOnBoardOf(int idPlayer) {
        return this.cardsOnBoard.get(idPlayer);
    }


    /**
     * Returns true if the board is full (all the players have played a card)
     *
     * @return true if full
     */
    public boolean isBoardFull() {
        return (this.cardsOnBoard.size() == 2);
    }

    /**
     * Returns the last card from the graveyard, or one before depending of the fromEnd given
     *
     * @param fromEnd : the n-th card from the end of the graveyard (0 for the last, 1 for the one before, etc)
     * @return the playcard needed
     */
    public M_PlayCard getLastCardsGraveyard(int fromEnd) {
        // we need to check if we will look at a card which doesn't exist
        if (fromEnd <= this.graveyard.size() - 1) return this.graveyard.get(this.graveyard.size() - 1 - fromEnd);
        else return null;
    }

}