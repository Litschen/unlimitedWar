package ch.zhaw.unlimitedWar.model.behavior;

import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Dice;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.helpers.AttackCountryResult;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;
import ch.zhaw.unlimitedWar.model.interfaces.Behavior;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomBehavior implements Behavior {

    //region static variables
    // try to attack in x cases out of 10 cases
    private final static int USE_CARD = 3;
    private final static int AGGRESSIVENESS = 7;
    //continue to attack in x cases out of 10 cases
    private final static int STUBBORNNESS = 9;
    //move soldiers in x cases out of 10 cases
    private final static int MOVE_WILLINGNESS = 8;
    private final static Logger LOGGER = Logger.getLogger(RandomBehavior.class.getName());
    private final static int MIN_DICE_RANGE = 0;
    private final static int MAX_DICE_RANGE = 10;
    //endregion


    @Override
    public Phase placeSoldiers(PlaceSoldiers placeSoldiers) {
        List<Country> ownedCountries = placeSoldiers.getOwnedCountries();
        int soldiersToPlace = placeSoldiers.getSoldiersToPlace();

        List<Card> cards = placeSoldiers.getPlayer().getCards();
        if (!cards.isEmpty() && willUseCard()) {
            Card card = cards.get(Dice.roll(0, cards.size() - 1));
            soldiersToPlace += card.getCardBonus(placeSoldiers.getPlayer());
            cards.remove(card);
        }

        while (soldiersToPlace > 0) {
            Country country = ownedCountries.get(ThreadLocalRandom.current()
                    .nextInt(0, ownedCountries.size()));
            int placedSoldiers = ThreadLocalRandom.current()
                    .nextInt(0, soldiersToPlace + 1);
            country.setSoldiersCount(country.getSoldiersCount() + placedSoldiers);
            soldiersToPlace = soldiersToPlace - placedSoldiers;
        }

        return Phase.ATTACK;
    }

    @Override
    public AttackCountryResult attackCountry(List<Country> allCountries, List<Country> ownedCountries) {
        AttackCountryResult result = new AttackCountryResult(Phase.MOVE);
        while (willAttack()) {
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            for (Country targetCountry : selectedCountry.getNeighboringCountries()) {
                try {

                    while (willContinueAttack() && selectedCountry.canInvade(targetCountry)) {
                        int attackerDice = selectedCountry.maxAmountDiceThrowsAttacker();
                        selectedCountry.invade(targetCountry, attackerDice, targetCountry.amountDiceThrowsDefender(attackerDice));
                        selectedCountry.shiftSoldiers(Dice.roll(0,
                                selectedCountry.getSoldiersCount() - Country.MIN_SOLDIERS_TO_STAY), targetCountry);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Could not calculate AttackerDice", e);
                }
            }
        }
        return result;
    }

    @Override
    public Phase moveSoldiers(List<Country> allCountries, List<Country> ownedCountries) {
        boolean hasMovedSoldiers = false;
        while (willMoveSoldiers() && !hasMovedSoldiers) {
            Country selectedCountry = ownedCountries.get(Dice.roll(0, ownedCountries.size() - 1));
            if (selectedCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
                Country selectedNeighbor = selectedCountry.getNeighboringCountries().get(Dice.roll(0,
                        selectedCountry.getNeighboringCountries().size() - 1));
                if (selectedCountry.getOwner().getOwnedCountries().contains(selectedNeighbor)) {
                    selectedCountry.shiftSoldiers(Dice.roll(1, selectedCountry.getSoldiersCount() -
                            Country.MIN_SOLDIERS_TO_STAY), selectedNeighbor);
                    hasMovedSoldiers = true;
                }
            }

        }
        return Phase.SET;
    }

    private boolean willUseCard() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= USE_CARD;
    }

    private boolean willAttack() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= AGGRESSIVENESS;
    }

    private boolean willContinueAttack() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= STUBBORNNESS;
    }

    private boolean willMoveSoldiers() {
        return Dice.roll(MIN_DICE_RANGE, MAX_DICE_RANGE) <= MOVE_WILLINGNESS;
    }

}
