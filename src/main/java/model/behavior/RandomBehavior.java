package java.model.behavior;

import java.model.AttackCountryResult;
import java.model.Country;
import java.model.Dice;
import java.model.enums.Phase;
import java.model.interfaces.Behavior;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBehavior implements Behavior {

    //region static variables
    // try to attack in x cases out of 10 cases
    private final static int AGGRESSIVENESS = 7;
    //continue to attack in x cases out of 10 cases
    private final static int STUBBORNNESS = 9;
    //move soldiers in x cases out of 10 cases
    private final static int MOVE_WILLINGNESS = 8;

    private final static int MIN_DICE_RANGE = 0;
    private final static int MAX_DICE_RANGE = 10;
    //end region


    /**
     * Sets the all soldiers random on owned countries
     *
     * @param allCountries    ArrayList with all Countries on the board
     * @param ownedCountries  all owned by current player
     * @param soldiersToPlace number of soldiers to distribute
     * @return next phase: attack
     */
    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
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
     * Decides random which enemy country to attack and if the behavior wants to continue attacking
     *
     * @param allCountries   ArrayList with all Countries on the naord
     * @param ownedCountries are countries from current player
     * @return next Phase: move
     */
    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        while (willAttack()) {
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
        return result;
    }


    /**
     *Decides random if and in between which countries a random amount of soldiers is moved.
     *
     * @param allCountries   ArrayList with all countries on the board
     * @param ownedCountries all countries from current player
     * @return next Phase: set
     */
    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        boolean hasMovedSoldiers = false;
        while (willMoveSoldiers() && !hasMovedSoldiers) {
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
     * @return true if behavior wants to attack
     */
    private boolean willAttack() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= AGGRESSIVENESS;
    }

    /**
     * @return true if behavior wants to continue attacking
     */
    private boolean willContinueAttack() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= STUBBORNNESS;
    }

    /**
     * @return true if behavior wants to move soldiers
     */
    private boolean willMoveSoldiers() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= MOVE_WILLINGNESS;
    }


}
