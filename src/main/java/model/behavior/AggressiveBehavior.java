package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
import model.helpers.MoveComparator;
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
        List<MoveCountry> countriesWithNeighbors = createMoveCountryList(ownedCountries);
        if (!countriesWithNeighbors.isEmpty()) {
            countriesWithNeighbors.sort(new MoveComparator());
            MoveCountry moveCountry = getMoveCountry(countriesWithNeighbors, ownedCountries);
            if (moveCountry != null) {
                moveCountry.getNeighbor().shiftSoldiers(moveCountry.getNeighbor().getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY, moveCountry.getOwn());
            }
        }
        return Phase.SETTINGPHASE;

    }

    private MoveCountry getMoveCountry(List<MoveCountry> countriesWithNeighbors, List<Country> ownedCountries) {
        int index = 0;
        int totalMaxSoldiers = 0;
        MoveCountry selectedMoveCountry = null;
        MoveCountry moveCountry;
        do {
            int currentCountryMaxSoldiers = 0;
            moveCountry = countriesWithNeighbors.get(index);
            Country ownCountry = moveCountry.getOwn();
            int currentSoldiers = ownCountry.getSoldiersCount();
            for (Country currentCountry : ownCountry.getNeighboringCountries()) {
                if (ownedCountries.contains(currentCountry)) {
                    int currentCountryTmpMaxSoldiers = currentCountry.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY + currentSoldiers;
                    if (currentCountryMaxSoldiers < currentCountryTmpMaxSoldiers) {
                        currentCountryMaxSoldiers = currentCountryTmpMaxSoldiers;
                        moveCountry.setNeighbor(currentCountry);
                    }
                }
            }
            if (currentCountryMaxSoldiers > totalMaxSoldiers) {
                selectedMoveCountry = moveCountry;
                totalMaxSoldiers = currentCountryMaxSoldiers;
            }
            index++;
        } while (countriesWithNeighbors.size() > index + 1 && moveCountry.getNumberOfNeighbors() == countriesWithNeighbors.get(index + 1).getNumberOfNeighbors());
        return selectedMoveCountry;
    }

    private List<MoveCountry> createMoveCountryList(List<Country> ownedCountries) {
        List<MoveCountry> countriesWithNeighbors = new ArrayList<>();
        boolean countryShouldBeAdded;
        int numberOfNeighbors;
        for (Country currentCountry : ownedCountries) {
            countryShouldBeAdded = false;
            numberOfNeighbors = 0;
            for (Country neighborCountry : currentCountry.getNeighboringCountries()) {
                if (neighborCountry.getOwner() != currentCountry.getOwner()) {
                    numberOfNeighbors++;
                } else if (neighborCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
                    countryShouldBeAdded = true;
                }
            }
            if (countryShouldBeAdded) {
                countriesWithNeighbors.add(new MoveCountry(numberOfNeighbors, null, currentCountry));
            }
        }
        return countriesWithNeighbors;
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
