package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Created by Stan on 07/12/2016.
 */
public class PlayerUnitTests {
    private Player player;
    private PlayCard beastCard;

    @Before
    public void setUp() throws Exception {
        this.player = new Player("Alice", 20);
        this.beastCard = new Beast("Bear", 3, 3);
    }

    @Test
    public void drawACard() throws Exception {

    }

    @Test
    public void addCardToHand() throws Exception {
        assertThat(this.player.addCardToStack(beastCard), is(true));
    }

    @Test
    public void getCardsStack() throws Exception {
        assertThat(this.player.getCardsStack().size(), is(equalTo(0)));
        this.player.cardsStack.add(beastCard);
        assertThat(this.player.getCardsStack().size(), is(equalTo(1)));
    }

    @Test
    public void numberOfCards() throws Exception {
        assertThat(this.player.numberOfCards(), is(equalTo(0)));
        this.player.cardsStack.add(beastCard);
        assertThat(this.player.numberOfCards(), is(equalTo(1)));
    }

    @Test
    public void stillHasCards() throws Exception {
        assertThat(this.player.stillHasCards(), is(false));
        this.player.cardsStack.add(beastCard);
        assertThat(this.player.stillHasCards(), is(true));
    }

    @Test
    public void getName() throws Exception {
        assertThat(this.player.getName(), is(equalTo("Alice")));
    }

    @Test
    public void getLifePoints() throws Exception {
        assertThat(this.player.getLifePoints(), is(equalTo(20)));
    }

    @Test
    public void isDead() throws Exception {
        assertThat(this.player.getLifePoints(), is(equalTo(20)));
        assertThat(this.player.isDead(), is(false));
        this.player.inflictDamages(20);
        assertThat(this.player.getLifePoints(), is(equalTo(0)));
        assertThat(this.player.isDead(), is(true));
    }

    @Test
    public void inflictDamages() throws Exception {
        assertThat(this.player.getLifePoints(), is(equalTo(20)));
        this.player.inflictDamages(5);
        assertThat(this.player.getLifePoints(), is(equalTo(15)));
        this.player.inflictDamages(-2);
        assertThat(this.player.getLifePoints(), is(equalTo(17)));
    }
}
