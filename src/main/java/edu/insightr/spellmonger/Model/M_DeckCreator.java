package edu.insightr.spellmonger.Model;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Hugues on 03/10/2016.
 * This class is a singleton
 * This classes is used as a static function to create a card deck, distribute the cards and shuffle them
 */
class M_DeckCreator {
    private static final Logger logger = Logger.getLogger(M_SpellmongerApp.class);
    private static final int highestNumberOfCards = 10;
    private static final int numBear = 10;
    private static final int numWolf = 10;
    private static final int numEagle = 10;
    private static final int numPoison = 3;
    private static final int numHeal = 4;
    private static final int numShield = 5;


    private static M_DeckCreator INSTANCE = null;

    /**
     * Default constructor for M_DeckCreator, set as private because it's a Singleton
     */
    private M_DeckCreator() {
    }

    /**
     * @return the instance of the M_DeckCreator
     */
    static M_DeckCreator getInstance() {
        if (INSTANCE == null)
            INSTANCE = new M_DeckCreator();

        return INSTANCE;
    }

    /**
     * The main function. Returns a full card deck
     */
    List<M_PlayCard> fillCardPool() {

        List<M_PlayCard> cardPool = new ArrayList<>(42);

        // Filling the cardPool List

        for (int i = 0; i < highestNumberOfCards; ++i) {
            if (i < numBear)
                cardPool.add(new M_Beast(M_SpellmongerApp.cardNameBear, 3, 3));
            if (i < numWolf)
                cardPool.add(new M_Beast(M_SpellmongerApp.cardNameWolf, 2, 2));
            if (i < numEagle)
                cardPool.add(new M_Beast(M_SpellmongerApp.cardNameEagle, 1, 1));
            if (i < numPoison)
                cardPool.add(new M_Ritual(M_SpellmongerApp.cardNamePoison, 3, false, true, 3));
            if (i < numHeal)
                cardPool.add(new M_Ritual(M_SpellmongerApp.cardNameHeal, -3, true, true, 3));
            if (i < numShield)
                cardPool.add(new M_Ritual(M_SpellmongerApp.cardNameShield, 0, true, false, 2));
        }
        Collections.shuffle(cardPool);

        logger.info("CardPool : " + cardPool);
        logger.info("There are " + cardPool.size() + " in the stack.");

        return cardPool;
    }
}

