package model.interfaces;

import model.enums.EventType;

public interface Event {
    int[] getEventData();
    EventType getEventType();
}
