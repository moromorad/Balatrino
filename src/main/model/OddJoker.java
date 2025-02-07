package model;

import java.util.List;

public class OddJoker implements Joker {
    String desc;
    public OddJoker() {
        desc = "Played cards with even rank each give +4 Mult";
    }

    // EFFECT: each even ranked card in the hand adds +4 Mult (2,4,6,8,10)
    @Override
    public void Ability(List<Integer> chipsAndMult, List<Card> playedHand) {
        int mult = 0;
        for (Card card : playedHand) {
            if (card.getRank().matches("\\d+")) {
                if (Integer.parseInt(card.getRank()) % 2 == 0) {
                    mult = mult + 4;
                }
            }
        }
        chipsAndMult.set(1, chipsAndMult.get(1) + mult);
       
    }

    @Override
    public String getDesc() {
       return desc;
    }

}
