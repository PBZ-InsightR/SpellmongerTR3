package edu.insightr.spellmonger.Model;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hugues on 03/10/2016.
 * <p>
 * This classes is used as a static function to create a card deck, distribute the cards and shuffle them
 */
class DeckCreator {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private static final int numBear=10;
    private static final int numWolf=10;
    private static final int numEagle=10;
    private static final int numPoison = 3;
    private static final int numHeal = 4;
    private static final int numShield=5;


    /**
     * The main function. Returns a full card deck
     *
     * @param
     */
    static List<PlayCard> fillCardPool() {

        List<PlayCard> cardPool = new ArrayList<>();
        // Filling the cardPool List


        // Optimisation => create a HashMap instead
        for (int i = 0; i < numBear; ++i) {
            cardPool.add(new Beast(SpellmongerApp.cardNameBear, 3, 3));
        }
        for (int i = 0; i < numWolf; ++i) {
            cardPool.add(new Beast(SpellmongerApp.cardNameWolf, 2, 2));
        }
        for (int i = 0; i < numEagle; ++i) {
            cardPool.add(new Beast(SpellmongerApp.cardNameEagle, 1, 1));
        }

        for (int i = 0; i < numPoison; ++i) {
            cardPool.add(new Ritual(SpellmongerApp.cardNamePoison, 3, false, true, 3));
        }
        for (int i = 0; i < numHeal; ++i) {
            cardPool.add(new Ritual(SpellmongerApp.cardNameHeal, -3, true, true, 3));
        }
        for (int i = 0; i < numShield; ++i) {
            cardPool.add(new Ritual(SpellmongerApp.cardNameShield,0,true, false, 2));
        }
        Collections.shuffle(cardPool);


        // For Tests : Display the cardPool list
        logger.info("\n");
        logger.info("CardPool : " + cardPool);
        logger.info("Size " + cardPool.size()); // For Tests : Display the cardPool list

        return cardPool;
    }


    }

