package model.Behaviors;

import model.Country;
import model.CountryTest;
import model.Enum.Phase;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Enum.PlayerColor.BLUE;
import static org.junit.Assert.assertEquals;


class AggressiveBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;


    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Sony", new UserBehavior());
        ownedCountries = new ArrayList<>();
        selectedCountries = new ArrayList<>();
    }

    @Test
    void placeSoldiers() {
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
    void moveSoldiers() {

        selectedCountries = CountryTest.makeList(2, testPlayer);
        ownedCountries = CountryTest.makeList(4, testPlayer);
        ownedCountries.add(selectedCountries.get(0));
        ownedCountries.add(selectedCountries.get(1));


        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(selectedCountries, ownedCountries));
    }
}