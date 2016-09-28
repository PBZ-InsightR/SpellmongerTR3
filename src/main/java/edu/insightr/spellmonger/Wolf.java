package edu.insightr.spellmonger;

/**
 * Created by thomas.Rousse on 21/09/2016.
 * define Wolf
 */
public class Wolf extends Beast{

    /**
     * Default constructor for the wolf
     */
    public Wolf(){
        super(2);
    }

    /**
     * @return the type of the creature.
     */    @Override
    public String toString() {
        return "Wolf";
    }
}
