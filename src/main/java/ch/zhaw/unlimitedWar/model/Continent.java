package ch.zhaw.unlimitedWar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Continent {

    public static final String STANDARD_TEXT_COLOR = "black";

    //region data fields
    private int soldierBonus;
    private List<Country> countries;
    private String name;
    //endregion


    //region Constructors
    public Continent(int soldierBonus, String name) {
        this.soldierBonus = soldierBonus;
        this.name = name;
        this.countries = new ArrayList<>();
    }

    public Continent(int soldierBonus, List<Country> countries, String name) {
        this.soldierBonus = soldierBonus;
        this.countries = countries;
        this.name = name;
    }
    //endregion

    //region getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonusForPlayer(Player player) {
        return continentOwnedByOnePlayer(player) ? soldierBonus : 0;
    }

    public int getSoldierBonus() {
        return soldierBonus;
    }
    //endregion

    public String getTextColor() {
        Player player = countries.get(0).getOwner();
        return continentOwnedByOnePlayer(player) ? player.getPlayerColor().toString() : STANDARD_TEXT_COLOR;
    }

    public void addCountry(Country country) {
        this.countries.add(country);
    }

    public void addCountries(List<Country> countries) {
        this.countries.addAll(countries);
    }

    private boolean continentOwnedByOnePlayer(Player player) {
        boolean samePlayer = true;
        Iterator it = countries.iterator();
        while (it.hasNext() && samePlayer) {
            samePlayer = ((Country) it.next()).getOwner() == player;
        }
        return samePlayer;
    }

}
