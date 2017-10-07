
package gui;

import classes.Territory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import engine.RiskGameEngine;
import engine.RiskGameEngine.State;
import engine.RiskUtils;
import java.util.Observer;
import java.util.Observable;
import java.awt.event.ActionListener;
import java.util.Map;

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
                System.out.println("came here in changeSCREEN 1 ");
		this.getContentPane().remove( this.currentPanel );
                //System.out.println("came here in changeSCREEN 2 ");
		this.currentPanel = newCurrentPanel;
                //System.out.println("came here in changeSCREEN 3 ");
		this.getContentPane().add( this.currentPanel );
                //System.out.println("came here in changeSCREEN 4 ");
		this.revalidate();
                //System.out.println("came here in changeSCREEN 5 ");
		this.repaint();
                System.out.println("came here in changeSCREEN 6 ");
	}
	
	
	public void update( Observable obs, Object obj )
	{

		State state = gameEngine.getState();
                System.out.println("came here in update ");
		/* attack phase of turn */
		if( state == State.attack )
		{
			System.out.println("attack state");
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
                    System.out.println("state fortify");
			mapScreenHandler.fortify();
		}
		else if ( state == State.loadStartScreen ) 
		{
                    System.out.println("state loadstart");
			this.changeScreen( this.startScreen );
		}
		else if ( state == State.loadSavedGame ) 
		{
                    System.out.println("state loadsaved");
			if ( currentPanel.getName().equals( "Start Screen" ) )
				( (StartScreenPanel)currentPanel ).chooseOpenFile();

		}
		else if ( state == State.createPlayers ) 
		{
                    System.out.println("state createPlayers");
			this.changeScreen( createPlayersScreen );
		}
		else if( state == State.assignTerritories )
		{
                        System.out.println("state assignT");
			
			this.changeScreen( mapScreen );
			this.pack();
			this.setLocation( RiskUtils.getRelativeScreenLocation( 0.10, 0.0) );
			mapScreenHandler.initializeMap();
                        //System.out.println("here at END");
					
		}
		else if( state == State.assignArmies )
		{   
                    System.out.println("state assignA");
			mapScreenHandler.assignArmies();
		}
		else if( state == State.placeArmies )
		{
                    System.out.println("state placeA");
			mapScreenHandler.placeArmies(); 
		}
		else if( state == State.failedInit)
		{   
                    System.out.println("state failed");
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
