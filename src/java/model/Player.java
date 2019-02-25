package model;

import model.Interface.IBehavior;

import java.util.ArrayList;

public class Player{

    //region static variables
    //Countries needed to get a Soldier to place
    public static final int COUNTRY_WEIGHT = 3;
    //endregion

    //region data fields
    private String color;
    private String name;
    /**
     * Behavior determines how the player or even the user can act. In  player.java are only methods/field that are the
     * same for all players.
     */
    private IBehavior behavior;
    private ArrayList<Country> ownedCountries;
    //endregion

    /**Constructor. Create Player with color, name and behavior
     * @param color
     * @param name
     * @param behavior
     */
    //region constructor
    public Player(String color, String name, IBehavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
        this.ownedCountries = new ArrayList<>();
    }
    //endregion

    /**
     * @return by Player color
     */
    //region getter setter
    public String getPlayerColor() {
        return color;
    }

    /**
     * @return by Player name
     */
    public String getPlayerName() {
        return name;
    }

    /**
     * @return by get Behavior
     */
    public IBehavior getBehavior(){
        return behavior;
    }

    /**
     * @return by behavior own countries
     */
    public ArrayList<Country> getOwnedCountries() { return ownedCountries; }
    //endregion


    /**
     * @return by number of soldiers to place
     */
    public int calculateSoldiersToPlace() {
        return Math.max(COUNTRY_WEIGHT, getOwnedCountries().size() / COUNTRY_WEIGHT);
    }

}
