package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class AggressiveBehavior implements IBehavior {


    /**
     * New soldiers have to be placed on their own countries.
     * Put one soldiers on one selected country. This is repeated until it has no longer soldiers to place
     * The AggressiveBehavior set soldiers aggressive
     *
     * @param allCountries    ArrayList with listed Countries
     * @param ownedCountries  are countries from current player
     * @param soldiersToPlace it would never be used
     * @return phase attack when none solders are to put on
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * The StrategicBehavior selects a country and a neighboring country to attack
     * Only those countries with more than 1 soldier can attack
     * The AggressiveBehavior attack as long as possible
     *
     * @param allCountries   ArrayList with listed Countries
     * @param ownedCountries are countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * The soldiers are singular being moved from one own country to another own country.
     * The countries must be both on the same continent. Only those countries with more than 1 soldier can move
     * The AggressiveBehavior put the soldiers as clever as possible
     *
     * @param allCountries   ArrayList with listed Countries
     * @param ownedCountries are countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
