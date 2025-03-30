package model;

import static org.junit.Assert.assertEquals;

import java.util.*;

// Represents a round of the game, it takes the deck, jokers, the score, and plays the round.

public class Round {
    private Deck deck;
    private List<Joker> jokers;
    private int scoreLeft;
    private List<Card> currentHand;

    // MODIFIES : this
    // EFFECTS : constructs the fields to play a round
    public Round(Deck deck, List<Joker> jokers, int score) {
        this.deck = deck;
        this.jokers = jokers;
        this.scoreLeft = score;
        currentHand = new ArrayList<Card>();
    }

    // REQUIRES : hand is only 5 cards
    // EFFECTS : returns the poker hand that is played in the hand
    public String getHandPlayed(List<Card> hand) {
        if (checkFlush(hand) & checkStraight(hand)) {
            return "Straight Flush";
        } else if (checkFlush(hand)) {
            return "Flush";
        } else if (checkStraight(hand)) {
            return "Straight";
        } else if (checkThreeOfAKind(hand)) {
            return "Three of a Kind";
        } else if (checkPair(hand)) {
            return "Pair";
        } else {
            return "High Card";
        }
    }

    // REQUIRES : amount is either 5 or 8
    // MODIFIES : this, EventLog
    // EFFECTS : pulls, amount of cards from the deck and adds them to the current hand
    public void pullFromDeck(int amount) {
        List<Card> randomList = new ArrayList<>();
        Random random = new Random();
        int nextIndex;
        for (int i = 0; i < amount; i++) {
            nextIndex = random.nextInt(deck.getCards().size());        
            randomList.add(deck.getCards().get(nextIndex));
            deck.getCards().remove(deck.getCards().get(nextIndex));
        }
        currentHand.addAll(randomList);
        EventLog.getInstance().logEvent(new Event("Pulled " + amount + " cards from the deck"));
    }

    // REQUIRES:cardString to be a string of 5 numbers in the range of 1-8 inclusive, no repeats,seprated by commas only
    // MODIFIES : this, EventLog
    // EFFECTS : pulls the 5 cards from the current hand, returns the list of those cards and removes them 
    public List<Card> pullFromHand(String cardString) {
        List<Card> pulledCards = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        
        for (String num : cardString.split(",")) {
            int index = Integer.parseInt(num.trim()) - 1; 
            indexes.add(index);
        }

        for (Integer num : indexes) {
            pulledCards.add(currentHand.get(num));
        }

        for (Card card : pulledCards) {
            currentHand.remove(card);
        }

        assertEquals(3,currentHand.size());
        assertEquals(5,pulledCards.size());
        EventLog.getInstance().logEvent(new Event("Pulled 5 cards from the current hand"));
        return pulledCards;
    }

    // EFFECTS : returns if the round has been won or not
    public boolean isWon() {
        if (scoreLeft <= 0) {
            return true;
        } 
        return false;
    }

    // REQUIRES : pokerHand is an actual poker hand from:
    //     (Straight Flush, Flush, Straight, Three of a Kind, Pair, and High Card)
    // EFFECTS : takes a poker hand and returns the according score and mult
    public List<Integer> getChipsAndMultFromPokerHand(String pokerHand) {
        List<Integer> chipsNMult = new ArrayList<>();
        if (pokerHand.equals("Straight Flush")) {
            chipsNMult.add(100);
            chipsNMult.add(8);

        } else if (pokerHand.equals("Flush")) {
            chipsNMult.add(35);
            chipsNMult.add(4);
            
        } else if (pokerHand.equals("Straight")) {
            chipsNMult.add(30);
            chipsNMult.add(4);
        } else if (pokerHand.equals("Three of a Kind")) {
            chipsNMult.add(30);
            chipsNMult.add(3);
        } else if (pokerHand.equals("Pair")) {
            chipsNMult.add(10);
            chipsNMult.add(2);
        } else if (pokerHand.equals("High Card")) {
            chipsNMult.add(5);
            chipsNMult.add(1);
        }
        return chipsNMult;
    }

    // REQUIRES : chipsAndMult has 2 elements, both positive and second one is larger than 0 and hand is 5 elements
    //MODIFIES : chipsAndMult
    // EFFECTS : takes chips and mult and applies jokers to them 
    public void applyJokers(List<Integer> chipsAndMult, List<Card> hand) {
        for (Joker joker : jokers) {
            joker.ability(chipsAndMult, hand);
        }
    }

    // REQUIRES : chipsAndMult has 2 elements, both positive and second one is larger than 0 and hand is 5 elements,
    //            element 1 * element 2 <= scoreLeft
    // MODIFIES : this
    // EFFECTS : applies the given chips and mult to the score
    public void applyScore(List<Integer> chipsAndMult) {
        scoreLeft = scoreLeft - (chipsAndMult.get(0) * chipsAndMult.get(1));
    }


    // REQUIRES : chipsAndMult has 2 elements, both positive and hand is 5 elements
    // MODIFIES : cnm
    //EFFECTS : adds chips from hand to cnm
    public void getHandScore(List<Card> hand, List<Integer> cnm) {
        int total = 0;
        for (Card card : hand) {
            total = total + card.getChips();
        }
        int chips = cnm.get(0);
        cnm.set(0,chips + total);
    }

    public List<Card> getCurrentHand() {
        return currentHand;
    }


    public Integer getScoreLeft() {
        return scoreLeft;
    }


    public Deck getDeck() {
        return deck;
    }


    // Helpers

    // REQUIRES : hand is 5 elements
    // EFFECTS : checks if hand is a pair
    private boolean checkPair(List<Card> hand) {
        int count;
        for (int i = 0; i < hand.size(); i++) {
            count = 1;
            for (int j = 0; j < hand.size(); j++) {
                if (i != j && hand.get(i).getRank().equals(hand.get(j).getRank())) {
                    count++;
                }
            }
            if (count == 2) {
                return true;
            }
        }
        return false;
    }
    

    // REQUIRES : hand is 5 elements
    // EFFECTS : checks if hand is a three of a kind
    private boolean checkThreeOfAKind(List<Card> hand) {
        int count;
        for (int i = 0; i < hand.size(); i++) {
            count = 1;
            for (int j = 0; j < hand.size(); j++) {
                if (i != j && hand.get(i).getRank().equals(hand.get(j).getRank())) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES : hand is 5 elements
    // EFFECTS : (helper for checkStraight) takes a hand and returns a list of each ranks index
    private List<Integer> getStraightIndex(List<Card> hand) {
        List<Integer> indexList = new ArrayList<>();
        for (Card card : hand) {
            if (card.getRank().equals("A")) {
                indexList.add(14);
            } else if (card.getRank().equals("K")) {
                indexList.add(13);
            } else if (card.getRank().equals("Q")) {
                indexList.add(12);
            } else if (card.getRank().equals("J")) {
                indexList.add(11);
            } else {
                indexList.add(Integer.parseInt(card.getRank()));
            }
            
        }
        return indexList;
    }

    // REQUIRES : hand is 5 elements
    // EFFECTS : checks if hand is a straight

    private boolean checkStraight(List<Card> hand) {
        List<Integer> sortedIndexList = getStraightIndex(hand);
        Collections.sort(sortedIndexList);
        for (int i = 0; i < (sortedIndexList.size() - 1); i++) {
            if (!((sortedIndexList.get(i + 1) - sortedIndexList.get(i)) == 1)) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES : hand is 5 elements
    // EFFECTS : checks if hand is a flush
    private boolean checkFlush(List<Card> hand) {
        List<String> suits = new ArrayList<>();
        for (Card card : hand) {
            suits.add(card.getSuit());
        }

        String firstSuit = suits.get(0);
        for (String suit : suits) {
            if (!suit.equals(firstSuit)) {
                return false;
            }
        }
        return true;
    }



}
