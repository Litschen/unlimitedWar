package ch.zhaw.unlimitedWar.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Continent {
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
        Iterator it = countries.iterator();
        boolean samePlayer = true;
        while (it.hasNext() && samePlayer) {
            samePlayer = ((Country) it.next()).getOwner() == player;
        }
        return samePlayer ? soldierBonus : 0;
    }

    //endregion


    public void addCountry(Country country) {
        this.countries.add(country);
    }

    public void addCountries(List<Country> countries) {
        this.countries.addAll(countries);
    }

}
