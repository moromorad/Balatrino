package ui;

import model.*;

import static org.junit.Assert.assertEquals;

import java.util.*;


public class Game {
    Deck deck;
    List<Joker> jokers;
    int maxHands;
    int roundsWon;
    int base;
    float factor;

    public Game() {
        deck = new Deck();
        jokers = new ArrayList<Joker>();
        maxHands = 4;
        roundsWon = 0;
        base = 275;
        factor = 1;
    }

    public void playBalatrino() {
        while (playRound()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Round Won!");
            timeDelay(300);
            System.out.println("Your upgrade is...");
            timeDelay(1000);
            upgrade();
            timeDelay(5000);
            roundsWon += 1;
            factor += 0.5;
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("You Lost :(");
        System.out.println("Rounds Won : " + roundsWon);
    }

    public boolean playRound() {
        Scanner scanner = new Scanner(System.in);
        List<String> jokerNames = new ArrayList<String>();
        extractJokerNames(jokerNames);
        Deck roundDeck = initializeRoundDeck();
        System.out.println("Starting new round...");
        Round round = new Round(roundDeck, jokers, Math.round(base * factor));

        for (int i = 0; i < maxHands; i++) {
            startHand(round, jokerNames, i);
            String choice = scanner.nextLine();
            if (choice.equals("v")) {
                choice = viewDeck(roundDeck, scanner);
            }
            List<Card> playedHand = round.pullFromHand(choice);
            int thisScoreLeft = scoringProtocol(round, playedHand);
            System.out.print("Score Left = " + thisScoreLeft);
            timeDelay(4000);
            if (round.isWon()) {
                return true;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            
        }
        return false;
    }

    // MODIFIES : this
    // EFFECTS : performs a random upgrade
    public void upgrade() {
        Random random = new Random();
        int randInt = random.nextInt(6);
        Joker odd = new OddJoker();
        Joker even = new EvenJoker();
        Joker joker = new JokerJoker();
        switch (randInt) {
            case 0:
                addJokerToDeck(odd);
                break;
            case 1:
                addJokerToDeck(even);
                break;
            case 2:
                addJokerToDeck(joker);
                break;
            case 3:
                deck.addToDeck(randRank(), randSuit());
                System.out.print("Added " + deck.getCards().get(deck.getSize() - 1).getFullName() + "!");
                break;
            case 4:
                upgradeRemove();
                break;
            case 5:
                maxHands = maxHands + 1;
                System.out.println("Increased hand size by 1!");
            default:
                // shouldn't happen
        } 
    }

    public Deck getDeck() {
        return deck;
    }





    // HELPERS

    // EFFECTS : adds a joker to the deck
    private void addJokerToDeck(Joker joker) {
        System.out.println("Added " + joker.getName() + "!");
        System.out.println("Description : " + joker.getDesc());
        jokers.add(joker);
    }

    public void upgradeRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Remove a card from the deck!");
        System.out.println("Enter the card you want to remove as 2 characters (e.g. king of diamonds is KD)");
        String chosenString;
        Card card = new Card("K","D");
        boolean removed = false;
        while (!removed) {
            chosenString = scanner.nextLine();
            card = new Card(chosenString.substring(0,1), chosenString.substring(1,2));
            for (Card c : deck.getCards()) {
                if (c.getCard().equals(chosenString)) {
                    deck.getCards().remove(c);
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                System.out.println("Couldn't find card, try again");
            }
        }
        System.out.println("Successfully removed " + card.getFullName());
    }





    // EFFECTS : generates a random rank
    private String randRank() {
        List<String> ranks = Arrays.asList("A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2");
        Random random = new Random();
        int rand = random.nextInt(ranks.size());
        return ranks.get(rand);
    }

    // EFFECTS : generates a random suit
    private String randSuit() {
        List<String> suits = Arrays.asList("S","H","C","D");
        Random random = new Random();
        int rand = random.nextInt(suits.size());
        return suits.get(rand);
    }


    // EFFECTS : extracts the names of the jokers in the game
    private void extractJokerNames(List<String> jokerNames) {
        for (Joker joker : jokers) {
            jokerNames.add(joker.getName());
        }
    }


    // EFFECTS : initialises the deck for the round
    private Deck initializeRoundDeck() {
        Deck roundDeck = new Deck();
        roundDeck.setDeck(new ArrayList<Card>(deck.getCards())); 
        return roundDeck;
    }

    // MODIFIES : round
    // EFFECTS : performs all the printing and calculations that go into scoring
    private int scoringProtocol(Round round, List<Card> playedHand) {
        List<String> playedHandNames = new ArrayList<String>();
        for (Card card : playedHand) {
            playedHandNames.add(card.getCard());
        }
        System.out.println("Played Hand = " + playedHandNames);
        String pokerHand = round.getHandPlayed(playedHand);
        System.out.println(pokerHand + "!");
        List<Integer> cnm = round.getChipsAndMultFromPokerHand(pokerHand);
        System.out.println(cnm);
        round.getHandScore(playedHand, cnm);
        System.out.println(cnm);
        timeDelay(300);
        System.out.println("Joker Time!");
        round.applyJokers(cnm, playedHand);
        System.out.println(cnm);
        int handScore = cnm.get(0) * cnm.get(1);
        System.out.println("This hand scored " + handScore + "!");
        round.applyScore(cnm);
        int thisScoreLeft = round.getScoreLeft();
        if (thisScoreLeft < 0) {
            thisScoreLeft = 0;
        }
        return thisScoreLeft;
    }

    // MODIFIES : round
    // EFFECTS : performs operations that are done at the start of every hand
    private void startHand(Round round, List<String> jokerNames, int i) {
        if (i == 0) {
            round.pullFromDeck(8);
        } else {
            round.pullFromDeck(5);
        }

        System.out.println("Jokers = " + jokerNames);
        List<String> currentHandNames = new ArrayList<String>();
        for (Card card : round.getCurrentHand()) {
            currentHandNames.add(card.getCard());
        }
        System.out.println("Current Hand = " + currentHandNames);
        System.out.println("Score Left = " + round.getScoreLeft());
        System.out.println("Hands Left = " + (maxHands - i));
        System.out.println("");
        System.out.println("Enter 'v' to view your deck");
        System.out.println("OR");
        System.out.println("Enter the hand you will play");
        System.out.println("(5 numbers 1-8 representing card choices from left to right separated by only commas)");
        System.out.println("");
    }

    // EFFECTS : prints the deck and gives the user a prompt to play a hand
    private String viewDeck(Deck roundDeck, Scanner scanner) {
        String choice;
        System.out.println("Here's your deck");
        System.out.println(roundDeck.getCardNames());
        System.out.println("Enter the hand you will play");
        System.out.println("(5 numbers 1-8 representing card choices from left to right separated by only commas)");
        choice = scanner.nextLine();
        return choice;
    }

    

    // EFFECTS : delays for "time" milliseconds
    private void timeDelay(int time) {
        try {
            Thread.sleep(time);  
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
