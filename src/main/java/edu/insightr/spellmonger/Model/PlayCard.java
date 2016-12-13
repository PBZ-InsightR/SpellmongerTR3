package edu.insightr.spellmonger.Model;

/**
 * Class that defines a Card in the game
 * last edition : anthony add Image to each card
 */
public abstract class PlayCard {

    private final String name;
    private final int damage;
    private final boolean direct;
    private final int cardValue;
    private Player owner;


    /**
     * @param name of card {Creature, Ritual}
     */
    PlayCard(String name, int damage, boolean direct, int cardValue) {
        this.name = name;
        this.damage = damage;
        this.owner = null;
        this.direct = direct;
        this.cardValue = cardValue;
    }

    /**
     * @return whether the card deals direct damage
     */
    public boolean isDirect() {
        return this.direct;
    }

    int getCardValue() {
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
    public void setOwner(Player owner) {
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
