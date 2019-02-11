package model;

import model.Interface.IBehavior;

import java.util.ArrayList;

public class Player{

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

    //region constructor
    public Player(String color, String name, IBehavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
        this.ownedCountries = new ArrayList<>();
    }
    //endregion

    //region getter setter
    public String getPlayerColor() {
        return color;
    }

    public String getPlayerName() {
        return name;
    }

    public IBehavior getBehavior(){
        return behavior;
    }

    public ArrayList<Country> getOwnedCountries() { return ownedCountries;
    }
    //endregion

}
