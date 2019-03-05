package model;

import model.Interface.IBehavior;

import java.util.ArrayList;

public class Player {

    //region static variables
    public static final int COUNTRY_WEIGHT = 3;
    //endregion

    //region data fields
    private Enum color;
    private String name;
    private int attackDiceCount;
    private IBehavior behavior;
    private ArrayList<Country> ownedCountries;
    private int userSoldiersToPlace = 0;
    //endregion

    public Player(Enum color, String name, IBehavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
        this.ownedCountries = new ArrayList<>();
    }

    //region getter setter
    public Enum getPlayerColor() {
        return color;
    }

    public String getPlayerName() {
        return name;
    }

    public IBehavior getBehavior() {
        return behavior;
    }

    public ArrayList<Country> getOwnedCountries() {
        return ownedCountries;
    }

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
     * Countries needed to get a Soldier to place
     *
     * @return by number of soldiers to place
     */
    public int calculateSoldiersToPlace() {
        return Math.max(COUNTRY_WEIGHT, getOwnedCountries().size() / COUNTRY_WEIGHT);
    }

}
