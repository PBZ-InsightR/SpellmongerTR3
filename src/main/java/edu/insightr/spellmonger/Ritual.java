package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

/**
 * Created by Vincent on 21/09/2016. Define Ritual
 * A generic ritual class
 */
class Ritual extends PlayCard {

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private final boolean targetsCurrentPlayer;

    /**
     * @param name   the name of the Ritual
     * @param damage the power of the ritual
     */
    Ritual(String name, int damage, boolean targetsCurrentPlayer, boolean direct) {
        super(name, damage, direct);
        this.targetsCurrentPlayer = targetsCurrentPlayer;
    }

    /**
     * if true -> heal current player
     * if false -> inflict damage to opponent player
     */
    @Override
    public void activate(SpellmongerApp app) {
        if (targetsCurrentPlayer) {
            this.getOwner().inflictDamages(this.getDamage());
            logger.info(this.getOwner().getName() + " casts a ritual that restore " + (-this.getDamage()) + " life points to himself/herself ");
        } else {
            if(!this.getOwner().equals(app.getOpponentPlayer()))
            {
                app.getOpponentPlayer().inflictDamages(this.getDamage());
                logger.info(this.getOwner().getName() + " casts a ritual that remove " + this.getDamage() + " life points to " + app.getOpponentPlayer().getName());
            }
            else
            {
                app.getCurrentPlayer().inflictDamages(this.getDamage());
                logger.info(this.getOwner().getName() + " casts a ritual that remove " + this.getDamage() + " life points to " + app.getCurrentPlayer().getName());
            }
        }
    }

    /**
     * @return the description of the ritual
     */
    @Override
    public String toString() {
        return this.getName() + ": its effect's level is " + this.getDamage();
    }

    /**
     * @return True if the ritual targets the caster, false if it targets the opponent
     */
    boolean targetsRitualCaster() {
        return this.targetsCurrentPlayer;
    }
}
