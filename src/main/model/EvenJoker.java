package model;

import java.util.List;

// Represents a joker that adds score on even cards
public class EvenJoker implements Joker {
    String desc;
    String name;

    public EvenJoker() {

        desc = "Even ranked cards add +4 Mult";
        name = "Even Steven";
    }

    @Override
    // EFFECT: each even ranked card in the hand adds +4 Mult (2,4,6,8,10)
    // MODIFIES chipsAndMult
    public void ability(List<Integer> chipsAndMult, List<Card> playedHand) {
        int mult = 0;
        for (Card card : playedHand) {
            if (card.getRank().matches("\\d+")) {
                if (Integer.parseInt(card.getRank()) % 2 == 0) {
                    mult = mult + 4;
                }
            }
        }
        chipsAndMult.set(1, chipsAndMult.get(1) + mult);
        EventLog.getInstance().logEvent(new Event("Even Joker Ability used"));
       
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

}
