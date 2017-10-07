package engine;

import java.awt.Color;
import java.util.Map;
import java.util.Observable;
import classes.Player;
import classes.RiskGame;
import classes.Territory;

/**
 * RiskGameEngine is responsible for the creation of the RiskGame object, either
 * by creating a new game and initializing players or by loading a saved game.
 */
public class RiskGameEngine extends Observable
{
	private RiskGame game;
	private State state;
	
	public enum State{ 
		failedInit(-1), loadStartScreen(1), loadSavedGame(2), createPlayers(3), 
		assignTerritories(4), assignArmies(5), placeArmies(6), turnInCards(7), attack(8),
		fortify(9);
		
		public final int value; 
		
		State( int value ) 
		{
			this.value = value; 
		}
	}

	public RiskGameEngine()
	{
            System.out.println("here 2");
            this.state = State.loadStartScreen;
		game = new RiskGame();
	}
	
	public void loadSavedGame()
	{
		this.state = State.loadSavedGame;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void loadStartScreen()
	{
		this.state = State.loadStartScreen;
		this.setChanged();
		this.notifyObservers();
	}
	
	public void createNewGame()
	{
		game.createNewGame();
		this.state = State.createPlayers;
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Creates the player objects received from GUI and advances to 
	 * assign territories state.  
	 * @param names
	 */
	public void createPlayers( Map<String, Color> playerInfo, String gameName )
	{
		if ( game.createPlayers( playerInfo, gameName  ) ) 
		{
			this.state = State.assignTerritories;
			this.setChanged();
			this.notifyObservers();
		}
		else
		{
			this.state = State.failedInit;
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	/**
	 * Advance the state to assignArmies 
	 */
	public void assignArmies()
	{
		this.state = State.assignArmies;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Begin first stage of a player's turn 
	 */
	public void placeArmies()
	{
		this.state = State.placeArmies;
		game.placeArmies();
		this.setChanged();
		this.notifyObservers();
		
	}
	
	/**
	 * Sets attack state, allows player to attack until finished 
	 */
	public void attack()
	{
		 this.state = State.attack;
		 this.setChanged();
		 this.notifyObservers();
	}
	
	/**
	 * Sets fortify state, allow player to move troops once.  
	 */
	public void fortify()
	{
		this.state = State.fortify;
		this.setChanged();
		this.notifyObservers();
	}
	/**
	 * Notifies observer that map needs to be updated 
	 */
	public void sendGUIMessage( String message )
	{
		this.setChanged();
		this.notifyObservers( message );
	}

	/**
	 * Call RiskGame method to set up the next player.  
	 */
	public Player getNextPlayer()
	{
		return game.getNextPlayer();
	}
	
	/**
	 * Call RiskGame method to get current player 
	 */
	public Player getCurrentPlayer()
	{
		return game.getCurrentPlayer();
	}

	/** 
	 * Calls RiskGame method to add territory to the current player.  
	 * @param territory String name of territory to add. 
	 */
	public void addTerritory( String territory )
	{
		if( game.getCurrentPlayer() != null )
			game.addTerritory( territory );
		
	}
	public void incrementTroops( String territory )
	{
		game.incrementTroops( territory );
	}
	
	/**
	 * If in fortify state, takes steps needed to end turn, otherwise updates
	 * the map with new state.  
	 * 
	 * @param movingFrom Territory starting move
	 * @param movingTo Territory ending move 
	 * @param numMoving int number of troops moving 
	 */
	public void moveTroops( Territory movingFrom, Territory movingTo,
			int numMoving )
	{
		game.moveTroops( movingFrom, movingTo, numMoving );
		
		if( this.state == State.fortify )
		{
			this.endTurn();
		}
		else
		{
			this.setChanged();
			this.notifyObservers( "troopsMoved" );
		}
	}
	
	/**
	 * Ends player's turn by assigning card if necessary and getting next 
	 * player.  
	 */
	public void endTurn()
	{
		game.endTurn();
		
		/* change state to placeArmies and get reinforcements */
		placeArmies();
	}

	public void loadGame( String gameFileName )
	{
		// Read & De-Serialize a game object
		// and set this.game to the de-serialized object
	}

	public RiskGame getGame()
	{
		return this.game;
	}

	public State getState()
	{
		return this.state;
	}
	
	public void turnInCards( int[] set )
	{
		this.game.turnInSet( set );
	}
	public boolean getCardStatus()
	{
		return game.getCardStatus();
	}
	
	//TODO ??????
	public void takeTurn()
	{
		
	}

	
}
