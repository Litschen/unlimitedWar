package model;

import model.Behaviors.RandomBehavior;
import model.Enum.ColorPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountryTest {

    private Country invadingCountry;
    private Country defendingCountry;

    @BeforeEach
    void setUp() {
        invadingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player(ColorPlayer.values()[(int)(Math.random()*ColorPlayer.values().length)], "test", new RandomBehavior()));
        defendingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player(ColorPlayer.values()[(int)(Math.random()*ColorPlayer.values().length)], "test", new RandomBehavior()));
        defendingCountry.getNeighboringCountries().add(invadingCountry);
        invadingCountry.getNeighboringCountries().add(defendingCountry);
    }

    @Test
    void testIsBordering() {
        assertTrue(invadingCountry.isBordering(defendingCountry));
        BoardBean boardBean = new BoardBean();
        for(Country country : boardBean.getCountries()){
            for(Country neighboring : country.getNeighboringCountries()){
                assertTrue(neighboring.getNeighboringCountries().contains(country));
                assertTrue(country.getNeighboringCountries().contains(neighboring));
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

            invadingCountry.setSoldiersCount(10);
            assertEquals(3, invadingCountry.maxAmountDiceThrowsAttacker());

        } catch (Exception e) {
            System.out.println(e);
            assertTrue(false);
        }
    }

    @Test
    public void testMaxAttackerDiceCountException() {
        invadingCountry.setSoldiersCount(1);
        Exception exception = assertThrows(Exception.class, () -> invadingCountry.maxAmountDiceThrowsAttacker());
        assertEquals("could not calculate maxAttackerDiceCount", exception.getMessage());
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
    void shiftSoldiers() {
    }
}