package model.Behaviors;

import model.Country;
import model.CountryTest;
import model.Enum.Phase;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Behaviors.testHelperBehavior.setUpMockCountry;
import static model.Enum.PlayerColor.BLUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RandomBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;


    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Lu", new UserBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void placeSoldiers() {
        selectedCountries = CountryTest.makeList(1, testPlayer);
        ownedCountries = CountryTest.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));

        testPlayer.setSoldiersToPlace(4);

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(6, selectedCountries.get(0).getSoldiersCount());
        assertEquals(3, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(7, selectedCountries.get(0).getSoldiersCount());
        assertEquals(2, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.SETTINGPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(8, selectedCountries.get(0).getSoldiersCount());
        assertEquals(1, testPlayer.getSoldiersToPlace());

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(selectedCountries, ownedCountries, 0));
        assertEquals(9, selectedCountries.get(0).getSoldiersCount());
        assertEquals(0, testPlayer.getSoldiersToPlace());
    }

    @Test
    void testAttackCountry() {
        Player testPlayer2 = new Player(BLUE, "testplayer02", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(testPlayer2);

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, testPlayer));
        ownedCountries = CountryTest.makeList(1, testPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());

    }

    @Test
    void testAttackCountryOwnCountries() {

        Player ownTestPlayer = new Player(BLUE, "ownPlayer", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(ownTestPlayer);

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, ownTestPlayer));
        ownedCountries = CountryTest.makeList(1, ownTestPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(mockAttackCountry, times(0)).invade(anyObject(), anyInt(), anyInt());

    }

    @Test
    void testAttackCountryNotOwnCountries() {
        Player opponentPlayer = new Player(BLUE, "opponentPlayer", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(opponentPlayer);

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, opponentPlayer));
        ownedCountries = CountryTest.makeList(1, opponentPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, selectedCountries);
        verify(mockAttackCountry, times(0)).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void moveSoldiers() {

        selectedCountries = CountryTest.makeList(2, testPlayer);
        ownedCountries = CountryTest.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));


        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));
    }
}