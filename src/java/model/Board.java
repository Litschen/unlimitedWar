package model;

import model.behavior.AggressiveBehavior;
import model.behavior.RandomBehavior;
import model.behavior.UserBehavior;
import model.enums.Flag;
import model.enums.PlayerColor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 12;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    public final static String RESOURCE_FILE = "countryNames.txt";
    //endregion

    //region data fields
    private Turn currentTurn;
    private List<Player> players;
    private List<Country> countries;
    private List<PlayerColor> playerColor = new ArrayList<>();
    //endregion

    public Board() {
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers();
        generateCountries();
        startTurn();
    }

    //region getter setter
    public List<Player> getPlayers() {
        return players;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public Country getCountryById(int id) {
        return this.countries.get(id);
    }

    private int getPlayerColor() {
        return ((int) Math.random() * playerColor.size());
    }

    public Turn getCurrentTurn(){return currentTurn;}
    //endregion

    //region methods to generate countries & set their properties

    /**
     * generates COUNTRY_COUNT_GENERATION countries random distributes
     * those random to all players and places START_SOLDIER_PER_PLAYER  owned countris of each player
     * Calls setCountryAttributes
     */
    private void generateCountries() {
        for (Player currentPlayer : players) {
            int countriesToGenerate = COUNTRY_COUNT_GENERATION / players.size();
            int solidersToDistribute = START_SOLDIER_PER_PLAYER - countriesToGenerate;
            for (int i = countriesToGenerate; i > 0; i--) {
                if (i != 1) {
                    int randomSoldierCount = ThreadLocalRandom.current()
                            .nextInt(MIN_SOLDIER_GENERATION, solidersToDistribute + 1);
                    countries.add(new Country("", randomSoldierCount + 1, currentPlayer));
                    solidersToDistribute -= randomSoldierCount;
                } else {
                    countries.add(new Country("", solidersToDistribute + 1, currentPlayer));
                }
                currentPlayer.getOwnedCountries().add(countries.get(countries.size() - 1));
            }
        }
        Collections.shuffle(countries);
        setCountryAttributes();
    }

    /**
     * gives countries random names and sets their neighbor
     */
    private void setCountryAttributes() {
        try {
            List<String> countryNames = Files.readAllLines(
                    new File(getClass().getClassLoader().getResource(RESOURCE_FILE).getPath()).toPath(), Charset.defaultCharset());
            Collections.shuffle(countryNames);
            for (Country country : countries) {
                country.setName(countryNames.remove(0));
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        setNeighbors();
    }

    /**
     * Set the neighboring countries
     */
    private void setNeighbors() {
        setFixedNeighbors(0, new int[]{1});
        setFixedNeighbors(1, new int[]{0, 6, 2});
        setFixedNeighbors(2, new int[]{1, 3});
        setFixedNeighbors(3, new int[]{2, 14});
        setFixedNeighbors(4, new int[]{6, 5});
        setFixedNeighbors(5, new int[]{4, 6, 7});
        setFixedNeighbors(6, new int[]{1, 4, 5, 7});
        setFixedNeighbors(7, new int[]{5, 6, 10});
        setFixedNeighbors(8, new int[]{9, 15});
        setFixedNeighbors(9, new int[]{8, 10, 11, 13});
        setFixedNeighbors(10, new int[]{7, 9, 11});
        setFixedNeighbors(11, new int[]{9, 10, 12, 13});
        setFixedNeighbors(12, new int[]{11, 13});
        setFixedNeighbors(13, new int[]{12, 11, 9});
        setFixedNeighbors(14, new int[]{15, 3});
        setFixedNeighbors(15, new int[]{14, 8});
    }

    /**
     * Sets pre-defined neighbor
     *
     * @param countryIndex         index of country to set neighbors from countries Arraylist
     * @param neighborCountryIndex array of indexes from countries Arraylist of all neighbors of the country above
     */
    private void setFixedNeighbors(int countryIndex, int[] neighborCountryIndex) {
        for (int i : neighborCountryIndex) {
            countries.get(countryIndex).getNeighboringCountries().add(countries.get(i));
        }
    }
    //endregion

    /**
     * Generates all Player with their respective personalities. The current player can choose a color,
     * the rest is randomly assigned
     * (temporarily the user color is fixed)
     */
    private void generatePlayers() {
        playerColor.addAll(Arrays.asList(PlayerColor.values()));

        players.add(new Player(playerColor.remove(1), "Stalout", new UserBehavior()));

        players.add(new Player(playerColor.remove(getPlayerColor()), "LMao", new RandomBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), "Hotler", new AggressiveBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), "Darfolini", new RandomBehavior()));
    }

    public void checkForNewTurn(){
        if(getCurrentTurn().getFlag() == Flag.TURNEND){
           startTurn();
        }
    }

    public void startTurn(){
        currentTurn = new Turn(players, countries);
    }
}
