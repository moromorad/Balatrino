package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OddJokerTest {
    List<Integer> cnm;
    Joker jk;
    List<Card> cards = Arrays.asList(new Card("2","H"),new Card("3","H"),new Card("4","S"),new Card("K","D"));

    @BeforeEach
    void setup() {
        cnm = Arrays.asList(10,6);
        jk = new OddJoker();
    }

    @Test
    void testAbility() {
        jk.ability(cnm, cards);
        assertEquals((Integer) 41,cnm.get(0));
        jk.ability(cnm, cards);
        assertEquals((Integer) 72,cnm.get(0));
    }

    @Test
    void testOddJoker() {
        assertEquals("Played cards with odd rank each give +31 Chips", jk.getDesc());
    }

    @Test
    void testGetName() {
        assertEquals("Odd Todd", jk.getName());
    }


}
