package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class AggressiveBehavior implements IBehavior {


    /**
     * puts as many soldiers as possible on the map
     *
     * @param allCountries all countries in the game
     * @param ownedCountries countries from current player
     * @param soldiersToPlace number of soldiers who can the current player set
     * @return next Phase: attack
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * attacks as often as possible
     *
     * @param allCountries all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * moves the soldiers as often as possible
     *
     * @param allCountries all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
