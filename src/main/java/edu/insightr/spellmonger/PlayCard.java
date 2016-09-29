package edu.insightr.spellmonger;

/**
 * <p>
 * Class that defines a Card in the game
 */
public abstract class PlayCard {

    protected String name;
    protected int damage;

    /**
     * Default constructor for the playcard
     */
    public PlayCard() {

    }

    /**
     * @param name  of card {Creature, Ritual}
     */
    public PlayCard(String name,int damage) {
        this.name = name;
        this.damage=damage;
    }
    public int getDamage() {
        return this.damage;
    }

    public String toString(){
        return "This Card named"+ this.name+"deals" + this.damage + " damage";

    }
}
