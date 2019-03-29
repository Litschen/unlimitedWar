package model.events;

import model.enums.EventType;
import model.interfaces.Event;

import java.util.List;

public class DiceEvent implements Event {

    private static final String TITLE = "Dice Results";
    private static final String DATA_LABEL = "Results: ";

    //region data fields
    private String playerName;
    private List<Integer> data;
    private EventType type;
    //endregion

    public DiceEvent(List<Integer> diceThrow, EventType type) {
        this.data = diceThrow;
        this.type = type;
    }

    @Override
    public String getTitle() {
        return playerName + " " + TITLE;
    }

    @Override
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String getDataLabel() {
        return DATA_LABEL;
    }

    @Override
    public List<Integer> getEventData() {
        return data;
    }

    @Override
    public EventType getEventType() {
        return type;
    }
}
