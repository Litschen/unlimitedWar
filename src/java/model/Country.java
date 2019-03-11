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
    public static final String CALCULATION_ERROR = "could not calculate maxAttackerDiceCount";
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
     * Checks if the parameter country is a neighbor of the country from which this method was called.
     *
     * @param country to check if is neighbor
     * @return true if country param is a neighbor otherwise false.
     */
    public boolean isBordering(@NotNull Country country) {
        return neighboringCountries.contains(country);
    }


    /**
     * Country invades the param defenderCountry, this method handles everything concerning invasion,
     * including: Calling calculateCasualties, processing said casualties,
     * changing ownership of defenderCountry if necessary and shifting soldiers in that case
     *
     * @param defenderCountry country to be invaded, it defends itself against the invasion
     * @param attackDiceCount amount of Dices the attacker is using to attack the defenderCountry
     * @param defendDiceCount amount of Dices the defender is using to defend
     */
    public void invade(Country defenderCountry, int attackDiceCount, int defendDiceCount) {
        if (canInvade(defenderCountry)) {
            int[] attackerRolls = Dice.roll(attackDiceCount);
            int[] defenderRolls = Dice.roll(defendDiceCount);

            Casualties casualties = calculateCasualties(attackerRolls, defenderRolls);
            removeSoldiers(casualties.getCasualtiesAttacker());
            defenderCountry.removeSoldiers(casualties.getCasualtiesDefender());

            if (defenderCountry.getSoldiersCount() <= 0) {
                // switch owner of country
                defenderCountry.getOwner().getOwnedCountries().remove(defenderCountry);
                defenderCountry.setOwner(owner);
                owner.getOwnedCountries().add(defenderCountry);

                shiftSoldiers(attackDiceCount, defenderCountry);
            }
        }
    }


    /**
     * Calculates the maximum of Dices the Attacker can use to invade from this country
     *
     * @return maximum int of Dices
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
     * Calculates the amount of dices the defender HAS to use to defend his country during an invasion
     *
     * @param amountAttacker dices the attacker is using to invade
     * @return amount of dices to use
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
     * Removes the specified amount of Soldiers from this country
     *
     * @param amountOfSoldiers the be removed
     */
    public void removeSoldiers(int amountOfSoldiers) {
        if(amountOfSoldiers < 0 || amountOfSoldiers > soldiersCount ){
            throw new IllegalArgumentException("Amount has to be at least 0 and max die amount of soldiers present on this country");
        }
        soldiersCount -= amountOfSoldiers;
    }


    /**
     * Calculates the casualties inflicted upon the defender and attacker during an country invasion
     *
     * @param diceThrowsAttacker int[] of the dices thrown by the attacker
     * @param diceThrowsDefender int[] of the dices thrown by the defender
     * @return Casualties object with saved casualties inflicted upon both sides
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
     * Checks of the specified country can be invaded from calling country.
     *
     * @param country to check if can be invaded
     * @return true if invasion is possible
     */
    public boolean canInvade(Country country) {
        return soldiersCount >= MIN_SOLDIERS_TO_INVADE && isBordering(country) &&
                owner != country.getOwner();
    }

    /**
     * Shifts the specified amount of soldiers from calling country to destination param country
     *
     * @param amountSoldiers to be shifted
     * @param destination    country the soldiers should be shifted to
     * @return true if shifted successful
     */
    public boolean shiftSoldiers(int amountSoldiers, Country destination) {

        boolean canShift = amountSoldiers < getSoldiersCount()
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