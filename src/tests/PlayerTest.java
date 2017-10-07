/**
 * CSCI 2120 Fall 2014
 * Risk Game Class PlayerTest
 * @author Shane McCulley
 * @date October 21, 2014
 **/
 
package tests;

import java.util.HashMap;

import classes.Card;
import classes.Continent;
import classes.Dice;
import classes.Player;
import classes.Territory;
import junit.framework.TestCase;
 
public class PlayerTest extends TestCase {
     
     //instance variables 
	Player testPlayer1; 
	Player testPlayer2; 
	Dice dice; 
     
    protected void setUp() 
    {
    	testPlayer1 = new Player("Test1");
    	testPlayer2 = new Player();
    	dice = new Dice();
    
    }
    
    /* testing hasTerritory method */
    public void hasTerritory()
    {
    	Territory t1 = new Territory( "t1", null, null );
    	assertEquals( testPlayer1.hasTerritory(), false );
    	
    	testPlayer1.addTerritory( t1 );
    	assertEquals( testPlayer1.hasTerritory(), true );
    	
    	testPlayer1.removeTerritory( t1 );
    	assertEquals( testPlayer1.hasTerritory(), false );
    }
    
    /* testing getNumTerritories */
    public void getNumTerritories()
    {
    	Territory t1 = new Territory( "t1", null, null );
    	assertEquals( testPlayer1.getNumTerritories(), 0 );
    	
    	testPlayer1.addTerritory( t1 );
    	assertEquals( testPlayer1.getNumTerritories(), 1 );
    	
    	testPlayer1.removeTerritory( t1 );
    	assertEquals( testPlayer1.getNumTerritories(), 0 );
    	
    }
     // Testing Player's String getName() method
    public void testGetName() 
    {
    	assertTrue( testPlayer1.getName() == "Test1");
    	assertTrue( (new Player("").getName() == "" ) );
         
    }
     
     // Testing Player's int getUnplacedArmies() method
    public void testGetUnplacedArmies() 
    {
    	assertEquals( testPlayer1.getUnplacedArmies(), 0 );
    	assertEquals( testPlayer2.getUnplacedArmies(), 0 );
    	
    	testPlayer1.addArmies(5);
    	assertEquals( testPlayer1.getUnplacedArmies(), 5 );
    	
    	/* armies haven't been placed yet, so they should be cumulative */
    	testPlayer1.addArmies(10);
    	assertEquals( testPlayer1.getUnplacedArmies(), 15 );
        
    }
     
    // Testing Player's int getTotalArmies() method
    public void testGetTotalArmies() 
    {
    	Territory t1 = new Territory("t1", null, null);
    	t1.setNumArmies(1);
    	Territory t2 = new Territory("t2", null, null);
    	t2.setNumArmies(2);
    	Territory t3 = new Territory("t3", null, null);
    	t3.setNumArmies(3);
    	
    	/* total armies are 0, they should increase with each territory added */
    	assertEquals( testPlayer1.getTotalArmies(), 0 );
    	
    	testPlayer1.addTerritory( t1 );
    	assertEquals( t1.getOccupant(), testPlayer1 );
    	assertEquals( testPlayer1.getTotalArmies(), 1 );
    	
    	testPlayer1.addTerritory( t2 );
    	assertEquals( t1.getOccupant(), testPlayer1 );
    	assertEquals( t2.getOccupant(), testPlayer1 );
    	assertEquals( testPlayer1.getTotalArmies(), 3 );
    	
    	testPlayer1.addTerritory( t3 );
    	assertEquals( testPlayer1.getTotalArmies(), 6 );
    	
    	testPlayer1.removeTerritory( t1 );  // should remove 1 army
    	assertEquals( testPlayer1.getTotalArmies(), 5 );
    	
    } 
    
    // Testing Player's ArrayList<Territory> getTerritoriesList() method
    public void testGetTerritoriesList() 
    {
    	assertEquals( testPlayer1.getTerritoriesList().size(), 0 );
    	testPlayer1.addTerritory( new Territory(null, null, null) );
    	
    	assertTrue( testPlayer1.getTerritoriesList().size() == 1 );
    	
    	HashMap<String, Territory> adj = new HashMap<String, Territory>();
		adj.put( "1",  new Territory( "T1", null, null) );
		adj.put( "2",  new Territory( "T2", null , null) );
		
		Territory t3 = new Territory( "T3", null, null);
		t3.setNeighbors( adj );
		
		testPlayer1.addTerritory( t3 ); 
		
		/*test the territories that come back */
		
		HashMap<String, Territory> gtl = 
				(HashMap<String, Territory>)testPlayer1.getTerritoriesList();
		
		assertTrue( !gtl.isEmpty() );
		assertTrue( gtl.containsKey( "T3" ) );
		assertTrue( gtl.containsValue( t3 ) );
        
    }
     
    // Testing Player's ArrayList<Continent> getContinentsList() method
    public void testGetContinentsList() 
    {
    	Continent con1 = new Continent( "C1", 5 );
    	Continent con2 = new Continent( "C2", 8 );
    	
    	assertEquals( testPlayer1.getContinentsList().size(), 0 );
    	testPlayer1.addContinent( con1 );
    	testPlayer1.addContinent( con2 );
    	
    	HashMap<String, Continent> gcl = 
    			(HashMap<String, Continent>)testPlayer1.getContinentsList();
    	assertTrue( !gcl.isEmpty() );
    	
    	/* should contain keys C1, C2 */
    	assertTrue( gcl.containsKey("C1" ) );
    	assertTrue( gcl.containsKey("C2" ) );
    	
    	/* should contain continents Con1 and Con2 as values */
    	assertTrue( gcl.containsValue( con1 ) );
    	assertTrue( gcl.containsValue( con2 ) );
    	
    }
    

    // Testing Player's void addTerritory(Territory) method
    public void testAddTerritory() 
    {
    	assertEquals( testPlayer1.getTerritoriesList().size(), 0 );
    	testPlayer1.addTerritory( new Territory(null, null, null) );
    	
    	assertTrue( testPlayer1.getTerritoriesList().size() == 1 );
    	
    	HashMap<String, Territory> adj = new HashMap<String, Territory>();
		adj.put( "1",  new Territory( "T1", null, null ) );
		adj.put( "2",  new Territory( "T2", null, null ) );
		
		Territory t3 = new Territory( "T3", null , null );
		t3.setNeighbors( adj );
		
		testPlayer1.addTerritory( t3 ); 
		
		/*test the territories that come back */
		
		HashMap<String, Territory> gtl = 
				(HashMap<String, Territory>)testPlayer1.getTerritoriesList();
		
		assertTrue( !gtl.isEmpty() );
		assertTrue( gtl.containsKey( "T3" ) );
		assertTrue( gtl.containsValue( t3 ) );
        
    }
    
    // Testing Player's void addContinent(Continent) method
    public void testAddContinent() 
    {
    	Continent con1 = new Continent( "C1", 5 );
    	Continent con2 = new Continent( "C2", 8 );
    	
    	assertEquals( testPlayer1.getContinentsList().size(), 0 );
    	
    	testPlayer1.addContinent( con1 );
    	assertTrue( testPlayer1.getContinentsList().size() == 1 );
    	
    	testPlayer1.addContinent( con2 );
    	assertTrue( testPlayer1.getContinentsList().size() == 2 );
    	
    	HashMap<String, Continent> gcl = 
    			(HashMap<String, Continent>)testPlayer1.getContinentsList();
    	
    	/* test for con1 and con2 */
    	assertTrue( gcl.containsKey( "C1" ));
    	assertTrue( gcl.containsValue( con1 ));
    	
    	assertTrue( gcl.containsKey( "C2" ));
    	assertTrue( gcl.containsValue( con2 ));
    	
        
    }
    
    // Testing Player's void addArmies(int) method
    public void testAddArmies() 
    {
    	assertEquals( testPlayer1.getUnplacedArmies(), 0 );
    	assertEquals( testPlayer2.getUnplacedArmies(), 0 );
    	
    	testPlayer1.addArmies(5);
    	assertEquals( testPlayer1.getUnplacedArmies(), 5 );
    	
    	/* armies haven't been placed yet, so they should be cumulative */
    	testPlayer1.addArmies(10);
    	assertEquals( testPlayer1.getUnplacedArmies(), 15 );
    	
    	/* testing test2 with several 0s */
    	testPlayer2.addArmies(0);
    	assertEquals( testPlayer2.getUnplacedArmies(), 0 );
    	
    	testPlayer2.addArmies(1);
    	assertEquals( testPlayer2.getUnplacedArmies(), 1 );
    	
    	testPlayer2.addArmies(0);
    	assertEquals( testPlayer2.getUnplacedArmies(), 1 );
    	
    	testPlayer2.addArmies(5);
    	assertEquals( testPlayer2.getUnplacedArmies(), 6 );
        
    }
    
    
    // Testing Player's void addTerritory() method
    public void addTerritory() 
    {
    	assertTrue( testPlayer1.getTerritoriesList() == null );
    	testPlayer1.addTerritory( new Territory(null, null, null) );
    	
    	assertTrue( testPlayer1.getTerritoriesList().size() == 1 );
    	
    	HashMap<String, Territory> adj = new HashMap<String, Territory>();
		adj.put( "1",  new Territory( "T1", null, null ) );
		adj.put( "2",  new Territory( "T2", null, null ) );
		
		Territory t3 = new Territory( "T3", null, null );
		t3.setNeighbors( adj );
		testPlayer1.addTerritory( t3 ); 
		
		/*test the territories that come back */
		
		HashMap<String, Territory> gtl = 
				(HashMap<String, Territory>)testPlayer1.getTerritoriesList();
		
		assertTrue( !gtl.isEmpty() );
		assertTrue( gtl.containsKey( "T3" ) );
		assertTrue( gtl.containsValue( t3 ) );
        
    }
     
    /*Testing Player's void attack method
     *void attack(Territory attacker, Territory defender, int numAttackingArmies)
    */
    public void testAttack() 
    {
        Territory t1 = new Territory(null, null, null);
        Territory t2 = new Territory("t2", null, null);
        t1.setNumArmies(100);
        t2.setNumArmies(15);
        
        testPlayer1.addTerritory( t1 );
        testPlayer2.addTerritory( t2 );
        
        assertTrue( t1.getOccupant() == testPlayer1 );
        assertTrue( t2.getOccupant() == testPlayer2 );
           
        assertEquals( testPlayer1.getHandSize(), 0 );
        assertEquals( testPlayer2.getHandSize(), 0 );
        testPlayer2.addCard( new Card( "Type", "Territory" ) );
        testPlayer2.addCard( new Card( "Type2", "Territory2" ) );
        assertEquals( testPlayer2.getHandSize(), 2 );
        
        /* after attacking, either t1 or t2 has less armies than before, or both */
        testPlayer1.attack( t1, t2, dice.roll( 3, 2 ) );
        
        /* armies should not grow after attacking */
        assertTrue( t1.getNumArmies() <= 100 && t2.getNumArmies() <= 15 );
        
        /* At least one side must lose armies */
        assertTrue( t1.getNumArmies() < 100 || t2.getNumArmies() < 15 );
        
        /* if defender armies reduced to 0, player should change */
        while( t2.getOccupant() == testPlayer2 && t1.getNumArmies() > 0  )
        {
        	 /* constantly attack t2 from t1 with 3 armies until win, defend
        	with 2 armies if possible, 1 if not */
        	testPlayer1.attack( t1, t2, 
        			dice.roll( 3, java.lang.Math.min( 2, t2.getNumArmies() )) );  
        }
        
        /* test1 should now control t2 after reducing armies to 0 */
        assertTrue( t2.getOccupant() == testPlayer1 );
        
        /* test1 should have to move 3 armies into t2 */
        assertEquals( t2.getNumArmies(), 3 );
        
        /* test1 should have both of test2's cards now */
        assertEquals( testPlayer1.getHandSize(), 2 );
        
        /* test attacking with 2 armies */
        Player test3 = new Player("T3");
        Territory t3 = new Territory("t3", null, null);
        test3.addTerritory( t3 );
        t3.setNumArmies( 1 );
        test3.addCard( new Card( "T3", "T3" ) );
        
        t1.setNumArmies( 100 );
        while( t3.getOccupant() == test3 && t1.getNumArmies() > 0 )
        {
        	 /* constantly attack t3 from t1 with 2 armies until win, defend
        	with 2 armies if possible, 1 if not */
        	testPlayer1.attack( t1, t3, 
        			dice.roll( 2, java.lang.Math.min( 2, t2.getNumArmies() )) );  
        }
        
        assertEquals( t3.getOccupant(), testPlayer1 );
        assertEquals( t3.getNumArmies(), 2 );
        assertEquals( testPlayer1.getHandSize(), 3 );
        
        /* test attacking with 1 army */
        Player test4 = new Player("T4");
        Territory t4 = new Territory("t4", null, null);
        test4.addTerritory( t4 );
        t4.setNumArmies( 1 );
        test4.addCard( new Card( "T4", "T4" ) );
        
        t1.setNumArmies( 100 );
        while( t4.getOccupant() == test4 && t1.getNumArmies() > 0 )
        {
        	 /* constantly attack t4 from t1 with 1 armies until win, defend
        	with 2 armies if possible, 1 if not */
        	testPlayer1.attack( t1, t4, 
        			dice.roll( 1, java.lang.Math.min( 2, t2.getNumArmies() )) );  
        }
        
        assertEquals( t4.getOccupant(), testPlayer1 );
        assertEquals( t4.getNumArmies(), 1 );
        assertEquals( testPlayer1.getHandSize(), 4 );
        
        int [] set = {0, 1, 2, 3};
        java.util.ArrayList<Card> cardList = testPlayer1.turnInSet( set );
        
        assertEquals( cardList.get(0).getType(), "Type" );
        assertEquals( cardList.get(1).getType(), "Type2" );
        
        assertEquals( cardList.get(0).getTerritory(), "Territory" );
        assertEquals( cardList.get(1).getTerritory(), "Territory2" );
        
    } 
    
    public void testMoveTroops( )
	{
    	Territory t4 = new Territory( "Test4", null, null);
    	t4.setNumArmies( 15 );
    	testPlayer1.addTerritory( t4 );
    	
    	Territory t5 = new Territory( "Test5", null, null );
    	t5.setNumArmies( 20 );
    	testPlayer1.addTerritory( t5 );
    	
    	Territory t6 = new Territory( "Test6", null, null );
    	t6.setNumArmies( 50 );
    	testPlayer2.addTerritory( t6 );
    	
    	// test 1 way
    	testPlayer1.moveTroops( t4, t5, 5 );
    	assertEquals( t4.getNumArmies(), 10 );
    	assertEquals( t5.getNumArmies(), 25 );
    	
    	// test 0 
    	testPlayer1.moveTroops( t4, t5, 0 );
    	assertEquals( t4.getNumArmies(), 10 );
    	assertEquals( t5.getNumArmies(), 25 );
    	
    	// test back and forth 
    	testPlayer1.moveTroops( t4, t5, 1 );
    	assertEquals( t4.getNumArmies(), 9 );
    	assertEquals( t5.getNumArmies(), 26 );
    	
    	testPlayer1.moveTroops( t5, t4, 1 );
    	assertEquals( t4.getNumArmies(), 10 );
    	assertEquals( t5.getNumArmies(), 25 );
    	
	}
 }
 
 
