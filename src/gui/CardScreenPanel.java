/**
 * CSCI 2120 Fall 2014
 * Risk class CardScreenPanel
 *
 * @author Shane McCulley
 * @date Dec 2, 2014
 **/
package gui;

import classes.Card;
import classes.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


@SuppressWarnings("serial")
public class CardScreenPanel extends JPanel {
    private static final int PREFERRED_WIDTH = 843;
    private static final int PREFERRED_HEIGHT = 227;
    private static final int DEFAULT_TROOP_VALUE = 4;
    private static final int MAX_CARDS = 5;

    private CardScreenHandler handler;

    /* menu panel and label */
    private JLabel tradeInLabel;
    private JPanel southPanel;
    private JPanel menuButtonPanel;

    /* panel for cards, scroll panel for view */
    private JPanel cardPanel;
    private JScrollPane scrollPane;
    private JLabel chooseSet;
    private JButton exitButton;
    private JButton acceptButton;

    /* current value of cards */
    private int tradeInValue;
    private Player currentPlayer;


    public CardScreenPanel(CardScreenHandler handler) {
        this.handler = handler;
        setLayout(new BorderLayout(10, 10));

        chooseSet = new JLabel("Choose a set of 3 cards below.  "
                + "Match 3 of a kind or 1 of each infantry, cavalry, artillery");
        this.add(chooseSet, BorderLayout.NORTH);

        addButtons();

		/* menu panel and label */
        southPanel = new JPanel(new BorderLayout());
        southPanel.add(menuButtonPanel, BorderLayout.EAST);
        southPanel.add(tradeInLabel, BorderLayout.WEST);
		
		/* panel for cards, scroll panel for view */
        cardPanel = new JPanel();
        scrollPane = createScrollPane();
        scrollPane.setViewportView(cardPanel);
        this.add(scrollPane, BorderLayout.CENTER);
		
		/* To prevent a very large screen if player has 6+ cards */
        scrollPane.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        this.add(southPanel, BorderLayout.SOUTH);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        handler.addView(this);

    }

    private void addButtons() {
		/* holds the bottom buttons */
        menuButtonPanel = new JPanel();
        menuButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		/* label for troop value */
        tradeInValue = DEFAULT_TROOP_VALUE;
        tradeInLabel = new JLabel("Number of troops from trade in:  "
                + tradeInValue);
        tradeInLabel.setHorizontalAlignment(SwingConstants.LEFT);
				
		/* accept button */
        acceptButton = new JButton("Accept");
        acceptButton.setEnabled(false);
        acceptButton.addActionListener(handler);
        menuButtonPanel.add(acceptButton);
		
		/* exit button */
        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(acceptButton.getPreferredSize());
        exitButton.addActionListener(handler);
        menuButtonPanel.add(exitButton);

    }

    private void nextTradeInValue() {
        tradeInValue = handler.getNextTroopCount(tradeInValue);
        tradeInLabel.setText("Number of troops from trade in:  "
                + tradeInValue);
    }

    private JScrollPane createScrollPane() {
        JScrollPane result = new JScrollPane();
        result.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        result.getHorizontalScrollBar().setUnitIncrement(20);
        result.getHorizontalScrollBar().setBlockIncrement(150);
        result.setVisible(true);

        return result;

    }

    /**
     * Sets the cards of the current player.
     *
     * @param currentPlayer the current player.
     */
    public void setPlayer(Player currentPlayer, boolean mustTradeCards) {
        this.currentPlayer = currentPlayer;
        removeAllCards();

        for (Card c : currentPlayer.getHand()) {
            addCard(c);
        }
        exitButton.setEnabled(!mustTradeCards);
    }

    public void removeAllCards() {
        cardPanel.removeAll();
        cardPanel.revalidate();
    }

    public void addCard(Card c) {
        CardComponent newCard = new CardComponent(c);
        newCard.addMouseListener(handler);
        cardPanel.add(newCard);
    }

    //TODO integrate these card functions  with game engine
    public int getNumSelected() {
        int result = 0;

        for (Component c : cardPanel.getComponents()) {
            if (((CardComponent) c).isSelected())
                result++;
        }

        return result;
    }

    public int[] getSelectedIndex() {
        int[] result = new int[3];
        int j = 0;

        for (int i = 0; i < cardPanel.getComponentCount(); i++) {
            if (((CardComponent) cardPanel.getComponent(i)).isSelected()) {
                result[j] = i;
                j++;
            }
        }

        if (j != 3 || j > 3) {
            System.out.println("J error, j = " + j);
            throw new RuntimeException("Error at getSelectedIndex in CSP ");
        }

        return result;
    }

    public Card[] getCards() {
        Card[] result = new Card[3];
        int j = 0;

        for (int i = 0; i < cardPanel.getComponentCount(); i++) {
            CardComponent cp = (CardComponent) cardPanel.getComponent(i);
            if (cp.isSelected()) {
                result[j] = cp.getCard();
                j++;
            }
        }

        if (j != 3 || j > 3)
            throw new RuntimeException("Error at getSelectedIndex in CSP ");

        return result;

    }

    /**
     * Enables or disables the accept button.
     *
     * @param b  true to enable, false otherwise.
     */
    public void setAccept(boolean b) {
        acceptButton.setEnabled(b);
    }

    public void removeSet(int[] selectedIndex) {
		/* iterate in descending order */
        for (int i = selectedIndex.length - 1; i >= 0; i--) {
            cardPanel.remove(selectedIndex[i]);
        }
		
		/* current player receives troops equal to trade in value */
        currentPlayer.addArmies(tradeInValue);
        acceptButton.setEnabled(false);
		
		/* allow player to leave if they have less than MAX_CARDS cards */
        if (currentPlayer.getHandSize() < MAX_CARDS)
            exitButton.setEnabled(true);

        nextTradeInValue();
        revalidate();
        repaint();
    }

    /** Testing below****************** */
    //TODO remove
    public static void main(String[] args) {
        System.out.println("came here");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    protected static CardScreenPanel initTest() {
        CardScreenHandler csh = new CardScreenHandler(null);
        CardScreenPanel csp = new CardScreenPanel(csh);
        csh.addView(csp);

        Card testA = new Card("cannon", "Northeast Territory");
        Card testB = new Card("horse", "Eastern United States");
        Card testC = new Card("troop", "Kamchatka");
        csp.addCard(testA);
        csp.addCard(testB);
        csp.addCard(testC);
        csp.addCard(testA);
        csp.addCard(testA);
        csp.addCard(testA);
        csp.addCard(testB);
        csp.addCard(testC);
        csp.addCard(testA);
        csp.addCard(testA);
        csp.addCard(testB);


        return csp;
    }

    protected static void createAndShowGUI() {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardScreenPanel csp = initTest();

        //testFrame.getContentPane().add( new CardComponent( testC ) );
        testFrame.getContentPane().add(csp);
        testFrame.pack();
        testFrame.setVisible(true);
    }


}
