package model.events;


import model.helpers.Casualties;
import model.enums.EventType;
import model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class CasualtiesEvent implements Event {

    //region data fields
    private List<Integer> data;
    //endregion

    public CasualtiesEvent(Casualties casualties){
        data = new ArrayList<>();
        data.add(casualties.getCasualtiesAttacker());
        data.add(casualties.getCasualtiesDefender());
    }

    @Override
    public List<Integer> getEventData() {
        return data;
    }

    @Override
    public EventType getEventType() {
        return EventType.CasualtiesEvent;
    }
}
