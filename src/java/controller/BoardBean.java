package controller;

import model.Country;
import model.Player;
import model.RandomBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class BoardBean {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 16;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    //endregion

    //region data fields
    private int turnCount = 0;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    //endregion

    //region constructors
    public BoardBean() {
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers();
        generateCountries();

    }
    //endregion

    //region getter setter
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    //endregion

    private void generateCountries() {
        for (Player currentPlayer : players) {
            int countriesToGenerate = COUNTRY_COUNT_GENERATION / players.size();
            int solidersToDistribute = START_SOLDIER_PER_PLAYER - countriesToGenerate;
            for (int i = countriesToGenerate; i > 0; i--) {
                if (i != 1) {
                    int randomSoldierCount = ThreadLocalRandom.current()
                            .nextInt(MIN_SOLDIER_GENERATION, solidersToDistribute + 1);
                    countries.add(new Country("test", randomSoldierCount + 1, currentPlayer));
                    solidersToDistribute -= randomSoldierCount;
                } else {
                    countries.add(new Country("test", solidersToDistribute + 1, currentPlayer));
                }
                currentPlayer.getOwnedCountries().add(countries.get(countries.size() - 1));
            }
        }
        Collections.shuffle(countries);
    }

    private void generatePlayers() {
        //TODO modify to include User, all Behaviors and color selection
        players.add(new Player("blue", "LMao", new RandomBehavior()));
        players.add(new Player("red", "Hotler", new RandomBehavior()));
        players.add(new Player("green", "Stalout", new RandomBehavior()));
        players.add(new Player("yellow", "Darfolini", new RandomBehavior()));
    }

    public void executeTurn() {
        //TODO test this
        turnCount++;
        for (Player currentPlayer : players) {
            if (currentPlayer.getOwnedCountries().size() > 0) {
                currentPlayer.getBehavior().placeSoldiers(countries, currentPlayer.getOwnedCountries(),
                        currentPlayer.calculateSoldiersToPlace());
                currentPlayer.getBehavior().attackCountry(countries, currentPlayer.getOwnedCountries());
                currentPlayer.getBehavior().moveSoldiers(countries, currentPlayer.getOwnedCountries());
            }
        }
    }
}
