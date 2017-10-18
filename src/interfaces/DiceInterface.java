/**
 * CSCI 2120 Fall 2014
 * Risk Game Class DiceInterface
 *
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;

/**
 * @interface DiceInterface specifying the dice used in the game to resolve attacks
 * Dice objects will be contained in Game objects
 **/
public interface DiceInterface {

    /**
     * Simulates the rolling of a set of dice
     * @param attacking an integer representing how many dice to use for the attack(1-3)
     * @param defending and integer to represent how many dice to use for the defense(1-2)
     * @return an integer array with the results of the rolls
     **/
    int[] roll(int attacking, int defending);

}
// end Dice interface