package ch.zhaw.unlimitedWar.model.events;

import ch.zhaw.unlimitedWar.model.enums.EventType;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.List;

public class UserEvent implements Event {

    //region data fields
    private final String title;
    private final String label;
    private String userName;
    private final EventType type;
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
