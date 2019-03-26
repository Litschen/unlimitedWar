package model.behavior;

import model.AttackCountryResult;
import model.Country;
import model.enums.Phase;
import model.interfaces.Behavior;

import java.util.List;

/**
 * TODO in MS2
 */
public class StrategicBehavior implements Behavior {

    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        return result;
    }
    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
