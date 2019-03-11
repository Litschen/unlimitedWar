package model.Behaviors;

import model.Country;
import model.CountryTest;
import model.Enum.Phase;
import model.Enum.PlayerColor;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Enum.PlayerColor.BLUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class UserBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;


    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Jackob", new UserBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void testPlaceSoldiers() {

        selectedCountries = CountryTest.makeList(1, testPlayer);
        ownedCountries = CountryTest.makeList(4, testPlayer);
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
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());

        // 2 Mal Meine Länder
        //Andere TestMethode
        //Testattack own country
    }

    @Test
    void testAttackCountryNotOwnCountries() {
        Player testPlayer2 = new Player(BLUE, "testplayer03", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(new Player(PlayerColor.GREEN, "Max", new UserBehavior()));

        selectedCountries.add(mockAttackCountry);
        selectedCountries.add(new Country("Spanien", 5, testPlayer2));
        ownedCountries = CountryTest.makeList(1, testPlayer2);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(selectedCountries, ownedCountries);
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());
        // 2 Mal Andere Länder
        //Andere TestMethode
        //Testattack own country
    }

    @Test
    void testMoveSoldiers() {

        selectedCountries = CountryTest.makeList(2, testPlayer);
        ownedCountries = CountryTest.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));


        testPlayer.calculateSoldiersToPlace();
        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));

    }


    private Country setUpMockCountry(Player player) {
        Country mockCountry = mock(Country.class);
        when(mockCountry.canInvade(anyObject())).thenReturn(true);
        when(mockCountry.getOwner()).thenReturn(player);
        when(mockCountry.canInvade(anyObject())).thenReturn(true);

        return mockCountry;
    }
}