package edu.insightr.spellmonger;

import java.util.Random;

/**
 * Created by sibel on 10/10/2016.
 * Define the Levels of the IA for the application
 */
class SmartPlay extends Player{

    private int level;
    /**
     *
     * @param level of the game
     */
     SmartPlay(String name, int lifePoints, int level){
        super(name,lifePoints);
        this.level = level;
    }

     SmartPlay(String name, int lifePoints){
        super(name,lifePoints);
        this.level = 0;
    }

    /**
     * @return a random number that will be the position of the card in the hand of a player
     */
    int level0(){
        if(getHand().isEmpty())
            return 0;
        int size = getHand().size();
        return new Random().nextInt(size);
    }

    /**
     * Ovveride the play of a card from playcard, with the IA
     *
     * @param game the app
     */
    @Override
    void playACard(SpellmongerApp game) {
        int playCardNumber;

        switch(level)
        {
            case 0:
            default:
                playCardNumber=level0();
                break;

        }
        if(getHand().isEmpty())
            return;

        PlayCard card = getHand().get(playCardNumber);
        card.setOwner(this);
        getHand().remove(playCardNumber);
        game.playCard(card);
    }
}
