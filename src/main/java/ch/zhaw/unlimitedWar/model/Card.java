package ch.zhaw.unlimitedWar.model;

public class Card {
    private final static int OWNING_BONUS = 1;

    private int soldierBonus;
    private Country country;

    // region getter setter
    public int getSoldierBonus() {
        return soldierBonus;
    }

    public void setSoldierBonus(int soldierBonus) {
        this.soldierBonus = soldierBonus;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // endregion

    public int getCardBonus(Player player) {
        int owningBonus = player.equals(country.getOwner()) ? OWNING_BONUS : 0;
        return owningBonus + soldierBonus;
    }
}
