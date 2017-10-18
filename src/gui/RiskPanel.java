/**
 * CSCI 2120 Fall 2014
 * Risk class RiskPanel
 *
 * @author Shane McCulley
 * @date Dec 5, 2014
 **/
package gui;

import engine.RiskUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class RiskPanel extends JPanel {
    private BufferedImage image;

    public RiskPanel(LayoutManager manager) {
        super(manager);
    }

    public RiskPanel() {
        super();
        image = RiskUtils.getImage("background.jpg");
    }

    @Override
    public void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null); ///m1
    }
}
