package ui;

import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class GameUI extends JFrame {
    private Game game;
    private DefaultListModel<String> handModel;
    private DefaultListModel<String> jokerModel;
    private JList<String> handList;
    private JList<String> jokerList;
    private JTextField cardInputField;
    private JButton playButton;
    private JButton viewDeckButton;
    private JLabel roundsWonLabel;
    private JLabel handsLeftLabel;
    private JLabel scoreLeftLabel;

    public GameUI() {
        super("Balatrino");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
        setLayout(new BorderLayout());

        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}