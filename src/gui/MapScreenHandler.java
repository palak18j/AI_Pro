/**
 * CSCI 2120 Fall 2014
 * Risk class MapScreenPanelHandler
 *
 * @author Shane McCulley
 * @date Dec 6, 2014
 **/
package gui;

import classes.Player;
import classes.Territory;
import engine.RiskGameEngine;
import engine.RiskGameEngine.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class MapScreenHandler implements ActionListener {

    private RiskGameEngine model;
    private MapScreenPanelTest view;
    private boolean initialized;

    public MapScreenHandler(RiskGameEngine model) {
        this.model = model;
        this.initialized = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void actionPerformed(ActionEvent event) {
        Component source = (Component) event.getSource();
        if (source instanceof JComboBox) {
            if (source.getName().equals("actionFromBox"))
                handleActionFrom((JComboBox<String>) source, event);
            else if (source.getName().equals("actionToBox"))
                handleActionTo((JComboBox<String>) source, event);
        }

		/* button event received */
        else {
            String command = event.getActionCommand();
			/* sets state to fortify and notify observers */
            if (command.equals("endTurn")) {
                if (model.getState() == State.attack)
                    model.fortify();
                else if (model.getState() == State.fortify)
                    model.endTurn();
            } else if (command.equals("openCards")) {
                view.showCards(model.getCurrentPlayer(), false);
            }
        }
    }

    private void updateLabel() {
        /*
         * continue while there are territories to assign
         * else all territories have been taken, advance to second stage
         */

        if (model.getGame().getUnselectedTerritoriesList().size() > 0) {
            Player currentPlayer = model.getNextPlayer();
            view.setLabelAssignTerritories(currentPlayer);
            view.setNextPlayer(currentPlayer);
        } else {
            model.assignArmies();
        }
    }

    public void repaintTerritories() {
        if (model.getState().equals(State.assignTerritories)) {
            Map<String, Territory> territoriesList = model.getGame().getTerritoriesList();

            for (Territory territory : territoriesList.values()) {
                if (territory.getOccupant() != null) {
                    view.addCircle(territory.getName());
                }
            }

            updateLabel();
        }
    }

    /**
     * Handles the logic dealing with comboBox events coming from actionFromBox.
     * These actions involve player selecting his own territory to either
     * reinforce troops or select for attack.
     *
     * @param comboBox the comboBox the event originated from
     * @param event the event generated from selection
     */
    private void handleActionFrom(JComboBox<String> comboBox, ActionEvent event) {
        
        System.out.println("CAME to handleActionFrom");
        String territory = ((String) comboBox.getSelectedItem());
        State state = model.getState();
		
		/* assign a territory, get next player */
        if (state == State.assignTerritories) {
            
            model.addTerritory(territory);
            comboBox.removeItem(comboBox.getSelectedItem());
            view.addCircle(territory);
			
			updateLabel();
        }
		
		/* assign 1 army to 1 territory, get next player */
        else if (state == State.assignArmies) {
			/* increase troop in territory by 1, decrease currentPlayer by 1*/
            System.out.println("111");
            model.incrementTroops(territory);
            view.repaint();

            Player nextPlayer = model.getNextPlayer();

            if (nextPlayer.getUnplacedArmies() > 0)
                view.assignArmies(nextPlayer);
            else {
                model.placeArmies();
            }
        }
		/* place all armies remaining and then begin turn */
        else if (state == State.placeArmies) {
            System.out.println("222:"+territory);
            model.incrementTroops(territory);
            //view.repaint();
            view.setLabelPlaceArmies(model.getCurrentPlayer());
			
			/* all armies placed, change to attack phase  */
            if (model.getCurrentPlayer().getUnplacedArmies() == 0)
                model.attack();
        }
		
		/* selecting territory to attack from, load adjacencies */
        else if (state == State.attack) {
            System.out.println("333");
            view.initActionToBox(territory);
        }
		
		/* populate the actionToBox with friendly adjacent territories */
        else if (state == State.fortify) {
            view.initFortifyToChoices(territory);
        }

    }

    /**
     * This box should only fire an event when the player is attacking
     * another player or fortifying one of his provinces.
     *
     * @param comboBox the comboBox the event originated from
     * @param event the event generated from selection
     */
    private void handleActionTo(JComboBox<String> comboBox, ActionEvent event) {
        State state = model.getState();
		/* open attack screen between the 2 territories */
        if (state == State.attack) {
            view.attack();
        }
		/* open move screen between 2 territories */
        else if (state == State.fortify) {
            view.fortify();
        }
    }

    public boolean hasInitialized() {
        return initialized;
    }
    /**
     * Begins the game by initializing map to allow players to select
     * territories.
     */
    public void initializeMap() {
        Player currentPlayer = model.getNextPlayer();
        view.setMap(model.getGame().getTerritoriesList());
        view.setNextPlayer(currentPlayer);
        view.setLabelAssignTerritories(currentPlayer);
        this.initialized = true;
    }

    /**
     * Continues with the assigning armies portion of initialization.
     */
    public void assignArmies() {
        //view.assignArmies(model.getNextPlayer());
        view.assignArmiesAutoInit(model.getNextPlayer(),model);
    }
    
    
    /**
     * Begins a player turn by placing armies
     */
    public void placeArmies() {
        if (model.getCardStatus() == true)
            view.showCards(model.getGame().getCurrentPlayer(), true);
        //   view.placeArmies(model.getCurrentPlayer());
        view.placeArmiesAuto(model.getCurrentPlayer(),model);
    }

    /**
     * Update map data with new troop values, update comboBox with new
     * territory if necessary.
     */
    public void updateMove() {
        Player currentPlayer = model.getCurrentPlayer();
        System.out.println("in handler -updateMove");
        view.updateMove(currentPlayer);
    }

    /**
     * Calls revalidate and repaint on the map.
     */
    public void updateMap() {
        view.updateMap();
    }

    /**
     * Updates actionFromBox with a list of territories player can attack from.
     * Must have at least 2 armies to attack from a territory.
     */
    public void attack() {
        Player currentPlayer = model.getCurrentPlayer();
        view.showAttackBox(currentPlayer);
    }

    /**
     * Add new territory to player's comboBox, show move troops screen.
     */
    public void updateCapturedState() {
        Player currentPlayer = model.getCurrentPlayer();
        currentPlayer.setHasCaptured(true);
        view.showMoveTroopsScreen();
        view.updateAttackBox(currentPlayer);
    }

    /**
     * Only updates possible attack locations from currentPlayer
     */
    public void updateAttackBox() {
        Player currentPlayer = model.getCurrentPlayer();
        view.updateAttackBox(currentPlayer);
    }

    /**
     * State has been set to fortify, populate actionFromBox with all
     * territories a player owns for troop movement.
     */
    public void fortify() {
        Player currentPlayer = model.getCurrentPlayer();
        view.initFortifyFromChoices(currentPlayer);
    }

    /**
     * Adds the mapScreenPanel so that we can call its methods easily.
     *
     * @param view mapScreenPanel to add to the member variables.
     */
    public void addView(MapScreenPanelTest view) {
        this.view = view;
    }

}
