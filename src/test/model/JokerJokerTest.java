package model;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

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
        jk.Ability(cnm, cards);
        assertEquals((Integer) 9,cnm.get(1));
        jk.Ability(cnm, cards);
        assertEquals((Integer) 13,cnm.get(1));
    }

    @Test
    void testJokerJoker() {
        assertEquals("+4 Mult", jk.getDesc());
    }



}
