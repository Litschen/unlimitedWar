package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/*
As information: the strategic behavior is not implemented yet. Therefore, the tests do not success
 */

class StrategicBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Mike", new StrategicBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void placeSoldiersSetOnBoth() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 4), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 6);
        assertTrue(ownedCountries.get(0).getSoldiersCount() > 5);
        assertTrue(ownedCountries.get(1).getSoldiersCount() > 5);
    }

    @Test
    void placeSoldiersSetOnOne() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(10, 0), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 3);
        assertEquals(8, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    /**
     * ownedCountries[0]: county surrounded by 5 opponent countries
     * ownedCountries[1]: county surrounded by 10 own countries
     * */
    @Test
    void placeSoldiersSetOnCountryInDanger() {
        ownedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        Player opponent = TestHelperBehavior.getMockPlayer();
        ownedCountries.get(0).addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponent));
        ownedCountries.get(1).addNeighboringCountries(TestHelperBehavior.getCountryList(10, testPlayer));

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 10);
        assertEquals(15, ownedCountries.get(0).getSoldiersCount());
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
    void moveSoldiers() {
        selectedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));

        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));
    }
}