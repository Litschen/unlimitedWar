package model;

import org.jetbrains.annotations.NotNull;

public class Country {

    //region static variables
    public static final int MIN_SOLDIERS_TO_INVADE = 2;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER = 3;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER = 2;
    public static final int METHOD_NOT_IMPLEMENTED_RETURN_VALUE = -1;
    public static final int COUNTRY_PIXEL_WIDTH = 35;
    public static final int COUNTRY_PIXEL_HEIGHT = 30;
    //endregion

    //region data fields
    private String name;
    private int soldiersCount;
    private Player owner;
    private Coordinates coordinates;
    private Country hasConnectorWith;
    //endregion

    /** Constructor. Create Name of the Country, nummber of soldiers and Player
     * @param name
     * @param soldiersCount
     * @param owner
     */
    //region constructors
    public Country(String name, int soldiersCount, @NotNull Player owner) {
        this.name = name;
        this.soldiersCount = soldiersCount;
        this.owner = owner;
    }

    /** Constructor. Create Name of the Country, nummber of soldiers, Player and coordinates
     * @param name
     * @param soldiersCount
     * @param owner
     * @param coordinates
     */
    public Country(String name, int soldiersCount, @NotNull Player owner, Coordinates coordinates) {
        this.name = name;
        this.soldiersCount = soldiersCount;
        this.owner = owner;
        this.coordinates = coordinates;
    }
    //endregion

    /**
     * @return by Name
     */
    //region getter setter
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return by number of Soldiers
     */
    public int getSoldiersCount() {
        return soldiersCount;
    }

    /**
     * @param soldiersCount
     */
    public void setSoldiersCount(int soldiersCount) {
        this.soldiersCount = soldiersCount;
    }

    /**
     * @return by Owner
     */
    public Player getOwner() {
        return owner;
    }

    public void setOwner(@NotNull Player owner) {
        this.owner = owner;
    }

    /**
     * @return Coordinates
     */
    public Coordinates getCoordinates() { return coordinates; }

    /**
     * @param coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setHasConnector(Country country) {this.hasConnectorWith = country;}
    //endregion

    /**
     * @param country
     * @return value is Bordering or not
     */
    public boolean isBordering(@NotNull Country country) {
        return haveConnector(country) || (touchVertically(country) || touchHorizontal(country));
    }

    private boolean touchVertically(Country country){
        Coordinates thisCoordinates = this.getCoordinates();
        Coordinates countryCoordinates = country.getCoordinates();

        int thisVerticalValue = (thisCoordinates.getTop() > 0) ?
                BoardBean.BOARD_PIXEL_HEIGHT - thisCoordinates.getTop() : thisCoordinates.getBottom();
        int countryVerticalValue = (countryCoordinates.getTop() > 0) ?
                BoardBean.BOARD_PIXEL_HEIGHT - countryCoordinates.getTop() : countryCoordinates.getBottom();
        return false;
    }

    private boolean touchHorizontal(Country country){
        return false;
    }

    private boolean haveConnector(Country country){ return this.hasConnectorWith == country; }

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
        int count = this.getSoldiersCount() - 1;
        if (count > 0) {
            return count > 3 ? 3 : count;
        } else {
            throw new Exception("could not calculate maxAttackerDiceCount");
        }
    }

    /**Number of dice determine attackers
     * @param amountAttacker
     * @return by number of count
     */
    public int amountDiceThrowsDefender(int amountAttacker) {
        int count = amountAttacker - 1;
        int soldiers = this.getSoldiersCount();

        if (count == 0) {
            return 1;
        } else if (soldiers <= count) {
            return soldiers;
        }
        return count;
    }

    /**
     * Add Soldier
     */
    public void addSoldier() {
        soldiersCount++;
    }

    /**
     * @param diceThrowsAttacker
     * @param diceThrowsDefender
     * @return by Casualties
     */
    public Casualties calculateCasualties(int[] diceThrowsAttacker, int[] diceThrowsDefender) {
        return new Casualties(METHOD_NOT_IMPLEMENTED_RETURN_VALUE, METHOD_NOT_IMPLEMENTED_RETURN_VALUE);
    }

    /**
     * @return value is Invade or not
     */
    private boolean canInvade(Country country) {
        return this.soldiersCount >= MIN_SOLDIERS_TO_INVADE && this.isBordering(country);
    }
}
