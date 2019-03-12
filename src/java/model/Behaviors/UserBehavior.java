package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;
import model.Player;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {

    /**
     * Put one soldiers on one selected country
     *
     * @param destinationCountries
     * @param ownedCountries       are countries from current player
     * @param soldiersToPlace      it would never be used
     * @return phase set phase as far as it can be to set by soldiers. Or phase attack phase none solders are to put on
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> destinationCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
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
     * attack other countries as often as possible,
     * until the number of own soldiers falls per country to 1.
     *
     * @param selectedCountries ist an ArrayList from selected countries. Index 0 is the country, witch is been attacked
     *                          and index 1 it's the country witch has been defended.
     * @param ownedCountries    are owned by the player
     * @return replace the next phase  manually
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
     * Singular movement from the soldiers in their own country
     *
     * @param selectedCountries are countries the player has selected
     * @param ownedCountries    are countries from current player
     * @return replace the next phase  manually
     */
    @Override
    public Phase moveSoldiers(ArrayList<Country> selectedCountries, ArrayList<Country> ownedCountries) {

        Country sourceCountry = selectedCountries.get(0);
        Country destinationCountry = selectedCountries.get(1);
        sourceCountry.shiftSoldiers(1, destinationCountry);

        if (sourceCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
            return Phase.MOVINGPHASE;
        }
        return Phase.SETTINGPHASE;
    }
}
