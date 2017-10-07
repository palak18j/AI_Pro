/**
 * CSCI 2120 Fall 2014
 * Risk Game class RiskGame
 * @author Shane McCulley
 * @date November 29, 2014
 **/

package gui;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import engine.RiskGameEngine;

public class StartScreenHandler implements ActionListener
{
	private RiskGameEngine model;

	public StartScreenHandler( RiskGameEngine gameEngine )
	{
		this.model = gameEngine;
	}

	public void actionPerformed( ActionEvent event )
	{
		String command = event.getActionCommand();

		if ( command.equals( "createNewGame" ) )
		{
			model.createNewGame();
		}

		else if ( command.equals( "loadSavedGame" ) )
		{
			model.loadSavedGame();
		}
		else if ( command.equals( "exitGame" ) )
		{
			System.exit( 0 );
		}
		else
		{
			System.out.println( "Invalid command in Start Screen" );
		}

	}

}
