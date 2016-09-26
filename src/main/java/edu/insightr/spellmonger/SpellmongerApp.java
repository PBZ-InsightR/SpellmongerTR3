package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Last Modification by Tara 26/09/2016
 * Class that simulates a card game (currently with 2 virtual players) :
 * <p>
 * There are currently 2 types of card that can be drawn by the player : Creatures and Rituals
 * Each card have an effect on the player or on its opponent
 * <p>
 * There are currently 3 different creatures that deals damages to its opponent :
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

    private Map<String, Integer> playersLifePoints = new HashMap<>(2);
    private Map<String, Integer> playersCreature = new HashMap<>(2);
    private List<String> cardPool = new ArrayList<>(70);


    /**
     * Constructor of the class
     *
     * @param playerA : {@code String} Name of Player A
     * @param playerB : {@code int} Name of the Player B
     *                Last Modified by : Tara
     */
    private SpellmongerApp(String playerA, String playerB) {
        playersLifePoints.put(playerA, 20);
        playersLifePoints.put(playerB, 20);

        playersCreature.put(playerA, 0);
        playersCreature.put(playerB, 0);

        int ritualMod = 3;

        // Filling the cardPool List (not random)
        for (int i = 0; i < 70; i++) {
            if (i % ritualMod == 0) {
                cardPool.add("Ritual");
            }
            if (i % ritualMod != 0) {
                cardPool.add("Creature");
            }

            if (ritualMod == 3) {
                ritualMod = 2;
            } else {
                ritualMod = 3;
            }

        }

        // For Tests : Display the cardPool list
        logger.info("\n");
        logger.info("CardPool : " + cardPool);
    }

    /**
     * Draw A Card
     * Return the String of the current card number in the card Pool
     *
     * @param currentPlayer     : {@code String} Name of the current Player
     * @param currentCardNumber : {@code int} Number (integer) of the current card number played in the card Pool
     * @return {@code String} of the name of the card drawn on the card Pool
     */
    private String drawACard(String currentPlayer, int currentCardNumber) {

        if ("Creature".equalsIgnoreCase(cardPool.get(currentCardNumber))) {
            logger.info(currentPlayer + " draw a Creature");
        }
        if ("Ritual".equalsIgnoreCase(cardPool.get(currentCardNumber))) {
            logger.info(currentPlayer + " draw a Ritual");
        }
        return cardPool.get(currentCardNumber);
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
    private void playACard(String drawn_card, String currentPlayer, String opponent) {

        if ("Creature".equalsIgnoreCase(drawn_card)) {
            playersCreature.put(currentPlayer, playersCreature.get(currentPlayer) + 1); // add a creature to the current player
            int nbCreatures = playersCreature.get(currentPlayer);
            if (nbCreatures > 0) {
                playersLifePoints.put(opponent, (playersLifePoints.get(opponent) - nbCreatures));
                logger.info("The " + nbCreatures + " creature(s) of " + currentPlayer + " attack and deal " + nbCreatures + " damages to its opponent");
            } else {
                logger.info(currentPlayer + "has no creatures, so deals 0 damage to its opponent");

            }
        }
        if ("Ritual".equalsIgnoreCase(drawn_card)) {
            int nbCreatures = playersCreature.get(currentPlayer);
            if (nbCreatures > 0) {
                playersLifePoints.put(opponent, (playersLifePoints.get(opponent) - nbCreatures - 3));
                logger.info("The " + nbCreatures + " creature(s) of " + currentPlayer + " attack and deal " + nbCreatures + " damages to its opponent");

                logger.info(currentPlayer + " cast a ritual that deals 3 damages to " + opponent);
            } else {
                logger.info(currentPlayer + " cast a ritual that deals 0 damage to " + opponent);
            }
        }
    }

    public static void main(String[] args) {

        String currentPlayer = "Alice";
        String opponent = "Bob";

        SpellmongerApp app = new SpellmongerApp(currentPlayer, opponent);

        boolean onePlayerDead = false;
        int currentCardNumber = 0;
        int roundCounter = 1;
        String winner = null;
        String drawn_card;

        while (!onePlayerDead) {
            logger.info("\n");
            logger.info("***** ROUND " + roundCounter);

            drawn_card = app.drawACard(currentPlayer, currentCardNumber);
            app.playACard(drawn_card, currentPlayer,opponent);

            logger.info(opponent + " has " + app.playersLifePoints.get(opponent) + " life points and " + currentPlayer + " has " + app.playersLifePoints.get(currentPlayer) + " life points ");

            if (app.playersLifePoints.get(currentPlayer) <= 0) {
                winner = opponent;
                onePlayerDead = true;
            }
            if (app.playersLifePoints.get(opponent) <= 0) {
                winner = currentPlayer;
                onePlayerDead = true;
            }

            if ("Alice".equalsIgnoreCase(currentPlayer)) {
                currentPlayer = "Bob";
                opponent = "Alice";
            } else {
                currentPlayer = "Alice";
                opponent = "Bob";
            }
            currentCardNumber++;
            roundCounter++;
        }

        logger.info("\n");
        logger.info("******************************");
        logger.info("THE WINNER IS " + winner + " !!!");
        logger.info("******************************");


    }

}
