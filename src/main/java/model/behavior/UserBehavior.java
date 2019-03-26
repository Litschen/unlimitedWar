package java.model.behavior;

import java.model.AttackCountryResult;
import java.model.Country;
import java.model.Player;
import java.model.enums.Phase;
import java.model.interfaces.Behavior;

import java.util.List;

public class UserBehavior implements Behavior {

    /**
     * Puts one soldier on the destinationCountries[0] country. This is repeated until there are no soldiers to place
     *
     * @param destinationCountries on index 0 is the select country to place 1 soldier
     * @param ownedCountries       countries from current player
     * @param soldiersToPlace      unused
     * @return phase set as long as soldierstoplace of the owner is greater than 0 otherwise attack phase
     */
    @Override
    public Phase placeSoldiers(List<Country> destinationCountries, List<Country> ownedCountries, int soldiersToPlace) {
        Phase phase = Phase.SETTINGPHASE;
        Country destination = destinationCountries.get(0);
        Player owner = destination.getOwner();
        if (owner.getSoldiersToPlace() > 0) {
            if (ownedCountries.contains(destination)) {
                destination.setSoldiersCount(destination.getSoldiersCount() + 1);
                owner.setSoldiersToPlace(owner.getSoldiersToPlace() - 1);
                if (owner.getSoldiersToPlace() == 0) {
                    phase = Phase.ATTACKPHASE;
                }
            }
        } else {
            phase = Phase.ATTACKPHASE;
        }
        return phase;
    }

    /**
     * Attacks selectedCountries[1] from selectedCountries[0].
     *
     * @param selectedCountries [0] attacks [1]
     * @param ownedCountries    of current player
     * @return attackphase, next phase is set from controller.
     */
    @Override
    public AttackCountryResult attackCountry(List<Country> selectedCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        Country attackCountry = selectedCountries.get(0);
        Country defendCountry = selectedCountries.get(1);

        if (ownedCountries.contains(attackCountry) && attackCountry.canInvade(defendCountry)) {
            int attackDiceCount = attackCountry.getOwner().getAttackDiceCount();
            int defendDiceCount = defendCountry.amountDiceThrowsDefender(attackDiceCount);
            result.addEvents(attackCountry.invade(defendCountry, attackDiceCount, defendDiceCount));
        }
        return result;
    }

    /**
     * Moves 1 soliders from selectedCountries[0] to selectedCountries[1] if possible
     *
     * @param selectedCountries are countries the player has selected
     * @param ownedCountries    all countries from current player
     * @return setting phase, next phase set in controller
     */
    @Override
    public Phase moveSoldiers(List<Country> selectedCountries, List<Country> ownedCountries) {
        Phase newPhase = Phase.SETTINGPHASE;
        Country sourceCountry = selectedCountries.get(0);
        Country destinationCountry = selectedCountries.get(1);
        sourceCountry.shiftSoldiers(1, destinationCountry);

        if (sourceCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
            newPhase = Phase.MOVINGPHASE;
        }
        return newPhase;
    }
}
