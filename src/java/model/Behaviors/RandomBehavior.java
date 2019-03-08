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
     * Can be used to set soldiers on own country. Place random soldiers on the map
     *
     * @param allCountries    all countries in the game
     * @param ownedCountries  countries from current player
     * @param soldiersToPlace number of soldiers which the current player places
     * @return next Phase: attack
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        while (soldiersToPlace > 0) {
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
     * Can be used to attack other countries. Attack countries random,
     * until the number of soldiers falls to 1.
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        while (willAttack()) {
            Collections.shuffle(ownedCountries);
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            for (Country targetCountry : selectedCountry.getNeighboringCountries()) {
                try {

                    while (willContinueAttack() && selectedCountry.canInvade(targetCountry)) {
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
     * Can be used to move own soldiers in between own countries. Move own solders random,
     * as long as they are greater than 1.
     *
     * @param allCountries   all countries in the game
     * @param ownedCountries countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        boolean hasMovedSoldiers = false;
        while (willMoveSoldiers() && !hasMovedSoldiers) {
            Collections.shuffle(ownedCountries);
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            if (selectedCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
                Country selectedNeighbor = selectedCountry.getNeighboringCountries().get(Dice.roll(0,
                        selectedCountry.getNeighboringCountries().size() - 1));
                if (selectedCountry.getOwner().getOwnedCountries().contains(selectedNeighbor)) {
                    selectedCountry.shiftSoldiers(Dice.roll(1, selectedCountry.getSoldiersCount() -
                            Country.MIN_SOLDIERS_TO_STAY), selectedNeighbor);
                    hasMovedSoldiers = true;
                }
            }

        }
        return Phase.SETTINGPHASE;
    }

    /**
     * @return true if dice eyes (between 1 and 10) are fewer than the value of aggressiveness
     */
    private boolean willAttack() {
        return Dice.roll(1, 10) <= AGGRESSIVNESS;
    }

    /**
     * @return true if dice eyes (between 1 and 10) are fewer than the value of stubbornness
     */
    private boolean willContinueAttack() {
        return Dice.roll(1, 10) <= STUBORNESS;
    }

    /**
     * @return true if dice eyes (between 1 and 10) are fewer than the value of move willingness
     */
    private boolean willMoveSoldiers() {
        return Dice.roll(1, 10) <= MOVEWILLINGNESS;
    }


}
