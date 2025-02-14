package model;

import java.util.List;

// represents a Joker, every joker has an ability and a descrition describing that ability.
public interface Joker {
    public void ability(List<Integer> chipsAndMult, List<Card> playedHand);

    public String getDesc();
    
    public String getName();

}
