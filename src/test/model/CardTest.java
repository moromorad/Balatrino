package model;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Card card6;
    @BeforeEach
    void runBefore() {
        card1 = new Card("K","D");
        card2 = new Card("Q", "H");
        card3 = new Card("J", "S");
        card4 = new Card("A", "C");
        card5 = new Card("8", "H");
        card6 = new Card("10", "D");
    }

    @Test
    void testGetChips() {
        assertEquals(10,card1.getChips());
        assertEquals(10,card2.getChips());
        assertEquals(10,card3.getChips());
        assertEquals(11,card4.getChips());
        assertEquals(8,card5.getChips());
        assertEquals(10,card6.getChips());
        
    }

    @Test
    void testGetCard() {
        assertEquals("KD",card1.getCard());
        assertEquals("10D",card6.getCard());
        assertEquals("JS",card3.getCard());
    }



}
