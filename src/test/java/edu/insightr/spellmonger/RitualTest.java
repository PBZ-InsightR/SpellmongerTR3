package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Standard test suite for rituals
 * Created by Stan on 02/10/2016.
 */
public class RitualTest {

    private Ritual blessing, curse;

    @Before
    public void setUp() {
        this.blessing = new Ritual("Blessing",-3,true);
        this.curse = new Ritual("Curse",3,false);
    }
    @Test
    public void targetsRitualCaster() throws Exception {
        assertEquals(blessing.targetsRitualCaster(),true);
        assertEquals(curse.targetsRitualCaster(),false);
    }

    @Test
    public void getDamage() throws Exception {
        assertEquals(blessing.getDamage(),-3);
        assertEquals(curse.getDamage(),3);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(blessing.getName(),"Blessing");
        assertEquals(curse.getName(),"Curse");
    }

}