package edu.insightr.spellmonger;

/**
 * Created by thomas.Rousse on 21/09/2016.
 * define Bear
 */
public class Bear extends Beast {

    /**
     * Default Constructor for bear
     */
    public Bear(){
        super(3);
    }

    /**
     * @return the type of the creature.
     */
    @Override
    public String toString() {
        return "Bear";
    }

}
