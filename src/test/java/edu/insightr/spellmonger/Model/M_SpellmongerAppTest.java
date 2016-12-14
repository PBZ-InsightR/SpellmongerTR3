package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Standard M_Spellmonger test
 * Created by Yasmeen on 13/12/2016.
 */
public class M_SpellmongerAppTest {

    private M_SpellmongerApp model;
    private M_Beast bear;
    private M_Beast wolf;


    @Before
    public void setUp() throws Exception {
        final int lifePoints = 20;
        List<String> playersList = new ArrayList<>(2);
        playersList.add(0, "Alice");
        playersList.add(1, "Bob");
        this.model = new M_SpellmongerApp(playersList, lifePoints);
        this.model.distributeCardAmongPlayers();
        bear = new M_Beast("Bear", 3, 3);
        wolf = new M_Beast("Wolf", 2, 2);
    }

    @Test
    public void getRoundCounter() throws Exception {
        assertThat(model.getRoundCounter(), is(1));
        model.nextTurn();
        assertThat(model.getRoundCounter(), is(2));
    }

    @Test
    public void getGraveyard() throws Exception {
        assertThat(model.getGraveyard().size(), is(0));
        model.playCard(bear);
        model.nextTurn();
        assertThat(model.getGraveyard().size(), is(1));
    }

    @Test
    public void getPlayerA() throws Exception {
        assertThat(model.getPlayerA().getName(), is("Alice"));
        assertThat(model.getPlayerA().getLifePoints(), is(20));
    }

    @Test
    public void getPlayerB() throws Exception {
        assertThat(model.getPlayerB().getName(), is("Bob"));
        assertThat(model.getPlayerB().getLifePoints(), is(20));
    }

    @Test
    public void getPlayer() throws Exception {
        assertThat(model.getPlayer(0).getName(), is("Alice"));
        assertThat(model.getPlayer(0).getLifePoints(), is(20));
        assertThat(model.getPlayer(1).getName(), is("Bob"));
        assertThat(model.getPlayer(1).getLifePoints(), is(20));
    }

    @Test
    public void getPlayerNames() throws Exception {
        assertThat(model.getPlayerNames()[0], is("Alice"));
        assertThat(model.getPlayerNames()[1], is("Bob"));
    }

    @Test
    public void setName() throws Exception {
        model.setName("P1", "Maurice");
        model.setName("P2", "Jacob");
        assertThat(model.getPlayerNames()[0], is("Maurice"));
        assertThat(model.getPlayerNames()[1], is("Jacob"));
    }

    @Test
    public void playCard() throws Exception {
        model.playCard(bear);
        model.playCard(wolf);
        assertTrue(model.isBoardFull());
        assertThat(model.getCardOnBoardOf(0), is(bear));
        assertThat(model.getCardOnBoardOf(1), is(wolf));
    }

    @Test
    public void nextTurn() throws Exception {
        assertThat(model.getGraveyard().size(), is(0));
        assertThat(model.getRoundCounter(), is(1));
        model.playCard(bear);
        model.nextTurn();
        assertThat(model.getGraveyard().size(), is(1));
        assertThat(model.getRoundCounter(), is(2));
    }

    @Test
    public void distributeCardAmongPlayers() throws Exception {
        assertThat(model.getPlayer(0).getCardsInHand().size(), is(3));
        assertThat(model.getPlayer(0).getCardsStack().size(), is(18));
        assertThat(model.getPlayer(1).getCardsInHand().size(), is(3));
        assertThat(model.getPlayer(1).getCardsStack().size(), is(18));
    }


    @Test
    public void pop3Cards() throws Exception {
        assertThat(model.getPlayer(0).getCardsStack().size(), is(18));
        model.pop3Cards();
        assertThat(model.getPlayer(0).getCardsStack().size(), is(15));
    }

    @Test
    public void playersStacksAreEmpty() throws Exception {
        int cardChangements = 6;
        assertFalse(model.playersStacksAreEmpty());
        for (int i = 0; i < cardChangements; i++) {
            model.pop3Cards();
        }
        assertTrue(model.playersStacksAreEmpty());
    }

    @Test
    public void playersHandsAreEmpty() throws Exception {
        assertFalse(model.playersHandsAreEmpty());
    }

    @Test
    public void shuffleGraveYardToStack() throws Exception {
        int round = 5;
        assertThat(model.getGraveyard().size(), is(0));
        for (int i = 1; i < round; i++) {
            model.playCard(bear);
            model.nextTurn();
        }
        assertThat(model.getGraveyard().size(), is(4));
        model.shuffleGraveYardToStack();
        assertThat(model.getGraveyard().size(), is(0));

    }

    @Test
    public void getCardOnBoardOf() throws Exception {
        model.playCard(bear);
        model.playCard(wolf);
        assertThat(model.getCardOnBoardOf(0), is(bear));
        assertThat(model.getCardOnBoardOf(1), is(wolf));
    }

    @Test
    public void isBoardFull() throws Exception {
        model.playCard(bear);
        model.playCard(wolf);
        assertTrue(model.isBoardFull());
        model.playCard(bear);
        assertFalse(model.isBoardFull());
    }

    @Test
    public void getLastCardsGraveyard() throws Exception {
        assertThat(model.getGraveyard().size(), is(0));
        model.playCard(bear);
        model.nextTurn();
        assertThat(model.getLastCardsGraveyard(0), is(bear));
        model.playCard(wolf);
        model.nextTurn();
        assertThat(model.getLastCardsGraveyard(0), is(wolf));
    }

}