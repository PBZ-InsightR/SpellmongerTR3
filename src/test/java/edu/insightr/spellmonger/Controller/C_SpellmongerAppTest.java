package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.M_SpellmongerApp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Stan on 12/12/2016.
 */
public class C_SpellmongerAppTest {

    M_SpellmongerApp model;
    C_SpellmongerApp controller;

    @Before
    public void setUp() throws Exception {
        final int lifePoints = 20;
        List<String> playersList = new ArrayList<>(2);
        playersList.add(0, "Alice");
        playersList.add(1, "Bob");
        this.model = new M_SpellmongerApp(playersList, lifePoints);
        this.controller = new C_SpellmongerApp(model); // is observable
        this.model.distributeCardAmongPlayers();
    }

    @Test
    public void get3Cards() throws Exception {
        assertThat(controller.get3Cards(0).size(), is(3));
        assertThat(controller.get3Cards(1).size(), is(3));
    }

    @Test
    public void setPlayedCardNames() throws Exception {

    }

    @Test
    public void getPlayerAPoints() throws Exception {

    }

    @Test
    public void getPlayerBPoints() throws Exception {

    }

    @Test
    public void playTurn() throws Exception {

    }

    @Test
    public void resolveTurn() throws Exception {

    }

    @Test
    public void playRound() throws Exception {

    }

    @Test
    public void getPlayerNames() throws Exception {

    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void displayMenu() throws Exception {

    }

    @Test
    public void getCardPlayerFromView() throws Exception {

    }

    @Test
    public void getOpponentCard() throws Exception {

    }

    @Test
    public void getLastCardNameInGraveyard() throws Exception {

    }

    @Test
    public void getNbCardOpponent() throws Exception {
        assertThat(controller.getNbCardOpponent(0), is(3));
    }

    @Test
    public void getCards() throws Exception {

    }

}