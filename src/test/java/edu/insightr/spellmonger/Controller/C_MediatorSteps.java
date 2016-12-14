package edu.insightr.spellmonger.Controller;

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

public class C_MediatorSteps {
    private M_Player MPlayerA, MPlayerB;
    private M_PlayCard cardA, cardB, cardC, cardD;


    @Given("^a player named \"([^\"]*)\" with (\\d+) health points$")
    public void aPlayerNamedWithHealthPoints(String name, int hp) throws Throwable {
        if (MPlayerA == null)
            MPlayerA = new M_SmartPlayer(name, hp);
        if (MPlayerB == null)
            MPlayerB = new M_SmartPlayer(name, hp);
    }


    @Given("^a playCard an \"([^\"]*)\" with an attack of (\\d+)$")
    public void aPlayCardAnWithAnAttackOf(String name, int attack) throws Throwable {
        if(cardA == null)
        {
            cardA = new M_Beast(name, attack, 3);
            cardA.setOwner(MPlayerA);
            assertThat(cardA.getDamage(), is(equalTo(3)));
            assertThat(cardA.getName(), is(equalTo("Eagle")));
        }

        else if(cardB == null)
        {
            cardB = new M_Beast(name, attack, 5);
            cardB.setOwner(MPlayerB);
            assertThat(cardB.getDamage(), is(equalTo(5)));
            assertThat(cardB.getName(), is(equalTo("Bear")));
        }

        else if(cardC == null)
        {
            cardC = new M_Ritual("Poison", 3, false, true, 3);
            cardC.setOwner(MPlayerA);
            assertThat(cardC.getDamage(), is(equalTo(3)));
            assertThat(cardC.getName(), is(equalTo("Poison")));
        }

        else if(cardD == null)
        {
            cardD = new M_Beast(name, attack, 5);
            cardD.setOwner(MPlayerB);
            assertThat(cardD.getDamage(), is(equalTo(1)));
            assertThat(cardD.getName(), is(equalTo("Eagle")));
        }




    }


    @Then("^\"([^\"]*)\" Should be dead$")
    public void shouldBeDead(String arg0) throws Throwable {
        C_Mediator.getInstance().resolveTurn(this.MPlayerA, this.MPlayerB, this.cardA, this.cardB);
        // Write code here that turns the phrase above into concrete actions
        assertThat(MPlayerA.getName(), is(equalTo(arg0)));
        assertThat(MPlayerA.getLifePoints(), is(equalTo(3)));
        assertThat(MPlayerB.getLifePoints(), is(equalTo(5)));

        C_Mediator.getInstance().resolveTurn(this.MPlayerA, this.MPlayerB, this.cardD, this.cardC);
        assertThat(MPlayerA.getLifePoints(), is(equalTo(0)));
        assertThat(MPlayerB.getLifePoints(), is(equalTo(5)));
        assertThat(MPlayerA.isDead(), is(true));
    }
}
