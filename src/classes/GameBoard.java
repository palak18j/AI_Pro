/**
 * CSCI 2120 Fall 2014
 * Risk Game Class GameBoard
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

import java.util.Map;

/**
 * GameBoard class specifying the board used in the game Game objects will
 * contain a GameBoard object
 **/
public class GameBoard
{

	private Map<String, Territory> territories;
	private Map<String, Continent> continents;

	public GameBoard( Map<String, Territory> territories,
			Map<String, Continent> continents )
	{
		this.territories = territories;
		this.continents = continents;
	}

	/**
	 * Returns a list of all the territories on the board
	 * 
	 * @return Map of Territory references
	 **/
	public Map<String, Territory> getTerritoriesList()
	{
		return this.territories;
	}

	/**
	 * Returns a list of all the continents on the board
	 * 
	 * @return Map of Continent references
	 **/
	public Map<String, Continent> getContinentsList()
	{
		return this.continents;
	}

	/**
	 * Retrieves a reference to a territory by name
	 * 
	 * @param territoryName the String containing the name of the territory to
	 *        get
	 * @return a reference to the specified territory
	 **/
	public Territory getTerritoryByName( String territoryName )
	{
		return this.territories.get( territoryName );
	}

	/**
	 * Retrieves a reference to a continent by name
	 * 
	 * @param continentName the String containing the name of the continent to
	 *        get
	 * @return a reference to the specified continent
	 **/
	public Continent getContinentByName( String continentName )
	{
		return this.continents.get( continentName );
	}

} // end GameBoard class