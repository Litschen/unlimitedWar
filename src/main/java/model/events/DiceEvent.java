package java.model.events;

import java.model.enums.EventType;
import java.model.interfaces.Event;

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
