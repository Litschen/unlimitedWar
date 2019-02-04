package model.Interface;

public interface IBehavior {

    /**
     * Places all available Soldiers for this Player on the board
     */
    void placeSoldiers();
    /**
     * Selects country to be attacked and executes the attack itself.
     */
    void attackCountry();
    /**
     * Doesn't have to be called at the end of each turn. Can be used to move soldiers in between countries.
     */
    void moveSoldiers();
}
