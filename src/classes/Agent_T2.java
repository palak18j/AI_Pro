package classes;

public class Agent_T2 implements Agent {

    String name;

    @Override
    public void setPrefs(int ID, GameBoard board) {

    }

    @Override
    public int pickCountry() {
        return -1;
    }

    @Override
    public void placeInitialArmies(int numberOfArmies) {

    }

    @Override
    public void cardsPhase(Card[] cards) {

    }

    @Override
    public void placeArmies(int numberOfArmies) {

    }

    @Override
    public void attackPhase() {

    }

    @Override
    public int moveArmiesIn(int countryCodeAttacker, int countryCodeDefender) {
        return -1;
    }

    @Override
    public void fortifyPhase() {

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String n) {
        name = n;
    }

    @Override
    public String description() {
        return name;        //additional things can be given
    }

    @Override
    public String youWon() {
        return "You won! " + name;
    }

    @Override
    public String message(String message, Object data) {
        return null;
    }

}
