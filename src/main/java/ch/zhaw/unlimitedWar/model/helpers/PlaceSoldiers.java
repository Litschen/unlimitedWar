package ch.zhaw.unlimitedWar.model.helpers;

import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;

import java.util.List;

public class PlaceSoldiers {
    private List<Country> allCountries;
    private List<Country> ownedCountries;
    private int soldiersToPlace;
    private List<Card> cards;

    public PlaceSoldiers(List<Country> allCountries, List<Country> ownedCountries, int soldiersToPlace, List<Card> cards) {
        this.allCountries = allCountries;
        this.ownedCountries = ownedCountries;
        this.soldiersToPlace = soldiersToPlace;
        this.cards = cards;
    }

    public List<Country> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(List<Country> allCountries) {
        this.allCountries = allCountries;
    }

    public List<Country> getOwnedCountries() {
        return ownedCountries;
    }

    public void setOwnedCountries(List<Country> ownedCountries) {
        this.ownedCountries = ownedCountries;
    }

    public int getSoldiersToPlace() {
        return soldiersToPlace;
    }

    public void setSoldiersToPlace(int soldiersToPlace) {
        this.soldiersToPlace = soldiersToPlace;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
