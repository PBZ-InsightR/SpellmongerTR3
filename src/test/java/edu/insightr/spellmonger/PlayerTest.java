package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Standard Test class for the player class.
 * Created by Stan on 02/10/2016.
 */
public class PlayerTest {

    private PlayCard beastcard;
    private Player playerA;

    @Before
    public void setUp() {
        this.playerA = new Player("Alice", 20);
        this.beastcard = new Beast("Bear", 3);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(this.playerA.getName(), "Alice");
    }

    @Test
    public void getLifePoints() throws Exception {
        assertEquals(this.playerA.getLifePoints(), 20);
    }

    @Test
    public void inflictDamages() throws Exception {
        assertEquals(this.playerA.getLifePoints(), 20);
        this.playerA.inflictDamages(5);
        assertEquals(this.playerA.getLifePoints(), 15);
        this.playerA.inflictDamages(-2);
        assertEquals(this.playerA.getLifePoints(), 17);
    }

    @Test
    public void addAndGetCreatures() throws Exception {
        assertEquals(this.playerA.addCreature(this.beastcard), true);
        int size = this.playerA.getCreatures().size();
        this.playerA.addCreature(this.beastcard);
        assertEquals(size + 1, this.playerA.getCreatures().size());
        assertEquals(this.playerA.getCreatures().contains(this.beastcard), true);
    }

}