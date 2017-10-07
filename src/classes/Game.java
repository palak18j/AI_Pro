/**
 * CSCI 2120 Fall 2014
 * Risk Game Class Game
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

/**
 * Game class interface specifying the Game model
 **/
public class Game
{

	public Game()
	{
		takeTurn();
	}

	/**
	 * Starts a new game and will call private methods to initialize the new
	 * game.
	 * 
	 * @param gameName the name used to save the game into a file
	 **/
	public void newGame( String gameName )
	{
	}

	/**
	 * Loads a saved game from serialized objects stored in a file
	 * 
	 * @param gameName the name of the file containing the saved game
	 **/
	public void loadSavedGame( String gameName )
	{
	}

	/**
	 * Starts the game loop that will begin actual game play
	 **/
	public void playGame()
	{
	}

	private void takeTurn()
	{
	}

}
// end Game class