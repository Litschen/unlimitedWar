package model.events;


import model.helpers.Casualties;
import model.enums.EventType;
import model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class CasualtiesEvent implements Event {

    private static final String TITLE = "Lost soldiers";
    private static final String DATA_LABEL = "Attacker : Defender";

    //region data fields
    private String playerName;
    private List<Integer> data;
    //endregion

    public CasualtiesEvent(Casualties casualties) {
        data = new ArrayList<>();
        data.add(casualties.getCasualtiesAttacker());
        data.add(casualties.getCasualtiesDefender());
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String getDataLabel() {
        return DATA_LABEL;
    }

    @Override
    public List<Integer> getEventData() {
        return data;
    }

    @Override
    public EventType getEventType() {
        return EventType.CASUALTIES;
    }
}
