package edu.insightr.spellmonger;


import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Hugues on 03/10/2016.
 * <p>
 * This classes is used as a static function to create a card deck, distribute the cards and shuffle them
 */
class DeckCreator {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);


    /**
     * The main function. Returns a full card deck
     *
     * @param maxNumberOfCard : the number of cards to be put in the list(int)
     */
    static ArrayList<PlayCard> fillCardPool(int maxNumberOfCard) {

        ArrayList<Integer> results = distribution(maxNumberOfCard);
        int totalBeast = results.get(0);
        int totalRitual = results.get(1);
        int[] numberOfBeast = distributionBeast(totalBeast);   // random numbers of beasts for each type of beast
        int numberOfRitual = Math.round(totalRitual / 2);       // number of rituals for each type of ritual
        int counterBeastType = 0;


        ArrayList<PlayCard> cardPool = new ArrayList<>();
        // Filling the cardPool List
        // IMPORTANT : We add a clone and not the card itself (otherwise, they would share the same
        // address!
        ArrayList<PlayCard> cardsList = generateCardList();

        for (PlayCard card : cardsList) {
            if (card.getClass().equals(Beast.class)) {
                for (int i = 0; i < numberOfBeast[counterBeastType]; ++i) {
                    cardPool.add((PlayCard) card.clone());
                }
                ++counterBeastType;
            } else if (card.getClass().equals(Ritual.class)) {
                for (int i = 0; i < numberOfRitual; ++i) {
                    cardPool.add((PlayCard) card.clone());
                }
            }
        }

        shuffleCardPool(cardPool);


        // For Tests : Display the cardPool list
        logger.info("\n");
        logger.info("Bear : " + numberOfBeast[0] + "    Wolf : " + numberOfBeast[1] + "    Eagle :" + numberOfBeast[2]);
        logger.info("Poison/Heal : " + numberOfRitual + " for each");
        logger.info("CardPool : " + cardPool);
        logger.info("Size of CardPool : " + cardPool.size());

        return cardPool;
    }

    /**
     * Creates the list of possible card (change this to change the available cards in the game
     *
     * @return the list of cards (ArrayList)
     */
    private static ArrayList<PlayCard> generateCardList() {
        final ArrayList<PlayCard> cardList;
        cardList = new ArrayList<>(Arrays.asList(
                new Beast("Bear", 3),
                new Beast("Wolf", 2),
                new Beast("Eagle", 1),
                new Ritual("Poison", 3, false),
                new Ritual("Heal", -3, true)
        ));

        return cardList;
    }


    /**
     * Return a list of numbers
     * The first number is the number of monsters [0]
     * The second number is the number of rituals [1]
     *
     * @param numberOfCard : input of the number of card
     * @return {@code List<int>} of the repartition of cards
     */
    private static ArrayList<Integer> distribution(int numberOfCard) {
        ArrayList<Integer> results = new ArrayList<>();

        int monsters;
        int rituals;
        int total;

        monsters = (numberOfCard * 5) / 6;
        rituals = numberOfCard / 6;
        total = monsters + rituals;

        // Make sure there is always an even number of
        // curse and blessings
        if (total != numberOfCard) {
            if (rituals % 2 == 0) {
                ++monsters;
            } else {
                ++rituals;
            }
        } else {
            if (rituals % 2 != 0) {
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
     * There is 58 beast, 58/3 = 19 average of each beast
     *
     * @param numCard : input of the total number of beasts
     * @return {@code int[]} of the repartition of beasts
     */
    private static int[] distributionBeast(int numCard) {
        int min = Math.round(numCard / 4); //the minimum number of beast will be 15
        int max = Math.round((int) (numCard / 2.5));  //the maximum will be 23
        Random randomNumX = new Random();
        Random randomNumY = new Random();
        int numBears = randomNumX.nextInt(max - min + 1) + min;
        int numWolves = randomNumY.nextInt(max - min + 1) + min;
        int numEagles = numCard - numBears - numWolves;
        return new int[]{numBears, numWolves, numEagles};
    }


    /**
     * Shuffles the cards pool given as a parameter
     *
     * @param cardPool : the list of cards (List)
     */
    private static void shuffleCardPool(List cardPool) {
        Collections.shuffle(cardPool);
    }
}
