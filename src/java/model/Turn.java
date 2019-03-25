package model;

import model.behavior.UserBehavior;
import model.enums.EventType;
import model.enums.Flag;
import model.enums.Phase;
import model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class Turn {


    //region data fields
    private List<Event> occurredEvents;
    private List<Player> activePlayers;
    private List<Country> countries;
    private Player currentPlayer;
    private int attackDiceCount;
    private int defendDiceCount;
    private Country firstSelectedCountry;
    private Country secondSelectedCountry;
    private Flag flag;
    private Phase currentPhase = Phase.SETTINGPHASE;
    //endregion

    public Turn(List<Player> players, List<Country> countries){
        this.activePlayers = players;
        this.countries = countries;
        occurredEvents = new ArrayList<>();
        currentPlayer = activePlayers.get(0);
        if(currentPlayerIsUser()){
            currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace());
        }
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
        } else if (this.firstSelectedCountry != null) {
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
        } else if (this.secondSelectedCountry != null) {
            this.secondSelectedCountry.setSelected(false);
        }
        this.secondSelectedCountry = secondSelectedCountry;
    }

    public List<Event> getOccurredEvents() {
        return occurredEvents;
    }

    public Event getLastEventOfType(EventType type){
        boolean found = false;
        List<Event> events = getOccurredEvents();
        int index = events.size() - 1;
        while(!found && index > 0){
            index--;
            found = events.get(index).getEventType() == type;
        }
        if(!found){
            throw new IllegalArgumentException("No such event occured");
        }
        return events.remove(index);
    }
    //endregion

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
        if(!currentPlayerIsUser()){
            if (currentPhase == Phase.SETTINGPHASE) {
                currentPhase = currentPlayer.getBehavior().placeSoldiers(countries,
                        currentPlayer.getOwnedCountries(), currentPlayer.calculateSoldiersToPlace());
            }
            if (currentPhase == Phase.ATTACKPHASE) {
                currentPhase = currentPlayer.getBehavior().
                        attackCountry(countries, currentPlayer.getOwnedCountries()).getNewPhase();
            }
            if (currentPhase == Phase.MOVINGPHASE) {
                currentPhase = currentPlayer.getBehavior().moveSoldiers(countries, currentPlayer.getOwnedCountries());
                cyclePlayer();
            }
        }
    }

    /**
     * execute on user interaction according to current phase.
     *
     * @param selectedCountry country selected in gui
     */
    public void executeUserTurn(Country selectedCountry) {
        if (currentPhase == Phase.SETTINGPHASE) {
            List<Country> destination = new ArrayList<>();
            destination.add(selectedCountry);
            setCurrentPhase(currentPlayer.getBehavior().placeSoldiers(destination, currentPlayer.getOwnedCountries(), 1));
            resetSelectedCountries();
        } else if (currentPhase == Phase.ATTACKPHASE) {
            if (firstSelectedCountry != null && secondSelectedCountry != null) {
                List<Country> countryList = new ArrayList<>();
                countryList.add(firstSelectedCountry);
                countryList.add(secondSelectedCountry);
                getOccurredEvents().addAll(
                        currentPlayer.getBehavior().attackCountry(countryList, currentPlayer.getOwnedCountries())
                                .getOccurredEvents());
                resetSelectedCountries();
                eliminatePlayersAndCheckUserResult();
            } else {
                setAttackAndDefendCountry(selectedCountry);
            }
        } else if (currentPhase == Phase.MOVINGPHASE) {
            if (firstSelectedCountry == null || secondSelectedCountry == null) {
                setMovingCountry(selectedCountry);
            } else {
                List<Country> countryList = new ArrayList<>();
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
        resetSelectedCountries();
        int nextPlayerIndex = activePlayers.indexOf(currentPlayer) + 1;
        eliminatePlayersAndCheckUserResult();
        if (nextPlayerIndex >= activePlayers.size()) {
            setFlag(Flag.TURNEND);
        }
        else {
            currentPlayer = activePlayers.get(nextPlayerIndex);
            if (currentPlayerIsUser()) {
                currentPlayer.setSoldiersToPlace(currentPlayer.calculateSoldiersToPlace());
            }
        }
    }

    /**
     * deselects the two selected countries
     */
    public void resetSelectedCountries() {
        setFirstSelectedCountry(null);
        setSecondSelectedCountry(null);
        setFlag(Flag.NONE);
    }


    /**
     * remove the players from the game which lost all their countries
     * if the user has won / lost set the flag to show the message and save the result in the database
     */
    protected void eliminatePlayersAndCheckUserResult() {
        boolean removed = activePlayers.removeIf(o -> ((Player) o).getOwnedCountries().isEmpty());
        if (removed) {
            if (activePlayers.size() == 1 && activePlayers.get(0).getBehavior() instanceof UserBehavior) {
                setFlag(Flag.GAME_WIN);
                // TODO: MS3 /F0410/ Spielresultat speichern
            } else {
                boolean playerIn = activePlayers.stream().filter(o -> ((Player) o).getBehavior() instanceof UserBehavior).findFirst().isPresent();
                if (!playerIn) {
                    setFlag(Flag.GAME_LOSE);
                    // TODO: MS3 /F0410/ Spielresultat speichern
                }
            }
        }
    }
}
