package model;

import java.util.List;

// represents a Joker, every joker has an ability and a descrition describing that ability.
public interface Joker {
    public List<Integer> Ability(int chips, int mult, List<Card> playedHand);
    public String getDesc();

}
