package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Unit tests for the M_Player class
 * Created by Stan on 07/12/2016.
 */
public class M_PlayerUnitTests {
    private M_Player MPlayer;
    private M_PlayCard beastCard;

    @Before
    public void setUp() throws Exception {
        this.MPlayer = new M_SmartPlayer("Alice", 20);
        this.beastCard = new M_Beast("Bear", 3, 3);
    }

    @Test
    public void addCardToHand() throws Exception {
        assertThat(this.MPlayer.addCardToStack(beastCard), is(true));
    }

    @Test
    public void getCardsStack() throws Exception {
        assertThat(this.MPlayer.getCardsStack().size(), is(equalTo(0)));
        this.MPlayer.cardsStack.add(beastCard);
        assertThat(this.MPlayer.getCardsStack().size(), is(equalTo(1)));
    }

    @Test
    public void numberOfCards() throws Exception {
        assertThat(this.MPlayer.getnumberOfCards(), is(equalTo(0)));
        this.MPlayer.cardsStack.add(beastCard);
        assertThat(this.MPlayer.getnumberOfCards(), is(equalTo(1)));
    }

    @Test
    public void stillHasCards() throws Exception {
        assertThat(this.MPlayer.stillHasCards(), is(false));
        this.MPlayer.cardsStack.add(beastCard);
        assertThat(this.MPlayer.stillHasCards(), is(true));
    }

    @Test
    public void getName() throws Exception {
        assertThat(this.MPlayer.getName(), is(equalTo("Alice")));
    }

    @Test
    public void getLifePoints() throws Exception {
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(20)));
    }

    @Test
    public void isDead() throws Exception {
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(20)));
        assertThat(this.MPlayer.isDead(), is(false));
        this.MPlayer.inflictDamages(20);
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(0)));
        assertThat(this.MPlayer.isDead(), is(true));
    }

    @Test
    public void inflictDamages() throws Exception {
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(20)));
        this.MPlayer.inflictDamages(5);
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(15)));
        this.MPlayer.inflictDamages(-2);
        assertThat(this.MPlayer.getLifePoints(), is(equalTo(17)));
    }
}
