package model;

import java.util.List;

public class OddJoker implements Joker {
    String desc;
    public OddJoker() {
        desc = "Played cards with even rank each give +4 Mult";
    }

    // EFFECT: each even ranked card in the hand adds +4 Mult
    @Override
    public void Ability(List<Integer> chipsAndMult, List<Card> playedHand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Ability'");
    }

    @Override
    public String getDesc() {
       return desc;
    }

}
