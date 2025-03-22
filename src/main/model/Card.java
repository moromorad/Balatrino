package model;


import org.json.JSONObject;

// Represents a card in a standard deck of 52 cards with a rank, suit, and number of chips it provides

public class Card {

    private String rank;
    private String suit;

    // REQUIRES: rank to be a number 2-10 or K Q J A and suit to be one of S H C D
    // MODIFIES : this
    // EFFECTS : constructs a card with a rank and suit
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;

    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rank == null) ? 0 : rank.hashCode());
        result = prime * result + ((suit == null) ? 0 : suit.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (rank == null) {
            if (other.rank != null)
                return false;
        } else if (!rank.equals(other.rank))
            return false;
        if (suit == null) {
            if (other.suit != null)
                return false;
        } else if (!suit.equals(other.suit))
            return false;
        return true;
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

    // EFFECTS : returns the fuull name of the card
    public String getFullName() {
        String fullRank = "";
        String fullSuit = "";
        if (rank.equals("K")) {
            fullRank = "King";
        } else if (rank.equals("Q")) {
            fullRank = "Queen";
        } else if (rank.equals("J")) {
            fullRank = "Jack";
        } else if (rank.equals("A")) {
            fullRank = "Ace";
        } else {
            fullRank = rank;
        }
        
        if (suit.equals("D")) {
            fullSuit = "Diamonds";
        } else if (suit.equals("S")) {
            fullSuit = "Spades";
        } else if (suit.equals("H")) {
            fullSuit = "Hearts";
        } else if (suit.equals("C")) {
            fullSuit = "Clubs";
        }

        return (fullRank + " of " + fullSuit);
    }

    // toJson methods are based on the "JsonSerialitaionDemo" application and edited to fit my project
    // GitHub : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECTS: returns the card as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getCard());
        return json;
    }


}
