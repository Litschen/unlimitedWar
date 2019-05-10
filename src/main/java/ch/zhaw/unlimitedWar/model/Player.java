package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.model.interfaces.Behavior;

import java.util.ArrayList;
import java.util.List;

public class Player {

    //region static variables
    public static final int COUNTRY_WEIGHT = 3;
    //endregion

    //region data fields
    private final Enum color;
    private String name;
    private int attackDiceCount;
    private Behavior behavior;
    private List<Country> ownedCountries;
    private List<Card> cards;
    private int soldiersToPlace = 0;
    private boolean playerGetCard;
    //endregion

    public Player(Enum color, String name, Behavior behavior) {
        this.color = color;
        this.name = name;
        this.behavior = behavior;
        this.ownedCountries = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.playerGetCard = true;
    }

    //region getter setter
    public Enum getPlayerColor() {
        return color;
    }

    public String getPlayerName() {
        return name;
    }

    public void setPlayerName(String name) {
        this.name = name;
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

    public boolean getPlayerGetCard() {
        return playerGetCard;
    }

    public void setPlayerGetCard(boolean playerGetCard) {
        this.playerGetCard = playerGetCard;
    }

    public List<Card> getCards() {
        return cards;
    }
    //endregion

    public void addOwnedCountries(List<Country> countries) {
        this.ownedCountries.addAll(countries);
    }

    public void addSoldiersToPlace(int additionalSoldiers) {
        setSoldiersToPlace(soldiersToPlace + additionalSoldiers);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public int calculateSoldiersToPlace(List<Continent> continents) {
        int continentBoni = 0;
        for (Continent continent : continents) {
            continentBoni += continent.getBonusForPlayer(this);
        }

        int countryCount = this.ownedCountries.size();
        int cityCount = (int) this.ownedCountries.stream().filter(country -> country.isCity()).count();
        int capitalCount = (int) this.ownedCountries.stream().filter(country -> country.isCapital()).count();

        return Math.max(COUNTRY_WEIGHT, ((countryCount + cityCount) / COUNTRY_WEIGHT)) + capitalCount + continentBoni;
    }

}
