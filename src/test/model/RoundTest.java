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
    int score;


    @BeforeEach
    void runBefore() {
        odd = new JokerJoker();
        even = new EvenJoker();
        joker = new JokerJoker();
        deck = new Deck();
        score = 600;
        jokers = Arrays.asList(odd,even,joker);
        round = new Round(deck, jokers,score);
        highHand = Arrays.asList(new Card("2", "S"), new Card("K", "D"), new Card("4", "H"), new Card("J", "C"), new Card("10", "S"));
        pairHand = Arrays.asList(new Card("A", "S"), new Card("A", "D"), new Card("Q", "H"), new Card("J", "C"), new Card("10", "S"));
        threeHand = Arrays.asList(new Card("A", "S"), new Card("A", "D"), new Card("A", "H"), new Card("J", "C"), new Card("10", "S"));
        straightHand = Arrays.asList(new Card("10", "S"), new Card("9", "D"), new Card("8", "H"), new Card("7", "C"), new Card("6", "S"));
        flushHand = Arrays.asList(new Card("4", "S"), new Card("6", "S"), new Card("Q", "S"), new Card("J", "S"), new Card("10", "S"));
        straightFlushHand = Arrays.asList(new Card("A", "S"), new Card("K", "S"), new Card("Q", "S"), new Card("J", "S"), new Card("10", "S"));
    }
    

    // no tests for check methods since they are checked through the getHandPlayed method
    @Test
    void getHandPlayedTest() {
        assertEquals("High Card", round.getHandPlayed(highHand));
        assertEquals("Pair", round.getHandPlayed(pairHand));
        assertEquals("Three of a Kind", round.getHandPlayed(threeHand));
        assertEquals("Straight", round.getHandPlayed(straightHand));
        assertEquals("Flush", round.getHandPlayed(flushHand));
        assertEquals("Straight Flush", round.getHandPlayed(straightFlushHand));
    }

    // tests both pullFromDeck and PullFromHand
    @Test
    void pullFromTest() {
        round.pullFromDeck(8);
        assertEquals(8, round.getCurrentHand().size());
        assertEquals(44, round.getDeck().getCards().size());
        round.pullFromHand("1,2,3,4,5");
        round.pullFromDeck(5);
        assertEquals(8,round.getCurrentHand().size());
        assertEquals(39, round.getDeck().getCards().size());
    }


    @Test
    void getChipsAndMultFromPokerHandTest() {
        assertEquals((Integer) 100,round.getChipsAndMultFromPokerHand("Straight Flush").get(0));
        assertEquals((Integer) 8,round.getChipsAndMultFromPokerHand("Straight Flush").get(1));
        assertEquals(2,round.getChipsAndMultFromPokerHand("Straight Flush").size());

        assertEquals((Integer) 5,round.getChipsAndMultFromPokerHand("High Card").get(0));
        assertEquals((Integer) 1,round.getChipsAndMultFromPokerHand("High Card").get(1));
    }


    @Test
    void applyJokersTest() {
        List<Integer> cnm = new ArrayList<>();
        cnm.add(30);
        cnm.add(2);
        round.applyJokers(cnm,highHand);
        assertEquals((Integer) 30,cnm.get(0));
        assertEquals((Integer) (2 + 4 + 12),cnm.get(1));
        round.applyJokers(cnm, pairHand);
        assertEquals((Integer) 92,cnm.get(0));
        assertEquals((Integer) (18 + 4 + 4),cnm.get(1));
    }
}
