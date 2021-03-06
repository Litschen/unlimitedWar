package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.helpers.TestHelperEvents;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import ch.zhaw.unlimitedWar.model.interfaces.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoardTest {

    private Board testBoard;

    @BeforeEach
    void setUp() {
        testBoard = new Board(PlayerColor.BLUE, "Felix");
    }

    @Test
    void testGenerateCountries() {
        int ownedCountriesPrevious = testBoard.getPlayers().get(0).getOwnedCountries().size();
        for (Player currentPlayer : testBoard.getPlayers()) {
            assertEquals(ownedCountriesPrevious, currentPlayer.getOwnedCountries().size());
            int soldierCount = 0;
            for (Country country : currentPlayer.getOwnedCountries()) {
                soldierCount += country.getSoldiersCount();
            }
            assertEquals(Board.START_SOLDIER_PER_PLAYER, soldierCount);
        }
        assertEquals(Board.COUNTRY_COUNT_GENERATION, testBoard.getCountries().size());
    }

    /**
     * Only tests name setting, if bordering is set correctly is checked in CountryTest
     */
    @Test
    void testSetCountryAttributes() {
        for (Country currentCountry : testBoard.getCountries()) {
            assertNotEquals("", currentCountry.getName());
            assertTrue(currentCountry.getName().length() > 1);
        }
    }

    @Test
    void testSetCityAndCapital() {
        for (Player player : testBoard.getPlayers()) {
            List<Country> countries = player.getOwnedCountries();
            int city = (int) countries.stream().filter(country -> country.isCity()).count();
            int capital = (int) countries.stream().filter(country -> country.isCapital()).count();

            assertEquals(2, city);
            assertEquals(1, capital);
        }
    }

    @Test
    void testGetEvents() {
        List<Event> events = TestHelperEvents.mockInvadeEvents(false);
        testBoard.getCurrentTurn().addEvents(events);

        List<Event> returnedEvents = testBoard.getEvents();
        assertEquals(events, returnedEvents);
        assertTrue(testBoard.getCurrentTurn().getOccurredEvents().isEmpty());
    }

}

