package model.events;


import model.enums.EventType;
import model.interfaces.Event;

public class CasualtiesEvent implements Event {
    @Override
    public int[] getEventData() {
        return new int[0];
    }

    @Override
    public EventType getEventType() {
        return null;
    }
}
