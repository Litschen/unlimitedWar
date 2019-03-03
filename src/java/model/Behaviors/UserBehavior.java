package model.Behaviors;


import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;
import model.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class UserBehavior implements IBehavior {

    Country currentPlayer;

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
        try {
            if (ownedCountries.contains(allCountries)) {
               // currentPlayer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Phase.ATTACKPHASE;
    }

    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

        try {
            if (ownedCountries.contains(allCountries)) {
                currentPlayer.shiftSoldiers(currentPlayer.getSoldiersCount(), currentPlayer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Phase.MOVINGPHASE;

    }
}

