/**
 * CSCI 2120 Fall 2014
 * Risk Game Class PlayerInterface
 *
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;

import classes.Card;
import classes.Continent;
import classes.Territory;

import java.util.HashMap;

/**
 * @interface PlayerInterface specifying players who are playing an active game
 * Player objects will be contained in Game objects
 **/
public interface PlayerInterface {

    /**
     * @return the Player's name
     **/
    String getName();

    /**
     * @return integer representing the number of armies the player has that can be placed
     **/
    int getUnplacedArmies();

    /**
     * @return integer representing the total number of armies a player has on the board
     **/
    int getTotalArmies();

    /**
     * @return an ArrayList of the territories the player controls
     **/
    HashMap<String, Territory> getTerritoriesList();

    /**
     * @return a possibly empty ArrayList of continents the player controls
     **/
    HashMap<String, Continent> getContinentsList();

    /**
     * Used to add territories to the player's list of controlled territories
     * @param newTerritory a reference to the Territory object the player now controls
     **/
    void addTerritory(Territory newTerritory);

    /**
     * Used to add a continent to the player's continent list, once all territories on
     * that continent are controlled by the player
     * @param newContinent a reference to the Continent object the player now controls
     **/
    void addContinent(Continent newContinent);

    /**
     * Used to increase the number of armies the player has that need to be placed
     * @param newArmies integer representing the number of armies the player received
     *  at the beginning of the turn
     **/
    void addArmies(int newArmies);

    /**
     * Used to give a player a new Card
     * @param newCard a reference to a Card object the player is being given
     **/
    void addCard(Card newCard);

    /**
     * Used by the player to initiate an attack on a territory
     * @param attacker a reference to a Territory object the player is attacking from
     * @param defender a reference to a Territory object that is being attacked
     * @param numAttackingArmies an integer representing the number of armies used in
     *  the attack
     **/
    void attack(Territory attacker, Territory defender, int numAttackingArmies);

}
// end Player interface