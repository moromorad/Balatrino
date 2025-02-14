package model;


import java.util.List;

public class JokerJoker implements Joker {
    String desc;
    String name;

    public JokerJoker() {

        desc = "+4 Mult";
        name = "Joker";
    }

    @Override
    // EFFECTS : adds +4 to the given mult 
    // MODIFIES chipsAndMult
    public void ability(List<Integer> chipsAndMult, List<Card> playedHand) {
        chipsAndMult.set(1,chipsAndMult.get(1) + 4);
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

}
