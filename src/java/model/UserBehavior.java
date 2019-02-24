package model;


import model.Interface.IBehavior;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {



    /**
     * Places all available Soldiers for this Player on the board
     * @param allCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public void placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {

    }

    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }

    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {

    }
}
