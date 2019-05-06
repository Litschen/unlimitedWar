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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

class AggressiveBehaviorTest {

    private Player testPlayer;
    private Player defendingPlayer;
    private List<Country> selectedCountries;
    private List<Country> ownedCountries;

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

        PlaceSoldiers placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 2);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(6, ownedCountries.get(0).getSoldiersCount());
        assertEquals(6, ownedCountries.get(1).getSoldiersCount());

        testPlayer.addOwnedCountries(ownedCountries);
        placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 3);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);
        assertTrue(ownedCountries.get(0).getSoldiersCount() > 6);
        assertTrue(ownedCountries.get(1).getSoldiersCount() > 6);
    }

    @Test
    void testPlaceSoldiersSetOnOne() {
        ownedCountries = TestHelperBehavior.setUpToTestPlaceSoldiers(2, Arrays.asList(4, 0), testPlayer);

        testPlayer.addOwnedCountries(ownedCountries);
        PlaceSoldiers placeSoldiers = TestHelperBehavior.createPlaceSoldiers(testPlayer, ownedCountries, 5);
        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(10, ownedCountries.get(0).getSoldiersCount());
        assertEquals(5, ownedCountries.get(1).getSoldiersCount());
    }

    @Test
    void testUseCardOnSet() {
        Country countryOfCard = new Country("Test", 10, testPlayer);
        PlaceSoldiers placeSoldiers = TestHelperBehavior.setUpPlaceSoldierWithCards(testPlayer, countryOfCard, 1, false, 1);

        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(0, testPlayer.getCards().size());
        assertEquals(21, countryOfCard.getSoldiersCount());
    }

    @Test
    void testUseCardOnSetWithOwningBonus() {
        Country countryOfCard = new Country("Test", 10, testPlayer);
        PlaceSoldiers placeSoldiers = TestHelperBehavior.setUpPlaceSoldierWithCards(testPlayer, countryOfCard, 1, true, 1);

        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(0, testPlayer.getCards().size());
        assertEquals(22, countryOfCard.getSoldiersCount());
    }

    @Test
    void testUseMultipleCardOnSet() {
        Country countryOfCard = new Country("Test", 10, testPlayer);
        PlaceSoldiers placeSoldiers = TestHelperBehavior.setUpPlaceSoldierWithCards(testPlayer, countryOfCard, 1, false, 5);

        testPlayer.getBehavior().placeSoldiers(placeSoldiers);

        assertEquals(0, testPlayer.getCards().size());
        assertEquals(25, countryOfCard.getSoldiersCount());
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
        defendingCountry.addNeighboringCountry(defendingCountry2);
        selectedCountries.add(defendingCountry2);
        soldiersDefender = 1;
        for (int i = 0; i < loopCnt; i++) {
            resetForAttackCountry(soldiersAttacker, soldiersDefender);
            testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        }
        verify(invadingCountry, atLeast(loopCnt * 2)).invade(any(), anyInt(), anyInt());
    }

    @Test
    void testMoveSoldiers() {
        ownedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        Player opponentPlayer = TestHelperBehavior.getMockPlayer();
        TestHelperBehavior.countriesBorderingEachOther(ownedCountries);
        ownedCountries.get(0).addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponentPlayer));

        testPlayer.getBehavior().moveSoldiers(null, ownedCountries);

        assertEquals(9, ownedCountries.get(0).getSoldiersCount());
        assertEquals(1, ownedCountries.get(1).getSoldiersCount());
    }

    @Test
    void testMoveSoldiersOption() {
        List<Country> ownedCountries1;
        ownedCountries = TestHelperBehavior.getCountryList(2, testPlayer);
        ownedCountries1 = TestHelperBehavior.getCountryList(2, testPlayer);

        Player opponentPlayer = TestHelperBehavior.getMockPlayer();

        TestHelperBehavior.countriesBorderingEachOther(ownedCountries);
        TestHelperBehavior.countriesBorderingEachOther(ownedCountries1);

        ownedCountries.get(0).addNeighboringCountries(TestHelperBehavior.getCountryList(5, opponentPlayer));
        ownedCountries.addAll(ownedCountries1);
        testPlayer.getBehavior().moveSoldiers(null, ownedCountries);

        assertEquals(9, ownedCountries.get(0).getSoldiersCount());

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

