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
    private ArrayList<PlayCard> playedCards;


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
        this.opponent = this.playersList.get(1);

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
            playersList.add(new Player(name, maxLifePoints));
        }
        return playersList;
    }


    /**
     * Adds a card to the graveyard
     *
     * @param used_card : the card which ust be put to the graveyard
     */
    private void discard(PlayCard used_card) {
        graveyard.add(used_card);
        //logger.info(used_card.getName() + " added to graveyard ");
    }


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
        boolean cardLeft = false;
        for (Player player : this.playersList) {
            if (player.stillHasCards()) cardLeft = true;
        }
        return cardLeft;
    }

    /**
     * Launches the game
     */
    private void play() {

        // 	Initialize of the variables
        boolean onePlayerDead = false;
        Player winner = currentPlayer;
        this.playedCards = new ArrayList<>();

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
            logger.info("***** ROUND " + roundCounter);

            // Each players chooses a card to play
            playersPlay();

            // The turn is resolved (damage denying, dame dealing, healing, etc)
            resolveTurn();

            // Put all plaid cards to the graveyard
            flushPlayedCards();

            //creaturesAttack();


            logger.info(opponent.getName() + " has " + opponent.getLifePoints() + " life points and " + currentPlayer.getName() + " has " + currentPlayer.getLifePoints() + " life points ");

            if (opponent.isDead()) {
                winner = currentPlayer;
                onePlayerDead = true;
            }

            nextTurn();
        }

        if (onePlayerDead) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            /*
            logger.info("Beasts controlled by " + currentPlayer.getName());
            logger.info(currentPlayer.getCreatures());
            logger.info("Beasts controlled by " + opponent.getName());
            logger.info(opponent.getCreatures());
            */
            logger.info("Graveyard : " + graveyard);
        }
    }

    /**
     * Makes the player play their turn
     */

    private void playersPlay() {

        for (Player player : this.playersList) {
            player.playACard(this);
        }
    }

    /**
     * Puts a card on the playboard (called at least one time for each player each turn
     *
     * @param card : the card plaid
     */
    void playCard(PlayCard card) {
        this.playedCards.add(card);
    }


    /**
     * Flushes the list of plaid cards during the current turn
     */
    private void flushPlayedCards() {
        for (PlayCard card : this.playedCards) discard(card);
        this.playedCards.clear();
    }

    /**
     * Resolves the turn after the players have plaid their cards
     */
    private void resolveTurn() {


        // FIRST VERSION
        // For this, we will only use two players. We'll see later if we use more players, and how to do it

        PlayCard cardA = this.playedCards.get(0);
        PlayCard cardB = this.playedCards.get(1);

        logger.info(cardA.getOwner().getName() + " puts a [" + cardA + "] to play.");
        logger.info(cardB.getOwner().getName() + " puts a [" + cardB + "] to play.");

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
                Beast stronger;
                if (beastA.getDamage() > beastB.getDamage()) {
                    weaker = beastB;
                    stronger = beastA;
                } else {
                    weaker = beastA;
                    stronger = beastB;
                }

                int damageBlocked = weaker.getDamage();
                logger.info(damageBlocked + " damages are blocked");

                stronger.activate(this, damageBlocked);
                //weaker.getOwner().inflictDamages(Math.abs(beastA.getDamage() - beastB.getDamage()));
            } else logger.info("Both beasts deal " + beastA.getDamage() + " damages to each other and die.");

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
            if (((cardA.getName().equals("Heal") || cardA.getName().equals("Poison")) && "Beast".equalsIgnoreCase(cardB.getClass().getSimpleName()))
                    || (cardB.getName().equals("Heal") || cardB.getName().equals("Poison")) && "Beast".equalsIgnoreCase(cardA.getClass().getSimpleName())) {
                cardA.activate(this);
                cardB.activate(this);

            }

            // Nothing happens when a shield is plaid
            else logger.info("No damage are heals are applied because of the shield");


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
     * Switch players and increment the turns counter
     */
    private void nextTurn() {

        Player tmp = opponent;
        opponent = currentPlayer;
        currentPlayer = tmp;
        ++roundCounter;
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
        if (numberOfCards % numberOfPlayers != 0) {
            logger.info("The players won't have the same cards number. Changing the size of the card pool is highly suggested!");
        }

        // Each player draws a card until there is no card left
        for (int i = 0; i < numberOfCards; i++) {
            playersList.get(i % numberOfPlayers).drawACard(this);
        }
        logger.info("Each player should have " + playersList.get(0).getHand().size() + " cards in their hand.");

        for (Player player : this.playersList) {
            logger.info("Hand of " + player.getName() + ":");
            String list = "";
            for (PlayCard card : player.getHand()) {
                list = list + card.getName() + ", ";
            }
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
     * Returns the opponent (the player which is not playing)
     *
     * @return the opponent (Player)
     */
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