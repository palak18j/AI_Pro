/**
 * CSCI 2120 Fall 2014
 * Risk Game Class Card
 *
 * @author Shane McCulley
 * @date October 21, 2014
 **/

package classes;

/**
 * Card class specifying the Risk game cards
 **/
public class Card {
    /* Instance variables */
    private String type;
    private String territory;

    public Card(String type, String territory) {
        this.type = type;
        this.territory = territory;
    }

    /**
     * @return String type (infantry, cavalry, artillery) of the card
     **/
    public String getType() {
        return type;
    }

    /**
     * @return String reference to the territory this card is matched to.
     **/
    public String getTerritory() {
        return territory;
    }

}
// end Card class

