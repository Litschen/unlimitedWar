package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country invadingCountry;
    private Country defendingCountry;

    @BeforeEach
    void setUp() {
        invadingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player("red", "test", new RandomBehavior()));
        defendingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player("red", "test", new RandomBehavior()));
    }

    @Test
    void testIsBordering() {
        assertTrue(invadingCountry.isBordering(defendingCountry));
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
        assertEquals(3, casualties.getCasualtiesDefender());
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
    public void testMaxAttackerDiceCount_Valid() {
        try {
            invadingCountry.setSoldiersCount(3);
            assertEquals(invadingCountry.maxAmountDiceThrowsAttacker(), 1);

            invadingCountry.setSoldiersCount(3);
            assertEquals(invadingCountry.maxAmountDiceThrowsAttacker(), 2);

            invadingCountry.setSoldiersCount(4);
            assertEquals(invadingCountry.maxAmountDiceThrowsAttacker(), 3);

            invadingCountry.setSoldiersCount(10);
            assertEquals(invadingCountry.maxAmountDiceThrowsAttacker(), 3);

        } catch (Exception e) {
            System.out.println(e);
            assertTrue(false);
        }
    }

    @Test
    public void testMaxAttackerDiceCount_Invalid() {
        Exception exception = assertThrows(Exception.class, () -> invadingCountry.maxAmountDiceThrowsAttacker());
        assertEquals("could not calculate maxAttackerDiceCount", exception.getMessage());
    }

    @Test
    public void testMaxDefenderDiceCount() {
        defendingCountry.setSoldiersCount(10);
        assertEquals(defendingCountry.amountDiceThrowsDefender(1), 1);
        assertEquals(defendingCountry.amountDiceThrowsDefender(1), 1);
        assertEquals(defendingCountry.amountDiceThrowsDefender(3), 2);

        defendingCountry.setSoldiersCount(3);
        assertEquals(defendingCountry.amountDiceThrowsDefender(1), 1);
        assertEquals(defendingCountry.amountDiceThrowsDefender(3), 2);

        defendingCountry.setSoldiersCount(2);
        assertEquals(defendingCountry.amountDiceThrowsDefender(3), 2);
        assertEquals(defendingCountry.amountDiceThrowsDefender(2), 1);

        defendingCountry.setSoldiersCount(1);
        assertEquals(defendingCountry.amountDiceThrowsDefender(1), 1);
        assertEquals(defendingCountry.amountDiceThrowsDefender(2), 1);
    }
}