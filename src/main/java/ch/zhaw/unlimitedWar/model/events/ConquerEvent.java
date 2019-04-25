package ch.zhaw.unlimitedWar.model.events;

import ch.zhaw.unlimitedWar.model.enums.EventType;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.Collections;
import java.util.List;

public class ConquerEvent implements Event {

    //region static variables
    private static final String TITLE = "Attack Result";
    private static final String DATA_LABEL = "won the battle!";
    //endregion

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
        return Collections.emptyList();
    }

    @Override
    public EventType getEventType() {
        return EventType.CONQUER;
    }
}
