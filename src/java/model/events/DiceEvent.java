package model.events;

import model.enums.EventType;
import model.interfaces.Event;

import java.util.List;

public class DiceEvent implements Event {

    //region data fields
    private List<Integer> data;
    private EventType type;
    //endregion

    public DiceEvent(List<Integer> diceThrow, EventType type){
        this.data = diceThrow;
        this.type = type;
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
