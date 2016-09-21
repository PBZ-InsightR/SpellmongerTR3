package edu.insightr.spellmonger;

/**
 * Created by Vincent on 21/09/2016. Define blessing
 */
public class Blessing extends Ritual {

    /**
     * @param power The amount of blessing points
     */
    public Blessing(int power) {
        super(power);
    }

    /**
     * @return the power of the blessing
     */
    @Override
    public String toString()
    {
        return "You gain " + getPower() + " life points. ";
    }
}
