package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;



import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// plays the game with a swing GUI
public class GameUI extends JFrame {
    private Game game;
    private Deck roundDeck;
    private JLabel roundsWonLabel;
    private JLabel handsLeftLabel;
    private JLabel scoreLeftLabel;
    private JPanel statsPanel;
    private JPanel centerPanel;
    private JPanel jokerPanel;
    private JLabel jokerNamesLabel;
    private JPanel handPanel;
    private JLabel handLabel;
    private JButton viewButton;
    private JLabel outputLabel;
    private JLabel deckLabel;
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    // constructs the GUI
    public GameUI() {
        super("Balatrino");
        game = new Game();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new GameListener());
        setPreferredSize(new Dimension(800, 800));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new BorderLayout());

        initStatsPanel();
        initCenterPanel();
        initBottomPanel();
        outputLabel = new JLabel();
        add(outputLabel,BorderLayout.EAST);
        
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        mainMenu();
    }

    // MODIFIES : this
    // EFFECTS : initiates the center panel of the JFrame
    private void initCenterPanel() {
        // center panel
        centerPanel = new JPanel(new GridLayout(2,1));
        add(centerPanel,BorderLayout.CENTER);

        jokerPanel = new JPanel(new FlowLayout());
        jokerNamesLabel = new JLabel();
        jokerPanel.add(jokerNamesLabel);

        handPanel = new JPanel(new FlowLayout());
        handLabel = new JLabel();
        handPanel.add(handLabel);

        centerPanel.add(jokerPanel);
        centerPanel.add(handPanel);
    }

    // MODIFIES : this
    // EFFECTS : initiates the north/stats panel of the JFrame
    private void initStatsPanel() {
        // top panel
        statsPanel = new JPanel(new FlowLayout());
        roundsWonLabel = new JLabel("Rounds Won: 0");
        handsLeftLabel = new JLabel();
        scoreLeftLabel = new JLabel();
        statsPanel.add(roundsWonLabel);
        statsPanel.add(handsLeftLabel);
        statsPanel.add(scoreLeftLabel);
        add(statsPanel,BorderLayout.NORTH);
    }

    // MODIFIES : this
    // EFFECTS : initiates the bottom panel of the JFrame
    private void initBottomPanel() {
        viewButton = new JButton("View Deck");
        deckLabel = new JLabel();
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog deckDialog = new JDialog(GameUI.this,"Deck");
                deckDialog.setSize(400,400);
                deckDialog.setLocationRelativeTo(GameUI.this);
                deckLabel.setText(roundDeck.getCardNames().toString());
                deckDialog.add(deckLabel);
                deckDialog.setSize(1200, 50);
                deckDialog.setVisible(true);
                
            }
        }
        );
        add(viewButton,BorderLayout.SOUTH);
    }


    // MODIFIES : this
    // EFFECTS : plays the game
    public void playGame() {
        askToLoad();
        roundsWonLabel.setText("Rounds Won: " + game.roundsWon);
        while (playRound()) {
            outputLabel.setText("Round Won!");
            game.timeDelay(700);
            outputLabel.setText("Your upgrade is...");
            game.timeDelay(1000);
            upgrade();
            game.timeDelay(5000);
            game.roundsWon += 1;
            roundsWonLabel.setText("Rounds Won: " + game.roundsWon);
            game.factor += 0.75;
            askToSave();
            
        }
        gameOver();
    }

    // MODIFIES : this
    // EFFECTS : plays a round
    public boolean playRound() {
        List<String> jokerNames = new ArrayList<String>();
        game.extractJokerNames(jokerNames);
        roundDeck = game.initializeRoundDeck();
        Round round = new Round(roundDeck, game.jokers, Math.round(game.base * game.factor));

        for (int i = 0; i < game.maxHands; i++) {
            startHand(round, jokerNames, i);
            deckLabel.setText(roundDeck.getCardNames().toString());
            game.timeDelay(5000);
            String choice = JOptionPane.showInputDialog(GameUI.this, "Enter the hand you will play");
            List<Card> playedHand = round.pullFromHand(choice);
            int thisScoreLeft = scoringProtocol(round, playedHand);
            scoreLeftLabel.setText("Score Left: " + thisScoreLeft);
            game.timeDelay(4000);
            if (round.isWon()) {
                return true;
            }
        }
        return false;
        
    }

    // MODIFIES : game, roundDeck
    // EFFECTS : performs operations that are done at the start of every hand
    private void startHand(Round round, List<String> jokerNames, int i) {
        if (i == 0) {
            round.pullFromDeck(8);
        } else {
            round.pullFromDeck(5);
        }
        jokerNamesLabel.setText("Jokers: " + jokerNames.toString());
        List<String> currentHandNames = new ArrayList<String>();
        for (Card card : round.getCurrentHand()) {
            currentHandNames.add(card.getCard());
        }
        handLabel.setText("Current Hand: " + currentHandNames.toString());
        scoreLeftLabel.setText("Score Left: " + round.getScoreLeft());
        handsLeftLabel.setText("Hands Left: " + (game.maxHands - i));
        outputLabel.setText("");
    }

    //MODIFIES : this
    //EFFECTS : performs operations that go into scoring and displays on the GUI
    public int scoringProtocol(Round round, List<Card> playedHand) {
        List<String> playedHandNames = new ArrayList<String>();
        extractHandNames(playedHand, playedHandNames);
        outputLabel.setText(playedHandNames.toString());
        game.timeDelay(2000);
        String pokerHand = round.getHandPlayed(playedHand);
        List<Integer> cnm = round.getChipsAndMultFromPokerHand(pokerHand);
        outputLabel.setText(pokerHand + "! " + cnm.toString());
        game.timeDelay(2000);
        round.getHandScore(playedHand, cnm);
        outputLabel.setText("Applied Cards " + cnm.toString());
        game.timeDelay(2000);
        round.applyJokers(cnm, playedHand);
        outputLabel.setText("Applied Jokers " + cnm.toString());
        game.timeDelay(2000);
        int handScore = cnm.get(0) * cnm.get(1);
        outputLabel.setText("This hand scored " + handScore + "!");
        round.applyScore(cnm);
        int thisScoreLeft = round.getScoreLeft();
        if (thisScoreLeft < 0) {
            thisScoreLeft = 0;
        }
        return thisScoreLeft;

    }

    // REQUIRES : playedHandNames is empty
    //MODIFIES : playedHandNames
    //EFFECTS : extracts the names (string) of each card in playedHand and adds them to playedHandNames
    private void extractHandNames(List<Card> playedHand, List<String> playedHandNames) {
        for (Card card : playedHand) {
            playedHandNames.add(card.getCard());
        }
    }

    // MODIFIES : this
    // EFFECTS : performs a random upgrade
    public void upgrade() {
        int randInt = new Random().nextInt(6); 
        switch (randInt) {
            case 0:
                addJokerToDeck(new OddJoker());
                break;
            case 1:
                addJokerToDeck(new EvenJoker());
                break;
            case 2:
                addJokerToDeck(new JokerJoker());
                break;
            case 3:
                game.deck.addToDeck(game.randRank(), game.randSuit());
                outputLabel.setText("Added " + game.deck.getCards().get(game.deck.getSize() - 1).getFullName() + "!");
                break;
            case 4:
                upgradeRemove();
                break;
            case 5:
                game.maxHands = game.maxHands + 1;
                outputLabel.setText("Increased hand size by 1!");
            default:
        } 
    }

    //MODIFIES : game
    // EFFECTS : adds a joker to the deck of game
    public void addJokerToDeck(Joker joker) {
        outputLabel.setText("Added " + joker.getName() + "!");
        game.timeDelay(700);
        outputLabel.setText("Description : " + joker.getDesc());
        game.jokers.add(joker);
    }


    // MODIFIES : game
    // EFFECTS : asks the user what card they want removed
    public void upgradeRemove() {
        outputLabel.setText("Removing a card from the deck!");
        game.timeDelay(700);
        String cardString = JOptionPane.showInputDialog(GameUI.this, 
                "Enter the card you want removed (e.g KD for king of diamonds)");
        String rank;
        String suit;
        if (cardString.contains("10")) {
            rank = cardString.substring(0,2);
            suit = cardString.substring(2,3);
        } else {
            rank = cardString.substring(0,1);
            suit = cardString.substring(1,2);
        }
        
        Card card = new Card(rank,suit);
        game.deck.removeCard(card);
        outputLabel.setText("Successfully removed " + card.getFullName());
        game.timeDelay(500);
    }


    // MODIFIES : game
    // EFFECTS : asks the user if they want to load and does it
    public void askToLoad() {
        int result = JOptionPane.showConfirmDialog(
                null,"Do you want to load from file?","Load",                  
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);        
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                game = jsonReader.read();
                outputLabel.setText("Loaded game from file " + JSON_STORE);
                game.timeDelay(200);
            } catch (IOException e) {
                System.out.println("Couldn't load from file " + JSON_STORE);
                e.printStackTrace();
            }
        }
    }

    //MODIFIES : game.json
    //EFFECTS : asks the user if they want to save the game and does it
    public void askToSave() {
        int result = JOptionPane.showConfirmDialog(null,"Do you want to save your progress?","Save",                  
                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(game);
                jsonWriter.close();
                outputLabel.setText("Saved game to " + JSON_STORE);
                game.timeDelay(200);
            } catch (IOException e) {
                System.out.println("Couldn't save to file " + JSON_STORE);
                e.printStackTrace();
            }
        }  
    }

    // EFFECTS : displays the game over screen
    public void gameOver() {
        JFrame frame = new JFrame("Game Over");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new GameListener());
        frame.setSize(800, 800);

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lostLabel = new JLabel("You Lost :(", SwingConstants.CENTER);
        lostLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lostLabel.setFont(new Font("Arial", Font.BOLD, 36));

        JLabel roundsLabel = new JLabel("Rounds Won = " + game.roundsWon, SwingConstants.CENTER);
        roundsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundsLabel.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(Box.createVerticalGlue());
        panel.add(lostLabel);
        panel.add(roundsLabel);
        panel.add(Box.createVerticalGlue());
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // EFFECTS : displays the main menu
    public void mainMenu() {
        JFrame menuFrame = new JFrame("Game");
        menuFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        menuFrame.addWindowListener(new GameListener());
        menuFrame.setSize(800, 800);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new OverlayLayout(menuPanel));

        ImageIcon bg = new ImageIcon("data/mainMenu.jpg");
        JLabel backgroundLabel = new JLabel(bg);
        backgroundLabel.setAlignmentX(0.5f);
        backgroundLabel.setAlignmentY(0.5f);

        JButton startButton = initStartButton(menuFrame);
        startButton.setAlignmentX(0.5f);
        startButton.setAlignmentY(0.1f);

        menuPanel.add(startButton);
        menuPanel.add(backgroundLabel);
        menuFrame.setContentPane(menuPanel);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);

    }

    // EFFECTS : initialises the start game button
    private JButton initStartButton(JFrame menuFrame) {
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                setVisible(true);
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        playGame();
                        return null;
                    }
                }.execute();
            }
        });
        return startButton;
    }


        
}