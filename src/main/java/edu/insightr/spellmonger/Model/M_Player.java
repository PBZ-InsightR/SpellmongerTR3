package edu.insightr.spellmonger.Model;


import java.util.ArrayList;


/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 * He has his name, his life points, his hand composed of 3 cards and 1 card's Stack which is initially composed of 21 cards
 */
public class M_Player {

    final ArrayList<M_PlayCard> cardsStack;
    final ArrayList<M_PlayCard> cardsInHand;
    private String name;
    private int lifePoints;

    /**
     * Constructor
     *
     * @param name       of the player
     * @param lifePoints of this player
     */
    public M_Player(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.cardsStack = new ArrayList<>();
        this.cardsInHand = new ArrayList<>();
    }

    /// *********** Getters ****************
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
     * Returns the number of cards in the hand of the player
     *
     * @return the number of cards in hand
     */
    int getnumberOfCards() {
        return this.cardsStack.size();
    }

    /// *********** Setters ****************

    /**
     * Returns a list of the card in the hand of the player
     * It is a clone, therefore no one can modify the list and affect the player
     *
     * @return the list of the cards in hand
     */
    public ArrayList<M_PlayCard> getCardsInHand() {
        ArrayList<M_PlayCard> clone = new ArrayList<>(this.cardsInHand.size());
        clone.addAll(this.cardsInHand);
        return clone;
    }

    /// *********** Methods ****************

    /**
     * Takes the id of the player, the id of the played card
     *
     * @param idPlayedCard id of the card played
     * @return the played card in the player's hand
     */
    public M_PlayCard playACard(int idPlayedCard) {
        M_PlayCard card = this.cardsInHand.get(idPlayedCard);
        this.cardsInHand.remove(idPlayedCard);
        return card;
    }


    /**
     * Draws a card from the game and add it to the cardsStack
     */
    void drawCardToStack(M_SpellmongerApp game) {
        M_PlayCard card = game.popCard();
        this.cardsStack.add(card);
    }

    /**
     * Draws a card from the player's stack and adds it to its hand
     * the top card.
     */
    void drawCardFromStack() {
        M_PlayCard card = cardsStack.get(cardsStack.size() - 1);
        cardsStack.remove(card);
        cardsInHand.add(card);
    }

    /**
     * Used for TESTS
     * Adds a card to the Stack of the player
     *
     * @param card : the card to be added
     */
    boolean addCardToStack(M_PlayCard card) {
        return this.cardsStack.add(card);
    }

    /**
     * Used for TESTS
     * Returns a list of the card in the Stack of the player
     * It is a clone, therefore no one can modify the list and affect the player
     *
     * @return the list of the cards in the stack
     */
    ArrayList<M_PlayCard> getCardsStack() {
        ArrayList<M_PlayCard> clone = new ArrayList<>(this.cardsStack.size());
        clone.addAll(this.cardsStack);
        return clone;
    }

    /**
     * Tells if the player still has cards in his hand
     *
     * @return : true if the player has cards
     */
    boolean stillHasCards() {
        return !(this.cardsStack.isEmpty());
    }

    /**
     * Return the state of life of the player
     *
     * @return whether the player is alive
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
