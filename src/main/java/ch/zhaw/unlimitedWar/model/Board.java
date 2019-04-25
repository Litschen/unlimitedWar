package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.model.behavior.AggressiveBehavior;
import ch.zhaw.unlimitedWar.model.behavior.RandomBehavior;
import ch.zhaw.unlimitedWar.model.behavior.StrategicBehavior;
import ch.zhaw.unlimitedWar.model.behavior.UserBehavior;
import ch.zhaw.unlimitedWar.model.enums.Flag;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Board {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 12;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    public final static String RESOURCE_FILE_COUNTRY = "countryNames.txt";
    public final static String RESOURCE_FILE_NAME = "computerOpponentNames.txt";
    private final static Logger LOGGER = Logger.getLogger(Board.class.getName());
    //endregion

    //region data fields
    private Turn currentTurn;
    private List<Player> players;
    private List<Country> countries;
    private List<Continent> continents;
    private List<PlayerColor> playerColor = new ArrayList<>();
    private int turnCounter = 1;
    //endregion

    public Board(PlayerColor color, String name) {
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers(color, name);
        generateCountries();
        currentTurn = new Turn(players, countries, turnCounter, continents);
        turnCounter++;
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
        return ((int) (Math.random() * playerColor.size()));
    }

    public Turn getCurrentTurn() {
        if (currentTurn.getFlag() == Flag.TURN_END) {
            currentTurn = new Turn(players, countries, turnCounter, continents);
            turnCounter++;
        }
        return currentTurn;
    }

    public List<Event> getEvents() {
        List<Event> occurredEvents = new ArrayList<>(currentTurn.getOccurredEvents());

        currentTurn.getOccurredEvents().clear();
        return occurredEvents;
    }
    //endregion

    //region methods to generate countries & set their properties
    private void generateCountries() {
        for (Player currentPlayer : players) {
            int countriesToGenerate = COUNTRY_COUNT_GENERATION / players.size();
            int soldiersToDistribute = START_SOLDIER_PER_PLAYER - countriesToGenerate;
            for (int i = countriesToGenerate; i > 0; i--) {
                if (i != 1) {
                    int randomSoldierCount = Dice.roll(MIN_SOLDIER_GENERATION, soldiersToDistribute);
                    countries.add(new Country("", randomSoldierCount + 1, currentPlayer));
                    soldiersToDistribute -= randomSoldierCount;
                } else {
                    countries.add(new Country("", soldiersToDistribute + 1, currentPlayer));
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
                    new File(Objects.requireNonNull(getClass().getClassLoader().getResource(RESOURCE_FILE_COUNTRY)).getPath()).toPath(), Charset.defaultCharset());
            Collections.shuffle(countryNames);
            for (Country country : countries) {
                country.setName(countryNames.remove(0));
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.WARNING, "Country Attributes could not be set", e);
        }
        setNeighbors();
    }

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
     * @param countryIndex         index of country to set neighbors from countries ArrayList
     * @param neighborCountryIndex array of indexes from countries ArrayList of all neighbors of the country above
     */
    private void setFixedNeighbors(int countryIndex, int[] neighborCountryIndex) {
        for (int i : neighborCountryIndex) {
            countries.get(countryIndex).addNeighboringCountries(Collections.singletonList(countries.get(i)));
        }
    }
    //endregion

    private void generatePlayers(PlayerColor color, String name) {

        playerColor.addAll(Arrays.asList(PlayerColor.values()));

        players.add(new Player(playerColor.remove(playerColor.indexOf(color)), name, new UserBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), name, new StrategicBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), name, new AggressiveBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), name, new RandomBehavior()));

        Collections.shuffle(players);
        setRandomNameOpponent();

    }

    private void setRandomNameOpponent() {
        try {
            List<String> opponentName = Files.readAllLines(
                    new File(Objects.requireNonNull(getClass().getClassLoader().getResource(RESOURCE_FILE_NAME)).getPath()).toPath(), Charset.defaultCharset());
            Collections.shuffle(opponentName);
            for (Player player : players) {
                if (!(player.getBehavior() instanceof UserBehavior)) {
                    player.setPlayerName(opponentName.remove(0));
                }
            }
        } catch (IOException | NullPointerException e) {
            LOGGER.log(Level.WARNING, "Country Attributes could not be set", e);
        }
    }
}
