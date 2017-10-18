/**
 * CSCI 2120 Fall 2014
 * Risk Game Class TerritoryTest
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import classes.Player;
import classes.Territory;
import junit.framework.TestCase;

import java.util.HashMap;

public class TerritoryTest extends TestCase {
    private Territory test1;
    private Territory test2;
    private HashMap<String, Territory> adj;


    protected void setUp() {
        test1 = new Territory(null, null, null);

		/*initialize adjacencies and add to them */
        adj = new HashMap<String, Territory>();
        adj.put("1", new Territory("T1", null, null));
        adj.put("2", new Territory("T2", null, null));

        test2 = new Territory("Test2", null, null);
        test2.setNeighbors(adj);

    }

    // tests String getName() method
    public void testGetName() {
        Territory test3 = new Territory("", null, null);

        assertTrue(test2.getName().equals("Test2"));
        assertTrue(test3.getName().equals(""));
    }


    //tests HashMap<String, Territory> getNeighbors() method
    public void testGetNeighbors() {
        HashMap<String, Territory> neighbors =
                (HashMap<String, Territory>) test2.getNeighbors();
        assertTrue(neighbors.size() == 2);
		
		/* make sure the territories are the same */
        Territory t1 = neighbors.get("1");
        Territory t2 = neighbors.get("2");

        assertTrue(t1.getName() == "T1");
        assertTrue(t1.getNeighbors() == null);
        assertTrue(t2.getName() == "T2");
        assertTrue(t2.getNeighbors() == null);

    }

    //test Player getOccupant()
    public void testGetOccupant() {
        assertTrue(test1.getOccupant() == null);
        test1.setOccupant(new Player("testPlayer"));
        assertTrue(test1.getOccupant().getName() == "testPlayer");

    }

    //test int getNumArmies()
    public void testGetNumArmies() {
        assertTrue(test1.getNumArmies() == 0);
        test1.setNumArmies(2);
        assertTrue(test1.getNumArmies() == 2);
        test1.setNumArmies(0);
        assertTrue(test1.getNumArmies() == 0);
        test2.setNumArmies(5);
        test2.setNumArmies(10);
        assertTrue(test2.getNumArmies() == 10);
    }

    //test void setOccupant( Player )
    public void testSetOccupant() {
        Player p1 = new Player("P1");
        p1.addTerritory(test1);
        p1.addTerritory(test2);

        test2.setOccupant(p1);
		
		/*get occupant of test2, and check to make sure it preserves player */
        Player p2 = test2.getOccupant();
        assertTrue(p2.getName() == "P1");
        //assertTrue( p2.getUnplacedArmies() == 5 );
        assertTrue(p2.getTerritoriesList().containsValue(test2));
        assertTrue(p2.getTerritoriesList().containsValue(test1));

    }

    //test void setNumArmies()
    public void testSetNumArmies() {
        assertTrue(test1.getNumArmies() == 0);
        test1.setNumArmies(2);
        assertTrue(test1.getNumArmies() == 2);
        test1.setNumArmies(0);
        assertTrue(test1.getNumArmies() == 0);

        test2.setNumArmies(0);
        assertTrue(test2.getNumArmies() == 0);

        test2.setNumArmies(50);
        assertTrue(test2.getNumArmies() == 50);
    }


}
