package edu.insightr.spellmonger;

/**
 * Created by Hugues on 20/10/2016.
 */
public interface Strategy {
    public void doOperation(PlayCard cardA, PlayCard cardB, SpellmongerApp engine);
}
