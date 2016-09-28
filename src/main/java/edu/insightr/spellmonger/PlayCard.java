package edu.insightr.spellmonger;

/**
 * Created by Tara on 26/09/2016.
 * <p>
 * Class that defines a Card in the game
 */
public abstract class PlayCard {

    protected String type;

    /**
     * Default constructor for the playcard
     * @param type Type of card {Creature, Ritual}
     */
    public PlayCard(String type) {
        this.type = type;
    }
}
