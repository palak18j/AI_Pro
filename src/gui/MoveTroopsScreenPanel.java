/**
 * CSCI 2120 Fall 2014
 * Risk class AttackScreenPanel
 * @author Shane McCulley
 * @date Dec 4, 2014
 **/
package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import classes.Territory;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

@SuppressWarnings( "serial" )
public class MoveTroopsScreenPanel extends JPanel
{

	private MoveTroopsScreenHandler handler;
	private String errorMessage;

	/* north panel description */
	private JPanel descriptionPanel;
	private JLabel descriptionLabel, instructionLabel;

	/* west panel and labels */
	private JPanel labelPanel;
	private JLabel movingFromLabel, movingToLabel;

	/* center buttons, slider */
	private JPanel sliderPanel, choicePanel;
	private JFormattedTextField numMovingField;
	private JSlider troopSlider;
	private JButton minButton, maxButton;

	/* south menu options */
	private JPanel menuPanel;
	private JButton moveButton, quitButton;

	/* defender, attacker information */
	private Territory movingFrom, movingTo;

	/**
	 * Create the panel.
	 * 
	 */
	public MoveTroopsScreenPanel( MoveTroopsScreenHandler handler )
	{
		this.handler = handler;
		this.setLayout( new BorderLayout( 10, 10 ) );
		// TODO: remove
		// dummyTerritory();

		descriptionPanel = getDescriptionPanel();
		this.add( descriptionPanel, BorderLayout.NORTH );

		labelPanel = getLabelPanel();
		this.add( labelPanel, BorderLayout.WEST );

		sliderPanel = getSliderPanel();
		this.add( sliderPanel, BorderLayout.CENTER );

		menuPanel = getMenuPanel();
		this.add( menuPanel, BorderLayout.SOUTH );

		this.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

		( (MoveTroopsScreenHandler)handler ).addView( this );

		// TODO: remove
		// initPanel(3);
	}

	/**
	 * Passes move data from panel through handler to the model to call move on
	 * the current player.
	 */
	public void callMoveTroops()
	{
		handler.moveTroops( movingFrom, movingTo,
				Integer.parseInt( numMovingField.getText() ) );

	}

	// TODO tie this in with good transition attack-> move panel, and fortify
	// i,e., add territories moving from, to here
	private void initPanel( int minToMove )
	{
		int maxToMove = movingFrom.getNumArmies() - 1;
		errorMessage = "Please enter an integer from "
				+ minToMove + " to " + maxToMove;

		troopSlider.setMinimum( minToMove );
		troopSlider.setMaximum( maxToMove );
		troopSlider.setMajorTickSpacing( 2 );
		troopSlider.setPaintTicks( true );
		troopSlider.setPaintLabels( true );
		troopSlider.setValue( maxToMove );

		/* add action to check for proper input */

		// textField.getActionMap().put("check", anAction)

		// allow user to input a number within specified range */
		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		NumberFormatter formatter = new NumberFormatter( numberFormat );
		formatter.setMinimum( minToMove );
		formatter.setMaximum( maxToMove );
		DefaultFormatterFactory nfFactory =
				new DefaultFormatterFactory( formatter );

		numMovingField.setFormatterFactory( nfFactory );

		numMovingField.getInputMap().put( KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, 0 ), "verify" );
		numMovingField.getActionMap().put(
				"verify", handler.getVerifyTextAction() );

	}

	public void handleValidInput()
	{
		moveButton.setEnabled( true );
		moveButton.requestFocus();
	}

	public void handleInvalidInput()
	{
		numMovingField.selectAll();
		moveButton.setEnabled( false );
		JOptionPane.showMessageDialog( this, errorMessage,
				"Invalid troop selection",
				JOptionPane.ERROR_MESSAGE );
	}

	public void setMaxTroops()
	{
		setTextField( troopSlider.getMaximum() );
	}

	public void setMinTroops()
	{
		setTextField( troopSlider.getMinimum() );
	}

	public void setTextField( int newValue )
	{
		numMovingField.setValue( newValue );
		troopSlider.setValue( newValue );
		handleValidInput();
	}

	/**
	 * <b>Attack phase:</b> Initializes move panel with the information provided
	 * for the attack phase.
	 * 
	 * @param movingFrom territory troops are moving from
	 * @param movingTo territory troops are moving to
	 */
	public void moveTroops( Territory movingFrom, Territory movingTo,
			int minToMove )
	{
		quitButton.setVisible( false );
		this.movingFrom = movingFrom;
		this.movingTo = movingTo;
		setLabelText();
		initPanel( minToMove );
		setDescriptionPanel();
	}

	/**
	 * <b>Fortify phase:</b> Initializes move panel with the information for the
	 * fortify phase, making the quit button available for the player.
	 * 
	 * @param movingFrom territory troops are moving from
	 * @param movingTo territory troops are moving to
	 */
	public void moveTroops( Territory movingFrom, Territory movingTo )
	{
		moveTroops( movingFrom, movingTo, 1 );
		quitButton.setVisible( true );

	}

	/**
	 * @return the description panel for the top of the panel
	 */
	private JPanel getDescriptionPanel()
	{
		JPanel result = new JPanel( new BorderLayout() );
		descriptionLabel = new JLabel();

		descriptionLabel.setBorder(
				BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );

		result.add( descriptionLabel, BorderLayout.CENTER );

		return result;
	}

	private void setDescriptionPanel()
	{
		descriptionLabel.setText( "<html>"
				+ "<font size = \"4\">Moving from "
				+ "<font size = \"5\">" + movingFrom.getName()
				+ "<font size = \"4\">" + " to "
				+ "<font size = \"5\">" + movingTo.getName()
				+ "</html>" );
	}

	/**
	 * @return the label panel for center west.
	 */
	private JPanel getLabelPanel()
	{
		JPanel result = new JPanel( new GridLayout( 2, 1 ) );
		movingFromLabel = new JLabel();
		movingToLabel = new JLabel();

		result.add( movingFromLabel );
		result.add( movingToLabel );

		return result;
	}

	private void setLabelText()
	{

		movingFromLabel.setText( "<html>" + movingFrom.getName()
				+ "<br>Troops: " + movingFrom.getNumArmies() + "</html>" );

		movingToLabel.setText( "<html>" + movingTo.getName()
				+ "<br>Troops: " + movingTo.getNumArmies() + "</html>" );

	}

	private JPanel getSliderPanel()
	{

		JPanel result = new JPanel( new BorderLayout() );
		JPanel innerPanel = new JPanel();
		choicePanel = new JPanel();

		minButton = new JButton( "Min" );
		minButton.addActionListener( handler );

		maxButton = new JButton( "Max" );
		maxButton.addActionListener( handler );

		/* value will be set in init based on territories */
		troopSlider = new JSlider();
		troopSlider.addChangeListener( handler );
		numMovingField = new JFormattedTextField();
		// textField.addActionListener( handler );
		numMovingField.setActionCommand( "textField" );
		numMovingField.setColumns( 3 );
		numMovingField.setAlignmentX( CENTER_ALIGNMENT );

		innerPanel.add( minButton );
		innerPanel.add( troopSlider );
		innerPanel.add( maxButton );
		result.add( innerPanel, BorderLayout.CENTER );

		instructionLabel = new JLabel( "Troops to move:" );
		instructionLabel.setHorizontalAlignment( JLabel.CENTER );
		choicePanel.add( instructionLabel );
		choicePanel.add( numMovingField );

		result.add( choicePanel, BorderLayout.NORTH );

		return result;
	}

	private JPanel getMenuPanel()
	{
		JPanel result = new JPanel();

		moveButton = new JButton( "Move" );
		moveButton.addActionListener( handler );
		result.add( moveButton );

		quitButton = new JButton( "Quit" );
		quitButton.addActionListener( handler );
		result.add( quitButton );
		quitButton.setVisible( false );

		return result;
	}


	/**
	 * @param movingTo Territory moving to
	 */
	public void setMovingTo( Territory movingTo )
	{
		this.movingTo = movingTo;
	}

	/**
	 * @param movingFrom Territory moving from
	 */
	public void setMovingFrom( Territory movingFrom )
	{
		this.movingFrom = movingFrom;
	}

}
