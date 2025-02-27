package persistence;

import model.*;
import ui.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// this class is based on the "JsonSerialitaionDemo" application and edited to fit my project
// GitHub : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads the game from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        return null;

    }

    //MODIFIES: g
    //EFFECTS: parses the deck from JSON object and adds them to the game
    private void addDeck(Game g, JSONObject jsonObj) {

    }

    //MODIFIES: g
    //EFFECTS: parses the jokers from JSON object and adds them to the game
    private void addJokers(Game g, JSONObject jsonObj) {
        
    }

    //MODIFIES: g
    //EFFECTS: parses the maxHands,roundsWon,base and factor  from JSON object and adds them to the game
    private void addGameElements(Game g, JSONObject jsonObj) {
        
    }
    
}
