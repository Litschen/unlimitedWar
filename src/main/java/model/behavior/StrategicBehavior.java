package model.behavior;

import model.Country;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
import model.helpers.AttackScore;
import model.helpers.AttackScoreComperator;
import model.interfaces.Behavior;

import java.util.*;

/**
 * TODO in MS2
 */
public class StrategicBehavior implements Behavior {

    private final static int COUNTRY_IS_WEAK_DEFENDED_BONUS = -10;
    private final static int MAX_SCORE_TO_SET_DEFENSIVE = 3;

    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
        for (List<AttackScore> scores = rateCountries(ownedCountries);
             !scores.isEmpty() && soldiersToPlace > 0;
             scores = rateCountries(ownedCountries)) {

            Country c;
            int setSoldiers = soldiersToPlace % 3 + 1;
            AttackScore attackScore = scores.get(scores.size() - 1);  // get last element

            if (attackScore.getScore() <= MAX_SCORE_TO_SET_DEFENSIVE) {
                c = attackScore.getAttacker();
            } else {
                c = scores.get(0).getAttacker();
            }

            c.setSoldiersCount(c.getSoldiersCount() + setSoldiers);
            soldiersToPlace -= setSoldiers;
        }

        return Phase.ATTACKPHASE;
    }

    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        List<AttackScore> scores = rateCountries(ownedCountries);
        scores.removeIf(attackScore -> attackScore.getAttacker().getSoldiersCount() == 1);
        int i = 0;
        boolean processNextScore;
        if (!scores.isEmpty()) {
            do {
                AttackScore currentScore = scores.get(i);
                Country attacker;
                Country defender;
                int attackerDice = 0;
                int defenderDice = 0;

                attacker = currentScore.getAttacker();
                defender = currentScore.getDefender();

                try {
                    while (attacker.canInvade(defender) && attackerDice >= defenderDice) {
                        attackerDice = attacker.maxAmountDiceThrowsAttacker();
                        defenderDice = defender.amountDiceThrowsDefender(attackerDice);
                        attacker.invade(defender, attackerDice, defenderDice);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
                processNextScore = false;
                if (i < scores.size()) {
                    processNextScore = scores.get(i).getScore() > Math.abs(COUNTRY_IS_WEAK_DEFENDED_BONUS);
                }
            } while (processNextScore);
        }
        return result;
    }

    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        Map<Country, List<Country>> destCountryMap = countryWithOwnNeighbor(ownedCountries);
        List<AttackScore> scores = rateCountries(new ArrayList(destCountryMap.keySet()));

        if (!scores.isEmpty()) {
            Collections.reverse(scores);
            Country dest = scores.get(0).getAttacker();
            int destScore = scores.get(0).getScore();

            List<AttackScore> neighborScores = rateCountries(destCountryMap.get(dest));
            Country src = neighborScores.get(0).getAttacker();
            int srcScore = neighborScores.get(0).getScore();

            if (destScore < srcScore) {
                int scoreDiff = Math.abs(Math.abs(srcScore) - Math.abs(destScore));
                int maxMoveSoldierCount = src.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY;
                int soldiersToShift = scoreDiff < maxMoveSoldierCount ? scoreDiff : maxMoveSoldierCount;
                src.shiftSoldiers(soldiersToShift, dest);
                System.out.println("shift: " + soldiersToShift);
            }
        }

        return Phase.SETTINGPHASE;
    }

    private Map<Country, List<Country>> countryWithOwnNeighbor(List<Country> ownedCountries) {
        Map<Country, List<Country>> countriesWithNeighbor = new HashMap<>();

        for (Country currentCountry : ownedCountries) {
            for (Country neighbor : currentCountry.getNeighboringCountries()) {
                List<Country> c = new ArrayList<>();
                if (currentCountry.getOwner() == neighbor.getOwner() && neighbor.getSoldiersCount() > 1) {
                    c.add(neighbor);
                }
                if (!c.isEmpty()) {
                    countriesWithNeighbor.put(currentCountry, c);
                }
            }
        }

        return countriesWithNeighbor;
    }

    private List<AttackScore> rateCountries(List<Country> ownedCountries) {
        List<AttackScore> scores = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            for (Country neighbor : currentCountry.getNeighboringCountries()) {
                int attackScore = currentCountry.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY;
                if (neighbor.getOwner() != currentCountry.getOwner()) {
                    attackScore -= (neighbor.getSoldiersCount() == Country.MIN_SOLDIERS_TO_STAY) ? COUNTRY_IS_WEAK_DEFENDED_BONUS : neighbor.getSoldiersCount();
                    scores.add(new AttackScore(attackScore, currentCountry, neighbor));
                }
            }
        }
        scores.sort(new AttackScoreComperator());
        return scores;
    }


}
