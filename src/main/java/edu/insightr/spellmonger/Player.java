package edu.insightr.spellmonger;


import java.util.ArrayList;


/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
class Player {

    private final String name;
    private int lifePoints;
    protected ArrayList<PlayCard> cardsInHand;

    /**
     * Constructor
     *
     * @param name       of the player
     * @param lifePoints of this player
     */
    Player(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.cardsInHand = new ArrayList<>();
//        this.game = game;
    }

    /**
     * Draws a card from the game
     */
    void drawACard(SpellmongerApp game) {
        PlayCard card = game.popCard();
        this.cardsInHand.add(card);
    }

    /**
     * Adds a card to the hand of the player
     * @param card : the card to be added
     */
    void addCardToHand(PlayCard card){
        this.cardsInHand.add(card);
    }


    /**
     * Returns a list of the card in the hand of the player
     * It is a clone, therefore noone can modify the list and affect the player
     * @return the list of the cards in hand
     */
    ArrayList<PlayCard> getCardsInHand() {
        ArrayList<PlayCard> clone = new ArrayList<>(this.cardsInHand.size());
        for(PlayCard card : this.cardsInHand) clone.add(card);
        return clone;
    }


    /**
     * @deprecated
     * This function has to be deleted and the usage must be changed
     * Use the other utility functions :
     *      - getCardsInHand() (it's a copy, therefore no manipulation affects the player)
     *      - numberOfCards()
     *      - stillHasCards()
     *      - addCardToHand(Playcard)
     * @return : the hand of the player
     */
    @Deprecated
    ArrayList<PlayCard> getHand(){
        return this.cardsInHand;
    }


    /**
     * Returns the number of cards in the hand of the player
     * @return the number of cards in hand
     */
    int numberOfCards(){
        return this.cardsInHand.size();
    }

    /**
     * Tells if the player still has cards in his hand
     *
     * @return : true if the player has cards
     */
    boolean stillHasCards() {
        return !(this.cardsInHand.isEmpty());
    }



    /**
     * Activates the card
     */
    void playACard(SpellmongerApp game) {
        // For the first level, we'll take the last card and remove it from the hand
        PlayCard card = this.cardsInHand.get(this.cardsInHand.size() - 1);
        //card.setOwner(this);
        this.cardsInHand.remove(this.cardsInHand.size() - 1);
        game.playCard(card);
    }

    /**
     * Returns the name of the player
     *
     * @return the name (String)
     */
    String getName() {
        return this.name;
    }

    /**
     * Returns the life points of the player
     *
     * @return the life points (Integer)
     */
    int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Return the state of life of the player
     *
     * @return wether the player is alive
     */
    boolean isDead() {
        return this.lifePoints <= 0;
    }

    /**
     * Deals some damage to the player
     *
     * @param damage : Damage to inflict
     */
    void inflictDamages(int damage) {
        this.lifePoints -= damage;
    }


}
