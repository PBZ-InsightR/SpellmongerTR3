package edu.insightr.spellmonger;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 * Standard test for AI
 * Created by Vincent on 20/10/2016.
 */
public class SmartPlayerTest {

    private SmartPlayer ia;
    private PlayCard beastCard;
    private PlayCard beastCard2;
    private PlayCard beastCard3;
    private ArrayList<PlayCard> cardToPlay;
    private int round;

    @Before
    public void setUp() {

        this.ia = new SmartPlayer("Alice", 20);
        this.beastCard = new Beast("Bear", 3, 3);
        this.beastCard2 = new Beast("Wolf", 2, 2);
        this.beastCard3 = new Beast("Eagle", 1, 1);
        this.cardToPlay = new ArrayList<>();
        this.ia.addCardToHand(beastCard);
        this.ia.addCardToHand(beastCard2);
        this.ia.addCardToHand(beastCard3);
    }

    @Test
    public void level0() {
        assertThat(this.ia.addCardToHand(this.beastCard), is(true));
        assertThat(this.ia.getCardsInHand().contains(this.beastCard), is(true));
        assertThat(this.ia.level0() >= 0, is(true));
        int max = this.ia.numberOfCards();
        assertThat(this.ia.level0() <= max, is(true));
    }

    @Test
    public void level1() throws Exception {

        int power = 0;
        for(PlayCard e : ia.getCardsInHand()) {
            power += e.getCardValue();
        }
        int avg=0;
        if(!ia.stillHasCards()) {
        avg =  power / ia.numberOfCards();


        if (avg > 2.3) {
            ia.getStrongCardList();

        } else if (avg < 1.8) {
            ia.getAverageCardList();
        } else {
            ia.getBadCardList();
        }
        }

        assertThat(avg <= 3, is(true));
        assertThat(power >= 0, is(true));

    }

    @Test
    public void getDeckPower() throws Exception {

    }

    @Test
    public void chooseCard() throws Exception {
        if(!cardToPlay.isEmpty()){
            int max = this.cardToPlay.size();
            assertThat(this.ia.level1() < max, is(true));
            assertThat(this.ia.level1() >= 0, is(true));
        }
    }

    @Test
    public void getStrongCardList() throws Exception {
        for (int i = 0; i < ia.getCardsInHand().size(); ++i) {
            if (ia.getCardsInHand().get(i).getCardValue() >= 2.0) {
                this.cardToPlay.add(ia.getCardsInHand().get(i));
            }
        }

        assertThat(this.cardToPlay.isEmpty(), is(false));
    }

    @Test
    public void getAverageCardList() throws Exception {

        for (int i = 0; i < ia.getCardsInHand().size(); ++i) {
            if (ia.getCardsInHand().get(i).getCardValue() == 2) {
                this.cardToPlay.add(ia.getCardsInHand().get(i));
            }
        }

        assertThat(this.cardToPlay.isEmpty(), is(false));

    }

    @Test
    public void getBadCardList() throws Exception {
        for (int i = 0; i < ia.getCardsInHand().size(); ++i) {
            if (ia.getCardsInHand().get(i).getCardValue() <= 2) {
                this.cardToPlay.add(ia.getCardsInHand().get(i));
            }
        }

        assertThat(this.cardToPlay.isEmpty(), is(false));
    }

}