package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.*;
import model.*;

import org.junit.jupiter.api.Test;

import ui.Game;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Game g = new Game();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }


    //maxHands = 4;
    //roundsWon = 0;
    //base = 275;
    //factor = 1;
    @Test
    void testWriterBasicGame() {
        try {
            Game g = new Game();
            g.getDeck().setDeck(new ArrayList<Card>());
            JsonWriter writer = new JsonWriter("./data/testJsonWriterEmpty.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterEmpty.json");
            g = reader.read();
            assertEquals(0,g.getDeck().getSize());
            assertEquals(0, g.getJokers().size());
            assertEquals(0,g.getRoundsWon());
            assertEquals(4,g.getMaxHands());
            assertEquals(275,g.getBase());
            assertEquals(1,g.getFactor());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            Game g = new Game();
            List<Joker> j = new ArrayList<>();
            j.add(new OddJoker());
            j.add(new EvenJoker());
            g.setJokers(j);
            JsonWriter writer = new JsonWriter("./data/testJsonWriterGeneral.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterGeneral.json");
            g = reader.read();
            assertEquals(52,g.getDeck().getSize());
            assertEquals(2, g.getJokers().size());
            assertEquals(0,g.getRoundsWon());
            assertEquals(4,g.getMaxHands());
            assertEquals(275,g.getBase());
            assertEquals(1,g.getFactor());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
