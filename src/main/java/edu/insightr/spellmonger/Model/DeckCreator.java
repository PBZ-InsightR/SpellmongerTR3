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
    private static final int highestNumberOfCards = 10;
    private static final int numBear = 10;
    private static final int numWolf = 10;
    private static final int numEagle = 10;
    private static final int numPoison = 3;
    private static final int numHeal = 4;
    private static final int numShield = 5;


    /**
     * The main function. Returns a full card deck
     *
     * @param
     */
    static List<PlayCard> fillCardPool() {

        List<PlayCard> cardPool = new ArrayList<>();
        // Filling the cardPool List

        // Optimisation => create a HashMap instead
        for (int i = 0; i < highestNumberOfCards; ++i) {
            if (i < numBear)
                cardPool.add(new Beast(SpellmongerApp.cardNameBear, 3, 3));
            if (i < numWolf)
                cardPool.add(new Beast(SpellmongerApp.cardNameWolf, 2, 2));
            if (i < numEagle)
                cardPool.add(new Beast(SpellmongerApp.cardNameEagle, 1, 1));
            if (i < numPoison)
                cardPool.add(new Ritual(SpellmongerApp.cardNamePoison, 3, false, true, 3));
            if (i < numHeal)
                cardPool.add(new Ritual(SpellmongerApp.cardNameHeal, -3, true, true, 3));
            if (i < numShield)
                cardPool.add(new Ritual(SpellmongerApp.cardNameShield, 0, true, false, 2));
        }
        Collections.shuffle(cardPool);

        logger.info("CardPool : " + cardPool);
        logger.info("There are " + cardPool.size() + " in the stack.");

        return cardPool;
    }
}

