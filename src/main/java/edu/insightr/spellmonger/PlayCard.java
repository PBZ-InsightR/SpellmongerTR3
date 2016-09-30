package edu.insightr.spellmonger;

/**
 * <p>
 * Class that defines a Card in the game
 */
abstract class PlayCard {

    final private String name;
    final private int damage;

//    /**
//     * Default constructor for the playcard
//     */
//    public PlayCard() {
//
//    }

    /**
     * @param name  of card {Creature, Ritual}
     */
    PlayCard(String name,int damage) {
        this.name = name;
        this.damage=damage;
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
    public String toString(){
        return "This Card named"+ this.name+"deals" + this.damage + " damage";

    }
}
