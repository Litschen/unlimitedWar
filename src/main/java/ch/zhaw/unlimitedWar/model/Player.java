package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.List;

public class Player {

    //region static variables
    public static final int COUNTRY_WEIGHT = 3;
    //endregion

    //region data fields
    private Enum color;
    private String name;
    private int attackDiceCount;
    private Behavior behavior;
    private ArrayList<Country> ownedCountries;
    private int soldiersToPlace = 0;
    //endregion

    public Player(Enum color, String name, Behavior behavior) {
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

    public Behavior getBehavior() {
        return behavior;
    }

    public List<Country> getOwnedCountries() {
        return ownedCountries;
    }

    public int getSoldiersToPlace() {
        return soldiersToPlace;
    }

    public void setSoldiersToPlace(int soldiersToPlace) {
        this.soldiersToPlace = soldiersToPlace;
    }

    public int getAttackDiceCount() {
        return attackDiceCount;
    }

    public void setAttackDiceCount(int attackDiceCount) {
        this.attackDiceCount = attackDiceCount;
    }

    public void setPlayerName(String name){ this.name = name; }

    //endregion

    public int calculateSoldiersToPlace(List<Continent> continents) {
        int continentBoni = 0;
        for (Continent continent : continents) {
            continentBoni += continent.getBonusForPlayer(this);
        }
        return Math.max(COUNTRY_WEIGHT, getOwnedCountries().size() / COUNTRY_WEIGHT) + continentBoni;
    }

}
