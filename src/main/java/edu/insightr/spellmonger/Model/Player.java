package edu.insightr.spellmonger.Model;


import java.util.ArrayList;


/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
public class Player {

    private String name;
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
    public void drawACard(SpellmongerApp game) {
        PlayCard card = game.popCard();
        this.cardsInHand.add(card);
    }

    /**
     * Adds a card to the hand of the player
     * @param card : the card to be added
     */
    public boolean addCardToHand(PlayCard card) {
        return this.cardsInHand.add(card);
    }


    /**
     * Returns a list of the card in the hand of the player
     * It is a clone, therefore no one can modify the list and affect the player
     * @return the list of the cards in hand
     */
    public ArrayList<PlayCard> getCardsInHand() {
        ArrayList<PlayCard> clone = new ArrayList<>(this.cardsInHand.size());
        for(PlayCard card : this.cardsInHand) clone.add(card);
        return clone;
    }

    /**
     * Returns the number of cards in the hand of the player
     * @return the number of cards in hand
     */
    public int numberOfCards() {
        return this.cardsInHand.size();
    }

    /**
     * Tells if the player still has cards in his hand
     *
     * @return : true if the player has cards
     */
    public boolean stillHasCards() {
        return !(this.cardsInHand.isEmpty());
    }



    /**
     * Activates the card
     */
    public void playACard(SpellmongerApp game) {
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
    public String getName() {
        return this.name;
    }

    /**
     * Change the name of the player
     *
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Returns the life points of the player
     *
     * @return the life points (Integer)
     */
    public int getLifePoints() {
        return this.lifePoints;
    }

    /**
     * Return the state of life of the player
     *
     * @return wether the player is alive
     */
    public boolean isDead() {
        return this.lifePoints <= 0;
    }

    /**
     * Deals some damage to the player
     *
     * @param damage : Damage to inflict
     */
    public void inflictDamages(int damage) {
        this.lifePoints -= damage;
    }


}
