/**
 * CSCI 2120 Fall 2014
 * Risk class AttackScreenPanel
 * @author Shane McCulley
 * @date Dec 4, 2014
 **/
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import classes.Player;
import classes.Territory;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.BevelBorder;

import engine.RiskUtils;

@SuppressWarnings( "serial" )
public class AttackScreenPanel extends JPanel
{

	private static final int MAX_ATTACK_DICE = 3;
	private static final int MAX_DEFEND_DICE = 2;
	private static final String IMAGE_NAME = "attack.png";

	private ActionListener handler;
	
	/* north panel description */
	private JPanel descriptionPanel;
	private JLabel descriptionLabel, instructionLabel;

	/* center west labels */
	private JPanel labelPanel;
	private JLabel attackerLabel, defenderLabel;

	/* center east buttons */
	private JPanel dicePanel;
	private List<JToggleButton> attackDiceGroup;
	private JToggleButton[] attackDice;
	private List<JToggleButton> defendDiceGroup;
	private JToggleButton[] defendDice;

	/* south menu options */
	private JPanel menuPanel;
	private JButton attackButton, backButton;

	/* icon panel */
	private JPanel iconPanel;
	private JLabel iconLabel;

	/* defender, attacker information */
	private Territory attacker;
	private Territory defender;
	private Player attackingPlayer;

	/* dice information */
	private int numAttacking;
	private int attackDiceEnabled;
	private int numDefending;
	private int defendDiceEnabled;

	/**
	 * Create the panel.
	 */
	public AttackScreenPanel( ActionListener handler )
	{
		this.handler = handler;

		/* north panel description */
		descriptionPanel = getDescriptionPanel();
		this.setLayout( new BorderLayout( 10, 10 ) );
		this.add( descriptionPanel, BorderLayout.NORTH );

		/* west panel country/player name, troop values */
		labelPanel = getLabelPanel();
		this.add( labelPanel, BorderLayout.WEST );

		/* east panel dice */
		dicePanel = getDicePanel();
		this.add( dicePanel, BorderLayout.EAST );

		/* south panel menu buttons */
		menuPanel = getMenuPanel();
		this.add( menuPanel, BorderLayout.SOUTH );

		iconPanel = new JPanel();
		iconLabel = getAttackIcon();
		iconLabel.setBorder( new EmptyBorder( 15, 33, 0, 0 ) );
		iconPanel.add( iconLabel );
		this.add( iconPanel, BorderLayout.CENTER );

		this.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
		( (AttackScreenHandler)handler ).addView( this );
		// this.setPreferredSize( RiskUtils.GAME_SIZE );
	}

	/**
	 * Initializes attacking panel with necessary information.
	 * 
	 * @param attackingFrom Territory currentPlayer is attacking from.
	 * @param attackingTo Territory currentPlayer is attacking.
	 */
	public void attack( Territory attackingFrom, Territory attackingTo )
	{
		attackingPlayer = attackingFrom.getOccupant();
		attacker = attackingFrom;
		defender = attackingTo;

		/* set default to maximum, constrain by available armies */
		this.numAttacking = attacker.getNumArmies() > MAX_ATTACK_DICE ?
				MAX_ATTACK_DICE : attacker.getNumArmies() - 1;
		this.numDefending = defender.getNumArmies() >= MAX_DEFEND_DICE ?
				MAX_DEFEND_DICE : defender.getNumArmies();
		attackDiceEnabled = MAX_ATTACK_DICE;
		defendDiceEnabled = MAX_DEFEND_DICE;

		setDescriptionLabel();
		setLabelText();
		setDicePanel();
		attackButton.setEnabled( true );
	}

	/**
	 * @return the description panel for the top of the panel
	 */
	private JPanel getDescriptionPanel()
	{
		JPanel result = new JPanel( new BorderLayout() );
		descriptionLabel = new JLabel( "", JLabel.CENTER );
		descriptionLabel.setBorder(
				BorderFactory.createBevelBorder( BevelBorder.LOWERED ) );

		result.add( descriptionLabel, BorderLayout.CENTER );

		return result;
	}

	private void setDescriptionLabel()
	{
		descriptionLabel.setText(
				"<html>"
						+ "<font size = \"4\">Attacking from "
						+ "<font size = \"5\">" + attacker.getName()
						+ "<font size = \"4\">" + " to "
						+ "<font size = \"5\">" + defender.getName()
						+ "</html>" );
	}

	/**
	 * @return the label panel for center west.
	 */
	private JPanel getLabelPanel()
	{
		JPanel result = new JPanel( new GridLayout( 2, 1 ) );
		attackerLabel = new JLabel();
		defenderLabel = new JLabel();

		result.add( attackerLabel );
		result.add( defenderLabel );

		return result;
	}

	private void setLabelText()
	{
		
		attackerLabel.setText( "<html>Attacker:  " + attacker.getNumArmies()
				+ " troops<br>"
				+ "Player: " + attacker.getOccupant().getName() + "</html>" );
		defenderLabel.setText( "<html>Defender: " + defender.getNumArmies()
				+ " troops<br>"
				+ "Player: " + defender.getOccupant().getName() + "</html>" );

	}

	private void setDicePanel()
	{
		int attackerArmies = attacker.getNumArmies();
		int defenderArmies = defender.getNumArmies();

		for ( int i = 0; i < MAX_ATTACK_DICE; i++ )
		{

			/* default state of all dice selected if possible */
			if ( attackerArmies > i + 1 )
			{
				attackDice[i].setEnabled( true );
				attackDice[i].setSelected( true );
				attackDice[i].setText( "" );
			}
			else
			{
				attackDice[i].setText( "" );
				attackDice[i].setSelected( false );
				attackDice[i].setEnabled( false );
				attackDiceEnabled--;
			}
		}

		for ( int i = 0; i < MAX_DEFEND_DICE; i++ )
		{

			/* default state of all dice selected if possible */
			if ( defenderArmies > i )
			{
				defendDice[i].setText( "" );
				defendDice[i].setEnabled( true );
				defendDice[i].setSelected( true );

			}

			else
			{
				defendDice[i].setText( "" );
				defendDice[i].setSelected( false );
				defendDice[i].setEnabled( false );
				defendDiceEnabled--;
			}
		}
	}

	private JPanel getDicePanel()
	{
		attackDice = new JToggleButton[MAX_ATTACK_DICE];
		defendDice = new JToggleButton[MAX_DEFEND_DICE];
		JPanel result = new JPanel( new BorderLayout() );
		JPanel attackPanel = new JPanel();
		JPanel defendPanel = new JPanel(
				new FlowLayout( FlowLayout.RIGHT ) );

		attackDiceGroup = new ArrayList<JToggleButton>( MAX_ATTACK_DICE );
		defendDiceGroup = new ArrayList<JToggleButton>( MAX_DEFEND_DICE );

		for ( int i = 0; i < MAX_ATTACK_DICE; i++ )
		{
			attackDice[i] = new JToggleButton();
			// attackDice[i].setPreferredSize( new Dimension
			// ( TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE ) );
			attackDice[i].setText( Integer.toString( i + 1 ) );
			attackDice[i].setPreferredSize( attackDice[i].getPreferredSize() );
			attackDiceGroup.add( attackDice[i] );
			attackPanel.add( attackDice[i] );

			attackDice[i].setActionCommand( Integer.toString( i + 1 ) );
			attackDice[i].setName( "attack" );

			/*
			 * default state of all dice selected if possible if( attackerArmies
			 * > i + 1 ) attackDice[i].setSelected( true ); else {
			 * attackDice[i].setEnabled( false ); attackDiceEnabled--; }
			 */
			attackDice[i].addActionListener( handler );

		}

		for ( int i = 0; i < MAX_DEFEND_DICE; i++ )
		{
			defendDice[i] = new JToggleButton();
			// defendDice[i].setPreferredSize( new Dimension
			// ( TOGGLE_BUTTON_SIZE, TOGGLE_BUTTON_SIZE ) );
			defendDice[i].setText( Integer.toString( i + 1 ) );
			defendDiceGroup.add( defendDice[i] );
			defendPanel.add( defendDice[i] );
			defendDice[i].setPreferredSize( defendDice[i].getPreferredSize() );
			/* Give name since text will display dice results */
			defendDice[i].setActionCommand( Integer.toString( i + 1 ) );
			defendDice[i].setName( "defend" );

			/*
			 * default state of all dice selected if possible if( defenderArmies
			 * > i ) defendDice[i].setSelected( defenderArmies > i ); else {
			 * defendDice[i].setEnabled( false ); defendDiceEnabled--; }
			 * defendDice[i].addActionListener( handler );
			 */

		}

		result.add( attackPanel, BorderLayout.CENTER );
		result.add( defendPanel, BorderLayout.SOUTH );

		instructionLabel = new JLabel( "Dice to roll" );
		instructionLabel.setHorizontalAlignment( JLabel.RIGHT );
		result.add( instructionLabel, BorderLayout.NORTH );

		return result;

	}

	private JPanel getMenuPanel()
	{
		JPanel result = new JPanel();

		attackButton = new JButton( "Attack" );
		attackButton.addActionListener( handler );

		backButton = new JButton( "Quit" );
		backButton.addActionListener( handler );

		result.add( attackButton );
		result.add( backButton );
		backButton.setPreferredSize( attackButton.getPreferredSize() );

		return result;
	}

	private JLabel getAttackIcon()
	{

		/* place image icon onto JLabel and return at scaled size */
		return RiskUtils.getScaledIcon( IMAGE_NAME, 100, 100 );
	}

	public void setAttackDiceGroup( int numDice )
	{
		for ( int i = 0; i < attackDiceGroup.size(); i++ )
		{
			/* set selected up to numDice, set false afterwards */
			attackDiceGroup.get( i ).setSelected( i < numDice );
		}

		this.numAttacking = numDice;

	}

	public void setDefendDiceGroup( int numDice )
	{

		for ( int i = 0; i < defendDiceGroup.size(); i++ )
		{
			/* set selected up to numDice, set false afterwards */
			defendDiceGroup.get( i ).setSelected( i < numDice );
		}

		this.numDefending = numDice;
	}

	// TODO: Possibly remove
	public Territory getAttacker()
	{
		return this.attacker;
	}

	public Territory getDefender()
	{
		return this.defender;
	}

	public void updateResults( int[] diceResults )
	{
		updateDiceText( diceResults );
		boolean capturedTerritory =
				attackingPlayer.attack( attacker, defender, diceResults );

		if ( capturedTerritory )
		{
			attackButton.setEnabled( false );
			setDefendDiceGroup( 0 );
			attackDiceEnabled = 0;
			JOptionPane.showMessageDialog( this, "You captured the territory!" );
			handler.actionPerformed( new ActionEvent( this,
					ActionEvent.ACTION_PERFORMED, "captured" ) );
		}
		else
		{
			checkAttackerBounds();
			checkDefenderBounds();
			setLabelText();
		}

		revalidate();
		repaint();

	}

	/**
	 * Handles parsing results and updating the view with highlighting of winner
	 * and losers.
	 * 
	 * @param diceResults
	 */
	private void updateDiceText( int[] diceResults )
	{
		/* highest attack rolls start at separator-1 and decrease to index 0 */
		int nextHighAttack = numAttacking - 1;

		/* high defense rolls start at length - 1 and decrease to separator */
		int nextHighDefend = diceResults.length - 1;

		while ( diceResults[nextHighDefend] != 0 && nextHighAttack >= 0 )
		{
			int attackRoll = diceResults[nextHighAttack];
			int defendRoll = diceResults[nextHighDefend];

			attackDiceGroup.get( nextHighAttack ).setText( Integer.toString(
					attackRoll ) );
			defendDiceGroup.get( nextHighDefend - numAttacking - 1 ).setText(
					Integer.toString(
							defendRoll ) );

			/* defense wins in a tie. Color green = won, red = lost */
			if ( defendRoll >= attackRoll )
			{

				attackDiceGroup.get( nextHighAttack ).setBackground( Color.RED );
				attackDiceGroup.get( nextHighAttack ).setContentAreaFilled(
						false );
				attackDiceGroup.get( nextHighAttack ).setOpaque( true );

				defendDiceGroup.get( nextHighDefend - numAttacking - 1 )
						.setBackground( Color.GREEN );
				defendDiceGroup.get(
						nextHighDefend - numAttacking - 1 )
						.setContentAreaFilled( false );
				defendDiceGroup.get(
						nextHighDefend - numAttacking - 1 ).setOpaque( true );

			}
			else
			{
				attackDiceGroup.get( nextHighAttack ).setBackground(
						Color.GREEN );
				attackDiceGroup.get( nextHighAttack ).setContentAreaFilled(
						false );
				attackDiceGroup.get( nextHighAttack ).setOpaque( true );

				defendDiceGroup.get(
						nextHighDefend - numAttacking - 1 )
						.setBackground( Color.RED );
				defendDiceGroup.get(
						nextHighDefend - numAttacking - 1 )
						.setContentAreaFilled( false );
				defendDiceGroup.get(
						nextHighDefend - numAttacking - 1 )
						.setOpaque( true );
			}
			nextHighDefend--;
			nextHighAttack--;
		}

		/* take care of the remaining attack rolls, no color change */
		while ( nextHighAttack >= 0 )
		{
			attackDiceGroup.get( nextHighAttack ).setText( Integer.toString(
					diceResults[nextHighAttack] ) );
			nextHighAttack--;
		}

		/* take care of the remaining attack rolls, no color change */
		while ( diceResults[nextHighDefend] != 0 )
		{
			defendDiceGroup.get( nextHighDefend - numAttacking - 1 )
					.setText( Integer.toString( diceResults[nextHighDefend] ) );
			nextHighDefend--;
		}

		attackButton.setEnabled( false );
		setLabelText();
		/* pause for 1.5 seconds, revert background */
		Timer t = new Timer( 1000, new ActionListener()
		{
			@Override
			public void actionPerformed( ActionEvent e )
			{
				revertBackground( attackDiceGroup );
				revertBackground( defendDiceGroup );

				/* must have attack dice enabled to attack */
				attackButton.setEnabled( attackDiceEnabled > 0 );

			}
		} );
		t.setRepeats( false );
		t.start();

	}

	private void checkDefenderBounds()
	{
		int defenderArmies = defender.getNumArmies();

		while ( defenderArmies < defendDiceEnabled )
		{
			JToggleButton defendDie = defendDice[defendDiceEnabled - 1];
			if ( defendDie.isSelected() )
			{
				defendDie.setSelected( false );
				numDefending--;
			}

			defendDie.setEnabled( false );

			defendDiceEnabled--;
		}
	}

	/**
	 * Ensures attacker has appropriate number of troops to attack. Must leave
	 * at least 1 troop behind, so n+1 troops needed to attack with n dice.
	 */
	private void checkAttackerBounds()
	{
		int attackerArmies = attacker.getNumArmies();
		
		while ( attackerArmies < attackDiceEnabled + 1 )
		{
			JToggleButton attackDie = attackDice[attackDiceEnabled - 1];
			
			if ( attackDie.isSelected() )
			{
				attackDie.setSelected( false );
				numAttacking--;
			}

			attackDie.setEnabled( false );

			attackDiceEnabled--;
		}

		if ( attackDiceEnabled == 0 )
		{
			attackButton.setEnabled( false );
		}
	}

	private void revertBackground( List<JToggleButton> list )
	{
		Color defaultColor = javax.swing.UIManager
				.getColor( "Panel.background" );
		for ( JToggleButton jtb : list )
		{
			jtb.setBackground( defaultColor );
			jtb.setContentAreaFilled( true );
		}
		attackButton.requestFocus();
	}

	/**
	 * @return the number of selected attack dice
	 */
	public int getNumAttacking()
	{
		return this.numAttacking;
	}

	/**
	 * @return the number of selected defend dice
	 */
	public int getNumDefending()
	{
		return this.numDefending;
	}

	public void setAttacker( Territory attacker )
	{
		this.attacker = attacker;
	}

	public void setDefender( Territory defender )
	{
		this.defender = defender;
	}

}
