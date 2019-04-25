package ch.zhaw.unlimitedWar.model.events;


import ch.zhaw.unlimitedWar.model.enums.EventType;
import ch.zhaw.unlimitedWar.model.helpers.Casualties;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class CasualtiesEvent implements Event {

    //region static variables
    private static final String TITLE = "Lost soldiers";
    private static final String DATA_LABEL = "Attacker : Defender";
    //endregion

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
