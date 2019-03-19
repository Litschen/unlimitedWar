package model.events;


import model.enums.EventType;
import model.interfaces.Event;

public class CasualtiesEvent implements Event {

    private int casualties;

    public CasualtiesEvent(int casualties){
        this.casualties = casualties;
    }

    @Override
    public int[] getEventData() {
        return new int[]{casualties};
    }

    @Override
    public EventType getEventType() {
        return EventType.CasualtiesEvent;
    }
}
