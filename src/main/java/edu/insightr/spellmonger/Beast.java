package edu.insightr.spellmonger;

/**
 * Created by Stanislas Daniel Claude Dolcini on 21/09/2016.
 * Defines a standard beast
 */
public abstract class Beast extends PlayCard {
    private final int damage;

    /**
     * @param damage the damage dealt by the beast
     */
    public Beast(int damage) {
        super("Creature");
        this.damage = damage;
    }

    /**
     * @return the current damage dealt by the beast
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * @return description string of the class.
     */
    @Override
    public String toString() {
        return "This beast deals " + this.damage + " damage";
    }
}
