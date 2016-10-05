package edu.insightr.spellmonger;

/**
 * Created by Vincent on 21/09/2016. Define Ritual
 * A generic ritual class
 */
class Ritual extends PlayCard {

    private final boolean targetsCaster;

    /**
     * @param name   the name of the Ritual
     * @param damage the power of the ritual
     */
    Ritual(String name, int damage, boolean targetsCaster) {
        super(name, damage);
        this.targetsCaster = targetsCaster;

    }

    /**
     * if true -> heal current player
     * if false -> inflict damage to opponent player
     */
    @Override
    public void activate(SpellmongerApp app){
        if(targetsCaster == true){
            app.getCurrentPlayer().inflictDamages(this.getDamage());
        }
        else{
            app.getOpponent().inflictDamages(this.getDamage());
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
        return this.targetsCaster;
    }
}
