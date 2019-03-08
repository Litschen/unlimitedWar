package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class AggressiveBehavior implements IBehavior {


    /**
     *
     * @param allCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
