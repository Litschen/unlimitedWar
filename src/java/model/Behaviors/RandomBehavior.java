package model.Behaviors;

import model.Country;
import model.Dice;
import model.Enum.Phase;
import model.Interface.IBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBehavior implements IBehavior {

    //region static variables
    //will try to attack in x out of 10 cases
    private final static int AGGRESSIVNESS = 7;
    //will continue to attack in x out of 10 cases
    private final static int STUBORNESS = 9;
    //will move soldiers in x out of 10 cases
    private final static int MOVEWILLINGNESS = 8;
    //end region

    /**
     * Places all available Soldiers for this Player on the board
     *
     * @param allCountries on the board
     * @param ownedCountries by the current Player
     * @param soldiersToPlace
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        while(soldiersToPlace > 0){
            Country country = ownedCountries.get(ThreadLocalRandom.current()
                    .nextInt(0, ownedCountries.size()));
            int placedSoldiers = ThreadLocalRandom.current()
                    .nextInt(0, soldiersToPlace + 1);
            country.setSoldiersCount(country.getSoldiersCount() + placedSoldiers);
            soldiersToPlace = soldiersToPlace - placedSoldiers;
        }

        return Phase.ATTACKPHASE;
    }

    /**
     * Selects country to be attacked and executes the attack itself. Might need to change signature
     *
     * @param allCountries on the board
     * @param ownedCountries by current Player
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        while(willAttack()){
            Collections.shuffle(ownedCountries);
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            for(Country targetCountry : selectedCountry.getNeighboringCountries()){
                try {

                while(willContinueAttack() && selectedCountry.canInvade(targetCountry))
                {
                    int attackerDice = selectedCountry.maxAmountDiceThrowsAttacker();
                    selectedCountry.invade(targetCountry, attackerDice, targetCountry.amountDiceThrowsDefender(attackerDice));
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return Phase.MOVINGPHASE;
    }

    /**
     * Doesn't have to be called at the end of each turn. Can be used to move soldiers in between countries.
     *
     * @param allCountries on the board
     * @param ownedCountries by current Player
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        boolean hasMovedSoldiers = false;
        while(willMoveSoldiers() && !hasMovedSoldiers){
            Collections.shuffle(ownedCountries);
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            if(selectedCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY){
                Country selectedNeighbor = selectedCountry.getNeighboringCountries().get(Dice.roll(0,
                        selectedCountry.getNeighboringCountries().size() - 1));
                if(selectedCountry.getOwner().getOwnedCountries().contains(selectedNeighbor)){
                    selectedCountry.shiftSoldiers(Dice.roll(1, selectedCountry.getSoldiersCount() -
                            Country.MIN_SOLDIERS_TO_STAY),selectedNeighbor);
                    hasMovedSoldiers = true;
                }
            }

        }
        return Phase.SETTINGPHASE;
    }

    private boolean willAttack(){
        return Dice.roll(1, 10) <= AGGRESSIVNESS;
    }
    private boolean willContinueAttack(){
        return Dice.roll(1, 10) <= STUBORNESS;
    }

    private boolean willMoveSoldiers(){
        return Dice.roll(1, 10) <= MOVEWILLINGNESS;
    }




}
