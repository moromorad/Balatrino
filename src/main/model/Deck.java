package model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    // MODIFIES : this
    // EFFECTS : constructs a standard deck of 52 cards (list of Card)
    public Deck() {

    }
    //EFFECTS : returns the deck
    public List<String> getDeck() {
        return new ArrayList<String>();
    }
    
    //MODIFIES : this
    // EFFECTS : adds the card to the deck
    public void addToDeck(String card) {

    }

    //MODIFIES : this
    // EFFECTS : removes the card from the deck
    public void removeFromDeck(String card) {

    }
    // EFFECTS : counts the number of the suit or rank in the deck
    public int numberInDeck(String str) {
        return 0;
    }



}
