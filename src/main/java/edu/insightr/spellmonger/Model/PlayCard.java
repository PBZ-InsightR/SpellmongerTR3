package edu.insightr.spellmonger.Model;

import javafx.scene.image.Image;
import org.apache.log4j.Logger;

/**
 * Class that defines a Card in the game
 * last edition : anthony add Image to each card
 */
public abstract class PlayCard {

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private final String name;
    private final int damage;
    private final boolean direct;
    private Player owner;
    private final int cardValue;


    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage, boolean direct,int cardValue)  {
        this.name = name;
        this.damage = damage;
        this.owner = null;
        this.direct = direct;
        this.cardValue = cardValue;
    }

    public boolean isDirect(){
        return this.direct;
    }
    /**
     * need to be overRide in children's definition
     */
    public void activate(SpellmongerApp app) {
        logger.info("erreur : action has not been implemented in a subclass of PLayCard");
    }

    public int getCardValue(){
        return this.cardValue;
    }
    /**
     * Returns the damage of the card
     *
     * @return the damage (Integer)
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns the name of the card
     *
     * @return the name (String)
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the owner of the card
     *
     * @return the owner (Player)
     */
    Player getOwner() {
        return this.owner;
    }

    /**
     * Sets the owner of the card
     *
     * @param owner : the owner of the card
     */
    void setOwner(Player owner) {
        this.owner = owner;
    }



    /**
     * Creates a string to describe the card
     *
     * @return the description of the card (String)
     */
    public String toString() {
        return "This Card named " + this.name + " deals " + this.damage + " damage";
    }


}
