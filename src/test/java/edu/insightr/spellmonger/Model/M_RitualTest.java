package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Standard test suite for rituals
 * Created by Stan on 02/10/2016.
 */
public class M_RitualTest {

    private M_Player MPlayerA;
    private M_Player MPlayerB;
    private M_Ritual blessing, curse, shield;

    @Before
    public void setUp() throws Exception {
        this.MPlayerA = new M_Player("Alice", 20);
        this.MPlayerB = new M_Player("Bob", 20);
        this.blessing = new M_Ritual("Heal", -3, true, true, 3);
        this.curse = new M_Ritual("Poison", 3, false, true, 3);
        this.shield = new M_Ritual("Shield", 0, true, false, 2);
        this.blessing.setOwner(MPlayerA);
    }

    @Test
    public void targetsRitualCaster() throws Exception {
        assertThat(blessing.targetsRitualCaster(), is(true));
        assertThat(curse.targetsRitualCaster(), is(false));
        assertThat(shield.targetsRitualCaster(), is(true));
    }

    @Test
    public void isDirect() throws Exception {
        assertThat(blessing.isDirect(), is(true));
        assertThat(curse.isDirect(), is(true));
    }

    @Test
    public void getCardValue() throws Exception {
        assertThat(blessing.getCardValue(), is(equalTo(3)));
        assertThat(curse.getCardValue(), is(equalTo(3)));
        assertThat(shield.getCardValue(), is(equalTo(2)));
    }

    @Test
    public void getDamage() throws Exception {
        assertThat(blessing.getDamage(), is(equalTo(-3)));
        assertThat(curse.getDamage(), is(equalTo(3)));
        assertThat(shield.getDamage(), is(equalTo(0)));
    }

    @Test
    public void getName() throws Exception {
        assertThat(blessing.getName(), is(equalTo("Heal")));
        assertThat(curse.getName(), is(equalTo("Poison")));
        assertThat(shield.getName(), is(equalTo("Shield")));
    }

    @Test
    public void getOwner() throws Exception {
        assertThat(blessing.getOwner(), is(MPlayerA));
    }

    @Test
    public void setOwner() throws Exception {
        this.blessing.setOwner(MPlayerB);
        assertThat(blessing.getOwner(), is(MPlayerB));
    }

}