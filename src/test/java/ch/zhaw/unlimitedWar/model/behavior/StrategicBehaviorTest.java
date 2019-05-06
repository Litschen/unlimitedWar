package ch.zhaw.unlimitedWar.model.behavior;

import ch.zhaw.unlimitedWar.helpers.TestHelperBehavior;
import ch.zhaw.unlimitedWar.model.Country;
import ch.zhaw.unlimitedWar.model.Player;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import ch.zhaw.unlimitedWar.model.helpers.PlaceSoldiers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ch.zhaw.unlimitedWar.model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StrategicBehaviorTest {

    private Player testPlayer;
    private List<Country> ownedCountries;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(BLUE, "Mike", new StrategicBehavior());
        ownedCountries = new ArrayList<>();
    }

    @Test
    void testPlaceSoldiersSetOnBoth() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 4), testPlayer);

        PlaceSoldiers placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 6);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);
        assertTrue(ownedCountries.get(0).getSoldiersCount() > 5);
        assertTrue(ownedCountries.get(1).getSoldiersCount() > 5);
    }

    @Test
    void testPlaceSoldiersSetOnOne() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(10, 0), testPlayer);

        PlaceSoldiers placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 3);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);
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

        PlaceSoldiers placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 10);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(15, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    @Test
    void testAttackCountryWeakDefendAndShift() {
        List<Country> toBeInvaded = new ArrayList<>();
        Country invadingCountry = Mockito.spy(new Country("", 1, testPlayer));
        when(invadingCountry.shiftSoldiers(anyInt(), any())).thenReturn(false);
        for (int i = 0; i < 10; i++) {
            setupWeakDefended(invadingCountry, 100, 1, 5);
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());

        }


        verify(invadingCountry, atLeast(50)).invade(any(), anyInt(), anyInt());
        verify(invadingCountry, atLeast(50)).shiftSoldiers(anyInt(), any());
    }

    @Test
    void testAttackCountryWeakest() {
        List<Country> toBeInvaded;
        Country invadingCountry = Mockito.spy(new Country("", 3, testPlayer));

        for (int i = 0; i < 10; i++) {
            toBeInvaded = setupWeakest(invadingCountry);
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());
        }

        verify(invadingCountry, atLeast(10)).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryOnlyWithAdvantage() {
        List<Country> toBeInvaded;
        Country invadingCountry = Mockito.spy(new Country("", 1, testPlayer));
        for (int i = 0; i < 10; i++) {
            toBeInvaded = setupWeakest(invadingCountry);
            invadingCountry.setSoldiersCount(2);
            testPlayer.getBehavior().attackCountry(toBeInvaded, testPlayer.getOwnedCountries());
        }

        verify(invadingCountry, never()).invade(any(), anyInt(), anyInt());
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

    private List<Country> setupWeakDefended(Country invadingCountry, int attacker, int defender, int countryCnt) {
        Player defendingPlayer = new Player(PlayerColor.GREEN, "", new RandomBehavior());
        invadingCountry.setSoldiersCount(attacker);
        invadingCountry.getNeighboringCountries().clear();

        List<Country> toBeInvaded = new ArrayList<>();

        for (int i = 0; i < countryCnt; i++) {
            toBeInvaded.add(new Country("", defender, defendingPlayer));
        }

        invadingCountry.addNeighboringCountries(toBeInvaded);
        testPlayer.getOwnedCountries().clear();
        testPlayer.getOwnedCountries().add(invadingCountry);

        return toBeInvaded;
    }

    private List<Country> setupWeakest(Country invadingCountry) {
        List<Country> toBeInvaded = setupWeakDefended(invadingCountry, 3, 30, 5);
        toBeInvaded.get(toBeInvaded.size() - 1).setSoldiersCount(4);
        return toBeInvaded;
    }
}