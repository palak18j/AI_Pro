/**
 * CSCI 2120 Fall 2014
 * Risk Game Class Territory
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

import java.awt.Point;
import java.util.Map;

/**
 * Territory class specifying territories on the board GameBoard objects will
 * contain Territory objects
 **/
public class Territory
{
	private String name;
	private Player occupant;
	private int armies;
	private Map<String, Territory> neighbors;
	private Point circleCenter;
	private Continent continent;

	public Territory( String name, Continent continent, Point circleCenter )
	{
		this.name = name;
		this.circleCenter = circleCenter;
		this.continent = continent;
	}

	/**
	 * Sets neighbors on configuration
	 * 
	 * @param neighbors a Map of adjacencies to this territory.
	 */
	public void setNeighbors( Map<String, Territory> neighbors )
	{
		this.neighbors = neighbors;
	}

	/**
	 * @return the continent this territory belongs to.
	 */
	public Continent getContinent()
	{
		return continent;
	}

	/**
	 * @return a Point identifying center of circle to draw for this territory.
	 */
	public Point getCircleCenter()
	{
		return circleCenter;
	}

	/**
	 * @return the X coordinate of the circle for this territory.
	 */
	public int getCircleX()
	{
		return circleCenter.x;
	}

	/**
	 * @return the Y coordinate of the circle for this territory.
	 */
	public int getCircleY()
	{
		return circleCenter.y;
	}

	/**
	 * @return the name of the territory
	 **/
	public String getName()
	{
		return name;
	}

	/**
	 * @return an ArrayList of all territories it shares a border with
	 **/
	public Map<String, Territory> getNeighbors()
	{
		return neighbors;
	}

	/**
	 * @return a reference to the player that currently owns this territory
	 **/
	public Player getOccupant()
	{
		return occupant;
	}

	/**
	 * @return the number of armies the occupying player has in the territory
	 **/
	public int getNumArmies()
	{
		return armies;
	}

	/**
	 * Used to set the new occupying player of a territory
	 * 
	 * @param occupant reference to the Player object who now occupies the
	 *        territory
	 **/
	public void setOccupant( Player occupant )
	{
		this.occupant = occupant;
	}

	/**
	 * Used by the occupying player to add armies to a territory
	 * 
	 * @param numArmies the number of armies to be placed into the territory
	 **/
	public void setNumArmies( int numArmies )
	{
		this.armies = numArmies;
	}

	/**
	 * Determines if a territory is adjacent to another for attacking, moving
	 * purposes.
	 * 
	 * @param t another territory to check adjacency with.
	 * @return true if adjacent, false otherwise.
	 */
	public boolean isAdjacent( Territory t )
	{
		return neighbors.containsValue( t );
	}

	/**
	 * Determines if a territory is adjacent to another for attacking, moving
	 * purposes.
	 * 
	 * @param t another territory to check adjacency with.
	 * @return true if adjacent, false otherwise.
	 */
	public boolean isAdjacent( String s )
	{
		return neighbors.containsKey( s );
	}

	public String toString()
	{
		return this.name;
	}

	/**
	 * Increase the number of armies by 1.
	 */
	public void incrementTroops()
	{
		this.armies++;
	}

	/**
	 * Passes color value from occupant to map.
	 * 
	 * @return Color specifies owner of territory by circle color.
	 */
	public java.awt.Color getColor()
	{
		return occupant.getColor();
	}

}// end Territory class