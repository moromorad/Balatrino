package model;

// Represents a card in a standard deck of 52 cards with a rank, suit, and number of chips it provides

public class Card {
    // REQUIRES: rank to be a number 2-10 or K Q J A and suit to be one of S H C D
    // MODIFIES : this
    // EFFECTS : constructs a card with a rank and suit
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;

    }


    // EFFECTS : returns the chips this card provides
    public int getChips() {
        if (getRank().matches("\\d+")) {
            return Integer.parseInt(getRank());

        } else {
            if (getRank().equals("A")) {
                return 11;
            } else {
                return 10;
            }
        }
        
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    // EFFECTS : returns the name of the card (rank and suit)
    public String getCard() {
        return (getRank() + getSuit());
    }



}
