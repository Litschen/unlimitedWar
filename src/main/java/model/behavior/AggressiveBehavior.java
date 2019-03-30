package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
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

        int numberOfNeighbors = 0;

        List<MoveCountry> ListCountriesWithNeighbors = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            for (Country neighborCountry : currentCountry.getNeighboringCountries()) {
                if (neighborCountry.isBordering(neighborCountry)) { // hat das Land einen Nachbaren          @Tina du luegsch of es land nachbar vo sicher selber isch?
                    numberOfNeighbors++; //countent die Zahl nach oben           @Tina die zahl wird nie wieder uf null gesetzt => nöchst land fangt scho mit z.B 4 nachbare ah
                }
                ListCountriesWithNeighbors.add(new MoveCountry((numberOfNeighbors, currentCountry, neighborCountry));
                // fügt der Land, die Nachbaren und den counter wie viel in die liste
            }
        }

        return Phase.SETTINGPHASE;
    }

    //@ Tina wieso returnt das int sötts nid MoveCountry zrug geh
    private int mostNeighbors(ArrayList<MoveCountry> listOfNeighbor) {
        //@Tina min vorschlag war dass es mit emne Comperator machsch wie de AttackscoreComperator.java
        //Dänn wär die funktion na:
        //listOfNeighbor.sort(din super duper comperator);
        //return listOfNeigbar.get(0);
        //oder du lasch die funktion ganz weg wells nur no zwei ziele wäret ¯\_(ツ)_/¯ din entscheid
        int biggestNumberOfNeighbors = 0;
        for (int i = 0; i < listOfNeighbor.size(); i++) {
            // wie soll ich die Summen vergleichen?
            if (listOfNeighbor.get(i) > biggestNumberOfNeighbors) {
                biggestNumberOfNeighbors = listOfNeighbor.get(i);
            }
        }
        return biggestNumberOfNeighbors;
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
