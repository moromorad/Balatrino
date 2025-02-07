package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {
    Deck deck;
    List<String> ranks = Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2");
    List<String> suits = Arrays.asList("S","H","C","D");
    List<String> deck2;


    @BeforeEach
    void runBefore() {
        deck = new Deck();
        deck2 = new ArrayList<String>();
        for (String suit : suits) {
            for (String rank : ranks) {
                deck2.add(rank + suit);
            }
        }
    }
    

    @Test
    void getDeckTest() {
        assertEquals(deck.getDeck(),deck2);
        
    }

    @Test
    void addToDeckTest() {
        deck.addToDeck("Q","S");
        assertEquals(5, deck.numberInDeck("Q"));
        assertEquals(14, deck.numberInDeck("S"));
        deck.addToDeck("Q", "H");
        assertEquals(6, deck.numberInDeck("Q"));
        assertEquals(14, deck.numberInDeck("H"));


    }

    @Test
    void removeFromDeckTest() {
        deck.removeFromDeck("Q", "S");
        assertEquals(3, deck.numberInDeck("Q"));
        assertEquals(12, deck.numberInDeck("S"));
    }

    @Test
    void numberInDeck() {
        assertEquals(4, deck.numberInDeck("Q"));
        assertEquals(4, deck.numberInDeck("K"));
        assertEquals(4, deck.numberInDeck("J"));
        assertEquals(4, deck.numberInDeck("A"));
        assertEquals(13, deck.numberInDeck("H"));
        assertEquals(13, deck.numberInDeck("S"));
        deck.removeFromDeck("Q","S");
        assertEquals(3, deck.numberInDeck("Q"));
        assertEquals(12, deck.numberInDeck("S"));
        deck.removeFromDeck("K","S");
        assertEquals(3, deck.numberInDeck("K"));
        assertEquals(11, deck.numberInDeck("S"));


    }


}
