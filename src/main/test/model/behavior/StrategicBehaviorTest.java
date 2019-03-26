package java.model.behavior;

import java.model.Country;
import java.model.enums.Phase;
import java.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.model.behavior.TestHelperBehavior.getMockCountry;
import static java.model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void placeSoldiers() {
        selectedCountries = TestHelperBehavior.getCountryList(1, testPlayer);
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));

        testPlayer.setSoldiersToPlace(2);

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(6, selectedCountries.get(0).getSoldiersCount());
        assertEquals(1, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(7, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(7, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());
    }

    @Test
    void testAttackCountry() {
        Player testPlayer2 = new Player(BLUE, "testplayer02", new UserBehavior());
        Country mockAttackCountry = getMockCountry(testPlayer2);

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