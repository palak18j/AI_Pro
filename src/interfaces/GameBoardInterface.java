/**
 * CSCI 2120 Fall 2014
 * Risk Game Class GameBoardInterface
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;
import java.util.ArrayList;

import classes.Territory;
import classes.Continent;



/**
 * @interface GameBoardInterface interface specifying the board used in the game
 * Game objects will contain a GameBoard object
 **/
public interface GameBoardInterface
{

	/**
	 * Returns a list of all the territories on the board
	 * @return ArrayList of Territory references
	 **/
	ArrayList<Territory> getTerritoriesList();
	
	/**
	 * Returns a list of all the continents on the board
	 * @return ArrayList of Continent references
	 **/
	ArrayList<Continent> getContinentsList();
	
	/**
	 * Retrieves a reference to a territory by name
	 * @param the String containing the name of the territory to get
	 * @return a reference to the specified territory
	 **/
	Territory getTerritoryByName( String territoryName );
	
	/**
	 * Retrieves a reference to a continent by name
	 * @param the String containing the name of the continent to get
	 * @return a reference to the specified continent
	 **/
	Continent getContinentByName( String continentName );

} 
// end GameBoard interface