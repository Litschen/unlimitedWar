package model.interfaces;

import model.enums.EventType;

import java.util.List;

public interface Event {
    List<Integer> getEventData();
    EventType getEventType();
}
