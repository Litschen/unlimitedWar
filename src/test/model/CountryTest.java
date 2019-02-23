package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country invadingCountry;
    private Country defendingCountry;

    @BeforeEach
    void setUp(){
        invadingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player("red", "test", new RandomBehavior()));
        defendingCountry = new Country("countryTest", BoardBean.START_SOLDIER_PER_PLAYER,
                new Player("red", "test", new RandomBehavior()));
    }
    @Test
    void isBorderingTest() {
        assertTrue(invadingCountry.isBordering(defendingCountry));
    }

    @Test
    void maxAmountDiceThrowsAttackerTest() {
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER, invadingCountry.maxAmountDiceThrowsAttacker());
        invadingCountry.setSoldiersCount(Country.MIN_SOLDIERS_TO_INVADE);
        assertEquals(1, invadingCountry.maxAmountDiceThrowsAttacker());
    }

    @Test
    void amountDiceThrowsDefenderTest() {
        assertEquals(Country.ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER,
                defendingCountry.amountDiceThrowsDefender(invadingCountry.maxAmountDiceThrowsAttacker()));
        defendingCountry.setSoldiersCount(1);
        assertEquals(1, defendingCountry.amountDiceThrowsDefender(invadingCountry.maxAmountDiceThrowsAttacker()));
    }

    @Test
    void calculateCasualtiesInvaderVictoryTest() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{6,6,6}, new int[]{1,1});
        assertEquals(0, casualties.getCasualtiesAttacker());
        assertEquals(3, casualties.getCasualtiesDefender());
    }

    @Test
    void calculateCasualtiesDefenderVictoryTest() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{1,1,1}, new int[]{6,6});
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals( 0,casualties.getCasualtiesDefender());
    }

    @Test
    void calculateCasualtiesStalemateTest() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{6,6,6}, new int[]{6,6});
        assertEquals(2, casualties.getCasualtiesAttacker());
        assertEquals(0, casualties.getCasualtiesDefender() );
    }

    @Test
    void calculateCasualtiesOneDefenderTest() {
        Casualties casualties = invadingCountry.calculateCasualties(new int[]{1,1,1}, new int[]{6});
        assertEquals(1,casualties.getCasualtiesAttacker());
        assertEquals(0,casualties.getCasualtiesDefender());
    }
}