package model;

import model.Behaviors.RandomBehavior;
import model.Behaviors.UserBehavior;
import model.Enum.Flag;
import model.Enum.PlayerColor;
import model.Enum.Phase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardBean {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 12;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    public final static String RESOURCE_FILE = "countryNames.txt";
    //endregion

    //region data fields
    private int turnCount = 1;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    private int attackDiceCount;
    private int defendDiceCount;
    private Country firstSelectedCountry;
    private Country secondSelectedCountry;
    private Flag flag;
    private Phase currentPhase = Phase.SETTINGPHASE;
    private List<PlayerColor> playerColor = new ArrayList<>();
    //endregion

    public BoardBean() {
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers();
        generateCountries();
        if (currentPlayerIsUser()) {
            currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace());
        }
        setFlag(Flag.NONE);
    }

    //region getter setter
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    public Country getCountryById(int id) {
        return this.countries.get(id);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        if (this.flag != Flag.GAME_LOSE) {
            this.flag = flag;
        }
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(Phase currentPhase) {
        this.currentPhase = currentPhase;
    }

    public int getAttackDiceCount() {
        return attackDiceCount;
    }

    public int getDefendDiceCount() {
        return defendDiceCount;
    }

    public Country getFirstSelectedCountry() {
        return firstSelectedCountry;
    }

    public void setFirstSelectedCountry(Country firstSelectedCountry) {
        this.firstSelectedCountry = firstSelectedCountry;
    }

    public Country getSecondSelectedCountry() {
        return secondSelectedCountry;
    }

    public void setSecondSelectedCountry(Country secondSelectedCountry) {
        this.secondSelectedCountry = secondSelectedCountry;
    }

    private int getPlayerColor() {
        return ((int) Math.random() * playerColor.size());
    }
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

        currentPlayer = new Player(playerColor.remove(1), "Stalout", new UserBehavior());
        players.add(currentPlayer);

        players.add(new Player(playerColor.remove(getPlayerColor()), "LMao", new RandomBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), "Hotler", new RandomBehavior()));
        players.add(new Player(playerColor.remove(getPlayerColor()), "Darfolini", new RandomBehavior()));
    }

    /**
     * @return true: when the current Player is a User
     */
    public boolean currentPlayerIsUser() {
        return currentPlayer.getBehavior() instanceof UserBehavior;
    }

    /**
     * perform a AI turn, cycles Player in the end
     */
    public void executeTurn() {
        if (currentPhase == Phase.SETTINGPHASE) {
            currentPhase = currentPlayer.getBehavior().placeSoldiers(countries,
                    currentPlayer.getOwnedCountries(), currentPlayer.calculateSoldiersToPlace());
        }
        if (currentPhase == Phase.ATTACKPHASE) {
            currentPhase = currentPlayer.getBehavior().attackCountry(countries, currentPlayer.getOwnedCountries());
        }
        if (currentPhase == Phase.MOVINGPHASE) {
            currentPhase = currentPlayer.getBehavior().moveSoldiers(countries, currentPlayer.getOwnedCountries());
            cyclePlayer();
        }
    }

    /**
     * execute on user interaction according to current phase.
     *
     * @param selectedCountry country selected in gui
     */
    public void executeUserTurn(Country selectedCountry) {
        if (currentPhase == Phase.SETTINGPHASE) {
            ArrayList<Country> destination = new ArrayList<>();
            destination.add(selectedCountry);
            setCurrentPhase(currentPlayer.getBehavior().placeSoldiers(destination, currentPlayer.getOwnedCountries(), 1));
            resetSelectedCountries();
        } else if (currentPhase == Phase.ATTACKPHASE) {
            if (firstSelectedCountry != null && secondSelectedCountry != null) {
                ArrayList<Country> countryList = new ArrayList<>();
                countryList.add(firstSelectedCountry);
                countryList.add(secondSelectedCountry);
                currentPlayer.getBehavior().attackCountry(countryList, currentPlayer.getOwnedCountries());
                resetSelectedCountries();
                eliminatePlayersAndCheckUserResult();
            } else {
                setAttackAndDefendCountry(selectedCountry);
            }
        } else if (currentPhase == Phase.MOVINGPHASE) {
            if (firstSelectedCountry == null || secondSelectedCountry == null) {
                setMovingCountry(selectedCountry);
            } else {
                ArrayList<Country> countryList = new ArrayList<>();
                countryList.add(firstSelectedCountry);
                countryList.add(secondSelectedCountry);
                Phase finishMove = currentPlayer.getBehavior().moveSoldiers(countryList, currentPlayer.getOwnedCountries());
                if (finishMove != Phase.MOVINGPHASE) {
                    setFlag(Flag.NONE);
                }
            }
        }
    }

    /**
     * indicates which country is attacking and which country is being attacked
     * called from controller
     *
     * @param country which the player selects
     */
    void setAttackAndDefendCountry(Country country) {
        if (currentPlayer.getOwnedCountries().contains(country) && country.getSoldiersCount() >= Country.MIN_SOLDIERS_TO_INVADE) {
            setFirstSelectedCountry(country);
        } else {
            setSecondSelectedCountry(country);
        }

        if (firstSelectedCountry != null && secondSelectedCountry != null && firstSelectedCountry.canInvade(secondSelectedCountry)) {
            setFlag(Flag.ATTACK);
            try {
                attackDiceCount = firstSelectedCountry.maxAmountDiceThrowsAttacker();
                defendDiceCount = secondSelectedCountry.amountDiceThrowsDefender(attackDiceCount);
            } catch (Exception e) {
                // TODO @huguemiz show error message on GUI
            }
        }
    }

    /**
     * save the two selected countries
     *
     * @param country is the country which the player selects on GUI
     */
    private void setMovingCountry(Country country) {
        if (firstSelectedCountry == null && currentPlayer.getOwnedCountries().contains(country) && country.getSoldiersCount() > 1) {
            setFirstSelectedCountry(country);
        } else if (secondSelectedCountry == null && currentPlayer.getOwnedCountries().contains(country)) {
            setSecondSelectedCountry(country);
        }

        if (firstSelectedCountry != null && secondSelectedCountry != null) {
            setFlag(Flag.MOVE);
        }
    }

    /**
     * Moves to the next Phase
     * resets the selected countries
     */
    public void moveToNextPhase() {
        Phase currentPhase = getCurrentPhase();

        if (currentPhase == Phase.SETTINGPHASE) {
            setCurrentPhase(Phase.ATTACKPHASE);
        } else if (currentPhase == Phase.ATTACKPHASE) {
            setCurrentPhase(Phase.MOVINGPHASE);
        } else if (currentPhase == Phase.MOVINGPHASE) {
            setCurrentPhase(Phase.SETTINGPHASE);
            cyclePlayer();
        }

        resetSelectedCountries();
    }

    /**
     * change the next player and delete the player without countries
     */
    private void cyclePlayer() {
        int nextPlayerIndex = players.indexOf(currentPlayer) + 1;
        eliminatePlayersAndCheckUserResult();
        if (nextPlayerIndex >= players.size()) {
            nextPlayerIndex = 0;
        }
        if (nextPlayerIndex == 0) {
            turnCount++;
        }
        currentPlayer = players.get(nextPlayerIndex);
        if (currentPlayerIsUser()) {
            currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace());
        }
        resetSelectedCountries();
    }

    /**
     * deselects the two selected countries
     */
    public void resetSelectedCountries() {
        setFirstSelectedCountry(null);
        setSecondSelectedCountry(null);
        setFlag(Flag.NONE);
    }

    private void eliminatePlayersAndCheckUserResult() {
        boolean removed = players.removeIf(o -> ((Player) o).getOwnedCountries().size() <= 0);
        if (removed) {
            if (players.size() == 1 && players.get(0).getBehavior() instanceof UserBehavior) {
                setFlag(Flag.GAME_WIN);
            } else {
                boolean playerIn = players.stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().isPresent();
                if (!playerIn) {
                    setFlag(Flag.GAME_LOSE);
                }
            }
        }
    }
}
