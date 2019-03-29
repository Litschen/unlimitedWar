package model;

import model.enums.EventType;
import model.events.CasualtiesEvent;
import model.events.ConquerEvent;
import model.events.DiceEvent;
import model.helpers.Casualties;
import model.interfaces.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Country {

    //region static variables
    public static final int MIN_SOLDIERS_TO_INVADE = 2;
    public static final int MIN_SOLDIERS_TO_STAY = 1;
    public static final int ABSOLUTE_MIN_AMOUNT_THROWS = 1;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER = 3;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER = 2;
    public static final String CALCULATION_ERROR = "could not calculate maxAttackerDiceCount";
    //endregion

    //region data fields
    private String name;
    private int soldiersCount;
    private Player owner;
    private List<Country> neighboringCountries;
    private boolean selected;
    //endregion

    public Country(String name, int soldiersCount, @NotNull Player owner) {
        this.name = name;
        this.soldiersCount = soldiersCount;
        this.owner = owner;
        this.neighboringCountries = new ArrayList<>();
        this.selected = false;
    }

    //region getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoldiersCount() {
        return soldiersCount;
    }

    public void setSoldiersCount(int soldiersCount) {
        this.soldiersCount = soldiersCount;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(@NotNull Player owner) {
        this.owner = owner;
    }

    public List<Country> getNeighboringCountries() {
        return neighboringCountries;
    }

    // TODO: use this method instead of call getter & add neighbors
    public void addNeighboringCountries(List<Country> neighboringCountries) {
        this.neighboringCountries.addAll(neighboringCountries);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    //endregion


    /**
     * @param country
     * @return
     */
    public boolean isBordering(@NotNull Country country) {
        return neighboringCountries.contains(country);
    }

    public List<Event> invade(Country defenderCountry, int attackDiceCount, int defendDiceCount) {
        List<Event> occurredEvents = new ArrayList<>();
        if (canInvade(defenderCountry)) {

            List<Integer> attackerRolls;
            List<Integer> defenderRolls;

            attackerRolls = Dice.roll(attackDiceCount);
            defenderRolls = Dice.roll(defendDiceCount);

            Casualties casualties = calculateCasualties(attackerRolls, defenderRolls);

            removeSoldiers(casualties.getCasualtiesAttacker());
            defenderCountry.removeSoldiers(casualties.getCasualtiesDefender());

            if (defenderCountry.getSoldiersCount() <= 0) {
                // switch owner of country
                defenderCountry.getOwner().getOwnedCountries().remove(defenderCountry);
                defenderCountry.setOwner(owner);
                owner.getOwnedCountries().add(defenderCountry);

                shiftSoldiers(attackDiceCount, defenderCountry);
                occurredEvents.add(new ConquerEvent(owner.getPlayerName()));
            }
            occurredEvents.add(new CasualtiesEvent(casualties));
            occurredEvents.add(new DiceEvent(attackerRolls, EventType.AttackerDiceEvent, owner.getPlayerName()));
            occurredEvents.add(new DiceEvent(defenderRolls, EventType.DefenderDiceEvent, defenderCountry.getOwner().getPlayerName()));

        }
        return occurredEvents;
    }


    /**
     * @throws Exception if the amount of Dices is below minimum allowed < ABSOLUTE_MIN_AMOUNT_THROWS
     */
    public int maxAmountDiceThrowsAttacker() throws Exception {
        int count = getSoldiersCount() - MIN_SOLDIERS_TO_STAY;
        if (count >= ABSOLUTE_MIN_AMOUNT_THROWS) {
            return count > ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER ? ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER : count;
        } else {
            throw new Exception(CALCULATION_ERROR);
        }
    }


    /**
     * Gives the number of defenders soldiers when it's been attacked by someone.
     * @param amountAttacker the soldiers who attack from the opponent
     * @return
     */
    public int amountDiceThrowsDefender(int amountAttacker) {
        int amountDefender = amountAttacker - 1;
        int soldiers = getSoldiersCount();

        if (amountDefender <= ABSOLUTE_MIN_AMOUNT_THROWS) {
            amountDefender = ABSOLUTE_MIN_AMOUNT_THROWS;
        } else if (soldiers < amountDefender) {
            amountDefender = soldiers;
        }
        return amountDefender;
    }

    /**
     * Remove soldiers as long as the number of soldiers are bigger than 0 or the number to delete soldiers will not be negative
     *
     * @param amountOfSoldiers is the number of soldiers to be deleted
     */
    public void removeSoldiers(int amountOfSoldiers) {
        if (amountOfSoldiers < 0 || amountOfSoldiers > soldiersCount) {
            throw new IllegalArgumentException("Amount has to be at least 0 and max die amount of soldiers present on this country");
        }
        soldiersCount -= amountOfSoldiers;
    }


    /**
     * @return Casualties object with saved casualties inflicted upon both sides
     */
    public Casualties calculateCasualties(List<Integer> diceThrowsAttacker, List<Integer> diceThrowsDefender) {
        Casualties casualties = new Casualties(0, 0);
        for (int i = 0; i < diceThrowsDefender.size(); i++) {
            if (diceThrowsDefender.get(i) >= diceThrowsAttacker.get(i)) {
                casualties.addCasualtiesAttacker();
            } else {
                casualties.addCasualtiesDefender();
            }
        }
        return casualties;
    }

    /**
     * returns a boolean if a country can attack
     *
     * @param country which attacks
     * @return true, if the number of soldiers is greater than or equal to
     * the minimum number of opponents soldiers to invade, the land to attack
     * is a neighboring country and not the own country from player
     */
    public boolean canInvade(Country country) {
        return soldiersCount >= MIN_SOLDIERS_TO_INVADE && isBordering(country) &&
                owner != country.getOwner();
    }

    /**
     * Move the soldiers to another country, which is adjacent, number of amount Soldiers ist
     * greater than 0 and  belongs to player.
     *
     * @return true if shifted successful
     */
    public boolean shiftSoldiers(int amountSoldiers, Country destination) {

        boolean canShift = getSoldiersCount() - amountSoldiers >= MIN_SOLDIERS_TO_STAY
                && amountSoldiers > 0
                && isBordering(destination)
                && getOwner() == destination.getOwner();
        if (canShift) {
            setSoldiersCount(getSoldiersCount() - amountSoldiers);
            destination.setSoldiersCount(destination.getSoldiersCount() + amountSoldiers);
        }
        return canShift;
    }

}