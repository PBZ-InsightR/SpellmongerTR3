package edu.insightr.spellmonger.Model;

/**
 * Created by Vincent on 21/09/2016. Define Ritual
 * A generic ritual class
 */
public class Ritual extends PlayCard {

    private final boolean targetsCurrentPlayer;

    /**
     * @param name   the name of the Ritual
     * @param damage the power of the ritual
     */
    public Ritual(String name, int damage, boolean targetsCurrentPlayer, boolean direct, int cardValue) {
        super(name, damage, direct, cardValue);
        this.targetsCurrentPlayer = targetsCurrentPlayer;
    }

    /**
     * @return the description of the ritual
     */
    @Override
    public String toString() {
        return this.getName();
    } //+this.info();


    /*
    public String info() {
        return ": its effect's level is " + this.getDamage();
    }*/

    /**
     * @return True if the ritual targets the caster, false if it targets the opponent
     */
    boolean targetsRitualCaster() {
        return this.targetsCurrentPlayer;
    }
}
