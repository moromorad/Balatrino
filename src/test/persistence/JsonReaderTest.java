package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.*;
import org.junit.jupiter.api.Test;

import model.*;
import ui.Game;


// this class is based on the "JsonSerialitaionDemo" application and edited to fit my project
// GitHub : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGame() {
        JsonReader reader = new JsonReader("./data/testJsonReaderEmpty.json");
        try {
            Game g = reader.read();
            assertEquals(0, g.getDeck().getSize());
            assertEquals(0, g.getJokers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test void testGeneralGame() {
        JsonReader reader = new JsonReader("./data/testJsonReaderGeneral.json");
        try {
            Game g = reader.read();
            assertEquals(3,g.getDeck().getSize());
            assertEquals(3,g.getJokers().size());
            List<Joker> js = g.getJokers();
            assertEquals("Odd Todd", js.get(0).getName());
            assertEquals("Even Steven", js.get(1).getName());
            assertEquals("Joker", js.get(2).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
