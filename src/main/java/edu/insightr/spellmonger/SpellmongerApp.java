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

    private Map<String, Integer> playersLifePoints = new HashMap<>(2);
    private Map<String, Integer> playersCreature = new HashMap<>(2);

    private List<PlayCard> cardPool = new ArrayList<>(70);


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


        int differentCards = 5;

        Random rand = new Random();
        int randomInt;

        // Filling the cardPool List (not random)
        for (int i = 0; i < 70; i++) {
            randomInt = rand.nextInt(differentCards); // Draw a random integer number from 0 to differentCards value
            switch (randomInt) {
                case 0:
                    Bear bear_beast = new Bear();
                    cardPool.add(bear_beast);
                    break;
                case 1:
                    Eagle eagle_beast = new Eagle();
                    cardPool.add(eagle_beast);
                    break;
                case 2:
                    Wolf wolf_beast = new Wolf();
                    cardPool.add(wolf_beast);
                    break;
                case 3:
                    Curse curse_ritual = new Curse(3);
                    cardPool.add(curse_ritual);
                    break;
                case 4:
                    Blessing blessing_ritual = new Blessing(3);
                    cardPool.add(blessing_ritual);
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
    private PlayCard drawACard(String currentPlayer, int currentCardNumber) {

        if ("Creature".equalsIgnoreCase(cardPool.get(currentCardNumber).type)) {
            logger.info(currentPlayer + " draw a Creature");

        }
        if ("Ritual".equalsIgnoreCase(cardPool.get(currentCardNumber).type)) {
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
    private void playACard(PlayCard drawn_card, String currentPlayer, String opponent) {

        if ("Creature".equalsIgnoreCase(drawn_card.type)) {
            playersCreature.put(currentPlayer, playersCreature.get(currentPlayer) + 1); // add a creature to the current player
            int damage = 0;


            if (drawn_card instanceof Bear) {
                Bear beast = (Bear) drawn_card;
                damage = beast.getDamage();
                logger.info("This creature is a " + beast.toString());
            } else if (drawn_card instanceof Eagle) {
                Eagle beast = (Eagle) drawn_card;
                damage = beast.getDamage();
                logger.info("This creature is a " + beast.toString());
            } else if (drawn_card instanceof Wolf) {
                Wolf beast = (Wolf) drawn_card;
                damage = beast.getDamage();
                logger.info("This creature is a " + beast.toString());
            } else {
                logger.info("An error have occurred : type of card (Creature) is not recognized ");
            }

            playersLifePoints.put(opponent, (playersLifePoints.get(opponent) - damage));
            logger.info("The creature of " + currentPlayer + " attacks and deals " + damage + " damages to its opponent");

        } else if ("Ritual".equalsIgnoreCase(drawn_card.type)) {
            if (drawn_card instanceof Curse) {
                Curse ritual = (Curse) drawn_card;
                logger.info("The ritual is a " + ritual.toString());

                playersLifePoints.put(opponent, (playersLifePoints.get(opponent) - ritual.getPower()));
                logger.info(currentPlayer + " cast a ritual that deals " + ritual.getPower() + " damages to " + opponent);
            } else if (drawn_card instanceof Blessing) {
                Blessing ritual = (Blessing) drawn_card;
                logger.info("The ritual is a " + ritual.toString());

                playersLifePoints.put(currentPlayer, (playersLifePoints.get(currentPlayer) + ritual.getPower()));
                logger.info(currentPlayer + " cast a ritual that restores " + ritual.getPower() + " life points to " + currentPlayer);
            } else {
                logger.info("An error have occurred : type of card (Ritual) is not recognized ");
            }

        } else {
            logger.info("An error have occurred : type of card is not recognized ");
        }
    }

    public static void main(String[] args) {

        String playerA = "Alice";
        String playerB = "Bob";
        SpellmongerApp app = new SpellmongerApp(playerA, playerB);

        String currentPlayer = "Alice";
        String opponent = "Bob";

        boolean onePlayerDead = false;
        int currentCardNumber = 0;
        int roundCounter = 1;
        String winner = null;
        PlayCard drawn_card;

        if (currentCardNumber < 70) {
            while (!onePlayerDead) {
                logger.info("\n");
                logger.info("***** ROUND " + roundCounter);

                drawn_card = app.drawACard(currentPlayer, currentCardNumber);
                app.playACard(drawn_card, currentPlayer, opponent);

                logger.info(opponent + " has " + app.playersLifePoints.get(opponent) + " life points and " + currentPlayer + " has " + app.playersLifePoints.get(currentPlayer) + " life points ");

                if (app.playersLifePoints.get(currentPlayer) <= 0) {
                    winner = opponent;
                    onePlayerDead = true;
                }
                if (app.playersLifePoints.get(opponent) <= 0) {
                    winner = currentPlayer;
                    onePlayerDead = true;
                }

                if (playerA.equalsIgnoreCase(currentPlayer)) {
                    currentPlayer = playerB;
                    opponent = playerA;
                } else {
                    currentPlayer = playerA;
                    opponent = playerB;
                }
                currentCardNumber++;
                roundCounter++;
            }

            logger.info("\n");
            logger.info("******************************");
            logger.info("THE WINNER IS " + winner + " !!!");
            logger.info("******************************");
        } else {
            logger.info("\n");
            logger.info("******************************");
            logger.info("No more cards in the CardPool - End of the game");
            logger.info("******************************");
        }


    }

}