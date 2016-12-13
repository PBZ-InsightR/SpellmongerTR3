package edu.insightr.spellmonger.Model;


import java.util.ArrayList;


/**
 * Created by Yasmeen on 28/09/2016.
 * Defines a player
 */
public class Player {

    final ArrayList<PlayCard> cardsStack;
    final ArrayList<PlayCard> cardsInHand;
    protected String name;
    private int lifePoints;


    /**
     * Default constructor
     */
    public Player() {
        this.name = "Default";
        this.lifePoints = 20;
        this.cardsStack = new ArrayList<>();
        this.cardsInHand = new ArrayList<>(3);
    }

    /**
     * Constructor
     *
     * @param name       of the player
     * @param lifePoints of this player
     */
    public Player(String name, int lifePoints) {
        this.name = name;
        this.lifePoints = lifePoints;
        this.cardsStack = new ArrayList<>();
        this.cardsInHand = new ArrayList<>(3);
    }

    /**
     * Draws a card from the game and add it to the cardsStack
     */
    void drawCardToStack(SpellmongerApp game) {
        PlayCard card = game.popCard();
        this.cardsStack.add(card);
    }

    /**
     * Draws a card from the player's stack and adds it to its hand
     */
    void drawCardFromStack() {
        PlayCard card = cardsStack.get(cardsStack.size() - 1);
        cardsStack.remove(card);
        cardsInHand.add(card);
    }

    /**
     * For TESTS
     * Adds a card to the Stack of the player
     *
     * @param card : the card to be added
     */
    boolean addCardToStack(PlayCard card) {
        return this.cardsStack.add(card);
    }

    /**
     * Returns a list of the card in the Stack of the player
     * It is a clone, therefore no one can modify the list and affect the player
     *
     * @return the list of the cards in the stack
     */
    ArrayList<PlayCard> getCardsStack() {
        ArrayList<PlayCard> clone = new ArrayList<>(this.cardsStack.size());
        clone.addAll(this.cardsStack);
        return clone;
    }

    /**
     * Returns a list of the card in the hand of the player
     * It is a clone, therefore no one can modify the list and affect the player
     *
     * @return the list of the cards in hand
     */
    public ArrayList<PlayCard> getCardsInHand() {
        ArrayList<PlayCard> clone = new ArrayList<>(this.cardsInHand.size());
        clone.addAll(this.cardsInHand);
        return clone;
    }

    /**
     * Returns the number of cards in the hand of the player
     *
     * @return the number of cards in hand
     */
    int numberOfCards() {
        return this.cardsStack.size();
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
     * Takes the id of the player, the id of the played card
     *
     * @param idPlayedCard : the number of the played card
     * @return the played card in the player's hand
     */
    public PlayCard playACard(int idPlayedCard) {
        PlayCard card = this.cardsInHand.get(idPlayedCard);
        this.cardsInHand.remove(idPlayedCard);
        return card;
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
