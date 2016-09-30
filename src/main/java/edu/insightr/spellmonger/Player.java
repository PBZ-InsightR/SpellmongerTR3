package edu.insightr.spellmonger;

import java.util.ArrayList;

/**
 * Created by Yasmeen on 28/09/2016.
 *
 * Defines a player
 */
class Player {

    private String name;
    private int lifePoints;
    private ArrayList<PlayCard> playerCreatures;
    private ArrayList<PlayCard> graveyard;

    Player(String name, int lifePoints){
        this.name=name;
        this.lifePoints=lifePoints;
        playerCreatures=new ArrayList<>();
        graveyard = new ArrayList<>();
    }


    String getName(){return name;}
    int getLifePoints(){return lifePoints;}

    /**
     * Adds a creature in the Beast list of the player
     * @param card : the Beast to put
     */
    void addCreature(PlayCard card){
        this.playerCreatures.add(card);
    }

    /**
     * Returns all the creatures
     * @return a list containing the creatures of the player
     */
    ArrayList<PlayCard> getCreatures(){
        // To make sure one can not modify our list freely, we return a clone
        ArrayList<PlayCard> clone = new ArrayList<>(this.playerCreatures.size());
        clone.addAll(this.playerCreatures);
        return clone;
    }

    void inflictDamages(int damage){this.lifePoints-=damage;}

    /**
     *
     * @param card : the card to put in the graveyard
     * @return true if the card was put in the graveyard, false otherwise
     */
    boolean addToGraveyard(PlayCard card){
        if (card != null){
            graveyard.add(card);
            return true;
        }
        return false;
    }

}
