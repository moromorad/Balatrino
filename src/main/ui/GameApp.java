package ui;

import java.io.IOException;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;

public class GameApp {
    private Game game;
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    public GameApp() {
        game = new Game();
    }

    public void playBalatrino() {
        askToLoad();
        while (game.playRound()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Round Won!");
            game.timeDelay(300);
            System.out.println("Your upgrade is...");
            game.timeDelay(1000);
            game.upgrade();
            game.timeDelay(5000);
            game.roundsWon += 1;
            game.factor += 0.5;
            askToSave();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("You Lost :(");
        System.out.println("Rounds Won : " + game.getRoundsWon());
    }

    //MODIFIES : game
    //EFFECTS : asks to load the game from file and does it
    private void askToLoad() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("would you like to load from file? (Enter y/n)");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                try {
                    game = jsonReader.read();
                    System.out.println("Loaded game from file " + JSON_STORE);
                } catch (IOException e) {
                    System.out.println("Couldn't load from file " + JSON_STORE);
                    e.printStackTrace();
                }
            }
        } 
    }

    //MODIFIES : game.json
    //EFFECTS : asks to save the game and does it
    private void askToSave() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Would you like to save? (Enter y/n)");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(game);
                    jsonWriter.close();
                    System.out.println("Saved game to " + JSON_STORE);
                } catch (IOException e) {
                    System.out.println("Couldn't save to file " + JSON_STORE);
                    e.printStackTrace();
                }
            }
        } 
        
    }
    

}
