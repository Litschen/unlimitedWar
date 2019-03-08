package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class StrategicBehavior implements IBehavior {
    /**
     * Can be used to set soldiers on own country. Place soldiers on the map as clever as possible.
     *
     * @param allCountries    all countries in the game
     * @param ownedCountries  countries from current player
     * @param soldiersToPlace number of soldiers which the current player places
     * @return next Phase: attack
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * Can be used to attack other countries. Attack other countries as clever as possible,
     * until the number of soldiers falls to 1.
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * Can be used to move own soldiers in between own countries. Move soldiers as clever as possible,
     * as long as they are greater than 1.
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
