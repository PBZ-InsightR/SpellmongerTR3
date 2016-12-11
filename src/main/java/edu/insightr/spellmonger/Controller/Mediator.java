package edu.insightr.spellmonger.Controller;


import edu.insightr.spellmonger.Model.PlayCard;
import edu.insightr.spellmonger.Model.Player;
import edu.insightr.spellmonger.Model.SpellmongerApp;
import org.apache.log4j.Logger;

/**
 * This class is a singleton.
 * This class is used to resolve a turn and decide, depending of the played cards, the heals and the damages done
 * Created by Hugues on 21/10/2016.
 */
public class Mediator {

    private static Mediator INSTANCE = null;

    private Mediator() {
    }

    public static Mediator getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Mediator();

        return INSTANCE;
    }


    public void resolveTurn(Player playerA, Player playerB, PlayCard cardA, PlayCard cardB) {
        final Logger logger = Logger.getLogger(SpellmongerApp.class);
        // Somebody played a shield, get out unless the other player play a heal card
        //Two Shields
        if (SpellmongerApp.cardNameShield.equals(cardA.getName()) && SpellmongerApp.cardNameShield.equals(cardB.getName())) {
            logger.info("Nothing Happens");
        }
        // One shield one heal
        else if (SpellmongerApp.cardNameShield.equals(cardA.getName())) {
            if (SpellmongerApp.cardNameHeal.equals(cardB.getName()))
                playerB.inflictDamages(cardB.getDamage());
        }
        // One shield one heal
        else if (SpellmongerApp.cardNameShield.equals(cardB.getName())) {
            if (SpellmongerApp.cardNameHeal.equals(cardA.getName()))
                playerA.inflictDamages(cardA.getDamage());
        }
        // Both card are direct spells or one card is beast and one is a spell
        else if (cardA.isDirect() && cardB.isDirect()
                || ((!cardA.isDirect() && cardB.isDirect() || (cardA.isDirect() && !cardB.isDirect())))) {

            //  CARD A
            // If cardA is a heal
            if (cardA.getName().equals(SpellmongerApp.cardNameHeal))
                playerA.inflictDamages(cardA.getDamage());

                // If it is a poison
            else if (cardA.getName().equals(SpellmongerApp.cardNamePoison))
                playerB.inflictDamages(cardA.getDamage());

                // If it is a beast
            else if (SpellmongerApp.listOfBeastsName.contains(cardA.getName()))
                playerB.inflictDamages(cardA.getDamage());

            //  CARD B
            // If cardB is a heal
            if (cardB.getName().equals(SpellmongerApp.cardNameHeal))
                playerB.inflictDamages(cardB.getDamage());

                // If it is a poison
            else if (cardB.getName().equals(SpellmongerApp.cardNamePoison))
                playerA.inflictDamages(cardB.getDamage());

                // If it is a beast
            else if (SpellmongerApp.listOfBeastsName.contains(cardB.getName()))
                playerA.inflictDamages(cardB.getDamage());

        }

        //Both cards are beasts
        else if (cardA.getDamage() < cardB.getDamage())
            playerA.inflictDamages(cardB.getDamage() - cardA.getDamage());
        else if (cardA.getDamage() > cardB.getDamage())
            playerB.inflictDamages(cardA.getDamage() - cardB.getDamage());
        //else damage compensate

    }

}
