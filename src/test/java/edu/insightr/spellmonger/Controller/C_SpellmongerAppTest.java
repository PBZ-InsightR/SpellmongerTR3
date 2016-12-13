package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.M_SpellmongerApp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Standard test for the C_SpellmongerApp class
 * Created by Yasmeen on 13/12/2016.
 */
public class C_SpellmongerAppTest {

    private M_SpellmongerApp model;
    private C_SpellmongerApp controller;

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
    public void play() throws Exception {

    }

    @Test
    public void play_IA() throws Exception {

    }

    @Test
    public void get3Cards() throws Exception {
        assertThat(controller.get3Cards(0).size(), is(3));
        assertThat(controller.get3Cards(1).size(), is(3));
    }

    @Test
    public void setPlayedCardNames() throws Exception {
        controller.setPlayedCardNames("Bear",0);
        controller.setPlayedCardNames("Wolf",1);
        assertThat(controller.getOpponentCard(1), is("Bear"));
        assertThat(controller.getOpponentCard(0), is("Wolf"));
    }

    @Test
    public void getPlayerLifePoints() throws Exception {
        assertThat(controller.getPlayerLifePoints(0), is(20));
        assertThat(controller.getPlayerLifePoints(1), is(20));
    }

    @Test
    public void getPlayerNames() throws Exception {
        assertThat(controller.getPlayerNames()[0], is("Alice"));
        assertThat(controller.getPlayerNames()[1], is("Bob"));
    }

    @Test
    public void setPlayerName() throws Exception {
        controller.setPlayerName("P1","Maurice");
        controller.setPlayerName("P2","Jacob");
        assertThat(controller.getPlayerNames()[0], is("Maurice"));
        assertThat(controller.getPlayerNames()[1], is("Jacob"));
    }

    @Test
    public void displayMenu() throws Exception {

    }

    @Test
    public void setCardPlayerFromView() throws Exception {

    }

    @Test
    public void getOpponentCard() throws Exception {
        controller.setPlayedCardNames("Poison",0);
        controller.setPlayedCardNames("Heal",1);
        assertThat(controller.getOpponentCard(1), is("Poison"));
        assertThat(controller.getOpponentCard(0), is("Heal"));
    }

    @Test
    public void subscribe() throws Exception {

    }

    @Test
    public void notifyObserver() throws Exception {

    }

    @Test
    public void getLastCardNameInGraveyard() throws Exception {
        String cardFound="";
        String cards[]={"Bear","Wolf","Eagle","Heal","Poison","Shield"};
        for(String card : cards){
            if(card.equals(controller.getLastCardNameInGraveyard(0))) cardFound=card;
        }
        assertTrue(cardFound.equals(controller.getLastCardNameInGraveyard(0)));
    }

    @Test
    public void getNbCardOpponent() throws Exception {
        assertThat(controller.getNbCardOpponent(0), is(3));
    }

    @Test
    public void getCards() throws Exception {
        assertThat(controller.getCards(0).size(), is(3));
    }

    @Test
    public void playerIsP2() throws Exception {
        assertFalse(controller.playerIsP2(0));
        assertTrue(controller.playerIsP2(1));
    }

}