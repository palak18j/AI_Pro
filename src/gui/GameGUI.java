/**
 * CSCI 2120 Fall 2014
 * Risk Game class RiskGame
 * @author Shane McCulley
 * @date November 29, 2014
 **/

package gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import engine.RiskGameEngine;
import engine.RiskGameEngine.State;
import engine.RiskUtils;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.ActionListener;

@SuppressWarnings( "serial" )
public class GameGUI extends JFrame implements Observer
{

	private RiskGameEngine gameEngine;
	private JPanel currentPanel;

	// START SCREEN
	private ActionListener startScreenHandler;
	private JPanel startScreen;

	// CREATE PLAYERS SCREEN
	private ActionListener createPlayersScreenHandler;
	private JPanel createPlayersScreen;
	
	// MAIN MAP SCREEN 
	private MapScreenHandler mapScreenHandler; 
	private MapScreenPanelTest mapScreen;
	
	/* internal frames to the main map screen below */
	
	// CARD SCREEN
	private CardScreenHandler cardHandler;
	private CardScreenPanel cardScreen;
	
	// ATTACK SCREEN
	private AttackScreenHandler attackHandler;
	private AttackScreenPanel attackScreen;
	
	//FORTIFY SCREEN 
	private MoveTroopsScreenHandler moveTroopsHandler;
	private MoveTroopsScreenPanel moveTroopsScreen;

	/**
	 * // SELECT TERRITORIES SCREEN private ActionListener
	 * selectTerritoriesScreenHandler; private JPanel selectTerritoriesScreen;
	 **/
	
    /**
     * // SELECT TERRITORIES SCREEN private ActionListener
 selectTerritoriesScreenHandler; private JPanel selectTerritoriesScreen;
     * @param gameEngine
     */
    public GameGUI( RiskGameEngine gameEngine )
	{	
            //System.out.println("cameHere");
            //this.setPreferredSize( new java.awt.Dimension( 500, 300 ) );//m1
		this.gameEngine = gameEngine;
		// START SCREEN INIT
		this.startScreenHandler = new StartScreenHandler( gameEngine );
		this.startScreen = new StartScreenPanel( startScreenHandler );

		// CREATE PLAYERS SCREEN INIT
		this.createPlayersScreenHandler = new CreatePlayersScreenHandler(
				this.gameEngine );
		this.createPlayersScreen = new CreatePlayersScreenPanel(
				this.createPlayersScreenHandler );
		
		//MAIN MAP SCREEN 
		mapScreenHandler = new MapScreenHandler( gameEngine );
		mapScreen = new MapScreenPanelTest( mapScreenHandler );
		
		/* all panels below are internal frames to the main map screen */
		//CARD SCREEN 
		cardHandler = new CardScreenHandler( gameEngine );
		cardScreen = new CardScreenPanel( cardHandler );
		mapScreen.setCardScreenPanel( cardScreen );
		
		//ATTACK SCREEN 
		attackHandler = new AttackScreenHandler( gameEngine );
		attackScreen = new AttackScreenPanel( attackHandler );
		mapScreen.setAttackScreenPanel( attackScreen );
		
		//FORTIFY SCREEN 
		moveTroopsHandler = new MoveTroopsScreenHandler( gameEngine );
		moveTroopsScreen = new MoveTroopsScreenPanel( moveTroopsHandler );
		mapScreen.setMoveTroopsScreenPanel( moveTroopsScreen );
		
		this.currentPanel = this.startScreen;	
		this.getContentPane().add( currentPanel );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
	}
	
	/**
	 * Method called by update to change panels. Removes currentPanel from
	 * contentPane, adds the argument to contentPane, and repaints.
	 * 
	 * @param newPanel the new panel to be added to contentPane
	 */
	private void changeScreen( JPanel newCurrentPanel )
	{
		this.getContentPane().remove( this.currentPanel );
		this.currentPanel = newCurrentPanel;
		this.getContentPane().add( this.currentPanel );
		this.revalidate();
		this.repaint();
	}
	
	
	public void update( Observable obs, Object obj )
	{

		State state = gameEngine.getState();

		/* attack phase of turn */
		if( state == State.attack )
		{
			
			if( obj instanceof String) 
			{
				if ( obj.equals( "captured") )
					mapScreenHandler.updateCapturedState();
				
				else if( obj.equals( "troopsMoved" )) 
					mapScreenHandler.updateMove();
			
				else if( obj.equals( "updateAttackBox") )
					mapScreenHandler.updateAttackBox();
				
				else
				{
					mapScreenHandler.updateMap();
				}
					
			}
			else
			{
				mapScreenHandler.attack();
			}

		} // end attack phase if 
		
		/*begin fortify phase of turn */
		else if ( state == State.fortify )
		{
			mapScreenHandler.fortify();
		}
		else if ( state == State.loadStartScreen ) 
		{
			this.changeScreen( this.startScreen );
		}
		else if ( state == State.loadSavedGame ) 
		{
			if ( currentPanel.getName().equals( "Start Screen" ) )
				( (StartScreenPanel)currentPanel ).chooseOpenFile();

		}
		else if ( state == State.createPlayers ) 
		{
			this.changeScreen( createPlayersScreen );
		}
		else if( state == State.assignTerritories )
		{
			
			this.changeScreen( mapScreen );
			this.pack();
			this.setLocation( RiskUtils.getRelativeScreenLocation( 0.10, 0.0) );
			mapScreenHandler.initializeMap();
					
		}
		else if( state == State.assignArmies )
		{
			mapScreenHandler.assignArmies();
		}
		else if( state == State.placeArmies )
		{
			mapScreenHandler.placeArmies(); 
		}
		else if( state == State.failedInit)
		{
			JOptionPane.showMessageDialog( null, "Game creation failed. "
					+ "Exiting." );
			System.exit ( state.value );
		}
		
		else
		{
			System.out.println( "Shit went wrong" );
		}
	}

}
