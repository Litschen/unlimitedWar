package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.Behavior;

import java.util.List;

/**
 * TODO in MS2
 */
public class StrategicBehavior implements Behavior {
    /**
     * Places the specified amount of soldiers on own countries in way that makes strategic sense.
     *
     * @param allCountries    all countries on the baord
     * @param ownedCountries  countries from current player
     * @param soldiersToPlace  amount to be placed
     * @return next phase: attack
     */
    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * Attacks enemy countries as long as it makes strategic sense.
     *
     * @param allCountries  all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * Moves Soldiers in a better position(Country) on the board if necessary
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries are countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
