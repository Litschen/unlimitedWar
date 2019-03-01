package model.Behaviors;


import model.Country;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {

    /**
     * Places all available Soldiers for this Player on the board
     *
     * @param destinationCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public int placeSoldiers(ArrayList<Country> destinationCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        int countAddedSoldiers = 0;
        for (Country c : destinationCountries) {
            if (ownedCountries.contains(c)) {
                c.addSoldier();
                countAddedSoldiers++;
            }
        }

        return countAddedSoldiers;
    }

    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }

    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }
}
