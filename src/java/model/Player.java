package model;

import model.Interface.IBehavior;

import java.util.ArrayList;

public class Player{

    //region static variables
    //Countries needed to get a Soldier to place
    public static final int COUNTRY_WEIGHT = 3;
    //endregion

    //region data fields
    private Enum color;
    private String name;
    private int attackDiceCount;
    /**
     * Behavior determines how the player or even the user can act. In  player.java are only methods/field that are the
     * same for all players.
     */
    private IBehavior behavior;
    private ArrayList<Country> ownedCountries;
    private int userSoldiersToPlace = 0;
    //endregion

    /**Constructor. Create Player with color, name and behavior
     * @param color
     * @param name
     * @param behavior
     */
    //region constructor
    public Player(Enum color, String name, IBehavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
        this.ownedCountries = new ArrayList<>();
    }
    //endregion

    //region getter setter
    public Enum getPlayerColor() {
        return color;
    }

    public String getPlayerName() {
        return name;
    }

    public IBehavior getBehavior(){
        return behavior;
    }

    public ArrayList<Country> getOwnedCountries() { return ownedCountries; }

    public int getUserSoldiersToPlace() {
        return userSoldiersToPlace;
    }

    public void setUserSoldiersToPlace(int userSoldiersToPlace) {
        this.userSoldiersToPlace = userSoldiersToPlace;
    }

    public int getAttackDiceCount() {
        return attackDiceCount;
    }

    public void setAttackDiceCount(int attackDiceCount) {
        this.attackDiceCount = attackDiceCount;
    }

    //endregion0

    /**
     * @return by number of soldiers to place
     */
    public int calculateSoldiersToPlace() {
        return Math.max(COUNTRY_WEIGHT, getOwnedCountries().size() / COUNTRY_WEIGHT);
    }

    public void addOwnedCountry(Country newCountry) {
        if (!this.ownedCountries.contains(newCountry)) {
            this.ownedCountries.add(newCountry);
        }
    }

    public void removeOwnedCountry(Country country) {
        if (this.ownedCountries.contains(country)) {
            this.ownedCountries.remove(country);
        }
    }

}
