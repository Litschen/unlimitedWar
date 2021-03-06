package ch.zhaw.unlimitedWar.model.behavior;

import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.helpers.AttackCountryResult;
import ch.zhaw.unlimitedWar.model.helpers.AttackScore;
import ch.zhaw.unlimitedWar.model.helpers.AttackScoreComparator;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;
import ch.zhaw.unlimitedWar.model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StrategicBehavior implements Behavior {

    //region static variables
    private final static int COUNTRY_IS_WEAK_DEFENDED_BONUS = -10;
    private final static int MAX_SCORE_TO_SET_DEFENSIVE = 3;
    private final static int MAX_SCORE_TO_SET_CARD = 4;
    private final static Logger LOGGER = Logger.getLogger(StrategicBehavior.class.getName());
    //endregion

    @Override
    public Phase placeSoldiers(PlaceSoldiers placeSoldiers) {
        int soldiersToPlace = placeSoldiers.getSoldiersToPlace();

        for (List<AttackScore> scores = rateCountries(placeSoldiers.getOwnedCountries());  // set up
             !scores.isEmpty() && soldiersToPlace > 0;  // condition
             scores = rateCountries(placeSoldiers.getOwnedCountries())) {

            List<Card> cards = placeSoldiers.getPlayer().getCards();
            int cardIndex = useCard(scores, cards, placeSoldiers.getPlayer());
            if (!cards.isEmpty() && cardIndex >= 0) {
                Card card = cards.get(cardIndex);
                soldiersToPlace += card.getCardBonus(placeSoldiers.getPlayer());
                cards.remove(card);
            }

            Country countryToPlaceSoldiers;
            int setSoldiers = soldiersToPlace % MAX_SCORE_TO_SET_DEFENSIVE + 1; // +1 to set at least 1 soldier

            if (setSoldiers > soldiersToPlace) {
                setSoldiers = soldiersToPlace;
            }

            AttackScore attackScore = scores.get(scores.size() - 1);  // get last element

            if (attackScore.getScore() <= MAX_SCORE_TO_SET_DEFENSIVE) {
                countryToPlaceSoldiers = attackScore.getAttacker();
            } else {
                countryToPlaceSoldiers = scores.get(0).getAttacker();
            }

            countryToPlaceSoldiers.setSoldiersCount(countryToPlaceSoldiers.getSoldiersCount() + setSoldiers);
            soldiersToPlace -= setSoldiers;
        }

        return Phase.ATTACK;
    }

    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVE);
        List<AttackScore> scores = rateCountries(ownedCountries);
        scores.removeIf(attackScore -> attackScore.getAttacker().getSoldiersCount() == 1);
        int i = 0;
        boolean processNextScore;
        if (!scores.isEmpty()) {
            do {
                AttackScore currentScore = scores.get(i);
                Country attacker = currentScore.getAttacker();
                Country defender = currentScore.getDefender();

                try {
                    int attackerDice = attacker.maxAmountDiceThrowsAttacker();
                    int defenderDice = defender.amountDiceThrowsDefender(attackerDice);
                    while (attacker.canInvade(defender) && attackerDice > defenderDice) {
                        attacker.invade(defender, attackerDice, defenderDice);
                        if (attacker.canInvade(defender)) {
                            attackerDice = attacker.maxAmountDiceThrowsAttacker();
                            defenderDice = defender.amountDiceThrowsDefender(attackerDice);
                        }
                        if (attacker.getOwner() == defender.getOwner()) {
                            int attackerScore = rateCountry(attacker);
                            int defenderScore = rateCountry(defender);
                            boolean shifted = true;
                            while (shifted && defenderScore < attackerScore) {
                                shifted = attacker.shiftSoldiers(1, defender);
                                attackerScore = rateCountry(attacker);
                                defenderScore = rateCountry(defender);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Could not calculate AttackerDice", e);
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
            if (!neighborScores.isEmpty()) {
                Country src = neighborScores.get(0).getAttacker();
                int srcScore = neighborScores.get(0).getScore();

                if (destScore < srcScore) {
                    int soldiersToMove = Math.abs(srcScore - destScore) / 2;
                    int maxMoveSoldierCount = src.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY;
                    int soldiersToShift = soldiersToMove < maxMoveSoldierCount ? soldiersToMove : maxMoveSoldierCount;
                    src.shiftSoldiers(soldiersToShift, dest);
                }
            }
        }

        return Phase.SET;
    }

    private Map<Country, List<Country>> countryWithOwnNeighbor(List<Country> ownedCountries) {
        Map<Country, List<Country>> countriesWithNeighbor = new HashMap<>();

        for (Country currentCountry : ownedCountries) {
            List<Country> destCountry = new ArrayList<>();

            for (Country neighbor : currentCountry.getNeighboringCountries()) {
                if (currentCountry.getOwner() == neighbor.getOwner() && neighbor.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
                    destCountry.add(neighbor);
                }
            }
            if (!destCountry.isEmpty()) {
                countriesWithNeighbor.put(currentCountry, destCountry);
            }
        }

        return countriesWithNeighbor;
    }

    private List<AttackScore> rateCountries(List<Country> ownedCountries) {
        List<AttackScore> scores = new ArrayList<>();
        for (Country currentCountry : ownedCountries) {
            for (Country neighbor : currentCountry.getNeighboringCountries()) {
                int score = rateCountry(currentCountry);
                if (neighbor.getOwner() != currentCountry.getOwner()) {
                    scores.add(new AttackScore(score, currentCountry, neighbor));
                }
            }
        }
        scores.sort(new AttackScoreComparator());
        return scores;
    }

    private int rateCountry(Country country) {
        int score = country.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY;
        for (Country neighboringCountry : country.getNeighboringCountries()) {
            if (neighboringCountry.getOwner() != country.getOwner()) {
                score -= (neighboringCountry.getSoldiersCount() == Country.MIN_SOLDIERS_TO_STAY) ?
                        COUNTRY_IS_WEAK_DEFENDED_BONUS : neighboringCountry.getSoldiersCount();
            }
        }
        return score;
    }

    private int useCard(List<AttackScore> scores, List<Card> cards, Player player) {
        int cardIndex = -1;
        if (!cards.isEmpty() && (scores.get(0).getScore() <= MAX_SCORE_TO_SET_CARD || player.getOwnedCountries().size() == 1)) {
            Card cardToUse = cards.get(0);
            cardIndex = 0;
            for (Card card : cards) {
                if (cardToUse.getCardBonus(player) < card.getCardBonus(player)) {
                    cardToUse = card;
                    cardIndex = cards.indexOf(cardToUse);
                }
            }
        }

        return cardIndex;
    }
}
