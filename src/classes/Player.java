/**
 * CSCI 2120 Fall 2014
 * Risk Game Class Player
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @interface PlayerInterface specifying players who are playing an active game
 *            Player objects will be contained in Game objects
 **/
public class Player {
    // Instance variables
    private String name;
    private int numArmies;
    private Map<String, Territory> territories;
    private Map<String, Continent> continents;
    private Hand cardHand;
    private Color circleColor;

    /* track capture for card at end of turn */
    private boolean hasCaptured;

    public Player() {
        this(null);
        hasCaptured = false;
    }

    public Player(String name) {
        this.name = name;
        territories = new HashMap<String, Territory>();
        continents = new HashMap<String, Continent>();
        cardHand = new Hand();
        hasCaptured = false;
    }

    public Player(String name, Color color) {
        this(name);
        circleColor = color;
    }

    /**
     * @return the player's color
     */
    public Color getColor() {
        return this.circleColor;
    }

    /**
     * @return the Player's name
     **/
    public String getName() {
        return name;
    }

    /**
     * @return integer representing the number of armies the player has that can
     *         be placed
     **/
    public int getUnplacedArmies() {
        return numArmies;
    }

    /**
     * @return integer representing the total number of armies a player has on
     *         the board
     **/
    public int getTotalArmies() {
        int result = 0;

        for (Territory t : territories.values())
            result = result + t.getNumArmies();

        return result;

    }

    /**
     * @return an ArrayList of the territories the player controls, should be at
     *         least 1 territory
     **/
    public Map<String, Territory> getTerritoriesList() {
        return this.territories;
    }

    /**
     * @return a possibly empty ArrayList of continents the player controls
     **/
    public Map<String, Continent> getContinentsList() {
        return this.continents;
    }

    /**
     * Used to add territories to the player's list of controlled territories
     * and set the occupant of that territory.
     *
     * @param newTerritory a reference to the Territory object the player now
     *        controls
     **/
    public void addTerritory(Territory newTerritory) {
        this.territories.put(newTerritory.getName(), newTerritory);
        newTerritory.setOccupant(this);
    }

    /**
     * Used to add a continent to the player's continent list, once all
     * territories on that continent are controlled by the player.
     *
     * @param newContinent a reference to the Continent object the player now
     *        controls
     **/
    public void addContinent(Continent newContinent) {
        this.continents.put(newContinent.getName(), newContinent);
        newContinent.setOccupant(this);
    }

    /**
     * Used to increase the number of armies the player has that need to be
     * placed.
     *
     * @param newArmies integer representing the number of armies the player
     *        received at the beginning of the turn
     **/
    public void addArmies(int newArmies) {
        this.numArmies += newArmies;
    }

    /**
     * Used to give a player a new Card
     *
     * @param newCard a reference to a Card object the player is being given
     **/
    public void addCard(Card newCard) {
        this.cardHand.acceptCard(newCard);
    }

    /**
     * Resolves the action of an attack by decreasing troops and reassigning
     * territories as needed.
     *
     * @param attackingFrom a reference to a Territory object the player is
     *        attacking from
     * @param attackingTo a reference to a Territory object that is being
     *        attacked
     * @param diceResults array containing attacker results in ascending order,
     *        0 separator, followed by defender results in ascending order.
     * @return true if attacker takes territory, false otherwise
     **/
    public boolean attack(Territory attackingFrom, Territory attackingTo,
                          int[] diceResults) {
        boolean result = false;

		/* Decrease these as players lose dice rolls */
        int attackerArmies = attackingFrom.getNumArmies();
        int defenderArmies = attackingTo.getNumArmies();

        int numAttacking = findSeparator(diceResults);

		/* highest attack rolls start at separator-1 and decrease to index 0 */
        int nextHighAttack = numAttacking - 1;

		/* high defense rolls start at length - 1 and decrease to separator */
        int nextHighDefend = diceResults.length - 1;

		/* process dice until attacker or defender runs out */
        while (diceResults[nextHighDefend] != 0 && nextHighAttack >= 0) {
            /* defense wins in a tie */
            if (diceResults[nextHighDefend] >= diceResults[nextHighAttack])
                attackerArmies--;
            else
                defenderArmies--;

            nextHighDefend--;
            nextHighAttack--;
        }

        if (defenderArmies == 0) {
            occupyTerritory(attackingTo, numAttacking);
            result = true;
        }


        attackingTo.setNumArmies(defenderArmies);
        attackingFrom.setNumArmies(attackerArmies);

        return result;

    }

    /**
     * Returns the index of the 0 separator in diceResults.
     *
     * @param diceResults array containing attacker results in ascending order,
     *        0 separator, followed by defender results in ascending order
     * @return integer index of 0 separator
     */
    private int findSeparator(int[] diceResults) {
        int separator = -1;
        int i = 0;

		/* loop through results until separator found */
        while (separator == -1 && i < diceResults.length) {
            if (diceResults[i] == 0)
                separator = i;
            else
                i++;
        }

        return separator;
    }

    /**
     * Takes control of territory captured and checks for elimination. If a
     * player is eliminated, gain their cards. Army moving minimum restrictions
     * handled in GUI.
     *
     * @param defender the country to take control of for this player.
     * @param numAttacking Number of troops attacking that must be moved into
     *        new territory.
     */
    private void occupyTerritory(Territory defender, int numAttacking) {
        Player defendingPlayer = defender.getOccupant();
        defendingPlayer.removeTerritory(defender);
        this.addTerritory(defender);

		/* handle defeated case, take cards */
        if (!defendingPlayer.hasTerritory())
            this.cardHand.takeHand(defendingPlayer.cardHand);

    }

    public Hand getHand() {
        return cardHand;
    }

    /**
     * Moves armies from one territory to another.
     *
     * @param movingFrom Territory where armies will decrease by amount
     * @param movingTo Territory where armies will increase by amount
     * @param amount int amount of troops to swap.
     */
    public void moveTroops(Territory movingFrom, Territory movingTo, int amount) {
        assert movingFrom.getOccupant() == this;
        assert movingTo.getOccupant() == this;
        movingFrom.setNumArmies(movingFrom.getNumArmies() - amount);
        movingTo.setNumArmies(movingTo.getNumArmies() + amount);
    }

    public java.util.ArrayList<Card> turnInSet(int[] set) {
        return cardHand.turnInSet(set);
    }

    public int getHandSize() {
        return cardHand.size();
    }

    /**
     * Removes a territory from the player when he loses one.
     *
     * @param oldTerritory The territory lost
     */
    public void removeTerritory(Territory oldTerritory) {
        this.territories.remove(oldTerritory.getName());
    }

    public boolean hasTerritory() {
        return (!this.territories.isEmpty());
    }

    public int getNumTerritories() {
        return territories.size();
    }

    /**
     * Decreases the amount of armies by 1.
     */
    public void decrementArmies() {
        this.numArmies--;
    }

    /**
     * Increases the amount of armies by 1.
     */
    public void incrementArmies() {
        this.numArmies++;
    }

    public void setHasCaptured(boolean flag) {
        this.hasCaptured = flag;
    }

    public boolean hasCaptured() {
        return this.hasCaptured;
    }

} // end Player class