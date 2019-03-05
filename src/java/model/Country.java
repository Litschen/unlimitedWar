package model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Country {

    //region static variables
    public static final int MIN_SOLDIERS_TO_INVADE = 2;
    public static final int MIN_SOLDIERS_TO_STAY = 1;
    public static final int ABSOLUTE_MIN_AMOUNT_THROWS = 1;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER = 3;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER = 2;
    //endregion

    //region data fields
    private String name;
    private int soldiersCount;
    private Player owner;
    private ArrayList<Country> neighboringCountries;
    //endregion

    public Country(String name, int soldiersCount, @NotNull Player owner) {
        this.name = name;
        this.soldiersCount = soldiersCount;
        this.owner = owner;
        this.neighboringCountries = new ArrayList<>();
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

    public ArrayList<Country> getNeighboringCountries() {
        return neighboringCountries;
    }
    //endregion

    /**
     * @param country
     * @return value is Bordering or not
     */
    public boolean isBordering(@NotNull Country country) {
        return this.neighboringCountries.contains(country);
    }


    /**
     * @param defenderCountry
     */
    public void invade(Country defenderCountry, int attackDiceCount, int defendDiceCount) {
        if (this.canInvade(defenderCountry)) {
            int[] attackerRolls = Dice.roll(attackDiceCount);
            int[] defenderRolls = Dice.roll(defendDiceCount);

            Casualties casualties = this.calculateCasualties(attackerRolls, defenderRolls);
            this.removeSoldiers(casualties.getCasualtiesAttacker());
            defenderCountry.removeSoldiers(casualties.getCasualtiesDefender());

            if (defenderCountry.getSoldiersCount() <= 0) {
                // switch owner of country
                defenderCountry.getOwner().getOwnedCountries().remove(defenderCountry);
                defenderCountry.setOwner(this.owner);
                this.owner.getOwnedCountries().add(defenderCountry);

                this.shiftSoldiers(attackDiceCount, defenderCountry);
            }
        }
    }

    /**
     * @return
     * @throws Exception
     */
    public int maxAmountDiceThrowsAttacker() throws Exception {
        int count = this.getSoldiersCount() - MIN_SOLDIERS_TO_STAY;
        if (count >= ABSOLUTE_MIN_AMOUNT_THROWS) {
            return count > ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER ? ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER : count;
        } else {
            throw new Exception("could not calculate maxAttackerDiceCount");
        }
    }

    /**
     * Number of dice determine attackers
     *
     * @param amountAttacker
     * @return by number of count
     */
    public int amountDiceThrowsDefender(int amountAttacker) {
        int amountDefender = amountAttacker - 1;
        int soldiers = this.getSoldiersCount();

        if (amountDefender <= ABSOLUTE_MIN_AMOUNT_THROWS) {
            amountDefender = ABSOLUTE_MIN_AMOUNT_THROWS;
        } else if (soldiers < amountDefender) {
            amountDefender = soldiers;
        }
        return amountDefender;
    }

    public void removeSoldiers(int amountOfSoldiers) {
        soldiersCount -= amountOfSoldiers;
    }

    /**
     * @param diceThrowsAttacker
     * @param diceThrowsDefender
     * @return by Casualties
     */
    public Casualties calculateCasualties(int[] diceThrowsAttacker, int[] diceThrowsDefender) {
        Casualties casualties = new Casualties(0, 0);
        for (int i = 0; i < diceThrowsDefender.length; i++) {
            if (diceThrowsDefender[i] >= diceThrowsAttacker[i]) {
                casualties.addCasualtiesAttacker();
            } else {
                casualties.addCasualtiesDefender();
            }
        }
        return casualties;
    }

    /**
     * @return value is Invade or not
     */
    public boolean canInvade(Country country) {
        return this.soldiersCount >= MIN_SOLDIERS_TO_INVADE && this.isBordering(country) &&
                this.owner != country.getOwner();
    }

    public boolean shiftSoldiers(int amountSoldiers, Country destination) {
        boolean canShift = amountSoldiers < getSoldiersCount() && amountSoldiers > 0;
        if (canShift) {
            this.setSoldiersCount(this.getSoldiersCount() - amountSoldiers);
            destination.setSoldiersCount(destination.getSoldiersCount() + amountSoldiers);
        }
        return canShift;
    }

}