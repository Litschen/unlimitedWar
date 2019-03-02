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
    public static final int METHOD_NOT_IMPLEMENTED_RETURN_VALUE = -1;
    //endregion

    //region data fields
    private String name;
    private int soldiersCount;
    private Player owner;
    private ArrayList<Country> neighboringCountries;
    //endregion

    /**
     * Constructor. Create Name of the Country, nummber of soldiers and Player
     *
     * @param name
     * @param soldiersCount
     * @param owner
     */
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
     * @param country
     */
    public void invade(Country country) {
        if (this.canInvade(country)) {

        }
    }

    /**
     * @return
     * @throws Exception
     */
    // /F0350/ Würfelanzahl bestimmen Angreifer
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
        int count = amountAttacker - MIN_SOLDIERS_TO_STAY;
        int amountDefenderDice = count;
        int soldiers = this.getSoldiersCount();

        if (count >= ABSOLUTE_MIN_AMOUNT_THROWS) {
            amountDefenderDice = ABSOLUTE_MIN_AMOUNT_THROWS;
        } else if (soldiers <= count) {
            amountDefenderDice = soldiers;
        }
        return amountDefenderDice;
    }

    /**
     * Add Soldier
     */
    public void addSoldier() {
        soldiersCount++;
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
    private boolean canInvade(Country country) {
        return this.soldiersCount >= MIN_SOLDIERS_TO_INVADE && this.isBordering(country) &&
                this.owner != country.getOwner();
    }

    public boolean shiftSoldiers(int mountSoldiers, Country destination) {
        if (mountSoldiers < getSoldiersCount() && mountSoldiers > 0) {
            this.setSoldiersCount(this.getSoldiersCount() - mountSoldiers);
            destination.setSoldiersCount(destination.getSoldiersCount() + mountSoldiers);
            return true;
        } else {
            return false;
        }
    }

}
