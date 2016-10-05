package edu.insightr.spellmonger;

/**
 * Class that defines a Card in the game
 */
abstract class PlayCard implements Cloneable{

     private final String name;
     private final int damage;

    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    /**
     * Returns the damage of the card
     * @return the damage (Integer)
     */
    int getDamage() {
        return this.damage;
    }

    /**
     * Returns the name of the card
     * @return the name (String)
     */
    String getName() {
        return this.name;
    }

    /**
     * Creates a string to describe the card
     * @return the description of the card (String)
     */
    public String toString() {
        return "This Card named " + this.name + " deals " + this.damage + " damage";
    }

    /**
     * Clones the objects (is used to create the cards deck
     * @return a clone of the object
     */
    public Object clone(){
        Object o = null;
        try {
            // We create a clone of the card
            o = super.clone();
        } catch(CloneNotSupportedException cnse) {
            // Should not happen since we implement the Cloneable interface
            // but it's always better to make sure it does not crash
            cnse.printStackTrace(System.err);
        }
        // return the clone
        return o;
    }

}
