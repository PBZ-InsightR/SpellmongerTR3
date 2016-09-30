package edu.insightr.spellmonger;

/**
 * Created by Stanislas Daniel Claude D on 21/09/2016.
 * Defines a standard beast
 */
class Beast extends PlayCard {


    /**
     * @param name is the name of the beast
     * @param damage is an heritage from the constructor of PlayCard

     *
     */
    Beast(String name, int damage) {
        super(name, damage);
    }

    /**
     * Creates a string to describe the card. This is an override of the toString() function in the Card class.
     * @return the description of the card (String)
     */
    @Override
    public String toString() {
        return getName() +" : deals " + getDamage() + " damage";
    }

}
