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
    Beast(String name, int damage) {
        super(name, damage);
    }


    @Override
    public void activate(SpellmongerApp app) {
        //app.getCurrentPlayer().addCreature(this);
        this.activate(app, 0);
    }

    /**
     * Activates the beast (used if some damages are blocked)
     *
     * @param app           : the game engine (SpellmongerApp)
     * @param damageBlocked : the amount of damages blocked (int)
     */
    void activate(SpellmongerApp app, int damageBlocked) {
        //app.getCurrentPlayer().addCreature(this);
        int damageDealt = this.getDamage() - damageBlocked;
        app.getOpponent().inflictDamages(damageDealt);
        logger.info(" " + app.getCurrentPlayer().getName() + "'s " + this.getName() + " deals " + damageDealt + " damage(s) to " + app.getOpponent().getName() + ".");
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
