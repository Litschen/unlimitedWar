package ch.zhaw.unlimitedWar.model;

import org.jetbrains.annotations.NotNull;

public class Card {
    // region static variables
    public final static int OWNING_BONUS = 1;
    private final static int MIN_BONUS = 1;
    private final static int MAX_BONUS = 2;
    // end

    private int soldierBonus;
    private Country country;

    // region getter setter
    public int getSoldierBonus() {
        return soldierBonus;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    // endregion

    public Card(@NotNull Country country) {
        this.country = country;
        this.soldierBonus = randomSoldierBonus();
    }

    public String getCardName() {
        return country.getName();
    }

    public int getCardBonus(Player player) {
        int owningBonus = isPlayerOwner(player) ? OWNING_BONUS : 0;
        return owningBonus + soldierBonus;
    }

    public boolean isPlayerOwner(Player player) {
        return player.equals(country.getOwner());
    }

    private int randomSoldierBonus() {
        return Dice.roll(MIN_BONUS, MAX_BONUS);
    }
}
