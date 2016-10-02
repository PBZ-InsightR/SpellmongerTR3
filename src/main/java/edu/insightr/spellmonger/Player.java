package edu.insightr.spellmonger;

import java.util.ArrayList;

/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
class Player {

    private final String name;
    private int lifePoints;
    private ArrayList<PlayCard> playerCreatures;

    /**
     * Constructor
     * @param name       of the player
     * @param lifePoints of this player
     */
    Player(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.playerCreatures = new ArrayList<>();
    }

    /**
     * Returns the name of the player
     * @return the name (String)
     */
    String getName() {
        return this.name;
    }

    /**
     * Returns the life points of the player
     * @return the life points (Integer)
     */
    int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Adds a creature in the Beast list of the player
     * @param card : the Beast to put
     */
    void addCreature(PlayCard card) {
        this.playerCreatures.add(card);
    }

    /**
     * Returns all the creatures
     * @return a list containing the creatures of the player
     */
    ArrayList<PlayCard> getCreatures() {
        // To make sure one can not modify our list freely, we return a clone
        ArrayList<PlayCard> clone = new ArrayList<>(this.playerCreatures.size());
        clone.addAll(this.playerCreatures);
        return clone;
    }

    /**
     * Deals some damage to the player
     * @param damage : Damage to inflict
     */
    void inflictDamages(int damage) {
        this.lifePoints -= damage;
    }
}
