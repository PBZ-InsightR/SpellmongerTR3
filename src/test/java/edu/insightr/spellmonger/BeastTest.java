package edu.insightr.spellmonger;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Standard Beast test
 * Created by Stan on 02/10/2016.
 */
public class BeastTest {
    private Beast beast;

    // Have to use assertThat instead
    @Before
    public void setUp() {
        this.beast = new Beast("Eagle", 1, 1);
    }

    @Test
    public void getDamage() throws Exception {
        assertThat(beast.getDamage(), is(equalTo(1)));
    }

    @Test
    public void getName() throws Exception {
        assertThat(beast.getName(), is(equalTo("Eagle")));
    }
}