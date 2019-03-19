package model;

import model.behavior.RandomBehavior;
import model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {

    private Country invadingCountry;
    private Country defendingCountry;
    private int soldiersToShift = Board.START_SOLDIER_PER_PLAYER - Country.MIN_SOLDIERS_TO_STAY;

    @BeforeEach
    void setUp() {
        invadingCountry = new Country("countryTest", Board.START_SOLDIER_PER_PLAYER,
                new Player(PlayerColor.values()[(int)(Math.random()*PlayerColor.values().length)], "test", new RandomBehavior()));
        defendingCountry = new Country("countryTest", Board.START_SOLDIER_PER_PLAYER,
                new Player(PlayerColor.values()[(int)(Math.random()*PlayerColor.values().length)], "test", new RandomBehavior()));
        defendingCountry.getNeighboringCountries().add(invadingCountry);
        invadingCountry.getNeighboringCountries().add(defendingCountry);
    }

    @Test
    void testIsBordering() {
        assertTrue(invadingCountry.isBordering(defendingCountry));
        Board board = new Board();
        for(Country country : board.getCountries()){
            for(Country neighboring : country.getNeighboringCountries()){
                assertTrue(neighboring.getNeighboringCountries().contains(country));
                assertTrue(country.getNeighboringCountries().contains(neighboring));
                assertFalse(country.getNeighboringCountries().contains(new Country("", 0,country.getOwner())));
                assertFalse(neighboring.getNeighboringCountries().contains(new Country("", 0,country.getOwner())));
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
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{6, 6, 6}, new int[]{1, 1});
        assertEquals(0, casualties.getCasualtiesAttacker());
        assertEquals(2, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesDefenderVictory() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{1, 1, 1}, new int[]{6, 6});
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesStalemate() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{6, 6, 6}, new int[]{6, 6});
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }

    @Test
    void testCalculateCasualtiesOneDefender() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{1, 1, 1}, new int[]{6});
        assertEquals(1, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender());
    }


    @Test
    public void testMaxAttackerDiceCount() {
        try {
            invadingCountry.setSoldiersCount(2);
            assertEquals(1, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(3);
            assertEquals(2, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(4);
            assertEquals(3, invadingCountry.maxAmountDiceThrowsAttacker());

            invadingCountry.setSoldiersCount(100);
            assertEquals(3, invadingCountry.maxAmountDiceThrowsAttacker());

        } catch (Exception e) {
            fail();
        }


    }

    @Test
    public void testMaxAttackerDiceCountException() {
        int[] testNumber = new int[] {1,0,-30};
        for(int test : testNumber){
            invadingCountry.setSoldiersCount(test);
            Exception exception = assertThrows(Exception.class, () -> invadingCountry.maxAmountDiceThrowsAttacker());
            assertEquals("could not calculate maxAttackerDiceCount", exception.getMessage());
        }
    }

    @Test
    public void testMaxDefenderDiceCount() {
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
    void shiftSoldiersValid() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertTrue(invadingCountry.shiftSoldiers(soldiersToShift, defendingCountry));
        assertEquals(Country.MIN_SOLDIERS_TO_STAY, invadingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER + soldiersToShift, defendingCountry.getSoldiersCount());
    }

    @Test
    void shiftSoldiersToManySoldiers() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertFalse(invadingCountry.shiftSoldiers(invadingCountry.getSoldiersCount(), defendingCountry));
    }

    @Test
    void shiftSoldiersNotSameOwner() {
        assertFalse(invadingCountry.shiftSoldiers(soldiersToShift, defendingCountry));
        assertFalse(defendingCountry.shiftSoldiers(soldiersToShift, invadingCountry));
        assertEquals(Board.START_SOLDIER_PER_PLAYER, defendingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER, invadingCountry.getSoldiersCount());
    }

    @Test
    void shiftSoldiersNull() {
        assertThrows(IllegalArgumentException.class, () -> defendingCountry.shiftSoldiers(soldiersToShift, null));
    }
    @Test
    void shiftSoldiersNotNeighboring() {
        removeNeighbors();
        assertFalse(defendingCountry.shiftSoldiers(soldiersToShift, invadingCountry));
        assertEquals(Board.START_SOLDIER_PER_PLAYER, invadingCountry.getSoldiersCount());
        assertEquals(Board.START_SOLDIER_PER_PLAYER, defendingCountry.getSoldiersCount());
    }

    @Test
    void invade() {
        defendingCountry.setSoldiersCount(0);
        invadingCountry.invade(defendingCountry, Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, 0);
        assertSame(defendingCountry.getOwner(), invadingCountry.getOwner());
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, defendingCountry.getSoldiersCount());
    }

    @Test
    void removeSoldiersIllegalArguments() {
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.removeSoldiers(-10));
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.removeSoldiers(invadingCountry.getSoldiersCount() + 4));
    }
    @Test
    void removeSoldiersValid() {
        invadingCountry.removeSoldiers(4);
        assertEquals(Board.START_SOLDIER_PER_PLAYER - 4, invadingCountry.getSoldiersCount());
    }


    @Test
    void canInvadeValid() {
        assertTrue(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void canInvadeSameOwner() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void canInvadeNotNeighboring() {
        defendingCountry.setOwner(invadingCountry.getOwner());
        removeNeighbors();
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void canInvadeToFewSoldiers() {
        invadingCountry.setSoldiersCount(Country.MIN_SOLDIERS_TO_STAY);
        assertFalse(invadingCountry.canInvade(defendingCountry));
    }

    @Test
    void canInvadeNull() {
        assertThrows(IllegalArgumentException.class, () -> invadingCountry.canInvade(null));
    }

    private void removeNeighbors() {
        invadingCountry.getNeighboringCountries().remove(defendingCountry);
        defendingCountry.getNeighboringCountries().remove(invadingCountry);
    }
}