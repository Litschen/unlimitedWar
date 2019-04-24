package model.behavior;


import helpers.TestHelperBehavior;
import model.Country;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RandomBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> allCountries;
    private ArrayList<Country> ownedCountries;
    private Country invadingCountry;
    private Country defendingCountry;
    private Player mockPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(BLUE, "Lu", new RandomBehavior());
        ownedCountries = new ArrayList<>();
        allCountries = new ArrayList<>();
        invadingCountry = mock(Country.class);
        defendingCountry = mock(Country.class);
        mockPlayer = mock(Player.class);
    }

    @Test
    void testPlaceSoldiers() {
        ownedCountries = TestHelperBehavior.getCountryList(10, testPlayer);
        int sum = 0;
        int amountPerCountry = ownedCountries.get(0).getSoldiersCount();
        testPlayer.getBehavior().placeSoldiers(ownedCountries, ownedCountries, 10);

        for (Country country : ownedCountries) {
            sum += country.getSoldiersCount();
        }
        assertEquals(ownedCountries.size() * amountPerCountry + 10, sum);
    }

    @Test
    void testAttackCountry() {
        when(invadingCountry.canInvade(defendingCountry)).thenReturn(true);
        when(invadingCountry.getSoldiersCount()).thenReturn(10);

        when(invadingCountry.getNeighboringCountries()).thenReturn(allCountries);
        when(defendingCountry.getSoldiersCount()).thenReturn(10);
        allCountries.add(invadingCountry);
        allCountries.add(defendingCountry);
        ownedCountries.add(invadingCountry);

        for (int i = 0; i < 100; i++) {
            testPlayer.getBehavior().attackCountry(allCountries, ownedCountries);
        }

        verify(invadingCountry, atLeastOnce()).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testMoveSoldiers() {
        when(invadingCountry.isBordering(defendingCountry)).thenReturn(true);
        when(invadingCountry.getSoldiersCount()).thenReturn(10);

        when(invadingCountry.getNeighboringCountries()).thenReturn(allCountries);
        when(defendingCountry.getSoldiersCount()).thenReturn(10);

        when(invadingCountry.getOwner()).thenReturn(mockPlayer);
        when(defendingCountry.getOwner()).thenReturn(mockPlayer);
        when(mockPlayer.getOwnedCountries()).thenReturn(allCountries);
        allCountries.add(invadingCountry);
        allCountries.add(defendingCountry);
        ownedCountries.add(invadingCountry);

        for (int i = 0; i < 100; i++) {
            testPlayer.getBehavior().moveSoldiers(allCountries, ownedCountries);
        }

        verify(invadingCountry, atLeastOnce()).shiftSoldiers(anyInt(), any());
    }
}