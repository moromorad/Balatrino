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
        jk.Ability(cnm, cards);
        assertEquals((Integer) 14,cnm.get(1));
        jk.Ability(cnm, cards);
        assertEquals((Integer) 22,cnm.get(1));
    }

    @Test
    void testJokerJoker() {
        assertEquals("Played cards with even rank each give +4 Mult", jk.getDesc());
    }


}
