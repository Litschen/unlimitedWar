package ch.zhaw.unlimitedWar.model;

import java.util.ArrayList;
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
    public List<Country> getCountries() {
        return countries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int getBonusForPlayer(Player player) {
        return 0;
    }

    //endregion


    private void addCountry(Country country) {

    }

    private void addCountries(List<Country> countries) {

    }

}
