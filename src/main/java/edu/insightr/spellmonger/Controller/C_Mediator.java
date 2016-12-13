package edu.insightr.spellmonger.Controller;


import edu.insightr.spellmonger.Model.M_PlayCard;
import edu.insightr.spellmonger.Model.M_Player;
import edu.insightr.spellmonger.Model.M_SpellmongerApp;
import org.apache.log4j.Logger;

/**
 * This class is a singleton.
 * This class is used to resolve a turn and decide, depending of the played cards, the heals and the damages done
 * Created by Hugues on 21/10/2016.
 */
class C_Mediator {

    private static C_Mediator INSTANCE = null;

    private C_Mediator() {
    }

    static C_Mediator getInstance() {
        if (INSTANCE == null)
            INSTANCE = new C_Mediator();

        return INSTANCE;
    }


    void resolveTurn(M_Player MPlayerA, M_Player MPlayerB, M_PlayCard cardA, M_PlayCard cardB) {
        final Logger logger = Logger.getLogger(M_SpellmongerApp.class);
        // Somebody played a shield, get out unless the other player play a heal card
        //Two Shields
        if (M_SpellmongerApp.cardNameShield.equals(cardA.getName()) && M_SpellmongerApp.cardNameShield.equals(cardB.getName())) {
            logger.info("Nothing Happens");
        }
        // One shield one heal
        else if (M_SpellmongerApp.cardNameShield.equals(cardA.getName())) {
            if (M_SpellmongerApp.cardNameHeal.equals(cardB.getName()))
                MPlayerB.inflictDamages(cardB.getDamage());
        }
        // One shield one heal
        else if (M_SpellmongerApp.cardNameShield.equals(cardB.getName())) {
            if (M_SpellmongerApp.cardNameHeal.equals(cardA.getName()))
                MPlayerA.inflictDamages(cardA.getDamage());
        }
        // Both card are direct spells or one card is beast and one is a spell
        else if (cardA.isDirect() && cardB.isDirect()
                || ((!cardA.isDirect() && cardB.isDirect() || (cardA.isDirect() && !cardB.isDirect())))) {

            //  CARD A
            // If cardA is a heal
            if (cardA.getName().equals(M_SpellmongerApp.cardNameHeal))
                MPlayerA.inflictDamages(cardA.getDamage());

                // If it is a poison
            else if (cardA.getName().equals(M_SpellmongerApp.cardNamePoison))
                MPlayerB.inflictDamages(cardA.getDamage());

                // If it is a beast
            else if (M_SpellmongerApp.listOfBeastsName.contains(cardA.getName()))
                MPlayerB.inflictDamages(cardA.getDamage());

            //  CARD B
            // If cardB is a heal
            if (cardB.getName().equals(M_SpellmongerApp.cardNameHeal))
                MPlayerB.inflictDamages(cardB.getDamage());

                // If it is a poison
            else if (cardB.getName().equals(M_SpellmongerApp.cardNamePoison))
                MPlayerA.inflictDamages(cardB.getDamage());

                // If it is a beast
            else if (M_SpellmongerApp.listOfBeastsName.contains(cardB.getName()))
                MPlayerA.inflictDamages(cardB.getDamage());

        }

        //Both cards are beasts
        else if (cardA.getDamage() < cardB.getDamage())
            MPlayerA.inflictDamages(cardB.getDamage() - cardA.getDamage());
        else if (cardA.getDamage() > cardB.getDamage())
            MPlayerB.inflictDamages(cardA.getDamage() - cardB.getDamage());
        //else damage compensate

    }

}
