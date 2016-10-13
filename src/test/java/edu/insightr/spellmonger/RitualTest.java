package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Standard test suite for rituals
 * Created by Stan on 02/10/2016.
 */
public class RitualTest {

    // Have to use assertThat instead
    private Ritual blessing, curse, shield;

    @Before
    public void setUp() {
        this.blessing = new Ritual("Heal",-3,true,true);
        this.curse = new Ritual("Poison",3,false,true);
        this.shield = new Ritual("Shield", 0, true, false);
    }
    @Test
    public void targetsRitualCaster() throws Exception {
        assertEquals(blessing.targetsRitualCaster(),true);
        assertEquals(curse.targetsRitualCaster(),false);
        assertEquals(shield.targetsRitualCaster(), true);
    }

    @Test
    public void getDamage() throws Exception {
        assertEquals(blessing.getDamage(),-3);
        assertEquals(curse.getDamage(),3);
        assertEquals(shield.getDamage(),0);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(blessing.getName(),"Heal");
        assertEquals(curse.getName(),"Poison");
        assertEquals(shield.getName(),"Shield");
    }

}