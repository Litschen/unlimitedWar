package model.Interface;

import model.Country;
import model.Enum.Phase;

import java.util.ArrayList;

/**
 * Behavior determines how the player or even the user can act. In  player.java are only methods/field that are the
 * same for all players.
 */
public interface IBehavior {

    /**
     * Places all available Soldiers for this Player on the board
     */
    Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace);

    /**
     * Selects country to be attacked and executes the attack itself.
     */
    Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries);

    /**
     * Doesn't have to be called at the end of each turn. Can be used to move soldiers in between countries.
     */
    Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries);
}
