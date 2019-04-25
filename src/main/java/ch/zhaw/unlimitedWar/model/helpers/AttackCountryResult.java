package ch.zhaw.unlimitedWar.model.helpers;

import ch.zhaw.unlimitedWar.model.enums.Phase;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

public class AttackCountryResult {
    //region data fields
    private Phase newPhase;
    private final List<Event> occurredEvents;
    //endregion

    public AttackCountryResult(Phase phase) {
        occurredEvents = new ArrayList<>();
        newPhase = phase;
    }

    //region getter setter
    public Phase getNewPhase() {
        return newPhase;
    }

    public List<Event> getOccurredEvents() {
        return occurredEvents;
    }
    //endregion

    public void addEvents(List<Event> events) {
        occurredEvents.addAll(events);
    }
}
