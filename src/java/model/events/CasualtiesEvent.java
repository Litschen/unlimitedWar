package model.events;


import model.enums.EventType;
import model.interfaces.Event;

public class CasualtiesEvent implements Event {

    //region data fields
    private int casualties;
    private EventType eventType;
    //endregion

    public CasualtiesEvent(int casualties, EventType eventType){
        this.casualties = casualties;
        this.eventType = eventType;
    }

    @Override
    public int[] getEventData() {
        return new int[]{casualties};
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
