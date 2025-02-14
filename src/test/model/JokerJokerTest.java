package model;

import static org.junit.Assert.assertEquals;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class JokerJokerTest {
    List<Integer> cnm;
    Joker jk;
    List<Card> cards;

    @BeforeEach
    void setup() {
        cnm = Arrays.asList(20,5);
        jk = new JokerJoker();
    }

    @Test
    void testAbility() {
        jk.ability(cnm, cards);
        assertEquals((Integer) 9,cnm.get(1));
        jk.ability(cnm, cards);
        assertEquals((Integer) 13,cnm.get(1));
    }

    @Test
    void testJokerJoker() {
        assertEquals("+4 Mult", jk.getDesc());
    }


    @Test
    void testGetName() {
        assertEquals("Joker", jk.getName());
    }

}
