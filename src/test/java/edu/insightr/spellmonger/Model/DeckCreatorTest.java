package edu.insightr.spellmonger.Model;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Standard test for the DeckCreator class
 * Created by Yasmeen on 02/11/2016.
 */
public class DeckCreatorTest {


    @Test
    public void fillCardPool() throws Exception {
        List<PlayCard> cards = DeckCreator.getInstance().fillCardPool();
        assertThat(cards.size(), is(equalTo(42)));
    }
}