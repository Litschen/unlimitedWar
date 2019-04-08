package helpers;

import model.enums.EventType;
import model.events.CasualtiesEvent;
import model.events.ConquerEvent;
import model.events.DiceEvent;
import model.interfaces.Event;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHelperEvents {

    public static List<Event> mockInvadeEvents(boolean addConquerEvent) {
        List<EventType> eventsOnInvade = new ArrayList<>();
        eventsOnInvade.add(EventType.CASUALTIES);
        eventsOnInvade.add(EventType.ATTACKER_DICE);
        eventsOnInvade.add(EventType.DEFENDER_DICE);

        if (addConquerEvent) {
            eventsOnInvade.add(EventType.CONQUER);
        }
        return mockEvents(eventsOnInvade);
    }

    public static List<Event> mockEvents(List<EventType> events) {
        List<Event> occurredEvents = new ArrayList<>();

        for (EventType eventType : events) {
            Event mockEvent = null;
            switch (eventType) {
                case CASUALTIES:
                    mockEvent = mock(CasualtiesEvent.class);
                    break;
                case DEFENDER_DICE:
                case ATTACKER_DICE:
                    mockEvent = mock(DiceEvent.class);
                    break;
                case CONQUER:
                    mockEvent = mock(ConquerEvent.class);
                    break;
            }

            when(mockEvent.getEventType()).thenReturn(eventType);
            occurredEvents.add(mockEvent);
        }

        return occurredEvents;
    }
}
