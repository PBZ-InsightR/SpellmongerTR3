package edu.insightr.spellmonger;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by sibel on 10/10/2016.
 * Define the Levels of the IA for the application
 */
class SmartPlayer extends Player {

    private int level;
    private ArrayList<PlayCard> cardToPlay;
    private int round;
    private int[] numberValue ;
    private int[] opponentNumberValue ;

    /**
     * @param level of the game
     */
    SmartPlayer(String name, int lifePoints, int level) {
        super(name, lifePoints);
        this.level = level;
        this.cardToPlay = new ArrayList<>();
        this.round = 1;
    }

    SmartPlayer(String name, int lifePoints) {
        super(name, lifePoints);
        this.cardToPlay = new ArrayList<>();
        this.level = 1;
        this.round = 1;
    }

    /**
     * @return a random number that will be the position of the card in the hand of a player
     */
    private int level0() {
        if (this.cardsInHand.isEmpty())
            return 0;

        int size = this.cardsInHand.size();
        return new Random().nextInt(size);
    }

    /**
     * @return the number of the card the player'll play in function of the value of his deck
     */
    private int level1() {
        if (this.round != 1) {
            this.cardToPlay.clear();
        }

        ++round;
        if(!this.cardsInHand.isEmpty()){
        int avg = getDeckPower() / this.cardsInHand.size();  //avg of the game is 2.1

        if (avg > 2.3) {
            getStrongCardList();

        } else if (avg < 1.9) {
            getAverageCardList();
        } else {
            getBadCardList();
        }
        }
        return chooseCard();

    }

    private void figureHands()
    {
        for(PlayCard currentCard : this.cardsInHand)
            this.numberValue[currentCard.getCardValue()]++;
        opponentNumberValue[0] = DeckCreator.getNumberCard().get("numShield")-this.numberValue[0];
        opponentNumberValue[3] = DeckCreator.getNumberCard().get("numBear")+DeckCreator.getNumberCard().get("numHeal")+DeckCreator.getNumberCard().get("numPoison")-this.numberValue[3];
        opponentNumberValue[2] = DeckCreator.getNumberCard().get("numWolf")-this.numberValue[2];
        opponentNumberValue[1] = DeckCreator.getNumberCard().get("numEagle") - this.numberValue[1];
    }

    private boolean haveShield(){

        return numberValue[0] != 0;

    }


    private boolean betterPlayingNextTurn(int value){
        // remplacer la main par celle de l'adversaire
        boolean result = true;
        int n = this.cardsInHand.size();
        double proportion = numberValue[value]/n ;
        double PGetValueNextTurn =  ( ( 1-proportion )*opponentNumberValue[value]-1 )/( n-1 ) + proportion*opponentNumberValue[value]/( n-1 );
        if(PGetValueNextTurn <= proportion) result = false ;

        return result;
    }


    int level2(){

        figureHands();
        int index = 0;
        if(this.haveShield())
            if (betterPlayingNextTurn(3)) {
                  int[] values = {2,3};
                  index = chooseCard(values);
            } else index = chooseCard(0);
        //if(opponent.haveShield()) same than this but with p = 0
        return index;
    }



    /**
     * @return the Destruction power of a deck
     */
    private int getDeckPower() {
        int count = 0;

        for (PlayCard e : this.cardsInHand) {
            count += e.getCardValue();
        }

        return count;
    }

    /**
     * @return the number of the card to choose
     */


    private int chooseCard() {
        if (this.cardToPlay.isEmpty())
            return 0;

        int size = this.cardToPlay.size();
        return new Random().nextInt(size);
    }

    private int chooseCard(int value){
        int index = 0;
        for (PlayCard currentCard : this.cardsInHand){
                if(currentCard.getCardValue()==value){

                    return index ;
                }
                index++;
        }
        return index;
    }

    private int chooseCard(String Name){
        int index = 0;
        for (PlayCard currentCard : this.cardsInHand){
            if(Name.equals(currentCard.getName())){

                return index ;
            }
            index++;
        }
        return index;
    }

    private int chooseCard(int[] tabValue){

        int index=-1;
        for(int value : tabValue){
           index = chooseCard(value);
           if(index!=-1){
               return index;
           }
        }
        return chooseCard();
    }
    /**
     *  The list the player can play is with his strongest cards
     */
    private void getStrongCardList() {


        for (PlayCard currentCard : this.cardsInHand) {
            if (currentCard.getCardValue() >= 2) {
                this.cardToPlay.add(currentCard);
            }
        }

        /*if (this.cardToPlay.isEmpty()) {
            getAverageCardList();
        */
    }


    /**
     *  The list the player can play is with his average cards
     */
    private void getAverageCardList() {


        for (PlayCard currentCard : this.cardsInHand){
            if (currentCard.getCardValue() == 2) {
                this.cardToPlay.add(currentCard);
            }
        }

        if(this.cardToPlay.isEmpty()) {
            getStrongCardList();
        }

    }

    /**
     * The list the player can play is with his lowest cards
     */
     private void getBadCardList() {


         for (PlayCard currentCard : this.cardsInHand){
            if (currentCard.getCardValue() <= 2) {
                this.cardToPlay.add(currentCard);

            }
        }

        /*if (this.cardToPlay.isEmpty()) {
            getAverageCardList();
        }*/

       /* if (this.cardToPlay.isEmpty()) {
            getStrongCardList();
        }*/

    }

    /**
     * Ovveride the play of a card from playcard, with the IA
     *
     * @param game the app
     */
    @Override
    void playACard(SpellmongerApp game) {
        int playCardNumber;

        switch (level) {
            case 0:
                playCardNumber = level0();
                break;
            case 1:
                playCardNumber = level1();
                break;
            default:
                playCardNumber = level1();
                break;
        }

        if (this.cardsInHand.isEmpty())
            return;

        PlayCard card = this.cardsInHand.get(playCardNumber);
        //card.setOwner(this);
        this.cardsInHand.remove(playCardNumber);
        game.playCard(card);
    }
}
