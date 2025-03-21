package ui;

import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;



import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

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

    // constructs the GUI
    public GameUI() {
        super("Balatrino");
        game = new Game();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        setVisible(true);
        setResizable(true);
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
                JDialog deckDialog = new JDialog(GameUI.this,"deck");
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
    // EFFECTS : plays a round
    public boolean playRound() {
        List<String> jokerNames = new ArrayList<String>();
        game.extractJokerNames(jokerNames);
        roundDeck = game.initializeRoundDeck();
        Round round = new Round(roundDeck, game.jokers, Math.round(game.base * game.factor));

        for (int i = 0; i < game.maxHands; i++) {
            startHand(round, jokerNames, i);
            deckLabel.setText(roundDeck.getCardNames().toString());
            game.timeDelay(7000);
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



        
}