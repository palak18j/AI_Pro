package engine;

import gui.GameGUI;

import javax.swing.*;

public class AutomatedGame {

    public static void main(String[] args) {
        /* start program on EDT */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        RiskGameEngine gameEngine = new RiskGameEngine();

        GameGUI gui = new GameGUI(gameEngine);

        AutomatedGameEngine automatedGameEngine = new AutomatedGameEngine(gameEngine, gui);

        gameEngine.addObserver(gui);

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.setSize(RiskUtils.AUTOMATED_GAME_SIZE);
        gui.setResizable(false);
        gui.setLocation(RiskUtils.getStartScreenPosition());
        gui.setVisible(true);

        automatedGameEngine.createNewGame();
        automatedGameEngine.createPlayers();
        automatedGameEngine.addTerritories();
    }
}
