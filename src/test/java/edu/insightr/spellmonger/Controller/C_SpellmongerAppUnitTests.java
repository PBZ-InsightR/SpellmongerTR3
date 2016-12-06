package edu.insightr.spellmonger.Controller;

import edu.insightr.spellmonger.Model.SpellmongerApp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Created by Stan on 07/12/2016.
 */
public class C_SpellmongerAppUnitTests {


    /**
     * Created by Stan on 30/11/2016.
     */

    C_SpellmongerApp controller;

    @Before
    public void setUp() throws Exception {
        final int lifePoints = 20;

        List<String> playersList = new ArrayList<>();
        playersList.add("Alice");
        playersList.add("Bob");

        SpellmongerApp model = new SpellmongerApp(playersList, lifePoints);
        this.controller = new C_SpellmongerApp(model); // is observable
        controller.play();
    }

    @Test
    public void play() throws Exception {

    }

    @Test
    public void get3Cards() throws Exception {
        assertThat(controller.get3Cards("Bob").size(), is(equalTo(3)));
    }

    @Test
    public void playTurn() throws Exception {

    }

    @Test
    public void getRoundCounter() throws Exception {

    }

    @Test
    public void getPlayerNames() throws Exception {

    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void subscribe() throws Exception {

    }

    @Test
    public void unsubscribe() throws Exception {

    }

    @Test
    public void notifyObserver() throws Exception {

    }

    @Test
    public void displayMenu() throws Exception {

    }

    @Test
    public void displayBoard() throws Exception {

    }

}
