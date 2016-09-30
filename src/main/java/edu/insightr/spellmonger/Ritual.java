package edu.insightr.spellmonger;

/**
 * Created by Vincent on 21/09/2016. Define Ritual
 * A generic ritual class
 */
class Ritual extends PlayCard {

    private boolean targetsCaster;

    /**
     * @param  name the name of the Ritual
     * @param damage the power of the ritual
     */
    Ritual(String name, int damage, boolean targetsCaster)
    {
        super(name, damage);
        this.targetsCaster = targetsCaster;

    }


    /**
     * @return the description of the ritual
     */
    @Override
    public String toString()
    {
            return "This effect's level is " + getDamage() + " .";

    }

    /**
     *
     * @return True if the ritual targets the caster, false if it targest the opponent
     */
    boolean targetsRitualCaster(){
        return this.targetsCaster;
    }
}
