package edu.insightr.spellmonger;

/**
 * The class which manages the context
 * Created by Hugues on 20/10/2016.
 */
public class Context {

    private Strategy strategy;
    private SpellmongerApp engine;
    private boolean strategyIsSet;

    public Context(SpellmongerApp engine){
        this.engine = engine;
    }

    public void setStrategy(Strategy strat){
        this.strategy = strat;
        this.strategyIsSet = true;
    }

    public void executeStrategy(PlayCard cardA, PlayCard cardB){
        if(strategyIsSet) strategy.doOperation(cardA, cardB, engine);
        strategyIsSet =false;
    }
}
