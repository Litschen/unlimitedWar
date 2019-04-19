package model.events;

import model.enums.EventType;
import model.interfaces.Event;

import java.util.List;

public class UserEvent implements Event {

    //region data fields
    private String title;
    private String label;
    private String userName;
    private EventType type;
    //endregion

    public UserEvent(String title, String label) {
        this.type = EventType.USER_DATA;
        this.title = title;
        this.label = label;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setPlayerName(String name) {
        this.userName = name;
    }

    @Override
    public String getPlayerName() {
        return userName;
    }

    @Override
    public String getDataLabel() {
        return label;
    }

    @Override
    public List<Integer> getEventData() {
        return null;
    }

    @Override
    public EventType getEventType() {
        return type;
    }
}
