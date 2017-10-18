/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import gui.GameGUI;

import javax.swing.*;

/**
 * @author lenovo
 */
public class NewClass {
    public static void main(String args[]) {
        System.out.println("hey");
        System.out.println("hhh");
            /* start program on EDT */
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("here");
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {
        System.out.println("invoked here");

        RiskGameEngine gameEngine = new RiskGameEngine();

        GameGUI gui = new GameGUI(gameEngine);

        gameEngine.addObserver(gui);

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.setSize(RiskUtils.GAME_SIZE);
        gui.setResizable(false);
        gui.setLocation(RiskUtils.getStartScreenPosition());
        gui.setVisible(true);
    }
}
