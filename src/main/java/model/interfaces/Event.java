package java.model.interfaces;

import java.model.enums.EventType;

import java.util.List;

public interface Event {
    List<Integer> getEventData();
    EventType getEventType();
}
