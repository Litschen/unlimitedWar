package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Enum.PlayerColor.BLUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/*
As information: the aggressive behavior is not implemented yet. Therefore, the tests do not success
 */

class AggressiveBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Sony", new AggressiveBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void placeSoldiers() {
        selectedCountries = TestHelperBehavior.makeList(1, testPlayer);
        ownedCountries = TestHelperBehavior.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));

        testPlayer.setSoldiersToPlace(3);

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(6, selectedCountries.get(0).getSoldiersCount());
        assertEquals(2, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(7, selectedCountries.get(0).getSoldiersCount());
        assertEquals(1, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(8, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(8, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());
    }

    @Test
    void testAttackCountry() {
        Player testPlayer2 = new Player(BLUE, "testplayer02", new UserBehavior());
        Country mockAttackCountry = TestHelperBehavior.setUpMockCountry(testPlayer2);

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, testPlayer));
        ownedCountries = TestHelperBehavior.makeList(1, testPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryNotOwnCountry() {
        ownedCountries = TestHelperBehavior.getMockCountryList(5, testPlayer);

        Player opponentPlayer = new Player(BLUE, "ownPlayer", new UserBehavior());
        Country attackCountry = TestHelperBehavior.setUpMockCountry(opponentPlayer);
        selectedCountries.add(attackCountry);
        selectedCountries.add(new Country("Spanien", 5, opponentPlayer));

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(attackCountry, never()).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void moveSoldiers() {
        selectedCountries = TestHelperBehavior.makeList(2, testPlayer);
        ownedCountries = TestHelperBehavior.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));

        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));
    }
}