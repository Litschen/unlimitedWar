package model.behavior;

import model.AttackCountryResult;
import model.Country;
import model.enums.Phase;
import model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO in MS2
 */
public class AggressiveBehavior implements Behavior {


    /**
     * New soldiers are placed on owned countries. The AggressiveBehavior set soldiers aggressive so it can attack as
     * much as possible
     *
     * @param allCountries    ArrayList with all countries on the board
     * @param ownedCountries  of the current player
     * @param soldiersToPlace amount to be placed
     * @return next Phase: attackphase
     */
    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
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
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        List<Country> canInvadeFromCountries = canAttackFrom(ownedCountries);
        while (canInvadeFromCountries.size() > 0) {
            canInvadeFromCountries = canAttackFrom(allCountries);
            for (Country invadingCountry : canInvadeFromCountries) {
                for (Country neighbor : invadingCountry.getNeighboringCountries()) {
                    if (invadingCountry.canInvade(neighbor)) {
                        try {
                            int attackerDice = invadingCountry.maxAmountDiceThrowsAttacker();
                            int defenderDice = neighbor.amountDiceThrowsDefender(attackerDice);
                            invadingCountry.invade(neighbor, attackerDice, defenderDice);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return result;
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
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        return Phase.SETTINGPHASE;
    }

    /**
     * Checks if there are any countries that can be invaded
     * @param ownedCountries by player of this behavior
     * @return ArrayList of countries which can invade another.
     */
    private List<Country> canAttackFrom(List<Country> ownedCountries) {
        List<Country> canInvadeFrom = new ArrayList<>();
        for (Country country : ownedCountries) {
            int i = 0;
            List<Country> neighbors = country.getNeighboringCountries();
            while (i < neighbors.size() && !country.canInvade(neighbors.get(i)) ) {
                i++;
            }
            if (i < neighbors.size()) {
                canInvadeFrom.add(country);
            }
        }
        return canInvadeFrom;
    }
}
