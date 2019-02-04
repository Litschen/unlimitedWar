package model;

import model.Interface.IBehavior;

public class Player{

    //region data fields
    private String color;
    private String name;
    /**
     * Behavior determines how the player or even the user can act. In  player.java are only methods/field that are the
     * same for all players.
     */
    private IBehavior behavior;

    //endregion

    //region constructor
    public Player(String color, String name, IBehavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
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
    //endregion

}
