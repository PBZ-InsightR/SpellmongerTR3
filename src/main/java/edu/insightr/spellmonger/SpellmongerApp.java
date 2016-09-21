package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellmongerApp {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);

    Map<String, Integer> playersLifePoints = new HashMap<>(2);
    Map<String, Integer> playersCreature = new HashMap<>(2);
    List<String> cardPool = new ArrayList<>(70);

    public SpellmongerApp() {
        playersLifePoints.put("Alice", 20);
        playersLifePoints.put("Bob", 20);
        playersCreature.put("Alice", 0);
        playersCreature.put("Bob", 0);
        int ritualMod = 3;

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
    }

    public static void main(String[] args) {
        SpellmongerApp app = new SpellmongerApp();

        boolean onePlayerDead = false;
        String currentPlayer = "Alice";
        String opponent = "Bob";
        int currentCardNumber = 0;
        int roundCounter = 1;
        String winner = null;

        while (!onePlayerDead) {
            logger.info("\n");
            logger.info("***** ROUND " + roundCounter);

            app.drawACard(currentPlayer, opponent, currentCardNumber);

            logger.info(opponent + " has " + app.playersLifePoints.get(opponent).intValue() + " life points and " + currentPlayer + " has " + app.playersLifePoints.get(currentPlayer).intValue() + " life points ");

            if (app.playersLifePoints.get(currentPlayer).intValue() <= 0) {
                winner = opponent;
                onePlayerDead = true;
            }
            if (app.playersLifePoints.get(opponent).intValue() <= 0) {
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

    public void drawACard(String currentPlayer, String opponent, int currentCardNumber) {

        if ("Creature".equalsIgnoreCase(cardPool.get(currentCardNumber))) {
            logger.info(currentPlayer + " draw a Creature");
            playersCreature.put(currentPlayer, playersCreature.get(currentPlayer).intValue() + 1);
            int nbCreatures = playersCreature.get(currentPlayer).intValue();
            if (nbCreatures > 0) {
                playersLifePoints.put(opponent, (playersLifePoints.get(opponent).intValue() - nbCreatures));
                logger.info("The " + nbCreatures + " creatures of " + currentPlayer + " attack and deal " + nbCreatures + " damages to its opponent");
            }
        }
        if ("Ritual".equalsIgnoreCase(cardPool.get(currentCardNumber))) {
            logger.info(currentPlayer + " draw a Ritual");
            int nbCreatures = playersCreature.get(currentPlayer).intValue();
            if (nbCreatures > 0) {
                playersLifePoints.put(opponent, (playersLifePoints.get(opponent).intValue() - nbCreatures - 3));
                logger.info("The " + nbCreatures + " creatures of " + currentPlayer + " attack and deal " + nbCreatures + " damages to its opponent");
            }
            logger.info(currentPlayer + " cast a ritual that deals 3 damages to " + opponent);
        }
    }

}
