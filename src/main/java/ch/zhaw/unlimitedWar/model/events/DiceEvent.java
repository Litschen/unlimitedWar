package ch.zhaw.unlimitedWar.model.events;

import ch.zhaw.unlimitedWar.model.enums.EventType;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.List;

public class DiceEvent implements Event {

    //region static variables
    private static final String TITLE = "Dice Results";
    private static final String DATA_LABEL = "Results: ";
    //endregion

    //region data fields
    private String playerName;
    private List<Integer> data;
    private EventType type;
    //endregion

    public DiceEvent(List<Integer> diceThrow, EventType type, String name) {
        this.data = diceThrow;
        this.type = type;
        this.playerName = name;
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
