package edu.insightr.spellmonger.Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Standard Beast test
 * Created by Stan on 02/10/2016.
 */

public class BeastTest {

    private Player playerA;
    private Player playerB;
    private Beast bear, wolf ,eagle;

    @Before
    public void setUp() throws Exception {
        this.playerA=new Player("Alice",20);
        this.playerB=new Player("Bob",20);
        this.bear=new Beast("Bear",3,3);
        this.wolf=new Beast("Wolf",2,2);
        this.eagle = new Beast("Eagle", 1, 1);
        this.eagle.setOwner(playerA);
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
        assertThat(eagle.getOwner(), is(equalTo(playerA)));
    }

    @Test
    public void setOwner() throws Exception {
        this.eagle.setOwner(playerB);
        assertThat(eagle.getOwner(), is(equalTo(playerB)));
    }

    @Test
    public void getImage() throws Exception {

    }
}