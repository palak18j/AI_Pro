/**
 * CSCI 2120 Fall 2014
 * Risk class RiskGameLoader
 *
 * @author Shane McCulley
 * @date Dec 5, 2014
 **/
package classes;

import engine.RiskUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads the config file and creates new GameBoard and Deck classes for
 * RiskGame.
 */
public class RiskGameLoader {
    private static final int CONTINENT = 0;
    private static final int TERRITORY = 1;
    private static final int POINT_X = 2;
    private static final int POINT_Y = 3;
    private static final String CONFIG_FILE = "classes/config.txt";
    private static final String[] CARD_TYPE = {"troop", "horse", "cannon"};

    /* used to build up game objects */
    private ArrayList<Card> cards;
    private ArrayList<String[]> configData;
    private HashMap<String, Territory> territories;
    private HashMap<String, Continent> continents;
    private HashMap<String, Territory> territoriesOfContinent;

    /* objects created and returned by this class */
    private final GameBoard gameBoard;
    private final Deck deck;

    public RiskGameLoader() {
        // gameBoard = new GameBoard( null, null );
        // deck = new Deck( null );
        configData = new ArrayList<String[]>();
        cards = new ArrayList<Card>();
        territories = new HashMap<String, Territory>();
        continents = new HashMap<String, Continent>();

        getConfigData(CONFIG_FILE);

		/* create card, territory, continent objects */
        createObjects();

		/* set adjacency and containing associations */
        setAssociations();

        deck = new Deck(cards);
        gameBoard = new GameBoard(territories, continents);

    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public Deck getDeck() {
        return this.deck;
    }

    private void createObjects()

    {
        int cardIndex = 0;
        Continent continent = null;

        for (int i = 0; i < configData.size(); i++) {
            String[] array = configData.get(i);

			/* initializing continent */
            if (array[CONTINENT].length() > 2) {
                continent = new Continent(
                        array[CONTINENT],
                        Integer.parseInt(array[CONTINENT + 1].trim()));
                continents.put(continent.getName(), continent);
            } else {
                Point p = new Point(Integer.parseInt(array[POINT_X].trim()),
                        Integer.parseInt(array[POINT_Y].trim()));
                Territory t = new Territory(array[TERRITORY], continent, p);
                territories.put(t.getName(), t);

				/* create new card */
                Card c = new Card(CARD_TYPE[cardIndex % 3], t.getName());
                cards.add(c);
                cardIndex++;
            }
        }
    }

    private void setAssociations() {

        Continent continent = null;

		/* loop over config file again */
        for (int i = 0; i < configData.size(); i++) {
            String[] array = configData.get(i);

			/* set new continent */
            if (array[CONTINENT].length() > 2) {
                /* set territories filled by previous continent */
                if (i != 0) {
                    continent.setTerritories(territoriesOfContinent);
                }

                this.territoriesOfContinent = new HashMap<String, Territory>();
                continent = continents.get(array[CONTINENT]);
            }

			/* get territory and parse the remaining line for neighbors */
            else {
                HashMap<String, Territory> neighbors =
                        new HashMap<String, Territory>();

                Territory t = territories.get(array[TERRITORY]);

				/* loop over remaining array and add to neighbors */
                for (int j = POINT_Y + 1; j < array.length; j++) {
                    Territory adj = territories.get(array[j]);
                    neighbors.put(adj.getName(), adj);
                }

                t.setNeighbors(neighbors);
                territoriesOfContinent.put(t.getName(), t);
            }

			/* after the last line has been processed, add to last continent */
            if (i == configData.size() - 1) {
                continent.setTerritories(territoriesOfContinent);
            }
        }
    }

    /**
     * Opens and parses the config file to initialize default game state.
     *
     * @param filename file name of the config file.
     */
    private void getConfigData(String filename) {

        /*****replaced by input stream for jar creation***********/
        //File inFile = new File( filename ); */

        InputStream is = RiskUtils.getResourceAsStream(CONFIG_FILE);

		/*
		 * if the file does not exist, is not accessible, is not regular, print
		 * error message and exit.  
		 */
        //if ( !( inFile.exists() && inFile.canRead() && inFile.isFile() ) )
        if (is == null) {
            JOptionPane.showMessageDialog(null, "Provided filename" +
                    filename + "is not valid. Exiting program.");
            System.exit(1);
        }

		/* if the file is valid, use bufferedreader in a try statement to open */
        else {
			/* try to open file, catch FileNotFoundException 
			try
			{*/
            //FileReader fin = new FileReader( inFile );
            BufferedReader bin = new BufferedReader(
                    new InputStreamReader(is));
            String currentLine;

				/* try to read file, catch IOException */
            try {
					/* try to read line, returns null if EOF */
                while ((currentLine = bin.readLine()) != null) {
                    String[] split = currentLine.split(",");

                    for (int i = 0; i < split.length; i++) {
                        split[i] = split[i].trim();
                    }

                    configData.add(split);
                }

					/* done reading file, perform cleanup by closing it */
                bin.close();

            } catch (IOException e) {
                System.err
                        .println("Couldn't read file: " + e.getMessage());
            }
            //	}
			/* TOCTTOU error occurred 
			catch ( FileNotFoundException e )
			{
				System.err.printf( "File %s is no longer readable. %n", filename );
				e.printStackTrace();
			} */
        }
    }
}
