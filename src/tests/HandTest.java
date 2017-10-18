/**
 * CSCI 2120 Fall 2014
 * Risk Game Class HandTest
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import classes.Card;
import classes.Hand;
import junit.framework.TestCase;

import java.util.ArrayList;

public class HandTest extends TestCase {
    Hand test1;


    protected void setUp() {
        test1 = new Hand();
    }


    //testing void acceptCard(Card)
    public void testAcceptCard() {
        ArrayList<Card> pCards;
        pCards = test1.getCards();

        assertTrue(pCards.isEmpty());

        test1.acceptCard(new Card("T1", "T2"));

        pCards = test1.getCards();

        assertTrue(pCards.size() == 1);
        Card c = pCards.get(0);

		/* Make sure the card we got back is the same */
        assertTrue(c.getType() == "T1");
        assertTrue(c.getTerritory() == "T2");

    }

    //testing ArrayList<Card> getCards()
    public void testGetCards() {
        Card c1 = new Card("C1", "CT1");
        Card c2 = new Card("C2", "CT2");

        test1.acceptCard(c1);
        assertTrue(test1.getCards().size() == 1);
        test1.acceptCard(c2);
        assertTrue(test1.getCards().size() == 2);
        int[] turnInIndex = {0, 1};

        ArrayList<Card> pCards = test1.getCards();
        assertTrue(pCards.size() == 2);
        assertTrue(pCards.equals(test1.getCards()));
		
		/*get cards should return no cards after this, as we have 2 cards and we are
		 * turning in cards with indices 0 and 1*/
        test1.turnInSet(turnInIndex);

        assertTrue(test1.getCards().size() == 0);

    }

    //testing ArrayList<Card> turnInSet( int[])
    public void testTurnInSet() {
        Card c1 = new Card("C1", "CT1");
        Card c2 = new Card("C2", "CT2");

        test1.acceptCard(c1);
        test1.acceptCard(c2);

        assertTrue(test1.getCards().size() == 2);
        int[] zeroIndex = {};
		
		/* should not change the # of cards */
        test1.turnInSet(zeroIndex);
        assertTrue(test1.getCards().size() == 2);

        int[] oneIndex = {1};
        test1.turnInSet(oneIndex);
        assertTrue(test1.getCards().size() == 1);
		
		/* We should be left with c1 at position 0 */
        ArrayList<Card> pCards = test1.getCards();
        Card testCard = pCards.get(0);
        assertTrue(testCard.getType() == "C1");
        assertTrue(testCard.getTerritory() == "CT1");
		
		/* should have no effect, as we already removed that index */
        test1.turnInSet(oneIndex);
        assertTrue(test1.getCards().size() == 1);

    }

    public void testTakeHand() {
        Hand test2 = new Hand();
        Card c1 = new Card("THType1", "THTerr1");
        Card c2 = new Card("THType2", "THTerr2");

        test2.acceptCard(c1);
        test2.acceptCard(c2);

        assertEquals(test1.size(), 0);
        assertEquals(test2.size(), 2);
        test1.takeHand(test2);
        assertEquals(test1.size(), 2);
        assertEquals(test2.size(), 2);

        Hand test3 = new Hand();

        assertEquals(test3.size(), 0);
        test3.takeHand(test1);

        assertEquals(test3.size(), 2);

    }
}