package model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class BoardBean {

    //region static variables
    public final static int START_SOLDIER_PER_PLAYER = 12;
    public final static int MIN_SOLDIER_GENERATION = 0;
    public final static int COUNTRY_COUNT_GENERATION = 16;
    private final static int TIME_INBETWEEN_AI_ACTIONS_MS = 1000;
    private final String ATTACKER_KEY = "attackDice";
    private final String DEFENDER_KEY = "defendDice";
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
                pause();
                currentPlayer.getBehavior().attackCountry(countries, currentPlayer.getOwnedCountries());
                currentPlayer.getBehavior().moveSoldiers(countries, currentPlayer.getOwnedCountries());
            }
        }
    }

    // /F0350/ Würfelanzahl bestimmen Angreifer
    public int maxAttackerDiceCount(Country country) throws Exception {
        int count = country.getSoldiersCount() - 1;
        if (count > 0) {
            return count > 3 ? 3 : count;
        } else {
            throw new Exception("could not calculate maxAttackerDiceCount");
        }
    }

    // /F0351/ Würfelanzahl bestimmen Verteidiger
    public int maxDefenderDiceCount(Country country, int attackerDiceCount) {
        int count = attackerDiceCount - 1;
        int soldiers = country.getSoldiersCount();

        if (count == 0) {
            return 1;
        } else if (soldiers <= count) {
            return soldiers;
        }
        return count;
    }

    //  /F0340/ Land angreifen
    public void attackRoll(HttpServletRequest request, HttpServletResponse response) {
        int attackDiceCount = 0;
        int defendDiceCount = 0;

        for (String key : request.getParameterMap().keySet()) {
            if (key.contains(ATTACKER_KEY)) {
                attackDiceCount++;
            } else if (key.contains(DEFENDER_KEY)) {
                defendDiceCount++;
            }
        }
        Dice dice = new Dice();
        int attackerHighestRoll = dice.getHighestRoll(dice.roll(attackDiceCount));
        int defenderHighestRoll = dice.getHighestRoll(dice.roll(defendDiceCount));

        // TODO call /F0341/

        // if soldiers = 0
        // call /F0370/
    }

    public void selectCountry(int soldiersCount) {
        int attackerDice = this.getDiceCount(3);
        int defenderDice = this.getDiceCount(2);
    }

    private int getDiceCount(int soldiersCount) {
        if (soldiersCount >= 3) {
            return 3;
        } else {
            return soldiersCount;
        }
    }

    private void pause() {
        try {
            Thread.sleep(TIME_INBETWEEN_AI_ACTIONS_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
