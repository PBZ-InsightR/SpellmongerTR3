package edu.insightr.spellmonger.Model;

/**
 * Created by Stanislas Daniel Claude D on 21/09/2016.
 * Defines a standard beast
 */
public class Beast extends PlayCard {
    //private static final Logger logger = Logger.getLogger(SpellmongerApp.class);

    /**
     * @param name   is the name of the beast
     * @param damage is an heritage from the constructor of PlayCard
     */
    public Beast(String name, int damage, int cardValue) {
        super(name, damage, false, cardValue);
    }

    /**
     * Creates a string to describe the card. This is an override of the toString() function in the Card class.
     *
     * @return the description of the card (String)
     */
    @Override
    public String toString() {
        return this.getName();// +this.info()
    }

    /*
    public String info() {
        return " : deals " + this.getDamage() + " damage";
    }
    */
}
