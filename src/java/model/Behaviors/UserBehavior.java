package model.Behaviors;


import model.Country;
import model.Enum.Phase;
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
    public Phase placeSoldiers(ArrayList<Country> destinationCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
       destinationCountries.get(0).addSoldier();
        return Phase.SETTINGPHASE;
    }

    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
       if(super.equals(allCountries.get(0).getOwner())){
           // allCountries.get(0).invade(allCountries.get(1));
       }
        return Phase.ATTACKPHASE;
    }

    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Phase.MOVINGPHASE;

    }
}

