package model.behavior;

import model.Country;
import model.Player;
import model.enums.Phase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(BLUE, "Jackob", new UserBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void testPlaceSoldiers() {
        selectedCountries = TestHelperBehavior.getCountryList(1, testPlayer);
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));

        testPlayer.setSoldiersToPlace(3);

        assertEquals(Phase.SET, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(6, selectedCountries.get(0).getSoldiersCount());
        assertEquals(2, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.SET, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(7, selectedCountries.get(0).getSoldiersCount());
        assertEquals(1, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACK, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(8, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACK, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(8, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());
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
    void testAttackCountryWithNotOwnCountry() {
        ownedCountries = TestHelperBehavior.getMockCountryList(5, testPlayer);

        Player player = new Player(BLUE, "ownPlayer", new UserBehavior());
        Country attackCountry = TestHelperBehavior.getMockCountry(player);
        selectedCountries.add(attackCountry);
        selectedCountries.add(ownedCountries.get(0));

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(attackCountry, never()).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testMoveSoldiers() {
        selectedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));

        assertEquals(Phase.MOVE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));
    }

}