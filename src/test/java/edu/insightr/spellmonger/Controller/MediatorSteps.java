package edu.insightr.spellmonger.Controller;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import edu.insightr.spellmonger.Model.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


/**
 * Test a final turn resolution
 * Created by Stan on 07/12/2016.
 */

public class MediatorSteps {
    private Player playerA,playerB;
    private PlayCard cardA, cardB, cardC, cardD;


    @Given("^a player named \"([^\"]*)\" with (\\d+) health points$")
    public void aPlayerNamedWithHealthPoints(String name, int hp) throws Throwable {
        if(playerA == null)
            playerA = new Player(name, hp);
        if(playerB == null)
            playerB = new Player(name, hp);
    }


    @Given("^a playCard an \"([^\"]*)\" with an attack of (\\d+)$")
    public void aPlayCardAnWithAnAttackOf(String name, int attack) throws Throwable {
        if(cardA == null)
        {
            cardA = new Beast(name,attack,3);
            cardA.setOwner(playerA);
            assertThat(cardA.getDamage(), is(equalTo(3)));
            assertThat(cardA.getName(), is(equalTo("Eagle")));
        }

        else if(cardB == null)
        {
            cardB = new Beast(name,attack,5);
            cardB.setOwner(playerB);
            assertThat(cardB.getDamage(), is(equalTo(5)));
            assertThat(cardB.getName(), is(equalTo("Bear")));
        }

        else if(cardC == null)
        {
            cardC = new Ritual("Poison", 3, false, true, 3);
            cardC.setOwner(playerA);
            assertThat(cardC.getDamage(), is(equalTo(3)));
            assertThat(cardC.getName(), is(equalTo("Poison")));
        }

        else if(cardD == null)
        {
            cardD = new Beast(name,attack,5);
            cardD.setOwner(playerB);
            assertThat(cardD.getDamage(), is(equalTo(1)));
            assertThat(cardD.getName(), is(equalTo("Eagle")));
        }




    }


    @Then("^\"([^\"]*)\" Should be dead$")
    public void shouldBeDead(String arg0) throws Throwable {
        Mediator.getInstance().resolveTurn(this.playerA, this.playerB, this.cardA, this.cardB);
        // Write code here that turns the phrase above into concrete actions
        assertThat(playerA.getName(), is(equalTo("Bob")));
        assertThat(playerA.getLifePoints(), is(equalTo(3)));
        assertThat(playerB.getLifePoints(), is(equalTo(5)));

        Mediator.getInstance().resolveTurn(this.playerA, this.playerB,this.cardD, this.cardC);
        assertThat(playerA.getLifePoints(), is(equalTo(0)));
        assertThat(playerB.getLifePoints(), is(equalTo(5)));
        assertThat(playerA.isDead(), is(true));
    }
}
