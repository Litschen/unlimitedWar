package model.events;

import model.enums.EventType;
import model.interfaces.Event;

import java.util.List;

public class ConquerEvent implements Event {

    private static final String TITLE = "Attack Result";
    private static final String DATA_LABEL = "won the battle!";

    //region data fields
    private String playerName;
    //endregion

    public ConquerEvent(String name) {
        playerName = name;
    }

    @Override
    public String getTitle() {
        return TITLE;
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
        return playerName + " " + DATA_LABEL;
    }

    @Override
    public List<Integer> getEventData() {
        return null;
    }

    @Override
    public EventType getEventType() {
        return EventType.ConquerEvent;
    }
}
