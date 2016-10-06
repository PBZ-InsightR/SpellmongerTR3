package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Last Modification by Tara 26/09/2016
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its opponent
 * <p>
 * There are currently 3 different creatures (Beast) that deals damages to its opponent :
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
    private Player currentPlayer, opponent;
    private int roundCounter;
    private List<PlayCard> cardPool;
    private List<PlayCard> graveyard;
    private ArrayList<Player> playersList;
    private ArrayList<PlayCard> plaidCards;


    /**
     * Constructor of the class
     *
     * @param playersList     : List of players
     * @param maxNumberOfCard : the number of cards in the deck
     *                        Last Modified by : Hugues
     */
    private SpellmongerApp(ArrayList<String> playersList, int maxLifePoints, int maxNumberOfCard) {

        this.playersList = createPlayers(playersList, maxLifePoints);

        this.currentPlayer = this.playersList.get(0);

        this.roundCounter = 1;

        this.graveyard = new ArrayList<>();


        // Use the DeckCreator class to fill and shuffle the cards deck
        this.cardPool = DeckCreator.fillCardPool(maxNumberOfCard);
    }

    public static void main(String[] args) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 70;

        ArrayList<String> playersList = new ArrayList<>();
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
    private ArrayList<Player> createPlayers(ArrayList<String> playersNames, int maxLifePoints) {
        ArrayList<Player> playersList = new ArrayList<>();
        for (String name : playersNames) {
            playersList.add(new Player(name, maxLifePoints, this));
        }
        return playersList;
    }

    /* This function is no longer used
    /**
     * Ritual already played must be add to graveyard
     *
     * @param used_card : used_card must be add to graveyard
     */
    /*
    private void discard(PlayCard used_card){
        graveyard.add(used_card);
        logger.info(used_card.getName() + " added to graveyard ");
    }
    */


    // This function is no longer used
    /*
    /**
     * Deals the damages from the creatures of the current player
     */
    /*
    private void creaturesAttack() {

        ArrayList<PlayCard> beastsList = currentPlayer.getCreatures();
        int totalDamages = 0;
        for (PlayCard beast : beastsList) {
            totalDamages += beast.getDamage();
        }
        logger.info("The beasts of " + this.currentPlayer.getName() + " deal " + totalDamages + " damages to " + this.opponent.getName());
        opponent.inflictDamages(totalDamages);
    }
    */

    /**
     * Says when all cards have been played.
     *
     * @return true if the game can continue
     */
    private boolean isThereAnyCardLeft() {
        return !(this.cardPool.isEmpty());
    }

    /**
     * Launches the game
     */
    private void play() {

        boolean onePlayerDead = false;
        Player winner = currentPlayer;
        this.plaidCards = new ArrayList<>();

        while (!onePlayerDead) {
            if (!isThereAnyCardLeft()) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
                break;
            }


            logger.info("\n");
            logger.info("***** ROUND " + roundCounter);

            // Each players chooses a card to play
            playersPlay();

            // The turn is resolved (damage denying, dame dealing, healing, etc)
            resolveTurn();


            //creaturesAttack();
            logger.info(opponent.getName() + " has " + opponent.getLifePoints() + " life points and " + currentPlayer.getName() + " has " + currentPlayer.getLifePoints() + " life points ");

            if (opponent.isDead()) {
                winner = currentPlayer;
                onePlayerDead = true;
            }

            nextTurn();
        }

        if (isThereAnyCardLeft()) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Beasts controlled by " + currentPlayer.getName());
            logger.info(currentPlayer.getCreatures());
            logger.info("Beasts controlled by " + opponent.getName());
            logger.info(opponent.getCreatures());
            logger.info("Graveyard : " + graveyard);
        }
    }

    /**
     * Makes the player play their turn
     */

    private void playersPlay() {

        for (Player player : this.playersList) {
            player.playACard();
        }
    }

    /**
     * Puts a card on the playboard (called at least one time for each player each turn
     *
     * @param card : the card plaid
     */
    void playCard(PlayCard card) {
        this.plaidCards.add(card);
    }

    /*
    /**
     * Flushes the list of plaid cards during the current turn
     */
    /*private void flushPlaidCards() {
        this.plaidCards.clear();
    }
    */

    /**
     * Resolves the turn after the players have plaid their cards
     */
    private void resolveTurn() {


        // FIRST VERSION
        // For this, we will only use two players. We'll see later if we use more players, and how to do it

        PlayCard cardA = this.plaidCards.get(0);
        PlayCard cardB = this.plaidCards.get(1);


        // If this is a beast-beast state
        if ("Beast".equalsIgnoreCase(cardA.getClass().getSimpleName())
                && "Beast".equalsIgnoreCase(cardB.getClass().getSimpleName())) {

            Beast beastA = (Beast) cardA;
            Beast beastB = (Beast) cardB;

            // First, if the cards have the same damages, we do nothing
            if (!(beastA.getDamage() == beastB.getDamage())) {
                // The player which used the weaker card takes damage equal to the difference of the damage between
                // the two cards
                Beast weaker;
                if (beastA.getDamage() > beastB.getDamage()) weaker = beastB;
                else weaker = beastA;

                weaker.getOwner().inflictDamages(Math.abs(beastA.getDamage() - beastB.getDamage()));
            }

        }

        // If this a ritual-ritual state
        else if ("Ritual".equalsIgnoreCase(cardA.getClass().getSimpleName())
                && "Ritual".equalsIgnoreCase(cardB.getClass().getSimpleName())) {
            Ritual ritualA = (Ritual) cardA;
            Ritual ritualB = (Ritual) cardB;

            //First, apply the healing effects
            if (ritualA.getName().equals("Heal")) ritualA.activate(this);
            if (ritualB.getName().equals("Heal")) ritualB.activate(this);

            //Then apply the poison effects IF the other player didn't use a shield
            if (ritualA.getName().equals("Poison") && !(ritualB.getName().equals("Shield"))) ritualA.activate(this);
            if (ritualB.getName().equals("Poison") && !(ritualA.getName().equals("Shield"))) ritualB.activate(this);

            //In a case of shield-heal or shieldshield, nothing happens so we don't need to code anything
        }

        // If this is a beast - ritual state
        else {
            // Healing - beast or Poison - beast
            if ((cardA.getName().equals("Heal") || cardA.getName().equals("Poison")) && "Beast".equalsIgnoreCase(cardB.getClass().getSimpleName())){
                cardA.activate(this);
                cardA.getOwner().inflictDamages(((Beast)cardB).getDamage());
            }

            else if((cardB.getName().equals("Heal") || cardB.getName().equals("Poison")) && "Beast".equalsIgnoreCase(cardA.getClass().getSimpleName())){
                cardB.activate(this);
                cardB.getOwner().inflictDamages(((Beast)cardB).getDamage());
            }

            // Nothing happens when a shield is plaid

        }

    }


    /* This function is no longer used
    /**
     * Draw A Card
     * Returns the card on the top of the deck (the last) and removes it
     *
     * @return {@code PlayCard} of the card drawn on the card Pool
     */
    /*
    private PlayCard drawACard() {
        PlayCard card = this.cardPool.get(this.cardPool.size() - 1);
        this.cardPool.remove(card);
        return card;
    }
    */

    /**
     * Switch players and increment turns and cardNumbers
     */
    private void nextTurn() {

        Player tmp = opponent;
        opponent = currentPlayer;
        currentPlayer = tmp;
        ++roundCounter;
    }

    Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    Player getOpponent() {
        return this.opponent;
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