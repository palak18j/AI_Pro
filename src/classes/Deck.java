/**
 * CSCI 2120 Fall 2014
 * Risk Game Class Deck
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck class specifying the deck of undistributed Risk game cards Game objects
 * will contain a Deck object
 **/
public class Deck {

    private ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
        shuffle();
    }

    /**
     * Issues one card to be given to a player. Because players can only hold 5
     * cards at a time, the deck should never run out of cards.
     *
     * @return Card from the front of the deck
     **/
    public Card deal() {
        Card c = this.hasCards() ? cards.remove(0) : null;
        return c;
    }

    /**
     * Used to add cards turned-in by players back to the deck. Turn in will be
     * verified in the game class.
     *
     * @param set an ArrayList of Cards turned in by a player
     **/
    public void acceptCards(ArrayList<Card> set) {
        for (Card c : set) {
            /* cards should be unique */
            if (!this.cards.contains(c))
                cards.add(c);
        }
    }

    /**
     * Shuffles the deck
     **/
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * @return true if there are cards left, false if not
     **/
    public boolean hasCards() {
        return (!cards.isEmpty());
    }

    /**
     * @return number of cards left in deck.
     */
    public int getSize() {
        return cards.size();
    }

}// end Deck class

