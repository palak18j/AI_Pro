/**
 * CSCI 2120 Fall 2014
 * Risk class MapScreenPanel
 *
 * @author Shane McCulley
 * @date Dec 1, 2014
 **/
package gui;

import classes.Player;
import classes.Territory;
import engine.RiskUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class MapScreenPanelTest extends JPanel {
    /** inner class MapImage */
    public class MapImage extends Component {
        private BufferedImage bufferedMap;
        private BufferedImage mapOverlay;
        private final static String MAP_PATH = "RiskBoard.jpg";
        private final static String OVERLAY_PATH = "BoardOverlay.png";

        private int w, h;

        public MapImage() {
            bufferedMap = readImage(MAP_PATH);
            mapOverlay = readImage(OVERLAY_PATH);
            w = bufferedMap.getWidth();
            h = bufferedMap.getHeight();

        }

        private BufferedImage readImage(String pathName) {

			/*URL url = MapScreenPanelTest.class.getClassLoader().getResource( pathName );
			BufferedImage img = null;
			
			try
			{
				img = ImageIO.read( url );
			}
			catch ( IOException e )
			{
				System.err.println( "Error reading image" );
				e.printStackTrace();
				System.exit( 1 );
			}*/
            System.out.println("came to readImage");
            BufferedImage img = RiskUtils.getImage(pathName);
                        
                        /*int newW=1000,newH=600;
                        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
                        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = dimg.createGraphics();
                        g2d.drawImage(tmp, 0, 0, null);
                        g2d.dispose();

                        img=dimg;*/
            return img;
        }

        public Dimension getPreferredSize() {
            if ((w + h) != 0)
                return new Dimension(w, h + 40);
            else
                return super.getPreferredSize();

        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(bufferedMap, 0, 0, null);
        }

        public BufferedImage getImg() {
            return bufferedMap;
        }

    }

    private ActionListener handler;

    /* instance variables */
    private MapImage img;
    private Map<Point, Territory> circlesToDraw;
    private static final int CIRCLE_HEIGHT = 24;
    private static final int CIRCLE_WIDTH = 26;

    /* north panel */
    private JPanel menuPanel;
    private JLabel infoLabel;
    private JComboBox<String> actionFromBox;
    private JComboBox<String> actionToBox;
    private JButton endTurnButton;
    private Component labelSpace;
    private Component comboBoxSpace;
    private Component endTurnSpace;


    /* map information */
    private Map<String, Territory> territories;

    /* JInternalFrame panels */
    private JInternalFrame cardFrame;
    private CardScreenPanel cardScreenPanel;

    private AttackScreenPanel attackScreenPanel;
    private MoveTroopsScreenPanel moveTroopsScreenPanel;
    private JInternalFrame attackFrame;
    private JPanel currentAttackFramePanel;

    /**
     * Create the panel.
     */
    public MapScreenPanelTest(ActionListener handler) {
        this.handler = handler;
        currentAttackFramePanel = new JPanel();

        cardFrame = new JInternalFrame();
        cardFrame.setLocation(RiskUtils.getRelativeScreenLocation(0.0, 0.25));

        attackFrame = new JInternalFrame();
        attackFrame.setLocation(RiskUtils.getRelativeScreenLocation(0.15, 0.15));

        circlesToDraw = new HashMap<Point, Territory>();


        final MapImage map = new MapImage();
        this.img = map;
        JButton cardButton = new JButton("Cards");
        menuPanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) menuPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        menuPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new EmptyBorder(0, 0, 0, 0)));
        menuPanel.setBounds(0, 0, 1280, 40);

        cardButton.setActionCommand("openCards");
        cardButton.addActionListener(handler);


        actionFromBox = new JComboBox<String>();
        actionFromBox.setName("actionFromBox");
        actionFromBox.setBounds(425, 11, 200, 22);
        actionFromBox.setPreferredSize(new Dimension(150, 22));
        setLayout(null);

        actionFromBox.setOpaque(false);
        menuPanel.add(cardButton);

        Component horizontalStrut = Box.createHorizontalStrut(50);
        menuPanel.add(horizontalStrut);

        infoLabel = new JLabel("");
        infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        menuPanel.add(infoLabel);

        labelSpace = Box.createHorizontalStrut(300);
        menuPanel.add(labelSpace);
        menuPanel.add(actionFromBox);

        actionToBox = new JComboBox<String>();
        actionToBox.setName("actionToBox");


        comboBoxSpace = Box.createHorizontalStrut(5);
        menuPanel.add(comboBoxSpace);

        menuPanel.add(actionToBox);
        actionToBox.setVisible(false);

        this.add(menuPanel);

        endTurnSpace = Box.createHorizontalStrut(20);

        menuPanel.add(endTurnSpace);

        endTurnButton = new JButton("End Turn");
        endTurnButton.setActionCommand("endTurn");
        endTurnButton.addActionListener(handler);
        menuPanel.add(endTurnButton);
        ((MapScreenHandler) handler).addView(this);

    }

    public BufferedImage getOverlay() {
        return img.mapOverlay;
    }


    /**
     * Adds a territory's circle to list of circles to draw.
     */
    public void addCircle(String newTerritory) {
        Territory territory = territories.get(newTerritory);
        circlesToDraw.put(territory.getCircleCenter(), territory);
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(img.getPreferredSize());
    }

    public void initGUI() {
        final MapImage map = new MapImage();
        add("Center", map);
        Dimension awk = map.getPreferredSize();
        this.setSize(awk.width, awk.height);
    }

    /**
     * Handles drawing of circles on map
     */
    @Override
    protected void paintComponent(Graphics g) {
		/* draws the actual map */
        g.drawImage(img.getImg(), 0, 40, null);

        for (Map.Entry<Point, Territory> entry : circlesToDraw.entrySet()) {
            Point p = entry.getKey();
            Territory t = entry.getValue();
            g.setColor(t.getColor());

            int x = p.x - (CIRCLE_WIDTH / 2);
            int y = p.y - (CIRCLE_HEIGHT / 2);
            g.fillOval(x, y, CIRCLE_WIDTH, CIRCLE_HEIGHT);

            g.setColor(java.awt.Color.BLACK);
            drawCenteredText(g, p.x, p.y, 13.0f,
                    Integer.toString(t.getNumArmies()));
        }

    }

    /**
     * Revalidate and repaint the main Map.
     */
    public void updateMap() {
        this.revalidate();
        this.repaint();
    }

    /**
     * Update map data with new troop values, erase actionToBox items.
     */
    public void updateMove(Player currentPlayer) {

        updateAttackBox(currentPlayer);

        actionToBox.removeActionListener(handler);
        actionToBox.removeAllItems();
        actionToBox.addActionListener(handler);
    }

    /**
     * Initializes the attack panel from territory selected in actionFromBox
     * to territory in actionToBox.
     */
    public void attack() {
		/* get territories from each box */
        Territory attacker =
                territories.get((String) actionFromBox.getSelectedItem());
        Territory defender =
                territories.get((String) actionToBox.getSelectedItem());
		
		/* initialize panel with data */
        attackScreenPanel.attack(attacker, defender);

        this.changeAttackFrameContent(attackScreenPanel);
        attackFrame.setVisible(true);

    }

    /**
     * Fortify phase: Initialize the move panel with territories selected
     * in actionFromBox to territory in actionToBox
     */
    public void fortify() {
        moveTroopsScreenPanel.moveTroops(
                territories.get((String) actionFromBox.getSelectedItem()),
                territories.get((String) actionToBox.getSelectedItem()));

        attackFrame.setVisible(false);

        this.changeAttackFrameContent(moveTroopsScreenPanel);
        attackFrame.show();

    }


    /**
     * Updates content in attackFrame with newContent
     *
     * @param newContent JPanel containing new content.
     */
    private void changeAttackFrameContent(JPanel newContent) {
        attackFrame.getContentPane().remove(currentAttackFramePanel);
        currentAttackFramePanel = newContent;
        attackFrame.getContentPane().add(currentAttackFramePanel);
        attackFrame.pack();
        attackFrame.revalidate();
        attackFrame.repaint();
    }

    /**
     * Initialization method at start of game to construct game board.
     *
     * @param territories list of territories from gameboard.
     */
    public void setMap(Map<String, Territory> territories) {
        this.territories = territories;
        initActionFromBox(territories);
        endTurnButton.setEnabled(false);

    }

    /**
     * Fills the actionFromBox with territories for choosing by the
     * current player.
     *
     * @param territories A list of territories to put in the box.
     */
    public void initActionFromBox(Map<String, Territory> territories) {
        actionFromBox.removeActionListener(handler);
        actionFromBox.removeAllItems();

        for (String s : territories.keySet()) {
            actionFromBox.addItem(s);
        }

        actionFromBox.addActionListener(handler);
        this.updateMap();
    }


    /**
     * Updates label with appropriate text for assigning territories and
     * sets the current player.
     */
    public void assignTerritories(Player currentPlayer) {
        setNextPlayer(currentPlayer);
    }

    /**
     * Sets the panel color of the current player to give visual feedback
     * of whose turn it is.
     *
     * @param currentPlayer new Player that is the currentPlayer.
     */
    public void setNextPlayer(Player currentPlayer) {
        menuPanel.setBackground(currentPlayer.getColor());

    }

    /**
     * Sets the values of the actionFromBox with the territories of the player.
     *
     * @param currentPlayer the current Player.
     */
    public void assignArmies(Player currentPlayer) {
        setNextPlayer(currentPlayer);
        initActionFromBox(currentPlayer.getTerritoriesList());
        setLabelPlaceArmies(currentPlayer);

    }

    public void placeArmies(Player currentPlayer) {
        endTurnButton.setEnabled(false);
        assignArmies(currentPlayer);
    }

    public void setLabelAssignTerritories(Player currentPlayer) {
        infoLabel.setText(currentPlayer.getName() + ", choose a territory "
                + "from the box.");
        repaint();
    }

    public void setLabelPlaceArmies(Player currentPlayer) {
        infoLabel.setText(currentPlayer.getName() + ", place your armies.  "
                + currentPlayer.getUnplacedArmies() + " left.");

        repaint();
    }

    private void setLabelAttack(Player currentPlayer) {
        infoLabel.setText(currentPlayer.getName() + ", begin attack phase");
        repaint();

    }

    private void setLabelFortify(Player currentPlayer) {
        infoLabel.setText(currentPlayer.getName() + ", reinforcement phase");
        repaint();
    }

    /**
     * @param currentPlayer
     */
    public void initFortifyFromChoices(Player currentPlayer) {
        setLabelFortify(currentPlayer);
        updateAttackBox(currentPlayer);
    }

    public void showMoveTroopsScreen() {
		/* player must move a minimum of troops based on # of dice rolled*/
        moveTroopsScreenPanel.moveTroops(
                attackScreenPanel.getAttacker(),
                attackScreenPanel.getDefender(),
                attackScreenPanel.getNumAttacking());

        attackFrame.setVisible(false);

        this.changeAttackFrameContent(moveTroopsScreenPanel);
        attackFrame.show();

    }

    /**
     * Brings up the card screen for the current player.  If the player
     * must trade cards, the exit will be disabled from the window.
     */
    public void showCards(Player currentPlayer, boolean mustTradeCards) {
        cardScreenPanel.setPlayer(currentPlayer, mustTradeCards);
        cardFrame.setVisible(!cardFrame.isVisible());
    }

    /**
     * Sets up the actionToBox for attack state.
     */
    public void initActionToBox(String attackingTerritory) {
        Territory attackingFrom = territories.get(attackingTerritory);
        Player attackingPlayer = attackingFrom.getOccupant();
        Map<String, Territory> choices = attackingFrom.getNeighbors();

        actionToBox.removeActionListener(handler);
        actionToBox.removeAllItems();
		
		/* check ownership before adding to box */
        for (Territory t : choices.values()) {
            if (t.getOccupant() != attackingPlayer)
                actionToBox.addItem(t.getName());
        }

        actionToBox.addActionListener(handler);
    }

    /**
     * Populates actionToBox with a list of neighbors of movingTerritory for
     * fortify state.
     *
     * TODO merge with initActionToBox with branching point.
     * @param movingTerritory String name of territory moving from.
     */
    public void initFortifyToChoices(String movingTerritory) {
        Territory movingFrom = territories.get(movingTerritory);
        Player movingPlayer = movingFrom.getOccupant();
        Map<String, Territory> choices = movingFrom.getNeighbors();

        actionToBox.removeActionListener(handler);
        actionToBox.removeAllItems();
		
		/* check ownership before adding to box */
        for (Territory t : choices.values()) {
            if (t.getOccupant() == movingPlayer)
                actionToBox.addItem(t.getName());
        }

        actionToBox.addActionListener(handler);
    }

    /**
     * Shows the actionToBox and populates actionFromBox with list of
     * territories that a player can attack from.
     *
     * @param currentPlayer
     */
    public void showAttackBox(Player currentPlayer) {
        actionToBox.setVisible(true);
        endTurnButton.setEnabled(true);
        actionToBox.setPreferredSize(actionFromBox.getPreferredSize());
        setLabelAttack(currentPlayer);
        updateAttackBox(currentPlayer);
    }

    /**
     * Populates actionFromBox with the current player's territories that
     * have more than 1 army present.
     *
     * @param currentPlayer
     */
    public void updateAttackBox(Player currentPlayer) {
        actionFromBox.removeActionListener(handler);
        actionFromBox.removeAllItems();
        for (Territory t : currentPlayer.getTerritoriesList().values()) {
			/* need at least 2 armies to fortify or attack */
            if (t.getNumArmies() > 1)
                actionFromBox.addItem(t.getName());
        }

        actionFromBox.addActionListener(handler);
        this.updateMap();
    }

    /**
     * Sets the attack screen panel that was initialized in the GUI.
     * @param panel the attack screen panel initialized in GUI.
     */
    public void setAttackScreenPanel(AttackScreenPanel panel) {
        this.attackScreenPanel = panel;
        currentAttackFramePanel = panel;
        attackFrame.getContentPane().add(panel);
        attackFrame.pack();
        this.add(attackFrame);

    }

    /**
     * Sets the card screen panel that was initialized in the GUI.
     * @param panel the card screen panel initialized in GUI.
     */
    public void setCardScreenPanel(CardScreenPanel panel) {
        this.cardScreenPanel = panel;

        cardFrame.getContentPane().add(panel);
        cardFrame.pack();
        this.add(cardFrame);
    }

    /**
     * Sets the move troops screen panel that was initialized in the GUI.
     * @param panel the move troops screen panel initialized in GUI.
     */
    public void setMoveTroopsScreenPanel(MoveTroopsScreenPanel panel) {
        this.moveTroopsScreenPanel = panel;
    }

    /**
     * Draws text in the center of the circle at the provided coordinates by
     * calculating the area of an enclosing square given by font metrics.
     *
     * @param g Graphics from paintComponent
     * @param x int x coordinate of circle.
     * @param y int y coordinate of circle
     * @param size float size of font to draw
     * @param text String text to write.
     */
    public static void drawCenteredText(Graphics g, int x, int y, float size, String text) {
        // Create a new font with the desired size
        Font newFont = g.getFont().deriveFont(size);
        g.setFont(newFont);
        // Find the size of string s in font f in the current Graphics context g.
        FontMetrics fm = g.getFontMetrics();
        java.awt.geom.Rectangle2D rect = fm.getStringBounds(text, g);

        int textHeight = (int) (rect.getHeight());
        int textWidth = (int) (rect.getWidth());
        // Find the top left and right corner
        int cornerX = x - (textWidth / 2);
        int cornerY = y - (textHeight / 2) + fm.getAscent();

        g.drawString(text, cornerX, cornerY);  // Draw the string.

    }

    /**
     * This is used in the half-finished implementation of clicking territories
     * instead of using combo box.
     */
    public class MapMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //int x = e.getX();
            //int y = e.getY();
            //MapScreenPanelTest mps = (MapScreenPanelTest)e.getSource();
            //mps.addCircle( e.getX(), e.getY() );

            //circlesToDraw.add(  new Point( e.getX(), e.getY() ));
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

    }

}
