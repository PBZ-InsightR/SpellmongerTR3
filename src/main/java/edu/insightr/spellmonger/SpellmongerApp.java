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

    private List<PlayCard> cardPool;
    private Player playerA, playerB, currentPlayer, opponent, winner;
    private boolean onePlayerDead;
    private int currentCardNumber, roundCounter;


    /**
     * Constructor of the class
     *
     * @param playerA : {@code String} Name of Player A
     * @param playerB : {@code int} Name of the Player B
     *                Last Modified by : Tara
     */
    private SpellmongerApp(Player playerA, Player playerB, int maxNumberOfCard) {
        cardPool = new ArrayList<>(maxNumberOfCard);
        this.playerA = playerA;
        this.playerB = playerB;

        this.onePlayerDead = false;

        currentPlayer = this.playerA;
        opponent = this.playerB;

        onePlayerDead = false;
        currentCardNumber = 0;
        roundCounter = 1;
        winner = null;

        int differentBeasts = 3;
        int differentRituals = 2;
        Random rand = new Random();
        int randomInt;

        int numberOfBeast=58;
        int numberOfRitual=12;

        // Filling the cardPool List
        for (int i = 0; i < numberOfBeast; ++i) {
            randomInt = rand.nextInt(differentBeasts); // Draw a random integer number from 0 to differentBeasts value
            switch (randomInt) {
                case 0:
                    Beast bear = new Beast("Bear", 3);
                    cardPool.add(bear);
                    break;
                case 1:
                    Beast wolf = new Beast("Wolf", 2);
                    cardPool.add(wolf);
                    break;
                case 2:
                    Beast eagle = new Beast("Eagle", 1);
                    cardPool.add(eagle);
                    break;
            }

        }

        for (int i = 0; i < numberOfRitual; ++i) {
            randomInt = rand.nextInt(differentRituals); // Draw a random integer number from 0 to differentRituals value
            switch (randomInt) {
                case 0:
                    Ritual curse = new Ritual("Curse", 3, false);
                    cardPool.add(curse);
                    break;
                case 1:
                    Ritual blessing = new Ritual("Blessing", -3, true);
                    cardPool.add(blessing);
                    break;
            }

        }

        // For Tests : Display the cardPool list
        logger.info("\n");
        logger.info("CardPool : " + cardPool);

    }



    /**
     * Draw A Card
     * Return the card (PlayCard type) of the current card number in the card Pool
     *
     * @param currentPlayer     : {@code String} Name of the current Player
     * @param currentCardNumber : {@code int} Number (integer) of the current card number played in the card Pool
     * @return {@code PlayCard} of the card drawn on the card Pool
     */
    private PlayCard drawACard(Player currentPlayer, int currentCardNumber) {

        if ("Creature".equalsIgnoreCase(cardPool.get(currentCardNumber).getClass().getName())) {
            logger.info(currentPlayer.getName() + " draw a Creature");

        }
        if ("Ritual".equalsIgnoreCase(cardPool.get(currentCardNumber).getClass().getName())) {
            logger.info(currentPlayer.getName() + " draw a Ritual");
        }
        return cardPool.get(currentCardNumber);
    }

    /**
     * Discard and Draw A Card
     * Return the card (PlayCard type) of the next card number in the card Pool
     *
     * @param currentPlayer     : {@code String} Name of the current Player
     * @param currentCardNumber : {@code int} Number (integer) of the current card number played in the card Pool
     * @return {@code PlayCard} of the next card drawn on the card Pool
     */
    private PlayCard discardAndDraw(Player currentPlayer, int currentCardNumber) {
        logger.info(currentPlayer.getName() + " discard");
        return drawACard(currentPlayer, currentCardNumber + 1);
    }

    /**
     * Says when all cards have been played.
     *
     * @param currentCardNumber: The number of card that have been played
     * @return true if the game can continue
     */
    private static boolean IsThereAnyCardLeft(int currentCardNumber, int maxNumberOfCard) {
        return !(currentCardNumber == maxNumberOfCard);
    }

    /**
     * Play A Card
     * <p>
     * Play the card drawn by the player and affects its opponent or the player itself.
     *
     * @param drawn_card    : {@code String} Name of the drawn card
     * @param currentPlayer : {@code String} Name of the current Player
     * @param opponent      : {@code String} Name of the opponent of the current Player
     */
    private void playACard(PlayCard drawn_card, Player currentPlayer, Player opponent) {

        //First, we check the type of the card and do an action depending on it

        if ("Beast".equalsIgnoreCase(drawn_card.getClass().getSimpleName())) {
            currentPlayer.addCreature(drawn_card);
            logger.info(currentPlayer.getName() + " plays a Beast. It is a " + drawn_card.getName());
        }
        else if ("Ritual".equalsIgnoreCase(drawn_card.getClass().getSimpleName())) {
            Player target;String verb;

            target = (((Ritual) drawn_card).targetsRitualCaster()) ? currentPlayer : opponent;
            verb = (drawn_card.getDamage() < 0) ? "restores" : "removes";

            target.inflictDamages(drawn_card.getDamage());
            logger.info(currentPlayer.getName() + " casts a ritual that " + verb + " " + drawn_card.getDamage() + " life points to " + target.getName());

            if (!currentPlayer.addToGraveyard(drawn_card)){
                logger.info("ERROR : Could not add the card to the graveyard");
            }
        }
        else {
            logger.info("An error have occurred : type of card is not recognized ");
        }
    }

    /**
     * Deals the damages from the creatures of the current player
     * @param currentPlayer : The current player
     * @param opponent : The opponent
     */
    private void creaturesAttack(Player currentPlayer, Player opponent){

        ArrayList<PlayCard> beastsList = currentPlayer.getCreatures();
        int totalDamages = 0;
        for (PlayCard beast : beastsList) {
            totalDamages += beast.getDamage();
        }
        logger.info("The beasts of " + currentPlayer.getName() + " deal " + totalDamages + " damages to " + opponent.getName());
        opponent.inflictDamages(totalDamages);
    }

    /**
     * Launches the game
     */
    private void play(int maxNumberOfCard) {
        while (!onePlayerDead) {
            if (!IsThereAnyCardLeft(currentCardNumber, maxNumberOfCard)) {
                logger.info("\n");
                logger.info("******************************");
                logger.info("No more cards in the CardPool - End of the game");
                logger.info("******************************");
                break;
            }
            logger.info("\n");
            logger.info("***** ROUND " + roundCounter);

            PlayCard drawn_card;
            drawn_card = drawACard(currentPlayer, currentCardNumber);


                /* the player discard at round 3 */
            if (roundCounter == 3) {
                drawn_card = discardAndDraw(currentPlayer, currentCardNumber);
            }

            playACard(drawn_card, currentPlayer, opponent);
            creaturesAttack(currentPlayer, opponent);
            logger.info(opponent.getName() + " has " + opponent.getLifePoints() + " life points and " + currentPlayer.getName() + " has " + currentPlayer.getLifePoints() + " life points ");


            if (currentPlayer.getLifePoints() <= 0) {
                winner = opponent;
                onePlayerDead = true;
            }
            if (opponent.getLifePoints() <= 0) {
                winner = currentPlayer;
                onePlayerDead = true;
            }

            if (playerA == currentPlayer) {
                currentPlayer = playerB;
                opponent = playerA;
            } else {
                currentPlayer = playerA;
                opponent = playerB;
            }
            ++currentCardNumber;
            ++roundCounter;
        }

        if (IsThereAnyCardLeft(currentCardNumber, maxNumberOfCard)) {
            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner.getName() + " !!!");
            logger.info("******************************");
            logger.info("Beasts controlled by " + playerA.getName());
            logger.info(playerA.getCreatures());
            logger.info("Beasts controlled by " + playerB.getName());
            logger.info(playerB.getCreatures());
        } else {
            logger.info("\n");
            logger.info("******************************");
            logger.info("No more cards in the CardPool - End of the game");
            logger.info("******************************");
        }
    }

    public static void main(String[] args) {
        // Important constants
        final int lifePoints = 20;
        final int maxNumberOfCard = 70;

        // We create the players
        Player playerA = new Player("Alice", lifePoints);
        Player playerB = new Player("Bob", lifePoints);

        // We create the application
        SpellmongerApp app = new SpellmongerApp(playerA, playerB, maxNumberOfCard);

        // We start the game
        app.play(maxNumberOfCard);
    }
}