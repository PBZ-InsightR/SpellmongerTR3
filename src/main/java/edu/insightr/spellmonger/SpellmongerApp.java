package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

import java.util.*;

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


    /**
     * Constructor of the class
     *
     * @param playerA : {@code String} Name of Player A
     * @param playerB : {@code int} Name of the Player B
     *                Last Modified by : Tara
     */
    private SpellmongerApp(Player playerA, Player playerB, int maxNumberOfCard) {
        this.currentPlayer = playerA;
        this.opponent = playerB;
        this.roundCounter = 1;
        this.graveyard = new ArrayList<>();


        // Use the DeckCreator class to fill and shuffle the cards deck
        this.cardPool = DeckCreator.fillCardPool(maxNumberOfCard);
    }


    /**
     * Says when all cards have been played.
     *
     * @return true if the game can continue
     */
    private boolean isThereAnyCardLeft() {
        return !(this.cardPool.isEmpty());
    }

    /**
     * Ritual already played must be add to graveyard
     *
     * @param used_card : used_card must be add to graveyard
     */
    private void discard(PlayCard used_card){
        graveyard.add(used_card);
        logger.info(used_card.getName() + " added to graveyard ");
    }

    /**
     * Play A Card
     * Play the card drawn by the player and affects its opponent or the player itself.
     *
     * @param drawn_card : {@code String} Name of the drawn card
     */
    private void playACard(PlayCard drawn_card) {

        //First, we check the type of the card and do an action depending on it

        if ("Beast".equalsIgnoreCase(drawn_card.getClass().getSimpleName())) {
            if(currentPlayer.addCreature(drawn_card)){
                logger.info(currentPlayer.getName() + " plays a Beast. It is a " + drawn_card.getName());
            }
        } else if ("Ritual".equalsIgnoreCase(drawn_card.getClass().getSimpleName())) {
            Player target;
            String verb;
            int lifepoints_effect;

            target = (((Ritual) drawn_card).targetsRitualCaster()) ? currentPlayer : opponent;
            verb = (drawn_card.getDamage() < 0) ? "restores" : "removes";
            lifepoints_effect = (drawn_card.getDamage() < 0) ? (-drawn_card.getDamage()) : drawn_card.getDamage();
            target.inflictDamages(drawn_card.getDamage());
            logger.info(currentPlayer.getName() + " casts a ritual that " + verb + " " + lifepoints_effect + " life points to " + target.getName());

            discard(drawn_card);

        } else {
            logger.info("An error have occurred : type of card is not recognized ");
        }
    }


    /**
     * Deals the damages from the creatures of the current player
     */
    private void creaturesAttack() {

        ArrayList<PlayCard> beastsList = currentPlayer.getCreatures();
        int totalDamages = 0;
        for (PlayCard beast : beastsList) {
            totalDamages += beast.getDamage();
        }
        logger.info("The beasts of " + this.currentPlayer.getName() + " deal " + totalDamages + " damages to " + this.opponent.getName());
        opponent.inflictDamages(totalDamages);
    }

    /**
     * Launches the game
     */
    private void play() {

        boolean onePlayerDead = false;
        Player winner = currentPlayer;

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
            PlayCard drawnCard = drawACard();
            playACard(drawnCard);
            creaturesAttack();
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
     * Switch players and increment turns and cardNumbers
     */
    private void nextTurn() {

        Player tmp = opponent;
        opponent = currentPlayer;
        currentPlayer = tmp;
        ++roundCounter;
    }

    /**
     * Draw A Card
     * Returns the card on the top of the deck (the last) and removes it
     *
     * @return {@code PlayCard} of the card drawn on the card Pool
     */
    private PlayCard drawACard() {
        PlayCard card = this.cardPool.get(this.cardPool.size() - 1);
        this.cardPool.remove(card);
        return card;
    }


    Player getCurrentPlayer(){return this.currentPlayer;}
    Player getOpponent(){return this.opponent;}


    public static void main(String[] args) {
        final int lifePoints = 20;
        final int maxNumberOfCards = 70;

        // We create the application
        SpellmongerApp app = new SpellmongerApp(new Player("Alice", lifePoints), new Player("Bob", lifePoints), maxNumberOfCards);

        // We start the game
        app.play();
    }
}