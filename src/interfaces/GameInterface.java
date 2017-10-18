/**
 * CSCI 2120 Fall 2014
 * Risk Game Class GameInterface
 *
 * @author Shane McCulley
 * @date September 15, 2014
 **/

package interfaces;


/**
 * @interface GameInterface interface specifying the Game model
 **/
public interface GameInterface {

    /**
     * Starts a new game and will call private methods to initialize the new game.
     * @param gameName the name used to save the game into a file
     **/
    void newGame(String gameName);

    /**
     * Loads a saved game from serialized objects stored in a file
     * @param gameName the name of the file containing the saved game
     **/
    void loadSavedGame(String gameName);

    /**
     * Starts the game loop that will begin actual game play
     **/
    void playGame();

    // note takeTurn() is a private method used by playGame()
    // so it does not appear in the interface


}
// end Game interface