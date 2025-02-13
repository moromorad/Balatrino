package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundTest {
    Joker odd;
    Joker even;
    Joker joker;
    Deck deck;
    List<Joker> jokers;
    Round round;
    List<Card> highHand;
    List<Card> pairHand;
    List<Card> threeHand;
    List<Card> straightHand;
    List<Card> flushHand;
    List<Card> straightFlushHand;


    @BeforeEach
    void runBefore() {
        odd = new JokerJoker();
        even = new EvenJoker();
        joker = new JokerJoker();
        deck = new Deck(); 
        jokers = Arrays.asList(odd,even,joker);
        round = new Round(deck, jokers);
        highHand = Arrays.asList(new Card("2", "S"), new Card("K", "D"), new Card("4", "H"), new Card("J", "C"), new Card("10", "S"));
        pairHand = Arrays.asList(new Card("A", "S"), new Card("A", "D"), new Card("Q", "H"), new Card("J", "C"), new Card("10", "S"));
        threeHand = Arrays.asList(new Card("A", "S"), new Card("A", "D"), new Card("A", "H"), new Card("J", "C"), new Card("10", "S"));
        straightHand = Arrays.asList(new Card("10", "S"), new Card("9", "D"), new Card("8", "H"), new Card("7", "C"), new Card("6", "S"));
        flushHand = Arrays.asList(new Card("4", "S"), new Card("6", "S"), new Card("Q", "S"), new Card("J", "S"), new Card("10", "S"));
        straightFlushHand = Arrays.asList(new Card("A", "S"), new Card("K", "S"), new Card("Q", "S"), new Card("J", "S"), new Card("10", "S"));
    }
    

    // no tests for check methods since they are checked through the getHandPlayed method
    @Test
    void getHandPlayed() {
        assertEquals("High Card", round.getHandPlayed(highHand));
        assertEquals("Pair", round.getHandPlayed(pairHand));
        assertEquals("Three of a Kind", round.getHandPlayed(threeHand));
        assertEquals("Straight", round.getHandPlayed(straightHand));
        assertEquals("Flush", round.getHandPlayed(flushHand));
        assertEquals("Straight Flush", round.getHandPlayed(straightFlushHand));
    }
}
