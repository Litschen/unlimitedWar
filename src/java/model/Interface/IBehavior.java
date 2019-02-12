package model.Interface;

import model.Country;
import model.Player;

import java.util.ArrayList;

public interface IBehavior {

    /**
     * Places all available Soldiers for this Player on the board
     */
    void placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace);
    /**
     * Selects country to be attacked and executes the attack itself.
     */
    void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries);
    /**
     * Doesn't have to be called at the end of each turn. Can be used to move soldiers in between countries.
     */
    void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries);
}
