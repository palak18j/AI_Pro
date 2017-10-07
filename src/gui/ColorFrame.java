/**
 * CSCI 2120 Fall 2014
 * Risk class ColorFrame
 * @author Shane McCulley
 * @date Dec 6, 2014
 **/
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Creates a frame to choose the player's color.
 */
@SuppressWarnings( "serial" )
public class ColorFrame extends JPanel
{
	public static final Color RED = new Color( 228, 26, 28 );
	public static final Color BLUE = new Color( 55, 126, 184 );
	public static final Color GREEN = new Color( 77, 175, 74 );
	public static final Color PURPLE = new Color( 152, 78, 163 );
	public static final Color ORANGE = new Color( 255, 127, 0 );
	public static final Color YELLOW = new Color( 255, 255, 51 );
	public static final Color BROWN = new Color( 166, 86, 40 );
	public static final Color PINK = new Color( 247, 129, 191 );
	public static final Color GRAY = new Color( 153, 153, 153 );
	public static final Color DEFAULT_COLOR =
			UIManager.getColor( "Panel.background" );

	private JPanel colorPanel;
	private JButton redButton, blueButton, greenButton, purpleButton,
			orangeButton, yellowButton, brownButton, pinkButton, grayButton;
	private HashMap<Color, JButton> colorMap;

	public ColorFrame( ActionListener handler )
	{
		this.setLayout( new BorderLayout() );
		initComponents( handler );
		addToPanel();

		this.add( colorPanel, BorderLayout.CENTER );
	}

	private void initComponents( ActionListener handler )
	{
		colorPanel = new JPanel( new GridLayout( 3, 3, 5, 5 ) );
		colorMap = new HashMap<Color, JButton>();

		redButton = new JButton();
		redButton.setActionCommand( "colorChosen" );
		redButton.addActionListener( handler );
		redButton.setBackground( RED );
		redButton.setText( "Red" );
		colorMap.put( RED, redButton );

		blueButton = new JButton();
		blueButton.setActionCommand( "colorChosen" );
		blueButton.addActionListener( handler );
		blueButton.setBackground( BLUE );
		blueButton.setText( "Blue" );
		colorMap.put( BLUE, blueButton );

		greenButton = new JButton();
		greenButton.setActionCommand( "colorChosen" );
		greenButton.addActionListener( handler );
		greenButton.setBackground( GREEN );
		greenButton.setText( "Green" );
		colorMap.put( GREEN, greenButton );

		purpleButton = new JButton();
		purpleButton.setActionCommand( "colorChosen" );
		purpleButton.addActionListener( handler );
		purpleButton.setBackground( PURPLE );
		purpleButton.setText( "Purple" );
		colorMap.put( PURPLE, purpleButton );

		orangeButton = new JButton();
		orangeButton.setActionCommand( "colorChosen" );
		orangeButton.addActionListener( handler );
		orangeButton.setBackground( ORANGE );
		orangeButton.setText( "Orange" );
		colorMap.put( ORANGE, orangeButton );

		yellowButton = new JButton();
		yellowButton.setActionCommand( "colorChosen" );
		yellowButton.addActionListener( handler );
		yellowButton.setBackground( new Color( 255, 255, 51 ) );
		yellowButton.setText( "Yellow" );
		colorMap.put( YELLOW, yellowButton );

		brownButton = new JButton();
		brownButton.setActionCommand( "colorChosen" );
		brownButton.addActionListener( handler );
		brownButton.setBackground( new Color( 166, 86, 40 ) );
		brownButton.setText( "Brown" );
		colorMap.put( BROWN, brownButton );

		pinkButton = new JButton();
		pinkButton.setActionCommand( "colorChosen" );
		pinkButton.addActionListener( handler );
		pinkButton.setBackground( new Color( 247, 129, 191 ) );
		pinkButton.setText( "Pink" );
		colorMap.put( PINK, pinkButton );

		grayButton = new JButton();
		grayButton.setActionCommand( "colorChosen" );
		grayButton.addActionListener( handler );
		grayButton.setBackground( new Color( 153, 153, 153 ) );
		grayButton.setText( "Gray" );
		colorMap.put( GRAY, grayButton );
	}

	/**
	 * Enables and sets the default color of a previously-chosen button.
	 * 
	 * @param buttonColor The default color of the button, used to look up the
	 *        button from the map.
	 */
	public void setDefaultColor( Color buttonColor )
	{
		JButton previousChoice = colorMap.get( buttonColor );
		previousChoice.setBackground( buttonColor );
		previousChoice.setEnabled( true );
	}

	/**
	 * Sets the default color of the JPanel and enables the button associated
	 * with that color.
	 * 
	 * @param colorPanel the JPanel containing the color of button being
	 *        released.
	 */
	public void setDefaultColor( JPanel colorPanel )
	{
		if ( colorPanel.getBackground() != DEFAULT_COLOR )
			setDefaultColor( colorPanel.getBackground() );

		colorPanel.setBackground( DEFAULT_COLOR );
	}

	private void addToPanel()
	{
		colorPanel.add( redButton );
		colorPanel.add( blueButton );
		colorPanel.add( greenButton );
		colorPanel.add( purpleButton );
		colorPanel.add( orangeButton );
		colorPanel.add( yellowButton );
		colorPanel.add( brownButton );
		colorPanel.add( pinkButton );
		colorPanel.add( grayButton );
	}

	public static void main( String[] args )
	{
                //System.out.println("here   ");
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public void run()
			{
				createAndShowGUI();
			}
		} );
	}

	public static void createAndShowGUI()
	{
		javax.swing.JFrame jf = new javax.swing.JFrame();
		ColorFrame frame = new ColorFrame( null );
		jf.getContentPane().add( frame );
		jf.pack();
		jf.setVisible( true );
		jf.setDefaultCloseOperation( javax.swing.JFrame.EXIT_ON_CLOSE );
	}
}
