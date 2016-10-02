package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Standard Beast test
 * Created by Stan on 02/10/2016.
 */
public class BeastTest {

    private Beast beast;
    @Before
    public void setUp() {
        this.beast = new Beast("Eagle",1);
    }

    @Test
    public void getDamage() throws Exception {
        assertEquals(beast.getDamage(),1);
    }

    @Test
    public void getName() throws Exception {
        assertEquals(beast.getName(),"Eagle");
    }

}