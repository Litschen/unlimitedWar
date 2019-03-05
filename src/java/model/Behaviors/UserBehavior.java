package model.Behaviors;


import model.Country;
import model.Enum.Phase;
import model.Interface.IBehavior;
import model.Player;

import java.util.ArrayList;

public class UserBehavior implements IBehavior {


    /**
     * Places all available Soldiers for this Player on the board
     *
     * @param destinationCountries
     * @param ownedCountries
     * @param soldiersToPlace
     */
    @Override
    public Phase placeSoldiers(ArrayList<Country> destinationCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        Country destination = destinationCountries.get(0);

        if (ownedCountries.contains(destination)) {
            Player owner = destination.getOwner();

            destination.addSoldier();
            owner.setUserSoldiersToPlace(owner.getUserSoldiersToPlace() - 1);
            if (owner.getUserSoldiersToPlace() == 0) {
                return Phase.ATTACKPHASE;
            }
        }

        return Phase.SETTINGPHASE;
    }

    @Override
    public Phase attackCountry(ArrayList<Country> selectedCountries, ArrayList<Country> ownedCountries) {
        Country attackCountry = selectedCountries.get(0);
        Country defendCountry = selectedCountries.get(1);

        if (ownedCountries.contains(attackCountry) && attackCountry.canInvade(defendCountry)){
            int attackDiceCount = attackCountry.getOwner().getAttackDiceCount();
            int defendDiceCount = defendCountry.amountDiceThrowsDefender(attackDiceCount);
            attackCountry.invade(defendCountry, attackDiceCount, defendDiceCount);
        }

        return Phase.ATTACKPHASE;
    }

    @Override
    public Phase moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Phase.MOVINGPHASE;

    }
}
