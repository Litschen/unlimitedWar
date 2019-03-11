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

        assertEquals(Phase.MOVINGPHASE, testUserBehavior.attackCountry(selectedCountries, ownedCountries));
    }

    @Test
    void testMoveSoldiers(){}


    private ArrayList<Country> makeList(int numbersOfListElements) {
        ArrayList<Country> list = new ArrayList <> ();
        for (int i = 0; i < numbersOfListElements; i++) {
            list.add(i, new Country("Polen", 1, testplayer));
        }
        return list;}
}