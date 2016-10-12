package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

/**
 * Created by Stanislas Daniel Claude D on 21/09/2016.
 * Defines a standard beast
 */
class Beast extends PlayCard {
    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);

    /**
     * @param name   is the name of the beast
     * @param damage is an heritage from the constructor of PlayCard
     */
    Beast(String name, int damage){
        super(name, damage, false);
    }


    @Override
    public void activate(SpellmongerApp app) {
        Player opponent = app.getOpponentPlayer();
        if(!this.getOwner().equals(app.getOpponentPlayer()))
        {
            app.getOpponentPlayer().inflictDamages(this.getDamage());
            logger.info(" " + this.getOwner().getName() + "'s " + this.getName() + " deals " + this.getDamage() + " damage(s) to " + opponent.getName() + ".");
        }

        else
        {
            app.getCurrentPlayer().inflictDamages(this.getDamage());
            logger.info(" " + this.getOwner().getName() + "'s " + this.getName() + " deals " + this.getDamage() + " damage(s) to " + app.getCurrentPlayer().getName() + ".");
        }


    }

    /**
     * Creates a string to describe the card. This is an override of the toString() function in the Card class.
     *
     * @return the description of the card (String)
     */
    @Override
    public String toString() {
        return this.getName() + " : deals " + this.getDamage() + " damage";
    }
}
