package model.events;

import model.enums.EventType;
import model.interfaces.Event;

public class DiceEvent implements Event {

    //region data fields
    private int[] data;
    //endregion

    public DiceEvent(int[] diceThrow){
        this.data = diceThrow;
    }

    @Override
    public int[] getEventData() {
        return data;
    }

    @Override
    public EventType getEventType() {
        return EventType.DiceEvent;
    }
}
