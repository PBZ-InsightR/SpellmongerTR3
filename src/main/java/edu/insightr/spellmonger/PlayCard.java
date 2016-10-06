package edu.insightr.spellmonger;

import org.apache.log4j.Logger;

/**
 * Class that defines a Card in the game
 */
abstract class PlayCard implements Cloneable,CardActivation{

     private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
     private final String name;
     private final int damage;
     private Player owner;


    /**
     * need to be overRide in children's definition
     */
   public void activate(SpellmongerApp app){
       logger.info("erreur : action has not been implemented in a subclass of PLayCard");
   }

    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage) {
        this.name = name;
        this.damage = damage;
        this.owner = null;
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
     * Returns the owner of the card
      * @return the owner (Player)
     */
    Player getOwner(){return this.owner;}
    /**
     * Creates a string to describe the card
     * @return the description of the card (String)
     */
    public String toString() {
        return "This Card named " + this.name + " deals " + this.damage + " damage";
    }

    /**
     * Clones the objects (is used to create the cards deck)
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

    /**
     * Sets the owner of the card
     * @param owner : the owner of the card
     */
    void setOwner(Player owner){
        this.owner = owner;
    }

}
