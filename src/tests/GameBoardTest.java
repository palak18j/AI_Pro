/**
 * CSCI 2120 Fall 2014
 * Risk Game Class GameBoardTest
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package tests;

import java.util.HashMap;
import junit.framework.TestCase;
import classes.Territory;
import classes.Continent;
import classes.GameBoard;

public class GameBoardTest extends TestCase
{

	private GameBoard testGameBoard;

	private Continent testContinent;
	private Continent testContinentEurope;

	private Territory testTerritory;
	private Territory testTerritoryFrance;

	/* to initialize the gameboard with some values */
	private Territory france;
	private Territory spain;
	private Continent europe;
	private Continent asia;

	protected void setUp()
	{

		france = new Territory( "France", null, null );
		spain = new Territory( "Spain", null, null );
		HashMap<String, Territory> territories = new HashMap<String, Territory>();
		territories.put( france.getName(), france );
		territories.put( spain.getName(), spain );

		europe = new Continent( "Europe", 5 );
		asia = new Continent( "Asia", 7 );
		HashMap<String, Continent> continents = new HashMap<String, Continent>();
		continents.put( europe.getName(), europe );
		continents.put( asia.getName(), asia );

		testGameBoard = new GameBoard( territories, continents );

		/* Fresh copy of tests for each method */
		testContinent = new Continent( null, 0 );
		testTerritory = new Territory(null, null, null);

		testContinentEurope = new Continent( "Europe", 5 );
		testTerritoryFrance = new Territory( "France", null, null );

	}

	// Testing GameBoard's getContinentByName(String): Continent method
	public void testGetContinentByName()
	{

		testContinent = testGameBoard.getContinentByName( "Europe" );
		assertEquals( testContinent, europe );

		testContinent = testGameBoard.getContinentByName( europe.getName() );
		assertEquals( testContinent, europe );

		// testContinent should be Europe
		assertTrue( testContinent != null );
		assertTrue( testContinent.getNumBonusArmies() > 0 );
		assertEquals( testContinent.getNumBonusArmies(),
				testContinentEurope.getNumBonusArmies() );

		assertEquals( testContinent.getName(), ( testContinentEurope.getName() ) );

		/* should not be any continents with "" as name */
		assertTrue( testGameBoard.getContinentByName( "" ) == null );
		assertTrue( testGameBoard.getContinentByName( "Europa" ) == null );

	}

	// Testing GameBoard's getContinentsList(): HashMap<String, Continent>
	// method
	public void testGetContinentsList()
	{
		HashMap<String, Continent> gcl = 
				(HashMap<String, Continent>)testGameBoard.getContinentsList();

		assertTrue( !gcl.isEmpty() );

		assertTrue( gcl.size() == 2 );

		assertTrue( gcl.containsKey( asia.getName() ) );
		assertTrue( gcl.containsKey( europe.getName() ) );
		assertTrue( gcl.containsValue( asia ) );
		assertTrue( gcl.containsValue( europe ) );

	}

	// Testing GameBoard's getTerritoriesList(): HashMap<String, Territory>
	// method
	public void testGetTerritoriesList()
	{
		HashMap<String, Territory> gtl = 
				(HashMap<String, Territory>)testGameBoard.getTerritoriesList();

		assertTrue( !gtl.isEmpty() );

		assertTrue( gtl.size() == 2 );

		assertTrue( gtl.containsKey( france.getName() ) );
		assertTrue( gtl.containsKey( spain.getName() ) );
		assertTrue( gtl.containsValue( france ) );
		assertTrue( gtl.containsValue( spain ) );

	}

	// Testing GameBoard's getTerritoryByName(String): Territory method
	public void testGetTerritoryByName()
	{
		testTerritory = testGameBoard.getTerritoryByName( "France" );
		assertEquals( testTerritory, france );

		testTerritory = testGameBoard.getTerritoryByName( france.getName() );
		assertEquals( testTerritory, france );

		// testTerritory should be france
		assertTrue( testTerritory != null );
		assertEquals( testTerritory.getName(), ( testTerritoryFrance.getName() ) );

		/* should not be any territories with "" as name */
		assertTrue( testGameBoard.getTerritoryByName( "" ) == null );
		assertTrue( testGameBoard.getTerritoryByName( "Franc" ) == null );

	}

}