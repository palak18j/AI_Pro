/**
 * CSCI 2120 Fall 2014
 * Risk Game Class ContinentInterface
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;

import java.util.ArrayList;
import classes.Player;
import classes.Territory;


/**
 * @interface ContinentInterface interface specifying continents on the board
 * GameBoard objects will contain Continent objects 
 **/
public interface ContinentInterface
{

	/**
	 * @return the name of the continent as a String
	 **/
	String getName();
	
	/**
	 * If no player owns all territories on a continent then this method should 
	 * return null
	 * @return a reference to the Player object that currently owns this continent
	 **/
	Player getOccupant();
	
	/**
	 * @return the number of additional armies a player gets for owning this continent
	 **/
	int getNumBonusArmies();
	
	/**
	 * Sets which player owns a continent, if any
	 * @param occupant a reference to the Player object that now owns this continent
	 **/
	void setOccupant( Player occupant );
	
	// May also want
	
	/**
	 * @return true of there is a player that owns all the territories on this continent
	 **/
	boolean isOccupied();
	
	/**
	 * @return an ArrayList of all the territories on the continent
	 **/
	ArrayList<Territory> getTerritories();

}
// end Continent interface
