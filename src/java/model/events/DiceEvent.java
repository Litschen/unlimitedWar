package model.events;

import model.enums.EventType;
import model.interfaces.Event;

public class DiceEvent implements Event {

    private int[] data;

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
