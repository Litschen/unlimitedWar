package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;
import model.Player;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {


    /**
     * Can places given number of Soldiers on the field
     *
     * @param destinationCountries Countries that do not belong to the current player
     * @param ownedCountries
     * @param soldiersToPlace
     * @return next Phase: attack when number of soldiers is 0 otherwise set Phase
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> destinationCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        Country destination = destinationCountries.get(0);

        if (ownedCountries.contains(destination)) {
            Player owner = destination.getOwner();

            destination.setSoldiersCount(destination.getSoldiersCount() + 1);
            owner.setSoldiersToPlace(owner.getSoldiersToPlace() - 1);
            if (owner.getSoldiersToPlace() == 0) {
                return Phase.ATTACKPHASE;
            }
        }

        return Phase.SETTINGPHASE;
    }

    /**
     * Can select  different country and attack until the number of soldiers falls to 1.
     *
     * @param selectedCountries Countries the player has selected
     * @param ownedCountries    countries from current player
     * @return next Phase: attack
     */
    @Override
    public Phase attackCountry(ArrayList<Country> selectedCountries, ArrayList<Country> ownedCountries) {
        Country attackCountry = selectedCountries.get(0);
        Country defendCountry = selectedCountries.get(1);

        if (ownedCountries.contains(attackCountry) && attackCountry.canInvade(defendCountry)) {
            int attackDiceCount = attackCountry.getOwner().getAttackDiceCount();
            int defendDiceCount = defendCountry.amountDiceThrowsDefender(attackDiceCount);
            attackCountry.invade(defendCountry, attackDiceCount, defendDiceCount);
        }

        return Phase.ATTACKPHASE;
    }

    /**
     * Can move the soldiers on their own land as long as they are greater than 1.
     *
     * @param selectedCountries Countries the player has selected
     * @param ownedCountries    countries from current player
     * @return next Phase: move
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> selectedCountries, ArrayList<Country> ownedCountries) {
        // TODO check if neighbour
        Country sourceCountry = selectedCountries.get(0);
        Country destinationCountry = selectedCountries.get(1);
        sourceCountry.shiftSoldiers(1, destinationCountry);

        if (sourceCountry.getSoldiersCount() > 1) {
            return Phase.MOVINGPHASE;
        }
        return Phase.AIPHASE;
    }
}
