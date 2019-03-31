package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
import model.helpers.MoveComperator;
import model.helpers.MoveCountry;
import model.interfaces.Behavior;

import java.util.*;

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
        List<MoveCountry> CountriesWithNeighbors = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            numberOfNeighbors = 0;
            for (Country neighborCountry : currentCountry.getNeighboringCountries()) {
                if (neighborCountry.getOwner() != currentCountry.getOwner()) {
                    numberOfNeighbors++;
                }
                CountriesWithNeighbors.add(new MoveCountry(numberOfNeighbors, currentCountry, neighborCountry));
            }
        }
        CountriesWithNeighbors.sort(new MoveComperator());
        List<MoveCountry> ownCountryNeigbors = new ArrayList<>();

        // Vergelicht, ob die beiden erste Länder die gleiche Anzahl der Nachbaren haben. Falls nicht, dann ist get(0) das Land mit den meisten Nachbaren
        if (CountriesWithNeighbors.get(0).getNumberOfNeighbors() != (CountriesWithNeighbors.get(1).getNumberOfNeighbors())) {
            // Nunn mussen die angreznden, eigenen Länder betrachtete werden. Welcher von ihnen haben am meisten soldaten?
            for (Country currentCountry : ownedCountries) { // Liste der eigenen Länder
                if(currentCountry.isBordering(currentCountry)){ // Liste der eigenen, angrenzdenden Länder.
                    for (Country currentCountryNeighbors : ownedCountries){
                        ownCountryNeigbors.add(new MoveCountry(CountriesWithNeighbors.get(0).getNeighbor().getSoldiersCount(), currentCountryNeighbors, ownedCountries));
                    }
                }
                Collections.list(ownCountryNeigbors);
                // Move:
                currentCountry.shiftSoldiers(MoveCountry.getNumberOfNeighbors(), fromCountry);

            }
            return Phase.SETTINGPHASE;
            //@Tina isch ja in MoveCountry gespeichertet
            CountriesWithNeighbors.get(0).getNumberOfNeighbors();
            CountriesWithNeighbors.get(0).getOwn();
            //für was isch das?

            // funktioniert nöt @Tina so würsch probiere z ahzahl vo nachbarländer zverschiebe das wetsch aber nid oder?
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
