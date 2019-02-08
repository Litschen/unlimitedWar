package controller;

import model.Country;
import model.Player;
import model.RandomBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    //region static variables
    private final static int MAX_SOLDIER_GENERATION = 10;
    private final static int MIN_SOLDIER_GENERATION = 1;
    private final static int COUNTRY_COUNT_GENERATION = 16;
    //endregion

    //region data fields
    private int turnCount = 0;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    //endregion

    //region constructors
    public Board(){
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers();
        generateCountries();

    }
    //endregion

    //region getter setter
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    //endregion

    private void generateCountries(){
        for(Player currentPlayer : players){
            for(int i = COUNTRY_COUNT_GENERATION / players.size(); i > 0 ; i--){
                int randomSoldierCount = ThreadLocalRandom.current()
                        .nextInt(MIN_SOLDIER_GENERATION, MAX_SOLDIER_GENERATION + 1);
                countries.add(new Country("test", randomSoldierCount, currentPlayer));
            }
        }
        Collections.shuffle(countries);
    }

    private void generatePlayers(){
        players.add(new Player("blue", "LMao", new RandomBehavior()));
        players.add(new Player("red", "Hotler", new RandomBehavior()));
        players.add(new Player("green", "Stalout", new RandomBehavior()));
        players.add(new Player("yellow", "Darfolini", new RandomBehavior()));
    }
}
