package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Standard Test class for SmartPlayer
 * Created by Vincent on 13/10/2016.
 */
public class SmartPlayTest {

    private SmartPlayer ia;
    private PlayCard beastcard;

    @Before
    public void setUp() {

        this.ia = new SmartPlayer("Alice", 20);
        this.beastcard = new Beast("Bear", 3);
    }

    @Test
    public void level0() {
        assertEquals(this.ia.addCreature(this.beastcard), true);
        this.ia.addCreature(this.beastcard);
        this.ia.addCreature(this.beastcard);
        assertEquals(this.ia.getCreatures().contains(this.beastcard), true);
        assertEquals(this.ia.level0()>=0, true);
        int max = this.ia.getHand().size();
        assertEquals(this.ia.level0() <= max, true);
    }
}