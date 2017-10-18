/**
 * CSCI 2120 Fall 2014
 * Risk Game class RiskGame
 *
 * @author Shane McCulley
 * @date November 29, 2014
 **/

package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

@SuppressWarnings("serial")
public class StartScreenPanel extends JPanel {

    private JFileChooser fileChooser;
    private ActionListener screenHandler;

    /* center panel label and buttons */
    private JPanel centerPanel;
    private JButton exitButton;

    private JLabel createNewGameLabel;
    private JButton createNewGameButton;

    private JLabel loadSavedGameLabel;
    private JButton loadSavedGameButton;

    /* welcome label */
    private JLabel welcomeLabel;


    public StartScreenPanel(ActionListener handler) {
        this.screenHandler = handler;
        this.setLayout(new BorderLayout());

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        this.setName("Start Screen");

		/* new game label and button */
        createNewGameLabel = new JLabel("Creates a new Risk Game: ");
        centerPanel.add(createNewGameLabel, constraints);

        createNewGameButton = new JButton("New Game");
        createNewGameButton.setActionCommand("createNewGame");
        createNewGameButton.addActionListener(this.screenHandler);
        centerPanel.add(createNewGameButton, constraints);

		/* load game label and button */
        constraints.gridy = 1;        // add to 2nd row
        loadSavedGameLabel = new JLabel("Loads a Risk Game saved to file: ");
        centerPanel.add(loadSavedGameLabel, constraints);

        loadSavedGameButton = new JButton("Load Game");
        loadSavedGameButton.setActionCommand("loadSavedGame");
        loadSavedGameButton.addActionListener(this.screenHandler);
        centerPanel.add(loadSavedGameButton, constraints);

		/* add exit button to 3rd row, 2nd column */
        constraints.gridy = 2;
        constraints.gridx = 1;
        exitButton = new JButton("Exit Game");
        exitButton.setActionCommand("exitGame");
        exitButton.addActionListener(this.screenHandler);
        exitButton.setPreferredSize(loadSavedGameButton.getPreferredSize());
        centerPanel.add(exitButton, constraints);

        this.add(centerPanel, BorderLayout.CENTER);

        welcomeLabel = new JLabel("<html><font size = 7>RiskGame</html");
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);

        this.add(welcomeLabel, BorderLayout.NORTH);

    }


    /**
     * Not implemented:  Opens a file chooser and takes no action afterwards.
     */
    protected void chooseOpenFile() {

        this.fileChooser = new JFileChooser(new File(
                System.getProperty("user.dir")));

        int status = fileChooser.showOpenDialog(this);

        if (status == JFileChooser.APPROVE_OPTION)
            System.out.println("A file was chosen");

        else
            System.out.println("Cancelled by user");


    }

}
