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
    private final int cardValue;
    private Player owner;
    private Image image;


    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage, boolean direct, int cardValue) {
        this.name = name;
        this.damage = damage;
        this.owner = null;
        this.direct = direct;
        this.cardValue = cardValue;
        setImage();
    }

    /**
     * @return wether the card deals direct damage
     */
    public boolean isDirect() {
        return this.direct;
    }

    public int getCardValue() {
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


    private void setImage() {
        String imgPath;
        switch (this.name) {
            case "Bear":
                imgPath = "/bear.png";
                break;
            case "Wolf":
                imgPath = "/wolf.png";
                break;
            case "Eagle":
                imgPath = "/eagle.png";
                break;
            case "Shield":
                imgPath = "/shield.png";
                break;
            case "Poison":
                imgPath = "/poison.png";
                break;
            case "Heal":
                imgPath = "/heal.png";
                break;
            default:
                imgPath = "/img.jpg";
                break;
        }
        this.image = new Image(getClass().getResourceAsStream(imgPath));
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
