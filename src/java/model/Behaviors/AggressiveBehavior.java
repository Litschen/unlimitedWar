package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;

/**
 * TODO in MS2
 */
public class AggressiveBehavior implements IBehavior {


    /**
     * New soldiers are placed on owned countries. The AggressiveBehavior set soldiers aggressive so it can attack as
     * much as possible
     *
     * @param allCountries    ArrayList with all countries on the board
     * @param ownedCountries of the current player
     * @param soldiersToPlace amount to be placed
     * @return next Phase: attackphase
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return Phase.ATTACKPHASE;
    }

    /**
     * Decides which country should be attacked. AggressiveBehavior attacks as long and often as possible
     *
     * @param allCountries   ArrayList with all countries on the board
     * @param ownedCountries of the current player
     * @return next Phase: movingphase
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.MOVINGPHASE;
    }

    /**
     * Moves soldiers in between two ownded countires once per turn. Moves to so that the AggressiveBehavior can attack
     * in the next turn.
     *
     * @param allCountries   ArrayList with all countries on the board
     * @param ownedCountries of the current player
     * @return next Phase: settingphase
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }
}
