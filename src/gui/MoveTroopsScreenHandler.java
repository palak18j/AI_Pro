/**
 * CSCI 2120 Fall 2014
 * Risk class MoveTroopsScreenHandler
 * @author Shane McCulley
 * @date Dec 5, 2014
 **/
package gui;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import classes.Territory;
import engine.RiskGameEngine;

public class MoveTroopsScreenHandler implements ActionListener,
		ChangeListener
{

	private RiskGameEngine model;
	private MoveTroopsScreenPanel view;
	private Action verifyTextAction;

	public MoveTroopsScreenHandler( RiskGameEngine model )
	{
		this.model = model;
		createVerifyTextAction();
	}

	public void addView( MoveTroopsScreenPanel view )
	{
		this.view = view;
	}

	@Override
	public void actionPerformed( ActionEvent event )
	{
		String command = event.getActionCommand();

		if ( command == "Move" )
		{
			/* calls handler's moveTroops method with data from fields */
			view.callMoveTroops();
			SwingUtilities.getAncestorOfClass(
					JInternalFrame.class, (Component)event.getSource() )
					.setVisible( false );
		}

		else if ( command == "Min" )
		{
			view.setMinTroops();
		}
		else if ( command == "Max" )
		{
			view.setMaxTroops();
		}

		/* close window, but do not change state */
		else if ( command.equals( "Quit" ) )
		{
			SwingUtilities.getAncestorOfClass(
					JInternalFrame.class, (Component)event.getSource() )
					.setVisible( false );
		}
	}

	public void moveTroops( Territory movingFrom, Territory movingTo,
			int numMoving )
	{
		model.moveTroops( movingFrom, movingTo, numMoving );
	}

	/**
	 * Creates an action for the textfield to verify correct input when the
	 * specified key set in MTSP is pressed. JFormattedTextField has a formatter
	 * factory set with the appropriate values.
	 * 
	 * @return an action to verify input on the field.
	 */
	@SuppressWarnings( "serial" )
	private void createVerifyTextAction()
	{
		this.verifyTextAction = new AbstractAction()
		{
			public void actionPerformed( ActionEvent event )
			{
				JFormattedTextField textField =
						(JFormattedTextField)event.getSource();

				// The text is invalid.
				if ( !textField.isEditValid() )
				{
					Toolkit.getDefaultToolkit().beep();
					view.handleInvalidInput();
				}
				else
					try
					{ // The text is valid,
						textField.commitEdit();
						view.handleValidInput();
					}
					catch ( java.text.ParseException e )
					{
						System.out.println( e.getMessage() );
					}
			}
		};
	}

	public Action getVerifyTextAction()
	{
		return verifyTextAction;
	}

	@Override
	public void stateChanged( ChangeEvent e )
	{
		JSlider slider = (JSlider)e.getSource();
		view.setTextField( slider.getValue() );
	}

}
