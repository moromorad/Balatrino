package model;
import java.util.*;

public class Round {

    // Represents a round of the game, it takes the deck and the jokers the player has and the score that needs to be gotten, and plays the round.
    public Round(Deck deck, List<Joker> jokers) {

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
    // MODIFIES : this
    // EFFECTS : pulls, amount of cards from the deck and adds them to the current hand
    public void pullCards(int amount) {

    }

    // REQUIRES : cardString to be a string of 5 numbers in the range of 1 to 8 inclusive, with no repeat numbers, seprated by commas only
    // MODIFIES : this
    // EFFECTS : pulls cards based on the 5 numbers given from the current hand, returns the this of those pulled cards
    public List<Card> pullFromHand(String cardString) {
        return new ArrayList<Card>();
    }

    // EFFECTS : returns if the round has been won or not
    public boolean isWon() {
        return false;
    }

    // EFFECTS : takes a poker hand and returns the according score and mult
    public List<Integer> getChipsAndMultFromPokerHand(String pokerHand) {
        return new ArrayList<>();
    }

    // EFFECTS : takes chips and mult and applies jokers to them
    public List<Integer> applyJokers(List<Integer> chipsAndMult) {
        return new ArrayList<>();
    }

    // MODIFIES : this
    // EFFECTS : applies the given chips and mult to the score
    public void applyScore(List<Integer> chipsAndMult) {

    }

    public List<Card> getCurrentHand() {
        return null;
    }

    public List<Card> getPlayedHand() {
        return null;
    }

    public Integer getScore() {
        return 0 ;
    }

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
        for (int i = 0 ; i < (sortedIndexList.size() - 1) ; i++ ) {
            if (!((sortedIndexList.get(i+1) - sortedIndexList.get(i)) == 1)) {
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
