package model.behavior;

import helpers.TestHelperBehavior;
import model.Country;
import model.Player;
import model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/*
As information: the strategic behavior is not implemented yet. Therefore, the tests do not success
 */

class StrategicBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(BLUE, "Mike", new StrategicBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void testPlaceSoldiersSetOnBoth() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 4), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 6);
        assertTrue(ownedCountries.get(0).getSoldiersCount() > 5);
        assertTrue(ownedCountries.get(1).getSoldiersCount() > 5);
    }

    @Test
    void testPlaceSoldiersSetOnOne() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(10, 0), testPlayer);

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 3);
        assertEquals(8, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    /**
     * ownedCountries[0]: county surrounded by 5 opponent countries
     * ownedCountries[1]: county surrounded by 10 own countries
     */
    @Test
    void testPlaceSoldiersSetOnCountryInDanger() {
        ownedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        Player opponent = TestHelperBehavior.getMockPlayer();
        ownedCountries.get(0).addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponent));
        ownedCountries.get(1).addNeighboringCountries(TestHelperBehavior.getCountryList(10, testPlayer));

        testPlayer.getBehavior().placeSoldiers(null, ownedCountries, 10);
        assertEquals(15, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    @Test
    void testAttackCountryWeakDefend() {
        List<Country> toBeInvaded = new ArrayList<>();
        Country invadingCountry = Mockito.spy(new Country("", 100, testPlayer));
        setupWeakDefended(invadingCountry, toBeInvaded);

        for (int i = 0; i < 10; i++) {
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());
            setupWeakDefended(invadingCountry, toBeInvaded);
        }

        verify(invadingCountry, atLeast(50)).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryWeakest() {
        List<Country> toBeInvaded = new ArrayList<>();
        Country invadingCountry = Mockito.spy(new Country("", 100, testPlayer));

        for (int i = 0; i < 10; i++) {
            setupWeakest(invadingCountry, toBeInvaded);
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());
        }

        verify(invadingCountry, atLeast(10)).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryOnlyWithAdvantage() {
        List<Country> toBeInvaded = new ArrayList<>();
        Country invadingCountry = Mockito.spy(new Country("", 100, testPlayer));
        for (int i = 0; i < 10; i++) {
            setupWeakest(invadingCountry, toBeInvaded);
            invadingCountry.setSoldiersCount(2);
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());
        }

        verify(invadingCountry, never()).invade(any(), anyInt(), anyInt());
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
        ownedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        TestHelperBehavior.countriesBorderingEachOther(ownedCountries);

        Player opponent = TestHelperBehavior.getMockPlayer();

        Country src = ownedCountries.get(0);
        src.setSoldiersCount(11);
        src.addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponent));

        Country dest = ownedCountries.get(1);
        dest.addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponent));

        testPlayer.getBehavior().moveSoldiers(null, ownedCountries);

        assertEquals(8, src.getSoldiersCount());
        assertEquals(8, dest.getSoldiersCount());
    }

    @Test
    void testMoveSoldiersCheckBestOption() {
        ownedCountries = TestHelperBehavior.getCountryList(3, testPlayer);
        TestHelperBehavior.countriesBorderingEachOther(ownedCountries);

        Player opponent = TestHelperBehavior.getMockPlayer();

        Country src = ownedCountries.get(0);
        src.setSoldiersCount(11);

        for (Country country : ownedCountries) {
            country.addNeighboringCountries(TestHelperBehavior.getCountryList(2, opponent));
        }

        Country dest = ownedCountries.get(1);
        dest.setSoldiersCount(4);
        dest.addNeighboringCountries(TestHelperBehavior.getCountryList(2, opponent));

        testPlayer.getBehavior().moveSoldiers(null, ownedCountries);

        assertEquals(8, src.getSoldiersCount());
        assertEquals(7, dest.getSoldiersCount());
    }

    @Test
    void testMoveSoldiersNotPossible() {
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);

        Player opponent = TestHelperBehavior.getMockPlayer();
        for (Country country : ownedCountries) {
            country.addNeighboringCountries(TestHelperBehavior.getCountryList(4, opponent));
        }

        testPlayer.getBehavior().moveSoldiers(null, ownedCountries);

        for (Country country : ownedCountries) {
            assertEquals(5, country.getSoldiersCount());
        }
    }

    private void setupWeakDefended(Country invadingCountry, List<Country> toBeInvaded) {
        Player defendingPlayer = new Player(PlayerColor.GREEN, "", new RandomBehavior());
        invadingCountry.setSoldiersCount(100);
        invadingCountry.getNeighboringCountries().clear();

        toBeInvaded = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            toBeInvaded.add(new Country("", 1, defendingPlayer));
        }

        invadingCountry.addNeighboringCountries(toBeInvaded);
        testPlayer.getOwnedCountries().clear();
        testPlayer.getOwnedCountries().add(invadingCountry);
    }

    private void setupWeakest(Country invadingCountry, List<Country> toBeInvaded) {
        setupWeakDefended(invadingCountry, toBeInvaded);
        toBeInvaded = invadingCountry.getNeighboringCountries();
        invadingCountry.setSoldiersCount(3);

        for (Country country : toBeInvaded) {
            country.setSoldiersCount(30);
        }

        toBeInvaded.get(toBeInvaded.size() - 1).setSoldiersCount(2);
    }
}