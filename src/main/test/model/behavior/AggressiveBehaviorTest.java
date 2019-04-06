package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

class AggressiveBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(BLUE, "Sony", new AggressiveBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void testPlaceSoldiersSetOnBoth() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 4), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 2);
        assertEquals(6, ownedCountries.get(0).getSoldiersCount());
        assertEquals(6, ownedCountries.get(1).getSoldiersCount());

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 3);
        assertTrue(ownedCountries.get(0).getSoldiersCount() > 6);
        assertTrue(ownedCountries.get(1).getSoldiersCount() > 6);
    }

    @Test
    void testPlaceSoldiersSetOnOne() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 0), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 5);
        assertEquals(10, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    @Test
    void testAttackCountry() {
        Player testPlayer2 = new Player(BLUE, "testplayer02", new UserBehavior());
        Country mockAttackCountry = TestHelperBehavior.getMockCountry(testPlayer2);

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, testPlayer));
        ownedCountries = TestHelperBehavior.getCountryList(1, testPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryNotOwnCountry() {
        ownedCountries = TestHelperBehavior.getMockCountryList(5, testPlayer);

        Player opponentPlayer = new Player(BLUE, "ownPlayer", new UserBehavior());
        Country attackCountry = TestHelperBehavior.getMockCountry(opponentPlayer);
        selectedCountries.add(attackCountry);
        selectedCountries.add(new Country("Spanien", 5, opponentPlayer));

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(attackCountry, never()).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testMoveSoldiers() {
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().moveSoldiers(null, ownedCountries));
        for (Country countryList : ownedCountries) {
            assertEquals(5, countryList.getSoldiersCount());
        }
    }

}

