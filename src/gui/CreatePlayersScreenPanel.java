/**
 * CSCI 2120 Fall 2014
 * Risk Game class CreatePlayersScreenPanel
 *
 * @author Shane McCulley
 * @date November 29, 2014
 **/

package gui;

import engine.RiskUtils;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class CreatePlayersScreenPanel extends JPanel {

    private final Integer[] COMBO_BOX_ITEMS = {3, 4, 5, 6};
    private final int TEXT_FIELD_LENGTH = 20;
    private final int MAX_PLAYERS = 6;
    private final double SCREEN_OFFSET = 0.33;

    /* north panel */
    private JPanel northPanel;
    private JLabel gameNameLabel;
    private JTextField gameNameField;
    private JLabel numPlayersLabel;
    private JComboBox<Integer> numPlayersBox;

    /* center panel*/
    private JPanel playersPanel;

    /* center west */
    private JPanel[] colorPanels;
    private JButton[] colorButtons;
    private JLabel[] playerLabels;

    /* center east */
    private JTextField[] playerFields;

    /* south */
    private JPanel menuButtonPanel;
    private JButton backButton;
    private JButton nextButton;

    /* color chooser */
    private JOptionPane colorPane;
    private ColorFrame colorFrame;
    private JDialog dialog;

    public CreatePlayersScreenPanel(ActionListener handler) {

        this.setLayout(new BorderLayout(0, 15));

		/* initialize components  */
        initLabelsAndButtons(handler);
        initColorChooser(handler);
        initFields((CreatePlayersScreenHandler) handler);
        initComboBox();

        northPanel = getLabelPanel();
        this.add(northPanel, BorderLayout.NORTH);

        playersPanel = getPlayersPanel();
        this.add(playersPanel, BorderLayout.CENTER);


        menuButtonPanel = getMenuButtonPanel(handler);
        this.add(menuButtonPanel, BorderLayout.SOUTH);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(this.getPreferredSize());
		
		/* set the dialog to appear closer to the center of the JFrame */
        dialog.setLocation(RiskUtils.getRelativeScreenLocation(
                SCREEN_OFFSET, SCREEN_OFFSET));

        ((CreatePlayersScreenHandler) handler).setView(this);


    }

    private JPanel getLabelPanel() {
        JPanel innerPanel = new JPanel();

        innerPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        innerPanel.add(gameNameLabel);
        innerPanel.add(gameNameField);

        innerPanel.add(numPlayersLabel);
        innerPanel.add(numPlayersBox);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(innerPanel, BorderLayout.CENTER);

        return labelPanel;
    }

    private JPanel getPlayersPanel() {
        JPanel playersPanel = new JPanel();
        playersPanel.setBorder(new CompoundBorder(
                new EmptyBorder(0, 0, 0, 0),
                BorderFactory.createRaisedBevelBorder()));
        playersPanel.setLayout(new GridLayout(6, 2, 10, 10));
        colorPanels = new JPanel[MAX_PLAYERS];

        for (int i = 0; i < playerLabels.length; i++) {
            //JPanel colorPanel = new JPanel( new GridLayout( 1, 2, 20, 5 ));
            colorPanels[i] = new JPanel(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(0, 0, 0, 5);
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.WEST;
            constraints.weightx = 1;
            colorPanels[i].add(playerLabels[i], constraints);
            playerLabels[i].setPreferredSize(
                    colorButtons[i].getPreferredSize());

            constraints = new GridBagConstraints();
            constraints.gridy = 0;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.gridx = 2;
            constraints.weighty = 1;
            constraints.insets = new Insets(0, 0, 0, 40);
            constraints.fill = GridBagConstraints.VERTICAL;
            colorPanels[i].add(colorButtons[i], constraints);

            playersPanel.add(colorPanels[i]);
            playersPanel.add(playerFields[i]);
        }

        return playersPanel;
    }

    private JPanel getMenuButtonPanel(ActionListener handler) {
        JPanel menuButtonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        backButton = new JButton("Back");
        backButton.addActionListener(handler);
        menuButtonPanel.add(new JLabel());
        menuButtonPanel.add(backButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(handler);
        nextButton.setEnabled(false);
        menuButtonPanel.add(nextButton);
        menuButtonPanel.setPreferredSize(menuButtonPanel.getPreferredSize());

        return menuButtonPanel;
    }

    /**
     * Collects the list of names from the GUI for creation of player
     * objects.
     * @return a List of names.
     */
    public Map<String, Color> getInformation() {
        int numStrings = (int) numPlayersBox.getSelectedItem();

        Map<String, Color> result = new HashMap<String, Color>();

        for (int i = 0; i < numStrings; i++) {
            result.put(playerFields[i].getText(),
                    colorPanels[i].getBackground());
        }

        return result;
    }

    public String getGameName() {
        return gameNameField.getText();
    }

    private void updateComboBox(Object obj) {
        int numPlayers = (int) obj - 1;
        for (int i = 3; i < playerLabels.length; i++) {
            boolean toShow = numPlayers >= i;
            playerLabels[i].setVisible(toShow);
            colorButtons[i].setVisible(toShow);
            playerFields[i].setVisible(toShow);
			
			/* clear fields that are being hidden */
            if (!toShow) {
                colorFrame.setDefaultColor(colorPanels[i]);
                playerFields[i].setText("");
            }
        }

        validateFields();
    }

    /**
     * Called by pressing the choose color button
     */
    public void setColor(int playerIndex) {

        dialog.setVisible(true);
        //
		/* will not fire until a value is chosen in pane */
        dialog.setVisible(false);

        if (!(colorPane.getValue() instanceof Integer)
                && colorPane.getValue() != null) {
            JButton selectedButton = (JButton) colorPane.getValue();
			/* no color has been selected for this player */
            if (colorPanels[playerIndex].getBackground() == ColorFrame.DEFAULT_COLOR) {
                colorPanels[playerIndex].setBackground(selectedButton.getBackground());
                selectedButton.setBackground(Color.DARK_GRAY);
                selectedButton.setEnabled(false);
            }
			
			/* a color has already been chosen */
            else {
				/* reset old choice */
                colorFrame.setDefaultColor(
                        colorPanels[playerIndex].getBackground());
				
				/* set new choice */
                colorPanels[playerIndex].setBackground(
                        selectedButton.getBackground());
            }

        }
		
		/* enable next if all fields complete */
        validateFields();
    }

    /**
     * Determines whether every visible field has data in order to enable
     * next button.
     */
    public void validateFields() {
        boolean isValid = true;

        for (int i = 0; i < (int) numPlayersBox.getSelectedItem(); i++) {
            if (playerFields[i].getText().equals("") ||
                    colorPanels[i].getBackground() == ColorFrame.DEFAULT_COLOR) {
                isValid = false;
            }
        }

        nextButton.setEnabled(isValid);
    }

    /**
     * Initializes the JComboBox and adds an anonymous item listener to update
     * the number of players selected.
     */
    private void initComboBox() {
        numPlayersBox = new JComboBox<Integer>(this.COMBO_BOX_ITEMS);
        numPlayersBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    updateComboBox(event.getItem());
                }
            }
        });

    }


    /**
     * Initializes labels with the strings to display and default visible.
     */
    private void initLabelsAndButtons(ActionListener handler) {
        colorButtons = new JButton[MAX_PLAYERS];
        playerLabels = new JLabel[MAX_PLAYERS];

        gameNameLabel = new JLabel("Game name: ");
        numPlayersLabel = new JLabel("Number of players: ");

        for (int i = 0; i < 6; i++) {
            playerLabels[i] = new JLabel("Player " + (i + 1) + ": ");
            colorButtons[i] = new JButton("Pick a color");

            colorButtons[i].addActionListener(handler);
            colorButtons[i].setName(Integer.toString(i));
            colorButtons[i].setActionCommand("chooseColor");
			
			/* set default of 3 visible */
            playerLabels[i].setVisible(i < 3);
            colorButtons[i].setVisible(i < 3);
        }
    }

    /**
     * Initializes fields with the proper length and visibility.
     */
    private void initFields(CreatePlayersScreenHandler handler) {
        playerFields = new JTextField[MAX_PLAYERS];
        gameNameField = new JTextField("My Risk game", TEXT_FIELD_LENGTH);
        for (int i = 0; i < playerFields.length; i++) {
            playerFields[i] = new JTextField("", TEXT_FIELD_LENGTH);
            playerFields[i].setName(Integer.toString(i));
			
			/* set first 3 visible for default option */
            playerFields[i].setVisible(i < 3);
		
			
			/* redirect tab, enter to the next textfield & verify input data */
            playerFields[i].setFocusTraversalKeysEnabled(false);
            playerFields[i].getInputMap().put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_TAB, 0), "verify");
            playerFields[i].getInputMap().put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_ENTER, 0), "verify");
            playerFields[i].getInputMap().put(KeyStroke.getKeyStroke(
                    "shift TAB"), "verify");
            playerFields[i].getActionMap().put(
                    "verify", createVerifyAction());
        }
    }

    private void initColorChooser(ActionListener handler) {
        colorFrame = new ColorFrame(handler);
		/* blank object array replaces default buttons */
        colorPane = new JOptionPane(colorFrame,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION, null, new Object[]{});

        dialog = colorPane.createDialog(null, "Choose a color");

    }

    /**
     * Creates a new verifyAction for each textField.  Having a single
     * action for each field did not produce any action events.
     * @return Action to verify fields and switch to next appropriate tab.
     */
    private Action createVerifyAction() {
        Action verifyAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateFields();
                JTextField src = (JTextField) e.getSource();
				
				/* go forward 1 if no modifier, otherwise go backwards 1 */
                int nextField = e.getModifiers() == 0 ?
                        Integer.parseInt(src.getName()) + 1 :  //true
                        Integer.parseInt(src.getName()) - 1; //false
				
				/* enforce bounds of [0, MAX_PLAYERS] on nextField*/
                nextField = (nextField + MAX_PLAYERS) % MAX_PLAYERS;

                if (nextButton.isEnabled())
                    nextButton.requestFocus();
                else if (playerFields[nextField].isVisible())
                    playerFields[nextField].requestFocus();

            }
        };
        return verifyAction;
    }
}