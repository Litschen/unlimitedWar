package model.behavior;

import model.Country;
import model.enums.Phase;
import model.helpers.AttackCountryResult;
import model.helpers.AttackScore;
import model.helpers.AttackScoreComperator;
import model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO in MS2
 */
public class StrategicBehavior implements Behavior {

    private final static int COUNTRY_IS_WEAK_DEFENDED_BONUS = -10;

    @Override
    public Phase placeSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace) {
        /*for (List<AttackScore> scores = rateCountries(ownedCountries);
             !scores.isEmpty() && soldiersToPlace > 0;
              scores = rateCountries(ownedCountries)) {

            Collections.reverse(scores);
            Country c = scores.get(0).getAttacker();
            int setSoldiers = soldiersToPlace % 3 + 1;
            c.setSoldiersCount(c.getSoldiersCount() + setSoldiers);
            soldiersToPlace -= setSoldiers;
        }*/

        return Phase.ATTACKPHASE;
    }

    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVINGPHASE);
        List<AttackScore> scores = rateCountries(ownedCountries);
        int i = 0;
        boolean processNextScore;
        if (!scores.isEmpty()) {
            do {
                AttackScore currentScore = scores.get(i);
                Country attacker;
                Country defender;
                int attackerDice;
                int defenderDice;

                attacker = currentScore.getAttacker();
                defender = currentScore.getDefender();

                try {
                    while (attacker.canInvade(defender)) {
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
        return Phase.SETTINGPHASE;
    }


    private List<AttackScore> rateCountries(List<Country> ownedCountries) {
        List<AttackScore> scores = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            if (currentCountry.getSoldiersCount() >= Country.MIN_SOLDIERS_TO_INVADE) {
                for (Country neighbor : currentCountry.getNeighboringCountries()) {
                    int attackScore = currentCountry.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY;
                    if (neighbor.getOwner() != currentCountry.getOwner()) {
                        attackScore -= (neighbor.getSoldiersCount() == Country.MIN_SOLDIERS_TO_STAY) ? COUNTRY_IS_WEAK_DEFENDED_BONUS : neighbor.getSoldiersCount();
                        scores.add(new AttackScore(attackScore, currentCountry, neighbor));
                    }
                }
            }
        }
        scores.sort(new AttackScoreComperator());
        return scores;
    }


}
