package edu.insightr.spellmonger;

import javafx.scene.image.Image;
import org.apache.log4j.Logger;

/**
 * Class that defines a Card in the game
 * last edition : anthony add Image to each card
 */
abstract class PlayCard {

    private static final Logger logger = Logger.getLogger(SpellmongerApp.class);
    private final String name;
    private final int damage;
    private final boolean direct;
    private Player owner;
    private Image image;


    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage, boolean direct) {
        this.name = name;
        this.damage = damage;
        this.owner = null;
        this.direct = direct;
        this.image = new Image(getClass().getResourceAsStream("/img.png"));
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

    /**
     * Returns the damage of the card
     *
     * @return the damage (Integer)
     */
    int getDamage() {
        return this.damage;
    }

    /**
     * Returns the name of the card
     *
     * @return the name (String)
     */
    String getName() {
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

    public Image getImage() {
        return image;
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
