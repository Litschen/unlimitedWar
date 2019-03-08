package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class AggressiveBehavior implements IBehavior {


    /**
     *Computer player who puts as many soldiers as possible on the map
     * @param allCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * Computer player who attacks as often as possible
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * Computer player who moves the soldiers as often as possible
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
