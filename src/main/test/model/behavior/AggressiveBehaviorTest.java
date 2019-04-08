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

import static model.enums.PlayerColor.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        Country invadingCountry = Mockito.spy(new Country("", 10, testPlayer));
        Player defendingPlayer = new Player(PlayerColor.GREEN, "", new RandomBehavior());
        Country defendingCountry = new Country("", 1, defendingPlayer);
        defendingPlayer.getOwnedCountries().add(defendingCountry);
        selectedCountries.add(defendingCountry);
        invadingCountry.addNeighboringCountries(selectedCountries);
        ownedCountries.add(invadingCountry);

        for (int i = 0; i < 100; i++) {
            testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
            invadingCountry.setSoldiersCount(10);
            defendingCountry.setSoldiersCount(10);
            defendingCountry.setOwner(defendingPlayer);
            defendingPlayer.getOwnedCountries().add(defendingCountry);
            testPlayer.getOwnedCountries().remove(defendingCountry);
        }
        verify(invadingCountry, atLeast(100)).invade(any(), anyInt(), anyInt());

        Country defendingCountry2 = new Country("", 1, defendingPlayer);
        defendingCountry.getNeighboringCountries().add(defendingCountry2);
        selectedCountries.add(defendingCountry2);

        for (int i = 0; i < 100; i++) {
            testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
            invadingCountry.setSoldiersCount(10);
            defendingCountry.setSoldiersCount(1);
            defendingCountry.setOwner(defendingPlayer);
            defendingPlayer.getOwnedCountries().add(defendingCountry);


            defendingCountry2.setSoldiersCount(1);
            defendingCountry2.setOwner(defendingPlayer);
            defendingPlayer.getOwnedCountries().add(defendingCountry2);

            testPlayer.getOwnedCountries().remove(defendingCountry2);
            testPlayer.getOwnedCountries().remove(defendingCountry);
        }
        verify(invadingCountry, atLeast(200)).invade(any(), anyInt(), anyInt());


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

        assertEquals(Phase.SET, testPlayer.getBehavior().moveSoldiers(null, ownedCountries));
        for (Country countryList : ownedCountries) {
            assertEquals(5, countryList.getSoldiersCount());
        }
    }

}

