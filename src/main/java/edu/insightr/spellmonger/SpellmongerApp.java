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
    private Player playerA, playerB, currentPlayer, opponent, winner;
    private boolean onePlayerDead;
    private int currentCardNumber, roundCounter, maxNumberOfCard;
    private List<PlayCard> cardPool;
    private final ArrayList<PlayCard> cardList;

    /**
     * Constructor of the class
     *
     * @param playerA : {@code String} Name of Player A
     * @param playerB : {@code int} Name of the Player B
     *                Last Modified by : Tara
     */
    private SpellmongerApp(Player playerA, Player playerB, int maxNumberOfCard) {
        this.maxNumberOfCard = maxNumberOfCard;
        this.onePlayerDead = false;
        this.playerA = playerA;
        this.playerB = playerB;
        this.currentPlayer = this.playerA;
        this.opponent = this.playerB;
        this.currentCardNumber = 0;
        this.roundCounter = 1;
        this.winner = null;
        this.cardPool = new ArrayList<>(this.maxNumberOfCard);
        this.cardList = new ArrayList<>(Arrays.asList(
                new Beast("Bear", 3),
                new Beast("Wolf", 2),
                new Beast("Eagle", 1),
                new Ritual("Curse", 3, false),
                new Ritual("Blessing", -3, true)
        ));
        fillCardPool();
    }

    /**
     *
     */
    private void fillCardPool() {
        ArrayList<Integer> results = Repartition(this.maxNumberOfCard);
        int totalBeast = results.get(0);
        int totalRitual = results.get(1);
        int[] numberOfBeast = RepartitionBeast(totalBeast);   // random numbers of beasts for each type of beast
        int numberOfRitual = Math.round(totalRitual/2);       // number of rituals for each type of ritual
        int counterBeastType = 0;

        // Filling the cardPool List
        for (PlayCard card : cardList) {
            if (card.getClass().equals(Beast.class)) {
                for (int i = 0; i < numberOfBeast[counterBeastType]; ++i) {
                    cardPool.add(card);
                }
                ++counterBeastType;
            } else if (card.getClass().equals(Ritual.class)) {
                for (int i = 0; i < numberOfRitual; ++i) {
                    cardPool.add(card);
                }
            }
        }

        Collections.shuffle(cardPool);

        // For Tests : Display the cardPool list
        logger.info("\n");
        logger.info("Bear : " + numberOfBeast[0] + "    Wolf : " + numberOfBeast[1] + "    Eagle :" + numberOfBeast[2]);
        logger.info("Curse/Blessing : " + numberOfRitual + " for each");
        logger.info("CardPool : " + cardPool);
        logger.info("Size of CardPool : " + cardPool.size());
    }

    /**
     * Says when all cards have been played.
     *
     * @return true if the game can continue
     */
    private boolean isThereAnyCardLeft() {
        return !(this.currentCardNumber == this.maxNumberOfCard);
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
        } else if ("Ritual".equalsIgnoreCase(drawn_card.getClass().getSimpleName())) {
            Player target;
            String verb;
            int lifepoints_effect;

            target = (((Ritual) drawn_card).targetsRitualCaster()) ? currentPlayer : opponent;
            verb = (drawn_card.getDamage() < 0) ? "restores" : "removes";
            lifepoints_effect = (drawn_card.getDamage() < 0) ? (-drawn_card.getDamage()) : drawn_card.getDamage();
            target.inflictDamages(drawn_card.getDamage());
            logger.info(currentPlayer.getName() + " casts a ritual that " + verb + " " + lifepoints_effect + " life points to " + target.getName());
        } else {
            logger.info("An error have occurred : type of card is not recognized ");
        }
    }

    /**
     * Deals the damages from the creatures of the current player
     *
     * @param currentPlayer : The current player
     * @param opponent      : The opponent
     */
    private void creaturesAttack(Player currentPlayer, Player opponent) {

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
    private void play() {
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

            PlayCard drawn_card;
            drawn_card = drawACard(currentPlayer, currentCardNumber);
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

        if (isThereAnyCardLeft()) {
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
     * Return a list of number
     * The first number is the number of monsters [0]
     * The second number is the number of rituals [1]
     *
     * @param numberOfCard : input of the number of card
     * @return {@code List<int>} of the repartition of cards
     */
    private static ArrayList<Integer> Repartition(int numberOfCard) {
        ArrayList<Integer> results = new ArrayList<>();

        int monsters;
        int rituals;
        int total;

        logger.info("\n");

        monsters = (numberOfCard * 5) / 6;
        rituals = numberOfCard / 6;
        total = monsters + rituals;

        if (total != numberOfCard) {
            if (rituals % 2 == 0) {
                ++monsters;
            } else {
                ++rituals;
            }
        } else {
            if (rituals % 2 == 0) {
                ++rituals;
                --monsters;
            }
        }

        results.add(monsters);
        results.add(rituals);
        return results;
    }

    /**
     * Generates 3 random numbers, whose sum is the total number of beasts
     * The first number (x) is the number of Bears
     * The second number (y) is the number of Wolfs
     * The third number (z) is the number of Eagles
     *
     * @param sum : input of the total number of beasts
     * @return {@code int[]} of the repartition of beasts
     */
    private static int[] RepartitionBeast(int sum) {
        int min = Math.round(sum / 4);
        int max = Math.round(sum / 3);
        Random randomNumX = new Random();
        Random randomNumY = new Random();
        int x = randomNumX.nextInt(max - min + 1) + min;
        int y = randomNumY.nextInt(max - min + 1) + min;
        int z = sum - x - y;
        return new int[]{x, y, z};
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
        app.play();
    }
}