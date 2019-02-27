package model;

import model.Interface.IBehavior;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBehavior implements IBehavior {
    //region constructors
    //end region

    /**
     * Places all available Soldiers for this Player on the board
     *
     * @param allCountries on the board
     * @param ownedCountries by the current Player
     * @param soldiersToPlace
     */
    @Override
    public int placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        while(soldiersToPlace > 0){
            Country country = ownedCountries.get(ThreadLocalRandom.current()
                    .nextInt(0, ownedCountries.size()));
            int placedSoldiers = ThreadLocalRandom.current()
                    .nextInt(0, soldiersToPlace + 1);
            country.setSoldiersCount(country.getSoldiersCount() + placedSoldiers);
            soldiersToPlace = soldiersToPlace - placedSoldiers;
        }

        return 0;
    }

    /**
     * Selects country to be attacked and executes the attack itself. Might need to change signature
     *
     * @param allCountries on the board
     * @param ownedCountries by current Player
     */
    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        //TODO
    }

    /**
     * Doesn't have to be called at the end of each turn. Can be used to move soldiers in between countries.
     *
     * @param allCountries on the board
     * @param ownedCountries by current Player
     */
    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

        /*while (soldiersToPlace > 1){
            placeSoldiers(allCountries, ownedCountries, soldiersToPlace);
        }*/


    }




}
