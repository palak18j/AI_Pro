package engine;

import classes.RiskGame;
import gui.GameGUI;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AutomatedGameEngine {
    private RiskGameEngine engine;
    private GameGUI gui;
//    private Agent players;

    public AutomatedGameEngine(RiskGameEngine engine, GameGUI gui) {
        this.engine = engine;
        this.gui = gui;
    }

    void createNewGame() {
        engine.createNewGame();
    }

    void createPlayers() {
        Map<String, Color> playerInfo = new HashMap<>();
        playerInfo.put("red", Color.RED);
        playerInfo.put("blue", Color.BLUE);
        playerInfo.put("green", Color.GREEN);
        engine.createPlayers(playerInfo, "automated");
    }

    void addTerritories() {
        RiskGame game = this.engine.getGame();

        while (!game.getUnselectedTerritoriesList().isEmpty()) {
            String territory = "";

            for (String key : game.getUnselectedTerritoriesList().keySet()) {
                territory = key;
                break;
            }

            engine.pickInitialTerritory(territory);
        }
    }
}
