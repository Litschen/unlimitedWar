package model.interfaces;

import model.enums.EventType;

import java.util.List;

public interface Event {

    String getTitle();

    void setPlayerName(String name);

    String getPlayerName();

    String getDataLabel();

    List<Integer> getEventData();

    EventType getEventType();

}
