package model.behavior;

import helpers.TestHelperBehavior;
import model.Country;
import model.Player;
import model.enums.Phase;
import model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AggressiveBehaviorTest {

    private Player testPlayer;
    private Player defendingPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(PlayerColor.BLUE, "Sony", new AggressiveBehavior());
        defendingPlayer = new Player(PlayerColor.GREEN, "", new RandomBehavior());
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
        int loopCnt = 100;
        int soldiersAttacker = 10;
        int soldiersDefender = 10;

        Country invadingCountry = Mockito.spy(new Country("", soldiersAttacker, testPlayer));
        Country defendingCountry = new Country("", 1, defendingPlayer);
        Country defendingCountry2 = new Country("", 1, defendingPlayer);

        //attack as often as possible
        selectedCountries.add(defendingCountry);
        ownedCountries.add(invadingCountry);
        invadingCountry.addNeighboringCountries(selectedCountries);

        for (int i = 0; i < loopCnt; i++) {
            resetForAttackCountry(soldiersAttacker, soldiersDefender);
            testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        }
        verify(invadingCountry, atLeast(loopCnt)).invade(any(), anyInt(), anyInt());

        //attack a country further
        defendingCountry.getNeighboringCountries().add(defendingCountry2);
        selectedCountries.add(defendingCountry2);
        soldiersDefender = 1;
        for (int i = 0; i < loopCnt; i++) {
            resetForAttackCountry(soldiersAttacker, soldiersDefender);
            testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        }
        verify(invadingCountry, atLeast(loopCnt * 2)).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryNotOwnCountry() {
        ownedCountries = TestHelperBehavior.getMockCountryList(5, testPlayer);

        Player opponentPlayer = new Player(PlayerColor.BLUE, "ownPlayer", new UserBehavior());
        Country attackCountry = TestHelperBehavior.getMockCountry(opponentPlayer);
        selectedCountries.add(attackCountry);
        selectedCountries.add(new Country("Spanien", 5, opponentPlayer));

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(attackCountry, never()).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testMoveSoldiers() {
        ownedCountries = TestHelperBehavior.getCountryList(4, testPlayer);
        Player opponentPlayer = TestHelperBehavior.getMockPlayer();

        assertEquals(Phase.SET, testPlayer.getBehavior().moveSoldiers(null, ownedCountries));

        for (Country countryList : ownedCountries) {
            countryList.addNeighboringCountries(TestHelperBehavior.getCountryList(3, opponentPlayer));
            assertEquals(5, countryList.getSoldiersCount());
        }


        for (Country countryList : ownedCountries) {
            assertEquals(5, countryList.getSoldiersCount());
        }
    }

    private void resetForAttackCountry(int attacker, int defender) {
        //attacker part
        resetForAttackCountryPlayer(attacker, testPlayer, ownedCountries);
        //defender part
        resetForAttackCountryPlayer(defender, defendingPlayer, selectedCountries);

    }

    private void resetForAttackCountryPlayer(int soldiers, Player player, List<Country> countries) {
        for (Country country : countries) {
            country.setSoldiersCount(soldiers);
            country.setOwner(player);
        }
        player.getOwnedCountries().clear();
        player.getOwnedCountries().addAll(countries);
    }
}

