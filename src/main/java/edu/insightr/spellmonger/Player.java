package edu.insightr.spellmonger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yasmeen on 28/09/2016.
 *
 * Defines a player
 */
public class Player {

    private String name;
    private int lifePoints;
    private List<PlayCard> playerCreatures;

    public Player(String name, int lifePoints){
        this.name=name;
        this.lifePoints=lifePoints;
        playerCreatures=new ArrayList<>(35);
    }

    public String getName(){return name;}
    public int getLifePoints(){return lifePoints;}

}
