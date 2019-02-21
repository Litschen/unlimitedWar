package model;


import model.Interface.IBehavior;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {

    /**
     * Creates Player for the game
     * @param allCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */

    Player computerPlayer01;
    Player computerPlayer02;
    Player computerPlayer03;

    @Override
    public void placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {

    }

    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }

    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }
}
