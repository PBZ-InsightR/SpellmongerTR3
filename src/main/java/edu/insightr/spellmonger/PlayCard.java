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
    int getDamage() {
        return this.damage;
    }
    String getName() {
        return this.name;
    }
    public String toString(){
        return "This Card named"+ this.name+"deals" + this.damage + " damage";

    }
}
