package model;

import model.Behaviors.AggressiveBehavior;
import model.Behaviors.RandomBehavior;
import model.Behaviors.StrategicBehavior;
import model.Behaviors.UserBehavior;
import model.Enum.ColorPlayer;
import model.Enum.Phase;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;
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
    private Country firstSelectedCountry;
    private Country secondSelectedCountry;
    private Boolean userHasSetSoldiers = false;
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

    private void setFixedNeighbors(int countryIndex, int[] neighborCountryIndex) {
        for (int i : neighborCountryIndex) {
            countries.get(countryIndex).getNeighboringCountries().add(countries.get(i));
        }
    }
    //endregion
    ColorPlayer cp = ColorPlayer.values()[(int)(Math.random()*ColorPlayer.values().length)];
    /**
     * Generates all Player with their respective personalities.
     * Each computer opponent is randomly assigned a color from the color options.
     */
    private void generatePlayers() {
        //TODO modify to include User, all Behaviors and color selection
        List<ColorPlayer> colorPlayer = new ArrayList<>();
        colorPlayer.addAll(Arrays.asList(ColorPlayer.values()));

        this.currentPlayer = new Player(colorPlayer.remove(1),"Stalout", new UserBehavior());
        players.add(this.currentPlayer);

        players.add(new Player(colorPlayer.remove((int)Math.random()*colorPlayer.size()), "LMao", new RandomBehavior()));
        players.add(new Player(colorPlayer.remove((int)Math.random()*colorPlayer.size()), "Hotler", new AggressiveBehavior()));
        players.add(new Player(colorPlayer.remove((int)Math.random()*colorPlayer.size()), "Darfolini", new StrategicBehavior()));
    }

    /**
     * Perform the player's move
     */
    public void executeTurn() {
        //TODO test this
        ArrayList<Country> currentTurnCountries = new ArrayList<>();
        Boolean executePhase = true;
        int soldiersToPlace = currentPlayer.calculateSoldiersToPlace();
        if (currentPlayer == players.get(0) && currentPhase == Phase.SETTINGPHASE) {
            turnCount++;
        }
        if (currentPlayerIsUser()) {
            if (!userHasSetSoldiers && currentPlayer.getUserSoldiersToPlace() == 0) {
                currentPlayer.setUserSoldiersToPlace(soldiersToPlace);
            } else {
                if (userHasSetSoldiers && currentPlayer.getUserSoldiersToPlace() == 0) {
                    currentPhase = Phase.ATTACKPHASE;
                }
                currentPlayer.setUserSoldiersToPlace(currentPlayer.getUserSoldiersToPlace() - 1);
                soldiersToPlace = 1;
            }
            if (firstSelectedCountry != null && secondSelectedCountry != null) {
                currentTurnCountries.add(firstSelectedCountry);
                currentTurnCountries.add(secondSelectedCountry);
            } else {
                executePhase = false;
                if (currentPhase == Phase.SETTINGPHASE) {
                    if (firstSelectedCountry != null) {
                        currentTurnCountries.add(firstSelectedCountry);
                        if (secondSelectedCountry != null) {
                            currentTurnCountries.add(secondSelectedCountry);
                        }
                        userHasSetSoldiers = true;
                        executePhase = true;
                    }
                }
            }

            if (currentPhase == Phase.SETTINGPHASE && executePhase) {
                Iterator it = currentTurnCountries.iterator();
                Collections.reverse(currentTurnCountries);
                boolean isOwner = false;
                while (it.hasNext() && !isOwner) {
                    Country c = (Country) it.next();
                    isOwner = c.getOwner() == currentPlayer;
                    if (!isOwner) {
                        it.remove();
                    }
                }
                executePhase = currentTurnCountries.size() > 0;
                if (!executePhase) {
                    currentPlayer.setUserSoldiersToPlace(currentPlayer.getUserSoldiersToPlace() + 1);
                }
            }
        } else {
            currentTurnCountries = this.countries;
        }
        if (executePhase && currentPlayer.getOwnedCountries().size() > 0) {
            if (currentPhase == Phase.SETTINGPHASE) {
                currentPhase = currentPlayer.getBehavior().placeSoldiers(currentTurnCountries, currentPlayer.getOwnedCountries(), soldiersToPlace
                );
            } else if (currentPhase == Phase.ATTACKPHASE) {
                currentPhase = currentPlayer.getBehavior().attackCountry(currentTurnCountries, currentPlayer.getOwnedCountries());
                userHasSetSoldiers = false;
            } else if (currentPhase == Phase.MOVINGPHASE) {
                currentPhase = currentPlayer.getBehavior().moveSoldiers(currentTurnCountries, currentPlayer.getOwnedCountries());
                cyclePlayer();
            }

            resetSelectedCountries();
        }
    }

    public void resetSelectedCountries() {
        if (firstSelectedCountry != null && secondSelectedCountry != null || currentPhase == Phase.SETTINGPHASE) {
            firstSelectedCountry = null;
            secondSelectedCountry = null;
        }
    }

    public void cyclePlayer() {
        int nextPlayerIndex = players.indexOf(currentPlayer) + 1;
        if (nextPlayerIndex == players.size()) {
            nextPlayerIndex = 0;
        }
        currentPlayer = players.get(nextPlayerIndex);
    }

    public boolean currentPlayerIsUser() {
        return currentPlayer.getBehavior() instanceof UserBehavior;
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
        // TODO: fix
        // int placedSoldiers = currentPlayer.getBehavior().placeSoldiers(countries, this.currentPlayer.getOwnedCountries(), 1);
//        soldiersToPlace -= placedSoldiers;

        if (soldiersToPlace == 0) {
            this.setCurrentPhase(Phase.ATTACKPHASE);
        }
    }

    public void setAttackAndDefendCountry(Country country) {
        if (this.currentPlayer.getOwnedCountries().contains(country) && country.getSoldiersCount() > 1) {
            this.setFirstSelectedCountry(country);
        } else {
            this.setSecondSelectedCountry(country);
        }

        if (this.firstSelectedCountry != null && this.secondSelectedCountry != null) {
            this.setModalToShow("attack");
            try {
                this.attackDiceCount = this.firstSelectedCountry.maxAmountDiceThrowsAttacker();
                this.defendDiceCount = this.secondSelectedCountry.amountDiceThrowsDefender(this.attackDiceCount);
            } catch (Exception e) {
                // TODO @huguemiz show error message on GUI
            }
        }
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
        firstSelectedCountry.invade(secondSelectedCountry, attackDiceCount, defendDiceCount);
        this.cancelAttack();
    }

    public void cancelAttack() {
        this.setFirstSelectedCountry(null);
        this.setSecondSelectedCountry(null);
        this.setModalToShow("");
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

    public void cancelMove() {
        this.getCountryByName(null);
        this.setModalToShow("");
    }

    //endregion
}
