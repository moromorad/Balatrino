package model;
import java.util.*;

public class Round {

    // Represents a round of the game, it takes the deck and the jokers the player has and the score that needs to be gotten, and plays the round.
    public Round(Deck deck, List<Joker> jokers) {

    }

    // REQUIRES : hand is only 5 cards
    // EFFECTS : returns the poker hand that is played in the hand
    public String getHandPlayed(List<Card> hand) {
        return "";
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
}
