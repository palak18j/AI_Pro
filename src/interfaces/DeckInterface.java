/**
 * CSCI 2120 Fall 2014
 * Risk Game Class DeckInterface
 *
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;

import classes.Card;

import java.util.ArrayList;


/**
 * @interface DeckInterface specifying the deck of undistributed Risk game cards
 * Game objects will contain Deck objects
 **/
public interface DeckInterface {

    /**
     * Issues one card to be given to a player
     * @return Card from the front of the deck
     **/
    Card deal();

    /**
     * Used to add cards turned-in by players back to the deck
     * @param set an ArrayList of Cards turned-in by a player
     **/
    void acceptCards(ArrayList<Card> set);

    /**
     * Shuffles the deck
     **/
    void shuffle();

    // may also want

    /**
     *
     **/
    boolean hasCards();

    /**
     * return number of cards left in deck
     */
    int getSize();
}
// end Deck interface


