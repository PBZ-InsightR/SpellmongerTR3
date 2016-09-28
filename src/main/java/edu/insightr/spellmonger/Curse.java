package edu.insightr.spellmonger;

/**
 * Created by Vincent on 21/09/2016. Define Curse
 */
public class Curse extends Ritual {

    /**
     * @param power The amout of Curse points
     */
    public Curse(int power) {
        super(power);
    }

    /**
     * @return the power of damage dealt
     */
    @Override
    public String toString()
    {
        return "Curse";
    }


}
