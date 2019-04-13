package model;

import helpers.TestHelperCountry;
import helpers.TestHelperEvents;
import model.behavior.RandomBehavior;
import model.enums.PlayerColor;
import model.helpers.Casualties;
import model.interfaces.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country invadingCountry;
    private Country defendingCountry;
    private int soldiersToShift = Board.START_SOLDIER_PER_PLAYER - Country.MIN_SOLDIERS_TO_STAY;

    @BeforeEach
    void setUp() {
        invadingCountry = new Country("countryTest", Board.START_SOLDIER_PER_PLAYER,
                new Player(PlayerColor.values()[(int) (Math.random() * PlayerColor.values().length)], "test", new RandomBehavior()));
        defendingCountry = new Country("countryTest", Board.START_SOLDIER_PER_PLAYER,
                new Player(PlayerColor.values()[(int) (Math.random() * PlayerColor.values().length)], "test", new RandomBehavior()));
        defendingCountry.getNeighboringCountries().add(invadingCountry);
        invadingCountry.getNeighboringCountries().add(defendingCountry);
    }

    @Test
    void testIsBordering() {
        assertTrue(invadingCountry.isBordering(defendingCountry));
        Board board = new Board();
        for (Country country : board.getCountries()) {
            for (Country neighboring : country.getNeighboringCountries()) {
                assertTrue(neighboring.getNeighboringCountries().contains(country));
                assertTrue(country.getNeighboringCountries().contains(neighboring));
                assertFalse(country.getNeighboringCountries().contains(new Country("", 0, country.getOwner())));
                assertFalse(neighboring.getNeighboringCountries().contains(new Country("", 0, country.getOwner())));
                assertFalse(neighboring.getNeighboringCountries().contains(null));
            }
        }
    }

    @Test
    void testMaxAmountDiceThrowsAttacker() throws Exception {
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, invadingCountry.maxAmountDiceThrowsAttacker());
        invadingCountry.setSoldiersCount(Country.MIN_SOLDIERS_TO_INVADE);
        assertEquals(1, invadingCountry.maxAmountDiceThrowsAttacker());
    }

    @Test
    void testAmountDiceThrowsDefender() throws Exception {
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER, defendingCountry.amountDiceThrowsDefender(Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER));
        defendingCountry.setSoldiersCount(1);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(invadingCountry.maxAmountDiceThrowsAttacker()));
    }

    @Test
    void testCalculateCasualtiesInvaderVictory() {
        Casualties casualties = invadingCountry.calculateCasualties(TestHelperCountry.CalculateRoll(6, 6,0) , TestHelperCountry.CalculateRoll(1, 1, 0));
        assertEquals(0, casualties.getCasualtiesAttacker());
        assertEquals(2, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesDefenderVictory() {
        Casualties casualties = invadingCountry.calculateCasualties(TestHelperCountry.CalculateRoll(1, 1, 1), TestHelperCountry.CalculateRoll(6, 6, 0));
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesStalemate() {
        Casualties casualties = invadingCountry.calculateCasualties(TestHelperCountry.CalculateRoll(6, 6, 6), TestHelperCountry.CalculateRoll(6, 6, 0));
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesOneDefender() {
        Casualties casualties = invadingCountry.calculateCasualties(TestHelperCountry.CalculateRoll(1, 1, 1), TestHelperCountry.CalculateRoll(6, 0, 0));
        assertEquals(1, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }

    @Test
    void testMaxAttackerDiceCount() throws Exception{

            invadingCountry.setSoldiersCount(2);
            assertEquals(1, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(3);
            assertEquals(2, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(4);
            assertEquals(3, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(100);
            assertEquals(3, invadingCountry.maxAmountDiceThrowsAttacker());

    }

    @Test
    void testMaxAttackerDiceCountException() {
        int[] testNumber = new int[]{1, 0, -30};
        for (int test : testNumber) {
            invadingCountry.setSoldiersCount(test);
            Exception exception = assertThrows(Exception.class, () -> invadingCountry.maxAmountDiceThrowsAttacker());
            assertEquals("could not calculate maxAttackerDiceCount", exception.getMessage());
        }
    }

    @Test
    void testMaxDefenderDiceCount() {
        defendingCountry.setSoldiersCount(10);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(1));
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(1));
        assertEquals(2, defendingCountry.amountDiceThrowsDefender(3));

        defendingCountry.setSoldiersCount(3);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(1));
        assertEquals(2, defendingCountry.amountDiceThrowsDefender(3));

        defendingCountry.setSoldiersCount(2);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(2));
        assertEquals(2, defendingCountry.amountDiceThrowsDefender(3));

        defendingCountry.setSoldiersCount(1);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(1));
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(2));
    }

    @Test
    void testShiftSoldiersValid() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertTrue(invadingCountry.shiftSoldiers(soldiersToShift, defendingCountry));
        assertEquals(Country.MIN_SOLDIERS_TO_STAY, invadingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER + soldiersToShift, defendingCountry.getSoldiersCount());
    }

    @Test
    void testShiftSoldiersToManySoldiers() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertFalse(invadingCountry.shiftSoldiers(invadingCountry.getSoldiersCount(), defendingCountry));
    }

    @Test
    void testShiftSoldiersNotSameOwner() {
        assertFalse(invadingCountry.shiftSoldiers(soldiersToShift, defendingCountry));
        assertFalse(defendingCountry.shiftSoldiers(soldiersToShift, invadingCountry));
        assertEquals(Board.START_SOLDIER_PER_PLAYER, defendingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER, invadingCountry.getSoldiersCount());
    }

    @Test
    void testShiftSoldiersNull() {
        assertThrows(IllegalArgumentException.class, () -> defendingCountry.shiftSoldiers(soldiersToShift, null));
    }

    @Test
    void testShiftSoldiersNotNeighboring() {
        removeNeighbors();
        assertFalse(defendingCountry.shiftSoldiers(soldiersToShift, invadingCountry));
        assertEquals(Board.START_SOLDIER_PER_PLAYER, invadingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER, defendingCountry.getSoldiersCount());
    }

    @Test
    void testInvade() {
        defendingCountry.setSoldiersCount(0);
        List<Event> events = invadingCountry.invade(defendingCountry, Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, 0);
        List<Event> eventsMock = TestHelperEvents.mockInvadeEvents(true);

        assertSame(defendingCountry.getOwner(), invadingCountry.getOwner());
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, defendingCountry.getSoldiersCount());
        assertEquals(4, events.size());

        for (Event event : events) {
            eventsMock.removeIf(mockEvent -> event.getEventType().equals(mockEvent.getEventType()));
        }
        assertTrue(eventsMock.isEmpty());
    }

    @Test
    void testRemoveSoldiersIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.removeSoldiers(-10));
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.removeSoldiers(invadingCountry.getSoldiersCount() + 4));
    }

    @Test
    void testRemoveSoldiersValid() {
        invadingCountry.removeSoldiers(4);
        assertEquals(Board.START_SOLDIER_PER_PLAYER - 4, invadingCountry.getSoldiersCount());
    }

    @Test
    void testCanInvadeValid() {
        assertTrue(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void testCanInvadeSameOwner() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void testCanInvadeNotNeighboring() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        removeNeighbors();
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void testCanInvadeToFewSoldiers() {
        invadingCountry.setSoldiersCount(Country.MIN_SOLDIERS_TO_STAY);
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void testCanInvadeNull() {
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.canInvade(null));
    }

    private void removeNeighbors() {
        invadingCountry.getNeighboringCountries().remove(defendingCountry);
        defendingCountry.getNeighboringCountries().remove(invadingCountry);
    }

}