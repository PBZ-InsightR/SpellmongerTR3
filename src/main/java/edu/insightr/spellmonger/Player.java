package edu.insightr.spellmonger;


import java.util.ArrayList;


/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
class Player {

    private final String name;
    private int lifePoints;
    private ArrayList<PlayCard> cardsInHand;

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
     * Returns the name of the player
     *
     * @return the name (String)
     */
    ArrayList<PlayCard> getHand() {
        return this.cardsInHand;
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
        card.setOwner(this);
        this.cardsInHand.remove(this.cardsInHand.size() - 1);
        game.playCard(card);
    }

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
