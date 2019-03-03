package model;

import model.Behaviors.AggressiveBehavior;
import model.Behaviors.RandomBehavior;
import model.Behaviors.StrategicBehavior;
import model.Behaviors.UserBehavior;
import model.Enum.Phase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardBean {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 12;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    //endregion

    //region data fields
    private int turnCount = 0;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    private int soldiersToPlace;
    private int attackDiceCount;
    private int defendDiceCount;
    private Country attackerCountry;
    private Country defenderCountry;
    private String modalToShow;
    private Phase currentPhase = Phase.SETTINGPHASE;
    //endregion

    public BoardBean() {
        players = new ArrayList<>();
        countries = new ArrayList<>();
        generatePlayers();
        generateCountries();

        // Hardcoded value to prevent NullPointerException
        // will be fixed by /F0210/
        setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace());
    }

    //region getter setter
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Country> getCountries() {
        return countries;
    }

    private int getDiceCount(int soldiersCount) {
        return soldiersCount >= 3 ? 3 : soldiersCount;
    }

    public Country getCountryById(int id) {
        return this.countries.get(id);
    }

    public Country getCountryByName(String countryName) {
        for (Country country : this.countries) {
            if (country.getName().equals(countryName)) {
                return country;
            }
        }

        return null;
    }

    public int getSoldiersToPlace() {
        return this.soldiersToPlace;
    }

    public void setSoldiersToPlace(int soldiersToPlace) {
        this.soldiersToPlace = soldiersToPlace;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Country getAttackerCountry() {
        return attackerCountry;
    }

    public void setAttackerCountry(Country attackerCountry) {
        this.attackerCountry = attackerCountry;
    }

    public Country getDefenderCountry() {
        return defenderCountry;
    }

    public void setDefenderCountry(Country defenderCountry) {
        this.defenderCountry = defenderCountry;
    }

    public String getModalToShow() {
        return modalToShow;
    }

    public void setModalToShow(String modalToShow) {
        this.modalToShow = modalToShow;
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

    //endregion

    //region methods to generate countries & set their properties

    /**
     * Generate Countries.
     * The number of sold soldiers is displayed on each country
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
     * The playing field colors the countries in the color of the owning player.
     */
    private void setCountryAttributes() {
        try {
            List<String> countryNames = Files.readAllLines(
                    new File(getClass().getClassLoader().getResource("countryNames.txt").getPath()).toPath(), Charset.defaultCharset());
            Collections.shuffle(countryNames);
            for (Country country : countries) {
                country.setName(countryNames.remove(0));
            }
            setNeighbors();

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void setNeighbors() {
        setfixedNeighbors(0, new int[]{1});
        setfixedNeighbors(1, new int[]{0, 6, 2});
        setfixedNeighbors(2, new int[]{1, 3});
        setfixedNeighbors(3, new int[]{2, 14});
        setfixedNeighbors(4, new int[]{6, 5});
        setfixedNeighbors(5, new int[]{4, 6, 7});
        setfixedNeighbors(6, new int[]{1, 4, 5, 7});
        setfixedNeighbors(7, new int[]{5, 6, 10});
        setfixedNeighbors(8, new int[]{9, 15});
        setfixedNeighbors(9, new int[]{8, 10, 11, 13});
        setfixedNeighbors(10, new int[]{7, 9, 11});
        setfixedNeighbors(11, new int[]{9, 10, 12, 13});
        setfixedNeighbors(12, new int[]{11, 13});
        setfixedNeighbors(13, new int[]{12, 11, 9});
        setfixedNeighbors(14, new int[]{15, 3});
        setfixedNeighbors(15, new int[]{14, 8});
    }

    private void setfixedNeighbors(int countryIndex, int[] neighborCountryIndex) {
        for (int i : neighborCountryIndex) {
            countries.get(countryIndex).getNeighboringCountries().add(countries.get(i));
        }
    }
    //endregion

    /**
     * Generates all Player with their respective personalities.
     * Each computer opponent is randomly assigned a color from the color options.
     */
    private void generatePlayers() {
        //TODO modify to include User, all Behaviors and color selection
        this.currentPlayer = new Player("green", "Stalout", new UserBehavior());
        players.add(this.currentPlayer);
        players.add(new Player("blue", "LMao", new RandomBehavior()));
        players.add(new Player("red", "Hotler", new AggressiveBehavior()));
        players.add(new Player("yellow", "Darfolini", new StrategicBehavior()));
    }

    /**
     * Perform the player's move
     */
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

    //region methods to handle user interactions

    /**
     * add one soldier to the selected country
     *
     * @param countryName name of the selected country
     */
    public void addSoldiersToCountry(String countryName) {
        // create List of countries to match interface parameter
        ArrayList<Country> countries = new ArrayList<>();
        countries.add(this.getCountryByName(countryName));

        int placedSoldiers = currentPlayer.getBehavior().placeSoldiers(countries, this.currentPlayer.getOwnedCountries(), 1);
        soldiersToPlace -= placedSoldiers;

        if (soldiersToPlace == 0) {
            this.setCurrentPhase(Phase.ATTACKPHASE);
        }
    }

    public void setAttackAndDefendCountry(Country country) {
        if (this.getCurrentPlayer().getOwnedCountries().contains(country)) {
            this.setAttackerCountry(country);
        } else {
            this.setDefenderCountry(country);
        }

        if (this.attackerCountry != null && this.defenderCountry != null) {
            this.setModalToShow("attack");
            try {
                this.attackDiceCount = this.attackerCountry.maxAmountDiceThrowsAttacker();
                this.defendDiceCount = this.defenderCountry.amountDiceThrowsDefender(this.attackDiceCount);
            } catch (Exception e) {
                // TODO @huguemiz show error message on GUI
            }
        }
    }


    public void setMoveSoldiersToCountry(Country country, String countryName) {
        if (this.getCurrentPlayer().getOwnedCountries().contains(country)) {
            this.getCountryByName(countryName);
        }
        if (this.getCountryByName(countryName) != null && this.getCountryByName(countryName) != null) {
            this.setModalToShow("move");
        }
        if (soldiersToPlace == 0) {
            this.setCurrentPhase(Phase.SETTINGPHASE);
        }
    }

    public void cancelAttack() {
        this.setAttackerCountry(null);
        this.setDefenderCountry(null);
        this.setModalToShow("");
    }

    public void cancelMove() {
        this.getCountryByName(null);
        this.setModalToShow("");
    }


    /**
     * TODO: update comment
     * The current player selects one of his countries from which he wants to attack.
     * At least two soldiers are needed on this land. He then selects which enemy neighbor he attacks.
     * After each die roll, the attack can be aborted. You can attack any number of countries per turn.
     * If the defender no longer has any soldiers in the country, this land changes ownership.
     *
     * @param
     * @param
     */
    public void attackRoll(int attackDiceCount) {
        this.modalToShow = "";
        int[] attackerHighestRoll = Dice.roll(attackDiceCount);
        int[] defenderHighestRoll = Dice.roll(defendDiceCount);

        Casualties casualties = attackerCountry.calculateCasualties(attackerHighestRoll, defenderHighestRoll);
        attackerCountry.removeSoldiers(casualties.getCasualtiesAttacker());
        defenderCountry.removeSoldiers(casualties.getCasualtiesDefender());

        if (defenderCountry.getSoldiersCount() <= 0) {
            defenderCountry.setOwner(currentPlayer);
            attackerCountry.shiftSoldiers(attackDiceCount, defenderCountry);
        }

        this.cancelAttack();
    }
    //endregion
}
