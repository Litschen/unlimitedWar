package ch.zhaw.unlimitedWar.model.interfaces;

import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.helpers.AttackCountryResult;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;

import java.util.List;

/**
 * Behavior determines how the player or even the user can act. In  player.java are only methods/field that are the
 * same for all players.
 */
public interface Behavior {

    /**
     * Can be used to set soldiers on own country. Place as many soldiers as possible on the map
     */
    Phase placeSoldiers(PlaceSoldiers placeSoldiers);

    /**
     * Can be used to attack other countries. Attack other countries as clever as possible,
     * until the number of soldiers falls to 1.
     */
    AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries);

    /**
     * Can be used to move own soldiers in between own countries. Move soldiers as clever as possible,
     * as long as they are greater than 1.
     */
    Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries);

}