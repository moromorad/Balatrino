package model;

import java.util.List;

// Represents a joker that adds score on odd cards
public class OddJoker implements Joker {
    String desc;
    String name;

    public OddJoker() {
        
        desc = "Played cards with odd rank each give +31 Chips";
        name = "Odd Todd";
    }

    // EFFECT: each odd ranked card in the hand adds +31 Chips (A,3,5,7,9)
    // MODIFIES chipsAndMult, EventLog
    @Override
    public void ability(List<Integer> chipsAndMult, List<Card> playedHand) {
        int chips = 0;
        for (Card card : playedHand) {
            if (card.getRank().matches("\\d+")) {
                if (!(Integer.parseInt(card.getRank()) % 2 == 0)) {
                    chips = chips + 31;
                }
            } else if (card.getRank().equals("A")) {
                chips = chips + 31;
            }
        }
        chipsAndMult.set(0, chipsAndMult.get(0) + chips);
        EventLog.getInstance().logEvent(new Event("Odd Joker Ability used"));
       
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

}
