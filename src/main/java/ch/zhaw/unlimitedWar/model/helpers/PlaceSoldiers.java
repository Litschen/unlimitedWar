package ch.zhaw.unlimitedWar.model.helpers;

import ch.zhaw.unlimitedWar.model.Card;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;

import java.util.List;

public class PlaceSoldiers {
    private Player player;
    private List<Country> allCountries;
    private List<Country> ownedCountries;
    private int soldiersToPlace;
    private List<Card> cards;

    public PlaceSoldiers(List<Country> allCountries, int soldiersToPlace, Player player) {
        this.allCountries = allCountries;
        this.ownedCountries = player.getOwnedCountries();
        this.soldiersToPlace = soldiersToPlace;
        this.cards = player.getCards();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
        this.player.setSoldiersToPlace(soldiersToPlace);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
