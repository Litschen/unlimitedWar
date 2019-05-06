package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.model.behavior.UserBehavior;
import ch.zhaw.unlimitedWar.model.enums.Flag;
import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.events.UserEvent;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Turn {

    //region data fields
    private List<Event> occurredEvents;
    private List<Player> activePlayers;
    private List<Country> countries;
    private List<Continent> continents;
    private Player currentPlayer;
    private int attackDiceCount;
    private int defendDiceCount;
    private Country firstSelectedCountry;
    private Country secondSelectedCountry;
    private Flag flag;
    private Phase currentPhase = Phase.SET;
    private int turnNumber;

    private final static String SET_COUNTRY_ERROR = "Attacking or defending country could not be set";
    private final static String SET_COUNTRY_ERROR_TITLE = "Attack failed";
    //endregion

    //region static variables
    private final static Logger LOGGER = Logger.getLogger(Turn.class.getName());
    //endregion

    public Turn(List<Player> players, List<Country> countries, int turnNumber, List<Continent> continents) {
        this.activePlayers = players;
        this.countries = countries;
        this.continents = continents;
        this.turnNumber = turnNumber;
        occurredEvents = new ArrayList<>();
        currentPlayer = activePlayers.get(0);
        if (currentPlayerIsUser()) {
            currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace(continents));
        }
        this.flag = Flag.NONE;
    }


    //region getter setter
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
        if (firstSelectedCountry != null) {
            firstSelectedCountry.setSelected(true);
        }
        if (this.firstSelectedCountry != null) {
            this.firstSelectedCountry.setSelected(false);
        }
        this.firstSelectedCountry = firstSelectedCountry;
    }

    public Country getSecondSelectedCountry() {
        return secondSelectedCountry;
    }

    public void setSecondSelectedCountry(Country secondSelectedCountry) {
        if (secondSelectedCountry != null) {
            secondSelectedCountry.setSelected(true);
        }
        if (this.secondSelectedCountry != null) {
            this.secondSelectedCountry.setSelected(false);
        }
        this.secondSelectedCountry = secondSelectedCountry;
    }

    public List<Event> getOccurredEvents() {
        return occurredEvents;
    }

    public void addEvents(List<Event> events) {
        occurredEvents.addAll(events);
    }

    public List<Player> getActivePlayers() {
        return activePlayers;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public List<Continent> getContinents() {
        return continents;
    }

    //endregion

    public boolean currentPlayerIsUser() {
        return currentPlayer.getBehavior() instanceof UserBehavior;
    }

    /**
     * perform a AI turn
     */
    public void executeTurn() {
        if (!currentPlayerIsUser()) {
            if (currentPhase == Phase.SET) {
                int soldiersToPlace = currentPlayer.calculateSoldiersToPlace(continents);
                PlaceSoldiers placeSoldiers = new PlaceSoldiers(countries, soldiersToPlace, currentPlayer);
                currentPhase = currentPlayer.getBehavior().placeSoldiers(placeSoldiers);
            }
            if (currentPhase == Phase.ATTACK) {
                currentPhase = currentPlayer.getBehavior().
                        attackCountry(countries, currentPlayer.getOwnedCountries()).getNewPhase();
            }
            if (currentPhase == Phase.MOVE) {
                currentPhase = currentPlayer.getBehavior().moveSoldiers(countries, currentPlayer.getOwnedCountries());
                cyclePlayer();
            }
        }
    }

    /**
     * @param selectedCountry country selected in gui
     */
    public void executeUserTurn(Country selectedCountry) {
        if (currentPhase == Phase.SET) {
            List<Country> destination = new ArrayList<>();
            if (selectedCountry.getOwner().equals(currentPlayer)) {
                destination.add(selectedCountry);
                PlaceSoldiers placeSoldiers = new PlaceSoldiers(destination, currentPlayer.getSoldiersToPlace(), currentPlayer);
                setCurrentPhase(currentPlayer.getBehavior().placeSoldiers(placeSoldiers));
            }
            resetSelectedCountries();
        } else if (currentPhase == Phase.ATTACK) {
            if (firstSelectedCountry != null && secondSelectedCountry != null) {
                List<Country> countryList = new ArrayList<>();
                countryList.add(firstSelectedCountry);
                countryList.add(secondSelectedCountry);
                if (flag == Flag.MOVE_AFTER_INVASION) {
                    currentPlayer.getBehavior().moveSoldiers(countryList, currentPlayer.getOwnedCountries());
                }
                addEvents(
                        currentPlayer.getBehavior().attackCountry(countryList, currentPlayer.getOwnedCountries())
                                .getOccurredEvents());
                if (firstSelectedCountry.getOwner() == secondSelectedCountry.getOwner() && firstSelectedCountry.getSoldiersCount() > Country.MIN_SOLDIERS_TO_STAY) {
                    flag = Flag.MOVE_AFTER_INVASION;
                } else {
                    resetSelectedCountries();
                }
                eliminatePlayersAndCheckUserResult();
            } else {
                setAttackAndDefendCountry(selectedCountry);
            }
        } else if (currentPhase == Phase.MOVE) {
            if (firstSelectedCountry == null || secondSelectedCountry == null) {
                setMovingCountry(selectedCountry);
            } else {
                List<Country> countryList = new ArrayList<>();
                countryList.add(firstSelectedCountry);
                countryList.add(secondSelectedCountry);
                Phase finishMove = currentPlayer.getBehavior().moveSoldiers(countryList, currentPlayer.getOwnedCountries());
                if (finishMove != Phase.MOVE) {
                    setFlag(Flag.NONE);
                }
            }
        }
    }

    public void setAttackAndDefendCountry(Country country) {
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
                LOGGER.log(Level.WARNING, SET_COUNTRY_ERROR, e);
                occurredEvents.add(new UserEvent(SET_COUNTRY_ERROR_TITLE, SET_COUNTRY_ERROR));
            }
        }
    }

    /**
     * sets country which the user selected to move soldiers in  between
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

    public void moveToNextPhase() {
        Phase currentPhase = getCurrentPhase();
        resetSelectedCountries();
        if (currentPhase == Phase.SET) {
            setCurrentPhase(Phase.ATTACK);
        } else if (currentPhase == Phase.ATTACK) {
            setCurrentPhase(Phase.MOVE);
        } else if (currentPhase == Phase.MOVE) {
            setCurrentPhase(Phase.SET);
            cyclePlayer();
        }
    }

    private void cyclePlayer() {
        int nextPlayerIndex = activePlayers.indexOf(currentPlayer) + 1;
        eliminatePlayersAndCheckUserResult();
        if (nextPlayerIndex >= activePlayers.size()) {
            setFlag(Flag.TURN_END);
        } else {
            currentPlayer = activePlayers.get(nextPlayerIndex);
            if (currentPlayerIsUser()) {
                currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace(continents));
            }
        }
    }

    public void resetSelectedCountries() {
        setFirstSelectedCountry(null);
        setSecondSelectedCountry(null);
        setFlag(Flag.NONE);
    }

    /**
     * remove the players from the game which lost all their countries
     * if the user has won / lost set the flag to show the message and save the result in the database
     */
    public void eliminatePlayersAndCheckUserResult() {
        boolean hasRemovedPlayers = activePlayers.removeIf(o -> o.getOwnedCountries().isEmpty());
        if (hasRemovedPlayers) {
            if (activePlayers.size() == 1 && currentPlayerIsUser()) {
                setFlag(Flag.GAME_WIN);

            } else {
                boolean playerIn = activePlayers.stream().anyMatch(o -> o.getBehavior() instanceof UserBehavior);
                if (!playerIn) {
                    setFlag(Flag.GAME_LOSE);
                }
            }
        }
    }


}
