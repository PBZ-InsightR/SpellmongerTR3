package edu.insightr.spellmonger;

/**
 * Created by Vincent on 21/09/2016. Define Ritual
 * A generic ritual class
 */
public abstract class Ritual {

    private int power;

    /**
     * @param power the power of the ritual
     */
    public Ritual(int power)
    {
        this.power = power;
    }

    /**
     * @return the power of the ritual
     */
    public int getPower()
    {
        return this.power;
    }

    /**
     * @return the description of the ritual
     */
    @Override
    public String toString()
    {

            return "This effect's level is " + this.power + " .";


    }
}
