package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a deck of cards
public class Deck {
    List<Card> deck;
    List<String> ranks = Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2");
    List<String> suits = Arrays.asList("S","H","C","D");

    // MODIFIES : this
    // EFFECTS : constructs a standard deck of 52 cards (list of Card)
    public Deck() {
        this.deck = new ArrayList<Card>();
        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(rank, suit));
            }
        }
    }

    //EFFECTS : returns the deck

    public List<String> getCardNames() {

        List<String> cardNames = new ArrayList<String>();
        for (Card card : deck) {
            cardNames.add(card.getRank() + card.getSuit());
        }
        return cardNames;
    }
    
    //MODIFIES : this
    // EFFECTS : adds the card to the deck
    public void addToDeck(String rank, String suit) {
        Card card = new Card(rank, suit);
        deck.add(card);
        EventLog.getInstance().logEvent(new Event("Added " + card.getFullName() + " to the deck"));
    }

    //MODIFIES : this
    // EFFECTS : removes the card from the deck
    public void removeFromDeck(String rank, String suit) {
        Card card = new Card(rank, suit);
        deck.remove(card);
        EventLog.getInstance().logEvent(new Event("Removed " + card.getFullName() + " from the deck"));

    }
    // REQUIRES : str is a rank or suit (e.g. K for king D for diamonds)
    // EFFECTS : counts the number of the suit or rank in the deck

    public int numberInDeck(String str) {

        int count = 0;
        if (str.equals("S") | str.equals("H") | str.equals("C") | str.equals("D")) {
            for (Card card : deck) {
                if (card.getSuit().equals(str)) {
                    count = count + 1;
                }
            }
        } else {
            for (Card card : deck) {
                if (card.getRank().equals(str)) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    public int getSize() {
        return deck.size();
    }

    public List<Card> getCards() {
        return deck;
    }

    public void setDeck(List<Card> cardList) {
        deck = cardList;
    }

    // MODIFIES : this
    // EFFECTS : removes the card from the deck
    public void removeCard(Card card) {
        deck.remove(card);
        EventLog.getInstance().logEvent(new Event("Removed " + card.getFullName() + " from the deck"));
    }
}
