package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Standard Test class for SmartPlayer
 * Created by Vincent on 13/10/2016.
 */
public class SmartPlayTest {

    private SmartPlayer ia;
    private PlayCard beastCard;

    @Before
    public void setUp() {

        this.ia = new SmartPlayer("Alice", 20);
        this.beastCard = new Beast("Bear", 3);
    }

    @Test
    public void level0() {
        assertThat(this.ia.addCreature(this.beastCard), is(true));
        this.ia.addCreature(this.beastCard);
        this.ia.addCreature(this.beastCard);
        assertThat(this.ia.getCreatures().contains(this.beastCard), is(true));
        assertThat(this.ia.level0() >= 0, is(true));
        int max = this.ia.getHand().size();
        assertThat(this.ia.level0() <= max, is(true));
    }
}