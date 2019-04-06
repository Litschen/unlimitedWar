package model.behavior;

import model.enums.EventType;
import model.events.CasualtiesEvent;
import model.events.ConquerEvent;
import model.events.DiceEvent;
import model.interfaces.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelperEvents {

    public static List<Event> mockInvadeEvents(boolean addConquerEvent) {
        List<EventType> eventsOnInvade = Arrays.asList(EventType.CasualtiesEvent, EventType.AttackerDiceEvent, EventType.DefenderDiceEvent);
        if (addConquerEvent) {
            eventsOnInvade.add(EventType.ConquerEvent);
        }
        return mockEvents(eventsOnInvade);
    }

    public static List<Event> mockEvents(List<EventType> events) {
        List<Event> occurredEvents = new ArrayList<>();

        for (EventType eventType : events) {
            Event mockEvent = null;
            switch (eventType) {
                case CasualtiesEvent:
                    mockEvent = mock(CasualtiesEvent.class);
                    break;
                case DefenderDiceEvent:
                case AttackerDiceEvent:
                    mockEvent = mock(DiceEvent.class);
                    break;
                case ConquerEvent:
                    mockEvent = mock(ConquerEvent.class);
                    break;
            }

            when(mockEvent.getEventType()).thenReturn(eventType);
            occurredEvents.add(mockEvent);
        }

        return occurredEvents;
    }
}
