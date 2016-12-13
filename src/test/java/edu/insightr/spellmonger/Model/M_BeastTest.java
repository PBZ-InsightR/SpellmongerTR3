package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Standard M_Beast test
 * Created by Stan on 02/10/2016.
 */

public class M_BeastTest {

    private M_Player MPlayerA;
    private M_Player MPlayerB;
    private M_Beast bear, wolf, eagle;

    @Before
    public void setUp() throws Exception {
        this.MPlayerA = new M_SmartPlayer("Alice", 20);
        this.MPlayerB = new M_SmartPlayer("Bob", 20);
        this.bear = new M_Beast("Bear", 3, 3);
        this.wolf = new M_Beast("Wolf", 2, 2);
        this.eagle = new M_Beast("Eagle", 1, 1);
        this.eagle.setOwner(MPlayerA);
    }

    @Test
    public void getCardValue() throws Exception {
        assertThat(bear.getCardValue(), is(equalTo(3)));
        assertThat(wolf.getCardValue(), is(equalTo(2)));
        assertThat(eagle.getCardValue(), is(equalTo(1)));
    }

    @Test
    public void getDamage() throws Exception {
        assertThat(bear.getDamage(), is(equalTo(3)));
        assertThat(wolf.getDamage(), is(equalTo(2)));
        assertThat(eagle.getDamage(), is(equalTo(1)));
    }

    @Test
    public void getName() throws Exception {
        assertThat(bear.getName(), is(equalTo("Bear")));
        assertThat(wolf.getName(), is(equalTo("Wolf")));
        assertThat(eagle.getName(), is(equalTo("Eagle")));
    }

    @Test
    public void getOwner() throws Exception {
        assertThat(eagle.getOwner(), is(equalTo(MPlayerA)));
    }

    @Test
    public void setOwner() throws Exception {
        this.eagle.setOwner(MPlayerB);
        assertThat(eagle.getOwner(), is(equalTo(MPlayerB)));
    }

}