package model;

import model.Behaviors.AggressiveBehavior;
import model.Behaviors.RandomBehavior;
import model.Behaviors.StrategicBehavior;
import model.Behaviors.UserBehavior;

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
    private Country attackerCountry;
    private Country defenderCountry;
    private String modalToShow;
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

    /**Show the Dice Count
     * @param soldiersCount
     * @return
     */
    private int getDiceCount(int soldiersCount) {
        if (soldiersCount >= 3) {
            return 3;
        } else {
            return soldiersCount;
        }
    }

    public Country getCountryById(int id) {
        return this.countries.get(id);
    }

    public int getSoldiersToPlace() {
        return this.soldiersToPlace;
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
    //endregion

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

    private void setNeighbors(){
        setfixedNeighbors(0, new int[] {1});
        setfixedNeighbors(1, new int[] {0,6,2});
        setfixedNeighbors(2, new int[] {1,3});
        setfixedNeighbors(3, new int[] {2,14});
        setfixedNeighbors(4, new int[] {6,5});
        setfixedNeighbors(5, new int[] {4,6,7});
        setfixedNeighbors(6, new int[] {1,4,5,7});
        setfixedNeighbors(7, new int[] {5,6,10});
        setfixedNeighbors(8, new int[] {9,15});
        setfixedNeighbors(9, new int[] {8,10,11,13});
        setfixedNeighbors(10, new int[] {7,9,11});
        setfixedNeighbors(11, new int[] {9,10,12,13});
        setfixedNeighbors(12, new int[] {11,13});
        setfixedNeighbors(13, new int[] {12,11,9});
        setfixedNeighbors(14, new int[] {15,3});
        setfixedNeighbors(15, new int[] {14,8});





    }

    private void setfixedNeighbors(int countryIndex, int[] neighborCountryIndex){
        for(int i : neighborCountryIndex){
            countries.get(countryIndex).getNeighboringCountries().add(countries.get(i));
        }
    }

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

    public boolean addSoldiersToCountry(ArrayList<Country> countries) {
        int placedSoldiers = currentPlayer.getBehavior().placeSoldiers(countries, currentPlayer.getOwnedCountries(), 0);
        soldiersToPlace -= placedSoldiers;
        return soldiersToPlace == 0;
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
    //  /F0340/ Land angreifen
    public void attackRoll(int attackDiceCount, int defendDiceCount) {
//        int attackerHighestRoll = Dice.roll(attackDiceCount);
//        int defenderHighestRoll = Dice.roll(defendDiceCount);

        Casualties casualties = attackerCountry.calculateCasualties(null, null);
        attackerCountry.removeSoldiers(casualties.getCasualtiesAttacker());
        defenderCountry.removeSoldiers(casualties.getCasualtiesDefender());

        if (defenderCountry.getSoldiersCount() <= 0) {
            //F0370/ Land erobern
            defenderCountry.setOwner(currentPlayer);

            //F0380/ Soldaten verschieben
        }
    }

    /**
     * Select Country
     *
     * @param soldiersCount
     */
    public void selectCountry(int soldiersCount) {
        int attackerDice = this.getDiceCount(3);
        int defenderDice = this.getDiceCount(2);
    }
}
