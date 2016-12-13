package edu.insightr.spellmonger.Model;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * Define the Cucumber tests for the M_PlayerSteps class.
 * Created by Stan on 30/11/2016.
 */
public class M_PlayerSteps {
    private M_Player MPlayerA;
    private int health;
    private int currentCardNumber;
    private String name;

    @Given("^I have a Player$")
    public void iHaveAPlayer() throws Throwable {
        MPlayerA = new M_Player("Bob", 20);
    }

    @When("^I check his health$")
    public void iCheckHisHealth() throws Throwable {
        this.health = MPlayerA.getLifePoints();
    }

    @Then("^I should have (\\d+) HP$")
    public void iShouldHaveHP(int currentHealth) throws Throwable {
        Assert.assertThat(currentHealth, is(equalTo(this.health)));
    }

    @When("^I check their names$")
    public void iCheckTheirNames() throws Throwable {
        this.name = MPlayerA.getName();
    }

    @Then("^I should have \"([^\"]*)\"$")
    public void iShouldHave(String currentName) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertThat(currentName, is(equalTo(this.name)));
    }

    @When("^I check his cardNumber$")
    public void iCheckHisCardNumber() throws Throwable {
        currentCardNumber = MPlayerA.getCardsStack().size();
    }

    @Then("^I should have (\\d+)$")
    public void iShouldHave(int cardNumber) throws Throwable {
        Assert.assertThat(cardNumber, is(equalTo(this.currentCardNumber)));
    }
}
