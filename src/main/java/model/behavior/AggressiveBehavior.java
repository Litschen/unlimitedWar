package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
import model.helpers.MoveComperator;
import model.helpers.MoveCountry;
import model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggressiveBehavior implements Behavior {


    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
        if (ownedCountries.size() > 0) {
            List<Country> countriesToSet = mostEffectiveCountry(ownedCountries, ownedCountries.get(0).getOwner());
            for (int i = 0; soldiersToPlace > 0 && countriesToSet.size() > 0; soldiersToPlace--) {
                Country c = countriesToSet.get(i);
                c.setSoldiersCount(c.getSoldiersCount() + 1);
                i++;
                if (i >= countriesToSet.size()) {
                    i = 0;
                }
            }
        }
        return Phase.ATTACKPHASE;
    }

    /**
     * return a list of countries to set a soldier
     */
    private List<Country> mostEffectiveCountry(List<Country> invadeFromCountries, Player owner) {
        Map<Country, Integer> map = new HashMap<>();
        for (Country invadeFrom : invadeFromCountries) {
            List<Country> invadeCountry = new ArrayList<>();

            // check how far the user can invade from this country
            checkIfAbleToAttackFurther(invadeCountry, invadeFrom, owner);
            map.put(invadeFrom, invadeCountry.size());
        }

        // evaluate the most effective country / countries to attack from
        List<Country> maxEntry = new ArrayList<>();
        int max = 0;
        for (Map.Entry<Country, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= max) {
                if (entry.getValue() > max) {
                    maxEntry.clear();
                }
                maxEntry.add(entry.getKey());
                max = entry.getValue();
            }
        }

        return maxEntry;
    }

    private void checkIfAbleToAttackFurther(List<Country> invadeCountry, Country invadeFrom, Player owner) {
        for (Country neighbor : invadeFrom.getNeighboringCountries()) {
            if (!invadeCountry.contains(neighbor) && !neighbor.getOwner().equals(owner)) {
                invadeCountry.add(neighbor);
                checkIfAbleToAttackFurther(invadeCountry, neighbor, owner);
            }
        }
    }

    /**
     * AggressiveBehavior attacks as long and often as possible
     */
    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        List<Country> canInvadeFromCountries = canAttackFrom(ownedCountries);
        while (canInvadeFromCountries.size() > 0) {
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
            canInvadeFromCountries = canAttackFrom(ownedCountries);
        }
        return result;
    }

    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {

        int numberOfNeighbors;

        List<MoveCountry> ListCountriesWithNeighbors = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            numberOfNeighbors = 0;
            for (Country neighborCountry : currentCountry.getNeighboringCountries()) {
                if (neighborCountry.getOwner() != currentCountry.getOwner()) {
                    // @Tina du luegsch of es land nachbar vo sicher selber isch?
                    // @manu das ergit wirklich kei sinn, so besser?
                    numberOfNeighbors++;
                     // @Tina die zahl wird nie wieder uf null gesetzt => nöchst land fangt scho mit z.B 4 nachbare ah
                    // @manu Stimmt, ah dass hani gar net denkt. Has jetzt unter de erste for Schliefe ta, will ja mit
                    // jedem neue Land de counter uf 0 chumt.
                }
                ListCountriesWithNeighbors.add(new MoveCountry((numberOfNeighbors, currentCountry, neighborCountry));
            }

        }

        ListCountriesWithNeighbors.sort(new MoveComperator()); // Werte werden sortiert

        if (eindeutig){ // falls eindeutige Zahl. @manu: bin grad nöt sicher, wie ich es prüfe sötti. Ha was mit stream() gfunde, doch
            // so ganz ihlüchend, isch es nöt
            // ----------------------------------
            // @Manu: mis problem ist, wie ich die Methode shiftSoldiers sööti ufrüfe, wenn ich en ListCountriesWithNeighbors.get vo
            // MoveCountry isch, aber shiftSoldiers zu Country köhrt: (Ha allgemein no sporblem, sache vo versch. Objekt üfzrüefe)
            Country selectedCountry = null;
            MoveCountry moveCountry = null;
            ListCountriesWithNeighbors.get(0);

            selectedCountry.shiftSoldiers(moveCountry.getNumberOfNeighbors()); // funktioniert nöt
            return Phase.SETTINGPHASE;

        } else { // falls nicht eindeutig

            return Phase.SETTINGPHASE;
        }




    }


    /**
     * Checks if there are any countries that can be invaded
     *
     * @param ownedCountries by player of this behavior
     * @return ArrayList of countries which can invade another.
     */
    private List<Country> canAttackFrom(List<Country> ownedCountries) {
        List<Country> canInvadeFrom = new ArrayList<>();
        for (Country country : ownedCountries) {
            int i = 0;
            List<Country> neighbors = country.getNeighboringCountries();
            while (i < neighbors.size() && !country.canInvade(neighbors.get(i))) {
                i++;
            }
            if (i < neighbors.size()) {
                canInvadeFrom.add(country);
            }
        }
        return canInvadeFrom;
    }
}
