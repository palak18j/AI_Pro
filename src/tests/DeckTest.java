/**
 * CSCI 2120 Fall 2014
 * Risk Game Class DeckTest
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import classes.Card;
import classes.Deck;
import junit.framework.TestCase;

import java.util.ArrayList;

public class DeckTest extends TestCase {
    private Deck test1;
    private ArrayList<Card> someCards = new ArrayList<Card>();

    protected void setUp() {
        someCards.add(new Card("type1", ""));
        someCards.add(new Card("", "territory2"));
        test1 = new Deck(someCards);
    }

    // Testing Deck's deal() method
    public void testDeal() {
        int numCards = test1.getSize();

		/* test1 should be full of cards */
        Card c = test1.deal();
        assertTrue(c != null);
        assertEquals(test1.getSize(), numCards - 1);

		/* deal all cards */
        for (int i = 0; i < test1.getSize(); i++) {
            c = test1.deal();
            assertTrue(c != null);
        }

        assertTrue(test1.getSize() == 0);
        assertTrue(test1.hasCards() == false);

        c = test1.deal(); // no cards left to deal

        assertNull(c);

    }

    // Testing Deck's acceptCards(ArrayList<Card>) method
    public void testAcceptCards() {
        /* Set up an array list of cards to test with */

        assertTrue(test1.hasCards() == true);

		/* deal all cards */
        while (test1.hasCards()) {
            test1.deal();
        }

        assertTrue(test1.hasCards() == false);
        assertTrue(test1.getSize() == 0);

		/* adding 2 cards, size should move to 2 */
        test1.acceptCards(someCards);
        assertTrue(test1.hasCards() == true);
        assertTrue(test1.getSize() == 2);

		/*
		 * testing duplicates in deck. We should expect to have 2 cards, not 4
		 * (2 repeated)
		 */
        test1.acceptCards(someCards);
        assertTrue(test1.getSize() == 2);

        test1.deal();
        test1.deal();

        assertTrue(test1.hasCards() == false);
        assertTrue(test1.getSize() == 0);

    }

    // Testing Deck's shuffle() method
    public void testShuffle() {
		/* using Collections.shuffle method, the only way it shouldn't work is if an exception is thrown. */
        int preShuffleSize = test1.getSize();
        test1.shuffle();
        assertEquals(test1.getSize(), preShuffleSize);

    }

    // Testing Deck's hasCards() method
    public void testHasCards() {
        assertTrue(test1.hasCards() == true);

		/* deal all cards */
        while (test1.hasCards()) {
            test1.deal();
        }

        assertTrue(test1.hasCards() == false);

		/* after dealing from an empty deck, we should still be empty */
        test1.deal();
        assertTrue(test1.hasCards() == false);

		/* after adding a single card, it should evaluate to true */
        ArrayList<Card> aCard = new ArrayList<Card>();
        aCard.add(new Card("aT", "bT"));
        test1.acceptCards(aCard);
        assertTrue(test1.hasCards() == true);

		/* and empty again */
        test1.deal();
        assertTrue(test1.hasCards() == false);

    }

    // Testing getSize method
    public void testGetSize() {
        int initSize = test1.getSize();
        assertTrue(initSize >= 0);

		/* dealing single card reduces size by 1 */
        test1.deal();
        assertEquals((initSize - 1), test1.getSize());

        initSize = test1.getSize();

		/* deal all cards and check size */
        for (int i = 1; i < test1.getSize() + 1; i++) {
            test1.deal();
            assertTrue((initSize - i) == test1.getSize());
        }

        assertEquals(test1.getSize(), 0);


        test1.acceptCards(someCards);

        assertEquals(test1.getSize(), 2);
        test1.deal();
        assertEquals(test1.getSize(), 1);

    }
}
