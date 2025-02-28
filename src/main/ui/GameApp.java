package ui;

import java.util.Scanner;

public class GameApp {
    Game game;

    public GameApp() {
        game = new Game();
    }

    public void playBalatrino() {
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

    }

    //MODIFIES : game.json
    //EFFECTS : asks to save the game and does it
    private void askToSave() {
        
    }
    

}
