/**
 * CSCI 2120 Fall 2014
 * Risk class CardComponent
 *
 * @author Shane McCulley
 * @date Dec 2, 2014
 **/
package gui;

import classes.Card;
import engine.RiskUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@SuppressWarnings("serial")
public class CardComponent extends JPanel {
    private static final int PAD_VALUE = 5;
    private JLabel countryName;
    private JLabel cardIcon;
    private Card card;
    private boolean isSelected = false;
    private CompoundBorder normalBorder;
    private CompoundBorder selectedBorder;

    public CardComponent(Card card) {
        initBorders();
        this.card = card;
        this.setBorder(normalBorder);

        this.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints(
                0, 0,                            // grid x, grid y
                1, 1,                            // grid width, height
                0, 0,                            // weight x, weight y
                GridBagConstraints.CENTER,        // anchor
                GridBagConstraints.NONE,        // fill
                new Insets(10, 10, 10, 10),    // insets
                0, 0);                        // x padding, y padding

        countryName = new JLabel(card.getTerritory());
        this.add(countryName, constraints);

		/* create icon and add to 2nd row */
        cardIcon = getCardIcon();
        constraints.gridy = 1;
        this.add(cardIcon, constraints);

    }

    private void initBorders() {
        normalBorder = new CompoundBorder(
                new EmptyBorder(PAD_VALUE, PAD_VALUE, PAD_VALUE, PAD_VALUE),
                BorderFactory.createBevelBorder(BevelBorder.RAISED));

		/* ensures cards don't move when selected by empty border */
        selectedBorder = new CompoundBorder(
                new EmptyBorder(2, 2, 2, 2),
                BorderFactory.createLineBorder(Color.RED, PAD_VALUE));
    }

    private JLabel getCardIcon() {
		/* construct appropriate path */
        String fileName = card.getType() + ".png";
		
		/* place image icon onto JLabel and return */
        return RiskUtils.getIcon(fileName);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean b) {
        if (b) {
            this.setBorder(selectedBorder);
            isSelected = true;
        }
		
		/* does nothing if not selected */
        else if (isSelected) {
            this.setBorder(normalBorder);
            isSelected = false;
        }
    }

    public Card getCard() {
        return this.card;
    }

}
