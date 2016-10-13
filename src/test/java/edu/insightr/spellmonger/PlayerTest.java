package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Standard Test class for the player class.
 * Created by Stan on 02/10/2016.
 */
public class PlayerTest {

    // Have to use assertThat instead
    private PlayCard beastcard;
    private Player playerA;

    @Before
    public void setUp() {

        this.playerA = new Player("Alice", 20);
        this.beastcard = new Beast("Bear", 3);
    }

    @Test
    public void getName() throws Exception {
        assertThat(this.playerA.getName(), is(equalTo("Alice")));
    }

    @Test
    public void getLifePoints() throws Exception {
        assertThat(this.playerA.getLifePoints(), is(equalTo(20)));
    }

    @Test
    public void inflictDamages() throws Exception {
        assertThat(this.playerA.getLifePoints(), is(equalTo(20)));
        this.playerA.inflictDamages(5);
        assertThat(this.playerA.getLifePoints(), is(equalTo(15)));
        this.playerA.inflictDamages(-2);
        assertThat(this.playerA.getLifePoints(), is(equalTo(17)));
        this.playerA.inflictDamages(17);
        assertThat(this.playerA.getLifePoints(), is(equalTo(0)));
        assertThat(this.playerA.isDead(), is(true));
    }

    @Test
    public void addAndGetCreatures() throws Exception {
        assertThat(this.playerA.addCreature(this.beastcard), is(true));
        int size = this.playerA.getCreatures().size();
        this.playerA.addCreature(this.beastcard);
        assertThat(this.playerA.getCreatures().size(), is(equalTo(size + 1)));
        assertThat(this.playerA.getCreatures().contains(this.beastcard), is(true));
    }

}