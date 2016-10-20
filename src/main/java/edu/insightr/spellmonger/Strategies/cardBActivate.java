package edu.insightr.spellmonger.Strategies;

import edu.insightr.spellmonger.PlayCard;
import edu.insightr.spellmonger.SpellmongerApp;
import edu.insightr.spellmonger.Strategy;

/**
 * The class for the "strategy" where only one card is activated.
 * It is the second one (cardB)
 * Created by Hugues on 20/10/2016.
 */
public class cardBActivate implements Strategy {
    @Override
    public void doOperation(PlayCard cardA, PlayCard cardB, SpellmongerApp engine) {
        cardA.activate(engine);
    }
}
