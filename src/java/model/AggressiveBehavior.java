package model;

import model.Interface.IBehavior;

import java.util.ArrayList;

public class AggressiveBehavior implements IBehavior {


    /**
     * @param allCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public boolean placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return true;
    }

    /**
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }

    /**
     * @param allCountries
     * @param ownedCountries
     */
    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }
}
