package ch.zhaw.unlimitedWar.model.behavior;

import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.helpers.AttackCountryResult;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;
import ch.zhaw.unlimitedWar.model.interfaces.Behavior;

import java.util.List;

public class UserBehavior implements Behavior {

    /**
     * Puts one soldier on the destinationCountries[0] country. This is repeated until there are no soldiers to place
     * @param placeSoldiers  Object with Solders attribute, List whit all Countries and owned Countries
     * @return phase set as long as soldiersToPlace of the owner is greater than 0 otherwise attack phase
     */
    @Override
    public Phase placeSoldiers(PlaceSoldiers placeSoldiers) {
        List<Country> destinationCountries = placeSoldiers.getAllCountries();

        Phase phase = Phase.SET;
        Country destination = destinationCountries.get(0);
        if (placeSoldiers.getSoldiersToPlace() > 0) {
            if (placeSoldiers.getOwnedCountries().contains(destination)) {
                destination.setSoldiersCount(destination.getSoldiersCount() + 1);
                placeSoldiers.setSoldiersToPlace(placeSoldiers.getSoldiersToPlace() - 1);
                if (placeSoldiers.getSoldiersToPlace() == 0) {
                    phase = Phase.ATTACK;
                }
            }
        } else {
            phase = Phase.ATTACK;
        }
        return phase;
    }

    /**
     * Attacks selectedCountries[1] from selectedCountries[0].
     *
     * @param selectedCountries [0] attacks [1]
     * @param ownedCountries    of current player
     * @return attackPhase, next phase is set from ch.zhaw.unlimitedWar.controller.
     */
    @Override
    public AttackCountryResult attackCountry(List<Country> selectedCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVE);
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
     * Moves 1 soldiers from selectedCountries[0] to selectedCountries[1] if possible
     *
     * @param selectedCountries are countries the player has selected
     * @param ownedCountries    all countries from current player
     * @return setting phase, next phase set in ch.zhaw.unlimitedWar.controller
     */
    @Override
    public Phase moveSoldiers(List<Country> selectedCountries, List<Country> ownedCountries) {
        Phase newPhase = Phase.SET;
        Country sourceCountry = selectedCountries.get(0);
        Country destinationCountry = selectedCountries.get(1);
        sourceCountry.shiftSoldiers(1, destinationCountry);

        if (sourceCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
            newPhase = Phase.MOVE;
        }
        return newPhase;
    }
}
