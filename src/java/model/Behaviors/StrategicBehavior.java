package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class StrategicBehavior implements IBehavior {
    /**
     * Put clever one soldiers on one selected country
     *
     * @param allCountries    all countries in the game
     * @param ownedCountries  countries from current player
     * @param soldiersToPlace it would never be used
     * @return phase attack when none solders are to put on
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * attack clever other countries as often as possible, until the number of own soldiers falls per country to 1.
     *
     * @param allCountries  all countries in the game
     * @param ownedCountries are countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * Singular movement from the soldiers in their own country
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries are countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
